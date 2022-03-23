package com.jyn.leetcode;

/**
 * 二分查找
 * https://leetcode-cn.com/problems/binary-search/
 */
public class 二分查找 {
    public static void main(String[] args) {
        int[] arr = {-1, 0, 3, 5, 9, 12};
        int target = 2;

        int search = binarySearch(arr, target, 0, arr.length - 1);
        System.out.println("结果：" + search);
    }

    public static int binarySearch(int[] arr, int m, int start, int end) {
        int num = -1;
        if (arr == null || arr.length == 0) return -1;
        if (start == end ) return num;
        if (arr[start] == m) return start;
        if (arr[end] == m) return end;

        int center = (start + end) / 2;
        //左半部分
        int left = binarySearch(arr, m, start, center);
        if (left != -1) num = left;

        //右半部分
        int right = binarySearch(arr, m, center + 1, end);
        if (right != -1) num = right;

        return num;
    }
}
