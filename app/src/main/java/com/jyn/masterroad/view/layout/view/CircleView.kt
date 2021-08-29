package com.jyn.masterroad.view.layout.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.view.draw.dp

/**
 * Created by jiaoyaning on 2021/8/29.
 */
class CircleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val padding = 50f.dp
    private val radius = 100f.dp


    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val size = ((padding + radius) * 2).toInt()

        /**
         * widthMeasureSpec 其实是两个东西的融合，1.限制类型; 2.限制类型的尺寸
         *      1.AT_MOST       限制上限
         *      2.EXACTLY       精确值
         *      3.UNSPECIFIED   不做限制
         */

        //获取宽度限制类型
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        //获取宽度的限制类型尺寸
        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val width = when (specWidthMode) {
            /**
             * 精确值
             *  父View给出精确值的时候，直接选择用父View给定的值
             */
            MeasureSpec.EXACTLY -> specWidthSize
            /**
             * 限制上限
             *  父view限制上限的时候，需要根据自身测量的尺寸来做出判断
             */
            MeasureSpec.AT_MOST -> if (size > specWidthSize) specWidthMode else size
            /**
             * 不限制
             *  父view不舍简直，那可以直接用自身尺寸
             */
            MeasureSpec.UNSPECIFIED -> size
            else -> size
        }

        /**
         * 测量高度
         *
         * 上面的代码其实是通用的，resolveSize()方法可以帮我们快速实现
         */
        val heigh = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(width, heigh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(padding + radius, padding + radius, radius, paint)
    }
}