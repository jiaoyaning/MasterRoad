package com.jyn.masterroad;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.jyn.masterroad.base.BaseActivity;
import com.jyn.masterroad.databinding.ActivityMainBinding;

import org.jetbrains.annotations.NotNull;

import static com.jyn.masterroad.base.RoutePath.HANDLER;
import static com.jyn.masterroad.base.RoutePath.MAIN;

/**
 * https://www.jianshu.com/p/2ee3672efb1f
 */
@Route(path = MAIN)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @NotNull
    private ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(activityMainBinding.getRoot());
        activityMainBinding.setMainViewModel(new MainViewModel());
//        activityMainBinding.handlerTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.handler_test) {
            ARouter.getInstance().build(HANDLER).navigation();
        }
    }
}
