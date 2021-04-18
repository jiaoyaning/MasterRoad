package com.jyn.masterroad;

import android.os.Handler;
import android.os.Looper;

import androidx.test.espresso.IdlingResource;

/**
 * Android Espresso——UI自动化测试框架实践
 * https://www.jianshu.com/p/471a4c54bb7c
 */
public class IdlingDelayResources implements IdlingResource {
    private boolean timesUp;
    private ResourceCallback mCallback;

    public IdlingDelayResources(int delayMillis) {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                timesUp = true;
            }
        },delayMillis);
    }

    @Override
    public String getName() {
        return IdlingDelayResources.class.getSimpleName();
    }

    @Override
    public boolean isIdleNow() {
        if (timesUp && mCallback != null) {
            mCallback.onTransitionToIdle();
        }
        return timesUp;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        mCallback = callback;
    }
}
