package com.jyn.leetcode.stack_queue;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/*
 * 栈栈
 * https://mp.weixin.qq.com/s/fc48Z5tSMlBHweYIS1UL0g
 *
 * 队列
 * https://mp.weixin.qq.com/s/TCg9_3cVuDfZLqK2eYrc7w
 */
public class StackQueueTest {
    // stack empty   push  pop
    // queue isEmpty offer poll

    public static void main(String[] args) {
        //栈，先进后出
        Stack<String> stack = new Stack<>();
        stack.push("1");
        stack.push("2");
        stack.push("3");
        stack.push("4");
        while (!stack.empty()) {
            String pop = stack.pop();
            System.out.println(pop);
        }

        Queue<String> queue = new LinkedList<>();
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");
        queue.offer("4");

        while (!queue.isEmpty()) {
            String poll = queue.poll();
            System.out.println(poll);
        }
    }
}
