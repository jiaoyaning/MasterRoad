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
        /*
         * 第一个判断，是为了性能。当这个singleton已经实例化之后，我们再取值其实是不需要再进入加锁阶段的，
         * 所以第一个判断就是为了减少加锁。把加锁只控制在第一次实例化这个过程中，后续就可以直接获取单例即可。
         */
        if (instance == null) {
            synchronized (DoubleCheckSingleton.class) {
                /*
                 * 防止重复创建对象。当两个线程同时走到synchronized这里，线程A获得锁，进入创建对象。
                 * 创建完对象后释放锁，然后线程B获得锁，如果这时候没有判断单例是否为空，那么就会再次创建对象作。
                 */
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
