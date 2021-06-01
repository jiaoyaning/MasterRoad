package com.jyn.java_kotlin.Java学习.Java类初始化和对象实例化.实例化;

/**
 * https://zhuanlan.zhihu.com/p/111491656
 *
 * jvm基础第三节: <clinit> 与 <init> 方法
 * https://www.jianshu.com/p/8a14ed0ed1e9
 *
 * 一网打尽“类”的初始化实例化知识点
 * https://mp.weixin.qq.com/s/ozsOXBpKpF1GTrX-NPAuFg
 *
 * 类的实例化过程：
 * 为父类和子类本身的实例变量分配堆内存并赋0值 ->
 * 初始化父类普通成员变量 & 执行父类普通代码块 -> 执行父类构造方法 ->
 * 初始化子类普通成员变量 & 执行子类普通代码块 -> 执行子类构造方法
 *
 * PS:所有的执行皆为线性执行
 * 既 初始化过程中如果遇到了实例化，先执行实例化代码
 */
public class Son extends Father {
    private int i = test();
    private static int j = method();

//    private static Son son = new Son(); //该行代码可证明 类的实例化过程可以插入到初始化过程中。

    /*
     * 在static语句块中使用到静态变量时一定要将该静态变量的声明语句放在static语句块的前面,
     * 否则会发生illegal forward references的编译错误
     */
    static {
        System.out.println("Son1 static代码块");
    }

    /**
     * 构造方法默认第一句是 super ，所以会先执行父类的<init>()
     */
    Son() {
        System.out.println("Son1 构造方法");
    }

    {
        System.out.println("Son1 普通代码块");
    }

    public int test() {
        System.out.println("Son1 成员变量所调用方法");
        return 1;
    }

    public static int method() {
        System.out.println("Son1 静态变量所调用方法");
        return 1;
    }

    public static int staticMethod() {
        System.out.println("Son1 未调用的静态方法");
        return 1;
    }

    public static void main(String[] args) {   // main方法所在的类会自动加载类和类的初始化，既执行<clinit>()
        Son son = new Son();

        /*
         * 执行结果：
         *
         * Father 静态变量所调用方法
         * Father static代码块
         * Son1 静态变量所调用方法
         * Son1 static代码块
         *
         * =======================   // 前面的执行结果是类的初始化过程
         *
         *  执行 i 变量初始化的时候调用test()方法
         *  执行父类test()方法，但是在父类的<init>()方法中test是有this对象调用的
         *  this对象又指正在创建的对象所以这里就调用的是子类的test
         * Son1 成员变量所调用方法
         *
         * Father 普通代码块
         * Father 构造方法
         *
         *  又把test()方法执行一遍
         * Son1 成员变量所调用方法
         *
         * Son1 普通代码块
         * Son1 构造方法
         */
    }
}
