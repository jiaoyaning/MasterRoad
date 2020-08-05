package com.jyn.java_kotlin.synchronizedTest;

/**
 * Created by jiaoyaning on 2020/8/5.
 */
class SynchronizedObject {
    public synchronized void showA() {
        System.out.println("showA...方法锁");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void showB() {
        synchronized (this) {
            System.out.println("showB...同步块 本类对象");
        }
    }

    public void showC() {
        String s = "1";
        synchronized (s) {
            System.out.println("showC...同步块 新对象");
        }
    }
}