package com.jyn.masterroad

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.apkfuns.logutils.LogUtils
import com.jyn.masterroad.databinding.ActivityMainBinding

/**
 * Messenger与AIDL的异同:
 * https://blog.csdn.net/jiwangkailai02/article/details/48098087
 *
 * https://github.com/leavesC/IPCSamples/blob/master/note/AndroidIPC%E6%9C%BA%E5%88%B6%EF%BC%883%EF%BC%89-AIDL.md
 */
val TAG = "AIDLTest"

class MainActivity : AppCompatActivity() {
    private lateinit var aidlTestInterface: AidlTestInterface

    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initService()
    }

    /**
     * 先启动aidl_test进程，模仿server端
     */
    private fun initService() {
        val intent = Intent()
        intent.setClassName("com.jyn.masterroad", "com.jyn.masterroad.AidlStringTestService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private val serviceConnection: ServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtils.tag(TAG).i("AidlStringTestService 失去链接:$name")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                aidlTestInterface = AidlTestInterface.Stub.asInterface(service)
                LogUtils.tag(TAG).i("AidlStringTestService 链接成功:$name")
                LogUtils.tag(TAG).i("test:" + aidlTestInterface.test)
            }
        }
    }
}