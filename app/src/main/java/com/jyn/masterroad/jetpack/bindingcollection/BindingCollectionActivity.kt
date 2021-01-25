package com.jyn.masterroad.jetpack.bindingcollection

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityBindingCollectionBinding

/**
 * 库位置:
 * https://github.com/evant/binding-collection-adapter
 */
@Route(path = RoutePath.BindingCollection.path)
class BindingCollectionActivity : AppCompatActivity() {

    lateinit var binding: ActivityBindingCollectionBinding
    lateinit var viewmodel: BindingCollectionModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindingCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewmodel = ViewModelProvider(this).get(BindingCollectionModel::class.java)
        viewmodel.recyclerView = binding.bindingCollectionRecycler;
        binding.viewModel = viewmodel
    }
}