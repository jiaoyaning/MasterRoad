package com.jyn.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 15. 三数之和
 * https://leetcode-cn.com/problems/3sum/
 */
public class 三数之和 {
    public static void main(String[] args) {
//        int[] nums = new int[]{-1, 0, 1, 2, -1, -4};
        int[] nums = new int[]{2, 13, -2, -5, -1, 10, 6, -8, 5, -5, 7, -5, -14, -4, -5, 10, -15, -2, -14, -6, 10, 6, -14, -14, -9, -11, 8, -3, -2, 12, -9, -14, 3, 5, -12, -13, -8, 1, -14, 12, 12, 0, 14, 5, 4, -14, -8, 4, -9, -7, 14, -13, 6, 7, -12, 5, 12, 11, -13, -5, 0, -6, -12, -12, 6, 13, 12, 13, 0, 5, 2, -11, 13, 1, 9, 2, 2, -14, 13, 8, -14, 4, 2, 8, -3, -3, -10, -14, -15, 14, -12, 1, -15, 14, -4, 6, 12, -6, -4, -3, 6, 5};
        System.out.println(threeSum(nums));
    }

    static List<List<Integer>> res = new ArrayList<>();
    static HashSet<List<Integer>> set = new HashSet<>();
    static List<Integer> cache = new ArrayList<>();

    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        dfs(nums, 0);
        return res;
    }

    //start为数组起始点
    public static void dfs(int[] nums, int start) {
        if (cache.size() == 3 && cache.get(0) + cache.get(1) + cache.get(2) == 0) {
            ArrayList<Integer> integers = new ArrayList<>(cache);
            if (!set.contains(integers)) {
                res.add(integers);
                set.add(integers);
            }
        }
        if (cache.size() > 3) return;
        for (int i = start; i < nums.length; i++) {
            cache.add(nums[i]);
            dfs(nums, i + 1);
            cache.remove(cache.size() - 1);
        }
    }
}
