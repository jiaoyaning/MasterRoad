package com.jyn.masterroad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class ThreadActivity extends AppCompatActivity {

    volatile int a = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        Log.i("main", "main 线程：" + System.identityHashCode(a));

        new Thread01().start();
    }

    class Thread01 extends Thread {
        @Override
        public void run() {
            super.run();
            a++;
            Log.i("main", "Thread01 线程：" + System.identityHashCode(a));
        }
    }
}