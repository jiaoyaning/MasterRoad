package com.jyn.leetcode;

/**
 * Created by jiaoyaning on 2021/5/24.
 */
public class TreeNode {
    int val;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int val) {
        this.val = val;
    }

    public TreeNode getTreeNode() {
        TreeNode treeNode1 = new TreeNode(0);
        TreeNode treeNode2_1 = new TreeNode(1);
        TreeNode treeNode2_2 = new TreeNode(2);
        treeNode1.left = treeNode2_1;
        TreeNode treeNode3_1 = new TreeNode(4);
        TreeNode treeNode3_2 = new TreeNode(5);
        treeNode2_1.left = treeNode3_1;
        treeNode2_1.right = treeNode3_2;
        treeNode1.right = treeNode2_2;
        TreeNode treeNode3_4 = new TreeNode(6);
        TreeNode treeNode3_5 = new TreeNode(7);
        treeNode2_2.left = treeNode3_4;
        treeNode2_2.right = treeNode3_5;
        return treeNode1;
    }
}
