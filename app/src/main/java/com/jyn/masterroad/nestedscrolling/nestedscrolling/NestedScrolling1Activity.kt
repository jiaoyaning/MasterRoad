package com.jyn.masterroad.nestedscrolling.nestedscrolling

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
 *
 * Andorid 嵌套滑动机制 NestedScrollingParent2和NestedScrollingChild2 详解
 * https://juejin.cn/post/6844903960432607246
 *
 * 【透镜系列】看穿 > NestedScrolling 机制 > (极其优秀)
 * https://juejin.cn/post/6844903761060577294
 */
@Route(path = RoutePath.NestedScrolling.path)
class NestedScrolling1Activity : BaseActivity<ActivityNestedScrolling1Binding>
(R.layout.activity_nested_scrolling1) {
    /**
     * NestedScrolling1,2,3 版本有什么差异？
     *      NestedScrolling1: 5.0版本新增，在recyclerview快速滚动后触发fling动作后，recyclerview达到顶部会立即停下来，
     *                        不再会继续通过fling的惯性将顶部图片展示出来，也就是说，NestedScrollingParent和NestedScrollingChild对fling的设计并不友好。
     *      NestedScrolling2: 8.0版本新增，并添加到v4包中，弥补上个版本fling的缺陷，重构出两个Helper已方便开发者使用
     *      NestedScrolling3: AndroidX版本新增，fix上个版本的bug，并且新增了水平和垂直方向消费的距离控制
     */
    override fun initView() {
        binding.recyclerViewContent.adapter = NestedScrollingAdapter()
        binding.recyclerViewContent.layoutManager = LinearLayoutManager(this)
    }
}