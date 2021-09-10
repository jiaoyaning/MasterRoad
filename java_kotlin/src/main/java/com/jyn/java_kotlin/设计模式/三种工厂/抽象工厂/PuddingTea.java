package com.jyn.java_kotlin.设计模式.三种工厂.抽象工厂;

/**
 * 奶茶
 */
public abstract class PuddingTea {
    private String name;
    private String price;

    public PuddingTea(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
