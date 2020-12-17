package com.jyn.java_kotlin.javatest;

/**
 * Java8 接口方法测试
 */
public class DefaultFunInterfaceTest implements DefaultFunInterface {

    public static void main(String[] args) {
        DefaultFunInterfaceTest test = new DefaultFunInterfaceTest();

        System.out.println(test.notDefaultTest());
        System.out.println(test.defaultTest());
        System.out.println(DefaultFunInterface.staticTest());
    }

    @Override
    public String notDefaultTest() {
        return "接口方法重写后的返回值";
    }
}
