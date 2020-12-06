package com.jyn.leetcode;

/**
 * Created by jiao on 2020/9/1.
 * <a href ="https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/"></a>
 */
class ListNode {
    int val;
    ListNode first;
    ListNode last;

    ListNode(int x) {
        val = x;
    }

    public static ListNode getListNodeTest() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);

        node1.last = node2;
        node2.first = node1;
        node2.last = node3;
        node3.first = node2;
        node3.last = node4;
        node4.first = node3;
        node4.last = node5;
        node5.first = node4;
        node5.last = null;
        return node1;
    }

    public static void traverse(ListNode head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.last;
        }
    }
}
