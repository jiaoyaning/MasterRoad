package com.jyn.masterroad;

import com.jyn.common.Utils.MLog;

/**
 * Created by jiaoyaning on 2022/11/22.
 */
class LogTest {
    public String id;

    public LogTest() {
        test2("** Java 方法传参 **");
    }

    public static final String userid = "** Java 全局变量 **";

    public void test1() {
        MLog.log("this is java 方法 msg " + getUserId());
    }

    public void test2(String id) {
        String userid2 = "** Java 局部变量 **";
        MLog.log("this is java 全局变量 msg " + userid);
        MLog.log("this is java 局部变量 msg " + userid2);
        LogTest logTest = new LogTest();
        MLog.log("this is java 对象属性 msg " + logTest.id);
        MLog.log("this is java 形参 msg " + id);
    }

    private String getUserId() {
        return "** Java 方法返回值 **";
    }
}
