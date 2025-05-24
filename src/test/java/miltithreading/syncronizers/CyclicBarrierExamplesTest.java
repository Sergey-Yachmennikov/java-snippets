package miltithreading.syncronizers;

import org.junit.jupiter.api.Test;


class CyclicBarrierExamplesTest {
    //    Повторное использование:
    //    CyclicBarrier — для многоразовых синхронизаций (например, этапы в итеративном алгоритме).
    //    CountDownLatch — для одноразовых событий (например, старт после инициализации).
    @Test
    void cyclicBarrierExampleTest() throws InterruptedException {
        CyclicBarrierExamples.cyclicBarrierExample();
        Thread.sleep(5000);
    }
}