package com.jyn.masterroad

import android.app.ActivityManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ObservableField
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.utils.ARouter.RoutePath
import com.jyn.common.utils.ARouter.goto
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
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private var routerList: ArrayList<MainViewModel> = getRouterList()

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
     * 通过反射创建Router列表
     */
    private fun getRouterList(): ArrayList<MainViewModel> {
        val routerList = arrayListOf<MainViewModel>()
        val classes = RoutePath.javaClass.classes
        for (i in classes.indices) {
            val name: String = classes[i].getField("name")[this] as String
            val path: String = classes[i].getField("path")[this] as String
            routerList.add(MainViewModel(ObservableField(name), ObservableField(path)))
        }
        return routerList
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

        override fun getItemCount(): Int {
            return routerList.size
        }
    }
}