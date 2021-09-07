package com.jyn.masterroad.view.touch.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.SparseArray
import android.view.MotionEvent
import android.view.View
import androidx.core.util.forEach
import com.jyn.masterroad.view.draw.dp

/**
 * 三、独立型
 *  重点：各个焦点的处理
 *
 *  (本次实验：每个触点相互独立，互不影响)
 */
class MultiTouchView3 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "MultiTouch"
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15f.dp
        style = Paint.Style.STROKE
        strokeWidth = 4f.dp
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15f.dp
    }

    //作为多点触控的path集合，用触点的ID当key
    var paths = SparseArray<Path>()

    override fun onDraw(canvas: Canvas) {
        for (i in 0 until paths.size()) {
            //valueAt(index)：取对应下标位置的value
            canvas.drawPath(paths.valueAt(i), paint)
        }

        canvas.drawText("独立型", 100f, height.toFloat() - 100, textPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            /**
             * 没一个按下都可以看成是一个全新的触点
             */
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                val path = Path()
                path.moveTo(event.getX(actionIndex), event.getY(actionIndex))
                //添加path，已触点的id为key
                paths.append(event.getPointerId(actionIndex), path)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                //直接暴力全部更新，管它滑动没滑动
                for (i in 0 until paths.size()) {
                    val painterId = event.getPointerId(i)
                    val path = paths.get(painterId)
                    path.lineTo(event.getX(i), event.getY(i))
                }
                invalidate()
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val painterId = event.getPointerId(actionIndex)
                paths.remove(painterId)
                invalidate()
            }
        }
        return true
    }
}