package com.jyn.masterroad.nestedscrolling.nestedccrolling.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.appbar.AppBarLayout;

import java.lang.reflect.Field;

public class FixAppBarLayoutBehavior2 extends AppBarLayout.Behavior {
    private static final String TAG = "FixBehavior";
    private OverScroller mScroller1;

    public FixAppBarLayoutBehavior2(Context context, AttributeSet attrs) {
        super(context, attrs);
        bindScrollerValue(context);
    }

    private void bindScrollerValue(Context context) {
        if (mScroller1 != null) return;
        mScroller1 = new OverScroller(context);
        try {
            Field fieldScroller = getScrollerField();
            if (fieldScroller != null) {
                fieldScroller.setAccessible(true);
                fieldScroller.set(this, mScroller1);
            }
        } catch (Exception ignored) {
        }
    }

    private Field getScrollerField() throws NoSuchFieldException {
        Class<?> superclass = this.getClass().getSuperclass();
        try {
            // support design 27及一下版本
            Class<?> headerBehaviorType = null;
            if (superclass != null) {
                headerBehaviorType = superclass.getSuperclass();
            }
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("mScroller");
            } else {
                return null;
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            // 可能是28及以上版本
            Class<?> headerBehaviorType = superclass.getSuperclass().getSuperclass();
            if (headerBehaviorType != null) {
                return headerBehaviorType.getDeclaredField("scroller");
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull CoordinatorLayout parent, @NonNull AppBarLayout child, @NonNull MotionEvent ev) {
        if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {//手指触摸屏幕的时候停止fling事件
            if (mScroller1 != null) {
                mScroller1.abortAnimation();
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev);
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout child, View target, int dx, int dy, int[] consumed, int type) {
        if (type == ViewCompat.TYPE_NON_TOUCH) {
            //当target滚动到边界时主动停止target fling,与下一次滑动产生冲突
            if (getTopAndBottomOffset() == 0) {
                ViewCompat.stopNestedScroll(target, type);
            }
        }
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
    }
}
