package com.halohoop.scrollimageviewdemo.views;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

import static android.R.attr.offset;

/**
 * Created by Pooholah on 2016/9/13.
 */

public class ScrollImageView extends ImageView {

    private float mDownY;
    private float mMoveY;

    public ScrollImageView(Context context) {
        this(context, null);
    }

    public ScrollImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public void reset() {
//        float widgetHeight = getHeight();
//        float widgetWidth = getWidth();
//        float drawableWidth = getDrawable().getIntrinsicWidth();
//        float ratio = drawableWidth / widgetWidth;
//
//        float drawableHeight = getDrawable().getIntrinsicHeight();
//
//        float offset = drawableHeight / ratio / 2 - widgetHeight / 2;
//        Matrix imageMatrix = getImageMatrix();
//        imageMatrix.postTranslate(0, offset);
//        invalidate();
//    }

    public void reset() {
        Matrix imageMatrix = getImageMatrix();

        float[] matrixValues = new float[3 * 3];
        imageMatrix.getValues(matrixValues);
        matrixValues[5] = 0;
        imageMatrix.setValues(matrixValues);
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoveY = event.getY();
                float disY = mMoveY - mDownY;
                scrollDown(disY);
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    public void scrollDown(float dis) {
        Matrix imageMatrix = getImageMatrix();
        Log.i("huanghaiqi", "huanghaiqi:" + imageMatrix.toString());
        imageMatrix.postTranslate(0, dis);
        setImageMatrix(imageMatrix);
        invalidate();
    }
}
