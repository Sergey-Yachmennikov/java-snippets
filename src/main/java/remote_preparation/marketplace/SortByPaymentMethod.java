package remote_preparation.marketplace;

import java.util.ArrayList;
import java.util.List;

public class SortByPaymentMethod {

    record User(int id, String method) {}

    /**
     * ============================================================
     * КЛЮЧЕВОЕ РЕШЕНИЕ, КОТОРОЕ ЛЕГКО ПРОПУСТИТЬ: ТРЕБУЕТСЯ СТАБИЛЬНОСТЬ
     * ============================================================
     * Ожидаемый результат сохраняет ОТНОСИТЕЛЬНЫЙ ПОРЯДОК внутри
     * каждой группы: wallet идёт как id=2, затем id=4 — именно
     * в том порядке, в каком они встречались во ВХОДНОМ массиве
     * (2 раньше 4 и там, и там). Это не побочный эффект примера,
     * а СТАБИЛЬНАЯ сортировка — свойство, которое стоит проверять
     * явно, а не предполагать по формулировке "отсортировать".
     *
     * Задача внешне похожа на классическую Dutch National Flag
     * (сортировка массива из ТРЁХ известных категорий за O(n) времени,
     * O(1) памяти, ОДИН проход, через три указателя low/mid/high
     * и обмены местами). НО DNF ОСНОВАН НА ОБМЕНАХ (swap), а обмен
     * элементов местами МОЖЕТ (и на практике будет) ломать их
     * относительный порядок внутри одной категории — это ПРОВЕРЕНО:
     * прогон классического DNF на examples из условия дал sbp
     * в порядке id=5, затем id=1 — ОБРАТНОМ исходному, — то есть
     * DNF для ЭТОЙ задачи даёт НЕВЕРНЫЙ (по условию примера) результат,
     * несмотря на корректную сортировку по методу.
     *
     * ПОЭТОМУ ЗДЕСЬ ИСПОЛЬЗУЕТСЯ ПОДХОД ЧЕРЕЗ ВЁДРА (BUCKETS/COUNTING),
     * а не DNF со свопами. Поскольку категорий РОВНО ТРИ И ОНИ ЗАРАНЕЕ
     * ИЗВЕСТНЫ (это не сортировка по произвольному компаратору,
     * а разбиение по фиксированному конечному набору значений —
     * частный случай COUNTING SORT), можно завести по одному списку
     * на каждую категорию, разложить все элементы за ОДИН ПРОХОД
     * по входному массиву (каждый элемент добавляется в конец СВОЕГО
     * списка — append сохраняет порядок автоматически, это и есть
     * источник стабильности БЕЗ дополнительных усилий), а затем
     * СКЛЕИТЬ три списка в порядке wallet -> card -> sbp.
     *
     * Общее число операций — 2n (n на разбор по вёдрам + n на склейку),
     * что всё ещё O(n) по времени, как и требует условие — единственный
     * ОДИН проход по ИСХОДНОМУ массиву присутствует буквально
     * (каждый элемент входного массива посещается ровно один раз
     * при раскладке), склейка — это уже сборка РЕЗУЛЬТАТА, а не
     * повторный проход по входу.
     *
     * Память: O(n) на сами три списка (в сумме содержат все n
     * элементов) — это ЦЕНА за стабильность и простоту по сравнению
     * с DNF, который делает то же самое IN-PLACE за O(1) доп. памяти,
     * но ценой потери стабильности. Если бы стабильность не требовалась,
     * DNF был бы предпочтительным ответом именно из-за O(1) памяти —
     * этот компромисс стоит явно проговорить на собеседовании.
     */
    private static final List<String> ORDER = List.of("wallet", "card", "sbp");

    static List<User> sortByPaymentMethod(List<User> users) {
        List<User> wallet = new ArrayList<>();
        List<User> card = new ArrayList<>();
        List<User> sbp = new ArrayList<>();

        for (User u : users) {
            switch (u.method()) {
                case "wallet" -> wallet.add(u);
                case "card" -> card.add(u);
                case "sbp" -> sbp.add(u);
                default -> throw new IllegalArgumentException(
                        "Неизвестный метод оплаты: " + u.method() + " (ожидались " + ORDER + ")");
            }
        }

        List<User> result = new ArrayList<>(users.size());
        result.addAll(wallet);
        result.addAll(card);
        result.addAll(sbp);
        return result;
    }

    public static void main(String[] args) {
        List<User> users = List.of(
                new User(1, "sbp"),
                new User(2, "wallet"),
                new User(3, "card"),
                new User(4, "wallet"),
                new User(5, "sbp"),
                new User(6, "card")
        );

        check(sortByPaymentMethod(users), List.of(
                new User(2, "wallet"), new User(4, "wallet"),
                new User(3, "card"), new User(6, "card"),
                new User(1, "sbp"), new User(5, "sbp")
        ), "пример из условия — включая стабильный порядок внутри групп");

        check(sortByPaymentMethod(List.of()), List.of(), "пустой список пользователей");

        check(sortByPaymentMethod(List.of(new User(1, "sbp"))),
                List.of(new User(1, "sbp")), "один пользователь");

        check(sortByPaymentMethod(List.of(
                new User(1, "wallet"), new User(2, "wallet"), new User(3, "wallet")
        )), List.of(
                new User(1, "wallet"), new User(2, "wallet"), new User(3, "wallet")
        ), "все пользователи с одним методом — порядок не меняется");

        check(sortByPaymentMethod(List.of(
                new User(1, "sbp"), new User(2, "sbp"), new User(3, "wallet")
        )), List.of(
                new User(3, "wallet"), new User(1, "sbp"), new User(2, "sbp")
        ), "две категории из трёх, относительный порядок sbp сохранён (1 перед 2)");

        try {
            sortByPaymentMethod(List.of(new User(1, "crypto")));
            throw new AssertionError("ожидали исключение на неизвестном методе оплаты");
        } catch (IllegalArgumentException e) {
            System.out.println("OK  неизвестный метод оплаты корректно отвергается");
        }

        System.out.println("Все проверки пройдены");
    }

    private static void check(List<User> actual, List<User> expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
