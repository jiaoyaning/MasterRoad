package com.jyn.masterroad.view.animation.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.Keep
import androidx.core.graphics.withSave
import androidx.core.view.marginLeft
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.px
import kotlinx.android.synthetic.main.activity_ipc_server.view.*
import kotlinx.android.synthetic.main.layout_animator_view_property.view.*

/**
 * Android动画框架总结
 * https://cristianoro7.github.io/2017/11/21/Android%E5%8A%A8%E7%94%BB%E6%A1%86%E6%9E%B6%E6%80%BB%E7%BB%93/
 *
 * Keyframe
 */
@Keep
class ObjectAnimatorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    //上半部分翻转动画
    private var topFlipAnimator = ObjectAnimator
        .ofFloat(this, "topFlip", -30f).apply {
            startDelay = 1000
            duration = 1500
        }

    private var bottomFlipAnimator = ObjectAnimator
        .ofFloat(this, "bottomFlip", 30f).apply {
            startDelay = 1000
            duration = 1500
        }

    init {
        setOnClickListener { bottomFlipAnimator.start() }
        bottomFlipAnimator.start()
    }

    var padding = 80f.px
    var size = 200f.px

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private var camera = Camera().apply {
        setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    var topFlip = 0f //上半部分倾斜角度
        set(value) {
            field = value
            invalidate()
        }
    var bottomFlip = 0f    //下半部分倾斜角度
        set(value) {
            field = value
            invalidate()
        }
    var flipRotation = 0f   //旋转角度

    override fun onDraw(canvas: Canvas) {
        //上半部分
        canvas.withSave {
            translate(padding + size / 2, padding + size / 2)
            rotate(-flipRotation)
            camera.apply {
                save()
                rotateX(topFlip)
                applyToCanvas(this@withSave)
                restore()
            }
            clipRect(-size, -size, size, 0f)//向上放大一倍裁切
            rotate(flipRotation)
            translate(-(padding + size / 2), -(padding + size / 2))
            drawBitmap(bitmap, padding, padding, paint)
        }

        //下半部分
        canvas.withSave {
            translate(padding + size / 2, padding + size / 2) //把图片中心变成坐标轴原点
            rotate(-flipRotation)
            camera.apply {
                save()
                rotateX(bottomFlip)
                applyToCanvas(this@withSave)
                restore()
            }
            clipRect(-size, 0f, size, size)//向下放大一倍裁切
            rotate(flipRotation)
            translate(-(padding + size / 2), -(padding + size / 2))
            drawBitmap(bitmap, padding, padding, paint)
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
        options.inTargetDensity = 200f.px.toInt()
        return BitmapFactory.decodeResource(resources, id, options)
    }
}