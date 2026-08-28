package remote_preparation.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PersonGrouper {

    public static void main(String[] args) {
        List<Person> people = List.of(
                new Person("Alice", 25),
                new Person("Bob", 30),
                new Person("Charlie", 25),
                new Person("David", 30),
                new Person("Eve", 35)
        );

        Map<Integer, List<String>> listMap = groupNamesByAge(people);
        System.out.println(listMap);
    }

    public static Map<Integer, List<String>> groupNamesByAge(List<Person> people) {
        return people.stream()
                .sorted(Comparator.comparingInt(Person::age))
                .collect(Collectors.groupingBy(
                        Person::age,
                        TreeMap::new,
                        Collectors.mapping(Person::name, Collectors.toList())
                ));
    }

    public static record Person(String name, int age) {}
}
