package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.px

/*
 * HenCoder Android 开发进阶: 自定义 View 1-1 绘制基础
 * https://juejin.cn/post/6844903486807785485
 *
 * Paint 官方API： https://developer.android.com/reference/android/graphics/Paint.html
 * Canvas 官方API：https://developer.android.com/reference/android/graphics/Canvas.html
 *
 * Paint的setStrokeCap、setStrokeJoin、setPathEffect
 * https://blog.csdn.net/lxk_1993/article/details/102936227
 */
class DrawView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "View"
    }

    /**
     * paint 的 Api 基本都是共有信息，比如颜色之类
     * canvas 的 drawXXX() Api 参数都是独有信息，只能自己用，比如圆的半径，方的位置
     */

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        /**
         * 画笔的风格
         *  Paint.Style.FILL    实心
         *  Paint.Style.STROKE  空心
         *  Paint.Style.FILL_AND_STROKE   同时实心和空心，该参数在某些场合会带来不可预期的显示效果。
         */
        style = Paint.Style.STROKE
        color = Color.parseColor("#2196F3") //设置颜色
        strokeWidth = 4f.px         //设置线条宽度
        textSize = 25f.px           //设置文字大小
        isAntiAlias = true          //设置抗锯齿开关

        /**
         * 设置线条端点形状的方法，端点有圆头 (ROUND)、平头 (BUTT) 和方头 (SQUARE) 三种
         *  Paint.Cap.BUTT   平头(默认)
         *  Paint.Cap.ROUND  圆头
         *  Paint.Cap.SQUARE 方形
         */
        strokeCap = Paint.Cap.ROUND
    }

    override fun onDraw(canvas: Canvas) {
        /**
         * 注意：所有的线条 宽度 & 颜色 都存放在paint(画笔)中
         */

        /**
         * 颜色填充
         * canvas.drawRGB(100, 200, 100); 效果一样
         * canvas.drawARGB(100, 100, 200, 100);  效果一样
         */
        canvas.drawColor(Color.parseColor("#10000000"))

        /**
         * 线条
         * 起点坐标(X,Y)，终点坐标(X,Y)
         * drawLines() 同下 drawPoints() 用法一致
         */
        canvas.drawLine(100f, 50f, 300f, 50f, paint)

        /**
         * 点
         * 点坐标(X,Y)
         * 注：点大小 = paint的宽度
         *
         * 点 集合
         * pts:     两个坐标为一组，
         * offset:  跳过前几个
         * count:   一共绘制几个
         */
        canvas.drawPoint(200f, 75f, paint)
        paint.strokeCap = Paint.Cap.SQUARE //端点为平头
        canvas.drawPoints(floatArrayOf(0f, 0f, 150f, 75f, 250f, 75f, 200f, 75f), 2, 4, paint)

        /**
         * 圆形
         * centerX centerY 是圆心的坐标，第三个参数 radius 是圆的半径
         */
        canvas.drawCircle(200f, 200f, 100f, paint)

        /**
         * 矩形
         * 左 上 右 下
         */
        canvas.drawRect(400f, 50f, 600f, 300f, paint)

        /**
         * 椭圆
         * 左 上 右 下
         */
        canvas.drawOval(700f, 50f, 900f, 300f, paint)

        /**
         * 圆角矩形
         * 左 上 右 下
         * rx 和 ry 是圆角的横向半径和纵向半径。
         */
        canvas.drawRoundRect(100f, 350f, 300f, 600f, 50f, 50f, paint)

        /**
         * 弧形 & 扇形
         * 左 上 右 下   描述弧形(扇形)所在的椭圆
         * startAngle   是弧形的起始角度（x 轴的正向，即正右的方向，是 0 度的位置；顺时针为正角度，逆时针为负角度）
         * sweepAngle   是弧形划过的角度；
         * useCenter    表示是否连接到圆心，如果不连接到圆心，就是弧形，如果连接到圆心，就是扇形
         */
        //空心扇形
        canvas.drawArc(400f, 350f, 600f, 600f, -10f, 100f, true, paint)
        paint.style = Paint.Style.FILL  //填充模式
        //弧形
        canvas.drawArc(400f, 350f, 600f, 600f, 100f, 160f, false, paint)
        paint.style = Paint.Style.STROKE //画线模式
        //不封口的弧形
        canvas.drawArc(400f, 350f, 600f, 600f, -90f, 70f, false, paint)

        /**
         * 文本
         */
        paint.style = Paint.Style.FILL  //填充模式
        canvas.drawText("MasterRoad", 100f, 700f, paint)
        paint.style = Paint.Style.STROKE //画线模式


        /**
         * bitmap
         */
        canvas.drawBitmap(getBitmap(R.mipmap.icon_master_road2), 700f, 350f, paint)
    }


    private fun getBitmap(id: Int): Bitmap {
        val options = BitmapFactory.Options()
        /**
         * options.inJustDecodeBounds = true 表示只读图片，不加载到内存中，就不会给图片分配内存空间，但是可以获取到图片的大小等属性;
         * 设置为false, 就是要加载这个图片.
         */
        options.inJustDecodeBounds = true //只读尺寸
        BitmapFactory.decodeResource(resources, id, options)
        options.inJustDecodeBounds = false //根据尺寸读取原图
        options.inDensity = options.outWidth
        options.inTargetDensity = 200
        return BitmapFactory.decodeResource(resources, id, options)
    }
}