package com.jyn.masterroad.nestedscrolling

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityNestedScrolling1Binding

/*
 * 基础：
 * https://juejin.cn/post/6844904184911773709
 *
 * https://juejin.cn/post/6844904184915951630#heading-8
 *
 * https://juejin.cn/post/6844903761060577294
 *
 * 高级UI必知必会之CoordinatorLayout源码解析及Behavior解耦思想分享 TODO
 * https://crazymo.blog.csdn.net/article/details/103191241
 *
 * Android CoordinatorLayout之自定义Behavior
 * https://www.jianshu.com/p/b987fad8fcb4
 *
 * AppBarLayout v26+抖动BugFix
 * https://www.jianshu.com/p/2924f32e8c22
 */
@Route(path = RoutePath.NestedScrolling.path)
class NestedScrolling1Activity : BaseActivity<ActivityNestedScrolling1Binding>
(R.layout.activity_nested_scrolling1) {
    override fun initView() {
        binding.recyclerViewContent.adapter = NestedScrollingAdapter()
        binding.recyclerViewContent.layoutManager = LinearLayoutManager(this)
    }
}