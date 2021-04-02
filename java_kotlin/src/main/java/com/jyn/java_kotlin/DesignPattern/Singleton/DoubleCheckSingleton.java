package com.jyn.java_kotlin.DesignPattern.Singleton;

import java.io.Serializable;

/*
 * 双重校验
 */
public class DoubleCheckSingleton implements Serializable {
    //volatile关键字严格遵循happens-before原则，即在读操作前，写操作必须全部完成。
    private static volatile DoubleCheckSingleton instance; // volatile 避免指令重排序

    private DoubleCheckSingleton() {
        if (instance != null) {
            throw new RuntimeException("实例已经存在，请通过 getInstance()方法获取");
        }
    }

    public static DoubleCheckSingleton getInstance() {
        // 第一次判断，如果这里为空，不进入抢锁阶段，直接返回实例
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                // 抢到锁之后再次判断是否为空
                if (instance == null) {
                    instance = new DoubleCheckSingleton();
                }
            }
        }
        return instance;
    }

    public void doSomething() {
        System.out.println("doSomething...");
    }

    /**
     * 当 ObjectInputStream类反序列化时，如果对象存在 readResolve 方法，则会调用该方法返回对象。
     */
    private Object readResolve() {
        return instance;
    }
}
