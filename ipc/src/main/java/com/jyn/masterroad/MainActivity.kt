package com.jyn.masterroad

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.apkfuns.logutils.LogUtils

/**
 * Messenger与AIDL的异同:
 * https://blog.csdn.net/jiwangkailai02/article/details/48098087
 *
 * https://github.com/leavesC/IPCSamples/blob/master/note/AndroidIPC%E6%9C%BA%E5%88%B6%EF%BC%883%EF%BC%89-AIDL.md
 */
val TAG = "AIDLTest"

class MainActivity : AppCompatActivity() {
    private lateinit var aidlTestInterface: AidlTestInterface
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initService()
    }

    /**
     * 先启动aidl_test进程，模仿server端
     */
    private fun initService() {
        val intent = Intent()
        intent.setClassName("com.jyn.masterroad", "com.jyn.masterroad.AidlTestService")
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private val serviceConnection: ServiceConnection by lazy {
        object : ServiceConnection {
            override fun onServiceDisconnected(name: ComponentName?) {
                LogUtils.tag(TAG).i("MainActivity 失去链接:$name")
            }

            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                aidlTestInterface = AidlTestInterface.Stub.asInterface(service)
                LogUtils.tag(TAG).i("MainActivity 链接成功:$name")
                LogUtils.tag(TAG).i("MainActivity test:" + aidlTestInterface.test)
            }
        }
    }
}