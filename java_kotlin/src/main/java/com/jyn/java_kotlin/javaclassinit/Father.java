package com.jyn.java_kotlin.javaclassinit;

/**
 * Created by jiaoyaning on 2020/12/26.
 */
public class Father {
    private int i = test();
    private static int j = method();

    static {
        System.out.println("Father static代码块");
    }

    Father() {
        System.out.println("Father 构造方法");
    }

    {
        System.out.println("Father 普通代码块");
    }

    public int test() {
        System.out.println("Father 成员变量所调用方法");
        return 1;
    }

    public static int method() {
        System.out.println("Father 静态变量所调用方法");
        return 1;
    }

    public static int staticMethod() {
        System.out.println("Father 未调用的静态方法");
        return 1;
    }
}
