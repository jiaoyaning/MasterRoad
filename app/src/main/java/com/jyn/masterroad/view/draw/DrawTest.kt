package com.jyn.masterroad.view.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.apkfuns.logutils.LogUtils

/**
 * Created by jiaoyaning on 2021/8/9.
 */
class DrawTest @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "View"
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        /**
         * 线
         */
        canvas.drawLine(50f, 50f, 250f, 50f, paint)

        /**
         * 圆
         */
        canvas.drawCircle(150f, 200f, 100f, paint)
    }
}