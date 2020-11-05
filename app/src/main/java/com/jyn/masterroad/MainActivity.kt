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
@Route(path = RoutePath.MAIN)
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    var routerList = arrayListOf<MainViewModel.RouterList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.mainViewModel = MainViewModel()

        main_recycle.layoutManager = LinearLayoutManager(this)
        main_recycle.adapter = MainAdapter(routerList, this);
    }

    init {
        routerList.add(MainViewModel.RouterList("handler", RoutePath.HANDLER))
    }

    /**
     * https://www.jianshu.com/p/379a8f5347de
     */
    class MainAdapter(var routerList: ArrayList<MainViewModel.RouterList>, var context: Context) : Adapter<MainAdapter.MainViewHolder>() {

        class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
            val layoutInflater = LayoutInflater.from(context)
            val itemMainBinding = ItemMainBinding.inflate(layoutInflater)
            return MainViewHolder(itemMainBinding.root)
        }

        override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
            val itemMainBinding = ItemMainBinding.bind(holder.itemView)
            itemMainBinding.list = routerList[0]
            itemMainBinding.executePendingBindings()
        }

        override fun getItemCount(): Int {
            return 10
        }
    }
}