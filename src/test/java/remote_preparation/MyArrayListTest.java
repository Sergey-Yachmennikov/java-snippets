package remote_preparation;

import org.junit.jupiter.api.Test;


class MyArrayListTest {

    @Test
    void all() {
        MyArrayList<Integer> list = new MyArrayList<>();

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        System.out.println("Список: " + list);          // [10, 20, 30, 40]
        System.out.println("Размер: " + list.size());    // 4
        System.out.println("Элемент по индексу 2: " + list.get(2)); // 30
        System.out.println("Содержит 20? " + list.contains(20));    // true
        System.out.println("Содержит 99? " + list.contains(99));    // false

        list.remove(3);
        System.out.println("После удаления индекса 1: " + list);
    }

}