package com.jyn.leetcode.二叉树;

import com.jyn.leetcode.base.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/*
 * https://leetcode-cn.com/problems/minimum-depth-of-binary-tree/
 */
public class 二叉树的最小深度 {

    public static void main(String[] args) {

    }

    public static int minDepth(TreeNode root) {
        if (root == null) return 0;
        Queue<TreeNode> q = new LinkedList<>();
        int res = 1;
        q.offer(root);
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                if (node.left == null && node.right == null) return res;
                if (node.left != null) q.offer(root.left);
                if (node.right != null) q.offer(root.right);
            }
            res++;
        }

        return res;
    }

    public static int minDepth2(TreeNode root) {
        if (root == null) return 0;
        int left = minDepth2(root.left) + 1;
        int right = minDepth2(root.right) + 1;
        return Math.min(left, right);
    }
}
