package com.jyn.masterroad.view.draw

import android.view.Choreographer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityOnDrawBinding
import com.jyn.masterroad.view.draw.view.*

/*
 * HenCoder Android 自定义 View 1-5: 绘制顺序
 * https://juejin.cn/post/6844903491031269383
 *
 * 面试官：View.post() 为什么能够获取到 View 的宽高？
 * https://mp.weixin.qq.com/s/GWB--a43N6I8Fl_81-Ltqw
 *
 * 【从入门到放弃】android布局优化深入解析
 * https://juejin.cn/post/6950791235413999652
 *
 * Android 资源加载源码分析一
 * https://mp.weixin.qq.com/s/RrM======j_LJnHNackrVGOgD3TA
 *
 * 通俗易懂，Android视图系统的设计与实现
 * https://mp.weixin.qq.com/s/F_EAB39JkdHfQwnpcxhDsA
 *
 * Android 的 Window 如何理解？Dialog 到底是不是子窗口？
 * https://mp.weixin.qq.com/s/hJcqsJhoGjsxOjXG_HyZxw
 *
 * Android 视图系统的设计与实现 | 通俗易懂
 * https://mp.weixin.qq.com/s/aFzPl6VcBNXujxw12GkT_w
 */
@Route(path = RoutePath.Draw.path)
class OnDrawActivity : BaseActivity<ActivityOnDrawBinding>
    (R.layout.activity_on_draw) {
    /**
     * 一个完整的绘制过程会依次绘制以下几个内容：
     *      1.背景
     *      2.主体（onDraw()）
     *      3.子 View（dispatchDraw()）
     *      4.滑动边缘渐变和滑动条
     *      5.前景
     */
    private val drawView by lazy { CanvasDrawXXXView(this) }
    private val paintView by lazy { PaintView(this) }
    private val pathView by lazy { PathView(this) }
    private val pathEffectView by lazy { PaintSetPathEffectView(this) }
    private val paintSetXfermodeView by lazy { PaintSetXfermodeView(this) }
    private val drawTextView by lazy { DrawTextView(this) }
    private val canvasView by lazy { CanvasView(this) }
    private val drawableView by lazy { DrawableView(this) }

    override fun initView() {
        binding.click = View.OnClickListener {
            switchView(
                when (it.id) {
                    R.id.btn_draw -> drawView
                    R.id.btn_paint -> paintView
                    R.id.btn_path -> pathView
                    R.id.btn_PathEffect -> pathEffectView
                    R.id.btn_xfermode -> paintSetXfermodeView
                    R.id.btn_draw_text -> drawTextView
                    R.id.btn_canvas_view -> canvasView
                    R.id.btn_drawable -> drawableView
                    else -> drawView
                }
            )
        }
    }

    private fun switchView(view: View) {
        binding.boxLayout.apply {
            removeAllViews()
            addView(view)
        }
    }

    private fun fpsDetection() {
        var starTime: Long = System.nanoTime()
        Choreographer.getInstance().postFrameCallback {
            LogUtils.tag("FPS")
                .i("starTime:$starTime ; frameTimeNanos:$it ; frameDueTime:${(it - starTime) / 1000000}")
            starTime = it
        }
    }
}