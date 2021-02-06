package com.jyn.masterroad.thread;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.jyn.common.utils.ARouter.RoutePath;
import com.jyn.masterroad.R;
import com.jyn.masterroad.base.BaseActivity;
import com.jyn.masterroad.databinding.ActivityThreadBinding;

/*
 * JDK线程池源码分析之ThreadPoolExecutor
 * https://www.jianshu.com/p/072703367564
 *
 * ExecutorService 的 shutdown 和 shutdownNow 区别与联系
 * https://www.jianshu.com/p/f2591bdd0877
 */
@Route(path = RoutePath.Thread.path)
public class ThreadActivity extends BaseActivity<ActivityThreadBinding> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_thread;
    }
}