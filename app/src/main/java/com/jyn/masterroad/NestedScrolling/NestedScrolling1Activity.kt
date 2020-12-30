package com.jyn.masterroad.NestedScrolling

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ItemMainBinding
import kotlinx.android.synthetic.main.activity_nested_scrolling1.*

/**
 * https://juejin.cn/post/6844904184915951630#heading-8
 *
 * https://juejin.cn/post/6844903761060577294
 */
@Route(path = RoutePath.NestedScrolling.path)
class NestedScrolling1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nested_scrolling1)
        recycler_view_content.adapter = NestedScrollingAdapter()
        recycler_view_content.layoutManager = LinearLayoutManager(this)
    }

    class NestedScrollingAdapter : RecyclerView.Adapter<NestedScrollingAdapter.NestedScrollingViewHolder>() {
        class NestedScrollingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedScrollingViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val itemView: View = layoutInflater.inflate(R.layout.item_main, parent, false)
            return NestedScrollingViewHolder(itemView)
        }

        override fun getItemCount(): Int {
            return 30
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: NestedScrollingViewHolder, position: Int) {
            holder.itemView.findViewById<Button>(R.id.main_recycle_item_btn).text = position.toString()
        }
    }
}