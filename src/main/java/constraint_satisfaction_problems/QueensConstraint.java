package constraint_satisfaction_problems;

import java.util.List;
import java.util.Map;

public class QueensConstraint extends Constraint<Integer, Integer> {
    private List<Integer> columns;

    public QueensConstraint(List<Integer> columns) {
        super(columns);
        this.columns = columns;
    }

    @Override
    public boolean satisfied(Map<Integer, Integer> assignment) {
        for (Map.Entry<Integer, Integer> item : assignment.entrySet()) {
            // q1c = ферзь на 1-й вертикали, q1r = ферзь на 1-й горизонтали
            int q1c = item.getKey();
            int q1r = item.getValue();
            // q2c = ферзь на 2-й вертикали
            for (int q2c = q1c + 1; q2c <= columns.size(); q2c++) {
                if (assignment.containsKey(q2c)) {
            // q2r = ферзь на 2-й горизонтали
                    int q2r = assignment.get(q2c);
            // тот же ряд?
                    if (q1r == q2r) {
                        return false;
                    }
            // одна диагональ?
                    if (Math.abs(q1r - q2r) == Math.abs(q1c - q2c)) {
                        return false;
                    }
                }
            }
        }
        return true; // нет конфликтов
    }
}