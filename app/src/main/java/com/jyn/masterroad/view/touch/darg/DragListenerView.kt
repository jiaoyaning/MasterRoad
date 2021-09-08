package com.jyn.masterroad.view.touch.darg

import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup

/*
 * OnDragListener(半透明)
 *
 * 源码：https://github.com/rengwuxian/HenCoderPlus7/blob/main/CustomViewTouchDrag/app/src/main/java/com/hencoder/drag/view/DragListenerGridView.kt
 */
class DragListenerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {

    override fun onFinishInflate() {
        super.onFinishInflate()
        /**
         * [startDrag]
         */
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }

    inner class DragListener : OnDragListener {
        /**
         * 所有的子view都会收到拖拽事件，相当于通知ViewGroup有一个view被拖拽了
         * 拖拽的View并不会被隐藏，而是居上显示一个半透明的复制View
         */
        override fun onDrag(v: View, event: DragEvent): Boolean {
            return true
        }
    }

    override fun onDragEvent(event: DragEvent?): Boolean {
        return super.onDragEvent(event)
    }
}