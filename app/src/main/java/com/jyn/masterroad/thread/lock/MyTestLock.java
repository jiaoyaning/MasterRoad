package com.jyn.masterroad.thread.lock;

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
public class MyTestLock implements Lock {

    MyTestSync sync = new MyTestSync();

    @Override
    public void lock() {
        // 阻塞式的获取锁，调用同步器模版方法，获取同步状态
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    /*
     * 静态内部类-自定义同步器
     *
     * AQS 内部维护了一个 同步队列 (双向链表)，用于管理同步状态。
     *  当线程获取同步状态失败时，就会将当前线程以及等待状态等信息构造成一个 Node 节点，将其加入到同步队列中尾部，阻塞该线程
     *  当同步状态被释放时，会唤醒同步队列中“首节点”的线程获取同步状态
     *
     * 等待队列 可以自己定义，没个条件都可以对应一个等待队列，等待队列是单链表，表示本身并不急着获取锁，可以先在队列里面进行等待
     * https://www.cnblogs.com/hekiraku/p/11983093.html (AQS双队列作用)
     *
     * 为什么要是双向同步队列？
     *  1.入队的时候方便找到最后一个节点。
     *  2.在队列同步器中，头节点是成功获取到同步状态的节点，而头节点的线程释放了同步状态后，将会唤醒其他后续节点，
     *    后继节点的线程被唤醒后需要检查自己的前驱节点是否是头节点，如果是则尝试获取同步状态。
     *    所以为了能让后继节点获取到其前驱节点，同步队列便设置为双向链表，而等待队列没有这样的需求，就为单链表。
     */
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
