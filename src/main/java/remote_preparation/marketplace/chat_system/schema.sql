-- Пользователь
CREATE TABLE users (
                       id            BIGINT PRIMARY KEY,
                       name          VARCHAR(255) NOT NULL,
                       registered_at TIMESTAMP NOT NULL
);

-- Чат
CREATE TABLE chats (
                       id         BIGINT PRIMARY KEY,
                       name       VARCHAR(255) NOT NULL,
       1                created_at TIMESTAMP NOT NULL
);

-- Связь многие-ко-многим User <-> Chat: отдельная таблица обязательна,
-- потому что ни один внешний ключ не может выразить связь "многие ко многим"
-- напрямую — она всегда моделируется через промежуточную таблицу
-- с составным первичным ключом из пары внешних ключей.
CREATE TABLE chat_members (
                              chat_id  BIGINT NOT NULL REFERENCES chats(id),
                              user_id  BIGINT NOT NULL REFERENCES users(id),
                              joined_at TIMESTAMP NOT NULL,
                              PRIMARY KEY (chat_id, user_id)
);

-- Сообщение: chat_id и author_id — NOT NULL, а не nullable.
-- "Обязательно принадлежит чату" и "не может принадлежать больше чем
-- одному чату" вместе означают: РОВНО один чат — это в точности то,
-- что выражает единственный NOT NULL внешний ключ, а не связующая
-- таблица (в отличие от Chat<->User, где связь именно многие-ко-многим).
CREATE TABLE messages (
                          id         BIGINT PRIMARY KEY,
                          chat_id    BIGINT NOT NULL REFERENCES chats(id),
                          author_id  BIGINT NOT NULL REFERENCES users(id),
                          text       TEXT NOT NULL,
                          created_at TIMESTAMP NOT NULL
);

-- Индекс под самый частый запрос к этой схеме — подсчёт сообщений
-- по чату (см. задачу 2). Без индекса это full scan таблицы messages
-- на каждый запрос активности чата.
CREATE INDEX idx_messages_chat_id ON messages(chat_id);