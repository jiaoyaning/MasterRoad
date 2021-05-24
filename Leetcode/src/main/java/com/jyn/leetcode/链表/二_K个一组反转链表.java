package com.jyn.leetcode.链表;

import com.jyn.leetcode.ListNode;

/**
 * K个一组反转链表
 * https://labuladong.gitbook.io/algo/mu-lu-ye-1/mu-lu-ye/k-ge-yi-zu-fan-zhuan-lian-biao
 */
public class 二_K个一组反转链表 {
    public static void main(String[] args) {
        ListNode listNode = ListNode.getListNodeTest();
        ListNode.print(reverseKGroup(listNode, 4));
    }

    /*
     * 先实现一个反转某一段的链表，由反转链表修改而得
     */
    public static ListNode reverseSegment(ListNode head, ListNode end) {
        if (head.next == end)
            return head; //此处返回的新的链表头
        ListNode last = reverseSegment(head.next, end);
        head.next.next = head;
        head.next = null;
        return last;
    }

    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) { //因为这次返回的是结尾，结尾是需要为null的
            return null;
        }
        ListNode right = head; //需要反转的右节点
        for (int i = 0; i < k; i++) { //判断是否够数，不够直接返回head
            if (right == null) return head;
            right = right.next;
        }
        ListNode left = head; //记录开始的位置，被反转后就变成了末尾，再由其指向下一个反转列表
        ListNode newHead = reverseSegment(left, right); //反转后，返回新的头结点
        left.next = reverseKGroup(right, k);//上一段反转成功后，其末尾指向下一段需要反转的链表
        return newHead;
    }
}
