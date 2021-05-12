package com.jyn.leetcode.反转链表;

import com.jyn.leetcode.ListNode;

/**
 * 反转链表
 * https://labuladong.gitbook.io/algo/mu-lu-ye-1/mu-lu-ye/di-gui-fan-zhuan-lian-biao-de-yi-bu-fen
 */
public class 反转链表 {
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
     * 第一遍
     * ①: last = 3 ; head = 2
     * ②: 1 -> 2 <-> 3
     * ③: 1 -> 2 <- 3
     *          ↓
     *         null
     * 第二遍
     * ①: last = 3 ; head = 1
     * ②: 1 <-> 2 <- 3
     * ③: 1 <- 2 <- 3
     *     ↓
     *    null
     */
    public static ListNode reverseList2(ListNode head) {
        if (head.next == null)
            return head; //终点head
        ListNode last = reverseList2(head.next); //① 终点head会一直传递到第一个递归点，所以返回值一直都是终点head
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

    //region 三、反转链表的一部分
    public static ListNode reverseBetween(ListNode head, int m, int n) {
        if (n == 1) { //N 等于 1 的时候，相当于反转前 n 个元素
            return reverseN(head, n);
        }
        head.next = reverseBetween(head, m - 1, n - 1); //找出需要反转的位置，并把箭头指向它的反转结果
        return head; //始终是返回head
    }
    //endregion
}
