package com.jyn.masterroad.view.draw

import android.view.Choreographer
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityOnDrawBinding

/*
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
class OnDrawActivity : BaseActivity<ActivityOnDrawBinding>(R.layout.activity_on_draw) {

    private val drawView by lazy { DrawView(this) }
    private val pathView by lazy { PathView(this) }
    private val pathEffectView by lazy { PathEffectView(this) }
    private val xfermodeView by lazy { XfermodeView(this) }

    override fun initView() {
        binding.click = onClickListener
    }

    private val onClickListener = View.OnClickListener {
        switchView(
            when (it.id) {
                R.id.btn_draw -> drawView
                R.id.btn_path -> pathView
                R.id.btn_PathEffect -> pathEffectView
                R.id.btn_xfermode -> xfermodeView
                else -> drawView
            }
        )
    }

    private fun switchView(view: View) {
        binding.boxLayout.apply {
            removeAllViews()
            view.layoutParams = RelativeLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
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