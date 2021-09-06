package com.jyn.masterroad.view.touch.view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp

/**
 * 多点触控大致类型
 *  一、接力型
 *     一个手指做完，另一个手指可以接着做，比如滑动列表
 *  二、配合型
 *     需要根据多指配合的手势，比如地图双指上滑变为3D视图
 *  三、各自为战型
 *     没个手指负责各自功能，比如切水果
 *
 * 单点事件序列
 *    Down -> Move -> Up
 * 多点事件序列
 *    Down -> Move -> PointerDone -> PointerUp -> Up
 */
class MultiTouchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    //图片偏移位置
    private var offsetX = 0f
    private var offsetY = 0f

    //当前点击位置
    private var downX = 0f
    private var downY = 0f

    //上次偏移的位置
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                //记录当前点击位置
                downX = event.x
                downY = event.y

                //记录上次偏移位置
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                //此次偏移位置 = 移动后的位置 - 触摸点位置 + 上次偏移位置
                offsetX = event.x - downX + originalOffsetX
                offsetY = event.y - downY + originalOffsetX
                invalidate()
            }

            MotionEvent.ACTION_UP ->{

            }
        }
        return true
    }


    private fun getBitmap(id: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //只读尺寸
        BitmapFactory.decodeResource(resources, id, options)
        options.inJustDecodeBounds = false //根据尺寸读取原图
        options.inDensity = options.outWidth
        options.inTargetDensity = 200f.dp.toInt()
        return BitmapFactory.decodeResource(resources, id, options)
    }
}