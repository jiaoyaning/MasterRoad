package com.jyn.java_kotlin.DesignPattern.Proxy;

import com.jyn.java_kotlin.DesignPattern.Proxy.JVMProxyBean.ActionDao;
import com.jyn.java_kotlin.DesignPattern.Proxy.JVMProxyBean.ActionDaoImpl;
import com.jyn.java_kotlin.DesignPattern.Proxy.JVMProxyBean.ActionInvocationHandler;
import com.jyn.java_kotlin.DesignPattern.Proxy.JVMProxyBean.ActionProxy;
import com.jyn.java_kotlin.DesignPattern.Proxy.JavassistBuild.JavassistBuildClass;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.util.proxy.ProxyFactory;

/*
 * 代理模式
 *
 *  一个对象本身不做实际的操作，而是通过其他对象来得到自己想要的结果。
 * 这样做的好处是可以在目标对象实现的基础上，增强额外的功能操作，即扩展目标对象的功能。
 * 代理模式具有无侵入性的优点
 * 我们增加新功能的时候，可以直接新增代理类，进行功能扩展，避免了修改源码。
 *
 * 动态代理竟然如此简单
 * https://mp.weixin.qq.com/s/TMH-EIwdM_nWYs8zy1aJUA
 *
 * https://mp.weixin.qq.com/s/78Fwo3uJ6wDnNE6To_nC6g
 *
 * https://mp.weixin.qq.com/s?__biz=MzkwMDE1MzkwNQ==&mid=2247495842&idx=1&sn=e04e448d3e193912bf4702125028451f&source=41#wechat_redirect
 */
public class ProxyTest {
    public static void main(String[] args) {
        ActionDao actionDao = new ActionDaoImpl();

        /*
         * 1、静态代理
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
         * 2、动态代理（JDK Proxy）
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
         *
         * 目标对象强制需要实现一个接口，否则无法使用 JDK 动态代理
         */
        InvocationHandler actionInvocationHandler = new ActionInvocationHandler(actionDao);
        ClassLoader classLoader = actionDao.getClass().getClassLoader();
        Class<?>[] interfaces = actionDao.getClass().getInterfaces();
        /*
         * ClassLoader loader： 指的是目标对象class对应的classLoader。
         * Class<?>[] interfaces： 代理类实现的接口，可以传入多个接口。
         * InvocationHandler h：指定代理类的「调用处理程序」，即调用接口中的方法时，会找到该代理工厂h，执行invoke()方法
         *
         * 第一个参数和第二个参数其实在业务上都是固定的，在这里就是actionDao对应的的classLoader和接口类型。
         */
        ActionDao actionInvocationDao = (ActionDao) Proxy.newProxyInstance(classLoader, interfaces, actionInvocationHandler);
        actionInvocationDao.doSomething();

        System.out.println();

        /*
         * 3.1、动态代理（Javassist动态生成class对象 方法1）
         */
        JavassistBuildClass<?> javassistBuildClass = new JavassistBuildClass<>(ActionDao.class);
        ActionDao actionDao1 = (ActionDao) javassistBuildClass.getProxyObject();
        actionDao1.doSomething();

        System.out.println();

        /*
         * 3.2、动态代理（Javassist动态生成class对象 方法2）
         */
        Class<?> aClass = createObjectClass();
        ActionDao actionDao2 = (ActionDao) getProxy(aClass);
        actionDao2.doSomething();
    }

    /**
     * Javaassist方式
     * 创建class类
     */
    public static Class<?> createObjectClass() {
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass cc = classPool.makeClass("java_kotlin.src.main.java.com.jyn.java_kotlin.DesignPattern.Proxy.ActionDaoImpl2");
//            CtClass cc = classPool.makeClass("java_kotlin.build.classes.java.main.com.jyn.java_kotlin.DesignPattern.Proxy.ActionDaoImpl2");
            // 设置接口
            CtClass ctClass = null;
            ctClass = classPool.get("com.jyn.java_kotlin.DesignPattern.Proxy.JVMProxyBean.ActionDao");
            cc.setInterfaces(new CtClass[]{ctClass});
            // 创建方法
            CtMethod saveUser = CtMethod.make("public void doSomething(){}", cc);
            saveUser.setBody("System.out.println(\"这是一个ActionDao的 Javaassist 实现类2\");");
            cc.addMethod(saveUser);
            return cc.toClass();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object getProxy(Class<?> clazz) {

        // 代理工厂
        ProxyFactory proxyFactory = new ProxyFactory();
        // 设置需要创建的子类
        proxyFactory.setSuperclass(clazz);
        proxyFactory.setHandler((self, thisMethod, proceed, args) -> {

            System.out.println("---- Javaassist2 开始拦截 ----");
            Object result = proceed.invoke(self, args);
            System.out.println("---- Javaassist2 结束拦截 ----");

            return result;
        });
        try {
            return proxyFactory.createClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
