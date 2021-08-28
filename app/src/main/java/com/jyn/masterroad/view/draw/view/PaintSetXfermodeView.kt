package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp

/**
 * 转换器 (Transfer mode)
 * https://juejin.cn/post/6844903487570968584#heading-17
 *
 * 官方文档
 * https://blog.csdn.net/cquwentao/article/details/51407135
 */
class PaintSetXfermodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        var WIDTH = 150f.dp
        var PADDING = 50f.dp
    }

    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas: Canvas) {
        circleBitmap(canvas)
        testXfermode(canvas)
    }

    private fun circleBitmap(canvas: Canvas) {

        /*
         * 需要新建一个图层(离屏缓冲)，因为旧图层上有背景色，会导致Xfermode失败
         *
         * 1. Canvas.saveLayer()
         *
         *
         * 2. View.setLayerType() 是直接把整个 View 都绘制在离屏缓冲中。
         *      setLayerType(LAYER_TYPE_HARDWARE) 是使用 GPU 来缓冲，
         *      setLayerType(LAYER_TYPE_SOFTWARE) 是直接直接用一个 Bitmap 来缓冲。
         *
         * 注意: saveLayer() 的范围要包含到前后两个图形才可，否则非导致转换失败
         */
        val saveLayer = canvas.saveLayer(PADDING, PADDING, WIDTH + PADDING, WIDTH + PADDING, null)
        //用ovel是因为可以根据方形框来绘制
        canvas.drawOval(PADDING, PADDING, WIDTH + PADDING, WIDTH + PADDING, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint)
        paint.xfermode = null
        //把新图层放回到底板上
        canvas.restoreToCount(saveLayer)
    }

    private fun testXfermode(canvas: Canvas) {
        val argb8888 = Bitmap.Config.ARGB_8888

        //先创建两个Bitmap图片，分别在图片上绘制圆形和方形
        val circleBitmap = Bitmap.createBitmap(WIDTH.toInt(), WIDTH.toInt(), argb8888)
        val squareBitmap = Bitmap.createBitmap(WIDTH.toInt(), WIDTH.toInt(), argb8888)

        //把circleBitmap添加到画布上
        val bitmapCanvas = Canvas(circleBitmap)
        //用Canvas 在circleBitmap上绘制圆形
        paint.color = Color.parseColor("#D81B60")
        bitmapCanvas.drawOval(50f.dp, 0f.dp, 150f.dp, 100f.dp, paint)


        //转换bitmap
        bitmapCanvas.setBitmap(squareBitmap)
        //用Canvas 在circleBitmap上绘制方形
        paint.color = Color.parseColor("#2196F3")
        bitmapCanvas.drawRect(0f.dp, 50f.dp, 100f.dp, 150f.dp, paint)


        //接下来把圆形和方形用Xfermode转换
        val saveLayer = canvas.saveLayer(50f.dp, 250f.dp, 200f.dp, 400f.dp, null)
        canvas.drawBitmap(circleBitmap, 50f.dp, 250f.dp, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        canvas.drawBitmap(squareBitmap, 50f.dp, 250f.dp, paint)
        paint.xfermode = null
        canvas.restoreToCount(saveLayer)
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
        options.inTargetDensity = WIDTH.toInt()
        return BitmapFactory.decodeResource(resources, id, options)
    }

}