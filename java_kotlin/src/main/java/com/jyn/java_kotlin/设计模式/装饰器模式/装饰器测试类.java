package com.jyn.java_kotlin.设计模式.装饰器模式;

/**
 * Created by jiaoyaning on 2021/8/14.
 */
public class 装饰器测试类 {
    public static void main(String[] args) {
        奶茶 奶茶 = new 奶茶();
        椰果奶茶 椰果布丁奶茶 = new 椰果奶茶(new 布丁奶茶(奶茶));
        System.out.println("名字：" + 椰果布丁奶茶.getName() + "; 价格:" + 椰果布丁奶茶.getPrice());
    }
}
