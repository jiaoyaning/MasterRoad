package com.jyn.masterroad.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class MyTouchRecyclerView extends RecyclerView {
    private boolean isCanScroll = false;

    public MyTouchRecyclerView(@NonNull Context context) {
        super(context);
    }

    public MyTouchRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTouchRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        stopScroll();
        if (isCanScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        return super.onInterceptTouchEvent(e);
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
        setNestedScrollingEnabled(false);
    }
}
