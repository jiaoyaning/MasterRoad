package com.jyn.java_kotlin.GCRoots;

/**
 * 什么是GC root
 * https://www.cnblogs.com/wjh123/p/11141497.html
 *
 * 如果判断是否需要被回收？
 * 1、引用计数法
 * 2、枚举根节点做可达性分析
 *
 *
 * 常见的GC Root有如下：
 * 1、通过System Class Loader或者Boot Class Loader加载的class对象，通过自定义类加载器加载的class不一定是GC Root
 * 2、处于激活状态的线程
 * 3、栈中的对象
 * 4、JNI栈中的对象
 * 5、JNI中的全局对象
 * 6、正在被用于同步的各种锁对象
 * 7、JVM自身持有的对象，比如系统类加载器等。
 * https://www.jianshu.com/p/dcfe84c50811
 *
 * 虚拟机栈中局部变量（也叫局部变量表）中引用的对象
 * 方法区中类的静态变量、常量引用的对象
 * 本地方法栈中 JNI (Native方法)引用的对象
 *
 * 使用JProfiler查找指定类的GCRoot (JProfiler插件)
 * https://blog.csdn.net/qq_42292831/article/details/108626922
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
