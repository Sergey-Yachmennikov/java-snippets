package local_bigtech;
//StackOverflowError
// свой стек
public class Oleg {

    public static int depth;

    public static void main(String[] args) {
        recurse();
    }

    static void recurse() {
        depth++;
        recurse();
    }

    public static void main(String[] args) {
        try {
            recurse();
        } catch (StackOverflowError e) {
            System.out.println("Упали на глубине: " + depth);   // обычно около 10–20 тысяч
        }
    }
}
