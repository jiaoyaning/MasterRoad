package com.jyn.masterroad.NestedScrolling.Behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.apkfuns.logutils.LogUtils;
import com.jyn.masterroad.R;

import java.lang.reflect.Field;

/**
 * https://juejin.cn/post/6844904184915951630#heading-6
 */
public class ContentBehavior extends CoordinatorLayout.Behavior {
    private String TAG = "NestedScrolling";

    private float topBarHeight;//topBar内容高度
    private float contentMaxTransY;//滑动内容初始化TransY

    public ContentBehavior() {
    }

    public ContentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        contentMaxTransY = context.getResources().getDimension(R.dimen.nested_scrolling_content_translation_y);
        topBarHeight = context.getResources().getDimension(R.dimen.top_bar_height);
        LogUtils.tag(TAG).i("topBarHeight:" + topBarHeight);
    }

    @Override
    public boolean onMeasureChild(@NonNull CoordinatorLayout parent, @NonNull View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        final int childLpHeight = child.getLayoutParams().height;
        //因为子view设置了translationY，所以需要重新Measure
        if (childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT || childLpHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {

            //先获取CoordinatorLayout的测量规格信息，若不指定具体高度则使用CoordinatorLayout的高度
            int availableHeight = View.MeasureSpec.getSize(parentHeightMeasureSpec);
            if (availableHeight == 0) {
                availableHeight = parent.getHeight();
            }

            //设置Content部分高度 需要去除掉topBar的高度，不然Content会有被挤出屏幕
            final int height = (int) (availableHeight - topBarHeight);
            final int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
                    childLpHeight == ViewGroup.LayoutParams.MATCH_PARENT
                            ? View.MeasureSpec.EXACTLY
                            : View.MeasureSpec.AT_MOST);

            //执行指定高度的测量，并返回true表示使用Behavior来代理测量子View
            parent.onMeasureChild(child, parentWidthMeasureSpec,
                    widthUsed, heightMeasureSpec, heightUsed);
            return true;
        }
        return false;
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //只接受内容View的垂直滑动
        return directTargetChild.getId() == R.id.recycler_view_content
                && axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    /**
     * consumed[0]表示x方向被消耗的距离；consumed[1]表示y方向被消耗的距离
     */
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull View child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        float transY = child.getTranslationY() - dy;

        LogUtils.tag(TAG).i("dy:" + dy);
        LogUtils.tag(TAG).i("transY:" + transY);

        //处理上滑
        if (dy > 0) {
            if (transY >= topBarHeight) {
                //把y方向的滑动给消耗掉（不消耗掉会出现滑动失灵的问题）
                consumed[1] = (int) dy;
                //设置距上高度
                child.setTranslationY(transY);
            } else {
                //有可能滑动太快导致transY计算过大，这种情况下直接到最顶
                child.setTranslationY(topBarHeight);
            }
        } else {
            //子view已经到定，不能继续向下滑动了
            if (!child.canScrollVertically(-1)) {
                if (transY >= topBarHeight && transY <= contentMaxTransY) {
                    //把y方向的滑动给消耗掉（不消耗掉会出现滑动失灵的问题）
                    consumed[1] = (int) dy;
                    //设置距上高度
                    child.setTranslationY(transY);
                } else {
                    child.setTranslationY(contentMaxTransY);
                }
            }
        }
    }


    private void stopViewScroll(View target) {
        if (target instanceof RecyclerView) {
            ((RecyclerView) target).stopScroll();
        }
        if (target instanceof NestedScrollView) {
            try {
                Class<? extends NestedScrollView> clazz = ((NestedScrollView) target).getClass();
                Field mScroller = clazz.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                OverScroller overScroller = (OverScroller) mScroller.get(target);
                assert overScroller != null;
                overScroller.abortAnimation();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
