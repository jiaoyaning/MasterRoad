package com.jyn.masterroad.view.layout

import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatViewInflater
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
 *
 * 手把手讲解 Android hook技术实现一键换肤
 * https://www.jianshu.com/p/4c8d46f58c4f
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
     *
     *
     *  measureChildWithMargins()
     *  measureChildren()
     *
     *  requestLayout 只会触发 measure 和 layout，invalidate 只会触发 draw。
     *
     *
     *
     *  [LayoutInflater.setFactory]
     *  [LayoutInflater.setFactory2]
     *  这两个的差别，只在于 setFactory2 多了一个 parent 字段
     *
     *  [AppCompatViewInflater.createView]
     */

    override fun initView() {

    }

    /**
     * 子线程更新UI
     */
}