package com.jyn.masterroad.view.layout

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityLayoutBinding

/*
 * HenCoder UI 部分 2-1 布局基础
 * https://juejin.cn/post/6844903508760625160
 * HenCoder UI 部分 2-2 全新定义 View 的尺寸
 * https://juejin.cn/post/6844903542491201544
 * HenCoder UI 部分 2-3 定制 Layout 的内部布局
 * https://juejin.cn/post/6844903550745575432
 *
 * ViewRootImpl介绍
 * https://www.jianshu.com/p/0df66d277671
 *
 * Dialog、Toast的Window和ViewRootImpl
 * https://blog.csdn.net/stven_king/article/details/78775211
 *
 * 面试官：View.post() 为什么能够获取到 View 的宽高？ (优秀)
 * https://mp.weixin.qq.com/s/GWB--a43N6I8Fl_81-Ltqw
 */
@Route(path = RoutePath.Layout.path)
class LayoutActivity : BaseActivity<ActivityLayoutBinding>
    (R.layout.activity_layout) {
    /**
     * 自定义layout的步骤
     *   1.重写onMeasure()
     *        遍历每个子View，测量子View
     *           测量完成后，得出子view的实际位置和尺寸，并暂时保存
     *           有些子view可能需要重新测量
     *        测量出所有子view的位置和尺寸后，计算出自己的尺寸，并用setMeasuredDimension(width,height)保存
     *   2.重写onLayout()
     *     遍历每个子view，调用他们的layout()方法来将位置和尺寸传给它们
     */

    override fun initView() {
        binding.boxLayout.viewTreeObserver.addOnGlobalLayoutListener {

        }
    }

    /**
     * 子线程更新UI
     */
}