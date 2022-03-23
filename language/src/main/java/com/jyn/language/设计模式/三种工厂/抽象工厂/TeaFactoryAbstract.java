package com.jyn.language.设计模式.三种工厂.抽象工厂;

/**
 * 抽象工厂
 */
abstract class TeaFactoryAbstract {
    public abstract CocoTea createCocoTea(String name, String price);

    public abstract PearlTea createPearlTea(String name, String price);

    public abstract PuddingTea createPuddingTea(String name, String price);
}
