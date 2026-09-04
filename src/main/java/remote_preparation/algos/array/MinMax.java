package remote_preparation.algos.array;


// Максимум и минимум в срезе
//
// Напиши функцию `MinMax(nums []int) (min, max int)`, которая за один проход
// возвращает минимальный и максимальный элементы среза.
// Обработай случай пустого среза — верни ошибку.
//
public class MinMax {

    public static void main(String[] args) {
        int[] nums = {5, 2, 9, 1, 7};

        try {
            MinMaxResult result = minMax(nums);

            System.out.println("Минимум: " + result.min);
            System.out.println("Максимум: " + result.max);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static MinMaxResult minMax(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }

        int min = nums[0];
        int max = nums[0];

        for (int i = 1; i < nums.length; i++) {
            min = Math.min(min, nums[i]);
            max = Math.max(max, nums[i]);
        }

        return new MinMaxResult(min, max);
    }

    static class MinMaxResult {
        private final int min;
        private final int max;

        public MinMaxResult(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }
}
