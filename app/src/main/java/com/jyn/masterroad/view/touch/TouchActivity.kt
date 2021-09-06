package com.jyn.masterroad.view.touch

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityTouchBinding
import com.jyn.masterroad.view.touch.view.MultiTouchView1
import com.jyn.masterroad.view.touch.view.MultiTouchView2
import com.jyn.masterroad.view.touch.view.ScalableImageView
import com.jyn.masterroad.view.touch.view.TouchLayout

/*
 * 【带着问题学】android事件分发8连问
 * https://juejin.cn/post/6965484155660402702
 *
 * 快速了解Android6.0系统触摸事件工作原理——InputManagerService
 * https://blog.csdn.net/warticles/article/details/81035943
 *
 * 反思 | Android 事件分发的“另一面”
 * https://mp.weixin.qq.com/s/27AL_nuIB6SR4lcB5S8B0Q
 */
@Route(path = RoutePath.Touch.path)
class TouchActivity : BaseActivity<ActivityTouchBinding>
(R.layout.activity_touch) {

    private val multiTouchView1 by lazy { MultiTouchView1(this) }
    private val multiTouchView2 by lazy { MultiTouchView2(this) }
    private val scalableImageView by lazy { ScalableImageView(this) }
    private val touchLayout by lazy { TouchLayout(this) }

    override fun initView() {
        binding.click = View.OnClickListener {
            switchView(when (it.id) {
                R.id.btn_multi_touch1 -> multiTouchView1
                R.id.btn_multi_touch2 -> multiTouchView2
                R.id.btn_scalable -> scalableImageView
                R.id.btn_touch_layout -> touchLayout
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