package com.jyn.java_kotlin.设计模式.代理模式.JVMProxyBean;

/**
 * Created by jiaoyaning on 2021/1/16.
 * Action静态代理类
 */
public class ActionProxy {
    private final ActionDao actionDao;

    public ActionProxy(ActionDao actionDao) {
        this.actionDao = actionDao;
    }

    public void doSomething() {
        System.out.println(" ---- 静态代理开始 ---- ");
        actionDao.doSomething();
        System.out.println(" ---- 静态代理结束 ---- ");
    }
}
