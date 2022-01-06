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
     * 1.7 -> 1.8 优化？
     *      1. 链表 —> 红黑树 ：链表长度超过8时改为红黑树，低于6时才变回链表
     *      2. 头插法 -> 尾插法
     *      3. 扩容时重新计算hash -> 只计算是否需要移动到下一个扩容位
     *
     *
     *
     * HashMap 的 hash() 算法实现(https://blog.csdn.net/qq_38182963/article/details/78940047)
     *     key的哈希值 ^ key的哈希值右移16位
     *   这样是为了防止一些极端情况出现，比如 A为 1000010001110001000001111000000，B 为 0111011100111000101000010100000。
     *   如果数组长度是16，也就是 15 与运算这两个数，会发现结果都是0。这显然不是一个好的hash算法
     *
     *
     *
     * 初始容量为什么是2的n次幂及扩容为什么是2倍的形式？(https://blog.csdn.net/weixin_44273302/article/details/113733422)
     *     1. 为了保证得到的新的数组索引和老数组索引一致
     *     2. rehash时的取余操作，hash % length == hash & (length - 1)这个关系只有在length等于二的幂次方时成立，位运算能比%高效得多。
     *
     */
    HashMap<String, String> hashMap = new HashMap<>();

    public HashMapTest() {
        hashMap.put("test", "test");
    }
}
