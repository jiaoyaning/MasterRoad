package com.jyn.masterroad.okhttp;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiao on 2020/8/11.
 */
public class OkHttpDemo {
    public void test() throws IOException {
        //同步请求
        OkHttpClient client1 = new OkHttpClient();
        // 或者
        OkHttpClient client2 = new OkHttpClient
                .Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();
        Request request = new Request.Builder()
                .url("http://myproject.com/helloworld.txt")
                .build();
        Response response = client2.newCall(request).execute(); // 同步

        //异步请求
        OkHttpClient client3 = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url("http://myproject.com/helloworld.txt")
                .build();
        client3.newCall(request).enqueue(new Callback() { // 异步
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("OkHttp", "Call Failed:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("OkHttp", "Call succeeded:" + response.message());
            }
        });

    }
}