package com.jyn.masterroad.view.touch

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityTouchBinding
import com.jyn.masterroad.view.touch.darg.DragHelperView
import com.jyn.masterroad.view.touch.darg.DragListenerView
import com.jyn.masterroad.view.touch.view.*

/*
 * 【带着问题学】android事件分发8连问
 * https://juejin.cn/post/6965484155660402702
 *
 * 快速了解Android6.0系统触摸事件工作原理——InputManagerService
 * https://blog.csdn.net/warticles/article/details/81035943
 *
 * 反思 | Android 事件分发的“另一面”
 * https://mp.weixin.qq.com/s/27AL_nuIB6SR4lcB5S8B0Q
 *
 * MotionEvent的getAction、getActionMask和getActionIndex的区别
 * https://www.jianshu.com/p/a62728297b1e
 */
@Route(path = RoutePath.Touch.path)
class TouchActivity : BaseActivity<ActivityTouchBinding>
(R.layout.activity_touch) {

    private val multiTouchView1 by lazy { MultiTouchView1(this) }
    private val multiTouchView2 by lazy { MultiTouchView2(this) }
    private val multiTouchView3 by lazy { MultiTouchView3(this) }
    private val scalableImageView by lazy { ScalableImageView(this) }
    private val touchLayout by lazy { TouchLayout(this) }
    private val twoPagerView by lazy { TwoPagerView(this) }
    private val dragHelperView by lazy { DragHelperView(this) }
    private val dragListenerView by lazy { DragListenerView(this) }

    override fun initView() {
        binding.click = View.OnClickListener {
            switchView(when (it.id) {
                R.id.btn_multi_touch1 -> multiTouchView1
                R.id.btn_multi_touch2 -> multiTouchView2
                R.id.btn_multi_touch3 -> multiTouchView3
                R.id.btn_scalable -> scalableImageView
                R.id.btn_touch_layout -> touchLayout
                R.id.btn_two_pager -> twoPagerView
                R.id.btn_drag_helper -> dragHelperView
                R.id.btn_drag_listener -> dragListenerView
                else -> scalableImageView
            })
        }
    }

    private fun switchView(view: View) {
        binding.boxLayout.apply {
            removeAllViews()
            addView(view)
        }
    }
}