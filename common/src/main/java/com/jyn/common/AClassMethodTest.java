package com.jyn.common;

import com.apkfuns.logutils.LogUtils;

public class AClassMethodTest {
    public static void main(String[] args) {
        logTest();
    }

    private static void logTest() {
        LogUtils.tag("main").i(" --> logTest");
    }
}
