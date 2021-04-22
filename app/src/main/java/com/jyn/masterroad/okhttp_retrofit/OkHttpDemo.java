package com.jyn.masterroad.okhttp_retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/*
 * 从设计模式看OkHttp源码 TODO
 * https://mp.weixin.qq.com/s/eHLXxjvMgII6c_FVRwwdjg
 *
 * OKHttp源码解析(一)--初阶
 * https://www.jianshu.com/p/82f74db14a18?utm_campaign=haruki&utm_content=note&utm_medium=reader_share&utm_source=weixin
 *
 * OKHttp3--Dispatcher分发器实现同步异步请求源码解析【三】
 * https://blog.csdn.net/qq_30993595/article/details/86681210
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