package com.yunfu.leaf.algorithm.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestSum3 {

    private static Set<List<Integer>> fing_sum3(List<Integer> arr){
        Collections.sort(arr);//先对数组进行排序
        List<Integer> list3 ;
        Set<List<Integer>> setList = new HashSet<>();
        
        for(int i = 0; i < arr.size()-2; i++){
            int j = i+1;
            int k = arr.size() - 1;
            while(j < k){
                //先固定arr[i]不动，左右一定逼近;arr[j]太小，往前移动一位
                if (arr.get(i) + arr.get(j) + arr.get(k) < 0 ) {
                    j++;
                } else if(arr.get(i) + arr.get(j) + arr.get(k) > 0){
                    k--;
                }else {
                    list3 = new ArrayList<>();
                    list3.add(arr.get(i));
                    list3.add(arr.get(j));
                    list3.add(arr.get(k));
                    setList.add(list3);
                    //j++;//这个需要删掉，不然的话有些元素没有被计算
                    k--;
                }
            }
        }
        return setList;
    }
    
    
    public static void main(String[] args) {
        Integer[] list = {2,1,0,-1,1,4,2,-2,-3};
        ArrayList<Integer> arrayList = new ArrayList<>(list.length);
        Collections.addAll(arrayList, list);
        Set<List<Integer>> output = fing_sum3(arrayList);
        for(List<Integer> list2 : output){
            System.out.println(list2);
        }
    }

}