package com.jyn.language.设计模式.代理模式.JVMProxyBean;

/**
 * Created by jiaoyaning on 2021/1/16.
 * Action实现类
 */
public class ActionDaoImpl implements ActionDao {

    @Override
    public void doSomething() {
        System.out.println("这是一个ActionDao的实现类");
    }
}
