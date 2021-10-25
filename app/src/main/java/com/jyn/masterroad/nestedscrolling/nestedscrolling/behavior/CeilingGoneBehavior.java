package com.jyn.masterroad.nestedscrolling.nestedscrolling.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.apkfuns.logutils.LogUtils;
import com.google.android.material.appbar.AppBarLayout;
import com.jyn.masterroad.R;

/*
 * https://www.jianshu.com/p/33dbf14089d3
 */
public class CeilingGoneBehavior extends AppBarLayout.ScrollingViewBehavior {
    /*
     * appbar的高度
     */
    int mParentHeight; // 1140
    /**
     * toolbar 高度
     */

    int mToolBarHeight;

    /*
     * 吸顶条高度
     */
    int mCeilingChildHeight;

    private View nested_scrolling_tv2;

    public CeilingGoneBehavior() {
    }

    public CeilingGoneBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    boolean isToTop = false;

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        super.onDependentViewChanged(parent, child, dependency);

        if (nested_scrolling_tv2 == null) {
            nested_scrolling_tv2 = parent.findViewById(R.id.nested_scrolling_tv2);
        }

        if (mParentHeight == 0) {
            mParentHeight = dependency.getHeight(); //获取AppBarLayout的高度 1140
            LogUtils.tag("NestedScrolling").i("mParentHeight:" + mParentHeight);
        }
        if (mCeilingChildHeight == 0) { //获取吸顶header的高度  120
            mCeilingChildHeight = parent.findViewById(R.id.nested_scrolling_tv).getHeight();
            LogUtils.tag("NestedScrolling").i("mCeilingChildHeight:" + mCeilingChildHeight);
        }

        if (mToolBarHeight == 0) { //ToolBar高度 168
            mToolBarHeight = parent.findViewById(R.id.nested_scrolling_tool_bar).getHeight();
            LogUtils.tag("NestedScrolling").i("mToolBarHeight:" + mToolBarHeight);
        }

        /*
         * dependency.getY() =  -732 时到达顶端
         */

        isToTop = dependency.getY() == -732;

        LogUtils.tag("NestedScrolling").i("是否滑动到了顶端:" + isToTop);

        return false;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return true;
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
        LogUtils.tag("NestedScrolling").i("RecyclerView 滑动:" + dyConsumed);
    }
}
