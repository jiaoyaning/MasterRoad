package com.jyn.masterroad.view.animation

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityAnimationBinding
import com.jyn.masterroad.view.animation.view.ObjectAnimatorView
import com.jyn.masterroad.view.animation.view.ViewPropertyAnimatorView

/*
 * https://juejin.cn/post/6844903494256689165
 * HenCoder Android 自定义 View 1-6： 属性动画（上手篇）
 *
 * https://juejin.cn/post/6844903494940360711
 * 【HenCoder Android 开发进阶】自定义 View 1-7：属性动画（进阶篇）
 *
 * Android补间动画原理分析
 * https://www.jianshu.com/p/93abf14de14d
 * https://cloud.tencent.com/developer/article/1330040
 *
 */
@Route(path = RoutePath.Animation.path)
class AnimationActivity : BaseActivity<ActivityAnimationBinding>
    (R.layout.activity_animation) {

    /**
     * 一、帧动画
     * 二、补间动画
     *     补间动画有4种类型，平移，旋转，透明度，缩放。补间动画只能作用在view上，并且不会改变View的属性,只会改变显示效果.
     * 三、属性动画
     */
    private val viewPropertyAnimatorView by lazy { ViewPropertyAnimatorView(this) }
    private val objectAnimatorView by lazy { ObjectAnimatorView(this) }

    override fun initView() {
        binding.click = View.OnClickListener {
            switchView(
                when (it.id) {
                    R.id.btn_ViewPropertyAnimator -> viewPropertyAnimatorView
                    R.id.btn_ObjectAnimator -> objectAnimatorView
                    else -> viewPropertyAnimatorView
                }
            )
        }
    }

    private fun switchView(view: View) {
        binding.boxLayout.apply {
            removeAllViews()
            view.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT)
            addView(view)
        }
    }
}