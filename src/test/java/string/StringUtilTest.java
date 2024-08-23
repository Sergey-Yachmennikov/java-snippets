package string;

import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest {

    @Test
    void compareToTest() {
        String author = "author";
        String book = "book";
        String duplicateBook = "book";

        assertThat(author.compareTo(book))
                .isEqualTo(-1);
        assertThat(book.compareTo(author))
                .isEqualTo(1);
        assertThat(duplicateBook.compareTo(book))
                .isEqualTo(0);
    }

    @Test
    void lowerCaseTest() {
        String upperString = "DDD is cool";
        assertEquals("ddd is cool", StringUtil.toLowerCase(upperString));
    }


    @Test
    void charsTest() {
        String temp = "dickness";
        IntStream chars = temp.chars();

        String collected = chars
                .mapToObj(v -> String.valueOf((char) (v - 32)))
                .collect(Collectors.joining());

        assertEquals("DICKNESS", collected);

    }

    @Test
    void messageFormat() {
        assertEquals("At 12:00:00 AM on Jan 1, 1901, there was a disturbance in the Force on planet 7.", StringUtil.messageFormat());
        assertEquals("The disk \"MyDisk\" contains 1,273 file(s).", StringUtil.messageFormat2());
    }
}