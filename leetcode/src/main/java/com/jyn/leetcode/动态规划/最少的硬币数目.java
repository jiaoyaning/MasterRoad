package com.jyn.leetcode.动态规划;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * https://leetcode-cn.com/problems/gaM7Ch/
 */
public class 最少的硬币数目 {
    public static void main(String[] args) {
        int[] coins = new int[]{1, 2, 5};
        int amount = 11;
        System.out.println(coinChange2(coins, amount));
    }

    static HashMap<Integer, Integer> mema = new HashMap<>();

    public static int coinChange(int[] coins, int amount) {
        if (coins.length == 0 || amount == 0) return 0;
        return dp(coins, amount);
    }

    public static int dp(int[] coins, int amount) {
        if (mema.containsKey(amount)) return mema.get(amount);
        if (amount == 0) return 0;
        if (amount < 0) return -1;
        int res = Integer.MAX_VALUE;
        for (int coin : coins) {
            int t = dp(coins, amount - coin);
            if (t != -1) {
                res = Math.min(res, t + 1);
            }
        }
        int i = res == Integer.MAX_VALUE ? -1 : res;
        mema.put(amount, i);
        return i;
    }

    public static int coinChange2(int[] coins, int amount) {
        int[] tables = new int[amount + 1];
        Arrays.fill(tables, amount + 1); 
        tables[0] = 0;
        for (int i = 0; i < tables.length; i++) {
            for (int coin : coins) {
                if (i - coin < 0) continue;
                tables[i] = Math.min(tables[i], tables[i - coin] + 1);
            }
        }

        return tables[amount] == amount + 1 ? -1 : tables[amount];
    }
}
