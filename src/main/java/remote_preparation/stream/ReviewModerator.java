package remote_preparation.stream;

import java.util.List;

// покупатели оставляют отзывы о продажах. Модерация хочет скрывать отзывы,
// которые содержат слова из чёрного списка (например, "Срочно", "бесплатно", "займы", "переход").
public class ReviewModerator {
    public static void main(String[] args) {
        List<String> reviews = List.of("Хороший товар", "Срочно куплю!", "Норм", "бесплатно!!!", "!");
        List<String> stopWords = List.of("Срочно", "бесплатно");

        List<String> result = reviews.stream()
                .filter(review -> stopWords.stream()
                        .noneMatch(stopWord -> review.toLowerCase().contains(stopWord.toLowerCase())))
                .toList();

        System.out.println(result);
    }
}
