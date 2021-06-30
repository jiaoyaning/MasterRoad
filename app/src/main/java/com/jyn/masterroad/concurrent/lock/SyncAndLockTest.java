package com.jyn.masterroad.concurrent.lock;

import android.view.View;

import com.apkfuns.logutils.LogUtils;

/*
 * 万字超强图文讲解AQS以及ReentrantLock应用
 * https://mp.weixin.qq.com/s/ASFv-H9OqfMMMwOkv5dDzA
 *
 * 老板让只懂Java基本语法的我，基于AQS实现一个锁 TODO
 * https://mp.weixin.qq.com/s/1Nq_izUkOGmtvkIQ9c0QRQ
 *
 * 一行一行源码分析清楚 AbstractQueuedSynchronizer TODO
 * https://javadoop.com/post/AbstractQueuedSynchronizer
 *
 * 响应中断测试
 * https://blog.csdn.net/zengmingen/article/details/53260650
 *
 * 由浅入深CAS，小白也能与BAT面试官对线 TODO
 * https://mp.weixin.qq.com/s/WKZYa0_srnquQqMCyV8cEQ
 *
 * 面试侃集合 | ArrayBlockingQueue篇
 * https://mp.weixin.qq.com/s/6hOBvfTitQpz8w53Hs0ymA
 *
 * 图文并茂的聊聊ReentrantReadWriteLock的位运算
 * https://mp.weixin.qq.com/s/NvNWmqZzpbKGRLhBJq9GuA
 *
 * 通俗易懂的ReentrantLock，不懂你来砍我
 * https://mp.weixin.qq.com/s/ks1-_tsTdWm1FEux42rgZw
 *
 * 由浅入深C A S，小白也能与BAT面试官对线
 * https://mp.weixin.qq.com/s/GR7lLGp9JH4bsAgQB3uLrw
 */
public class SyncAndLockTest {
    private static final String TAG = "lock";

    //region Synchronized三种锁方式
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

    //endregion

    //region 自定义 AQS
    MyTestAQS myTestAQS = new MyTestAQS();

    public void myTestAQS(View view) {
        new Thread(() -> aqsTest(1)).start();
        new Thread(() -> aqsTest(2)).start();
    }

    private void aqsTest(int i) {
        myTestAQS.lock();
        LogUtils.tag(TAG).i("MyTestAQS lock i:" + i);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myTestAQS.unlock();
        LogUtils.tag(TAG).i("MyTestAQS unlock i:" + i);
    }
    //endregion

    //region 自定义 Lock
    MyTestLock myTestLock = new MyTestLock();

    public void myTestLock(View view) {
        new Thread(() -> lockTest(1)).start();
        new Thread(() -> lockTest(2)).start();
    }

    private void lockTest(int i) {
        myTestLock.lock();
        LogUtils.tag(TAG).i("MyTestLock lock i:" + i);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myTestLock.unlock();
        LogUtils.tag(TAG).i("MyTestLock unlock i:" + i);
    }
    //endregion
}
