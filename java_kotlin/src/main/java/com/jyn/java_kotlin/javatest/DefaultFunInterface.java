package com.jyn.java_kotlin.javatest;

/**
 * java8 支持接口的默认实现，通过default关键字实现
 */
public interface DefaultFunInterface {
    String notDefaultTest();

    default String defaultTest() {
        return "接口default方法默认返回值";
    }

    static String staticTest() {
        return "接口static方法默认返回值";
    }
}
