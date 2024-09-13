package data_structures.trie;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TrieNodeTest {

    @Test
    void trieTest() throws IOException {
        String pathToFile = "/Users/sergejacmennikov/Desktop/projects/java-snippets/src/test/java/data_structures/trie/numbers.txt";
        String pathToSave = "/Users/sergejacmennikov/Desktop/projects/java-snippets/src/test/java/data_structures/trie/tree.dat";
        List<String> lines = Files.readAllLines(Paths.get(pathToFile));

        TrieNode root = new TrieNode();
        for (String line : lines) root.insert(line);

        assertTrue(root.containsString("18АА0597"));
        assertFalse(root.containsString("18АА0596"));

        List<String> extracted = root.extractData();
        assertArrayEquals(lines.toArray(), extracted.toArray());

        // save tree in file
        TrieNode.writeToFile(pathToSave, root);

        // build tree from file
        TrieNode rootNodeFromFile = TrieNode.readFromFile(pathToSave);
        System.out.println(rootNodeFromFile.children);
    }
}