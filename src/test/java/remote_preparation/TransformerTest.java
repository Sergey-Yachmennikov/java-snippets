package remote_preparation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransformerTest {

    @Test
    void andThen() {
        Transformer<String, Integer> step1 = String::length;
        Transformer<Integer, String> step2 = i -> "Число: " + i;
        Transformer<String, Boolean> step3 = s -> s.length() > 5;

        // Строим цепочку из 3 трансформеров
        Transformer<String, Boolean> chain = step1
                .andThen(step2)  // step1 + step2 -> новый трансформер
                .andThen(step3); // новый трансформер + step3 -> еще один трансформер

        // Применяем всю цепочку
        Boolean result = chain.apply("Hello World");
        assertTrue(result);
    }
}