package algorithm;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * coolma 2019/5/21
 */
public class FindThreeNum {

    public static List<int[]> run(int[] a,int sum){
        List<int[]> list=new ArrayList<int[]>();
        for(int i=0;i<a.length-2;i++) {
            for (int j = i+1; j < a.length - 1; j++) {
                for (int k = j+1; k < a.length; k++) {
                    if (a[i] + a[j] + a[k] == sum) {
                        //System.out.println(i+","+j+","+k);
                        int[] b= {a[i],a[j],a[k]};
                        Arrays.sort(b);
                        if(!exists(list,b)) {
                            list.add(b);
                        }

                    }

                }
            }
        }
        return list;
    }
    private static boolean exists( List<int[]> list,int[] b){

        for(int[] c:list){
            if(Arrays.equals(b,c)) return true;
        }
        return false;
    }


    public static void main(String[] args) {
        int[] nums={-1, 0, 1, 2, -1, -4};
        List<int[]> list=run(nums,0);
        System.out.println(JSON.toJSONString(list));


    }
}
