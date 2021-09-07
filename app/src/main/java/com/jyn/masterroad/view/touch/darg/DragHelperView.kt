package com.jyn.masterroad.view.touch.darg

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/*
 * Android 拖拽滑动（OnDragListener和ViewDragHelper）
 * https://blog.csdn.net/qq_31339141/article/details/107597055
 *
 * 源码：https://github.com/rengwuxian/HenCoderPlus7/blob/main/CustomViewTouchDrag/app/src/main/java/com/hencoder/drag/view/DragHelperGridView.kt
 */
class DragHelperView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ViewGroup(context, attrs) {
    companion object {
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

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spaceWidth = MeasureSpec.getSize(widthMeasureSpec)
        val spaceHeight = MeasureSpec.getSize(heightMeasureSpec)
        val childWith = spaceWidth / COLUMNS
        val childHeight = spaceHeight / ROWS
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }
}