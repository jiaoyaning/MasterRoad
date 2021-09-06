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
 * 多点触控大致类型
 *  一、接力型
 *     一个手指做完，另一个手指可以接着做，比如滑动列表
 *  二、配合型
 *     需要根据多指配合的手势，比如地图双指上滑变为3D视图
 *  三、各自为战型
 *     没个手指负责各自功能，比如切水果
 *
 * 注意：触摸事件都是相对View而言的，而不是手指
 *      比如，down事件的时候，应该是view上出现了一个触摸点，而不是手指触摸view。
 *
 * 单点事件序列
 *    -> Down
 *    -> Move
 *    -> Up
 *
 * 多点事件序列
 *    -> Down           p(x,y,index,id) //x,y:坐标值 ; index:触控点索引 ; id:触控点id
 *    -> Move           p(x,y,index,id)
 *    -> PointerDone    p(x,y,index,id) p(x,y,index,id) 多指包含多个
 *    -> Move           p(x,y,index,id) p(x,y,index,id)
 *    -> PointerUp      p(x,y,index,id) p(x,y,index,id)
 *    -> Up
 */
class MultiTouchView @JvmOverloads constructor(
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

    //第二个触控点ID
    private var trackingPointerId = 0

    override fun onDraw(canvas: Canvas) {
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        /**
         * event.getX()                 //获取的是index为0的触控点
         * event.getX(int)              //获取指定index的触控点
         * event.getPointerCount()      //获取此时有多少个触控点
         * event.findPointerIndex(int)  //获取对应index触控点的id
         * event.getActionIndex()       //获取当前行为触控点的index(多点移动情况下，始终为0)
         *
         * 注意：index始终从0开始，如果有三个触摸点，随后前一个取消，依旧从0开始， 所以需要以id作为触摸点的唯一表示。
         *    & 在多触控点移动的情况下，是无法单独获取每个触控点的移动轨迹的
         */

        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                LogUtils.tag(TAG).i("onTouchEvent -> DOWN")
                trackingPointerId = event.getPointerId(0) //记录触控点ID

                //记录当前点击位置
                downX = event.x
                downY = event.y

                //记录上次偏移位置
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_POINTER_DOWN -> { //多触控点按下时
                //触发多点触控时，记录下当前触发点的index
                val actionIndex = event.actionIndex
                trackingPointerId = event.getPointerId(actionIndex) //记录触控点ID

                //记录第二个触控点的位置
                downX = event.getX(actionIndex)
                downY = event.getY(actionIndex)

                //记录上次偏移位置
                originalOffsetX = offsetX
                originalOffsetY = offsetY
            }

            MotionEvent.ACTION_MOVE -> {
                LogUtils.tag(TAG).i("onTouchEvent -> MOVE")
                val index = event.findPointerIndex(trackingPointerId) //找到当前手指，已第二个为准
                //此次偏移位置 = 移动后的位置 - 触摸点位置 + 上次偏移位置
                offsetX = event.getX(index) - downX + originalOffsetX
                offsetY = event.getY(index) - downY + originalOffsetY
                invalidate()
            }

            MotionEvent.ACTION_POINTER_UP -> { //多触控点抬起时，在该方法内的 getPointerCount() 包含这个正在抬起的触点
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                //判断当前抬起的触点是否是正在记录且使用的触点，如果是，这选取最后一个触点使用
                if (pointerId == trackingPointerId) {
                    //注意：该getPointerCount()是包含这个正在抬起的触点的
                    val newIndex = if (actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2  //如果是最后一个的话，因为它本身已经不能用了，所以需要再多-1
                    } else {
                        event.pointerCount - 1  //非最后一个话，直接选用最后一个触点
                    }

                    trackingPointerId = event.getPointerId(newIndex) //记录转移后的触控点ID

                    //记录第二个触控点的位置
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)

                    //记录上次偏移位置
                    originalOffsetX = offsetX
                    originalOffsetY = offsetY
                }
            }

            MotionEvent.ACTION_UP -> {
                LogUtils.tag(TAG).i("onTouchEvent -> UP")
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