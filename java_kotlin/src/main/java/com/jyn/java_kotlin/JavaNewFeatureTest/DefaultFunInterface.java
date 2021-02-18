package com.jyn.java_kotlin.JavaNewFeatureTest;

/*
 * https://www.runoob.com/java/java9-private-interface-methods.html
 * java8 Java9 支持接口的默认实现，通过default关键字实现
 *
 * 1.8中，接口可以实现如下变量/方法
 * 常量
 * 抽象方法
 * 默认方法
 * 静态方法
 *
 * 1.9中
 * 私有方法
 * 私有静态方法
 *
 *
 * https://blog.csdn.net/Mandypan/article/details/52138062
 * https://developer.ibm.com/zh/articles/l-javainterface-abstract/
 * java中abstract和interface的区别
 * 1. 相同点
 * A. 两者都是抽象类，都不能实例化。
 * B. interface实现类及abstract的子类都必须要实现已经声明的抽象方法。
 *
 * 2. 不同点
 * A. interface需要实现，要用implements，而abstract class需要继承，要用extends。
 * B. 一个类可以实现多个interface，但一个类只能继承一个abstract class。
 * C. interface强调特定功能的实现，而abstract强调所属关系。
 */
public interface DefaultFunInterface {

    //不可被修改的常量
    String variable = "这是一个接口内的常量";

    //抽象方法
    String notDefaultTest();

    //默认方法
    default String defaultTest() {
        return "接口default方法默认返回值";
    }

    //静态方法
    static String staticTest() {
        return "接口static方法默认返回值";
    }

    /**
     * 1.9
     * 私有方法
     */
//    private String privateFunTest() {
//        return "1.9新特性 , 可以在接口里写私有方法";
//    }

    /**
     * 1.9
     * 私有静态方法
     */
//    private static String privateStaticFunTest() {
//        return "1.9新特性 , 可以在接口里写私有静态方法";
//    }
}
