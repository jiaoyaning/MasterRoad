package com.jyn.java_kotlin.设计模式.Proxy.JavassistBuild;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

/**
 * Created by jiaoyaning on 2021/1/16.
 * 通过Javassist创建的动态代理对象
 * 可以代理多种接口
 *
 * <p>
 * https://my.oschina.net/u/2457218/blog/1499778
 */
public class JavassistBuildClass<T> {
    private final Class<T> t;//接口

    public JavassistBuildClass(Class<T> t) {
        this.t = t;
    }

    private static final String PROXYFREFIX = "$Proxy";//生成的代理对象名称前缀
    private static final String PROXYSUFFIX = "Impl";//生成的代理对象名称前缀

    //生成代理对象
    public T getProxyObject() {
        T proxyObject = null;
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ctClass = pool.makeClass(getPackageName() + "." + getProxyObjectName()); //创建代理类对象

            //设置代理类的接口
            CtClass interf = pool.getCtClass(getPackageName() + "." + t.getSimpleName()); //获取代理对象的接口类
            CtClass[] interfaces = new CtClass[]{interf};

            ctClass.setInterfaces(interfaces);

            CtMethod[] methods = interf.getDeclaredMethods(); //代理类的所有方法
            CtField[] fields = interf.getDeclaredFields();//代理类的所有属性
            for (CtMethod method : methods) {
                String methodName = method.getName();
                CtMethod cm = new CtMethod(method.getReturnType(), methodName, method.getParameterTypes(), ctClass);
                cm.setBody("System.out.println(\"这是一个ActionDao的 Javaassist 实现类1\");");
                ctClass.addMethod(cm);
            }
            proxyObject = (T) ctClass.toClass().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return proxyObject;
    }

    //获取包名
    public String getPackageName() {
        Package aPackage = t.getPackage();
        String packageName = aPackage.getName();
        return packageName;
    }

    //获取代理对象的名称
    public String getProxyObjectName() {
        return PROXYFREFIX + t.getSimpleName() + PROXYSUFFIX;
    }
}
