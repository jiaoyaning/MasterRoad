package com.jyn.masterroad;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jyn.masterroad.databinding.ActivityMainBinding;

@Route(path = "/app/mainActivity")
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
//        activityMainBinding.handlerTest.setOnClickListener(this);
        findViewById(R.id.handler_test).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.handler_test) {
            ARouter.getInstance().build("/app/handler").navigation();
        }
    }
}
