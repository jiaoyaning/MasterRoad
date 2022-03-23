package com.jyn.language.设计模式.装饰器模式;

/**
 * 具体装饰角色
 */
public class 奶茶 implements 茶 {

    @Override
    public String getName() {
        return "奶茶";
    }

    @Override
    public String getPrice() {
        return "10";
    }
}
