package com.jyn.java_kotlin.设计模式.三种工厂.抽象工厂;

/**
 * 具体工厂 创造热饮
 */
public class TeaFactoryHot extends TeaFactoryAbstract {
    @Override public CocoTea createCocoTea(String name, String price) {
        return new CocoHotTea(name, price);
    }

    @Override public PearlTea createPearlTea(String name, String price) {
        return new PearlHotTea(name, price);
    }

    @Override public PuddingTea createPuddingTea(String name, String price) {
        return new PuddingHodTea(name, price);
    }
}
