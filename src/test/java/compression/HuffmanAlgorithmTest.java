package compression;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HuffmanAlgorithmTest {
    private final static String text = "where there's a will there's a way";

    @Test
    void huffmanAlgorithmTest() {
        String compresed = HuffmanAlgorithm.compress(text);
        String decompressed = HuffmanAlgorithm.uncompress(compresed);
        System.out.println(compresed);
        System.out.println(decompressed);
        assertEquals(text, decompressed);
    }
}