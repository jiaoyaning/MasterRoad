package com.jyn.masterroad.view.custom;

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

import static com.jyn.common.Utils.DensityUtilsKt.dp2px;

/*
 * 仪表盘
 */
public class DashboardView extends View {
    Paint mPaint;
    float RADIUS = 300;
    Path arcPath = new Path(); //弧线路径
    Path calibration = new Path(); //刻度路径
    private final int mAngle = 90;

    private final int mNum = 5;

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

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private void initPath() {
        //抗锯齿
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画笔模式
        mPaint.setStyle(Paint.Style.STROKE);
        //画笔宽度
        mPaint.setStrokeWidth(dp2px(2));
        //画笔颜色
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawCalibration(canvas);
        drawPointer(canvas);
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
        arcPath.addArc(arcRectF, (mAngle / 2) + 90, 360 - mAngle);
        canvas.drawPath(arcPath, mPaint);
    }

    /**
     * 刻度
     */
    private void drawCalibration(Canvas canvas) {
        //定义刻度方块
        RectF calibrationRectF = new RectF(0, 0, dp2px(2), dp2px(10));

        //添加刻度。
        calibration.addRect(calibrationRectF, Path.Direction.CW);

        PathMeasure pathMeasure = new PathMeasure(arcPath, false);
        //获取 arcPath（弧线）的长度
        float length = pathMeasure.getLength();
        //需要减去一个刻度的宽度
        float advance = (length - dp2px(2)) / (mNum - 1);

        PathDashPathEffect pathDashPathEffect = new PathDashPathEffect(calibration,
                advance,
                0,
                PathDashPathEffect.Style.ROTATE); //虚线效果

        mPaint.setPathEffect(pathDashPathEffect); //设置路径效果
        canvas.drawPath(arcPath, mPaint);
        mPaint.setPathEffect(null);
    }

    /**
     * 指针
     */
    private void drawPointer(Canvas canvas) {
        for (int i = 0; i < mNum; i++) {
            //根据刻度获取角度。
            int angle = getAngleFromCalibration(i);
            canvas.save();
            canvas.rotate(angle + 180f, getWidth() >> 1, getHeight() >> 1);
            canvas.drawLine(getWidth() >> 1, getHeight() >> 1,
                    (getWidth() >> 1) - RADIUS + dp2px(14),
                    (getHeight() >> 1) - dp2px(2), mPaint);
            canvas.restore();
        }
    }

    private int getAngleFromCalibration(int mark) {
        return (int) ((float) (mAngle / 2) + 90 + (360 - (float) mAngle) / mNum * mark);
    }
}
