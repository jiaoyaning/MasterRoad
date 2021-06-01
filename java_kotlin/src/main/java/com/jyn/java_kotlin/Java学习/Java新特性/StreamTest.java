package com.jyn.java_kotlin.Java学习.Java新特性;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/*
 * Java 8 中新增的 Stream 类提供了一种新的数据处理方式。
 * 这种方式将元素集合看做一种流，在管道中传输，经过一系列处理节点，最终输出结果。
 *
 * https://blog.csdn.net/y_k_y/article/details/84633001
 *
 * Java8 Stream：2万字20个实例，玩转集合的筛选、归约、分组、聚合
 * https://mp.weixin.qq.com/s/-vvEbf1d4z5Um7qy2zJNkg
 */
public class StreamTest {
    @SuppressWarnings("NewApi")
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
        Map<String, String> collect = examples
                .stream()
                .collect(Collectors.toMap(Example::getName, Example::getAge));
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

    /*
     * 创建steam的三种方式
     *
     * 1、通过 java.util.Collection.stream() 方法用集合创建流
     * 2、使用 java.util.Arrays.stream(T[] array)方法用数组创建流
     * 3、使用 Stream 的静态方法：of()、iterate()、generate()
     */
    @SuppressWarnings("NewApi")
    public void createSteam() {
        /*
         * stream是顺序流，由主线程按顺序对流执行操作，
         * parallelStream是并行流，内部以多线程并行执行的方式对流进行分段操作
         * 如果流中的数据量足够大，并行流可以加快处速度。
         */
        List<String> list = Arrays.asList("a", "b", "c");
        Stream<String> stream = list.stream();                  // 创建一个顺序流
        Stream<String> parallelStream = list.parallelStream();  // 创建一个并行流
        Stream<String> streamToParallel = stream.parallel();    // 把顺序流转成并行流


        int[] array = {1, 3, 5, 6, 8};
        IntStream arrayStream = Arrays.stream(array);           // 用数组创建流

        Stream<Integer> stream1 = Stream.of(1, 3, 5, 6, 8);
        Stream<Integer> stream2 = Stream.iterate(0, (x) -> x + 3).limit(4);  // 0 3 6 9s
        Stream<Double> stream3 = Stream.generate(Math::random).limit(3);
    }
}
