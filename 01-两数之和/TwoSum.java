package com.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wuzhuo
 *
 */
public class TwoSum {
	public int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map=new HashMap<Integer,Integer>();
        for(int i=0;i<nums.length;i++){
            if(map.containsKey(nums[i])){
                return new int[]{map.get(nums[i]),i};
            }else {
                map.put(target-nums[i],i);
            }
        }
        return null;
    }
}
