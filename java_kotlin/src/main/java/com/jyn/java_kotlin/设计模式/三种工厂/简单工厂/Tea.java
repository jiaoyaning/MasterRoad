package com.jyn.java_kotlin.设计模式.三种工厂.简单工厂;

/**
 * 奶茶
 */
public abstract class Tea {
    private String name;
    private String price;

    public Tea(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
