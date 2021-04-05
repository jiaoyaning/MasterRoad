package com.jyn.java_kotlin.DesignPattern.Singleton;

/**
 * 枚举单例是唯一一种不会被外部破坏的单例
 */
public enum EnumSingleton {
    INSTANCE;

    public void doSomething() {
        System.out.println("doSomething");
    }
}
