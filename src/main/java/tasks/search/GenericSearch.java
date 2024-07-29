package tasks.search;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericSearch {

    public static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> getSuccessors) {
        // frontier — то, что нам нужно проверить
        Stack<Node<T>> frontier = new Stack<>();
        frontier.push(new Node<>(initial, null));
        // explored — то, где мы уже
        Set<T> explored = new HashSet<>();
        explored.add(initial);
        // продолжаем, пока есть что просматривать
        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.pop();
            T currentState = currentNode.state;

            // если мы нашли искомое, заканчиваем
            if (goalTest.test(currentState)) {
                return currentNode;
            }

            // проверяем, куда можно двинуться дальше и что мы еще не исследовали
            for (T child : getSuccessors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue; // пропустить состояния, которые уже исследовали
                }

                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }

        return null; // все проверили, пути к целевой точке не нашли
    }

    public static <T> List<T> nodeToPath(Node<T> node) {
        List<T> path = new ArrayList<>();
        path.add(node.state);
        // двигаемся назад, от конца к началу
        while (node.parent != null) {
            node = node.parent;
            path.addFirst(node.state); // добавить в начало
        }

        return path;
    }

    public static class Node<T> implements Comparable<Node<T>> {
        final T state;
        Node<T> parent;
        double cost;
        double heuristic;

        // для dfs и bfs мы не будем использовать свойства cost и heuristic
        Node(T state, Node<T> parent) {
            this.state = state;
            this.parent = parent;
        }

        // для astar мы будем использовать свойства cost и heuristic
        Node(T state, Node<T> parent, double cost, double heuristic) {
            this.state = state;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }

        @Override
        public int compareTo(Node<T> other) {
            Double mine = cost + heuristic;
            Double theirs = other.cost + other.heuristic;
            return mine.compareTo(theirs);
        }
    }
}
