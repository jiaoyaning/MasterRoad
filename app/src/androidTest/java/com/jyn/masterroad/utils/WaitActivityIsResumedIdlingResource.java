package com.jyn.masterroad.utils;

import android.app.Activity;

import androidx.test.espresso.IdlingResource;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitor;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import androidx.test.runner.lifecycle.Stage;

import java.util.Collection;

/*
 * 等待Activity打开
 * https://stackoverflow.com/questions/36544360/espresso-does-no-record-any-intent-if-there-are-no-buttons
 */
class WaitActivityIsResumedIdlingResource implements IdlingResource {
    private final ActivityLifecycleMonitor instance;
    private final String activityToWaitClassName;
    private volatile ResourceCallback resourceCallback;
    boolean resumed = false;

    public WaitActivityIsResumedIdlingResource(String activityToWaitClassName) {
        instance = ActivityLifecycleMonitorRegistry.getInstance();
        this.activityToWaitClassName = activityToWaitClassName;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        resumed = isActivityLaunched();
        if (resumed && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }

        return resumed;
    }

    private boolean isActivityLaunched() {
        Collection<Activity> activitiesInStage = instance.getActivitiesInStage(Stage.RESUMED);
        for (Activity activity : activitiesInStage) {
            if (activity.getClass().getName().equals(activityToWaitClassName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }
}
