package com.jyn.java_kotlin.Java学习.泛型;

import java.util.ArrayList;

/*
 * 【知识点】Java泛型机制7连问
 * https://juejin.cn/post/6978833860284907527
 *
 * Gson解析泛型
 * https://www.jianshu.com/p/4f797b1f8011
 */
public class Test {
    /*
     * 1.声明侧(Retrofit的返回值)：泛型类、或泛型接口的声明、带有泛型参数的方法、带有泛型参数的成员变量
     *      声明侧泛型会被记录在Class文件的Constant pool中,
     * 2.使用侧(Gson解析泛型)：方法的局部变量
     *      使用侧的泛型会被直接擦除掉
     */

    public static void test() {
        ArrayList<String> list1 = new ArrayList(); //声明侧
        list1.add("1"); //编译通过
//        list1.add(1); //编译错误
        String str1 = list1.get(0); //返回类型就是String

        ArrayList list2 = new ArrayList<String>(); //使用侧
        list2.add("1"); //编译通过
        list2.add(1);   //编译通过
        Object object = list2.get(0); //返回类型就是Object

        new ArrayList<String>().add("11"); //编译通过
//        new ArrayList<String>().add(22); //编译错误

        String str2 = new ArrayList<String>().get(0); //返回类型就是String
    }
}
