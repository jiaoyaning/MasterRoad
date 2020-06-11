package com.jyn.custom_view.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by jiaoyaning on 2020/6/2.
 */
public class Dashboard extends View {
    Paint mPaint;
    float RADIUS = 300;
    Path dash = new Path();
    private PathDashPathEffect pathDashPathEffect;

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
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画线模式
        mPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 2));//线宽度
        mPaint.setColor(Color.BLACK);
        dash.addRect(0, 0,
                DensityUtils.dp2px(getContext(), 2), DensityUtils.dp2px(getContext(), 10),
                Path.Direction.CW);
        pathDashPathEffect = new PathDashPathEffect(dash, DensityUtils.dp2px(getContext(), 20),
                0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawOval(canvas);
    }

    private void drawOval(Canvas canvas) {
        @SuppressLint("DrawAllocation")
        RectF rectF = new RectF((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS);
        canvas.drawOval(rectF, mPaint);
        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawOval(rectF, mPaint);
        mPaint.setPathEffect(null);
    }
}
