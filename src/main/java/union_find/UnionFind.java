package union_find;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UnionFind<T> {
    private final Map<T, T> map = new HashMap<>();

    private UnionFind(List<T> arr) {
        for (T i: arr) map.put(i, i);
    }

    public static <T> UnionFind<T> of(List<T> list) {
        return new UnionFind<>(list);
    }

    public Map<T, T> getMap() {
        return map;
    }

    public T find(T x) {
        T y = map.getOrDefault(x, x);
        if (y != x) {
            y = find(y);
            map.put(x, y);
        }

        return y;
    }

    public void union(T x, T y) {
        map.put(find(x), find(y)); // value is a root of key (set new root for value)
    }
}
