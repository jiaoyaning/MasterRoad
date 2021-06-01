package com.jyn.java_kotlin.Java学习.Java新特性;

import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/*
 * https://juejin.cn/post/6844903849753329678
 * Lambda表达式，即函数式编程，可以将行为进行传递。
 * 把函数作为一个方法的参数（函数作为参数传递进方法中）。
 *
 * Lambda表达式更像是一个方法的抽象，(输入的参数) -> (输出的参数)
 */
public class JavaLambdaTest {
    public static void main(String[] args) {
        //判断真假 返回类型:boolean
        Predicate<String> predicate = x -> x.length() > 3;
        System.out.println(predicate.test("11111"));
        System.out.println(predicate.test("11"));

        //消费消息 没有返回值
        Consumer<String> consumer = System.out::println;
        consumer.accept("我是一个消费消息");

        //将T映射为R（转换功能）
        Function<String, Integer> function = Integer::parseInt;
        Integer apply = function.apply("10");
        System.out.println("Function 转换功能测试:" + apply);

        //生产消息
        Supplier<Integer> supplier = () -> Integer.valueOf("10");
        System.out.println("Supplier方法测试:" + supplier.get());


        UnaryOperator<Boolean> unaryOperator = uglily -> !uglily;
        Boolean apply2 = unaryOperator.apply(true);
        System.out.println("UnaryOperator方法测试:" + apply2);

        BinaryOperator<Integer> operator = (x, y) -> x * y;
        Integer apply3 = operator.apply(2, 3);
        System.out.println("BinaryOperator方法测试:" + apply3);

        test(() -> "我是一个演示的函数式接口");
    }

    /**
     * 演示自定义函数式接口使用
     */
    public static void test(Worker worker) {
        String work = worker.work();
        System.out.println(work);
    }

    public interface Worker {
        String work();
    }
}
