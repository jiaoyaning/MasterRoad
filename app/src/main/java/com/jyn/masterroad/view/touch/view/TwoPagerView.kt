package com.jyn.masterroad.view.touch.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup

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
 */
class TwoPagerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }
}