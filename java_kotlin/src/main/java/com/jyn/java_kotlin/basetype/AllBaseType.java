package com.jyn.java_kotlin.basetype;

/**
 * Created by jiao on 2020/8/10.
 * <p>
 * 详解Java中自动装箱拆箱Integer https://www.jianshu.com/p/d81a7163a899
 */
public class AllBaseType {
    public static void main(String[] args) {
        intTest();
    }

    public static void intTest() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println(g == (a + b));//true
        System.out.println(g.equals(a + b));//false
    }
}
