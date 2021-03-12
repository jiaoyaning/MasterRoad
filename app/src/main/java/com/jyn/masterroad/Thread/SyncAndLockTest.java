package com.jyn.masterroad.Thread;

/*
 * 万字超强图文讲解AQS以及ReentrantLock应用 TODO
 * https://mp.weixin.qq.com/s/ASFv-H9OqfMMMwOkv5dDzA
 *
 * 老板让只懂Java基本语法的我，基于AQS实现一个锁 TODO
 * https://mp.weixin.qq.com/s/1Nq_izUkOGmtvkIQ9c0QRQ
 *
 * 一行一行源码分析清楚AbstractQueuedSynchronizer TODO
 * https://javadoop.com/post/AbstractQueuedSynchronizer
 *
 * 响应中断测试
 * https://blog.csdn.net/zengmingen/article/details/53260650
 */
public class SyncAndLockTest {
    /*
     * Synchronized三种锁方式
     * 1. 锁方法
     * 2. 锁静态方法
     * 3. 锁对象
     */
    private static final Object object = new Object();

    public synchronized void normalSyncMethod() {
        //临界区
    }

    public static synchronized void staticSyncMethod() {
        //临界区
    }

    public void syncBlockMethod() {
        synchronized (object) {
            //临界区
        }
    }
}
