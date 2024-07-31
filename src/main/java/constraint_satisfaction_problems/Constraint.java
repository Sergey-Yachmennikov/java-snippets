package constraint_satisfaction_problems;

import java.util.List;
import java.util.Map;


    // V — тип переменной, D — тип домена.
public abstract class Constraint<V, D> {
    // Переменные, для которых существует ограничение
    protected List<V> variables;

    protected Constraint(List<V> variables) {
        this.variables = variables;
    }

    // Необходимо переопределить в подклассах
    public abstract boolean satisfied(Map<V, D> assignment);
}

