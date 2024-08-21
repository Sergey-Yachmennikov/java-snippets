package string;

import org.junit.jupiter.api.Test;

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



}