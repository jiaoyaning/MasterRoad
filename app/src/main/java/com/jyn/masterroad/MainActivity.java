package com.jyn.masterroad

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.jyn.masterroad.base.RoutePath
import com.jyn.masterroad.databinding.ActivityMainBinding

/**
 * https://www.jianshu.com/p/2ee3672efb1f
 */
@Route(path = RoutePath.MAIN)
class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.root);
//        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.main_layout)
        //        activityMainBinding.handlerTest.setOnClickListener(this);
    }

    override fun onClick(v: View) {
        if (v.id == R.id.handler_test) {
            ARouter.getInstance().build(RoutePath.HANDLER).navigation()
        }
    }
}