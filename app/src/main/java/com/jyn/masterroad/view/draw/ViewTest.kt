package com.jyn.masterroad.view.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View


/**
 * Created by jiaoyaning on 2021/8/9.
 */
class ViewTest @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)//抗锯齿

    override fun onDraw(canvas: Canvas) {
        drawLine(canvas)
        drawCircle(canvas)
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawLine(50f, 50f, 200f, 50f, paint)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(200f, 200f, 100f, paint)
    }
}