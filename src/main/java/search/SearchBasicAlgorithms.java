package search;

public class SearchBasicAlgorithms {

    private SearchBasicAlgorithms() {}

    public static int binarySearchRecursive(int[] array, int startIndex, int endIndex, int elementToFind) {
        if (endIndex >= startIndex) {
            int middleIndex = startIndex + (endIndex - startIndex) / 2;

            if (array[middleIndex] == elementToFind) {
                return middleIndex; // base case
            }

            return array[middleIndex] > elementToFind
                ? binarySearchRecursive(array, startIndex, middleIndex - 1, elementToFind)
                : binarySearchRecursive(array, middleIndex + 1, endIndex, elementToFind);
        }

        return  -1;
    }

    public static int binarySearch(int[] array, int elementToFind) {
        int startIndex = 0;
        int endIndex = array.length - 1;
        int middleIndex;

        while (startIndex <= endIndex) {
            middleIndex = startIndex + (endIndex - startIndex) / 2;

            if (array[middleIndex] == elementToFind) {
                return middleIndex;
            }

            if (array[middleIndex] > elementToFind) {
                endIndex = middleIndex - 1;
            } else {
                startIndex = middleIndex + 1;
            }
        }

        return  -1;
    }
}
