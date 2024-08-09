package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Eratosfen {

    public static List<Integer> eratosfenPrimes(int max) {
        boolean[] isPrime = new boolean[max];
        Arrays.fill(isPrime, true);
        for (int i = 2; i * i < max; i++) {
            if (isPrime[i]) {
                for (int j = 2 * i; j < max; j += i) {
                    isPrime[j] = false;
                }
            }
        }

        ArrayList<Integer> primes = new ArrayList<>();
        for (int i = 0; i < max; i++) {
            if (isPrime[i]) {
                primes.add(i);
            }
        }

        return primes;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }

        if (number % 2 == 0) {
            return number == 2;
        }

        if (number % 3 == 0) {
            return number == 3;
        }

        for (int i = 5; i * i < number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }
}
