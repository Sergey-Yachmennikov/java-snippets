package remote_preparation.marketplace;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterReviews {

    /**
     * ГЛАВНОЕ РЕШЕНИЕ, КОТОРОЕ УСЛОВИЕ НЕ ЗАДАЁТ ЯВНО: искать стоп-слово
     * как ПОДСТРОКУ или как ОТДЕЛЬНОЕ СЛОВО (по границам слов)?
     *
     * Пример из условия ЭТУ развилку не показывает: "бесплатно!!!"
     * содержит "бесплатно" и как подстроку, и как отдельное слово
     * (пунктуация не считается частью слова) — оба подхода дают
     * одинаковый результат на этом примере.
     *
     * Но подходы РАСХОДЯТСЯ на входах вроде:
     *   стоп-слово "кот", отзыв "Который завтра приедет"
     * Подстрочный поиск заблокирует отзыв, потому что "кот" — подстрока
     * слова "коТорый". Отзыв, не имеющий никакого отношения к котам,
     * будет ложно отфильтрован — классическая проблема ЛОЖНЫХ
     * СРАБАТЫВАНИЙ (false positive) в системах модерации на подстроках.
     * (Это тот же класс проблем, что печально известен по реальным
     * автоматическим фильтрам: "Scunthorpe problem" — блокировка
     * невинных слов, содержащих подстроку из списка запрещённых.)
     *
     * Поэтому здесь ВЫБРАНО сравнение ПО ГРАНИЦАМ СЛОВ: отзыв
     * содержит стоп-слово, только если стоп-слово встречается
     * как ЦЕЛОЕ слово (токен), а не как часть более длинного слова.
     * Для системы модерации отзывов это осмысленный выбор
     * по умолчанию, но его стоило бы уточнить у заказчика задачи —
     * оба поведения бывают нужны в разных контекстах.
     *
     * ВТОРАЯ РАЗВИЛКА: РЕГИСТР. Пример из условия содержит
     * "Срочно" (стоп-слово) и "Срочно куплю!" (отзыв) — с ОДИНАКОВЫМ
     * регистром первой буквы, поэтому по примеру нельзя определить,
     * нужно ли сравнение регистронезависимым. Для реальной системы
     * модерации регистронезависимое сравнение — единственный разумный
     * выбор: иначе "СРОЧНО" или "срочно" (написанные капсом или
     * с маленькой буквы) обошли бы фильтр — а именно такое написание
     * характерно для спама. Здесь приведение к нижнему регистру
     * сделано явно, а не оставлено неопределённым поведением.
     *
     * ПУНКТУАЦИЯ И ЮНИКОД: токенизация через регулярку \p{L}\p{N}
     * (юникодные классы "буква" и "цифра") вместо \w или простого
     * split по пробелам — \w в Java по умолчанию соответствует
     * только ASCII-буквам (a-zA-Z0-9_), кириллица под него НЕ
     * подпадает без явного флага UNICODE_CHARACTER_CLASS. Без этого
     * "Срочно" разбилось бы на отдельные "символы, не входящие в \w"
     * и слово перестало бы правильно выделяться как токен.
     */
    private static final Pattern WORD_PATTERN =
            Pattern.compile("[\\p{L}\\p{N}]+", Pattern.UNICODE_CHARACTER_CLASS);

    /**
     * Возвращает отзывы, НЕ содержащие ни одного стоп-слова
     * (как отдельное слово, без учёта регистра).
     *
     * Сложность: построение множества стоп-слов — O(m), где m —
     * суммарная длина stopWords. Дальше для каждого отзыва — токенизация
     * O(длина отзыва) и проверка каждого токена в HashSet — O(1)
     * в среднем на токен. Итого O(N + M), где N — суммарная длина
     * всех отзывов, M — суммарная длина стоп-слов, БЕЗ учёта числа
     * стоп-слов как отдельного множителя (в отличие от наивного
     * подхода "для каждого отзыва проверить containsIgnoreCase
     * с каждым стоп-словом", который был бы O(reviews * stopWords)
     * по количеству сравнений, а HashSet сводит это к O(1) на слово).
     */
    static List<String> filterReviews(String[] reviews, String[] stopWords) {
        Set<String> stopWordSet = new HashSet<>();
        for (String sw : stopWords) {
            stopWordSet.add(sw.toLowerCase(Locale.ROOT));
        }

        List<String> result = new ArrayList<>();
        for (String review : reviews) {
            if (!containsStopWord(review, stopWordSet)) {
                result.add(review);
            }
        }
        return result;
    }

    private static boolean containsStopWord(String review, Set<String> stopWordSet) {
        String lower = review.toLowerCase(Locale.ROOT);
        Matcher matcher = WORD_PATTERN.matcher(lower);
        while (matcher.find()) {
            if (stopWordSet.contains(matcher.group())) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String[] reviews = {"Хороший товар", "Срочно куплю!", "Норм", "бесплатно!!!"};
        String[] stopWords = {"Срочно", "бесплатно"};

        check(filterReviews(reviews, stopWords),
                List.of("Хороший товар", "Норм"),
                "пример из условия");

        check(filterReviews(new String[]{"Который завтра приедет"}, new String[]{"кот"}),
                List.of("Который завтра приедет"),
                "стоп-слово как подстрока другого слова НЕ должно блокировать отзыв");

        check(filterReviews(new String[]{"У меня есть кот"}, new String[]{"кот"}),
                List.of(),
                "стоп-слово как ОТДЕЛЬНОЕ слово должно блокировать отзыв");

        check(filterReviews(new String[]{"СРОЧНО продам", "срочно продам", "Срочно продам"},
                        new String[]{"срочно"}),
                List.of(),
                "сравнение регистронезависимое во всех вариантах написания");

        check(filterReviews(new String[]{"Товар просто супер!"}, new String[]{"плохой"}),
                List.of("Товар просто супер!"),
                "стоп-слово отсутствует — отзыв проходит");

        check(filterReviews(new String[]{}, new String[]{"спам"}), List.of(), "пустой массив отзывов");
        check(filterReviews(new String[]{"Привет"}, new String[]{}), List.of("Привет"), "пустой массив стоп-слов");

        check(filterReviews(new String[]{"бесплатно, но не точно"}, new String[]{"бесплатно"}),
                List.of(),
                "знаки препинания сразу после стоп-слова не мешают распознаванию");

        System.out.println("Все проверки пройдены");
    }

    private static void check(List<String> actual, List<String> expected, String label) {
        if (!actual.equals(expected)) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
