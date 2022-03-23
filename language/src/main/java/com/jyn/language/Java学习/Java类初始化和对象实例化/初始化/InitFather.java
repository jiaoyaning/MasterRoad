package com.jyn.language.Java学习.Java类初始化和对象实例化.初始化;

public class InitFather {
    private static int j = staticFun();

    public static int staticFun() {
        System.out.println("InitFather 静态变量所引用的static方法");
        return 1;
    }

    static {
        System.out.println("InitFather static代码块");
    }

    InitFather() {
        System.out.println("InitFather 构造方法");
    }

    {
        System.out.println("InitFather 普通代码块");
    }

}
