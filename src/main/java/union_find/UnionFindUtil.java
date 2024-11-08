package union_find;

import java.util.ArrayList;
import java.util.List;

public class UnionFindUtil {

    private UnionFindUtil() {}

    public static void printSets(int[] universe, DisjointSet ds) {
        for (int i: universe) System.out.print(ds.Find(i) + " ");
        System.out.println();
    }

    public static void run1() {
        // вселенная предметов
        int[] universe = { 1, 2, 3, 4, 5 };

        // инициализируем класс `DisjointSet`
        DisjointSet ds = new DisjointSet();

        // создаем одноэлементный набор для каждого элемента вселенной
        ds.makeSet(universe);
        printSets(universe, ds);

        ds.Union(4, 3);        // 4 и 3 находятся в одном наборе
        printSets(universe, ds);

        ds.Union(2, 1);        // 1 и 2 находятся в одном наборе
        printSets(universe, ds);

        ds.Union(1, 3);        // 1, 2, 3, 4 находятся в одном наборе
        printSets(universe, ds);

        boolean isTheSameSet = ds.Find(1) == ds.Find(3);

        System.out.println(isTheSameSet);
        System.out.println(ds.getParent());
    }

    public static void run2() {
        List<Integer> integers = List.of(1, 2, 3, 4, 5);
        UnionFind<Integer> uf = UnionFind.of(integers);
        uf.union(3, 1);
        uf.union(1, 0);
        uf.union(5, 4);
        uf.union(2, 0);
        uf.find(5);
        System.out.println(uf.getMap());
    }
}
