package com.jyn.masterroad.nestedscrolling.nestedscrolling

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityNestedScrolling2Binding

/*
 * AppBarLayout v26+抖动BugFix
 * https://www.jianshu.com/p/2924f32e8c22
 *
 * 实现二次吸顶效果
 * https://mp.weixin.qq.com/s/dBmpQe09F2yvTl0Xz-50uA
 */
@Route(path = RoutePath.NestedScrolling2.path)
class NestedScrolling2Activity : BaseActivity<ActivityNestedScrolling2Binding>
(R.layout.activity_nested_scrolling2) {

    override fun initView() {
        binding.recyclerViewContent.adapter = NestedScrollingAdapter()
    }
}