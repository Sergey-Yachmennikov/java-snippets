package remote_preparation.stream;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class CountSubcategoriesExample {

    public static Map<String, Integer> countSubcategories(List<Category> roots) {
        return roots.stream()
                .collect(Collectors.toMap(
                        Category::name,
                        c -> c.children().size(),
                        (a, b) -> a,          // merge на случай дублей имён
                        LinkedHashMap::new    // сохраняем порядок
                ));
    }


    public record Category(String name, List<Category> children) {

        public Category(String name) {
            this(name, List.of());
        }

        public Category(String name, List<Category> children) {
            this.name = Objects.requireNonNull(name, "name must not be null");
            this.children = children == null
                    ? List.of()
                    : List.copyOf(children);   // защитная копия + иммутабельность
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Category(String name1, List<Category> children1))) return false;
            return name.equals(name1) && children.equals(children1);
        }
    }
}
