package yandex;

import java.util.HashMap;
import java.util.Map;

/**
 * Банкомат.
 * Инициализируется набором купюр и умеет выдавать купюры для заданной суммы, либо отвечать отказом.
 * При выдаче купюры списываются с баланса банкомата.
 * Допустимые номиналы: 50₽, 100₽, 500₽, 1000₽, 5000₽.
 *
 * Другие валюты и номиналы должны легко добавляться разработчиками в будущем.
 * Многопоточные сценарии могут быть добавлены позже (например резервирование).
 */
class ATM implements ATMI {

    private final Map<Integer, Integer> moneyByNominal = new HashMap<>();

    private final int[] nominals;

    public ATM(int[] nominals, int capacity) {
        this.nominals = nominals;

        for (int nominal : nominals) {
            moneyByNominal.put(nominal, capacity);
        }
    }

    public Map<Integer, Integer> getMoneyByNominal() {
        return moneyByNominal;
    }

    @Override
    public Map<Integer, Integer> withdraw(int sum) {

        if (sum < nominals[nominals.length - 1]) {
            System.out.println("Сумма слишком мала");
            return new HashMap<>();
        }

        Map<Integer, Integer> combination = calculateCombination(sum);
        dispenseMoney(combination);

        return combination;
    }

    private Map<Integer, Integer> calculateCombination(int sum) {
        Map<Integer, Integer> combination = new HashMap<>();

        for (int nominal : nominals) {
            int availableNotesCount = moneyByNominal.get(nominal);
            if (availableNotesCount == 0) continue;

            int neededCount = sum / nominal;
            int actualNotesCount = Math.min(availableNotesCount, neededCount);

            if (actualNotesCount > 0) {
                combination.put(nominal, actualNotesCount);
                sum -= actualNotesCount * nominal;
                moneyByNominal.put(nominal, availableNotesCount - actualNotesCount);
            }
        }

        return combination;
    }

    private void dispenseMoney(Map<Integer, Integer> combination) {
        System.out.println("Начинаем снятие");
        System.out.println(combination);
        System.out.println("Снятие денег окончено");
    }
}