package com.jyn.masterroad.jetpack.bindingcollection

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jyn.masterroad.BR
import com.jyn.masterroad.R
import me.tatarka.bindingcollectionadapter2.ItemBinding

class BindingCollectionModel : ViewModel() {

    var items: MutableLiveData<MutableList<String>>? = null
        get() {
            if (field == null) {
                field = MutableLiveData(mutableListOf("测试数据:1", "测试数据:2", "测试数据:3", "测试数据:4", "测试数据:5"))
            }
            return field
        }

    var itemBinding: ItemBinding<String> = ItemBinding.of(BR.binding_collection_item, R.layout.item_binding_collection)

    fun addOne(v: View) {
        items?.let {
            val tmp = it.value
            tmp?.add("测试数据:" + (it.value?.size?.plus(1)))
            it.value = tmp
        }
    }
}