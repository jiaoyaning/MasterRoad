package com.jyn.masterroad.nestedscrolling

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.jyn.masterroad.R

class NestedScrollingAdapter : RecyclerView.Adapter<NestedScrollingAdapter.NestedScrollingViewHolder>()  {
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