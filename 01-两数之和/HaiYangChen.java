import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author haiyang.chen
 */
public class Question20190507 {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 4};
        int target = 6;
        System.out.println(Arrays.toString(getTwoIndexForEqualsTarget(nums, target)));
    }

    private static int[] getTwoIndexForEqualsTarget(int[] array, int target) {
        if (array == null || array.length == 0) {
            return null;
        }

        Map<Integer, Integer> arrayMap = new HashMap<>(array.length);
        for (int i = 0; i < array.length; i++) {


            int value = target - array[i];
            if (arrayMap.containsKey(value)) {
                return new int[]{i, arrayMap.get(value)};
            }

            arrayMap.put(array[i], i);
        }

        return null;
    }
}