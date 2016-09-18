package com.tplink.tpcompass.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.utils.CalculateUtils;
import com.tplink.tpcompass.utils.LogUtils;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class CompassGradienterView extends SurfaceView implements SurfaceHolder.Callback {

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
    private float mFixLineLength = 70.0f;
    /**
     * 固定竖线的粗细
     */
    private float mFixLineWidth = 10.0f;
    /**
     * 中间圆的线条粗细
     */
    private float mMiddleCircleWidth = 2.0f;
    /**
     * 中间圆的半径
     */
    private static final float mMiddleCircleRadius = 50.0f;
    private float mMiddleCircleLeft;
    private float mMiddleCircleTop;
    private float mMiddleCircleRight;
    private float mMiddleCircleBottom;
    private float mWhiteStartAngle = 0;
    private float mWhiteEndAngle = 180;
    private int mMiddleGradienterOvalColor;
    private Bitmap mIndexDialBitmap;
    private float mEveryWordAngle;
    private String[] mWordsOnIndexDial;
    private Rect mEveryWordRect;
    private float mMiddlePadding;
    private float mCompassWordsRadius;
    private Path mTriangleWithPlatePath;
    private float mTriangleWithPlateMargin;
    private float mTriangleWithPlateHeight;
    private float mTriangleWithPlateHalfWidth;
    private int mPlateWordsNumberColor;
    /**
     * 传感器获取到的水平值
     */
    private float mLevelAngle;
    /**
     * 表盘旋转的角度
     */
    private float mNorthOffsetAngle;
    /**
     * 水平与X轴的角度
     */
    private float mPitch;
    /**
     * 水平与Y轴的角度
     */
    private float mRoll;
    private float[] mGradienterRotateAngleAndValue;


    public CompassGradienterView(Context context) {
        this(context, null);
    }

    public CompassGradienterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompassGradienterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        initAttrs(attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        getHolder().addCallback(this);
        initPaint();
        mMiddleGradienterOvalColor = Color.parseColor("#FF181818");

        /**
         * 表盘有12个词，得到画布每次需要旋转的角度
         */
        mEveryWordAngle = 360 / 12;

        initPlateWords();

        mEveryWordRect = new Rect();
        mPlateWordsNumberColor = Color.parseColor("#FF919191");

        mTriangleWithPlatePath = new Path();
        mTriangleWithPlateMargin = 30;
        mTriangleWithPlateHeight = 25;
        mTriangleWithPlateHalfWidth = 20;

        mGradienterRotateAngleAndValue = new float[2];
    }

    /**
     * 国际化的时候还需要重新调一下这个方法，因此特别抽出来
     */
    private void initPlateWords() {
        String north = getContext().getResources().getString(R.string.north);
        String east = getContext().getResources().getString(R.string.east);
        String south = getContext().getResources().getString(R.string.south);
        String west = getContext().getResources().getString(R.string.west);
        mWordsOnIndexDial = new String[]{
                north, "30°", "60°",
                east, "120°", "150°",
                south, "210°", "240°",
                west, "300°", "330°"};
    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
    }

    private Bitmap getAFitIndexDialBitmap(boolean needScale) {
        BitmapFactory.Options options = null;
        if (needScale) {
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeResource(getContext().getResources(), R.mipmap
                    .index_dial, options);
            float ratio = options.outHeight / (mCompassRadius * 2);
            options.inJustDecodeBounds = false;
            options.inSampleSize = (int) Math.ceil(ratio);
        }
        return BitmapFactory.decodeResource(getContext().getResources(), R.mipmap
                .index_dial, options);
    }

    private void initAttrs(AttributeSet attrs) {
        //TODO deal with attrs
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mCompassRadius = mHeight / 4.0f;
        mMiddlePoint = new PointF(mWidth / 2.0f, mHeight / 2.0f);

        left = mMiddlePoint.x - mCompassRadius;
        top = mMiddlePoint.y - mCompassRadius;
        right = mMiddlePoint.x + mCompassRadius;
        bottom = mMiddlePoint.y + mCompassRadius;

        mMiddleCircleLeft = mMiddlePoint.x - mMiddleCircleRadius;
        mMiddleCircleTop = mMiddlePoint.y - mMiddleCircleRadius;
        mMiddleCircleRight = mMiddlePoint.x + mMiddleCircleRadius;
        mMiddleCircleBottom = mMiddlePoint.y + mMiddleCircleRadius;

        mIndexDialBitmap = getAFitIndexDialBitmap(false);

        /**
         * 画表盘每组文字的中心位置离表盘的padding距离
         */
        mMiddlePadding = 45;
        mCompassWordsRadius = mCompassRadius - mMiddlePadding;
    }

    public void setNorthOffsetAngle(float northOffsetAngle) {
        this.mNorthOffsetAngle = northOffsetAngle;
        postInvalidate();
    }

    public void setLevelAngle(float levelAngle) {
        this.mLevelAngle = levelAngle;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //模拟从传感器获取的值
//        mNorthOffsetAngle = 30;
//        mLevelAngle = 30;//模拟从传感器获取的值

        boolean isScreenFacingTheSky = CalculateUtils.getGradienterRotateAngleAndValue
                (mPitch, mRoll,
                        mGradienterRotateAngleAndValue);

        mLevelAngle = mGradienterRotateAngleAndValue[1];

        //画表盘
        canvas.save();
        canvas.rotate(mNorthOffsetAngle, mMiddlePoint.x, mMiddlePoint.y);
        canvas.drawBitmap(mIndexDialBitmap,
                mMiddlePoint.x - mIndexDialBitmap.getWidth() / 2,
                mMiddlePoint.y - mIndexDialBitmap.getHeight() / 2, null);
        canvas.restore();

        //画表盘文字
        for (int i = 0; i < mWordsOnIndexDial.length; i++) {
            mPaint.getTextBounds(mWordsOnIndexDial[i], 0, mWordsOnIndexDial[i].length(),
                    mEveryWordRect);
            float wordAngle = mNorthOffsetAngle + mEveryWordAngle * i;
            updatePlateWordsPaint(i);
            if (wordAngle >= 0 && wordAngle < 90) {
                //第1象限
                float tmpAngle = 90 - wordAngle;
                double deltaX = Math.cos(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                double deltaY = Math.sin(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                float tmpWordPpintX = (float) (mMiddlePoint.x + deltaX);
                float tmpWordPpintY = (float) (mMiddlePoint.y - deltaY);
                drawTextInRightPosition(canvas, mWordsOnIndexDial[i], tmpWordPpintX, tmpWordPpintY);
            } else if (wordAngle >= 90 && wordAngle < 180) {
                //第4象限
                float tmpAngle = 180 - wordAngle;
                double deltaX = Math.sin(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                double deltaY = Math.cos(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                float tmpWordPpintX = (float) (mMiddlePoint.x + deltaX);
                float tmpWordPpintY = (float) (mMiddlePoint.y + deltaY);
                drawTextInRightPosition(canvas, mWordsOnIndexDial[i], tmpWordPpintX, tmpWordPpintY);
            } else if (wordAngle >= 180 && wordAngle < 270) {
                //第3象限
                float tmpAngle = 270 - wordAngle;
                double deltaX = Math.cos(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                double deltaY = Math.sin(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                float tmpWordPpintX = (float) (mMiddlePoint.x - deltaX);
                float tmpWordPpintY = (float) (mMiddlePoint.y + deltaY);
                drawTextInRightPosition(canvas, mWordsOnIndexDial[i], tmpWordPpintX, tmpWordPpintY);
            } else {
                //第2象限
                float tmpAngle = 360 - wordAngle;
                double deltaX = Math.sin(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                double deltaY = Math.cos(Math.toRadians(tmpAngle)) * mCompassWordsRadius;
                float tmpWordPpintX = (float) (mMiddlePoint.x - deltaX);
                float tmpWordPpintY = (float) (mMiddlePoint.y - deltaY);
                drawTextInRightPosition(canvas, mWordsOnIndexDial[i], tmpWordPpintX, tmpWordPpintY);
            }
        }

        //画中间的圆圈边框
        updateMiddleCircleStrokePaint();
        canvas.drawOval(
                mMiddleCircleLeft, mMiddleCircleTop,
                mMiddleCircleRight, mMiddleCircleBottom,
                mPaint);

        LogUtils.i("mLevelAngle:" + mLevelAngle);

        //画中间
        if (isScreenFacingTheSky) {
            if (mLevelAngle != 0) {
                canvas.save();
                //画中间的填充白色半圆
                canvas.rotate(mGradienterRotateAngleAndValue[0], mMiddlePoint.x, mMiddlePoint.y);
                updateMiddleCircleHalfFillPaint();
                canvas.drawArc(
                        mMiddleCircleLeft, mMiddleCircleTop,
                        mMiddleCircleRight, mMiddleCircleBottom,
                        mWhiteStartAngle, mWhiteEndAngle, false,
                        mPaint);
                //画中间的水平仪椭圆
                float middleOvalHeight = (float) (Math.cos(Math.toRadians(mLevelAngle)) *
                        mMiddleCircleRadius);
                updateMiddleOvalPaint(isScreenFacingTheSky);
                canvas.drawOval(
                        mMiddleCircleLeft, mMiddlePoint.y - middleOvalHeight,
                        mMiddleCircleRight, mMiddlePoint.y + middleOvalHeight, mPaint);
                canvas.restore();
            } else {
                //draw nothing, leave it black
            }
        } else {//face the floor
            if (mLevelAngle != 180) {
                canvas.save();
                //画中间的填充白色半圆
                canvas.rotate(mGradienterRotateAngleAndValue[0], mMiddlePoint.x, mMiddlePoint.y);
                updateMiddleCircleHalfFillPaint();
                canvas.drawArc(
                        mMiddleCircleLeft, mMiddleCircleTop,
                        mMiddleCircleRight, mMiddleCircleBottom,
                        mWhiteStartAngle, mWhiteEndAngle, false,
                        mPaint);
                //画中间的水平仪椭圆
                float middleOvalHeight = (float) (Math.cos(Math.toRadians(90 - (180 -
                        mLevelAngle))) *
                        mMiddleCircleRadius);
                updateMiddleOvalPaint(isScreenFacingTheSky);
                canvas.drawOval(
                        mMiddleCircleLeft, mMiddlePoint.y - middleOvalHeight,
                        mMiddleCircleRight, mMiddlePoint.y + middleOvalHeight, mPaint);
                canvas.restore();
            } else {
                //draw fill white circle
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setColor(Color.WHITE);
                canvas.drawOval(
                        mMiddleCircleLeft, mMiddleCircleTop,
                        mMiddleCircleRight, mMiddleCircleBottom,
                        mPaint);
            }
        }

        //画固定的竖线
        updateTopFixLinePaint();
        canvas.drawLine(
                mMiddlePoint.x, mMiddlePoint.y - mCompassRadius,
                mMiddlePoint.x, mMiddlePoint.y - mCompassRadius - mFixLineLength,
                mPaint);

        //画跟随表盘的红色的三角形
        canvas.save();
        canvas.rotate(mNorthOffsetAngle, mMiddlePoint.x, mMiddlePoint.y);
        mTriangleWithPlatePath.reset();
        //move to top
        float triangleTopY = mMiddlePoint.y - mCompassRadius - mTriangleWithPlateMargin -
                mTriangleWithPlateHeight;
        mTriangleWithPlatePath.moveTo(
                mMiddlePoint.x,
                triangleTopY);
        mTriangleWithPlatePath.lineTo(
                mMiddlePoint.x - mTriangleWithPlateHalfWidth,
                triangleTopY + mTriangleWithPlateHeight);
        mTriangleWithPlatePath.lineTo(
                mMiddlePoint.x + mTriangleWithPlateHalfWidth,
                triangleTopY + mTriangleWithPlateHeight);
        updateTriangleWithPlatePaint();
        canvas.drawPath(mTriangleWithPlatePath, mPaint);
        canvas.restore();
    }

    private void updateTriangleWithPlatePaint() {
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
    }

    private void updateTopFixLinePaint() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mFixLineWidth);
    }

    private void updateMiddleOvalPaint(boolean isScreenFacingTheSky) {
        if (isScreenFacingTheSky) {
            mPaint.setColor(mMiddleGradienterOvalColor);
        } else {
            mPaint.setColor(Color.WHITE);
        }
    }

    private void updateMiddleCircleHalfFillPaint() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
    }

    private void updateMiddleCircleStrokePaint() {
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mMiddleCircleWidth);
    }

    private void updatePlateWordsPaint(int i) {
        if (i % 3 == 0) {
            mPaint.setColor(Color.WHITE);
        } else {
            mPaint.setColor(mPlateWordsNumberColor);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextSize(30);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
    }

    private void drawTextInRightPosition(Canvas canvas, String text, float tmpWordPpintX, float
            tmpWordPpintY) {
        tmpWordPpintX = tmpWordPpintX - mEveryWordRect.width() / 2;
        tmpWordPpintY = tmpWordPpintY + mEveryWordRect.height() / 2;
        canvas.drawText(text, tmpWordPpintX, tmpWordPpintY, mPaint);
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

    public void setGradienterData(float pitch, float roll) {
        this.mPitch = pitch;
        this.mRoll = roll;
        postInvalidate();
    }
}
