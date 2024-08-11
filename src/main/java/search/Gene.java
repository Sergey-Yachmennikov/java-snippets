package search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Gene {

    private final ArrayList<Codon> codons = new ArrayList<>();

    public Gene(String geneStr) {
        for (int i = 0; i < geneStr.length() - 3; i += 3) {
            codons.add(new Codon(geneStr.substring(i, i + 3)));
        }
    }

    public boolean linearContains(Codon key) {
        for (Codon codon : codons) {
            if (codon.compareTo(key) == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean binaryContains(Codon key) {
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        Collections.sort(sortedCodons);
        int low = 0;
        int high = sortedCodons.size() - 1;
        while (low <= high) { // while there is still a search space
            int middle = (low + high) / 2;
            int comparison = codons.get(middle).compareTo(key);
            if (comparison < 0) { // средний кодон меньше искомого
                low = middle + 1;
            } else if (comparison > 0) { // средний кодон больше искомого
                high = middle - 1;
            } else { // средний кодон равен искомому
                return true;
            }
        }
        return false;
    }

    public enum Nucleotide {
        A, C, G, T
    }

    public static class Codon implements Comparable<Codon> {

        public final Nucleotide first, second, third;
        private final Comparator<Codon> comparator =
                Comparator.comparing((Codon c) -> c.first)
                        .thenComparing((Codon c) -> c.second)
                        .thenComparing((Codon c) -> c.third);

        public Codon(String codonStr) {
            first = Nucleotide.valueOf(codonStr.substring(0, 1));
            second = Nucleotide.valueOf(codonStr.substring(1, 2));
            third = Nucleotide.valueOf(codonStr.substring(2, 3));
        }

        @Override
        public int compareTo(Codon another) {
            return comparator.compare(this, another);
        }
    }
}