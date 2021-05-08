package com.jyn.masterroad.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/*
 * 封装DataBinding-新写法
 * https://mp.weixin.qq.com/s/2os4z9lhuahYPbB-UtPmcg
 */
abstract class BaseActivity<dataBinding : ViewDataBinding>(var id: Int = 0) : AppCompatActivity() {

    val binding: dataBinding by lazy {
        DataBindingUtil.setContentView<dataBinding>(this, id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initData()
        initView()
    }

    open fun initData() {}
    open fun initView() {}

    /*
     * 获取ViewModel实例
     *
     * reified : 由于java泛型是伪泛型,为了兼容java1.5以前的版本,java运行时,会泛型擦除 会擦除为泛型上界,
     * 如果没有泛型上界会擦除为Object,所以jvm在程序运行时是不知道泛型的真实类型,
     * reified 能保证运行时依然能拿到泛型的具体类型.(当前只限制支持内联函数可用)
     */
    inline fun <reified T : ViewModel> createVM(): T {
        return ViewModelProvider(this).get(T::class.java)
    }
}