package com.jyn.masterroad

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.ARouter.goto
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMainBinding
import com.jyn.masterroad.databinding.ItemMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://www.jianshu.com/p/2ee3672efb1f
 *
 * RecycleView列表绑定
 * https://blog.csdn.net/yu75567218/article/details/87860020
 *
 * item 直接绑定方式
 * http://www.voidcn.com/article/p-zannxayh-brz.html
 *
 * DataBinding 教程
 * https://blog.csdn.net/guiying712/article/details/80206037
 *
 * 自定义databinding双向绑定
 * https://www.jianshu.com/p/4a2ae008bae5
 *
 * https://www.jianshu.com/p/c69b0e4e18f1
 */
@Route(path = RoutePath.Main.path)
class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var routerList: ArrayList<MainViewModel> = MainViewModel.getRouterList()

    override fun getLayoutId() = R.layout.activity_main

    override fun init() {
//        binding = ActivityMainBinding.inflate(layoutInflater) //第二种实现方式
        main_recycle.adapter = MainAdapter(routerList, this)
    }

    /**
     * 第一版参考
     * https://www.jianshu.com/p/379a8f5347de
     */
    inner class MainAdapter(var routerList: ArrayList<MainViewModel>, var context: Context) : Adapter<MainAdapter.MainViewHolder>() {

        inner class MainViewHolder(var binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val binding = ItemMainBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MainViewHolder(binding)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemMainBinding = holder.binding
            itemMainBinding.mainRecycleItemBtn.setOnClickListener {
                routerList[position].path.get()?.let { it1 -> goto(it1) }
            }
            itemMainBinding.list = routerList[position]
            itemMainBinding.executePendingBindings()
        }

        override fun getItemCount(): Int = routerList.size
    }
}