package com.jyn.masterroad;

import com.jyn.common.Utils.MLog;

/**
 * Created by jiaoyaning on 2022/11/22.
 */
class LogTest {
    public String chat_id;

    public LogTest() {
        test3("", "** JAVA 形参1 **", "");
    }

    public void test0() {
        test3("", "** JAVA 形参2 **", "");
    }

    public String userid = "** JAVA 全局变量 **";

    public String test_Id = userid;

    public void test1() {
        MLog.log("this is java 方法 msg " + testFun());
        String id = testFun();
        MLog.log("this is java 方法 msg " + id);
    }

    public void test2() {
        String userid2 = "** JAVA 局部变量 **";
        String test_Id2 = userid2;
        MLog.log("this is java 全局变量 msg " + test_Id);
        MLog.log("this is java 局部变量 msg " + test_Id2);
        LogTest logTest = new LogTest();
        MLog.log("this is java 对象属性 msg " + logTest.chat_id);
    }

    public void test3(String first, String chat_id, String last) {
        MLog.log("this is java 形参 msg " + chat_id);
    }

    private String testFun() {
        System.currentTimeMillis();
        System.currentTimeMillis();
        System.currentTimeMillis();
        return getUserId();
    }

    private String getUserId() {
        System.currentTimeMillis();
        return new LogTest().chat_id;
    }
}
