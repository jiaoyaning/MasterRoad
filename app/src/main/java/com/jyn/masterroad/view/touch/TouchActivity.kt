package com.jyn.masterroad.view.touch

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityTouchBinding
import com.jyn.masterroad.view.touch.darg.DragHelperLayout
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

    /**
     * OnDragListener 和 ViewDragHelper 使用场景区别？
     *   OnDragListener 在API 11时加入的，它的重点在于内容的移动而不是控件的移动，可以不需要自己自定义View，
     *                  只要实现 setOnDragListener() 即可，系统会帮你生成一个可拖拽的像素，该图像像素和界面是无关的，
     *                  而且可以附加拖拽时的数据，能够跨进程传数据
     *   ViewDragHelper 是一个工具类，用户要拖动某个ViewGroup里面的某个子View时使用（tryCaptureView() 判断一个或多个子View)，
     *                  它需要自定义View，需要让 ViewDragHelper 接管ViewGroup的触摸事件，可以实时的拖拽移动手动修改子View的位置，使用 ViewDragHelper 是在界面的操作
     */
    override fun initView() {
        binding.click = View.OnClickListener {
            switchView(when (it.id) {
                R.id.btn_multi_touch1 -> MultiTouchView1(this)
                R.id.btn_multi_touch2 -> MultiTouchView2(this)
                R.id.btn_multi_touch3 -> MultiTouchView3(this)
                R.id.btn_scalable -> ScalableImageView(this)
                R.id.btn_touch_layout -> TouchLayout(this)
                R.id.btn_two_pager -> TwoPagerView(this)
                R.id.btn_drag_helper -> DragHelperView(this)
                R.id.btn_drag_listener -> DragListenerView(this)
                R.id.btn_drag_helper_layout -> DragHelperLayout(this)
                else -> ScalableImageView(this)
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