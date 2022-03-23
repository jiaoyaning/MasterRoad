package com.jyn.language.设计模式.三种工厂.工厂方法;

/**
 * 珍珠奶茶
 */
public class PearlTeaFactory2 implements TeaFactory2 {

    @Override
    public Tea create(String name, String price) {
        return new PearlTea(name, price);
    }
}
