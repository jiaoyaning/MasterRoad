package com.jyn.masterroad.view.touch.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * MotionEvent的getAction、getActionMask和getActionIndex的区别
 * https://www.jianshu.com/p/a62728297b1e
 */
class TouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        /**
         * [MotionEvent.getAction]          //表示触摸动作的原始32位信息，包括Touch事件的具体动作和触控点信息
         * [MotionEvent.getActionMasked]    //表示触摸的动作，按下、抬起、移动等信息。
         * [MotionEvent.getActionIndex]     //表示多点触控中触控点的信息
         *
         * [MotionEvent.ACTION_UP]
         * [MotionEvent.ACTION_DOWN]
         * [MotionEvent.ACTION_CANCEL]
         * [MotionEvent.ACTION_MOVE]
         *
         * [MotionEvent.ACTION_POINTER_DOWN]    非第一个触点点击
         * [MotionEvent.ACTION_POINTER_UP]      非第一个触点抬起
         */


        if (event.actionMasked == MotionEvent.ACTION_UP) { //抬起
            performClick() //触发点击
        }
        /**
         * down事件为起点，如果消费则是down及后续事件，
         * 不能自己消费down别的view消费move
         */
        return true //true 消费这个事件序列
    }
}