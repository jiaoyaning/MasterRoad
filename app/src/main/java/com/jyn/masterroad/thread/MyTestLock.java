package com.jyn.masterroad.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/*
 * 万字超强图文讲解AQS以及ReentrantLock应用
 * https://mp.weixin.qq.com/s/ASFv-H9OqfMMMwOkv5dDzA
 *
 * 如何理解互斥锁、条件锁、读写锁以及自旋锁？ - 邱昊宇的回答 - 知乎
 * https://www.zhihu.com/question/66733477/answer/246535792
 *
 * 自定义互斥锁
 */
class MyTestLock implements Lock {


    @Override
    public void lock() {

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    // 静态内部类-自定义同步器
    private static class MyTestSync extends AbstractQueuedSynchronizer {

        /*
         * 尝试获取锁
         */
        @Override
        protected boolean tryAcquire(int arg) {
            // 调用AQS提供的方法，通过CAS保证原子性
            if (compareAndSetState(0, arg)) {
                // 我们实现的是互斥锁，所以标记获取到同步状态（更新state成功）的线程
                setExclusiveOwnerThread(Thread.currentThread());
                //获取同步状态成功，返回 true
                return true;
            }
            // 获取同步状态失败，返回 false
            return false;
        }

        /*
         * 尝试释放锁
         */
        @Override
        protected boolean tryRelease(int arg) {
            // 未拥有锁却让释放，会抛出IMSE
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            // 可以释放，清空排它线程标记
            setExclusiveOwnerThread(null);
            // 设置同步状态为0，表示释放锁
            setState(0);
            return true;
        }

        /*
         * 是否是唯一持有锁
         */
        @Override
        protected boolean isHeldExclusively() { // 是否独占式持有
            return getState() == 1;
        }

        /*
         * 主要用于等待/通知机制，每个condition都有一个与之对应的条件等待队列
         */
        Condition newCondition() {
            return new ConditionObject();
        }
    }
}
