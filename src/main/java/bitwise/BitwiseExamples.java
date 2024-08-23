package bitwise;

public class BitwiseExamples {

    public static void binaryInteger() {
        int i = 0b00000010;
        int j = 0b00000100;
        int p = 0b00001000;
        int p1 = 0b00001001;
        int p2 = 0b00001011;
        int p3 = 0b00001111;
        int p4 = 0b01000000;
        byte p5 = (byte) 0b10000000;
        byte p6 = (byte) 0b10000000;
        System.out.println(i);
        System.out.println(j);
        System.out.println(p);
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(Integer.toBinaryString(p4));
        System.out.println(p6);
    }
}
