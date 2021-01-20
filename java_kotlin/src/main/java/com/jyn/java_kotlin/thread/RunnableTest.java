package com.jyn.java_kotlin.thread;

/**
 *
 * @author jiao
 * @date 2020/9/1
 *
 * ExecutorService 的 shutdown 和 shutdownNow 区别与联系
 * https://www.jianshu.com/p/f2591bdd0877
 *
 * 基于一个Runnable对象创建的多线程并发，只是能操作同一个资源而已，并不能保证线程安全。
 */
public class RunnableTest implements Runnable{

    public int testInteger = 0;

    @Override
    public void run() {
        while (testInteger<10){
            System.out.println(testInteger++);
        }
    }

    public static void main(String[] args) {
        RunnableTest runnableTest = new RunnableTest();
        RunnableTest runnableTest2 = new RunnableTest();
        new Thread(runnableTest).start();
        new Thread(runnableTest).start();
    }
}
