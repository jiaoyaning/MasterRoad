package com.jyn.leetcode;

import java.util.HashMap;

/**
 * Created by jiaoyaning on 2020/9/25.
 */
public class DuoShuArr {
    public static void main(String[] args) {

    }

    /**
     * 用hashmap存下每个字符出现的次数，边存边查。
     */
    public static int DuoShuShuZhu(int[] arr) {
        HashMap<Integer, Integer> time = new HashMap<>();

        for (int i = 0; i < arr.length; i++) {
            if (time.containsKey(arr[i])) { //如果出现过则+1
                int t = time.get(arr[i]);
                t++;
                time.put(arr[i], t);
                if (t > arr.length / 2) { //如果已满足条件直接返回
                    return t;
                }
            } else { //没出现过则put进hashmap
                time.put(arr[i], 1);
            }
        }

        return -1;
    }

    /**
     * 去除特殊字符并转换小写，转成char数组后头尾逐步往中间比较。
     */
    public static boolean isHuiWen(String test) {
        String filter = test
                .replaceAll("[^a-zA-Z0-9]", "") //去除非字母或者数字字符
                .toLowerCase();//大写转小写
        char[] array = filter.toCharArray();

        for (int i = 0; i < array.length / 2; i++) { //头尾判断
            if (array[i] != array[array.length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 异或是相同为0，不同为1。
     * 所以两个相同的数字异或肯定会等于0，如此可以求出没有重复的那一个数字
     */
    public static int onlyOne(int[] arr) {
        int start = 0;
        for (int i = 0; i < arr.length; i++) {
            start = start ^ arr[i];
        }
        return start;
    }
}
