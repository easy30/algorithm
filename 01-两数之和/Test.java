package study.googleAuthenticator.controller;

import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        int arr[]={2,5,100,32,30,11,23};
        int[] result= getTargetSubscript(arr,34);
        if(result!=null && result.length!=0){
            for(int i=0;i<result.length;i++){
                System.out.print(result[i]+"  ");
            }
        }
    }
    public static int[]  getTargetSubscript(int[] arr, int target) {
        Map<Integer,Integer> map=new HashMap();
        for(int i=0;i<arr.length;i++){
            int tmp=target-arr[i];
            if(map.containsKey(tmp)){
                System.out.println("目标下标："+map.get(tmp)+ "     "+i);
                return new int[]{map.get(tmp),i};
            }else {
                map.put(arr[i],i);
            }
        }
        return null;
    }
}
