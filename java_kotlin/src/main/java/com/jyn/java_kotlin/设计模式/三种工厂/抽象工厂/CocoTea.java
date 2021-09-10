package com.jyn.java_kotlin.设计模式.三种工厂.抽象工厂;

/**
 * 奶茶
 */
public abstract class CocoTea {
    private String name;
    private String price;

    public CocoTea(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
