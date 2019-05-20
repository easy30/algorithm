package com.cehome;

import java.util.Arrays;

public class LongestStringRuidong {

    public static void main(String[] args) {
        System.out.println(getLongestStringLength("pwkew"));
    }

    public static int getLongestStringLength(String str) {
        int[] m = new int[256];
        int result = 0; int left = 0;
        for (int i=0; i<str.length(); i++) {
            if (m[str.charAt(i)] > left) {
                left = m[str.charAt(i)];
            }
            int value = i - left + 1;
            result = value > result ? value : result;
            m[str.charAt(i)] = i + 1;
            System.out.println(left + "," + result + "," + value + "," + Arrays.toString(m));
        }
        return result;
    }
}
