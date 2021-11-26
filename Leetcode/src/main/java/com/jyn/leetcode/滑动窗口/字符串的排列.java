package com.jyn.leetcode.滑动窗口;

import java.util.HashMap;

/**
 * 567. 字符串的排列
 * https://leetcode-cn.com/problems/permutation-in-string/
 */
public class 字符串的排列 {
    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "eboaidoo";
//        String s1 = "hello";
//        String s2 = "ooolleoooleh";

        boolean inclusion = checkInclusion(s1, s2);
        System.out.println(inclusion);
    }

    @SuppressWarnings("NewApi")
    public static boolean checkInclusion(String s1, String s2) {
        HashMap<Character , Integer> window = new HashMap<>(); //滑动窗口
        HashMap<Character , Integer> need = new HashMap<>(); // 所需要的子串，字符及数量
        char[] s1arr = s1.toCharArray(); //子串
        char[] s2arr = s2.toCharArray();

        for(int i = 0 ; i< s1arr.length ; i++){ //初始化need子串所需数量
            need.put(s1arr[i] , (need.getOrDefault(s1arr[i] , 0) + 1));
        }

        int left = 0 , right = 0; //左右指针
        int size = 0; //窗口中符合字符的数量

        while(right < s2arr.length){
            char r = s2arr[right]; //右指针 字符
            right++;

            window.put(r , window.getOrDefault(r , 0) + 1);

            if(need.containsKey(r) && window.get(r).equals(need.get(r))){ //找到符合条件的一个字符
                size++;
            }

            while((right - left) == s1arr.length){ //表示当前滑动窗口长度已够，也需要移动左指针了
                char l = s2arr[left]; //需要移除的左指针字符，
                left++;

                if(size == need.size()) { //完全匹配，则返回true
                    return true;
                }

                if(need.containsKey(l) && window.get(l).equals(need.get(l))){
                    size --;
                }

                window.put(l , window.get(l) - 1);
            }
        }

        return false;
    }
}
