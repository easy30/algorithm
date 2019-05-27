package com.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeSum {
	public List<List<Integer>> threeSum(int[] nums) {
		List<List<Integer>> list = new ArrayList<List<Integer>>();
		if(nums != null && nums.length>2){
			Arrays.sort(nums);
			for(int i=0;i<nums.length-2;i++){
				for(int j=i+1;j<nums.length-1;j++){
					for(int a=j+1;a<nums.length;a++){
						if(nums[i]+nums[j]+nums[a] == 0){
							List<Integer> mapList = new ArrayList<Integer>();
			        			mapList.add(nums[i]);
			        			mapList.add(nums[j]);
			        			mapList.add(nums[a]);
			        			if(!list.contains(mapList)){
			        				list.add(mapList);
			        			}
			        			break;
						}
					}
				}
			}
//	            System.out.print(nums[i]+"  ");
//			Map<Integer,Integer> map = new HashMap<Integer,Integer>();
//			map.put(nums[0], nums[0]);
//	        for(int i=1;i<nums.length-1;i++){
//	        		int num = nums[i]+nums[i+1];
//	        		if(map.containsKey(0-num)){
//	        			List<Integer> mapList = new ArrayList<Integer>();
//	        			mapList.add(map.get(0-num));
//	        			mapList.add(nums[i]);
//	        			mapList.add(nums[i+1]);
//	        			list.add(mapList);
//	        		}else{
//	        			map.put(nums[i], nums[i]);
//	        		}
//	        }
		}
		return list;
    }
	
	public static void main(String[] args) {
		ThreeSum threeSum = new ThreeSum();
		int[] nums = {-1, 0, 1, 2, -1, -4};
		List<List<Integer>> list = threeSum.threeSum(nums);
		for(List<Integer> maps:list){
			for(Integer num:maps){
				System.out.print(num+" ");
			}
			System.out.println("");
		}
	}
}
