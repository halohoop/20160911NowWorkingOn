package com.tplink.tpcompass.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class CompassView extends SurfaceView implements SurfaceHolder.Callback {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mCompassRadius;
    private PointF mMiddlePoint;
    private float left;
    private float top;
    private float right;
    private float bottom;

    /**
     * 固定竖线的长度
     */
    private static final float FIX_LINE_LENGTH = 70.0f;
    /**
     * 固定竖线的粗细
     */
    private static final float FIX_LINE_WIDTH = 10.0f;
    /**
     * 中间圆的线条粗细
     */
    private static final float MIDDLE_CIRCLE_WIDTH = 2.0f;
    /**
     * 中间圆的半径
     */
    private static final float MIDDLE_CIRCLE_RADIUS = 50.0f;
    private float mMiddleCircleLeft;
    private float mMiddleCircleTop;
    private float mMiddleCircleRight;
    private float mMiddleCircleBottom;
    private float mWhiteStartAngle = 0;
    private float mWhiteEndAngle = 180;
    private float mBlackStartAngle = 180;
    private float mBlackEndAngle = 360;
    private float mRotateAngle;
    private int mMiddleGradienterOvalColor;

    public CompassView(Context context) {
        this(context, null);
    }

    public CompassView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompassView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initAttrs(attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getHolder().addCallback(this);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mMiddleGradienterOvalColor = Color.parseColor("#FF282828");
    }

    private void initAttrs(AttributeSet attrs) {
        //TODO deal with attrs
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCompassRadius = mHeight / 4.0f;
        mMiddlePoint = new PointF(mWidth / 2.0f, mHeight / 2.0f);

        left = mMiddlePoint.x - mCompassRadius;
        top = mMiddlePoint.y - mCompassRadius;
        right = mMiddlePoint.x + mCompassRadius;
        bottom = mMiddlePoint.y + mCompassRadius;

        mMiddleCircleLeft = mMiddlePoint.x - MIDDLE_CIRCLE_RADIUS;
        mMiddleCircleTop = mMiddlePoint.y - MIDDLE_CIRCLE_RADIUS;
        mMiddleCircleRight = mMiddlePoint.x + MIDDLE_CIRCLE_RADIUS;
        mMiddleCircleBottom = mMiddlePoint.y + MIDDLE_CIRCLE_RADIUS;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画固定的竖线
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(FIX_LINE_WIDTH);
        canvas.drawLine(
                mMiddlePoint.x, mMiddlePoint.y - mCompassRadius,
                mMiddlePoint.x, mMiddlePoint.y - mCompassRadius - FIX_LINE_LENGTH,
                mPaint);

        //画中间的圆圈边框
        mPaint.setStrokeWidth(MIDDLE_CIRCLE_WIDTH);
        canvas.drawOval(
                mMiddleCircleLeft, mMiddleCircleTop,
                mMiddleCircleRight, mMiddleCircleBottom,
                mPaint);

        //simulate data
        mWhiteStartAngle = 0;
        mWhiteEndAngle = 180;
        mBlackStartAngle = 180;
        mBlackEndAngle = 360;
        mRotateAngle = 30;
        //simulate data
        canvas.save();
        //画中间的填充白色半圆
        canvas.rotate(mRotateAngle, mMiddlePoint.x, mMiddlePoint.y);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(
                mMiddleCircleLeft, mMiddleCircleTop,
                mMiddleCircleRight, mMiddleCircleBottom,
                mWhiteStartAngle, mWhiteEndAngle, false,
                mPaint);
        //画中间的水平仪椭圆
        float middleOvalHeight = (float) (Math.cos(mRotateAngle) * MIDDLE_CIRCLE_RADIUS);
        mPaint.setColor(mMiddleGradienterOvalColor);
        canvas.drawOval(
                mMiddleCircleLeft, mMiddlePoint.y - middleOvalHeight,
                mMiddleCircleRight, mMiddlePoint.y + middleOvalHeight, mPaint);
        canvas.restore();


        //画中间的椭圆
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        //surfaceview templete
        Canvas canvas = surfaceHolder.lockCanvas();
        draw(canvas);//final to onDraw
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}
