package com.jyn.leetcode.fanzhuanlianbiao206;

import com.jyn.leetcode.ListNode;

/**
 * Created by jiao on 2020/9/1.
 * 反转链表
 */
public class FanZhuanLianBiao {
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

    public static void main(String[] args) {
        ListNode.traverse(ListNode.getListNodeTest());
        ListNode listNode = reverseList(ListNode.getListNodeTest());
        ListNode.traverse(listNode);
    }
}
