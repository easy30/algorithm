import java.util.*;

/**
 * 本周题目：
 * 给定一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？找出所有满足条件且不重复的三元组。
 * <p>
 * 注意：答案中不可以包含重复的三元组。
 * <p>
 * 例如, 给定数组 nums = [-1, 0, 1, 2, -1, -4]，
 * <p>
 * 满足要求的三元组集合为：
 * [
 * [-1, 0, 1],
 * [-1, -1, 2]
 * ]
 *
 * @author haiyang.chen
 * @since 0.0.1
 */
public class ChenHaiYang {

    public static void main(String[] args) {
        int[] array = new int[]{-1, 0, 1, 3, -3, -1, -3, 3, 4};
        List<List<Integer>> equalsTargetIndies = getEqualsTargetIndies(array, 3, 0);
        print(equalsTargetIndies);
    }

    private static String getKey(List<Integer> willSortArray) {
        StringBuilder sb = new StringBuilder();
        for (Integer i : willSortArray) {
            sb.append(i.toString());
        }

        return sb.toString();
    }

    private static void print(final List<List<Integer>> list) {
        for (List<Integer> tempArray : list) {
            System.out.println(tempArray);
        }
    }

    private static List<List<Integer>> getEqualsTargetIndies(int[] array, final int length, final int target) {

        int arrayLength = array.length;

        List<List<Integer>> result = new ArrayList<List<Integer>>();

        int[] pointers = new int[length];

        for (int i = 0; i < pointers.length; i++) {
            pointers[i] = i;
        }

        int current = length - 1;
        int last = length - 1;

        do {
            if (current == last) {
                if (pointers[current] < arrayLength) {

                    final List<Integer> listIfNeed = createListIfEqualsTarget(pointers, array, target);

                    if (listIfNeed != null) {
                        result.add(listIfNeed);
                    }

                    pointers[current]++;
                } else {
                    current--;
                }
            } else {
                if (pointers[current] < arrayLength) {
                    pointers[current]++;
                    for (int i = current + 1; i < pointers.length; i++) {
                        pointers[i] = pointers[i - 1] + 1;
                    }
                    current = last;
                } else {
                    current--;
                }
            }

        } while (pointers[0] < arrayLength);

        return result;
    }

    private static List<Integer> createListIfEqualsTarget(int[] point, int[] array, final int target) {

        int sum = 0;
        for (int value : point) {
            sum += array[value];
        }

        if (target == sum) {

            final List<Integer> integers = new ArrayList<Integer>(point.length);

            for (int index : point) {
                integers.add(array[index]);
            }

            Collections.sort(integers);

            final String key = getKey(integers);

            if (!uniqueKeySet.contains(key)) {
                uniqueKeySet.add(key);
                return integers;
            }
        }

        return null;

    }

    static Set<String> uniqueKeySet = new HashSet<String>();

}
