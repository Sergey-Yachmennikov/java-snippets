package remote_preparation;

import java.util.*;

public class CategoryCounter {

    // Модель категории
    static class Category {
        String name;
        List<Category> children;

        Category(String name, List<Category> children) {
            this.name = name;
            this.children = children;
        }
    }

    public static void main(String[] args) {
        List<Category> categories = List.of(
                new Category("Бытовая техника", List.of(
                        new Category("Телевизоры", List.of(
                                new Category("ЭЛТ", List.of()),
                                new Category("LED", List.of()),
                                new Category("OLED", List.of())
                        )),
                        new Category("Холодильники", List.of(
                                new Category("Двухкамерные", List.of()),
                                new Category("Однокамерные", List.of())
                        )),
                        new Category("Утюги", List.of())
                )),
                new Category("Растения", List.of(
                        new Category("Комнатные", List.of()),
                        new Category("Садовые", List.of())
                ))
        );

        Map<String, Integer> result = new LinkedHashMap<>();
        for (Category root : categories) {
            result.put(root.name, countSubcategories(root));
        }

        System.out.println(result);
        // {Бытовая техника=8, Растения=2}
    }

    // Рекурсивно считает количество ВСЕХ вложенных подкатегорий (без учёта самой категории)
    private static int countSubcategories(Category category) {
        int count = 0;
        for (Category child : category.children) {
            count += 1;                          // считаем самого ребёнка
            count += countSubcategories(child);   // и рекурсивно всех его потомков
        }
        return count;
    }
}