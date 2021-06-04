package com.jyn.masterroad.jetpack.bindingcollection

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.jyn.masterroad.BR
import com.jyn.masterroad.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

class BindingCollectionModel : ViewModel() {

    var items: ObservableList<String> = ObservableArrayList()

    @SuppressLint("StaticFieldLeak")
    var recyclerView: RecyclerView? = null

    var itemBinding: ItemBinding<String> = ItemBinding
            .of(BR.binding_collection_item, R.layout.item_binding_collection)

    fun addOne(v: View) {
        items.add("测试数据:" + (items.size.plus(1)))
        recyclerView?.adapter?.itemCount?.minus(1)?.let { recyclerView?.scrollToPosition(it) } //滑动到最后一行
    }
}