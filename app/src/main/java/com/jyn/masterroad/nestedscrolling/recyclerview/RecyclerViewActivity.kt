package com.jyn.masterroad.nestedscrolling.recyclerview

import androidx.recyclerview.widget.ConcatAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityRecyclerViewBinding
import com.jyn.masterroad.nestedscrolling.recyclerview.adapter.HeaderAdapter
import com.jyn.masterroad.nestedscrolling.recyclerview.adapter.RecyclerAdapter

/*
 * 抽丝剥茧RecyclerView - 化整为零
 * https://www.jianshu.com/p/1ae2f2fcff2c
 *
 * 抽丝剥茧RecyclerView - ItemAnimator & Adapter
 * https://juejin.cn/post/6844903942938165255
 *
 * 抽丝剥茧RecyclerView - LayoutManager
 * https://juejin.cn/post/6844903924256735239
 *
 * 把LayoutManager撸出花儿来，自定义无限循环的LayoutManager
 * https://mp.weixin.qq.com/s/NAI4ZmoGPoW8UubwNHRXtA
 *
 * 巧用RecyclerView ItemDecoration 实现炫酷视差效果
 * https://mp.weixin.qq.com/s/-q4L8BNfs141RtWJa8-N7Q
 *
 * 你了解RecyclerView的布局过程吗？
 * https://mp.weixin.qq.com/s/OSuJTbFvn2RQMLVlzYgDDw
 *
 * View的有效曝光监控（上）｜RecyclerView 篇
 * https://juejin.cn/post/6844904190041407501
 *
 * 面试官：RecyclerView布局动画原理了解吗？
 * https://mp.weixin.qq.com/s/SQbgostu3_A966KygyOcTw
 *
 * 老大爷都能看懂的RecyclerView动画原理
 * https://mp.weixin.qq.com/s/Vz6GZOcxxiBi4M4Z8SDEpw
 * https://mp.weixin.qq.com/s/GN4OiZVTpGWSvdVas7LfkQ
 *
 * RecyclerView 刷新列表数据的 notifyDataSetChanged() 为什么是昂贵的?
 * https://mp.weixin.qq.com/s/dS51WxN9RkAGgdFV9_Q1Tg
 *
 * 在 RecyclerView 中使用 ListAdapter
 * https://mp.weixin.qq.com/s/WZi3cemT4bfrKfDIpjFdbg
 *
 * RecyclerView 刷新列表数据的 notifyDataSetChanged() 为什么是昂贵的?
 * https://mp.weixin.qq.com/s/dS51WxN9RkAGgdFV9_Q1Tg
 *
 * 【Android】自定义无限循环的LayoutManager
 * https://juejin.cn/post/6909363022980972552
 *
 * RecyclerView高级进阶之优雅地解决瀑布流的两个神坑
 * https://mp.weixin.qq.com/s/0frJ3kJd25jNipoa0wi-Kg
 *
 * GridLayoutManager这么用，你可能还真没尝试过
 * https://juejin.cn/post/6844903930002948110
 */
@Route(path = RoutePath.RecyclerView.path)
class RecyclerViewActivity : BaseActivity<ActivityRecyclerViewBinding>
    (R.layout.activity_recycler_view) {
    override fun initView() {
        val recyclerAdapter = RecyclerAdapter()
        val headerAdapter = HeaderAdapter()
        binding.recycler.adapter = ConcatAdapter(headerAdapter, recyclerAdapter)
    }
}