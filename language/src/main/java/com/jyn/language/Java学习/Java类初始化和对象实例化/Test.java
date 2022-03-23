package com.jyn.language.Java学习.Java类初始化和对象实例化;

import com.jyn.language.Java学习.Java类初始化和对象实例化.初始化.InitSon;

public class Test {

    public static void main(String[] args) {
        InitSon.main(args); //类的初始化测试
        /*
         * 执行结果
         *   InitFather 静态变量所引用的static方法      1.Father静态变量
         *   InitFather static代码块                  2.Father静态代码
         *   InitSon 静态变量所引用的static方法         3.Son静态变量
         *   InitSon static代码块                     4.Son静态代码
         */
    }
}
