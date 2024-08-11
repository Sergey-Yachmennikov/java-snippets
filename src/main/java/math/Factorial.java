package math;

public class Factorial {

    public static int fact(int x) {
        return x == 1 ? 1 : x * fact(x - 1);
    }

    public static int factLinear(int x) {
        if (x == 1) return 1;
        int value = 1;

        for (int i = x + 1; i > 0; i--) {
            if (i == 1) break;
            value *= i - 1;
        }

        return value;
    }
}