package arrays;


public class ArraysTasks {

    private ArraysTasks() {}

    /**
     * @topic Bitwise
     */
    public static int findUniqueItem(int[] array) {
        int rest = array[0];
        for (int i = 1; i < array.length; i++) rest ^= array[i];

        return rest;
    }

    /**
     * @topic Two pointers
     */
    public static int containerWithMostWater(int[] lines) {
        int result = Integer.MIN_VALUE;
        int leftLine = 0;
        int rightLine = lines.length - 1;

        while (leftLine < rightLine) {
            int width = rightLine - leftLine;
            int height = Math.min(lines[leftLine], lines[rightLine]);
            int area = width * height;
            result = Math.max(result, area);

            if (lines[leftLine] > lines[rightLine]) rightLine--;
            else leftLine++;
        }

        return result;
    }
}
