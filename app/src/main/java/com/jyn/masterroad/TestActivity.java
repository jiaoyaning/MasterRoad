package com.jyn.masterroad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TestActivity extends AppCompatActivity {

    FrameLayout fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        getLifecycle().addObserver(new Presenter());
        fragment = findViewById(R.id.fragment);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment, new Test1Fragment())
                .addToBackStack(null)
                .commitNow();


        //步骤1. 创建被观察者(Observable),定义要发送的事件。
        Observable<String> observable = Observable.create(
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter)
                            throws Exception {
                        emitter.onNext("文章1");
                        emitter.onNext("文章2");
                        emitter.onNext("文章3");
                        emitter.onComplete();
                    }
                })
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return null;
                    }
                });

        //步骤2. 创建观察者(Observer)，接受事件并做出响应操作。
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("main", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.d("main", "onNext : " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.d("main", "onError : " + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d("main", "onComplete");
            }
        };

        //步骤3. 观察者通过订阅（subscribe）被观察者把它们连接到一起。
        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}