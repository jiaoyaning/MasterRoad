package com.jyn.masterroad.view.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup

class TouchLayout @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        TODO("Not yet implemented")
    }

    /**
     * OnTouchListener()监听地点
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 是否拦截
     */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return super.onInterceptTouchEvent(ev)
    }

    /**
     * 是否是一个可滑动控件
     *
     * 主要用于判断列表的点击和滑动效果，比如微信聊天item点击变背景色，滑动却不会变色
     * 返回true时，会延迟100毫秒变色，预防误判。
     */
    override fun shouldDelayChildPressedState(): Boolean  = false
}