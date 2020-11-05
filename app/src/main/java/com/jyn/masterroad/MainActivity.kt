package com.jyn.masterroad

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityMainBinding

/**
 * https://www.jianshu.com/p/2ee3672efb1f
 *
 * RecycleView列表绑定
 * https://blog.csdn.net/yu75567218/article/details/87860020
 */
@Route(path = RoutePath.MAIN)
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        activityMainBinding.mainViewModel = MainViewModel()
    }

    /**
     * https://www.jianshu.com/p/379a8f5347de
     */
    class MainAdapter(routerList: MainViewModel.RouterList) : Adapter<MainAdapter.MainViewHolder>() {

        class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {

        }

        override fun getItemCount(): Int {
        }

        override fun onBindViewHolder(holderMain: MainViewHolder, position: Int) {
        }
    }
}