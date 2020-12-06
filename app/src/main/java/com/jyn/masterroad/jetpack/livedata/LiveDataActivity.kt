package com.jyn.masterroad.jetpack.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.R
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityLiveDataBinding
import kotlinx.android.synthetic.main.activity_live_data.*

/**
 * https://juejin.im/post/6892704779781275662
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