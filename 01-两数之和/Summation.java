package com.cehome;

import java.util.HashMap;
import java.util.Map;

public class Summation {

    public static void main(String[] args) {
        System.out.println(findTowNum(new int[]{2, 7, 11, 15}, 9));
    }

    public static String findTowNum(int[] num, int target) {
        String result = "";
        Map<Integer, Integer> map = new HashMap<>();
        if (num.length > 1 && target > 0) {
            for (int i=0; i< num.length; i++) {
                if (num[i] <= target) {
                    Integer two = target - num[i];
                    Integer j = map.get(two);
                    if (j != null) {
                        result = j + "," + i;
                    }
                    map.put(num[i], i);
                }
            }
        }
        return result;
    }
}
