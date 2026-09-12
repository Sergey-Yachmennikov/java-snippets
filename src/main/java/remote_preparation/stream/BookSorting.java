package remote_preparation.stream;

import java.util.Comparator;
import java.util.List;

// Предположим, у вас есть список объектов класса `Book`, каждый из которых содержит свойства:
//
//        - `title` (название книги, тип String)
//        - `pages` (количество страниц, тип int).
//
//        Необходимо:
//        1. Отфильтровать книги с 200+ страницами.
//        2. Отсортировать их по убыванию количества страниц.
//        3. Извлечь названия первых трёх книг.

record Book(String title, int pages) {}

public class BookSorting {

    public static void main(String[] args) {
        List<Book> books = List.of(
                new Book("Java Programming", 250),
                new Book("Spring Boot Guide", 320),
                new Book("Effective Java", 150),
                new Book("Clean Code", 400),
                new Book("Algorithms", 180)
        );

        List<String> result = books.stream()
                .filter(b -> b.pages() >= 200)
                .sorted(Comparator.comparingInt(Book::pages).reversed())
                .limit(3)
                .map(Book::title)
                .toList();

        System.out.println(result);
    }
}
