package guru;

public class A {
    private static int y;
    private final int instanceNumber;
    private int x;

    public A(int instanceNumber) {
        this.instanceNumber = instanceNumber;
    }

    @Override
    public String toString() {
        return String.valueOf(instanceNumber);
    }

    private static void doId(int z, A a) {
        z = 1;
        a.x = 12;
        a.y = 13;
        a = new A(2);
        a.x = 21;
        a.y = 22;
    }

    public static void main(String[] args) {
        A a = new A(1);
        a.x = 1;
        a.y = 2; // statis
        int z = 3;

        A.doId(z, a);

        System.out.println("a=" + a);
        System.out.println("a.instanceNumber=" + a.instanceNumber);
        System.out.println("a.x=" + a.x);
        System.out.println("a.y=" + a.y);
        System.out.println("z=" + z);
    }
}
