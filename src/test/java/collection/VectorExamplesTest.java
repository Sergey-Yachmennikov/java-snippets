package collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class VectorExamplesTest {

    @Test
    void create() {
        VectorExamples.create();
    }

//    Принцип PECS в Java — это правило, которое помогает правильно использовать
//    дженерики с wildcards (? extends, ? super).
//    PECS расшифровывается как:
//    Producer Extends, Consumer Super
//    Если объект производит данные → используем extends
//    Если объект потребляет данные → используем super

    @Test
    void PECSPrincipleTest() {
        List<? extends Number> numbers = List.of(1, 2.0, 3L);
        // numbers.add(2); // compilation error
        Number number = numbers.get(0); // but can get elements from collection
        // ? extends Number говорит: "Я читаю объекты как Number, но не знаю точный подтип (Integer, Double и т.д.)".

        List<? super Number> list = new ArrayList<>();
        list.add(1);     // ✅ можно добавить любой подтип Number
        list.add(42);
        list.add(22.34141);
        list.add(22.2f);

        int value = (int) list.getFirst(); // но доставать только через кастинг
    }
}