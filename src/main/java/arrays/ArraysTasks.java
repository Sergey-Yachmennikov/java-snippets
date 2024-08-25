package arrays;


public class ArraysTasks {

    private ArraysTasks() {}

    public static int findUniqueItem(int[] array) {
        int rest = array[0];

        for (int i = 1; i < array.length; i++) {
            rest ^= array[i];
        }

        return rest;
    }
}
