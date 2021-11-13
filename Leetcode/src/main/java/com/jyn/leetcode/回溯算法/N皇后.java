package com.jyn.leetcode.回溯算法;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/n-queens/
 */
public class N皇后 {
    public static void main(String[] args) {
        System.out.println(solveNQueens(4));
    }


    public static List<List<String>> res = new ArrayList<>(); // N皇后最终结果

    public static List<List<String>> solveNQueens(int n) {
        char[][] board = new char[n][n]; //棋盘
        for (char[] line : board) {
            Arrays.fill(line, '.');
        }
        dps(board, 0);//先从第一行开始
        return res;
    }

    /**
     * @param board 表盘
     * @param n     当前是第几行
     */
    public static void dps(char[][] board, int n) {
        if (n == board.length) { //如果添加到最后一行了，则表示该表盘符合条件，可以添加进res中
            res.add(charArrayToList(board));
            return;
        }

        for (int i = 0; i < board[n].length; i++) { //对当前行进行添加
            if (isConform(board, n, i)) { //如果符合条件
                board[n][i] = 'Q';
                dps(board, n + 1);//进行下一行的排列
                board[n][i] = '.';
            }
        }
    }

    public static boolean isConform(char[][] board, int x, int y) {
        //1.当前行没有Q
        for (int i = 0; i < board[x].length; i++) {
            if (board[x][i] == 'Q') {
                return false;
            }
        }
        //2.当前列没有Q
        for (int i = 0; i < board.length; i++) {
            if (board[i][y] == 'Q') {
                return false;
            }
        }
        //3.左上角没有Q
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        //4.右上角没有Q
        for (int i = x - 1, j = y + 1; i >= 0 && j < board.length; i--, j++) {
            if (board[i][j] == 'Q') {
                return false;
            }
        }
        return true;
    }

    public static List<String> charArrayToList(char[][] target) {
        List<String> res = new ArrayList<>();
        for (char[] chars : target) {
            res.add(new String(chars));
        }
        return res;
    }
}
