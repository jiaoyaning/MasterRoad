package com.jyn.masterroad.thread.lock;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/*
 * 用AQS实现简单锁功能
 */
public class MyTestAQS {

    // 获取锁
    public void lock() {
        sync.acquire(1);
    }

    // 释放锁
    public void unlock() {
        sync.release(1);
    }


    private final Sync sync = new Sync();

    // 这个内部类就是继承并实现了 AQS 但我这里只先实现两个方法
    private static class Sync extends AbstractQueuedSynchronizer {

        @Override
        public boolean tryAcquire(int acquires) {
            // CAS 方式尝试获取锁，成功返回true，失败返回false
            return compareAndSetState(0, 1);
        }

        @Override
        protected boolean tryRelease(int releases) {
            // 释放锁
            setState(0);
            return true;
        }
    }
}
