-- Outbox для переименования файлов (local_bigtech.file.outbox).
-- Отдельная таблица-очередь: продюсер (FileRenameOutboxService) пишет сюда
-- строку в ТОЙ ЖЕ DB-транзакции, где меняет files.name — без похода на ФС.
-- Консьюмер (FileRenameOutboxRelay) забирает PENDING-строки отдельными
-- короткими транзакциями и уже вне транзакции выполняет I/O на диске.
CREATE TABLE file_rename_outbox (
                                     id         BIGINT PRIMARY KEY AUTO_INCREMENT,
                                     file_id    BIGINT NOT NULL REFERENCES files(id),
                                     old_name   VARCHAR(255) NOT NULL,
                                     new_name   VARCHAR(255) NOT NULL,
                                     status     VARCHAR(16) NOT NULL DEFAULT 'PENDING', -- PENDING | DONE | FAILED
                                     attempts   INT NOT NULL DEFAULT 0,
                                     last_error VARCHAR(1000),
                                     created_at TIMESTAMP NOT NULL
);

-- Под самый частый запрос релея: "дай мне порцию PENDING-событий по id".
CREATE INDEX idx_file_rename_outbox_status ON file_rename_outbox(status, id);
