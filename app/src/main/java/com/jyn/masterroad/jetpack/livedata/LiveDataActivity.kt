package com.jyn.masterroad.jetpack.livedata

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.databinding.ActivityLiveDataBinding
import kotlinx.android.synthetic.main.activity_live_data.*

/**
 * https://juejin.im/post/6892704779781275662
 *
 * Android官方架构组件LiveData: 观察者模式领域二三事
 * https://juejin.cn/post/6844903748574117901
 *
 * https://yutiantina.github.io/2019/09/20/%E5%9F%BA%E4%BA%8ELiveData%E5%AE%9E%E7%8E%B0%E4%BA%8B%E4%BB%B6%E6%80%BB%E7%BA%BF%E6%80%9D%E8%B7%AF%E5%92%8C%E6%96%B9%E6%A1%88/
 * 基于LiveData实现事件总线思路和方案
 */
@Route(path = RoutePath.LiveData.path)
class LiveDataActivity : AppCompatActivity() {

    lateinit var dataBinding: ActivityLiveDataBinding;
    lateinit var viewModel: LiveDataTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //获取dataBinding对象
        dataBinding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)
        dataBinding.lifecycleOwner = this

        //获取viewModel对象
        viewModel = ViewModelProvider(this).get(LiveDataTestViewModel::class.java)
        dataBinding.model = viewModel

        /**
         * 点击方法设置之，在xml中绑定OnClickListener接口
         */
        dataBinding.onClick = View.OnClickListener {
            viewModel.subtract()
        }

        viewModel.num.observe(this, Observer {
            LogUtils.tag("main").i("Observer -> num改变了$it")
            tv_num.text = it.toString()
        })

        viewModel.numString?.observe(this, Observer {
            LogUtils.tag("main").i("Observer -> numString改变了$it")
        })
    }
}