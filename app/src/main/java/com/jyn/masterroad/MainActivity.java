package com.jyn.masterroad;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.apkfuns.logutils.LogUtils;
import com.jyn.masterroad.databinding.ActivityMainBinding;

@Route(path = "/app/mainActivity")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.handlerTest.setOnClickListener(this);
        LogUtils.tag("main").i(activityMainBinding.handlerTest);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.handler_test) {
            ARouter.getInstance().build("/app/handler").navigation();
        }
    }
}
