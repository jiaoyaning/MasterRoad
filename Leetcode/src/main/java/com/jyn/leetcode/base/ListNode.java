package com.jyn.leetcode.base;

/*
 * https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
 */
public class ListNode {
    int val;
    public ListNode first;
    public ListNode next;

    ListNode(int x) {
        val = x;
    }

    public static ListNode getListNodeTest() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);

        node1.next = node2;
        node2.first = node1;
        node2.next = node3;
        node3.first = node2;
        node3.next = node4;
        node4.first = node3;
        node4.next = node5;
        node5.first = node4;
        node5.next = node6;
        node6.first = node5;
        node6.next = null;
        return node1;
    }

    public static void print(ListNode head) {
        while (head != null) {
            System.out.print(head.val+" -> ");
            head = head.next;
        }
        System.out.println("null");
    }
}
