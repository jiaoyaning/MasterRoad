package com.jyn.masterroad.jetpack.hilt;

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.apkfuns.logutils.LogUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HiltDaggerActivity : AppCompatActivity() {

    @Inject
    lateinit var hiltTestData: HiltTestData

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        val staticTest = hiltTestData.getStaticTest()
        LogUtils.tag("main").i("HiltTestData base:$staticTest")
    }
}