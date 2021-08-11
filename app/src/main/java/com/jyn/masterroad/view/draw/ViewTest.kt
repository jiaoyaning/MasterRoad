package com.jyn.masterroad.view.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
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

        pathTest(canvas)
    }

    private fun drawLine(canvas: Canvas) {
        canvas.drawLine(50f, 50f, 300f, 50f, paint)
    }

    private fun drawCircle(canvas: Canvas) {
        canvas.drawCircle(100f, 150f, 50f, paint)
    }

    private val path = Path()
    private fun pathTest(canvas: Canvas) {
        canvas.drawPath(path, paint)
    }

    /**
     * 只有当view尺寸改变的时候，才修改path，减少绘制消耗
     */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        path.reset()
        /*
         * Path.Direction: 方向，作用是为了判断多个图像相交时的填充方式
         *
         * Path.Direction.CW:  顺时针
         * Path.Direction.CCW: 逆时针
         */
        path.addCircle(250f, 150f, 50f, Path.Direction.CW)
        path.addRect(250f, 100f, 350f, 200f, Path.Direction.CW)

        /**
         * Path.FillType.WINDING
         * Path.FillType.EVEN_ODD
         * Path.FillType.INVERSE_WINDING
         * Path.FillType.INVERSE_EVEN_ODD
         */
        path.fillType = Path.FillType.EVEN_ODD
    }
}