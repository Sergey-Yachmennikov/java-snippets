package data_structures;

public class BinaryHeapUtil {

    public static void displayTree(int []heap, int size) {
        int nBlanks = 32;
        int itemsPerRow = 1;
        int column = 0;
        int j = 0; // current item
        String delimeter = "--------------------------------------------------------------------------";
        System.out.println(delimeter);
        while (size > 0) {
            if (column == 0)
                for (int k = 0; k < nBlanks; k++)
                    System.out.print(' ');
            System.out.print(heap[j]);
            if (++j == size) // done?
                break;
            if (++column == itemsPerRow) {
                nBlanks /= 2;
                itemsPerRow *= 2;
                column = 0;
                System.out.println();
            } else
                for (int k = 0; k < nBlanks * 2 - 2; k++)
                    System.out.print(' ');
        }
        System.out.println("\n" + delimeter);
    }
}
