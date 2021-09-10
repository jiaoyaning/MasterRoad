package com.jyn.java_kotlin.设计模式.三种工厂;

import com.jyn.java_kotlin.设计模式.三种工厂.工厂方法.CocoTeaFactory2;
import com.jyn.java_kotlin.设计模式.三种工厂.工厂方法.TeaFactory2;
import com.jyn.java_kotlin.设计模式.三种工厂.简单工厂.TeaFactory1;

/*
 * 别再到处 new 对象了，试试 3 大工厂模式，真香！！
 * https://mp.weixin.qq.com/s/2UY-kya6bKCfgxxzJQHuDg
 */
public class FactoryTest {
    //region 一、简单工厂
    private void simpleFactoryTest() {
        TeaFactory1.create("Coco", "16");
        TeaFactory1.create("Pearl", "16");
        TeaFactory1.create("Pudding", "16");
    }
    //endregion

    //region 二、工厂方法
    private void factoryMethodTest() {
        TeaFactory2 teaFactory1 = new CocoTeaFactory2();
        teaFactory1.create("Coco", "16");
    }
    //endregion

    //region 三、抽象工厂

    //endregion
}
