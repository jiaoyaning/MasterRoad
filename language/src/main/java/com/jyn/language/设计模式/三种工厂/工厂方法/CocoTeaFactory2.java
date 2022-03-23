package com.jyn.language.设计模式.三种工厂.工厂方法;

/**
 * 椰果奶茶
 */
public class CocoTeaFactory2 implements TeaFactory2 {

    @Override
    public Tea create(String name, String price) {
        return new CocoTea(name, price);
    }
}
