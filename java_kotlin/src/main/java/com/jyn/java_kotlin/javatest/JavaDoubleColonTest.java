package com.jyn.java_kotlin.javatest;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 双冒号 方法引用
 * https://blog.csdn.net/zhoufanyang_china/article/details/87798829
 * https://www.toutiao.com/i6807719546158318092/
 * <p>
 * Java方法引用
 * https://www.cnblogs.com/wuhenzhidu/p/10727065.html
 * <p>
 * Optional类
 * https://www.cnblogs.com/zhangboyu/p/7580262.html
 * <p>
 * Created by jiaoyaning on 2020/12/16.
 */
@SuppressWarnings("NewApi")
public class JavaDoubleColonTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("01", "02", "03");
        list.forEach(it -> System.out.println(it));
        list.forEach(System.out::println);
        list.forEach(JavaDoubleColonTest::print);

        //双冒号::静态方法引用
        Function<String, Integer> function = Integer::parseInt;
        Integer apply = function.apply("10");
        System.out.println("静态方法引用 Function<Integer, String>"+apply);

        //双冒号::对象的实例方法引用
        String test1 = "Hello JDK8";
        Function<Integer, String> function1 = test1::substring;
        String apply1 = function1.apply(2);
        System.out.println("对象的实例方法引用 Function<Integer, String>" + apply1);


        //双冒号::构造方法引用
        Supplier<Example> exampleSupplier = Example::new;
        Example get = exampleSupplier.get();
        System.out.println("构造方法引用 Supplier<Example> 无参" + get.toString());

        Function<String, Example> exampleFunction = Example::new;
        Example apply2 = exampleFunction.apply("构造函数");
        System.out.println("构造方法引用 Function<String, Example> 一个参数" + apply2.toString());

        BiFunction<String, String, Example> exampleBiFunction = Example::new;
        Example apply3 = exampleBiFunction.apply("构造参数name", "构造参数age");
        System.out.println("构造方法引用 BiFunction<String, String, Example> 两个参数" + apply3.toString());

        /*
         * Example 类并没有implements InterfaceExample接口
         * 类似Lambda表达式
         */
        Example.InterfaceExample com = Example::new;
        Example.InterfaceExample2 com2 = Example::new;

        Example example = com.create();
        Example example2 = com2.create("test");
        System.out.println("Example.InterfaceExample 无参" + example.toString());
        System.out.println("Example.InterfaceExample 有参" + example2.toString());

        // 函数引用也是一种函数式接口，所以也可以将函数引用作为方法的参数
        String sayHello = sayHello(String::toUpperCase, "xiao xie hello");

        Function<String, String> toUpperCase = String::toUpperCase;
        String sayHello2 = sayHello(toUpperCase, "xiao xie hello");
        System.out.println("方法引用:" + sayHello);
        System.out.println("方法引用:" + sayHello2);
    }

    public static void print(String content) {
        System.out.println(content);
    }

    public static String sayHello(Function<String, String> function, String parameter) {
        return function.apply(parameter);
    }
}
