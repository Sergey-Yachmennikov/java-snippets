package remote_preparation.stream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CountSubcategoriesExampleTest {

    @Test
    void countSubcategories() {
        List<CountSubcategoriesExample.Category> roots = List.of(
                new CountSubcategoriesExample.Category("Бытовая техника", List.of(
                        new CountSubcategoriesExample.Category("Телевизоры", List.of(
                                new CountSubcategoriesExample.Category("ЭЛТ"),
                                new CountSubcategoriesExample.Category("LED"),
                                new CountSubcategoriesExample.Category("OLED")
                        )),
                        new CountSubcategoriesExample.Category("Холодильники", List.of(
                                new CountSubcategoriesExample.Category("Двухкамерные"),
                                new CountSubcategoriesExample.Category("Однокамерные")
                        )),
                        new CountSubcategoriesExample.Category("Утюги")
                )),
                new CountSubcategoriesExample.Category("Растения", List.of(
                        new CountSubcategoriesExample.Category("Комнатные"),
                        new CountSubcategoriesExample.Category("Садовые")
                ))
        );

        Map<String, Integer> result = CountSubcategoriesExample.countSubcategories(roots);
        System.out.println(result);

        assertEquals(Map.of("Бытовая техника", 3, "Растения", 2), result);
    }
}