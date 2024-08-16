package bitwise;


public final class IsEven {

    private IsEven() {}
    public static boolean isEven(int number) {
        return (number & 1) == 0;
    }
}
