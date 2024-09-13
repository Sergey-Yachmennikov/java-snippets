package data_structures.trie;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    char value;
    List<TrieNode> children;

    public TrieNode() {
        this.value = ' ';
    }

    private TrieNode(char value) {
        this.value = value;
    }

    public void insert(String data) {
        if (data.length() == 0) return;
        if (children == null) children = new ArrayList<>();

        char c = data.charAt(0);
        TrieNode child = findNodeByChar(c);

        if (child == null) {
            child = new TrieNode(c);
            children.add(child);
        }

        child.insert(data.substring(1));
    }

    private TrieNode findNodeByChar(char c) {
        if (children != null) {
            for (TrieNode node : children) {
                if (node.value == c) return node;
            }
        }

        return null;
    }

    public boolean containsString(String str) {
        TrieNode current = this;
        for (int i = 0; i < str.length(); i++) {
            current = current.findNodeByChar(str.charAt(i));
            if (current == null) return false;
        }

        return true;
    }

    public List<String> extractData() {
        List<String> result = new ArrayList<>();
        getAllNumbers("", result);
        return result;
    }

    private void getAllNumbers(String path, List<String> result) {
        if (value != ' ') path = path + value;
        if (children != null) for (TrieNode child: children) child.getAllNumbers(path, result);
        else result.add(path);
    }

    public void writeToFile(PrintWriter writer) {
        writer.write(value);
        if (children != null) for (TrieNode child : children) child.writeToFile(writer);
        writer.write(']');
    }

    public void readFromFile(FileReader reader) throws IOException {
        char ch;
        while ((ch = (char) reader.read()) != ']') {
            TrieNode node = new TrieNode(ch);
            node.readFromFile(reader);
            if (children == null) children = new ArrayList<>();
            children.add(node);
        }
    }

    public static void writeToFile(String pathToSave, TrieNode root) {
        try (PrintWriter out = new PrintWriter(pathToSave)) {
            root.writeToFile(out);
            out.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static TrieNode readFromFile(String path) {
        TrieNode root = new TrieNode();
        try (FileReader reader = new FileReader(path)) {
            reader.read(); // skip root node value
            root.readFromFile(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return root;
    }

    @Override
    public String toString() {
        return "TrieNode {" +
                " value = " + value + "\n" +
                "  children = " + children +
                '}' + "\n";
    }
}
