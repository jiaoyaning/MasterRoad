package com.jyn.java_kotlin.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

/**
 * Java知识点—反射
 * https://mp.weixin.qq.com/s/a6CPTxlaN6-o46lpb0vY6w
 * <p>
 * Java反射进阶—聊聊反射的几个问题
 * https://mp.weixin.qq.com/s/oKiIAwVyPjmUGt6M6jyk6w
 * <p>
 * 主要有三种方法获取Class对象：
 * <p>
 * 1、根据类路径获取类对象
 * 2、直接获取
 * 3、实例对象的getClass()方法
 */
public class ReflectTest {

    public static void main(String[] args) throws Exception {

        /*
         * 1、根据类路径获取类对象
         * TODO 需要引入class文件，单个Java执行下，暂时没找到好的解决方案
         */

//        try {
//            Class clzForName = Class.forName("com.jyn.java_kotlin.Reflection.reflection.Test");
//            System.out.println("Class.forName方法得到的 class :" + clzForName);
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }

        /*
         * 实例对象的getClass()方法
         */
        Test test = new Test();
        Class<? extends Test> clzGetClass = test.getClass();
        System.out.println("getClass()方法得到的 class :" + clzGetClass);

        /*
         * 3.直接获取
         */
        Class<Test> clzClass = Test.class;
        System.out.println("Test.class方法得到的 class :" + clzClass);

        constructor(clzClass);

        instance(clzClass);
    }

    /**
     * 反射获取构造方法
     */
    private static void constructor(Class<Test> clzClass) throws NoSuchMethodException {
        //获取所有构造函数（不包括私有构造方法）
        Constructor<?>[] constructors1 = clzClass.getConstructors();

        //获取所有构造函数（包括私有构造方法）
        Constructor<?>[] constructors2 = clzClass.getDeclaredConstructors();

        //获取无参构造函数
        Constructor<Test> constructor = clzClass.getConstructor();

        //获取参数为String的构造函数
        Constructor<Test> constructor1 = clzClass.getConstructor(String.class);

        //获取参数为 (int , String) 的私有构造函数
        Constructor<Test> constructor2 = clzClass.getDeclaredConstructor(int.class, String.class);
    }


    /**
     * 反射创建实例对象
     */
    private static void instance(Class<Test> clz) throws Exception {
        /*
         * 调用 Class 对象的 newInstance() 方法
         * 这个方法只能调用无参构造函数，也就是 Class 对象的 newInstance 方法不能传入参数
         */
        Test test = clz.newInstance();

        /*
         * 调用Constructor对象的newInstance()方法
         */
        Constructor<Test> constructor = clz.getDeclaredConstructor(int.class, String.class);
        //否则会报错 can not access a member of class com.example.testapplication.reflection.User with modifiers "private"
        constructor.setAccessible(true); //使得私有方法可以通过反射调用
        constructor.newInstance(10, "这是一个newInstance()创建的对象");

        /*
         * 获取类的属性（包括私有属性）
         */
        Field field = clz.getField("string");  //只能获取public
        Field declaredField = clz.getDeclaredField("integer");  //能获取所有类型

        field.set(test, "反射修改后的string");

        declaredField.setAccessible(true);
        declaredField.set(test, 100);

        System.out.println("field.set() 方法测试 :" + test.toString());


        /*
         * 获取类的方法
         */
        clz.getMethod("");
    }
}
