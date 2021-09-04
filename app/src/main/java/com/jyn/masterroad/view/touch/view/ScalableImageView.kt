package com.jyn.masterroad.view.touch.view

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.annotation.Keep
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.R
import com.jyn.masterroad.view.draw.dp
import kotlin.math.max
import kotlin.math.min

/**
 * Created by jiaoyaning on 2021/9/3.
 */
@Keep
class ScalableImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), Runnable {
    companion object {
        const val TAG = "ScalableImageView"
        var image_size = 300f.dp.toInt()
        var extra_scale_fraction = 1.5f //额外放缩系数，多放大一点，方便上下滑动
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getBitmap(R.mipmap.icon_master_road2)

    private var originalOffsetX = 0f //初始偏移
    private var originalOffsetY = 0f //初始偏移

    private var offsetX = 0f //滑动偏移
    private var offsetY = 0f

    private var smallScale = 0f
    private var bigScale = 0f
        set(value) {
            field = value * extra_scale_fraction //多放大一些
        }
    private var big = false //放大控制变量

    private var scaleFraction = 0f //动画进度
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFraction", 0f, 1f)
    }

    /**
     * OverScroller(可以超出边界，制造回弹效果) 可以设置初始速度，比较适合Fling处理
     * Scroller(无法回弹) 设置初始速度失效，比较适合从头滑到尾
     *
     * OverScroller & Scroller 只是帮忙计算，需要自己手动调用当前计算结果并二次处理
     */
    private var overScroller = OverScroller(context)

    /**
     * GestureDetector          //SDK 内置版本(跟随系统)
     * GestureDetectorCompat    //AndroidX 版本(跟随开发者SDK)
     *
     * GestureDetectorCompat属于一个外挂手势侦测器 ， 需要手动挂到OnTouchEvent()
     * GestureDetector是一个手势侦测器，用来检测手势
     */
    private val gestureDetector = GestureDetectorCompat(context,
        //object可以同时继承 OnDoubleTapListener 来实现双击事件监听
        object : GestureDetector.OnGestureListener {
            /**
             * 要不要消费该事件序列
             */
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            /**
             * 是否显示按下状态，比如按下变色等场景
             */
            override fun onShowPress(e: MotionEvent?) {
            }

            /**
             * 按下抬起事件(不包含长按监听)
             *      相当于点击监听的替代，因为在覆盖onTouchEvent()方法的时候，把点击事件也给屏蔽掉了
             *      相比onSingleTapConfirmed()，在不支持双击的情况下比较准确，因为取消了300毫秒的双击延迟等待
             *
             * return 是否消费了点击事件(不如onDown()优先级)，
             *      这个返回值，其实不影响整体流程，主要用于系统做记录，开发者很难用到
             */
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            /**
             * 手指移动时事件，只有手指触屏的时候才会触发
             *  downEvent:      第一个点击事件
             *  currentEvent:   触发当前move运动的事件
             *  distanceX:      旧位置 - 新位置 的X轴值
             *  distanceY:      旧位置 - 新位置 的Y轴值
             *
             *  return : 是否消费该事件(同onSingleTapUp)
             */
            override fun onScroll(
                downEvent: MotionEvent, currentEvent: MotionEvent,
                distanceX: Float, distanceY: Float
            ): Boolean {
                setScroll(distanceX, distanceY)
                return true
            }

            /**
             * 长按事件
             * 用于代替onTouchEvent()方法所覆盖的长按事件
             */
            override fun onLongPress(e: MotionEvent?) {
            }

            /**
             * 惯性事件
             *  downEvent:      第一个点击事件
             *  currentEvent:   触发当前move运动的事件
             *  velocityX       X轴速率(单位事件内的X轴的位移)
             *  velocityY       Y轴速率(单位事件内的Y轴的位移)
             *
             *  注意：惯性事件需要自己手动写哦
             */
            override fun onFling(
                downEvent: MotionEvent, currentEvent: MotionEvent?,
                velocityX: Float, velocityY: Float
            ): Boolean {
                LogUtils.tag(TAG).i("onFling -> velocityX:$velocityX ; velocityY:$velocityY")
                setFling(velocityX, velocityY)
                return true
            }
        })
        .apply {
            setIsLongpressEnabled(false) //是否开启长按事件
            /**
             * 双击需要用GestureDetectorCompat来实现
             *
             * setOnDoubleTapListener()双击事件
             */
            setOnDoubleTapListener(object : GestureDetector.OnDoubleTapListener {
                /**
                 * 双击事件
                 * 只有第二次按下的时候收到一个事件
                 */
                override fun onDoubleTap(e: MotionEvent): Boolean {
                    setDoubleTap(e)
                    return true
                }

                /**
                 * 单击事件，用来判定该次点击是单纯的SingleTap(单击)而不是DoubleTap(双击)和LongPress(长按)
                 *
                 * 支持双击的时候，比较准确，但是会有300毫秒延迟来判断是否是双击
                 */
                override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                    return false
                }

                /**
                 * 双击事件及后续事件
                 * 第二次按下 移动 抬起 等多个事件序列
                 */
                override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                    return false
                }
            })
        }

    /**
     * 双击事件
     */
    fun setDoubleTap(e: MotionEvent) {
        big = !big
        if (big) { //放大动画时，根据点击位置进行放大后的偏移处理
            /**
             * scale = bigScale / smallScale    在smallScale的基础上又放大了多少倍，故而用除法(真实放大倍率)
             * x = e.x - width / 2f             由中心点放大，所以要先找到触摸点相对中心店的位置(x,y)
             * x * scale - x                    减去原始点坐标后，就是放大后的点相对原始点的偏移量了
             *
             * 但是，之所以没有根据触摸点放大，是因为放大倍速过多，我们应该是想要减去这部分多余的放大倍速的
             * x - x * scale
             */
            val scale = bigScale / smallScale
            val x = e.x - width / 2f
            val y = e.y - height / 2f
            offsetX = x - x * scale
            offsetY = y - y * scale
            fixOffset() //控制缩放动画的边界
            scaleAnimator.start()
        } else {
            scaleAnimator.reverse()
        }
    }

    /**
     * 限制手动滑动时的移动以及边界
     */
    fun setScroll(distanceX: Float, distanceY: Float) {
        LogUtils.tag(TAG).i("onScroll -> offsetX:$offsetX ; offsetY:$offsetY")
        if (big) {
            offsetX -= distanceX
            offsetY -= distanceY
            fixOffset() //控制移动时的边界
            invalidate()
        }
    }

    /**
     * 控制移动边界
     * 最少不能少于左边界和上边界
     * 最多不能多余右边界和下边界
     */
    private fun fixOffset() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }


    /**
     * Fling事件后，交由overScroller 来计算 fling值
     */
    fun setFling(velocityX: Float, velocityY: Float) {
        if (big) {
            overScroller.fling(
                offsetX.toInt(), offsetY.toInt(), //起始坐标
                velocityX.toInt(), velocityY.toInt(), //起始速度
                (-(bitmap.width * bigScale - width) / 2).toInt(),   //最小X值 (图片宽 - 屏幕宽)
                ((bitmap.width * bigScale - width) / 2).toInt(),    //最大X值
                (-(bitmap.height * bigScale - height) / 2).toInt(), //最小Y值 (图片宽 - 屏幕宽)
                ((bitmap.height * bigScale - height) / 2).toInt(),  //最大Y值
                40f.dp.toInt(), //回弹效果
                40f.dp.toInt()
            )
            /**
             * [postOnAnimation] 下一帧时执行
             * [post] 立刻执行
             *
             * [ViewCompat.postOnAnimation] 会判断版本，从而选择合适的执行方法
             */
            ViewCompat.postOnAnimation(this, this)
        }
    }

    /**
     * Fling事件后，动态刷新 Fling 动画
     */
    override fun run() {
        /*
         * 让scroll去计算自己当前位置
         * return scroller是否还在运动中
         */
        val computeScrollOffset = overScroller.computeScrollOffset()
        if (!computeScrollOffset) return //如果 Scroll 已停止运动，则直接return

        //获取scroll计算的结果
        offsetX = overScroller.currX.toFloat()
        offsetY = overScroller.currY.toFloat()
        LogUtils.tag(TAG).i("overScroller -> offsetX:$offsetX ; offsetY:$offsetY")

        invalidate()
        postOnAnimation(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event) //废除原生，使用外挂
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        originalOffsetX = (width - image_size) / 2f
        originalOffsetY = (height - image_size) / 2f

        // 宽 > 高
        if (bitmap.width.toFloat() / bitmap.height.toFloat() > width.toFloat() / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()// 最小为宽度比
            bigScale = height / bitmap.width.toFloat()// 最大为高度比
        } else {
            bigScale = width / bitmap.width.toFloat()// 最小为宽度比
            smallScale = height / bitmap.width.toFloat()// 最大为高度比
        }
    }

    override fun onDraw(canvas: Canvas) {
        /**
         * 缩小动画时，可以逐步回到0点位置
         * 放大动画时，可以逐步放大到偏移位置
         */
        canvas.translate(offsetX * scaleFraction, offsetY * scaleFraction)
        val scale = smallScale + (bigScale - smallScale) * scaleFraction
        //根据屏幕中心点放大缩小
        canvas.scale(scale, scale, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    private fun getBitmap(id: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true //只读尺寸
        BitmapFactory.decodeResource(resources, id, options)
        options.inJustDecodeBounds = false //根据尺寸读取原图
        options.inDensity = options.outWidth
        options.inTargetDensity = image_size
        return BitmapFactory.decodeResource(resources, id, options)
    }
}