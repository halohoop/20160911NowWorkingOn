/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * MarkableImageView.java
 *
 * Description
 *
 * Author huanghaiqi
 *
 * Ver 1.0, 2016-09-08, huanghaiqi, Create file
 */

package com.halohoop.markableimageview.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import uk.co.senab.photoview.PhotoView;

public class MarkableImageView extends PhotoView {

    final String TAG = "huanghaiqi";
    final boolean mIsNeedDebug = false;
    /**
     * paint to draw arrow rect circle .etc;
     */
    private Paint mPaint;
    private PointF mStartPointF;
    private PointF mEndPointF;
    /**
     * mark whether is Editing mode
     */
    private boolean mIsEditing = false;
    private PointF[] mTrianglePointFs;
    /**
     * the angle of arrow to rotate
     */
    private double mAngle;
    private float heightOfArrow = 40.0f;
    private float widthOfArrow = 10.0f;

//    private double mAngle;

    public MarkableImageView(Context context) {
        this(context, null);
    }

    public MarkableImageView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public MarkableImageView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(100.0f);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(3);
        mStartPointF = new PointF();
        mEndPointF = new PointF();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (mIsEditing) {
            int action = event.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // This is the arrow start point
                    mStartPointF.x = event.getX();
                    mStartPointF.y = event.getY();
                    if (mTrianglePointFs == null) {
                        mTrianglePointFs = new PointF[4];
                        mTrianglePointFs[0] = new PointF();
                        mTrianglePointFs[1] = new PointF();
                        mTrianglePointFs[2] = new PointF();
                        mTrianglePointFs[3] = new PointF();
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    mEndPointF.x = event.getX();
                    mEndPointF.y = event.getY();
                    // 箭头角度
                    updateAngle();
                    updateTrianglePointFs();

                    invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            return true;
        } else {
            return super.dispatchTouchEvent(event);
        }
    }

    private void updateAngle() {
//        float disX = Math.abs(mEndPointF.x - mStartPointF.x);
//        float disY = Math.abs(mEndPointF.y - mStartPointF.y);
        float disX = mEndPointF.x - mStartPointF.x;
        float disY = mEndPointF.y - mStartPointF.y;
        mAngle = Math.atan(disY / disX);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mIsEditing) {
            //draw triangle
            canvas.save();
            Path triangle = new Path();
            triangle.moveTo(mTrianglePointFs[0].x, mTrianglePointFs[0].y);
            triangle.lineTo(mTrianglePointFs[2].x, mTrianglePointFs[2].y);
            triangle.lineTo(mTrianglePointFs[3].x, mTrianglePointFs[3].y);
            triangle.close();
            canvas.drawPath(triangle, mPaint);
            canvas.restore();

            canvas.drawLine(mStartPointF.x, mStartPointF.y, mTrianglePointFs[1].x,
                    mTrianglePointFs[1].y, mPaint);

            //just for debug
            if (mIsNeedDebug) {
                mPaint.setColor(Color.RED);
                canvas.drawCircle(mTrianglePointFs[0].x, mTrianglePointFs[0].y, 5, mPaint);
                mPaint.setColor(Color.YELLOW);
                canvas.drawCircle(mTrianglePointFs[1].x, mTrianglePointFs[1].y, 5, mPaint);
                mPaint.setColor(Color.GREEN);
                canvas.drawCircle(mTrianglePointFs[2].x, mTrianglePointFs[2].y, 5, mPaint);
                mPaint.setColor(Color.BLUE);
                canvas.drawCircle(mTrianglePointFs[3].x, mTrianglePointFs[3].y, 5, mPaint);
                mPaint.setColor(Color.BLACK);
            }
            //just for debug
        }
    }

    public void updateTrianglePointFs() {
        mTrianglePointFs[0] = mEndPointF;

        double sH = Math.abs(Math.sin(mAngle) * heightOfArrow);
        double cH = Math.abs(Math.cos(mAngle) * heightOfArrow);

        double sW = Math.abs(Math.sin(mAngle) * widthOfArrow);
        double cW = Math.abs(Math.cos(mAngle) * widthOfArrow);


        //left top to right bottom
        if (mStartPointF.x <= mEndPointF.x && mStartPointF.y <= mEndPointF.y) {
            mTrianglePointFs[1].y =
                    (float) (mTrianglePointFs[0].y - sH);
            mTrianglePointFs[1].x =
                    (float) (mTrianglePointFs[0].x - cH);

            mTrianglePointFs[2].y = (float) (mTrianglePointFs[1].y - cW);
            mTrianglePointFs[2].x = (float) (mTrianglePointFs[1].x + sW);

            mTrianglePointFs[3].y = (float) (mTrianglePointFs[1].y + cW);
            mTrianglePointFs[3].x = (float) (mTrianglePointFs[1].x - sW);
        }
        //right bottom to left top
        else if (mStartPointF.x >= mEndPointF.x && mStartPointF.y >= mEndPointF.y) {
            mTrianglePointFs[1].y =
                    (float) (mTrianglePointFs[0].y + sH);
            mTrianglePointFs[1].x =
                    (float) (mTrianglePointFs[0].x + cH);

            mTrianglePointFs[2].y = (float) (mTrianglePointFs[1].y + cW);
            mTrianglePointFs[2].x = (float) (mTrianglePointFs[1].x - sW);

            mTrianglePointFs[3].y = (float) (mTrianglePointFs[1].y - cW);
            mTrianglePointFs[3].x = (float) (mTrianglePointFs[1].x + sW);
        }
        //left bottom to right top
        else if (mStartPointF.x < mEndPointF.x && mStartPointF.y > mEndPointF.y) {
            mTrianglePointFs[1].y =
                    (float) (mTrianglePointFs[0].y + sH);
            mTrianglePointFs[1].x =
                    (float) (mTrianglePointFs[0].x - cH);

            mTrianglePointFs[2].y = (float) (mTrianglePointFs[1].y - cW);
            mTrianglePointFs[2].x = (float) (mTrianglePointFs[1].x - sW);

            mTrianglePointFs[3].y = (float) (mTrianglePointFs[1].y + cW);
            mTrianglePointFs[3].x = (float) (mTrianglePointFs[1].x + sW);
        }
        //right top to left bottom
        else {
            mTrianglePointFs[1].y =
                    (float) (mTrianglePointFs[0].y - sH);
            mTrianglePointFs[1].x =
                    (float) (mTrianglePointFs[0].x + cH);

            mTrianglePointFs[2].y = (float) (mTrianglePointFs[1].y + cW);
            mTrianglePointFs[2].x = (float) (mTrianglePointFs[1].x + sW);

            mTrianglePointFs[3].y = (float) (mTrianglePointFs[1].y - cW);
            mTrianglePointFs[3].x = (float) (mTrianglePointFs[1].x - sW);
        }


    }

    public void enterEditMode() {
        mIsEditing = true;
    }

    public void exitEditMode() {
        mIsEditing = false;
    }
}
