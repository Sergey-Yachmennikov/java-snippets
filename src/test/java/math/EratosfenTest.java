package math;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EratosfenTest {

    @Test
    void eratosfenPrimesTest() {
        System.out.println(Eratosfen.eratosfenPrimes(100));
    }
    // isPrime
    @Test
    void isPrimeTest() {
        assertTrue(Eratosfen.isPrime(37));
        assertFalse(Eratosfen.isPrime(36));
    }
}