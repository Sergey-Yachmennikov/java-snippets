package remote_preparation;

import org.junit.jupiter.api.Test;

class MyLinkedListTest {

    @Test
    void all() {
        MyLinkedList<Integer> list = new MyLinkedList<>();

        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        System.out.print("Список после добавления: ");
        list.printList(); // [10 -> 20 -> 30 -> 40]

        System.out.println("Содержит 30? " + list.contains(30)); // true
        System.out.println("Содержит 100? " + list.contains(100)); // false

        boolean removed = list.remove(20);
        System.out.println("Удалили 20? " + removed); // true

        System.out.print("Список после удаления 20: ");
        list.printList(); // [10 -> 30 -> 40]

        removed = list.remove(100);
        System.out.println("Удалили 100? " + removed); // false

        System.out.println("Размер списка: " + list.size());
    }
}