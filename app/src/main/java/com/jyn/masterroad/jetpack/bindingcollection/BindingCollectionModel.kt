package com.jyn.masterroad.jetpack.bindingcollection

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.jyn.masterroad.BR
import com.jyn.masterroad.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

class BindingCollectionModel : ViewModel() {

//    var items: ObservableList<String> = ObservableArrayList()

    var items: MutableLiveData<MutableList<String>> = MutableLiveData(mutableListOf())

    @SuppressLint("StaticFieldLeak")
    var recyclerView: RecyclerView? = null

    var itemBinding: ItemBinding<String> = ItemBinding
            .of(BR.binding_collection_item, R.layout.item_binding_collection)

    fun addOne(v: View) {
        val value: MutableList<String>? = items.value?.apply {
            add("测试数据:" + (size.plus(1)))
        }

        items.postValue(value)

        recyclerView?.adapter?.itemCount?.minus(1)
                ?.let { recyclerView?.scrollToPosition(it) } //滑动到最后一行
    }
}