package com.jyn.leetcode.翻转链表;

import com.jyn.leetcode.ListNode;

/**
 * Created by jiao on 2020/9/1.
 * 反转链表
 */
public class 翻转链表 {
    public static void main(String[] args) {
        ListNode.print(ListNode.getListNodeTest());
        ListNode listNode = reverseList(ListNode.getListNodeTest());
        ListNode.print(listNode);
    }

    //region 左右指针法
    public static ListNode reverseList(ListNode head) {
        ListNode left = null;
        ListNode right = head;
        while (right != null) {
            ListNode tmp = right.next;
            right.next = left;
            left = right;
            right = tmp;
        }

        return left;
    }
    //endregion

    //region 一、递归反转整个链表
    /*
     * 原始链表： 1 -> 2 -> 3 -> null
     *
     * 第一遍
     * ①: last = 3 ; head = 2
     *
     * ②: 1 -> 2 <-> 3
     *
     * ③: 1 -> 2 <- 3
     *          ↓
     *         null
     *
     * 第二遍
     * ①: last = 3 ; head = 1
     *
     * ②: 1 <-> 2 <- 3
     *
     * ③: 1 <- 2 <- 3
     *     ↓
     *    null
     */
    public static ListNode reverseList2(ListNode head) {
        if (head.next == null)
            return head; //递归的终点
        ListNode last = reverseList2(head.next); //① 递归的终点会一直传递到第一个递归点，并没有被改变
        head.next.next = head;  //②
        head.next = null;       //③
        return last;
    }

    //endregion

    //region 二、反转链表前 N 个节点
    /*
     * 原始链表： 1 -> 2 -> 3 -> 4 -> 5 -> null
     * 基本同上，只需要记录第N+1个节点。
     * 把上一个指向null的操作变成指向第N+1个节点。
     */
    public static ListNode successor = null;

    public static ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            successor = head.next;
            return head;
        }
        ListNode last = reverseN(head.next, n - 1);
        head.next.next = head;
        head.next = successor;
        return last;
    }

    //endregion
}
