package remote_preparation.multithreating.transfer;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Контрактные тесты: один и тот же набор прогоняется по всем трём стратегиям.
 * Если реализация нарушает контракт, тест назовёт виновника.
 */
class TransferServiceTest {

    @FunctionalInterface
    interface TransferStrategy {
        boolean transfer(Account from, Account to, BigDecimal amount);
    }

    static Stream<Arguments> strategies() {
        return Stream.of(
                Arguments.of("pessimistic",
                        (TransferStrategy) PessimisticTransferService::transferMoney),
                Arguments.of("tryLock",
                        (TransferStrategy) TryLockTransferService::transferMoney),
                Arguments.of("optimistic",
                        (TransferStrategy) OptimisticTransferService::transferMoney));
    }

    private static Account account(long id, String balance) {
        return new Account(id, new BigDecimal(balance));
    }

    private static BigDecimal money(String value) {
        return new BigDecimal(value);
    }

    private static void assertBalance(String expected, Account account) {
        assertEquals(0, new BigDecimal(expected).compareTo(account.balance()),
                "ожидалось " + expected + ", получено " + account.balance());
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("успешный перевод меняет оба баланса")
    void transfersMoney(String name, TransferStrategy strategy) {
        Account from = account(1, "100.00");
        Account to = account(2, "50.00");

        assertTrue(strategy.transfer(from, to, money("30.00")));
        assertBalance("70.00", from);
        assertBalance("80.00", to);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("перевод всей суммы допустим")
    void allowsFullBalanceTransfer(String name, TransferStrategy strategy) {
        Account from = account(1, "100.00");
        Account to = account(2, "0.00");

        assertTrue(strategy.transfer(from, to, money("100.00")));
        assertBalance("0.00", from);
        assertBalance("100.00", to);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("при нехватке средств возвращает false и не меняет балансы")
    void rejectsInsufficientFunds(String name, TransferStrategy strategy) {
        Account from = account(1, "10.00");
        Account to = account(2, "50.00");

        assertFalse(strategy.transfer(from, to, money("10.01")));
        assertBalance("10.00", from);
        assertBalance("50.00", to);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("перевод самому себе отклоняется")
    void rejectsSelfTransfer(String name, TransferStrategy strategy) {
        Account account = account(1, "100.00");

        assertFalse(strategy.transfer(account, account, money("10.00")));
        assertBalance("100.00", account);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("нулевая и отрицательная сумма отвергаются")
    void rejectsNonPositiveAmount(String name, TransferStrategy strategy) {
        Account from = account(1, "100.00");
        Account to = account(2, "50.00");

        // Отрицательная сумма без этой проверки превратилась бы в списание
        // со счёта получателя в обход проверки достаточности средств
        assertThrows(IllegalArgumentException.class,
                () -> strategy.transfer(from, to, money("-10.00")));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.transfer(from, to, money("0.00")));
        assertBalance("100.00", from);
        assertBalance("50.00", to);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("null-аргументы отвергаются")
    void rejectsNulls(String name, TransferStrategy strategy) {
        Account from = account(1, "100.00");
        Account to = account(2, "50.00");

        assertThrows(NullPointerException.class, () -> strategy.transfer(null, to, money("1.00")));
        assertThrows(NullPointerException.class, () -> strategy.transfer(from, null, money("1.00")));
        assertThrows(NullPointerException.class, () -> strategy.transfer(from, to, null));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("сумма с лишними знаками после запятой отвергается")
    void rejectsExcessScale(String name, TransferStrategy strategy) {
        Account from = account(1, "100.00");
        Account to = account(2, "50.00");

        assertThrows(IllegalArgumentException.class,
                () -> strategy.transfer(from, to, money("0.001")));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("копеечные переводы не теряют точность")
    void keepsPrecisionOnSmallAmounts(String name, TransferStrategy strategy) {
        // На double здесь накопилась бы ошибка: 0.1 + 0.2 != 0.3
        Account from = account(1, "1.00");
        Account to = account(2, "0.00");

        for (int i = 0; i < 100; i++) {
            assertTrue(strategy.transfer(from, to, money("0.01")));
        }
        assertBalance("0.00", from);
        assertBalance("1.00", to);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("встречные переводы не приводят к дедлоку")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void survivesOppositeDirectionTransfers(String name, TransferStrategy strategy) throws Exception {
        // Классический сценарий дедлока: A->B и B->A одновременно.
        // Без упорядоченного захвата локов тест повиснет и упадёт по таймауту
        Account a = account(1, "1000.00");
        Account b = account(2, "1000.00");
        int iterations = 5000;

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(2);
        ExecutorService pool = Executors.newFixedThreadPool(2);

        try {
            pool.submit(() -> {
                try {
                    start.await();
                    for (int i = 0; i < iterations; i++) {
                        strategy.transfer(a, b, money("1.00"));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
            pool.submit(() -> {
                try {
                    start.await();
                    for (int i = 0; i < iterations; i++) {
                        strategy.transfer(b, a, money("1.00"));
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });

            start.countDown();
            assertTrue(done.await(25, TimeUnit.SECONDS), "похоже на дедлок");
        } finally {
            pool.shutdownNow();
        }

        assertEquals(0, money("2000.00").compareTo(a.balance().add(b.balance())));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("под нагрузкой сумма всех балансов сохраняется")
    @Timeout(value = 60, unit = TimeUnit.SECONDS)
    void preservesTotalBalanceUnderLoad(String name, TransferStrategy strategy) throws Exception {
        int accountCount = 10;
        int threads = 16;
        int transfersPerThread = 2000;

        List<Account> accounts = new ArrayList<>();
        for (int i = 0; i < accountCount; i++) {
            accounts.add(account(i, "1000.00"));
        }
        BigDecimal expectedTotal = money("10000.00");

        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        try {
            for (int t = 0; t < threads; t++) {
                pool.submit(() -> {
                    try {
                        start.await();
                        for (int i = 0; i < transfersPerThread; i++) {
                            ThreadLocalRandom rnd = ThreadLocalRandom.current();
                            Account from = accounts.get(rnd.nextInt(accountCount));
                            Account to = accounts.get(rnd.nextInt(accountCount));
                            if (from != to) {
                                strategy.transfer(from, to, money("1.00"));
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();
                    }
                });
            }
            start.countDown();
            assertTrue(done.await(55, TimeUnit.SECONDS));
        } finally {
            pool.shutdownNow();
        }

        // Инвариант проверяется ПОСЛЕ завершения всех потоков.
        // Для оптимистичной стратегии это принципиально: в середине работы
        // сумма может временно не сходиться из-за окна между списанием и зачислением
        BigDecimal total = accounts.stream()
                .map(Account::balance)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        assertEquals(0, expectedTotal.compareTo(total), "деньги потерялись или размножились: " + total);
        accounts.forEach(acc ->
                assertTrue(acc.balance().signum() >= 0, "отрицательный баланс: " + acc));
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("strategies")
    @DisplayName("баланс не уходит в минус при гонке за последними средствами")
    @Timeout(value = 30, unit = TimeUnit.SECONDS)
    void neverOverdraws(String name, TransferStrategy strategy) throws Exception {
        // 100 потоков одновременно пытаются снять по 10 при балансе 100.
        // Успешных переводов должно быть ровно 10 — ни одним больше
        Account from = account(1, "100.00");
        Account to = account(2, "0.00");
        int threads = 100;

        AtomicInteger succeeded = new AtomicInteger();
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);
        ExecutorService pool = Executors.newFixedThreadPool(threads);

        try {
            for (int i = 0; i < threads; i++) {
                pool.submit(() -> {
                    try {
                        start.await();
                        if (strategy.transfer(from, to, money("10.00"))) {
                            succeeded.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        done.countDown();
                    }
                });
            }
            start.countDown();
            assertTrue(done.await(25, TimeUnit.SECONDS));
        } finally {
            pool.shutdownNow();
        }

        assertEquals(10, succeeded.get());
        assertBalance("0.00", from);
        assertBalance("100.00", to);
    }

    @Test
    @DisplayName("два разных объекта с одним id отвергаются")
    void rejectsDuplicateIds() {
        // Иначе упорядочивание локов по id перестаёт быть строгим порядком
        Account a = account(1, "100.00");
        Account b = account(1, "100.00");

        assertThrows(IllegalStateException.class,
                () -> PessimisticTransferService.transferMoney(a, b, money("10.00")));
    }
}
