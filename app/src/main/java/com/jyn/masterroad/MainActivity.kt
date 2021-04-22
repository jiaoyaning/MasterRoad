package com.jyn.masterroad

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.common.ARouter.goto
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityMainBinding
import com.jyn.masterroad.databinding.ItemMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/*
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
class MainActivity : BaseActivity<ActivityMainBinding>
(R.layout.activity_main) {

    private var routerList: ArrayList<MainViewModel> = MainViewModel.getRouterList()

    override fun initView() {
//        binding = ActivityMainBinding.inflate(layoutInflater) //第二种实现方式
        val mainAdapter = MainAdapter(routerList, this)
        binding.mainRecycle.adapter = mainAdapter
        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.mainRecycle.layoutManager = gridLayoutManager
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return mainAdapter.getItemViewType(position)
            }
        }
        binding.mainFab.setOnClickListener {
            Toast.makeText(this, "被点击", Toast.LENGTH_LONG).show()
        }
    }

    /*
     * 第一版参考
     * https://www.jianshu.com/p/379a8f5347de
     */
    open inner class MainAdapter(var routerList: ArrayList<MainViewModel>, var context: Context) : Adapter<MainAdapter.MainViewHolder>() {

        open inner class MainViewHolder(var binding: ItemMainBinding) : RecyclerView.ViewHolder(binding.root)

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

        override fun getItemViewType(position: Int): Int {
            if (routerList[position].span == 1)
                return 2
            else if (routerList[position].span == 2)
                return 1
            return 2
        }
    }
}