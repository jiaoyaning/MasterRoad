package com.jyn.masterroad.view.layout.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import com.apkfuns.logutils.LogUtils
import kotlin.math.max

/*
 * Android onMeasure、Measure、measureChild、measureChildren 的一些区别
 * https://www.jianshu.com/p/56b9d4ab4ee8
 */
class TagLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    companion object{
        const val TAG = "Layout"
    }
    /**
     *  1.AT_MOST       限制上限
     *  2.EXACTLY       精确值
     *  3.UNSPECIFIED   不做限制
     */
    private val childrenBounds = mutableListOf<Rect>()

    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        LogUtils.tag(TAG).i("onMeasure measuredWidth:"+this.measuredWidth)
        this.post {
            LogUtils.tag(TAG).i("post onMeasure measuredWidth:"+this.measuredWidth)
            LogUtils.tag(TAG).i("post onMeasure width:"+this.width)
        }
        LogUtils.tag(TAG).i("onMeasure width:"+this.width)


        var widthUsed = 0  //整体已用宽度
        var heightUsed = 0 //整体已用高度
        var lineWidthUsed = 0 //每一行的宽度使用情况
        var lineMaxHeight = 0 //每一行的最高高度

        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec) //layout的宽度
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec) //layout的横向模式

        for ((index, child) in children.withIndex()) {
            /*
            val layoutParams = child.layoutParams //获取子view的layout属性
            val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec) //layout的横向模式
            val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec) //layout的宽度

            var childWidthSpecMode = 0
            var childWidthSpecSize = 0
            when (layoutParams.width) {
                LayoutParams.MATCH_PARENT -> //子view撑满情况下
                    when (widthSpecMode) {      //本layout的不同情况下
                        //精确值 , 限制上限
                        MeasureSpec.EXACTLY,
                        MeasureSpec.AT_MOST -> { //layout宽度精确值时
                            childWidthSpecMode = MeasureSpec.EXACTLY //给子view设置精确值mode
                            childWidthSpecSize = widthSpecSize - widthUsed //layout剩余空间
                        }
                        //不做限制
                        MeasureSpec.UNSPECIFIED -> { //layout宽度不做限制时
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED //也对自view不做限制
                            childWidthSpecSize = 0 //宽度设定一个默认值0
                        }
                    }
                LayoutParams.WRAP_CONTENT -> //子view自适应
                    when (widthSpecMode) {      //本layout的不同情况下
                        //精确值, 限制上限
                        MeasureSpec.EXACTLY, MeasureSpec.AT_MOST -> {
                            childWidthSpecMode = MeasureSpec.AT_MOST
                            childWidthSpecSize = widthSpecSize - widthUsed
                        }
                        //不做限制
                        MeasureSpec.UNSPECIFIED -> {
                            childWidthSpecMode = MeasureSpec.UNSPECIFIED //也对自view不做限制
                            childWidthSpecSize = 0 //宽度设定一个默认值0
                        }
                    }
                else -> {   //精确要求
                    childWidthSpecMode = MeasureSpec.EXACTLY //给子view设置精确值mode
                    childWidthSpecSize = layoutParams.width //layout剩余空间
                }
            }
            */

            /**
             * 测量子view
             * (越等同于上面一坨)
             *
             * 不舍限制，让子view完全测绘
             */
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)

            if (widthSpecMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.measuredWidth > widthSpecSize) {
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                lineWidthUsed = 0
                //换行后再次测绘该子view，因为已经高度发生了变化
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (index >= childrenBounds.size) childrenBounds.add(Rect())

            val childBounds = childrenBounds[index]
            childBounds.set(
                lineWidthUsed, heightUsed,
                lineWidthUsed + child.measuredWidth, heightUsed + child.measuredHeight
            )
            lineWidthUsed += child.measuredWidth
            widthUsed = max(widthUsed, lineWidthUsed)
            lineMaxHeight = max(lineMaxHeight, child.measuredHeight)
        }

        val selfWidth = widthUsed
        val selfHeight = lineMaxHeight + heightUsed //要加上最后一行的高度，才是整体的高度
        setMeasuredDimension(selfHeight, selfWidth)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for ((index, child) in children.withIndex()) {
            val childBounds = childrenBounds[index]
            child.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    /**
     * 把子view的LayoutParams改成MarginLayoutParams，不然[measureChildWithMargins]方法会报错
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}