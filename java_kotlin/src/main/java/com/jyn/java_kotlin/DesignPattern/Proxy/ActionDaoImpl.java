package com.jyn.java_kotlin.DesignPattern.Proxy;

/**
 * Created by jiaoyaning on 2021/1/16.
 * Action实现类
 */
public class ActionDaoImpl implements ActionDao{

    @Override
    public void doSomething() {
        System.out.println("这是一个ActionDao的实现类");
    }
}
