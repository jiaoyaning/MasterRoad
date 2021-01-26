package com.jyn.masterroad.RecyclerView

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jyn.masterroad.R

/**
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
 */
class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
    }
}