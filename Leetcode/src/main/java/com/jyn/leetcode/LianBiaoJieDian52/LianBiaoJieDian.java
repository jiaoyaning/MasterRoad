package com.jyn.leetcode.LianBiaoJieDian52;

/**
 * Created by jiao on 2020/9/16.
 * 剑指 Offer 52. 两个链表的第一个公共节点
 * https://leetcode-cn.com/problems/liang-ge-lian-biao-de-di-yi-ge-gong-gong-jie-dian-lcof/
 */
public class LianBiaoJieDian {
    /**
     * 先计算两个链表各自的长度 m和n
     * 把长的缩减到短的长度
     * 然后把两个长度相同的链表逐个比较
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if(headA == null || headB == null){
            return null;
        }

        int lengthA = 1, lengthB = 1;
        ListNode tempA = headA, tempB = headB;
        while (tempA.next != null) {
            lengthA++;
            tempA = tempA.next;
        }
        while (tempB.next != null) {
            lengthB++;
            tempB = tempB.next;
        }


        if (lengthA > lengthB) {
            for (int i = 0; i < lengthA - lengthB; i++) {
                headA = headA.next;
            }
        } else if (lengthA < lengthB){
            for (int i = 0; i < lengthB - lengthA; i++) {
                headB = headB.next;
            }
        }

        while (headA.next != null) {
            if (headA.equals(headB)) {
                return headA;
            } else {
                headA = headA.next;
                headB = headB.next;
            }
        }

        return null;
    }
}
