package yandex;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

class ATMTest {
// 50₽, 100₽, 500₽, 1000₽, 5000₽.
    @Test
    void withdraw_baseCase() {
        final int[] nominals = new int[] {5000, 1000, 500, 100, 50};
        ATM atm = new ATM(nominals, 50);
        Map<Integer, Integer> withdraw = atm.withdraw(5100);
        Map<Integer, Integer> moneyByNominal = atm.getMoneyByNominal();

        assertThat(withdraw.get(100)).isEqualTo(1);
        assertThat(withdraw.get(5000)).isEqualTo(1);

        assertThat(moneyByNominal.get(100)).isEqualTo(49);
        assertThat(moneyByNominal.get(5000)).isEqualTo(49);
    }

    @Test
    void withdraw_tooLessMoney() {
        final int[] nominals = new int[] {5000, 1000, 500, 100, 50};
        ATM atm = new ATM(nominals, 50);
        Map<Integer, Integer> withdraw = atm.withdraw(5049);
        Map<Integer, Integer> moneyByNominal = atm.getMoneyByNominal();

        //assertThat(withdraw.size()).isEqualTo(0);
        // Переделать под цикл для каждого номинала
        assertThat(moneyByNominal.get(5000)).isEqualTo(49);
    }

    @Test
    void test1111() {
        Function convertor = null;
        List<Object> list = List.of().stream().map(item -> convertor.apply(item)).toList();
        System.out.println(list);
    }
}