package remote_preparation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


class TransactionTest {

    @Autowired
    private ResourceLoader resourceLoader;

    @Test
    void aggregation() {
        List<Transaction> transactions = Arrays.asList(
                new Transaction("Покупка", 500.0, "USD"),
                new Transaction("Продажа", 300.0, "EUR"),
                new Transaction("Покупка", 700.0, "USD"),
                new Transaction("Продажа", 150.0, "EUR"),
                new Transaction("Покупка", 200.0, "RUB"),
                new Transaction("Обмен", 1200.0, "USD"),
                new Transaction("Покупка", 800.0, "EUR"),
                new Transaction("Продажа", 100.0, "RUB"),
                new Transaction("Покупка", 400.0, "GBP"),
                new Transaction("Обмен", 50.0, "RUB")
        );

        Map<String, Double> result = transactions.stream()
                // Шаг 1: Группируем по валюте и суммируем amount
                .collect(Collectors.groupingBy(
                        Transaction::getCurrency,           // Ключ: валюта
                        Collectors.summingDouble(Transaction::getAmount) // Значение: сумма
                ))
                // Шаг 2: Преобразуем Map в Stream для дальнейшей обработки
                .entrySet().stream()
                // Шаг 3: Фильтруем валюты с общей суммой > 1000
                .filter(entry -> entry.getValue() > 1000)
                // Шаг 4: Сортируем по убыванию суммы
                .sorted((entry1, entry2) -> Double.compare(entry2.getValue(), entry1.getValue()))
                // Шаг 5: Собираем в новый Map (сохраняя порядок)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,                  // Ключ: валюта
                        Map.Entry::getValue,                // Значение: сумма
                        (e1, e2) -> e1,                     // При конфликте (не возникнет)
                        LinkedHashMap::new                  // Сохраняем порядок
                ));

        System.out.println(result);
        LinkedBlockingQueue e = new LinkedBlockingQueue<>();
        List<String> oleg = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:filename.txt");
        Map<String, String> map = new HashMap<>();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        ForkJoinPool pool = new ForkJoinPool(4);
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("dick", "pick");
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("dich");
    }

}