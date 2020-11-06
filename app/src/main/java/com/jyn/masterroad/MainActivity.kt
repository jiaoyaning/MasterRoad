package com.jyn.masterroad

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
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityMainBinding
import com.jyn.masterroad.databinding.ItemMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * https://www.jianshu.com/p/2ee3672efb1f
 *
 * RecycleView列表绑定
 * https://blog.csdn.net/yu75567218/article/details/87860020
 */
@Route(path = RoutePath.Main.path)
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    private var routerList: ArrayList<MainViewModel.RouterList> = RoutePath.getRouterList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.mainViewModel = MainViewModel()

        main_recycle.layoutManager = LinearLayoutManager(this)
        main_recycle.adapter = MainAdapter(routerList, this);
    }

    /**
     * https://www.jianshu.com/p/379a8f5347de
     */
    class MainAdapter(var routerList: ArrayList<MainViewModel.RouterList>, var context: Context) : Adapter<MainAdapter.MainViewHolder>() {

        class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView: View = layoutInflater.inflate(R.layout.item_main, parent, false)
            return MainViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemMainBinding = ItemMainBinding.bind(holder.itemView);
            itemMainBinding.list = routerList[position]
            itemMainBinding.executePendingBindings()
        }

        override fun getItemCount(): Int {
            return routerList.size
        }
    }
}