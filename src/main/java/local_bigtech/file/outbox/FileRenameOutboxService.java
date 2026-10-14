package local_bigtech.file.outbox;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Transactional Outbox, "продюсер": см. local_bigtech/file/outbox/schema.sql.
 * В отличие от local_bigtech.file.FileRenameService — здесь внутри @Transactional
 * нет НИКАКОГО I/O на файловую систему. Единственное, что делает транзакция —
 * обновляет files.name и кладёт событие-намерение в file_rename_outbox,
 * двумя SQL-операциями в рамках одной короткой DB-транзакции.
 * Реальный rename на диске выполняет {@link FileRenameOutboxRelay} асинхронно,
 * уже вне этой транзакции — поэтому лок на строке (FOR UPDATE) и соединение
 * с БД никогда не удерживаются на время диска.
 */
@Service
public class FileRenameOutboxService {

    private final JdbcTemplate jdbcTemplate;

    public FileRenameOutboxService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void renameFile(String oldName, String newName) {
        if (oldName == null || oldName.isBlank() || newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("oldName/newName must not be blank");
        }
        if (oldName.equals(newName)) {
            return;
        }

        Long fileId = jdbcTemplate.queryForObject(
                "select id from files where name = ? for update",
                Long.class,
                oldName
        );

        Integer clashCount = jdbcTemplate.queryForObject(
                "select count(*) from files where name = ?",
                Integer.class,
                newName
        );
        if (clashCount != null && clashCount > 0) {
            throw new IllegalStateException("File with name " + newName + " already exists");
        }

        // "Логическое" переименование видно читателям БД сразу после коммита.
        jdbcTemplate.update(
                "update files set name = ? where id = ?",
                newName,
                fileId
        );

        // Публикуем намерение переименовать на ФС в той же транзакции —
        // атомарно с update выше за счёт того, что это один и тот же commit.
        // Сам rename на диске сюда НЕ входит.
        jdbcTemplate.update(
                "insert into file_rename_outbox (file_id, old_name, new_name, status, attempts, created_at) " +
                        "values (?, ?, ?, 'PENDING', 0, now())",
                fileId,
                oldName,
                newName
        );
    }
}
