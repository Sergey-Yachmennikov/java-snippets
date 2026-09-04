package remote_preparation.marketplace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopKFrequent {

    // ============================================================
    // ПОДХОД 1: BUCKET SORT (сортировка по вёдрам) — O(n) времени
    // ============================================================

    /**
     * Ключевое наблюдение: частота ЛЮБОГО элемента не может превышать n
     * (длину массива) — элемент физически не может встретиться больше
     * n раз в массиве из n элементов. Значит, диапазон ВОЗМОЖНЫХ частот
     * заранее известен и ОГРАНИЧЕН: от 1 до n, а не произволен.
     *
     * Это позволяет применить СЧЁТНУЮ сортировку по частоте вместо
     * сравнения элементов друг с другом (обычная сортировка сравнением
     * не может быть быстрее O(n log n) — фундаментальный предел,
     * а вот сортировка БЕЗ сравнений, по известному диапазону значений,
     * может быть линейной).
     *
     * Заводим buckets[f] — список элементов с частотой РОВНО f,
     * для f от 0 до n. Раскладываем каждый уникальный элемент
     * в бакет, соответствующий его частоте — это O(k) операций,
     * где k — число уникальных элементов, k <= n.
     *
     * Дальше идём по buckets ОТ НАИБОЛЬШЕЙ ЧАСТОТЫ К НАИМЕНЬШЕЙ
     * (buckets[n], buckets[n-1], ..., buckets[1]) и набираем первые
     * k элементов — это и есть k самых частых, потому что мы
     * обходим бакеты СТРОГО в порядке убывания частоты.
     *
     * TIE-BREAKING (порядок элементов с ОДИНАКОВОЙ частотой) условием
     * НЕ ОПРЕДЕЛЁН. Здесь элементы внутри одного бакета идут в порядке
     * их первого появления при построении frequency map (HashMap
     * этого не гарантирует строго, но на практике для целых чисел
     * малого диапазона порядок обычно стабилен; если tie-breaking
     * важен для конкретной задачи — нужно явно уточнять правило,
     * например "меньшее значение раньше" или "более раннее первое
     * появление", и сортировать бакет соответствующим образом).
     *
     * Сложность: O(n) — один проход для подсчёта частот (n),
     * один проход для раскладки по бакетам (k <= n), один проход
     * по бакетам от n до 1 в худшем случае (n бакетов), итого O(n).
     * ЭТО ЛУЧШЕ, ЧЕМ O(n log n) обычной сортировки по частоте —
     * при больших n разница существенна.
     */
    static List<Integer> topKFrequentBucket(int[] arr, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : arr) {
            freq.merge(x, 1, Integer::sum);
        }

        int n = arr.length;
        List<List<Integer>> buckets = new ArrayList<>(n + 1);
        for (int i = 0; i <= n; i++) {
            buckets.add(new ArrayList<>());
        }
        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            buckets.get(e.getValue()).add(e.getKey());
        }

        List<Integer> result = new ArrayList<>(k);
        for (int f = n; f >= 1 && result.size() < k; f--) {
            for (int val : buckets.get(f)) {
                result.add(val);
                if (result.size() == k) {
                    break;
                }
            }
        }
        return result;
    }

    // ============================================================
    // ПОДХОД 2: MIN-HEAP РАЗМЕРА K — O(n log k) времени
    // ============================================================

    /**
     * Альтернатива для случаев, когда n НЕ известно заранее или очень
     * велико (bucket sort требует массив buckets размера n+1 — при n
     * порядка 10^9 это O(n) памяти впустую, даже если k мало).
     *
     * Идея: поддерживаем min-heap (кучу с минимумом на вершине)
     * размера РОВНО k. Проходим по всем уникальным (элемент, частота)
     * парам; если куча ещё не заполнена — добавляем; если заполнена
     * и текущая частота БОЛЬШЕ минимума в куче — вытесняем минимум
     * и добавляем текущий элемент. В конце куча содержит ровно k
     * элементов с наибольшими частотами.
     *
     * ПОЧЕМУ ЭТО ЛУЧШЕ ПРИ МАЛОМ k И БОЛЬШОМ n: размер кучи ограничен
     * k, а не n — просеивание (sift up/down) стоит O(log k), а не
     * O(log n). При k существенно меньше n (например, "топ-10 из
     * миллиарда") это заметно эффективнее полной сортировки O(n log n)
     * и не требует O(n) памяти под массив buckets.
     *
     * Сложность: O(n log k) — n уникальных элементов, каждая операция
     * с кучей размера k стоит O(log k). Память: O(n) на frequency map
     * (неизбежно для ЛЮБОГО подхода — нужно же посчитать частоты)
     * + O(k) на саму кучу, БЕЗ O(n) под buckets.
     */
    static List<Integer> topKFrequentHeap(int[] arr, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int x : arr) {
            freq.merge(x, 1, Integer::sum);
        }

        // Куча сравнивает ПАРЫ (значение, частота) по частоте — минимум наверху
        PriorityQueue<Map.Entry<Integer, Integer>> minHeap =
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());

        for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
            minHeap.offer(e);
            if (minHeap.size() > k) {
                minHeap.poll(); // выкидываем текущий минимум — он не входит в топ-k
            }
        }

        List<Integer> result = new ArrayList<>(k);
        while (!minHeap.isEmpty()) {
            result.add(minHeap.poll().getKey());
        }
        // Куча отдаёт от МЕНЬШЕЙ частоты к БОЛЬШЕЙ (poll с min-heap) —
        // разворачиваем, чтобы порядок совпадал с bucket-подходом
        // (от самой частой к менее частой)
        java.util.Collections.reverse(result);
        return result;
    }

    public static void main(String[] args) {
        int[] arr = {1, 1, 1, 1, 2, 3, 3, 3, 4, 4};
        int k = 3;

        check(topKFrequentBucket(arr, k), List.of(1, 3, 4), "[bucket] пример из условия");
        check(topKFrequentHeap(arr, k), List.of(1, 3, 4), "[heap] пример из условия");

        check(topKFrequentBucket(new int[]{1}, 1), List.of(1), "[bucket] один элемент");
        check(topKFrequentHeap(new int[]{1}, 1), List.of(1), "[heap] один элемент");

        check(topKFrequentBucket(new int[]{1, 2, 3, 4}, 4), sortedAsSet(List.of(1, 2, 3, 4)),
                "[bucket] k равен числу уникальных элементов, все частоты равны 1");

        check(topKFrequentBucket(new int[]{5, 5, 5, 5, 5}, 1), List.of(5),
                "[bucket] один уникальный элемент, встречается много раз");
        check(topKFrequentHeap(new int[]{5, 5, 5, 5, 5}, 1), List.of(5),
                "[heap] один уникальный элемент, встречается много раз");

        check(topKFrequentBucket(new int[]{4, 1, -1, 2, -1, 2, 3}, 2), List.of(-1, 2),
                "[bucket] с отрицательными числами, k=2");
        check(topKFrequentHeap(new int[]{4, 1, -1, 2, -1, 2, 3}, 2), List.of(-1, 2),
                "[heap] с отрицательными числами, k=2");

        System.out.println("Все проверки пройдены (оба подхода)");
    }

    private static List<Integer> sortedAsSet(List<Integer> values) {
        List<Integer> copy = new ArrayList<>(values);
        copy.sort(null);
        return copy;
    }

    private static void check(List<Integer> actual, List<Integer> expected, String label) {
        List<Integer> a = sortedAsSet(actual);
        List<Integer> e = sortedAsSet(expected);
        if (!a.equals(e)) {
            throw new AssertionError(label + ": ожидали (как множество) " + e + ", получили " + a);
        }
        System.out.println("OK  " + label + " -> " + actual);
    }
}
