package remote_preparation.algos.array;

public class MissingNumber {

    public static void main(String[] args) {
        int[] nums = {3, 0, 1};

        int result = missingNumber(nums);

        System.out.println("Пропущенное число: " + result);
    }

    public static int missingNumber(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Массив не должен быть null");
        }

        int n = nums.length;

        int expectedSum = n * (n + 1) / 2;
        int actualSum = 0;

        for (int num : nums) {
            actualSum += num;
        }

        return expectedSum - actualSum;
    }
}