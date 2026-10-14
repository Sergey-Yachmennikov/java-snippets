package local_bigtech.file;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FileRenameService {

    private final JdbcTemplate jdbcTemplate;

    public FileRenameService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // @Transactional покрывает ТОЛЬКО БД. Чтобы минимизировать окно рассинхрона
    // между БД и ФС, необратимый шаг (переименование на ФС) выполняется ПОСЛЕДНИМ,
    // перед коммитом: если он упадёт, Spring откатит update ниже бесплатно,
    // и БД снова будет соответствовать реальному состоянию ФС (oldName).
    // Остаточный риск (ФС переименована, но сам коммит транзакции падает уже
    // после return) неустраним обычным JDBC-transactional и требует либо
    // идемпотентного повторного вызова, либо сверочной (reconciliation) задачи,
    // либо outbox/аудит-лога для ручного восстановления — здесь не реализовано.
    @Transactional
    public void renameFile(String oldName, String newName) {
        if (oldName == null || oldName.isBlank() || newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("oldName/newName must not be blank");
        }
        if (oldName.equals(newName)) {
            return;
        }

        // SELECT ... FOR UPDATE блокирует строку до конца транзакции — исключает
        // гонку с параллельным renameFile над тем же файлом.
        // queryForObject бросит EmptyResultDataAccessException, если файла с таким
        // именем нет, и IncorrectResultSizeDataAccessException, если имена не уникальны
        // (name явно не объявлен UNIQUE) — оба случая стоит оборачивать в понятное
        // доменное исключение, здесь не сделано для краткости.
        // От SQL-инъекций защищено: "?" — параметр PreparedStatement, а не
        // конкатенация строки. Не заменять на String.format/"+" с oldName внутри SQL.
        Long fileId = jdbcTemplate.queryForObject(
                "select id from files where name = ? for update",
                Long.class,
                oldName
        );

        // Проверяем конфликт имени ДО похода на ФС — чтобы не переименовывать файл
        // впустую, если update ниже всё равно упадёт на unique-констрейнте newName.
        Integer clashCount = jdbcTemplate.queryForObject(
                "select count(*) from files where name = ?",
                Integer.class,
                newName
        );
        if (clashCount != null && clashCount > 0) {
            throw new IllegalStateException("File with name " + newName + " already exists");
        }

        // 1) Сначала обновляем БД — это ОБРАТИМЫЙ шаг: если что-то дальше пойдёт
        // не так, транзакция откатится, и этот update "исчезнет" сам по себе.
        jdbcTemplate.update(
                "update files set name = ? where id = ?",
                newName,
                fileId
        );

        // 2) Переименование на ФС — НЕОБРАТИМЫЙ шаг, поэтому он идёт последним,
        // прямо перед неявным коммитом. Если он бросит исключение — транзакция
        // откатится, и update выше откатится вместе с ней (консистентность с ФС).
        renameOnFileSystem(oldName, newName);

        // Нет логирования/аудита шагов, хотя операция затрагивает два разных
        // хранилища (ФС + БД) — без логов невозможно восстановить, что именно
        // разошлось при частичном сбое (см. комментарий про остаточный риск выше).
    }

    private void renameOnFileSystem(String oldName, String newName) {
        // тело не важно
    }
}
