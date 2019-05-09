package com.yunfu.leaf.algorithm.common;


import java.util.HashMap;

public class TestArrayAddEquals {

	//总时间复杂度： n+1+n*log2^n 约等于 n*log2^n
	public static void main(String[] args) {
		int[] array = {0,6,8,2,5,11,3};     
		System.out.println("["+printIndex(array,19)+"]");
	}
	
	private static String printIndex(int[] array,int sum) {
		
		HashMap map = new HashMap();
		for (int i = 0; i < array.length; i++) {
			map.put(array[i], i); // 将值和下标存入Map
		}
		
		quickSort(array,0,array.length-1);  //复杂度为 n*log2^n
		int i =0;
		int j= array.length-1;
		boolean flag = false;
		while(i<j) {
			if(array[i]+array[j]<sum) {
				i++;
			}
			if(array[i]+array[j]>sum) {
				j--;
			}
			if(array[i]+array[j]==sum) {
				flag = true;
				break;
			}
		}
		
		if(flag) {
			return map.get(array[i])+","+map.get(array[j]);
		}else{
			return "-1,-1";
		}
	}
	
	//{6,8,2,5,11,3}
	private static void quickSort(int[] data, int left, int right) {
		if(left>=right)
			return;
		int point = data[left];
		int i = left+1;
		int j = right;
		while(true){
			while(i<right && data[i]<point){
				i++;
			}
			while(j>left && data[j]>point){
				j--;
			}
			if(i<j){
				swap(data,i,j);
			}else{
				break;
			}
		}
	
		swap(data,left,j);
		quickSort(data,left,j-1);
		quickSort(data,j+1,right);
	}
 
    private static void swap(int[] data,int i,int j){
        if(i==j){
            return;
        }
        int tmp = 0;
        tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
    
}
