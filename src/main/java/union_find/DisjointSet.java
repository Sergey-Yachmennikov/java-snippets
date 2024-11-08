package union_find;

import java.util.HashMap;
import java.util.Map;

public class DisjointSet {

    private final Map<Integer, Integer> parent = new HashMap<>();
    private final Map<Integer, Integer> rank = new HashMap<>();

    public Map<Integer, Integer> getParent() {
        return parent;
    }

    public void makeSet(int[] universe) {
        for (int i: universe) {
            parent.put(i, i);
            rank.put(i, 0);
        }
    }

    // Находим корень множества, которому принадлежит элемент `k`
    public int Find(int k) {
        if (parent.get(k) != k) parent.put(k, Find(parent.get(k)));
        return parent.get(k);
    }

    // Выполняем объединение двух подмножеств
    public void Union(int a, int b) {
        int x = Find(a);
        int y = Find(b);

        if (x == y) return;

        if (rank.get(x) > rank.get(y)) {
            parent.put(y, x);
        } else if (rank.get(x) < rank.get(y)) {
            parent.put(x, y);
        } else {
            parent.put(x, y);
            rank.put(y, rank.get(y) + 1);
        }
    }
}
