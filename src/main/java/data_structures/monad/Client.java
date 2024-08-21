package data_structures.monad;


public class Client {
    String name;
    int age;
    boolean isSingle;

    public Client(String name, int age, boolean isSingle) {
        this.name = name;
        this.age = age;
        this.isSingle = isSingle;
    }

    public String getName() {
        return name;
    }
}
