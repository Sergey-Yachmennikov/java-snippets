package enums;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DayTest {

    @Test
    void getType() {
        assertEquals("dick", Day.MONDAY.getType());
    }

    @Test
    void getIndex() {
        assertEquals(1, Day.MONDAY.getIndex());
    }

    @Test
    void values() {
        assertEquals("[dick, pick]", Arrays.toString(Day.values()));
    }

    @Test
    void valueOf() {
        assertEquals(Day.MONDAY, Day.valueOf("MONDAY"));
    }
}