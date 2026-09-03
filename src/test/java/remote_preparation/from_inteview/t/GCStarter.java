package remote_preparation.from_inteview.t;


// как будет работать GC с этим классом
public class GCStarter {

    public static void main(String[] args) {
        A objA = new A();
        B objB = new B();

        objA.b = objB;
        objB.a = objA;

        objA = null;
        objB = null;

        System.gc();
    }

    static class A {
        B b;
    }

    static class B {
        A a;
    }
}
