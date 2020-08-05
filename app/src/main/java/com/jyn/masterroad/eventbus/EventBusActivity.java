package com.jyn.masterroad.eventbus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jyn.masterroad.Presenter;
import com.jyn.masterroad.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

@Route(path = "/test/EventBusActivity")
public class EventBusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);

        EventBus.getDefault().post(new Presenter());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Presenter presenter) {

    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().register(this);
    }
}