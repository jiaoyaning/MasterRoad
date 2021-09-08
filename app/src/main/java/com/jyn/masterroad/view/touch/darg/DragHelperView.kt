package com.jyn.masterroad.view.touch.darg

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.customview.widget.ViewDragHelper
import com.apkfuns.logutils.LogUtils

/*
 * Android 拖拽滑动（OnDragListener和ViewDragHelper）
 * https://blog.csdn.net/qq_31339141/article/details/107597055
 *
 * ViewDragHelper基础使用 (详细)
 * https://blog.csdn.net/xingzhong128/article/details/79388085
 *
 * 写了那么多Android布局，你知道elevation属性吗
 * https://www.jianshu.com/p/c1d17a39bc09
 *
 * 源码：https://github.com/rengwuxian/HenCoderPlus7/blob/main/CustomViewTouchDrag/app/src/main/java/com/hencoder/drag/view/DragHelperGridView.kt
 */
class DragHelperView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    /*
     *  ViewDragHelper 的使用可以分成三个步骤：
     *     1. 使用 ViewDragHelper.create() 创建ViewDragHelper对象
     *     2. 将事件拦截 onInterceptTouchEvent() 和 onTouchEvent() 交由 ViewDragHelper 接管
     *     3. 提供 ViewDragHelper.Callback 处理View的拖拽，ViewGroup重写 computeScroll 处理拖拽动画
     */

    companion object {
        const val TAG = "Drag"
        const val COLUMNS = 2
        const val ROWS = 3
    }

    init {
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#EF5350")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#9C27B0")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#1E88E5")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#00695C")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#00695C")) })
        addView(View(context).apply { setBackgroundColor(Color.parseColor("#546E7A")) })
    }

    /**
     * 创建完之后，需要挂载在[onInterceptTouchEvent]和[onTouchEvent]方法中
     */
    private var dragHelper = ViewDragHelper.create(this, DragCallback())

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val specWidth = MeasureSpec.getSize(widthMeasureSpec)
        val specHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWidth = specWidth / COLUMNS
        val childHeight = specHeight / ROWS
        measureChildren(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        setMeasuredDimension(specWidth, specHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft: Int
        var childTop: Int
        val childWidth = width / COLUMNS
        val childHeight = height / ROWS
        for ((index, child) in children.withIndex()) {
            childLeft = index % 2 * childWidth
            childTop = index / 2 * childHeight
            child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight)
        }
    }

    /**
     * 挂载
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return dragHelper.shouldInterceptTouchEvent(ev)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        dragHelper.processTouchEvent(event) //外挂侦测器
        return true
    }

    override fun computeScroll() {
        /**
         * continueSettling()会自动帮助处理便宜
         */
        if (dragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this)
        } else {
            dragHelper.capturedView?.elevation = 0f
        }
    }

    inner class DragCallback : ViewDragHelper.Callback() {
        var capturedLeft = 0f
        var capturedTop = 0f

        /**
         * 尝试抓住View，当手触摸到要拖拽的View时就会回调
         * 返回true表示要触发拖拽，但返回true还不能够实现拖拽，还需要重写clampViewPositionHorizontal()或clampViewPositionVertical()
         * 返回false表示不拖拽
         */
        override fun tryCaptureView(child: View, pointerId: Int): Boolean {
            LogUtils.tag(TAG).i(child)
            return true
        }

        /**
         * 限制View在拖拽时水平方向的偏移，如果只有只重写了clampViewPositionHorizontal，View的拖拽只能在水平方向移动
         * left：X轴滑动偏移了多少
         * dx：相对于ViewGroup而言，dy表示偏移的距离
         *
         * return： 自己所设置的偏移
         */
        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int {
            return left
        }

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int {
            return top
        }

        /**
         * 当拖动状态改变
         *      STATE_IDLE: 静止状态
         *      STATE_DRAGGING：用户正在拖动状态
         *      STATE_SETTLING: 用户拖动放手之后将子视图安置到最终位置的中间状态
         */
        override fun onViewDragStateChanged(state: Int) {
            super.onViewDragStateChanged(state)
        }

        /**
         * 被拖拽View的回调
         */
        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            LogUtils.tag(TAG).i(capturedChild)

            //高度加1，可以显示在别的view上方
            capturedChild.elevation = capturedChild.elevation + 2
            //记录一下初始位置，等松手的时候再放回去
            capturedLeft = capturedChild.left.toFloat()
            capturedTop = capturedChild.top.toFloat()
        }

        /**
         * 当View被移动的时候回调
         */
        override fun onViewPositionChanged(changedView: View, left: Int, top: Int, dx: Int, dy: Int) {}

        /**
         * 当松手的时候会回调
         */
        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            /**
             * settleCapturedViewAt() 只是帮忙辅助计算，需要自己手动获取参数并移动
             */
            dragHelper.settleCapturedViewAt(capturedLeft.toInt(), capturedTop.toInt())
            postInvalidateOnAnimation()
        }
    }
}