package com.jyn.java_kotlin.DesignPattern.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by jiaoyaning on 2021/1/16.
 * 代理模式
 * 一个对象本身不做实际的操作，而是通过其他对象来得到自己想要的结果。
 * 这样做的好处是可以在目标对象实现的基础上，增强额外的功能操作，即扩展目标对象的功能。
 * 代理模式具有无侵入性的优点
 * 我们增加新功能的时候，可以直接新增代理类，进行功能扩展，避免了修改源码。
 * <p>
 * 动态代理竟然如此简单
 * https://mp.weixin.qq.com/s/TMH-EIwdM_nWYs8zy1aJUA
 */
public class ProxyTest {
    public static void main(String[] args) {
        ActionDao actionDao = new ActionDaoImpl();

        /*
         * 静态代理
         * JVM 可以在编译期确定最终的执行方法
         * 已事先知道要代理的是什么类，通常只能代理一个雷
         *
         * 缺点
         * 每次都需要重新编写代理类，而且代理类过多难维护
         */
        ActionProxy actionProxy = new ActionProxy(actionDao);
        actionProxy.doSomething();

        System.out.println();

        /*
         * 动态代理
         * 程序运行时通过反射机制动态生成
         * 可以代理接口下的所有实现类
         * 继承 InvocationHandler 接口 使用 Proxy 类中的 newProxyInstance 方法动态的创建代理类，这是一种基于接口的动态代理。也叫做 JDK 动态代理
         *
         * 类加载器、接口数组你可以把它理解为一个方法树，每棵叶子结点都是一个方法，
         * 通过后面的 proxy.doSomething() 来告诉 JVM 执行的是方法树上的哪个方法。
         *
         * 总结生成代理类对象的过程
         * 1、通过 loader 和 interfaces 创建动态代理类（首先，根据代理类全路径和接口创建代理类的字节码，其次，根据代理类的字节码生成代理类）。
         * 2、通过反射机制获取动态代理类的构造函数（参数类型是 InvocationHandler.class 类型）。
         * 3、通过动态代理类的构造函数和调用处理器对象创建代理类实例。
         */
        InvocationHandler actionInvocationHandler = new ActionInvocationHandler(actionDao);
        ClassLoader classLoader = actionDao.getClass().getClassLoader();
        Class<?>[] interfaces = actionDao.getClass().getInterfaces();
        ActionDao actionInvocationDao = (ActionDao) Proxy.newProxyInstance(classLoader, interfaces, actionInvocationHandler);
        actionInvocationDao.doSomething();
    }
}
