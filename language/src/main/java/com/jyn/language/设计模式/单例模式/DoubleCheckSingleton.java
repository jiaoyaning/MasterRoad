package com.jyn.language.设计模式.单例模式;

import java.io.Serializable;

/*
 * 双重校验
 */
public class DoubleCheckSingleton implements Serializable {
    /**
     * volatile关键字严格遵循happens-before原则，即在读操作前，写操作必须全部完成。
     *
     * 1. 给 instance 分配内存
     * 2. 调用 instance 的构造函数来初始化成员变量，形成实例
     * 3. 将 instance 对象指向分配的内存空间（执行完这步 instance 才是非 null 了）
     *
     * 指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，最终的执行顺序可能是 1-2-3 也可能是 1-3-2。
     * 如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。
     */
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
     * @see java.io.ObjectInputStream
     */
    private Object readResolve() {
        return instance;
    }
}
