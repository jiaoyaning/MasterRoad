package com.jyn.masterroad.nestedscrolling

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityNestedScrolling2Binding
import kotlinx.android.synthetic.main.activity_nested_scrolling1.*

@Route(path = RoutePath.NestedScrolling2.path)
class NestedScrolling2Activity : BaseActivity<ActivityNestedScrolling2Binding>
(R.layout.activity_nested_scrolling2) {

    override fun initView() {
        binding.recyclerViewContent.adapter = NestedScrollingAdapter()
    }
}