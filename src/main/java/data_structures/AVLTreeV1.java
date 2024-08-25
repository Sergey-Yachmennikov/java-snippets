package data_structures;

import java.util.ArrayList;
import java.util.List;

public class AVLTreeV1 {

    private Node root;

    private static class Node {
        int key;
        int height;
        Node left;
        Node right;

        public Node(int key) {
            this.key = key;
        }
    }

    void updateHeight(Node n) {
        n.height = 1 + Math.max(height(n.left), height(n.right));
    }

    int height(Node n) {
        return n == null ? -1 : n.height;
    }

    int getBalance(Node n) {
        return (n == null) ? 0 : height(n.right) - height(n.left);
    }

    Node rotateRight(Node y) {
        Node x = y.left;
        Node z = x.right;
        x.right = y;
        y.left = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node rotateLeft(Node  y) {
        Node x = y.right;
        Node z = x.left;
        x.left = y;
        y.right = z;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    Node rebalance(Node z) {
        updateHeight(z);
        int balance = getBalance(z);
        if (balance > 1) {
            if (height(z.right.right) > height(z.right.left)) {
                z = rotateLeft(z);
            } else {
                z.right = rotateRight(z.right);
                z = rotateLeft(z);
            }
        } else if (balance < -1) {
            if (height(z.left.left) > height(z.left.right))
                z = rotateRight(z);
            else {
                z.left = rotateLeft(z.left);
                z = rotateRight(z);
            }
        }
        return z;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node root, int key) {
        if (root == null) {
            return new Node(key);
        } else if (root.key > key) {
            root.left = insert(root.left, key);
        } else if (root.key < key) {
            root.right = insert(root.right, key);
        } else {
            throw new RuntimeException("duplicate Key!");
        }

        return rebalance(root);
    }

    public void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, int key) {
        if (node == null) {
            return node;
        } else if (node.key > key) {
            node.left = delete(node.left, key);
        } else if (node.key < key) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left == null) ? node.right : node.left;
            } else {
                Node mostLeftChild = mostLeftChild(node.right);
                node.key = mostLeftChild.key;
                node.right = delete(node.right, node.key);
            }
        }
        if (node != null) {
            node = rebalance(node);
        }
        return node;
    }

    Node find(int key) {
        Node current = root;
        while (current != null) {
            if (current.key == key) {
                break;
            }
            current = current.key < key ? current.right : current.left;
        }
        return current;
    }

    private Node mostLeftChild(Node node) {
        return node.left == null ? node : mostLeftChild(node.left);
    }

    public List<Integer> traverseInOrder() {
        List<Integer> result = new ArrayList<>();
        traverseInOrder(root, result);
        return result;
    }

    private void traverseInOrder(Node node, List<Integer> list) {
        if (node == null) return;
        traverseInOrder(node.left, list);
        list.add(node.key);
        traverseInOrder(node.right, list);
    }
}
