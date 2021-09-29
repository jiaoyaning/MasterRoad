package com.jyn.masterroad.utils.aop

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.alibaba.android.arouter.facade.annotation.Route
import com.jyn.apt_annotation.APTBindView
import com.jyn.common.ARouter.RoutePath
import com.jyn.masterroad.R
import com.jyn.reflect.ReflectBind
import com.jyn.reflect.ReflectBindView

@Route(path = RoutePath.APT.path)
@SuppressLint("NonConstantResourceId")
class AOPActivity : AppCompatActivity() {
    /*
     * 一、通过反射的方式来实现view的绑定
     *  优点：简单
     *  缺点：运行时反射，效率太差
     */
    @ReflectBindView(R.id.aop_btn_reflect)
    var btnReflect: Button? = null

    /*
     * 二、通过APT来生成代码，实现编译期生成代码，运行期直接执行
     *  [APT-annotation] : 注解类 Java库 因为主库和生成库都需要用到该注解，故而抽取成单独的一个库
     *  [APT-processor]  : 代码生成库 java类 主要用于生成代码
     *  [APT]            : 对于所生成代码的调用
     */
    @APTBindView(R.id.aop_btn_apt)
    var btnApt: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aop)
        ReflectBind.bind(this)

        btnReflect?.setOnClickListener {
            Toast.makeText(this, "ReflectBindView 测试", Toast.LENGTH_SHORT).show()
        }

        btnApt?.setOnClickListener {
            Toast.makeText(this, "APTBind 测试", Toast.LENGTH_SHORT).show()
        }
    }
}