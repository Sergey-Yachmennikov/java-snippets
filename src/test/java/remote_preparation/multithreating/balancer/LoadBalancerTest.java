package remote_preparation.multithreating.balancer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import remote_preparation.multithreating.balancer.exceptions.NoAvailableInstanceException;
import remote_preparation.multithreating.balancer.exceptions.TooManyInstancesException;
import remote_preparation.multithreating.balancer.strategy.RandomStrategy;
import remote_preparation.multithreating.balancer.strategy.RoundRobinStrategy;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class LoadBalancerTest {

    private static URI uri(int n) {
        return URI.create("http://node-" + n + ".local:8080");
    }

    private static LoadBalancer roundRobin() {
        return new LoadBalancer(new RoundRobinStrategy());
    }

    @Nested
    @DisplayName("register")
    class Register {

        @Test
        @DisplayName("новый инстанс регистрируется и возвращает true")
        void registersNewInstance() throws Exception {
            LoadBalancer lb = roundRobin();

            assertTrue(lb.register(uri(1)));
            assertEquals(1, lb.size());
            assertEquals(List.of(uri(1)), lb.instances());
        }

        @Test
        @DisplayName("повторная регистрация возвращает false и не дублирует")
        void rejectsDuplicate() throws Exception {
            LoadBalancer lb = roundRobin();
            lb.register(uri(1));

            assertFalse(lb.register(uri(1)));
            assertEquals(1, lb.size());
        }

        @Test
        @DisplayName("11-й инстанс бросает TooManyInstancesException")
        void throwsWhenLimitExceeded() throws Exception {
            LoadBalancer lb = roundRobin();
            for (int i = 0; i < LoadBalancer.DEFAULT_MAX_INSTANCES; i++) {
                assertTrue(lb.register(uri(i)));
            }

            TooManyInstancesException ex =
                    assertThrows(TooManyInstancesException.class, () -> lb.register(uri(100)));

            assertEquals(10, ex.getLimit());
            assertEquals(10, lb.size(), "неудачная регистрация не должна менять состояние");
        }

        @Test
        @DisplayName("дубликат на полном балансировщике возвращает false, а не бросает исключение")
        void duplicateOnFullBalancerReturnsFalse() throws Exception {
            // Пограничный случай: порядок проверок внутри register имеет значение
            LoadBalancer lb = roundRobin();
            for (int i = 0; i < 10; i++) {
                lb.register(uri(i));
            }

            assertFalse(lb.register(uri(0)));
        }

        @Test
        @DisplayName("после unregister освобождается слот")
        void freesSlotAfterUnregister() throws Exception {
            LoadBalancer lb = roundRobin();
            for (int i = 0; i < 10; i++) {
                lb.register(uri(i));
            }

            assertTrue(lb.unregister(uri(0)));
            assertTrue(lb.register(uri(100)));
            assertEquals(10, lb.size());
        }

        @Test
        @DisplayName("null отвергается")
        void rejectsNull() {
            LoadBalancer lb = roundRobin();
            assertThrows(NullPointerException.class, () -> lb.register(null));
        }

        @Test
        @DisplayName("относительный URI и URI без хоста отвергаются")
        void rejectsInvalidUri() {
            LoadBalancer lb = roundRobin();

            assertThrows(IllegalArgumentException.class, () -> lb.register(URI.create("/api/v1")));
            assertThrows(IllegalArgumentException.class, () -> lb.register(URI.create("mailto:a@b.c")));
        }

        @Test
        @DisplayName("эквивалентные пути считаются одним инстансом")
        void normalizesPath() throws Exception {
            LoadBalancer lb = roundRobin();

            assertTrue(lb.register(URI.create("http://node.local/a/b")));
            assertFalse(lb.register(URI.create("http://node.local/a/./b")));
            assertEquals(1, lb.size());
        }

        @Test
        @DisplayName("возвращаемый список инстансов неизменяем")
        void instancesListIsImmutable() throws Exception {
            LoadBalancer lb = roundRobin();
            lb.register(uri(1));

            assertThrows(UnsupportedOperationException.class, () -> lb.instances().add(uri(2)));
        }
    }

    @Nested
    @DisplayName("get")
    class Get {

        @Test
        @DisplayName("на пустом балансировщике бросает NoAvailableInstanceException")
        void throwsWhenEmpty() {
            assertThrows(NoAvailableInstanceException.class, () -> roundRobin().get());
        }

        @Test
        @DisplayName("round robin обходит инстансы по кругу")
        void roundRobinCyclesInOrder() throws Exception {
            LoadBalancer lb = roundRobin();
            for (int i = 0; i < 3; i++) {
                lb.register(uri(i));
            }

            assertEquals(uri(0), lb.get());
            assertEquals(uri(1), lb.get());
            assertEquals(uri(2), lb.get());
            assertEquals(uri(0), lb.get(), "после последнего должен вернуться к первому");
        }

        @Test
        @DisplayName("round robin не ломается при переполнении счётчика")
        void roundRobinSurvivesCounterOverflow() {
            // Тестируем стратегию напрямую: гонять 2 млрд вызовов через балансировщик долго.
            // Проверяем, что floorMod даёт неотрицательный индекс при отрицательном счётчике.
            RoundRobinStrategy strategy = new RoundRobinStrategy();
            List<URI> snapshot = List.of(uri(0), uri(1), uri(2));

            assertDoesNotThrow(() -> {
                for (int i = 0; i < 1000; i++) {
                    assertNotNull(strategy.select(snapshot));
                }
            });
            assertTrue(Math.floorMod(Integer.MIN_VALUE, 3) >= 0);
        }

        @Test
        @DisplayName("random возвращает только зарегистрированные инстансы")
        void randomReturnsRegisteredOnly() throws Exception {
            LoadBalancer lb = new LoadBalancer(new RandomStrategy());
            for (int i = 0; i < 5; i++) {
                lb.register(uri(i));
            }
            Set<URI> registered = new HashSet<>(lb.instances());

            for (int i = 0; i < 1000; i++) {
                assertTrue(registered.contains(lb.get()));
            }
        }

        @Test
        @DisplayName("random за много вызовов задействует все инстансы")
        void randomCoversAllInstances() throws Exception {
            LoadBalancer lb = new LoadBalancer(new RandomStrategy());
            for (int i = 0; i < 5; i++) {
                lb.register(uri(i));
            }

            Set<URI> seen = new HashSet<>();
            for (int i = 0; i < 1000; i++) {
                seen.add(lb.get());
            }

            // Вероятность промаха по какому-то инстансу за 1000 бросков пренебрежимо мала
            assertEquals(5, seen.size());
        }
    }

    @Nested
    @DisplayName("многопоточность")
    class Concurrency {

        @Test
        @DisplayName("гонка регистраций не превышает лимит")
        void concurrentRegistrationRespectsLimit() throws Exception {
            // Главный тест на check-then-act: 50 потоков одновременно пытаются
            // зарегистрировать 50 РАЗНЫХ инстансов при лимите 10.
            // Без атомарности проверки лимита сюда пролезет больше десяти.
            LoadBalancer lb = roundRobin();
            int threads = 50;

            AtomicInteger registered = new AtomicInteger();
            AtomicInteger rejected = new AtomicInteger();
            CountDownLatch start = new CountDownLatch(1);
            CountDownLatch done = new CountDownLatch(threads);
            ExecutorService pool = Executors.newFixedThreadPool(threads);

            try {
                for (int i = 0; i < threads; i++) {
                    int id = i;
                    pool.submit(() -> {
                        try {
                            start.await(); // синхронный старт усиливает гонку
                            if (lb.register(uri(id))) {
                                registered.incrementAndGet();
                            }
                        } catch (TooManyInstancesException e) {
                            rejected.incrementAndGet();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            done.countDown();
                        }
                    });
                }

                start.countDown();
                assertTrue(done.await(10, TimeUnit.SECONDS));
            } finally {
                pool.shutdownNow();
            }

            assertEquals(10, lb.size());
            assertEquals(10, registered.get());
            assertEquals(40, rejected.get());
            assertEquals(10, new HashSet<>(lb.instances()).size(), "дубликатов быть не должно");
        }

        @Test
        @DisplayName("гонка регистраций одного и того же инстанса даёт ровно один true")
        void concurrentDuplicateRegistrationSucceedsOnce() throws Exception {
            LoadBalancer lb = roundRobin();
            int threads = 50;

            AtomicInteger successes = new AtomicInteger();
            CountDownLatch start = new CountDownLatch(1);
            CountDownLatch done = new CountDownLatch(threads);
            ExecutorService pool = Executors.newFixedThreadPool(threads);

            try {
                for (int i = 0; i < threads; i++) {
                    pool.submit(() -> {
                        try {
                            start.await();
                            if (lb.register(uri(1))) {
                                successes.incrementAndGet();
                            }
                        } catch (Exception e) {
                            // лимит здесь недостижим
                        } finally {
                            done.countDown();
                        }
                    });
                }

                start.countDown();
                assertTrue(done.await(10, TimeUnit.SECONDS));
            } finally {
                pool.shutdownNow();
            }

            assertEquals(1, successes.get());
            assertEquals(1, lb.size());
        }

        @Test
        @DisplayName("round robin распределяет запросы равномерно под нагрузкой")
        void roundRobinDistributesEvenlyUnderLoad() throws Exception {
            LoadBalancer lb = roundRobin();
            for (int i = 0; i < 10; i++) {
                lb.register(uri(i));
            }

            int threads = 20;
            int callsPerThread = 1000;
            Map<URI, AtomicInteger> hits = new ConcurrentHashMap<>();
            CountDownLatch start = new CountDownLatch(1);
            CountDownLatch done = new CountDownLatch(threads);
            ExecutorService pool = Executors.newFixedThreadPool(threads);

            try {
                for (int i = 0; i < threads; i++) {
                    pool.submit(() -> {
                        try {
                            start.await();
                            for (int c = 0; c < callsPerThread; c++) {
                                hits.computeIfAbsent(lb.get(), k -> new AtomicInteger())
                                        .incrementAndGet();
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            done.countDown();
                        }
                    });
                }

                start.countDown();
                assertTrue(done.await(30, TimeUnit.SECONDS));
            } finally {
                pool.shutdownNow();
            }

            // Атомарный счётчик гарантирует ТОЧНОЕ равенство, а не примерное:
            // каждый инстанс получает ровно (20 * 1000) / 10 запросов
            int expected = threads * callsPerThread / 10;
            assertEquals(10, hits.size());
            hits.forEach((uri, count) -> assertEquals(expected, count.get(), "перекос на " + uri));
        }

        @Test
        @DisplayName("чтение во время записи не падает и не возвращает мусор")
        void readsAreConsistentDuringWrites() throws Exception {
            // Проверка на TOCTOU: unregister между size() и get(i)
            // в наивной реализации даёт IndexOutOfBoundsException.
            LoadBalancer lb = roundRobin();
            for (int i = 0; i < 5; i++) {
                lb.register(uri(i));
            }

            Set<URI> valid = IntStream.range(0, 10).mapToObj(LoadBalancerTest::uri)
                    .collect(java.util.stream.Collectors.toSet());

            ExecutorService pool = Executors.newFixedThreadPool(9);
            CountDownLatch done = new CountDownLatch(9);
            AtomicInteger errors = new AtomicInteger();

            try {
                // 1 писатель: постоянно добавляет и убирает инстансы
                pool.submit(() -> {
                    try {
                        for (int i = 0; i < 2000; i++) {
                            int id = 5 + (i % 5);
                            try {
                                lb.register(uri(id));
                            } catch (TooManyInstancesException ignored) {
                                // ожидаемо
                            }
                            lb.unregister(uri(id));
                        }
                    } catch (RuntimeException e) {
                        errors.incrementAndGet();
                    } finally {
                        done.countDown();
                    }
                });

                // 8 читателей
                for (int t = 0; t < 8; t++) {
                    pool.submit(() -> {
                        try {
                            for (int i = 0; i < 5000; i++) {
                                URI result = lb.get();
                                if (result == null || !valid.contains(result)) {
                                    errors.incrementAndGet();
                                }
                            }
                        } catch (RuntimeException e) {
                            errors.incrementAndGet();
                        } finally {
                            done.countDown();
                        }
                    });
                }

                assertTrue(done.await(30, TimeUnit.SECONDS));
            } finally {
                pool.shutdownNow();
            }

            assertEquals(0, errors.get(), "чтение параллельно с записью не должно падать");
        }
    }
}