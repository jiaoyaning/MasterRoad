package com.jyn.java_kotlin.javatest;

/**
 * Java8 Java9 接口方法测试
 *
 * Java8 中，接口可以实现如下变量/方法
 * 常量
 * 抽象方法
 * 默认方法
 * 静态方法
 */
public class DefaultFunInterfaceTest implements DefaultFunInterface {

    public static void main(String[] args) {
        DefaultFunInterfaceTest test = new DefaultFunInterfaceTest();
        System.out.println(variable);

        System.out.println(test.notDefaultTest());
        System.out.println(test.defaultTest());
        System.out.println(DefaultFunInterface.staticTest());
    }

    @Override
    public String notDefaultTest() {
        return "接口方法重写后的返回值";
    }

    /**
     * 可重写可不写
     */
    @Override
    public String defaultTest() {
        return null;
    }
}
