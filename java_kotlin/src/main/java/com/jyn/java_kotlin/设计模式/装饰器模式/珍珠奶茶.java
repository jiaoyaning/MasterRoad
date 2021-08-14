package com.jyn.java_kotlin.设计模式.装饰器模式;

/**
 * Created by jiaoyaning on 2021/8/13.
 */
class 珍珠奶茶 implements 茶 {
    private 茶 tea;

    public 珍珠奶茶(茶 tea) {
        this.tea = tea;
    }

    @Override
    public String getName() {
        return tea.getName() + "+珍珠";
    }

    @Override
    public String getPrice() {
        return tea.getPrice() + "+2";
    }
}
