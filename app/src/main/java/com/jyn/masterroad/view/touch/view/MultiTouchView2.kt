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
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp

/**
 * 二、协作型
 *  重点：中心焦点的处理
 *
 *  (本次实验：移动平分，一个往左，一个往右，根据移动速度平分)
 */
class MultiTouchView2 @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    companion object {
        const val TAG = "MultiTouch"
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    //图片偏移位置 (控制图片位移的本质参数)
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
        /**
         * 多触点点的焦点坐标
         *
         * 计算方式：所有触控点的x,y平均值，就是其中心点坐标
         */
        val focusX: Float
        val focusY: Float

        /**
         * 重点：pointerCount获取到的焦点数量会包含本次UP的触控点
         *      所以多指触控抬起时会导致多计算。
         */
        var pointerCount = event.pointerCount

        //是否是多点触控的抬起事件，如果是，需要把这个事件给减去
        val isPointerUp = event.actionMasked == MotionEvent.ACTION_POINTER_UP

        var sumX = 0f
        var sumY = 0f
        for (i in 0 until pointerCount) {
            //略过该UP事件
            if (!(isPointerUp && i == event.actionIndex)) {
                sumX += event.getX(i)
                sumY += event.getY(i)
            }
        }

        //数量应该减一
        if (isPointerUp) pointerCount--


        focusX = sumX / pointerCount
        focusY = sumY / pointerCount

        LogUtils.tag(TAG).i("sumX:${sumX.toInt()} ; sumY:${sumY.toInt()} ; focusX:${focusX.toInt()} ; focusX:${focusX.toInt()}")

        when (event.actionMasked) {
            /**
             * 所有涉及焦点增减的处理其实完全一致
             */
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_POINTER_DOWN,
            MotionEvent.ACTION_POINTER_UP -> {
                //把点击位置变为中心点位置
                downX = focusX
                downY = focusY

                //记录上次偏移位置
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                offsetX = focusX - downX + originalOffsetX
                offsetY = focusY - downY + originalOffsetY
                invalidate()
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