package com.jyn.java_kotlin.DesignPattern.Singleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * 单例模式-温故而知新
 * https://mp.weixin.qq.com/s/hpGIo4TycWvYFDSSywk8Kw
 *
 * 为什么用枚举类来实现单例模式越来越流行？
 * https://zhuanlan.zhihu.com/p/80127173
 *
 * 「源码分析」— 为什么枚举是单例模式的最佳方法
 * https://mp.weixin.qq.com/s/bU6I5NyaRq1eGXTzUhudUw
 */
public class SingleTest {

    /*
     * 破坏单例模式的方法及解决办法
     * 1、除枚举方式外, 其他方法都会通过反射的方式破坏单例,反射是通过调用构造方法生成新的对象。
     *    所以如果我们想要阻止单例破坏，可以在构造方法中进行判断，若已有实例, 则阻止生成新的实例。
     * 2、如果单例类实现了序列化接口Serializable, 就可以通过反序列化破坏单例，
     *    所以我们可以不实现序列化接口,如果非得实现序列化接口，可以重写反序列化方法readResolve(), 反序列化时直接返回相关单例对象。
     */

    public static void main(String[] args) throws Exception {
        EnumSingleton.INSTANCE.doSomething();

        DoubleCheckSingleton doubleCheckSingleton = DoubleCheckSingleton.getInstance();
        doubleCheckSingleton.doSomething();

        //反序列化攻击单例
        byte[] bytes = serialize(doubleCheckSingleton);
        DoubleCheckSingleton doubleCheckSingleton2 = (DoubleCheckSingleton) deserialize(bytes);
        doubleCheckSingleton2.doSomething();

        System.out.println("是否攻击单例模式成功:" + (doubleCheckSingleton == doubleCheckSingleton2));
    }

    //序列化
    private static byte[] serialize(Object object) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        return baos.toByteArray();
    }

    //反序列化
    private static Object deserialize(byte[] bytes) throws Exception {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
}
