package string;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}