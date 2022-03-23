package com.jyn.language.设计模式.单例模式;

/**
 * 枚举单例是唯一一种不会被外部破坏的单例
 */
public enum EnumSingleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("doSomething");
    }
}
