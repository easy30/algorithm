package com.cehome.jishou;

public class Test {
    public static void main(String[] args) {
        String s="abcedefadkjhoobbe";
        int length=maxLength(s);
        System.out.println(length);
    }
    public static  int maxLength(String s){
        int length=0;
        StringBuilder subStr=new StringBuilder();
        for(int i=0;i<s.length();i++){
            int index=subStr.indexOf(String.valueOf(s.charAt(i)));
            subStr=subStr.append(s.charAt(i));
            if(index>-1){
                subStr.replace(0,subStr.length(),subStr.substring(index+1));
            }else{
                length = Math.max(length, subStr.length());
            }
            System.out.println(subStr.toString());
        }
        return length;
    }
}
