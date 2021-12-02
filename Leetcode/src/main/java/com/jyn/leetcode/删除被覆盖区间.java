package com.jyn.leetcode;

import java.util.Arrays;

/**
 * 1288. 删除被覆盖区间
 * https://leetcode-cn.com/problems/remove-covered-intervals/
 */
public class 删除被覆盖区间 {
    public static void main(String[] args) {
//        int[][] intervals = {{34335, 39239}, {15875, 91969}, {29673, 66453}, {53548, 69161}, {40618, 93111}};
        int[][] intervals = {{1, 4}, {3, 6}, {2, 8}};
        System.out.println(removeCoveredIntervals2(intervals));
    }

    public static int removeCoveredIntervals(int[][] intervals) {
        int n = intervals.length;
        int m = n;

        for (int i = 0; i < n; i++) {
            int[] target = intervals[i];

            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                int[] now = intervals[j];

                if (target[0] >= now[0] && target[1] <= now[1]) {
                    m--;
                    break;
                }
            }
        }

        return m;
    }

    public static int removeCoveredIntervals2(int[][] intervals) {
        int res = intervals.length;
        //排序法，左边递增，右边递减，这样长的可以覆盖短的。
        Arrays.sort(intervals, (a, b) ->
                a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]
        );
        System.out.println(Arrays.deepToString(intervals));
        return res;
    }
}
