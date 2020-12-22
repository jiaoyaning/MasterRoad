package com.jyn.masterroad

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jyn.masterroad.databinding.ActivityMainBinding

/**
 * Messenger与AIDL的异同:
 * https://blog.csdn.net/jiwangkailai02/article/details/48098087
 */
class MainActivity : AppCompatActivity() {
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }
}