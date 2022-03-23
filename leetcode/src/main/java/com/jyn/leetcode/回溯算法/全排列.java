package com.jyn.leetcode.回溯算法;

import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutations/
 */
public class 全排列 {
    static List<List<Integer>> res = new LinkedList<>();
    static LinkedList<Integer> track = new LinkedList<>();

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        char[] res = new char[0];
        System.out.println(permute(nums));
    }

    public static List<List<Integer>> permute(int[] nums) {
        dfs(nums);
        return res;
    }

    public static void dfs(int[] nums) {
        if (track.size() == nums.length) {
            res.add(new LinkedList<>(track));
            return;
        }

        for (int num : nums) {
            if (track.contains(num)) continue;
            track.addLast(num);
            dfs(nums);
            track.removeLast();
        }
    }
}
