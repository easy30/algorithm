package com.cehome;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeNumSum {

    public static void main(String[] args) {
        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
//        quickSort(nums, 0, nums.length -1);
//        System.out.println(Arrays.toString(nums));
        System.out.println(JSON.toJSONString(threeSum(nums)));
    }

    public static List<int[]> threeSum(int[] nums) {
        List<int[]> res = new ArrayList<>();
        quickSort(nums, 0, nums.length -1);  //先排序
        if (nums == null || nums.length < 3) {
            return res;
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) break;
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue; // 去重复
            }
            int tag = 0 - nums[i]; //目标值
            int j = i + 1, k = nums.length - 1; //准备前后两个指针
            while (j < k) {
                if (nums[j] + nums[k] == tag) {
                    int[] re = new int[]{nums[i], nums[j], nums[k]};
                    res.add(re);
                    while (j < k && nums[j] == nums[j + 1]) {
                        j++; //重复往右侧移动
                    }
                    while (j < k && nums[k] == nums[k - 1]) {
                        k--; //重复往左侧移动
                    }
                    j++; k--;
                } else if (nums[j] + nums[k] < tag) {
                    j++; //小于目标值往右侧移动
                } else {
                    k--; //大于往左侧移动
                }
            }
        }
        return res;

    }

    /**
     * 快速排序
     * @param numbers
     * @param start
     * @param end
     */
    public static void quickSort(int[] numbers, int start, int end) {
        if (start < end) {
            int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）
            int temp; // 记录临时中间值
            int i = start, j = end;
            do {
                while ((numbers[i] < base) && (i < end))
                    i++;
                while ((numbers[j] > base) && (j > start))
                    j--;
                if (i <= j) {
                    temp = numbers[i];
                    numbers[i] = numbers[j];
                    numbers[j] = temp;
                    i++;
                    j--;
                }
            } while (i <= j);
            if (start < j)
                quickSort(numbers, start, j);
            if (end > i)
                quickSort(numbers, i, end);
        }
    }
}
