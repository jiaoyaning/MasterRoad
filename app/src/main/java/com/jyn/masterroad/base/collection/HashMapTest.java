package com.jyn.masterroad.base.collection;

import java.util.HashMap;

/*
 * 面试阿里，HashMap 这一篇就够了
 * https://mp.weixin.qq.com/s/3hi33ueXoau1S6CVtayHPw
 *
 * HashMap连环18问
 * https://mp.weixin.qq.com/s/s7NVXm8KDTcy6xWiUixcNA
 *
 * 阿里面试 HashMap 的 21 连击！一招下来你还有多少血？
 * https://mp.weixin.qq.com/s/p8gHKoLe8FyinhaRE8LfdQ
 *
 * HashMap中的为什么hash的长度为2的幂而&位必须为奇数
 * https://blog.csdn.net/zjcjava/article/details/78495416
 *
 * jdk1.8中HashMap在扩容的时候做了哪些优化
 * https://blog.csdn.net/weixin_32705525/article/details/113076205
 */
public class HashMapTest {
    /*
     * 1.7 -> 1.8 优化
     *      1. 链表 —> 红黑树 ：链表长度超过8时改为红黑树，低于6时才变回链表
     *      2. 头插法 -> 尾插法
     *      3. 扩容时重新计算hash -> 只计算是否需要移动到下一个扩容位
     */
    HashMap<String, String> hashMap = new HashMap<>();

    public HashMapTest() {
        hashMap.put("test", "test");
    }
}
