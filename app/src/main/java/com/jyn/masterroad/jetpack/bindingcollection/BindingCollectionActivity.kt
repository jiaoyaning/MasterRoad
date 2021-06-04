package com.jyn.masterroad.jetpack.bindingcollection

import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.masterroad.base.BaseActivity
import com.jyn.masterroad.databinding.ActivityBindingCollectionBinding

/**
 * 库位置:
 * https://github.com/evant/binding-collection-adapter
 */
@Route(path = RoutePath.BindingCollection.path)
class BindingCollectionActivity : BaseActivity<ActivityBindingCollectionBinding>
    (R.layout.activity_binding_collection) {

    lateinit var viewmodel: BindingCollectionModel

    override fun initData() {
        viewmodel = createVM()
        viewmodel.recyclerView = binding.bindingCollectionRecycler
        binding.viewModel = viewmodel
    }
}