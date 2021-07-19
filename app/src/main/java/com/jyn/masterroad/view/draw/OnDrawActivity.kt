package com.jyn.masterroad.view.draw

import android.view.Choreographer
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityOnDrawBinding

/**
 * 面试官：View.post() 为什么能够获取到 View 的宽高？
 * https://mp.weixin.qq.com/s/GWB--a43N6I8Fl_81-Ltqw
 *
 * 【从入门到放弃】android布局优化深入解析
 * https://juejin.cn/post/6950791235413999652
 *
 * Android 资源加载源码分析一
 * https://mp.weixin.qq.com/s/RrMj_LJnHNackrVGOgD3TA
 */
class OnDrawActivity : BaseActivity<ActivityOnDrawBinding>(R.layout.activity_on_draw) {

    private fun fpsDetection() {
        var starTime: Long = System.nanoTime()
        Choreographer.getInstance().postFrameCallback {
            LogUtils
                .tag("FPS")
                .i("starTime:$starTime ; frameTimeNanos:$it ; frameDueTime:${(it - starTime) / 1000000}")
            starTime = it
        }
    }
}