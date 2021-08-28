package com.jyn.masterroad.view.draw.view

import android.content.Context
import android.graphics.*
import android.graphics.PixelFormat.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.toColorInt
import com.jyn.masterroad.view.draw.dp

/**
 * Android Drawable 那些不为人知的高效用法
 * https://blog.csdn.net/lmj623565791/article/details/43752383
 *
 * Android UI-自定义Drawable
 * https://www.jianshu.com/p/4e5c66a73259
 */
class DrawableView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val bitmap = Bitmap.createBitmap(20,  20, Bitmap.Config.ARGB_8888)

    /**
     * Bitmap:位图，像素数据的映射，图片信息的存储
     * Drawable:绘制工具，可以用的调用canvas，有一个很重要的作用:重用绘制代码
     */
    init {
        //ktx 便捷互转，严苛意义应该是相互生产
        val drawable = bitmap.toDrawable(resources)
        val bitmap = drawable.toBitmap(20, 20)
        background = MeshDrawable()
    }

    override fun onDraw(canvas: Canvas) {
        drawableTest(canvas)
    }

    private fun drawableTest(canvas: Canvas) {
        val colorDrawable = ColorDrawable(Color.RED)
        //需要设定边界
        colorDrawable.setBounds(0, 0, 200, 200)
        colorDrawable.draw(canvas)
    }

    /**
     * 自定义一个网格drawable
     */
    class MeshDrawable : Drawable() {
        private val interval = 50f.dp //网格间隔
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = "#F9A825".toColorInt()
        }

        /**
         * 核心绘制代码
         */
        override fun draw(canvas: Canvas) {
            //竖线
            var x = bounds.left.toFloat()
            while (x <= bounds.right) {
                canvas.drawLine(x, bounds.top.toFloat(), x, bounds.bottom.toFloat(), paint)
                x += interval

            }

            //横线
            var y = bounds.top.toFloat()
            while (y <= bounds.bottom) {
                canvas.drawLine(bounds.left.toFloat(), y, bounds.right.toFloat(), y, paint)
                y += interval
            }
        }

        /**
         * 透明度
         *  0（0x00）表示完全透明，
         *  255（0xFF）表示完全不透明。
         */
        override fun setAlpha(alpha: Int) {
            paint.alpha = alpha
        }

        override fun getAlpha(): Int = paint.alpha

        /**
         * 颜色过滤器
         *  在绘制出来之前，被绘制内容的每一个像素都会被颜色过滤器改变。
         */
        override fun setColorFilter(colorFilter: ColorFilter?) {
            paint.colorFilter = colorFilter
        }

        override fun getColorFilter(): ColorFilter? = paint.colorFilter

        /**
         * 不透明度
         * 这个方法的意思是获得不透明度。 有几个值：PixelFormat：UNKNOWN，TRANSLUCENT，TRANSPARENT，或者 OPAQUE。
         *      OPAQUE：便是完全不透明，遮盖在他下面的所有内容
         *      TRANSPARENT：透明，完全不显示任何东西
         *      TRANSLUCENT：只有绘制的地方才覆盖底下的内容。
         */
        override fun getOpacity(): Int {
            return when (paint.alpha) {
                0 -> TRANSPARENT
                0xFF -> OPAQUE
                else -> TRANSLUCENT
            }
        }
    }
}
