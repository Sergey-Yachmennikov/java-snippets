package data_structures;

import java.util.*;

public class BinaryTreeV1 {
    private Node root;

    private static class Node {
        int value;
        Node left;
        Node right;

        Node(int value) {
            this.value = value;
            right = null;
            left = null;
        }
    }

    public int getRootValue() {
        return root.value;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void add(int value) {
        root = addRecursive(root, value);
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            return current; // value already exists
        }

        return current;
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }

        if (value == current.value) {
            return true;
        }

        return value < current.value
                ? containsNodeRecursive(current.left, value)
                : containsNodeRecursive(current.right, value);
    }

    public int findSmallestValue() {
        return findSmallestValue(root);
    }

    private int findSmallestValue(Node node) {
        return node.left == null ? node.value : findSmallestValue(node.left);
    }

    public void delete(int value) {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, int value) {

        // no element case
        if (current == null) {
            return null;
        }

        // target element = deleting value case
        if (value == current.value) {
            //  1: no children
            if (current.left == null && current.right == null) {
                return null;
            }

            // Case 2: only 1 child
            if (current.right == null) {
                return current.left;
            }

            if (current.left == null) {
                return current.right;
            }

            // Case 3: 2 children
            int smallestValue = findSmallestValue(current.right);
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }

        // recursive case where there are some element count
        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
        } else {
            current.right = deleteRecursive(current.right, value);
        }

        return current;
    }

    // traversal part
    public List<Integer> traverseInOrder() {
        List<Integer> list = new ArrayList<>();
        traverseInOrder(root, list);
        return list;
    }

    private void traverseInOrder(Node node, List<Integer> list) {
        if (node != null) {
            traverseInOrder(node.left, list);
            list.add(node.value);
            traverseInOrder(node.right, list);
        }
    }

    public List<Integer> traversePreOrder() {
        List<Integer> list = new ArrayList<>();
        traversePreOrder(root, list);
        return list;
    }

    private void traversePreOrder(Node node, List<Integer> list) {
        if (node != null) {
            list.add(node.value);
            traverseInOrder(node.left, list);
            traverseInOrder(node.right, list);
        }
    }

    public List<Integer> traversePostOrder() {
        List<Integer> list = new ArrayList<>();
        traversePostOrder(root, list);
        return list;
    }

    private void traversePostOrder(Node node, List<Integer> list) {
        if (node != null) {
            traverseInOrder(node.left, list);
            traverseInOrder(node.right, list);
            list.add(node.value);
        }
    }

    public List<Integer> traverseInOrderReversed() {
        List<Integer> list = new ArrayList<>();
        traverseInOrderReversed(root, list);
        return list;
    }

    private void traverseInOrderReversed(Node node, List<Integer> list) {
        if (node != null) {
            traverseInOrderReversed(node.right, list);
            list.add(node.value);
            traverseInOrderReversed(node.left, list);
        }
    }

    public List<Integer> traverseLevelOrder() { // bfs
        List<Integer> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node node = nodes.remove();
            list.add(node.value);

            if (node.left != null) {
                nodes.add(node.left);
            }

            if (node.right != null) {
                nodes.add(node.right);
            }
        }

        return list;
    }

    public List<Integer> traverseInOrderIterative() {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node current = root;

        while (current != null || !stack.isEmpty()) {
            while (current != null) {
                stack.push(current);
                current = current.left;
            }

            Node top = stack.pop();
            list.add(top.value);
            current = top.right;
        }

        return list;
    }

    public List<Integer> traversePostOrderIterative() {
        List<Integer> list = new ArrayList<>();
        Stack<Node> stack = new Stack<>();
        Node prev = root;
        Node current;
        stack.push(root);

        while (!stack.isEmpty()) {
            current = stack.peek();
            boolean hasChild = (current.left != null || current.right != null);
            boolean isPrevLastChild = (prev == current.right ||
                    (prev == current.left && current.right == null));

            if (!hasChild || isPrevLastChild) {
                current = stack.pop();
                list.add(current.value);
                prev = current;
            } else {
                if (current.right != null) {
                    stack.push(current.right);
                }
                if (current.left != null) {
                    stack.push(current.left);
                }
            }
        }

        return list;
    }

    public void invertTree() {
        root = invertTree(root);
    }

    private Node invertTree(Node node) {
        if (node == null) return null;

        Node left = invertTree(node.left);
        Node right = invertTree(node.right);

        node.left = right;
        node.right = left;

        return node;
    }

    public int getDepth() {
        if (root == null) return 0;
        return getDepth(root);
    }

    private int getDepth(Node node) {
        int leftDepth = 1;
        int rightDepth = 1;

        if (node.left != null) {
            leftDepth += getDepth(node.left);
        }

        if (node.right != null) {
            rightDepth += getDepth(node.right);
        }

        return Math.max(leftDepth, rightDepth);
    }

    public boolean hasPathSum(int sum) {
        if (root == null) return false;
        return hasPathSum(root, sum);
    }

    private boolean hasPathSum(Node node, int sum) {
        if (node == null) return false;
        if (node.left == null && node.right == null && node.value == sum) return true;
        return hasPathSum(node.left, sum - node.value) || hasPathSum(node.right, sum - node.value);
    }
}
