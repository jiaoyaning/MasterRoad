package com.jyn.java_kotlin.kotlinlearn.statictest;

/**
 * Created by jiaoyaning on 2020/12/6.
 */
class KotlinStaticToJavaTest {
    public static void main(String[] args) {
        System.out.println(KotlinStaticObject.testValWithConst);
        System.out.println(KotlinStaticClass.testValWithConst);

        /*
         * 非const修饰静态，需要Companion才能访问
         * 解决方案：@JvmField
         */
        System.out.println(KotlinStaticObject.getTestVarWithJvmStatic());
        System.out.println(KotlinStaticClass.Companion.getTestVar());
        System.out.println(KotlinStaticClass.testVarWithJvmField);

        /*
         * java调用kotlin静态方法
         * 解决方案：@JvmStatic
         *
         * object类需要INSTANCE ——> 因为object类似于Java的单例，instance就是获取实例对象的方法。
         * class中的伴生需要Companion
         */
        KotlinStaticObject.INSTANCE.testFun();
        KotlinStaticClass.Companion.testFun();

        //添加@JvmStatic注解后，伴生对象的静态方法，不再需要Companion
        KotlinStaticObject.testFunWithJvmStatic();
        KotlinStaticClass.staticFunWithJvmStatic();
    }
}
