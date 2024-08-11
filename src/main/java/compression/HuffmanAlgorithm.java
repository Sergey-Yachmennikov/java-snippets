package compression;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class HuffmanAlgorithm {

    private static CodeTreeNode codeTree;

    private HuffmanAlgorithm() {}

    public static String compress(String text) {
        TreeMap<Character, Integer> frequencyMap = countFrequency(text);
        ArrayList<CodeTreeNode> codeTreeNodes = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            codeTreeNodes.add(new CodeTreeNode(entry.getKey(), entry.getValue()));
        }

        codeTree = huffman(codeTreeNodes);
        TreeMap<Character, String> codes = new TreeMap<>();

        for (Character c: frequencyMap.keySet()) {
            codes.put(c, codeTree.getCodeForCharacter(c, ""));
        }

        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            encoded.append(codes.get(text.charAt(i)));
        }

        return encoded.toString();
    }

    public static String uncompress(String encodedText) {
        StringBuilder decoded = new StringBuilder();
        CodeTreeNode node = codeTree;
        for (int i = 0; i < encodedText.length(); i++) {
            node = encodedText.charAt(i) == '0' ? node.left : node.right;
            if (node.content != null) {
                decoded.append(node.content);
                node = codeTree;
            }
        }

        return decoded.toString();
    }

    private static CodeTreeNode huffman(ArrayList<CodeTreeNode> codeTreeNodes) {
        while (codeTreeNodes.size() > 1) {
            Collections.sort(codeTreeNodes);
            CodeTreeNode left = codeTreeNodes.removeLast();
            CodeTreeNode right = codeTreeNodes.removeLast();
            CodeTreeNode parent = new CodeTreeNode(null, left.weight + right.weight, left, right);
            codeTreeNodes.add(parent);
        }

        return codeTreeNodes.getFirst();
    }


    private static TreeMap<Character, Integer> countFrequency(String text) {
        TreeMap<Character, Integer> frequencyMap = new TreeMap<>();

        for (int i = 0; i < text.length(); i++) {
            Character c = text.charAt(i);
            frequencyMap.compute(c, (k, count) -> count != null ? count + 1 : 1);
        }

        return frequencyMap;
    }

    private static class CodeTreeNode implements Comparable<CodeTreeNode> {
        Character content;
        int weight;
        CodeTreeNode left;
        CodeTreeNode right;

        public CodeTreeNode(Character content, int weight) {
            this.content = content;
            this.weight = weight;
        }

        public CodeTreeNode(Character content, int weight, CodeTreeNode left, CodeTreeNode right) {
            this.content = content;
            this.weight = weight;
            this.left = left;
            this.right = right;
        }

        public String getCodeForCharacter(Character ch, String parentPath) {
            if (content == ch) {
                return parentPath;
            } else {
                if (left != null) {
                    String path = left.getCodeForCharacter(ch, parentPath + 0);
                    if (path != null) {
                        return path;
                    }
                }

                if (right != null) {
                    String path = right.getCodeForCharacter(ch, parentPath + 1);
                    if (path != null) {
                        return path;
                    }
                }
            }

            return null;
        }

        @Override
        public int compareTo(CodeTreeNode o) {
            return weight - o.weight;
        }
    }
}
