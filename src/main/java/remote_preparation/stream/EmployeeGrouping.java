package remote_preparation.stream;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

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
