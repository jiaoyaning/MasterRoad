package com.jyn.java_kotlin.Java学习.自动拆装箱;

/**
 * 详解Java中自动装箱拆箱
 * https://www.jianshu.com/p/d81a7163a899
 *
 * 如果是包装类之间的“==”运算，在没有算数运算的情况下不会自动拆箱，比较的是对象地址。
 * 遇到算数运算时会进行自动拆箱，比较数值。
 *
 * 如果是包装类和基本类型之间的"=="比较，包装类会拆箱后比较
 *
 * 包装类的equals(Object)方法是不处理数据转型关系的，如Long.equals(Integer.value(3))，类型不一致就会返回false。
 */
public class AllBaseType {
    public static void main(String[] args) {
        /*
         * 装箱
         * 实际执行：Integer a = Integer.valueOf(1);
         */
        Integer a = 1;
        /*
         * 拆箱
         * 实际执行：int b = a.intValue();
         */
        int b = a;//自动拆箱
        intTest();
    }

    public static void intTest() {
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        /*
         * Integer.valueOf(321); 会缓存范围为-128到127的值。
         * 如果i的值在此范围，将会从定义好的Integer对象数组中返回Integer对象，这样做可以减少对象的频繁创建。
         */
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;

        /*
         * 对象之间的“==”号比较的是地址
         * c和d是同一个缓存返回的对象所以为true
         * e和f是不同的对象，地址不同，自然为false
         */
        System.out.println(c == d);//true
        System.out.println(e == f);//false

        /*
         * a、b、c都进行了拆箱操作，a和b拆箱后进行相加操作，跟拆箱后的c进行比较，结果自然是true。
         */
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println(g == (a + b));//true
        System.out.println(g.equals(a + b));//false


        Integer x = 3;
        int y = 3;
        System.out.println(x == y); //是x拆箱后的值比较
        System.out.println(x.equals(y)); //y装箱后的equals方法比较
    }
}
