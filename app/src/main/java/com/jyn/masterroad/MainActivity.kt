package com.jyn.masterroad

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.utils.goto
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityMainBinding
import com.jyn.masterroad.databinding.ItemMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_main.view.*


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
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private var routerList: ArrayList<MainViewModel.RouterList> = RoutePath.getRouterList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        main_recycle.layoutManager = LinearLayoutManager(this)
        main_recycle.adapter = MainAdapter(routerList, this)

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        //最大分配内存
        val memory = activityManager.memoryClass
        LogUtils.tag("main").i("最大可用内存: $memory")


        //应用程序最大可用内存
        val maxMemory = Runtime.getRuntime().maxMemory().toInt() / 1024 / 1024
        //应用程序已被分配的内存
        val totalMemory = Runtime.getRuntime().totalMemory().toInt() / 1024 / 1024.toLong()
        //应用程序已获得内存中未使用内存
        val freeMemory = Runtime.getRuntime().freeMemory().toInt() / 1024 / 1024.toLong()

        LogUtils.tag("main").i("maxMemory : $maxMemory")
        LogUtils.tag("main").i("totalMemory : $totalMemory")
        LogUtils.tag("main").i("freeMemory : $freeMemory")
    }

    /**
     * 第一版参考
     * https://www.jianshu.com/p/379a8f5347de
     */
    class MainAdapter(var routerList: ArrayList<MainViewModel.RouterList>, var context: Context) : Adapter<MainAdapter.MainViewHolder>() {
        var i = 0;

        class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView: View = layoutInflater.inflate(R.layout.item_main, parent, false)
            return MainViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemMainBinding = ItemMainBinding.bind(holder.itemView);
            holder
                    .itemView
                    .main_recycle_item_btn
                    .setOnClickListener {
                        routerList[position].path.get()?.let { it1 ->
                            goto(it1)

                            //可以动态修改数据
//                            routerList[position].name.set(routerList[position].name.get() + ++i)
//                            itemMainBinding.executePendingBindings()
                        }
                    }
            itemMainBinding.list = routerList[position]
            itemMainBinding.executePendingBindings()
        }

        override fun getItemCount(): Int {
            return routerList.size
        }
    }
}