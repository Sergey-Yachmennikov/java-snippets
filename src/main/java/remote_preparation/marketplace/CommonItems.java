package remote_preparation.marketplace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CommonItems {

    /** Продавец: id + список товаров. record — потому что это просто неизменяемые данные. */
    record Seller(int sellerId, List<String> items) {
    }

    /**
     * Задача — пересечение множеств (SET INTERSECTION) товаров по всем
     * продавцам: то, что встречается у КАЖДОГО, а не сумма или объединение.
     *
     * ПОЧЕМУ LinkedHashSet, А НЕ ПРОСТО HashSet: результат в примере
     * из условия — ['стол', 'кофта'], и это НЕ алфавитный порядок,
     * а порядок ПЕРВОГО ПОЯВЛЕНИЯ этих товаров в списке ПЕРВОГО
     * продавца ("варежки, шуба, валенки, СТОЛ, тапка, шарф, КОФТА,
     * рубашка" — стол на позиции 3, кофта на позиции 6, то есть
     * стол раньше). Обычный HashSet порядок не гарантирует вообще
     * (и на практике он зависит от хешей строк, то есть недетерминирован
     * между разными наборами данных) — LinkedHashSet сохраняет порядок
     * вставки, что и даёт воспроизводимый, соответствующий примеру результат.
     *
     * АЛГОРИТМ: берём множество товаров ПЕРВОГО продавца (в порядке
     * вставки), затем последовательно пересекаем его с множеством
     * товаров каждого следующего продавца через retainAll — этот метод
     * оставляет в множестве только элементы, присутствующие в обоих,
     * и КАК РАЗ И ЕСТЬ операция пересечения множеств "из коробки".
     *
     * retainAll работает лучше, если у ВХОДНОГО множества (у которого
     * вызван метод) реализация с быстрым contains — здесь это
     * LinkedHashSet у накопителя, а не наоборот, поэтому множество
     * очередного продавца оборачивается в обычный HashSet перед
     * передачей в retainAll: сам retainAll вызывается на накопителе
     * (LinkedHashSet), а проверка "содержится ли элемент" идёт
     * в переданное множество (HashSet), и она должна быть O(1).
     *
     * ДЕДУПЛИКАЦИЯ ВНУТРИ ОДНОГО ПРОДАВЦА: если в списке товаров
     * одного продавца есть повторы (сам по себе список — не set),
     * Set автоматически схлопывает их — это корректно, потому что
     * нас интересует ФАКТ наличия товара у продавца, а не сколько раз
     * он там упомянут.
     *
     * Сложность: O(N), где N — суммарное количество товаров у ВСЕХ
     * продавцов (каждый товар обрабатывается константное число раз
     * при построении множеств и при retainAll), в отличие от наивного
     * подхода "для каждого товара первого продавца проверить
     * List.contains для каждого другого продавца", который был бы
     * O(items1 * items2 * ... * itemsK) в худшем случае из-за линейного
     * поиска в списках вместо O(1) в хеш-множествах.
     */
    static List<String> commonItems(List<Seller> goods) {
        if (goods.isEmpty()) {
            return List.of();
        }

        Set<String> common = new LinkedHashSet<>(goods.get(0).items());

        for (int i = 1; i < goods.size(); i++) {
            common.retainAll(new HashSet<>(goods.get(i).items()));
        }

        return new ArrayList<>(common);
    }

    public static void main(String[] args) {
        List<Seller> goods = List.of(
                new Seller(1, List.of("варежки", "шуба", "валенки", "стол", "тапка", "шарф", "кофта", "рубашка")),
                new Seller(2, List.of("микро", "тумба", "майка", "стол", "ложка", "кофта")),
                new Seller(3, List.of("вилка", "кофта", "тарелка", "шарф", "шуба", "стол", "кастрюля")),
                new Seller(4, List.of("кровер", "тумба", "кофта", "пылесос", "валенки", "ложка", "стол"))
        );

        check(commonItems(goods), List.of("стол", "кофта"), "пример из условия");

        check(commonItems(List.of()), List.of(), "нет продавцов вообще");

        check(commonItems(List.of(new Seller(1, List.of("а", "б", "в")))),
                List.of("а", "б", "в"), "один продавец — все его товары общие");

        check(commonItems(List.of(
                new Seller(1, List.of("а", "б")),
                new Seller(2, List.of("в", "г"))
        )), List.of(), "нет ни одного общего товара");

        check(commonItems(List.of(
                new Seller(1, List.of("а", "а", "б")), // повтор "а" у одного продавца
                new Seller(2, List.of("а", "б", "б"))
        )), List.of("а", "б"), "дубликаты внутри списка продавца не влияют на результат");

        check(commonItems(List.of(
                new Seller(1, List.of("з", "а", "б")),
                new Seller(2, List.of("а", "б", "з")),
                new Seller(3, List.of("б", "з", "а"))
        )), List.of("з", "а", "б"), "порядок результата — по первому появлению у ПЕРВОГО продавца");

        System.out.println("Все проверки пройдены");
    }

    private static void check(List<String> actual, List<String> expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
