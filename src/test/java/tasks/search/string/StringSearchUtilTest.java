package tasks.search.string;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class StringSearchUtilTest {

    String text = "aabaabaaaaaabaabaabaabbaaab";
    String sample = "aabaab";

    @Test
    void searchNaiveTest() {
        ArrayList<Integer> positions = StringSearchUtil.searchNaive(text, sample);
        System.out.println(positions);
    }

    @Test
    void KMPSearchTest() {
        ArrayList<Integer> positions = StringSearchUtil.KMPSearch(text, sample);
        System.out.println(positions);
    }
}