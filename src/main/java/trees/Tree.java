package trees;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree {
    int value;
    Tree left;
    Tree right;

    public Tree(int value, Tree left, Tree right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Tree(int value) {
        this.value = value;
    }

    public int sumRecursive() {
        int sum = value;

        if (left != null) {
            sum += left.sumRecursive();
        }

        if (right != null) {
            sum += right.sumRecursive();
        }

        return sum;
    }

    public int sumDeep() {
        Stack<Tree> stack = new Stack<>();
        stack.push(this);
        int sum = 0;

        while (!stack.isEmpty()) {
            Tree node = stack.pop();
            sum += node.value;

            if (node.right != null) {
                stack.push(node.right);
            }

            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return sum;
    }

    public int sumWide() {
        LinkedList<Tree> linkedList = new LinkedList<>();
        linkedList.offer(this);
        int sum = 0;

        while (!linkedList.isEmpty()) {
            Tree node = linkedList.poll();
            sum += node.value;

            if (node.left != null) {
                linkedList.offer(node.left);
            }

            if (node.right != null) {
                linkedList.offer(node.right);
            }
        }

        return sum;
    }
}
