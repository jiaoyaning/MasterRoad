package com.jyn.leetcode.二叉树

import com.jyn.leetcode.TreeNode

/**
 * 二叉树遍历框架
 * https://labuladong.gitbook.io/algo/mu-lu-ye-1/mu-lu-ye-1/er-cha-shu-xi-lie-1
 */
fun traverse(root: TreeNode) {
    // 前序遍历
    traverse(root.left)
    // 中序遍历
    traverse(root.right)
    // 后序遍历
}