package com.jyn.masterroad.view.draw.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.view.draw.px

/*
 * 路径效果
 * https://juejin.cn/post/6844903487570968584#heading-32
 *
 * Android：视图绘制(五) ------Paint进阶之PathEffect
 * https://blog.csdn.net/u010635353/article/details/52701298
 *
 * Android——详解Paint的setPathEffect(PathEffect effect)
 * https://blog.csdn.net/u012230055/article/details/103148506
 */
@SuppressLint("DrawAllocation")
class PathEffectView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val gap = 150f
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 13f.px
    }

    private fun getPaint(): Paint {
        return Paint(Paint.ANTI_ALIAS_FLAG).apply {
            style = Paint.Style.STROKE
            strokeWidth = 2f
        }
    }

    /**
     * PathEffect大致分为两类
     *  单一效果
     *      CornerPathEffect    把所有拐角变成圆角
     *      DiscretePathEffect  把线条进行随机的偏离，让轮廓变得乱七八糟
     *      DashPathEffect      使用虚线来绘制线条
     *      PathDashPathEffect
     *  组合效果
     *      SumPathEffect       同时叠加两个效果
     *      ComposePathEffect   先对Path使用一个PathEffect，然后再在改变后的Path上使用另一个PathEffect
     */

    override fun onDraw(canvas: Canvas) {
        //原图形
        val paint = getPaint()
        val path = Path().apply {
            moveTo(50f, 50f)
            lineTo(150f, 200f)
            lineTo(400f, 30f)
            lineTo(500f, 100f)
            lineTo(550f, 50f)
        }
        canvas.drawPath(path, paint)
        canvas.drawText("原图形", 600f, 100f, textPaint)


        cornerPathEffect(canvas)
        discretePathEffect(canvas)
        dashPathEffect(canvas)
        pathDashPathEffect(canvas)
    }

    /**
     * CornerPathEffect    把所有拐角变成圆角
     *
     * radius 圆角的半径
     */
    private fun cornerPathEffect(canvas: Canvas) {
        val paint = getPaint()
        val path = Path().apply {
            moveTo(50f, 50f + gap)
            lineTo(150f, 200f + gap)
            lineTo(400f, 30f + gap)
            lineTo(500f, 100f + gap)
            lineTo(550f, 50f + gap)
        }
        paint.pathEffect = CornerPathEffect(40f)
        canvas.drawPath(path, paint)
        canvas.drawText("CornerPathEffect", 600f, 100 + gap, textPaint)
    }

    /**
     * DiscretePathEffect  把线条进行随机的偏离，让轮廓变得乱七八糟
     *
     * segmentLength    用来拼接的每个线段的长度
     * deviation        偏离量
     */
    private fun discretePathEffect(canvas: Canvas) {
        val paint = getPaint()
        val path = Path().apply {
            moveTo(50f, 50f + gap * 2)
            lineTo(150f, 200f + gap * 2)
            lineTo(400f, 30f + gap * 2)
            lineTo(500f, 100f + gap * 2)
            lineTo(550f, 50f + gap * 2)
        }
        paint.pathEffect = DiscretePathEffect(20f, 5f)
        canvas.drawPath(path, paint)
        canvas.drawText("DiscretePathEffect", 600f, 100 + gap * 2, textPaint)
    }

    /**
     * DashPathEffect   虚线
     *
     * intervals[]  数组,虚线的格式：数组中元素必须为偶数（最少是 2 个）, 按照「画线长度、空白长度、画线长度、空白长度」……的顺序排列
     * phase        将虚线向”左“偏移的偏移量
     */
    private fun dashPathEffect(canvas: Canvas) {
        val paint = getPaint()
        val path = Path().apply {
            moveTo(50f, 50f + gap * 3)
            lineTo(150f, 200f + gap * 3)
            lineTo(400f, 30f + gap * 3)
            lineTo(500f, 100f + gap * 3)
            lineTo(550f, 50f + gap * 3)
        }

        paint.pathEffect = DashPathEffect(floatArrayOf(20f, 10f, 5f, 10f), 0f)
        canvas.drawPath(path, paint)
        canvas.drawText("DashPathEffect", 600f, 100 + gap * 3, textPaint)
    }


    /**
     * PathDashPathEffect   使用一个 Path 来绘制虚线
     *
     * shape    用来绘制的 Path
     * advance  两个shape 之间的间隔，PS: 是两个shape起点的间隔，而不是前一个的终点和后一个的起点的距离
     * phase    将虚线向”左“偏移的偏移量
     * style    拐弯改变形状的时候 shape 的转换方式
     *      TRANSLATE   位移
     *      ROTATE      旋转
     *      MORPH       变体
     */
    private fun pathDashPathEffect(canvas: Canvas) {
        val paint = getPaint()
        val dashPath = Path().apply {
            moveTo(15f, 0f)
            lineTo(0f, 20f)
            lineTo(30f, 20f)
            close()
        } //path形状

        val path = Path().apply {
            moveTo(50f, 50f + gap * 4)
            lineTo(150f, 200f + gap * 4)
            lineTo(400f, 30f + gap * 4)
            lineTo(500f, 100f + gap * 4)
            lineTo(550f, 50f + gap * 4)
        }

        paint.pathEffect = PathDashPathEffect(dashPath, 40f, 0f, PathDashPathEffect.Style.TRANSLATE)
        canvas.drawPath(path, paint)
        canvas.drawText("PathDashPathEffect TRANSLATE", 600f, 100 + gap * 4, textPaint)

        path.offset(0f, gap / 2)
        paint.pathEffect = PathDashPathEffect(dashPath, 40f, 0f, PathDashPathEffect.Style.ROTATE)
        canvas.drawPath(path, paint)
        canvas.drawText("PathDashPathEffect ROTATE", 600f, 100 + gap * 4.5f, textPaint)

        path.offset(0f, gap / 2)
        paint.pathEffect = PathDashPathEffect(dashPath, 40f, 0f, PathDashPathEffect.Style.MORPH)
        canvas.drawPath(path, paint)
        canvas.drawText("PathDashPathEffect MORPH", 600f, 100 + gap * 5f, textPaint)

    }


    private fun sumPathEffect(canvas: Canvas) {

    }

    private fun composePathEffect(canvas: Canvas) {

    }
}