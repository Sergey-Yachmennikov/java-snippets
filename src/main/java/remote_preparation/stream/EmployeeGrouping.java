package remote_preparation.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

// Группировка и сортировка сотрудников по отделам
//
// **Условие:**
//
// Дан список сотрудников: `record Employee(String name, String department, int salary) {}`
//
// Реализуйте метод: `Map<String, List<Employee>> groupAndSortByDepartment(List<Employee> employees)`
//
// Метод должен вернуть карту:
//
//        - ключ — название отдела (department)
//        - значение — отсортированный список сотрудников по убыванию зарплаты
//        - отделы без сотрудников должны быть исключены
// **Требования:**
//
//        - Использовать Java 8+ Stream API
//        - Не модифицировать исходный список сотрудников
//        - Сортировка внутри каждого отдела — по убыванию salary
//        - Результат — Map<String, List<Employee>>
//        - Решение должно быть лаконичным и читаемым
public class EmployeeGrouping {

    public static void main(String[] args) {

        List<Employee> employees = List.of(
                new Employee("Alice", "IT", 100000),
                new Employee("Bob", "HR", 80000),
                new Employee("Charlie", "IT", 120000),
                new Employee("Diana", "Finance", 90000),
                new Employee("Eve", "HR", 85000)
        );

        Map<String, List<Employee>> result = groupEmployee(employees);
        System.out.println(result);
    }

    public static Map<String, List<Employee>> groupEmployee(List<Employee> employees) {
        return employees.stream()
                .sorted(Comparator.comparingInt(Employee::salary).reversed())
                .collect(Collectors.groupingBy(Employee::department));
    }

    public record Employee(String name, String department, int salary) {}
}
