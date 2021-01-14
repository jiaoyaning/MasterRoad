package com.jyn.java_kotlin.JavaNewFeatureTest;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Java 8 中新增的 Stream 类提供了一种新的数据处理方式。
 * 这种方式将元素集合看做一种流，在管道中传输，经过一系列处理节点，最终输出结果。
 * <p>
 * https://blog.csdn.net/y_k_y/article/details/84633001
 */
public class StreamTest {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("mma", "aqqq", "abbbb", "ccccccc");

        /*
         * map：接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
         * flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
         *
         * filter：过滤流中的某些元素
         * limit(n)：获取n个元素
         * skip(n)：跳过n元素，配合limit(n)可实现分页
         * distinct：通过流中元素的 hashCode() 和 equals() 去除重复元素
         */
        list.stream()
                .filter(it -> it.contains("a")) //过滤
                .map(it -> it + "," + it + "拼接")
                .flatMap(it -> {
                    String[] split = it.split(",");
                    return Arrays.stream(split);
                })
                .sorted() //排序
                .forEach(System.out::println);

        System.out.println("=========分割线==========");

        Example example1 = new Example("test1", "18");
        Example example2 = new Example("test2", "19");
        Example example3 = new Example("test3", "20");
        List<Example> examples = Arrays.asList(example1, example2, example3);

        System.out.println("=========分割线==========");

        /*
         * Collector 工具库：Collectors
         */
        //装成list
        examples.stream()
                .map(Example::getName)
                .collect(Collectors.toList())
                .forEach(System.out::println);

        //转成map,注:key不能相同，否则报错
        Map<String, String> collect = examples.stream().collect(Collectors.toMap(Example::getName, Example::getAge));
        collect.forEach((s, s2) -> System.out.print(s + " : " + s2));


        System.out.println("=========分割线==========");

        /*
         * peek：如同于map，能得到流中的每一个元素。
         * 但map接收的是一个Function表达式，有返回值；
         * 而peek接收的是Consumer表达式，没有返回值。
         *
         * 注更改源数据
         */
        examples.stream()
                .peek(it -> it.setName("我消费了"))
                .forEach(System.out::println);
    }
}
