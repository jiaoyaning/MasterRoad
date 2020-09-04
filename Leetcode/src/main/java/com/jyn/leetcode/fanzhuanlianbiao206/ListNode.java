package com.jyn.leetcode.fanzhuanlianbiao206;

/**
 * Created by jiao on 2020/9/1.
 * <a href ="https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/"></a>
 */
class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }

    public static ListNode getListNodeTest() {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        node1.next = node2;
        node2.next = node3;
        node3.next = node4;
        node4.next = node5;
        node5.next = null;
        return node1;
    }

    public static void traverse(ListNode head){
        while (head!=null){
            System.out.println(head.val);
            head = head.next;
        }
    }
}
