package utils;

import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;

import static org.junit.jupiter.api.Assertions.*;

class InterviewTaskUtilTest {

    @Test
    void changeArrayListInLoop() {
        assertThrows(ConcurrentModificationException.class, InterviewTaskUtil::changeArrayListInLoop);
    }
}