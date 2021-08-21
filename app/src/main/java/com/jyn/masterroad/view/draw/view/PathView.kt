package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.apkfuns.logutils.LogUtils

/*
 * Android：视图绘制(三) ------Path介绍
 * https://blog.csdn.net/u010635353/article/details/52611065
 *
 * Android：视图绘制(四) ------Path进阶
 * https://blog.csdn.net/u010635353/article/details/52649686
 */
class PathView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)  {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path() //绘制路径
    lateinit var pathMeasure: PathMeasure //对于path路径的计算

    override fun onDraw(canvas: Canvas) {
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

        /*
         * 计算路径长度
         * forceClosed 是否要闭合路径
         */
        pathMeasure = PathMeasure(path, false)
        val pathLength = pathMeasure.length //计算路径长度
        LogUtils.tag(DrawView.TAG).i(pathLength)
//        pathMeasure.getPosTan() //固定长度地方的切角

        /*
         * Path.FillType.WINDING            (根据方向 <0为外，>0为内) 默认规则
         * Path.FillType.EVEN_ODD           (不管方向 偶数为外，奇数为内) 镂空专用
         * Path.FillType.INVERSE_WINDING    WINDING的反规则
         * Path.FillType.INVERSE_EVEN_ODD   EVEN_ODD的反规则
         */
        path.fillType = Path.FillType.EVEN_ODD
    }
}