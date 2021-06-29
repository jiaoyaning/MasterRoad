package com.jyn.java_kotlin.Java学习.Java新特性;

import java.util.Optional;

/*
 * 1.8版本Optional
 * https://www.cnblogs.com/zhangboyu/p/7580262.html
 *
 * Java 8 Optional 最佳指南
 * https://mp.weixin.qq.com/s/PqK0KNVHyoEtZDtp5odocA
 */
@SuppressWarnings("NewApi")
public class OptionalTest {
    public static void main(String[] args) {
        //从 Optional 实例中取回实际值对象的方法之一是使用 get() 方法：
        Example example = new Example("测试", "18");
        Optional<Example> optional = Optional.ofNullable(example);
        optional.ifPresent(it -> System.out.println(it.toString()));

        //Optional 类提供了 API 用以返回对象值，或者在对象为空的时候返回默认值。
        Example example1 = null;
        Optional.ofNullable(example1).orElse(example);

        //在example不为空时orElse() 方法仍然创建了 User 对象。与之相反，orElseGet() 方法不创建 User 对象。
        Optional.of(example).orElse(new Example("新创建"));
        Optional.of(example).orElseGet(() -> new Example("新创建"));
    }
}
