package com.jyn.masterroad.utils.aop

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.apt.APTBindView
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.reflect.ReflectBind
import com.jyn.reflect.ReflectBindView

@Route(path = RoutePath.APT.path)
@SuppressLint("NonConstantResourceId")
class AOPActivity : AppCompatActivity() {

    @ReflectBindView(R.id.btn_reflect_1)
    var btn_reflect_1: Button? = null

    @APTBindView(R.id.btn_apt_1)
    var btn_apt_1: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)
        ReflectBind.bind(this)


        btn_reflect_1?.setOnClickListener {
            Toast.makeText(this, "ReflectBindView 测试", Toast.LENGTH_SHORT).show()
        }

        btn_apt_1?.setOnClickListener {
            Toast.makeText(this, "APTBind 测试", Toast.LENGTH_SHORT).show()
        }
    }
}