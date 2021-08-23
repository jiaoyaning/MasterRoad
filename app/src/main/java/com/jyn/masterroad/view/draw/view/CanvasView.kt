package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.util.AttributeSet
import android.view.View
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.px

/*
 * HenCoder Android 开发进阶：自定义 View 1-4 Canvas 对绘制的辅助
 * https://juejin.cn/post/6844903489789755406
 */
class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 15f.px
    }
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    override fun onDraw(canvas: Canvas) {
        clip(canvas)
    }

    /**
     * 裁切
     */
    private fun clip(canvas: Canvas) {
        canvas.drawText("clip裁切", 50f, 20f.px, textPaint)
        //原图
        canvas.drawBitmap(bitmap, 50f, 100f, paint)

        //裁切
        canvas.save()
        canvas.clipRect(300f, 150f, 500f, 250f)
        canvas.drawBitmap(bitmap, 300f, 100f, paint)
        canvas.restore()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.save()
            canvas.clipOutRect(550f, 150f, 750f, 250f)
            canvas.drawBitmap(bitmap, 550f, 100f, paint)
            canvas.restore()
        }


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