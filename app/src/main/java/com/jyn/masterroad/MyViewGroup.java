package com.jyn.masterroad;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * Created by jiao on 2020/7/30.
 */
public class MyViewGroup extends LinearLayout {


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i("main", "MyViewGroup——>dispatchTouchEvent");
        boolean dispatchTouchEvent = super.dispatchTouchEvent(ev);
        return dispatchTouchEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i("main", "MyViewGroup——>onInterceptTouchEvent");
        boolean onInterceptTouchEvent = super.onInterceptTouchEvent(ev);
        return onInterceptTouchEvent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("main", "MyViewGroup——>onTouchEvent");
//        boolean onTouchEvent = super.onTouchEvent(event);
        return true;
    }
}
