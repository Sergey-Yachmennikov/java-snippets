package constraint_satisfaction_problems;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CSPTest {

    @Test
    void CSPTestStart() {
        List<String> variables = List.of("Western Australia", "Northern Territory", "South Australia", "Queensland",
                "New South Wales", "Victoria", "Tasmania");

        Map<String, List<String>> domains = new HashMap<>();

        for (String variable : variables) {
            domains.put(variable, List.of("red", "green", "blue"));
        }

        CSP<String, String> csp = new CSP<>(variables, domains);
        csp.addConstraint(new MapColoringConstraint("Western Australia", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Western Australia", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("South Australia", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Queensland", "Northern Territory"));
        csp.addConstraint(new MapColoringConstraint("Queensland", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("Queensland", "New South Wales"));
        csp.addConstraint(new MapColoringConstraint("New South Wales", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("Victoria", "South Australia"));
        csp.addConstraint(new MapColoringConstraint("Victoria", "New South Wales"));
        csp.addConstraint(new MapColoringConstraint("Victoria", "Tasmania"));

        Map<String, String> solution = csp.backtrackingSearch();
        if (solution == null) {
            System.out.println("No solution found!");
        } else {
            System.out.println(solution);
        }

        assert solution != null;
        assertEquals(
                "{Western Australia=red, New South Wales=green, Victoria=red, Tasmania=green, Northern Territory=green, South Australia=blue, Queensland=red}",
                solution.toString()
        );
    }
}