package local_bigtech.file.outbox;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Transactional Outbox, "консьюмер"/релей: см. local_bigtech/file/outbox/schema.sql.
 * Требует @EnableScheduling на классе @SpringBootApplication, иначе
 * {@link #processPendingEvents()} не будет вызываться.
 * Ключевая идея: claimBatch() — короткая DB-транзакция без I/O, а сам
 * rename на ФС в {@link #process(OutboxEvent)} выполняется УЖЕ ПОСЛЕ её
 * коммита, вне какой-либо транзакции и без удержания локов/соединения.
 */
@Component
public class FileRenameOutboxRelay {

    private static final int MAX_ATTEMPTS = 5;

    private final JdbcTemplate jdbcTemplate;

    public FileRenameOutboxRelay(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Scheduled(fixedDelay = 5000)
    public void processPendingEvents() {
        for (OutboxEvent event : claimBatch(20)) {
            process(event);
        }
    }

    // "FOR UPDATE SKIP LOCKED" позволяет нескольким инстансам релея разбирать
    // очередь параллельно: строки, уже захваченные другим инстансом, просто
    // пропускаются, а не блокируют текущего (в отличие от обычного FOR UPDATE).
    @Transactional
    public List<OutboxEvent> claimBatch(int limit) {
        return jdbcTemplate.query(
                "select id, file_id, old_name, new_name, attempts " +
                        "from file_rename_outbox " +
                        "where status = 'PENDING' " +
                        "order by id " +
                        "for update skip locked " +
                        "limit ?",
                (rs, rowNum) -> new OutboxEvent(
                        rs.getLong("id"),
                        rs.getLong("file_id"),
                        rs.getString("old_name"),
                        rs.getString("new_name"),
                        rs.getInt("attempts")
                ),
                limit
        );
    }

    private void process(OutboxEvent event) {
        try {
            renameOnFileSystemIdempotent(event.oldName(), event.newName());
            markDone(event.id());
        } catch (Exception e) {
            markFailedAttempt(event.id(), event.attempts() + 1, e.getMessage());
        }
    }

    // Идемпотентность — обязательное требование для outbox-воркера: процесс
    // может упасть между успешным rename на диске и markDone(), и тогда это
    // же событие будет обработано повторно. Реальная реализация должна сверять
    // фактическое состояние путей на диске (например, Files.exists(oldPath) /
    // Files.exists(newPath)) и считать операцию уже выполненной, если oldPath
    // больше не существует, а newPath уже существует, а не просто повторять
    // rename "в слепую". Тело не важно по условию задачи — здесь оставлено как
    // заглушка.
    private void renameOnFileSystemIdempotent(String oldName, String newName) {
        // тело не важно
    }

    @Transactional
    void markDone(long eventId) {
        jdbcTemplate.update(
                "update file_rename_outbox set status = 'DONE' where id = ?",
                eventId
        );
    }

    // После MAX_ATTEMPTS неудачных попыток событие помечается FAILED и выходит
    // из цикла автоматических ретраев — дальше это "dead letter", требующий
    // ручного вмешательства/алерта, а не бесконечного повтора.
    @Transactional
    void markFailedAttempt(long eventId, int attempts, String error) {
        String status = attempts >= MAX_ATTEMPTS ? "FAILED" : "PENDING";
        jdbcTemplate.update(
                "update file_rename_outbox set attempts = ?, status = ?, last_error = ? where id = ?",
                attempts,
                status,
                error,
                eventId
        );
    }
}
