package com.tplink.tpcompass.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class GradienterView extends View {

    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private float mGradientRadius;
    private PointF mMiddlePoint;
    private float left;
    private float top;
    private float right;
    private float bottom;

    /**
     * 边框粗细
     */
    private static final float FIX_LINE_WIDTH = 5.0f;
    /**
     * 两边突出的两条横线的长度
     */
    private static final float FIX_LINE_LENGTH = 35.0f;
    /**
     * 中间度数文字的字体大小
     */
    private static final float MIDDLE_TEXT_SIZE = 100;

    private float mMiddleCircleLeft;
    private float mMiddleCircleTop;
    private float mMiddleCircleRight;
    private float mMiddleCircleBottom;
    private float mWhiteStartAngle = 0;
    private float mWhiteEndAngle = 180;
    private float mRotateAngle;
    private int mMiddleGradienterOvalColor;
    private int mTextDegree;
    private Typeface mCustomDegreeFont;
    private Typeface mNormalTypeface;

    public GradienterView(Context context) {
        this(context, null);
    }

    public GradienterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GradienterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initAttrs(attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        mMiddleGradienterOvalColor = Color.parseColor("#FF282828");

        mCustomDegreeFont = Typeface.createFromAsset(getContext().getAssets(), "fonts/Roboto-Thin" +
                ".ttf");
        mNormalTypeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL);
    }

    private void initAttrs(AttributeSet attrs) {
        //TODO deal with attrs
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mGradientRadius = mHeight / 4.0f;
        mMiddlePoint = new PointF(mWidth / 2.0f, mHeight / 2.0f);

        left = mMiddlePoint.x - mGradientRadius;
        top = mMiddlePoint.y - mGradientRadius;
        right = mMiddlePoint.x + mGradientRadius;
        bottom = mMiddlePoint.y + mGradientRadius;

        float halfFixLineWidth = FIX_LINE_WIDTH / 2.0f;
        mMiddleCircleLeft = mMiddlePoint.x - mGradientRadius - halfFixLineWidth;
        mMiddleCircleTop = mMiddlePoint.y - mGradientRadius - halfFixLineWidth;
        mMiddleCircleRight = mMiddlePoint.x + mGradientRadius + halfFixLineWidth;
        mMiddleCircleBottom = mMiddlePoint.y + mGradientRadius + halfFixLineWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画固定的两边的横线
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(FIX_LINE_WIDTH);
        canvas.drawLine(
                mMiddlePoint.x - mGradientRadius - FIX_LINE_LENGTH,
                mMiddlePoint.y,
                mMiddlePoint.x - mGradientRadius - 4,
                mMiddlePoint.y,
                mPaint);
        canvas.drawLine(
                mMiddlePoint.x + mGradientRadius + 4,
                mMiddlePoint.y,
                mMiddlePoint.x + mGradientRadius + FIX_LINE_LENGTH,
                mMiddlePoint.y,
                mPaint);

        //画中间的圆圈边框
        canvas.drawOval(
                mMiddleCircleLeft, mMiddleCircleTop,
                mMiddleCircleRight, mMiddleCircleBottom,
                mPaint);

//        //simulate data
        mWhiteStartAngle = 0;
        mWhiteEndAngle = 180;
        mRotateAngle = 45;//超过45就不画了，why?
        //超过45就不画了，why?
        mTextDegree = 6;
//        //simulate data
        canvas.save();
//        //画中间的填充白色半圆
        canvas.rotate(mRotateAngle, mMiddlePoint.x, mMiddlePoint.y);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawArc(
                mMiddleCircleLeft + 2.5f, mMiddleCircleTop + 2.5f,
                mMiddleCircleRight - 2.5f, mMiddleCircleBottom - 2.5f,
                mWhiteStartAngle, mWhiteEndAngle, false,
                mPaint);
        //画中间的水平仪椭圆
        float middleOvalHeight = (float) (Math.cos(mRotateAngle) * mGradientRadius);
        mPaint.setColor(mMiddleGradienterOvalColor);
        canvas.drawOval(
                mMiddleCircleLeft + 2.5f, mMiddlePoint.y - middleOvalHeight,
                mMiddleCircleRight - 2.5f, mMiddlePoint.y + middleOvalHeight, mPaint);
        //draw middle text
        mPaint.setColor(Color.WHITE);
        mPaint.setTypeface(mCustomDegreeFont);
        Rect textBounds = new Rect();
        mPaint.setTextSize(MIDDLE_TEXT_SIZE);
        String textDegree = mTextDegree + "°";
        mPaint.getTextBounds(textDegree, 0, (textDegree).length(), textBounds);
        int halfTextWidth = textBounds.width() / 2;
        int halfTextHeight = textBounds.height() / 2;
        canvas.drawText(textDegree, mMiddlePoint.x - halfTextWidth, mMiddlePoint.y +
                halfTextHeight, mPaint);
        canvas.restore();
        mPaint.setTypeface(mNormalTypeface);
    }

}
