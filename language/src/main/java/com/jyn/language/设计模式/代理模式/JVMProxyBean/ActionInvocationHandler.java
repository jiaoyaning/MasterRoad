package com.jyn.language.设计模式.代理模式.JVMProxyBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 */
public class ActionInvocationHandler<T> implements InvocationHandler {

    private final T action;

    public ActionInvocationHandler(T action) {
        this.action = action;
    }


    /**
     * @param o       代理对象
     * @param method  真正执行的方法
     * @param objects 调用第二个参数 method 时传入的参数列表值
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        System.out.println(" ---- 动态代理开始 ---- ");
        Object invoke = method.invoke(action, objects);
        System.out.println(" ---- 动态代理结束 ---- ");
        return invoke;
    }
}
