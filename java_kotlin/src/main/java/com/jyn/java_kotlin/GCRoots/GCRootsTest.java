package com.jyn.java_kotlin.GCRoots;

/**
 * 什么是GC root
 * https://www.cnblogs.com/wjh123/p/11141497.html
 *
 * 虚拟机栈中局部变量（也叫局部变量表）中引用的对象
 * 方法区中类的静态变量、常量引用的对象
 * 本地方法栈中 JNI (Native方法)引用的对象
 */
public class GCRootsTest {
    private byte[] byteArray = new byte[100 * 1024 * 1024];

    private static GCRootsTest gc2; //方法区中类的静态变量
    private static final GCRootsTest gc3 = new GCRootsTest(); //方法区中的常量

    public static void m1() {
        GCRootsTest gc1 = new GCRootsTest(); //虚拟机栈中的局部变量
        System.gc();
        System.out.println("第一次GC完成");
    }

    public static void main(String[] args) {
        m1();
    }
}
