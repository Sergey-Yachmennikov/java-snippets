package remote_preparation.marketplace.chat_system;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MostActiveChat {

    /** Пользователь: id + имя + дата регистрации. */
    record User(long id, String name, Instant registeredAt) {
    }

    /** Чат: id + название + дата создания. */
    record Chat(long id, String name, Instant createdAt) {
    }

    /**
     * Сообщение: chatId и authorId — НЕ Optional, а обязательные long.
     * Это прямое отражение ограничения из условия: "сообщение обязательно
     * принадлежит чату" и "не может принадлежать более чем одному чату
     * одновременно" вместе значат — РОВНО одно значение chatId,
     * не список и не nullable-поле. Модель типов сама не даёт
     * создать сообщение без чата или с несколькими чатами.
     */
    record Message(long id, long chatId, long authorId, String text, Instant createdAt) {
    }

    /**
     * Результат подсчёта активности: сам чат + число сообщений.
     * Отдельный record вместо голого Map.Entry — так возвращаемое
     * значение самодокументировано в сигнатуре метода.
     */
    record ChatActivity(Chat chat, long messageCount) {
    }

    /**
     * Находит чат(ы) с максимальным числом сообщений.
     *
     * ВОЗВРАЩАЕТСЯ СПИСОК, А НЕ ОДИН ЧАТ — сознательное решение.
     * Условие говорит "самый активный чат", в единственном числе,
     * но ничего не гарантирует ОТСУТСТВИЕ ничьей по числу сообщений.
     * Если два чата набрали одинаковый максимум, произвольный выбор
     * одного из них (например, "первый встреченный") — это скрытое,
     * недетерминированное решение, зависящее от порядка обхода
     * HashMap, а не от бизнес-логики. Возврат СПИСКА делает ничью
     * видимой вызывающему коду, который сам решает, как её разрешить
     * (например, взять чат с более ранней createdAt).
     *
     * АЛГОРИТМ — та же частотная карта, что в задачах 34/36/45:
     * один проход по сообщениям, накапливаем count по chatId,
     * затем один проход по счётчикам, чтобы найти максимум
     * и все чаты, его достигшие.
     *
     * Сложность: O(M + C), где M — число сообщений, C — число чатов.
     * Без индекса это ровно то же самое, что делает SQL-запрос
     *   SELECT chat_id, COUNT(*) FROM messages GROUP BY chat_id
     *   ORDER BY COUNT(*) DESC
     * — GROUP BY тоже линеен по числу строк при наличии индекса
     * по chat_id (см. idx_messages_chat_id в schema.sql).
     */
    static List<ChatActivity> findMostActiveChats(List<Chat> chats, List<Message> messages) {
        Map<Long, Long> countByChatId = new HashMap<>();
        for (Message m : messages) {
            countByChatId.merge(m.chatId(), 1L, Long::sum);
        }

        long maxCount = 0;
        for (long count : countByChatId.values()) {
            maxCount = Math.max(maxCount, count);
        }

        List<ChatActivity> result = new ArrayList<>();
        for (Chat chat : chats) {
            long count = countByChatId.getOrDefault(chat.id(), 0L);
            if (count == maxCount && maxCount > 0) {
                result.add(new ChatActivity(chat, count));
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Instant now = Instant.parse("2026-01-01T00:00:00Z");

        User alice = new User(1, "Alice", now);
        User bob = new User(2, "Bob", now);

        Chat general = new Chat(1, "General", now);
        Chat random = new Chat(2, "Random", now);
        Chat empty = new Chat(3, "Empty", now);

        List<Chat> chats = List.of(general, random, empty);

        List<Message> messages = List.of(
                new Message(1, general.id(), alice.id(), "привет", now),
                new Message(2, general.id(), bob.id(), "как дела", now),
                new Message(3, general.id(), alice.id(), "норм", now),
                new Message(4, random.id(), bob.id(), "лол", now)
        );

        List<ChatActivity> mostActive = findMostActiveChats(chats, messages);
        check(mostActive.size(), 1, "ровно один явный лидер по числу сообщений");
        check(mostActive.get(0).chat().id(), general.id(), "лидер — general (3 сообщения против 1)");
        check(mostActive.get(0).messageCount(), 3L, "у лидера 3 сообщения");

        // Ничья по количеству сообщений
        List<Message> tiedMessages = List.of(
                new Message(1, general.id(), alice.id(), "привет", now),
                new Message(2, general.id(), bob.id(), "как дела", now),
                new Message(3, random.id(), alice.id(), "хай", now),
                new Message(4, random.id(), bob.id(), "ку", now)
        );
        List<ChatActivity> tied = findMostActiveChats(chats, tiedMessages);
        check(tied.size(), 2, "ничья: оба чата с 2 сообщениями возвращаются оба");

        // Нет сообщений вообще
        check(findMostActiveChats(chats, List.of()).size(), 0, "без сообщений нет активного чата");

        // Пустой список чатов
        check(findMostActiveChats(List.of(), messages).size(), 0, "без чатов результат пуст, даже если сообщения есть");

        System.out.println("Все проверки пройдены");
    }

    private static void check(long actual, long expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
