package com.jyn.java_kotlin.设计模式.Proxy.JVMProxyBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类
 */
public class ActionInvocationHandler implements InvocationHandler {

    private final ActionDao actionDao;

    public ActionInvocationHandler(ActionDao actionDao) {
        this.actionDao = actionDao;
    }


    /**
     * @param o       代理对象
     * @param method  真正执行的方法
     * @param objects 调用第二个参数 method 时传入的参数列表值
     */
    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        actionStart();
        Object invoke = method.invoke(actionDao, objects);
        actionDone();
        return invoke;
    }

    public void actionStart() {
        System.out.println(" ---- 动态代理开始 ---- ");
    }

    public void actionDone() {
        System.out.println(" ---- 动态代理结束 ---- ");
    }
}
