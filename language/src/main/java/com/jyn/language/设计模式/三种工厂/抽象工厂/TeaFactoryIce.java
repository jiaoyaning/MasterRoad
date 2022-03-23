package com.jyn.language.设计模式.三种工厂.抽象工厂;

/**
 * 具体工厂 创造冷饮
 */
public class TeaFactoryIce extends TeaFactoryAbstract {

    @Override public CocoTea createCocoTea(String name, String price) {
        return new CocoIceTea(name, price);
    }

    @Override public PearlTea createPearlTea(String name, String price) {
        return new PearlIceTea(name, price);
    }

    @Override public PuddingTea createPuddingTea(String name, String price) {
        return new PuddingIceTea(name, price);
    }
}
