package data_structures;


public class BinarySearchTree<K extends Comparable<K>, V> {
    private Node<K, V> root;

    private static class Node<K extends Comparable<K>, V> {
        private K key;
        private V value;
        private int N;
        private Node<K, V> left;
        private Node<K, V> right;

        public Node(K key, V value, int n) {
            this.key = key;
            this.value = value;
            N = n;
        }
    }

    public int size() {
        return size(root);
    }

    private int size(Node<K, V> node) {
        if (node == null) return 0;
        return node.N;
    }

    public V get(K key) {
        return get(root, key);
    }

    private V get(Node<K, V> node, K key) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison < 0) return get(node.left, key);
        else if (comparison > 0) return get(node.right, key);
        else return node.value;
    }

    public void put(K key, V value) {
        root = put(root, key, value);
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) return new Node<>(key, value, 1);
        int comparison = key.compareTo(node.key);
        if (comparison < 0) return put(node.left, key, value);
        else if (comparison > 0) return put(node.right, key, value);
        else node.value = value;
        node.N = size(node.left) + size(node.right) + 1;

        return node;
    }

    public K min() {
        return min(root).key;
    }

    private Node<K, V> min(Node<K, V> node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    public K floor(K key) {
        Node<K, V> node = floor(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node<K, V> floor(Node<K, V> node, K key) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison == 0) return node;
        if (comparison < 0) return floor(node.left, key);
        Node<K, V> rNode = floor(node.right, key);
        if (rNode != null) return rNode;
        return node;
    }

    public K select(int k) {
        return select(root, k).key;
    }

    private Node<K, V> select(Node<K, V> node, int k) {
        if (node == null) return null;
        int t = size(node.left);
        if (t > k) return select(node.left, k);
        else if (t < k) return select(node.right, k - t - 1);
        else return node;
    }

    public int rank(K key) {
        return rank(root, key);
    }

    private int rank(Node<K, V> node, K key) {
        if (node == null) return 0;
        int comparison = key.compareTo(node.key);
        if (comparison < 0) return rank(node.left, key);
        else if (comparison > 0) return 1 + size(node.left) + rank(node.right, key);
        else return size(node.left);
    }

    public void deleteMix() {
        root = deleteMin(root);
    }

    private Node<K, V> deleteMin(Node<K, V> node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void delete(K key) {
        root = delete(root, key);
    }

    private Node<K, V> delete(Node<K, V> node, K key) {
        if (node == null) return null;
        int comparison = key.compareTo(node.key);
        if (comparison < 0) node.left = delete(node.left, key);
        else if (comparison > 0) node.right = delete(node.right, key);
        else {
            if (node.right == null) return node.left;
            if (node.left == null) return node.right;
            Node<K, V> t = node;
            node = min(root.right);
            node.right = deleteMin(t.right);
            node.left = t.left;
        }

        node.N = size(node.left) + size(node.right) + 1;
        return node;
    }

    public void print() {
        print(root);
    }

    private void print(Node<K, V> node) {
        if (node == null) return;
        print(node.left);
        System.out.println(node.key);
        print(node.right);
    }
}
