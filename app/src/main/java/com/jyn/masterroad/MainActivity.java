package com.jyn.masterroad;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jyn.masterroad.databinding.ActivityMainBinding;

import static com.jyn.masterroad.base.RoutePath.HANDLER;
import static com.jyn.masterroad.base.RoutePath.MAIN;

@Route(path = MAIN)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.handlerTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.handler_test) {
            ARouter.getInstance().build(HANDLER).navigation();
        }
    }
}
