package math;

public class RandomGenerator {
    private final int a;
    private final int c;
    private final int m;
    private int xlast;

    public RandomGenerator(int a, int c, int m, int xlast) {
        this.a = a;
        this.c = c;
        this.m = m;
        this.xlast = xlast;
    }

    int get() {
        xlast = (a * xlast + c) % m;
        return Math.abs(xlast);
    }
}
