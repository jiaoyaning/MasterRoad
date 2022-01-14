package com.jyn.masterroad

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.goto
import com.jyn.common.Utils.TimeUtils
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMainBinding
import com.jyn.masterroad.databinding.ItemMainBinding

/*
 * https://www.jianshu.com/p/c69b0e4e18f1
 *
 * 一个 Android MVVM 组件化架构框架
 * https://juejin.cn/post/6866628586414997512
 *
 * 几个场景下Activity的生命周期变化
 * https://mp.weixin.qq.com/s/yrxYcK42QaE0sGgo7oEGjA
 *
 * Android 12：全新的App启动动画
 * https://mp.weixin.qq.com/s/AJZ7eM_GUaqOb9AuaLvrjA
 *
 * Carson带你学Android：BroadcastReceiver史上最全面解析
 * https://www.jianshu.com/p/ca3d87a4cdf3
 *
 * 手撸网易云进阶课程-性能优化之NDK高效加载GIF
 * https://juejin.cn/post/6844904008797126664
 */
class MainActivity : BaseActivity<ActivityMainBinding>
(R.layout.activity_main) {

    private var routerList: ArrayList<MainViewModel> = MainViewModel.getRouterList()

    override fun initData() {
        val coldStartTime: Long = TimeUtils.getTimeCalculate(TimeUtils.COLD_START)
        // 这里记录的TimeUtils.coldStartTime是指Application启动的时间，最终的冷启动时间等于Application启动时间+热启动时间
        TimeUtils.sColdStartTime = if (coldStartTime > 0) coldStartTime else 0
        LogUtils.tag("MainActivity").i("冷启动时间：${TimeUtils.sColdStartTime}")
    }

    override fun initView() {
        // binding = ActivityMainBinding.inflate(layoutInflater) //第二种实现方式
        val mainAdapter = MainAdapter(routerList, this)
        binding.mainRecycle.adapter = mainAdapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.mainRecycle.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) = mainAdapter.getItemViewType(position)
        }
    }

    /*
     * 第一版参考
     * https://www.jianshu.com/p/379a8f5347de
     */
    open inner class MainAdapter(
            private var routerList: ArrayList<MainViewModel>, var context: Context
    ) : Adapter<MainAdapter.MainViewHolder>() {

        open inner class MainViewHolder(var binding: ItemMainBinding) :
                RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val binding =
                    ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemMainBinding = holder.binding
            itemMainBinding.mainRecycleItemBtn.setOnClickListener {
                routerList[position].path.get()?.let(::goto)
            }
            itemMainBinding.list = routerList[position]
            itemMainBinding.executePendingBindings()
        }

        override fun getItemCount(): Int = routerList.size

        override fun getItemViewType(position: Int): Int =
                when (routerList[position].span) {
                    1 -> 2
                    2 -> 1
                    else -> 2
                }
    }
}