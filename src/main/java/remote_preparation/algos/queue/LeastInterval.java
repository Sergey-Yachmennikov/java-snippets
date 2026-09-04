package remote_preparation.algos.queue;

import java.util.HashMap;
import java.util.Map;

public class LeastInterval {

    static int leastInterval(char[] tasks, int n) {
        if (tasks.length == 0) {
            return 0;
        }

        Map<Character, Integer> freq = new HashMap<>();
        for (char t : tasks) {
            freq.merge(t, 1, Integer::sum);
        }

        int maxFreq = 0;
        for (int f : freq.values()) {
            maxFreq = Math.max(maxFreq, f);
        }

        int maxCount = 0;
        for (int f : freq.values()) {
            if (f == maxFreq) {
                maxCount++;
            }
        }

        int skeleton = (maxFreq - 1) * (n + 1) + maxCount;
        return Math.max(tasks.length, skeleton);
    }

    public static void main(String[] args) {
        check(leastInterval("AAABBB".toCharArray(), 2), 8, "пример из условия");
        check(leastInterval(new char[]{}, 2), 0, "пустой массив задач");
        check(leastInterval("AAAAA".toCharArray(), 2), 13, "один тип задачи, много простоев");
        check(leastInterval("AAABBBCCCDDD".toCharArray(), 2), 12, "задач достаточно, простоев нет — max(len, формула)=len");
        check(leastInterval("A".toCharArray(), 2), 1, "одна задача");
        check(leastInterval("AA".toCharArray(), 0), 2, "n=0: интервал не нужен");
        check(leastInterval("AAABBBCCC".toCharArray(), 2), 9, "три типа поровну, ровно заполняют промежутки");
        check(leastInterval("ABCABC".toCharArray(), 0), 6, "n=0, разные задачи — простой порядок выполнения");

        System.out.println("Все проверки пройдены");
    }

    private static void check(int actual, int expected, String label) {
        if (actual != expected) {
            throw new AssertionError(label + ": ожидали " + expected + ", получили " + actual);
        }
        System.out.println("OK  " + label);
    }
}
