package algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * coolma 2019/5/15
 * 本周题目
 * 无重复字符的最长子串
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 示例 1:
 * <p>
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * <p>
 * 示例 2:
 * <p>
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * <p>
 * 示例 3:
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 */
public class MaxNotRepeatSub {

    //pwkeZwycndyu
    public static int run(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = 0;
        int max = 0;
        int i = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        while (i < s.length()) {
            char c = s.charAt(i);
            Integer index = map.get(c);
            if (index == null) {
                n++;
                map.put(c, i);
                i++;

            } else {
                if (n > max) max = n;

                //n = 0;
                map.clear();
                int j = index + 1;
                while(j<=i){
                    map.put(s.charAt(j),j);
                    j++;
                }
                n=i-index;
                i=j;

            }


        }

        if (n > max) max = n;
        return max;

    }
    //pwkeZwycndyu
    public static int run1(String s) {
        if (s == null || s.length() == 0) return 0;
        int n = 0;
        int max = 0;
        int i = 0;
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        while (i < s.length()) {
            char c = s.charAt(i);
            Integer index = map.get(c);
            if (index == null) {
                n++;
                map.put(c, i);
                i++;

            } else {
                if (n > max) max = n;
                n = 0;
                map.clear();
                i = index + 1;
            }


        }

        if (n > max) max = n;
        return max;

    }



    public static void main(String[] args) {
        System.out.println(run("pwkeZwycndyu"));
    }
}
