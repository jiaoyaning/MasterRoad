package com.jyn.custom_view.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.jyn.common.utils.DensityUtils;

/**
 * Created by jiaoyaning on 2020/6/2.
 * 仪表盘
 */
public class DashboardView extends View {
    Paint mPaint;
    float RADIUS = 300;
    Path arcPath = new Path(); //弧线路径
    Path calibration = new Path(); //刻度路径

    private int num = 20;


    public DashboardView(Context context) {
        this(context, null);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPath();
    }

    private void initPath() {
        //抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔模式
        mPaint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 2));
        //画笔颜色
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawCalibration(canvas);
    }

    /**
     * 弧线
     */
    private void drawArc(Canvas canvas) {
        //定义弧形位置
        RectF arcRectF = new RectF((getWidth() >> 1) - RADIUS,
                (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS,
                (getHeight() >> 1) + RADIUS);

        //添加弧形，并绘制
        arcPath.addArc(arcRectF, 135, 270);
        canvas.drawPath(arcPath, mPaint);
    }

    /**
     * 刻度
     */
    private void drawCalibration(Canvas canvas) {
        //定义刻度方块
        RectF calibrationRectF = new RectF(0, 0,
                DensityUtils.dp2px(getContext(), 2),
                DensityUtils.dp2px(getContext(), 10));

        //添加刻度。
        calibration.addRect(calibrationRectF, Path.Direction.CW);

        PathMeasure pathMeasure = new PathMeasure(arcPath, false);
        //获取 arcPath（弧线）的长度
        float length = pathMeasure.getLength();
        //需要减去一个刻度的宽度
        float advance = (length - DensityUtils.dp2px(getContext(), 2)) / (num - 1);

        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(calibration,
                advance,
                0,
                PathDashPathEffect.Style.ROTATE);

        mPaint.setPathEffect(pathDashPathEffect);
        canvas.drawPath(arcPath, mPaint);
        mPaint.setPathEffect(null);
    }
}
