package com.cehome.jishou;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {

        int[] arr={-2,-1,0,1,2,3,4};
        List list=treeSum(arr);
        System.out.println(list);
        //[[-2, -1, 3], [-2, 0, 2], [-1, 0, 1]]
    }

    public static List treeSum(int[] arr){
        List result=new ArrayList();

        for(int i=0;i<arr.length;i++){
            for(int j=i+1;j<arr.length;j++){
                for(int k=j+1;k<arr.length;k++){
                    if(arr[i]+arr[j]+arr[k]==0){
                        List tmp=new ArrayList();
                        tmp.add(arr[i]);
                        tmp.add(arr[j]);
                        tmp.add(arr[k]);
                        result.add(tmp);
                    }
                }
            }
        }
        return result;
    }
}
