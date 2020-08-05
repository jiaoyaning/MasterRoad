package com.jyn.java_kotlin.synchronizedTest;

/**
 * Created by jiaoyaning on 2020/8/5.
 */
public class SynchronizedTest {
    public static void main(String[] args) {
        final SynchronizedObject synchronizedObject = new SynchronizedObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedObject.showA();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedObject.showB();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronizedObject.showC();
            }
        }).start();
    }
}
