package com.jyn.masterroad.view.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by jiao on 2020/7/28.
 */
public class MyButtom extends androidx.appcompat.widget.AppCompatButton {
    public MyButtom(Context context) {
        super(context);
    }

    public MyButtom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyButtom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
//        boolean dispatchTouchEvent = super.dispatchTouchEvent(event);
        Log.i("main", "MyButtom dispatchTouchEvent " + true);
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean onTouchEvent = super.onTouchEvent(event);
        Log.i("main", "MyButtom onTouchEvent "+onTouchEvent);
        return onTouchEvent;
    }

    public @interface WVCallBackType {
        String NOTIFY = "notify";
        String CALLBACK = "callback";
    }
}
