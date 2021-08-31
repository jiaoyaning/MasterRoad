package com.jyn.masterroad.view.layout

import android.os.Bundle
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
 */
@Route(path = RoutePath.Layout.path)
class LayoutActivity : BaseActivity<ActivityLayoutBinding>
    (R.layout.activity_layout) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        binding.boxLayout.viewTreeObserver.addOnGlobalLayoutListener {

        }
    }

    /**
     * 子线程更新UI
     */
}