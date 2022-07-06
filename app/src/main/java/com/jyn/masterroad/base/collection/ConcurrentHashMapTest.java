package com.jyn.masterroad.base.collection;

import com.apkfuns.logutils.LogUtils;

/*
 * 你真的了解 ConcurrentHashMap 吗？
 * https://mp.weixin.qq.com/s/7qcmwNbKfEgRIfpUqCoNsg
 *
 * 面试官：ConcurrentHashMap 为什么放弃了分段锁？
 * https://mp.weixin.qq.com/s/L5nI17bWC7fxX6jS2Dh7sg
 */
public class ConcurrentHashMapTest {
    /*
     * 1.7 -> 1.8 的改动
     *
     *   锁 ：分段锁 -> 读写锁
     *         1.7：Segment + ReentrantLock
     *         1.8：CAS + Synchronized
     *
     *   size：调用时计算 -> 实时计算
     *         1.7：遍历所以Segment，计算元素个事和修改次数，与上一次所计算的相比较。
     *              大于表明有改动需要重新计算，等于则直接返回，如果重新计算次数过多，则全部加锁，再次计算。
     *         1.8：在扩容 和 addCount()时 实时计算
     */

    private void logUtilsTest() {
        LogUtils.tag("ConcurrentHashMapTest").i("logUtilsTest ----> 1");
    }
}
