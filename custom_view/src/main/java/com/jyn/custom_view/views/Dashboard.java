package com.jyn.custom_view.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by jiaoyaning on 2020/6/2.
 */
public class Dashboard extends View {
    Paint mPaint;

    public Dashboard(Context context) {
        this(context, null);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Dashboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画线模式
//        mPaint.setStrokeWidth(Utils.px2dp(2));//线宽度
        mPaint.setColor(Color.BLACK);
    }
}
