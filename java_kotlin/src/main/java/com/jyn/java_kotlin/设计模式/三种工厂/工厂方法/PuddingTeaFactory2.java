package com.jyn.java_kotlin.设计模式.三种工厂.工厂方法;

/**
 * 布丁奶茶
 */
public class PuddingTeaFactory2 implements TeaFactory2 {

    @Override
    public Tea create(String name, String price) {
        return new PuddingTea(name, price);
    }
}
