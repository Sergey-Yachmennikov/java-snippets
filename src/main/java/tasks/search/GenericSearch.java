package tasks.search;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class GenericSearch {

    public static <T> Node<T> dfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
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
            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue; // пропустить состояния, которые уже исследовали
                }

                explored.add(child);
                frontier.push(new Node<>(child, currentNode));
            }
        }

        return null; // все проверили, пути к целевой точке не нашли
    }

    public static <T> Node<T> bfs(T initial, Predicate<T> goalTest, Function<T, List<T>> successors) {
        // frontier — это то, что мы только собираемся проверить
        Queue<Node<T>> frontier = new LinkedList<>();
        frontier.offer(new Node<>(initial, null));
        // explored — это то, что уже проверено
        Set<T> explored = new HashSet<>();
        explored.add(initial);
        // продолжаем, пока есть что проверять
        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;
            // если мы нашли искомое, то процесс закончен
            if (goalTest.test(currentState)) {
                return currentNode;
            }
            // ищем неисследованные ячейки, в которые можно перейти
            for (T child : successors.apply(currentState)) {
                if (explored.contains(child)) {
                    continue; // пропустить состояния, которые уже исследовали
                }
                explored.add(child);
                frontier.offer(new Node<>(child, currentNode));
            }
        }
        return null; // все проверили, пути к целевой точке не нашли
    }

    public static <T> Node<T> astar(T initial, Predicate<T> goalTest, Function<T, List<T>> successors, ToDoubleFunction<T> heuristic) {
        PriorityQueue<Node<T>> frontier = new PriorityQueue<>();
        frontier.offer(new Node<>(initial, null, 0.0, heuristic.applyAsDouble(initial)));
        // explored — то, что мы уже просмотрели T = MazeLocation / cost
        Map<T, Double> explored = new HashMap<>();
        explored.put(initial, 0.0);
        // продолжаем, пока есть что просматривать
        while (!frontier.isEmpty()) {
            Node<T> currentNode = frontier.poll();
            T currentState = currentNode.state;
            // если цель найдена, мы закончили
            if (goalTest.test(currentState)) {
                return currentNode;
            }
            // проверяем, в какую из неисследованных ячеек направиться
            List<T> adjacentCells = successors.apply(currentState);
            for (T cell : adjacentCells) {
                // 1 — для сетки, для более сложных приложений
                // здесь должна быть функция затрат
                double newCost = currentNode.cost + 1;
                if (!explored.containsKey(cell) || explored.get(cell) > newCost) {
                    explored.put(cell, newCost);
                    frontier.offer(new Node<>(cell, currentNode, newCost, heuristic.applyAsDouble(cell)));
                }
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
