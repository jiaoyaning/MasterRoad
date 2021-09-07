package com.jyn.masterroad.view.touch.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.children
import com.apkfuns.logutils.LogUtils
import kotlin.math.abs

/*
 * 源码
 * https://github.com/rengwuxian/HenCoderPlus7/blob/main/CustomViewTouchViewGroup/app/src/main/java/com/hencoder/viewgroup/view/TwoPager.kt
 *
 * android view滑动助手类OverScroller
 * https://www.jianshu.com/p/d94454762d3e
 *
 * Android 列表滚动优化之 OverScroller 揭秘
 * http://blog.hanschen.site/2021/04/09/android-over-scroller/
 *
 * android 滑动基础篇
 * https://juejin.cn/post/6960876681892462623
 *
 * Android ViewConfiguration配置说明
 * https://blog.csdn.net/heng615975867/article/details/80406534
 *
 * Android VelocityTracker获取滑动速度
 * https://www.jianshu.com/p/e77704b59379
 *
 * OverScroller的一些重要方法和属性
 * https://blog.csdn.net/chaoyangsun/article/details/94398225
 */
class TwoPagerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {
    companion object {
        const val TAG = "TwoPager"
    }

    var downX = 0f          //down点X坐标
    var downScrollX = 0f    //已划过的X轴距离
    var scrolling = false   //是否正在滑动

    private val viewConfiguration = ViewConfiguration.get(context)          //获取view相关的系统配置
    private var minVelocity = viewConfiguration.scaledMinimumFlingVelocity  //飞速滑动的最小初始速率值。单位是 像素/秒
    private var maxVelocity = viewConfiguration.scaledMaximumFlingVelocity  //飞速滑动的最大初始速率值。单位是 像素/秒
    private var pagingSlop = viewConfiguration.scaledPagingTouchSlop        //滚动页面时的滑动距离

    private val velocityTracker = VelocityTracker.obtain()          // 滑动速度跟踪器
    private val overScroller = OverScroller(context)  // 用来计算滑动位置

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        for (child in children) {
            //忽略子view的宽高设置，直接全部撑满
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        //滑动速度跟踪器清空旧数据并且添加event
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        var result = false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                scrolling = false
                downX = event.x
                downScrollX = scrollX.toFloat()
            }

            MotionEvent.ACTION_MOVE -> {
                if (!scrolling) {
                    val dx = downX - event.x
                    if (abs(dx) > pagingSlop) {
                        scrolling = true
                        parent.requestDisallowInterceptTouchEvent(true)
                        result = true
                    }
                }
            }
        }
        return result
    }


    /**
     * [scrollTo] 移动到某个点
     *   注意：此处方向与绘制点击时的那个坐标值是刚好取反的，比如translate(100,0)是向下平移100像素，但是scrollTo(100,0)是往上移动100
     *   原理：scrollTo(100,0)时，本质意思是把(100,0)处的像素移动到圆点，故而整个view才会往上移动100像素
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downScrollX = scrollX.toFloat() //scrollX：已滑动的距离
            }

            MotionEvent.ACTION_MOVE -> {
                //按下时的坐标 - 滑动坐标 + 前一次滑动距离
                val dx = (downX - event.x + downScrollX).toInt()
                        .coerceAtMost(width)
                        .coerceAtLeast(0)
                scrollTo(dx, 0)
            }

            MotionEvent.ACTION_UP -> {
                /**
                 * 获取当前滑动速度
                 *      units       单位毫秒，即单位内划过的距离
                 *      maxVelocity 最大速率返回限制，超过及返回该速率
                 */
                velocityTracker.computeCurrentVelocity(1000, maxVelocity.toFloat()) // 5m/s 5km/h
                val vx = velocityTracker.xVelocity //获取当前速率

                val targetPage =
                        if (abs(vx) > minVelocity) { //速率超过翻页限制时，直接翻页
                            if (vx < 0) 1 else 0
                        } else { // 否则根据滑动是否屏幕过半，过半翻页否则不翻页
                            if (scrollX > width / 2) 1 else 0
                        }

                // 根据已滑动的距离计算差值，
                val scrollDistance = if (targetPage == 0) -scrollX else width - scrollX
                /**
                 * startScroll()
                 * startX,startY 起点
                 * dx,dy         滑动距离
                 */
                overScroller.startScroll(scrollX, 0, scrollDistance, 0)
                postInvalidateOnAnimation()//startScroll()需要配合postInvalidateOnAnimation使用
            }
        }
        return true
    }

    /**
     * [onDraw]中被调用
     *   [OverScroller.startScroll] 之后调用 [postInvalidateOnAnimation] ->
     *   ViewRootImp.InvalidateOnAnimationRunnable.run -> [invalidate] -> [onDraw]
     */
    override fun computeScroll() {
        if (!overScroller.computeScrollOffset()) return
        scrollTo(overScroller.currX, overScroller.currY)
        postInvalidateOnAnimation()
    }
}