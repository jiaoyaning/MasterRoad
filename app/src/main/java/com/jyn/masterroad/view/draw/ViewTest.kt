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
class ViewTest @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "View"
    }

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
    lateinit var pathMeasure: PathMeasure //对于path路径的计算
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
         *
         * 方向可以根据射线法来判断内外
         *  从view内部延伸出一根线，与path方向的相交判断，path从线的右边穿过+1，从右边穿过-1
         */
        path.addCircle(250f, 150f, 50f, Path.Direction.CW)
        path.addRect(250f, 100f, 350f, 200f, Path.Direction.CW)

        /**
         * forceClosed 是否要自动闭合
         */
        pathMeasure = PathMeasure(path, false)
        //计算路径长度
        LogUtils.tag(TAG).i(pathMeasure.length)

        /**
         * Path.FillType.WINDING            (根据方向 <0为外，>0为内) 默认规则
         * Path.FillType.EVEN_ODD           (不管方向 偶数为外，奇数为内) 镂空专用
         * Path.FillType.INVERSE_WINDING    WINDING的反规则
         * Path.FillType.INVERSE_EVEN_ODD   EVEN_ODD的反规则
         */
        path.fillType = Path.FillType.EVEN_ODD
    }
}