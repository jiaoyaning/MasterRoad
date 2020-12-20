package com.jyn.masterroad.jetpack.bindingcollection

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jyn.masterroad.BR
import com.jyn.masterroad.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

class BindingCollectionModel : ViewModel() {

    var items: ObservableList<String> = ObservableArrayList()


    var itemBinding: ItemBinding<String> = ItemBinding.of(BR.binding_collection_item, R.layout.item_binding_collection)

    fun addOne(v: View) {
        items.add("测试数据:" + (items.size.plus(1)))
    }
}