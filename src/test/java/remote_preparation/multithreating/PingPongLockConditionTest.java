package remote_preparation.multithreating;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PingPongLockConditionTest {

    @Test
    void alternatesStartingWithPing() throws InterruptedException {
        List<String> result = new PingPongLockCondition(3).start();

        assertEquals(List.of("ping", "pong", "ping", "pong", "ping", "pong"), result);
    }

    @RepeatedTest(50)
    void alwaysAlternatesRegardlessOfScheduling() throws InterruptedException {
        List<String> result = new PingPongLockCondition(5).start();

        assertEquals(10, result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(i % 2 == 0 ? "ping" : "pong", result.get(i),
                    "нарушен порядок на позиции " + i);
        }
    }

    @Test
    void completesWithoutDeadlock() {
        assertTimeoutPreemptively(Duration.ofSeconds(5),
                () -> new PingPongLockCondition(10_000).start());
    }
}