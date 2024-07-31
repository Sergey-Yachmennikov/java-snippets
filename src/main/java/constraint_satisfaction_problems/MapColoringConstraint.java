package constraint_satisfaction_problems;


import java.util.List;
import java.util.Map;

public final class MapColoringConstraint extends Constraint<String, String> {
    private String place1, place2;

    public MapColoringConstraint(String place1, String place2) {
        super(List.of(place1, place2));
        this.place1 = place1;
        this.place2 = place2;
    }

    @Override
    public boolean satisfied(Map<String, String> assignment) {
    // если какой-либо регион place отсутствует в присваивании,
    // то его цвета не могут привести к конфликту
        if (!assignment.containsKey(place1) ||
                !assignment.containsKey(place2)) {
            return true;
        }
    // проверяем, не совпадает ли цвет, присвоенный
    // place1, с цветом, присвоенным place2
        return !assignment.get(place1).equals(assignment.get(place2));
    }
}
