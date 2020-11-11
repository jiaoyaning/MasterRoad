package com.jyn.masterroad.jetpack.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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

    var i = 0;

    lateinit var dataBinding: ActivityLiveDataBinding;
    lateinit var viewModel: LiveDataTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = ActivityLiveDataBinding.inflate(layoutInflater)
        setContentView(dataBinding.root)

        viewModel = ViewModelProvider(this).get(LiveDataTestViewModel::class.java)
        dataBinding.model = viewModel

        dataBinding.onClick = View.OnClickListener {
            LogUtils.tag("main").i("我被点击了")
            viewModel.subtract()
        }

        viewModel.num.observe(this, Observer {
            LogUtils.tag("main").i("我改变了$it")
            tv_num.text = it.toString()
        })
    }
}