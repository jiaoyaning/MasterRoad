package com.jyn.masterroad;

import android.content.Context;

import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 * 官方文档
 * https://developer.android.com/training/testing#UIAutomator
 *
 * 测试单个应用的界面
 * https://developer.android.com/training/testing/ui-testing/espresso-testing
 *
 * Espresso
 * https://developer.android.com/training/testing/espresso
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.jyn.masterroad", appContext.getPackageName());
    }
}
