package com.cehome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Summation {

    public static void main(String[] args) {
        System.out.println(Arrays.toString(findTowNum(new int[]{2, 7, 11, 15}, 9)));
    }

    public static int[] findTowNum(int[] num, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        if (num.length > 1 && target > 0) {
            for (int i=0; i< num.length; i++) {
                if (num[i] <= target) {
                    Integer two = target - num[i];
                    Integer j = map.get(two);
                    if (j != null) {
                        result[0] = j;
                        result[1] = i;
                    }
                    map.put(num[i], i);
                }
            }
        }
        return result;
    }
}
