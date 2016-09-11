package cn.com.tplink.halohoop.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import cn.com.tplink.utils.crop.CropLayout;
import cn.com.tplink.utils.crop.CropView;

public class MainActivity extends AppCompatActivity implements CropLayout.Callback {

    private final String TAG = "halohoop";

    private WindowManager.LayoutParams mWindowLayoutParams;
    private WindowManager mWindowManager;
    private CropLayout mCropLayout;
    private CropView mCropView;
    private ImageButton mCancelButton;
    private ImageButton mScrollButton;
    private DisplayMetrics mDisplayMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inflate crop layout
        mCropLayout = new CropLayout(MainActivity.this);
        mCropView = mCropLayout.getCropView();
        mCropLayout.setFocusable(true);
        mCropLayout.setFocusableInTouchMode(true);

        mCancelButton = mCropLayout.getCancelButton();
        mScrollButton = mCropLayout.getConfirmButton();
        mCropLayout.setCallback(this);
        mScrollButton.setImageResource(R.mipmap.screenshot_scroll_down);

        // Setup the window that we are going to use
        mWindowLayoutParams = new WindowManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0, 0,
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                        | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
                        | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                        | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED,
                PixelFormat.TRANSLUCENT);
        mWindowLayoutParams.setTitle("ScreenshotAnimation");

        mWindowManager = (WindowManager) MainActivity.this.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);

        Display defaultDisplay = mWindowManager.getDefaultDisplay();
        mDisplayMetrics = new DisplayMetrics();
        defaultDisplay.getRealMetrics(mDisplayMetrics);


        // Initialize crop area
        int width = mDisplayMetrics.widthPixels;
        int height = mDisplayMetrics.heightPixels;
        RectF outer = new RectF(0, 0, width, height);
        RectF inner = new RectF(width / 4, height / 4, width / 4 * 3, height / 4 * 3);
        mCropView.initialize(inner, outer);


        mWindowManager.addView(mCropLayout, mWindowLayoutParams);

        mCancelButton.setVisibility(View.VISIBLE);
        mScrollButton.setVisibility(View.VISIBLE);

        float[] dims = {
                mDisplayMetrics.widthPixels, mDisplayMetrics.heightPixels
        };


        try {
            Class<?> aClass = Class.forName("android.view.SurfaceControl");
            Method screenshot = aClass.getDeclaredMethod("screenshot", int.class, int
                    .class);
            screenshot.setAccessible(true);
            Bitmap mScreenBitmap = (Bitmap) screenshot.invoke(null, (int)dims[0], (int)dims[1]);
            Log.i(TAG, "" + (mScreenBitmap != null));

        } catch (ClassNotFoundException e) {
            Log.i(TAG, "ClassNotFoundException");
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            Log.i(TAG, "NoSuchMethodException");
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            Log.i(TAG, "InvocationTargetException");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            Log.i(TAG, "IllegalAccessException");
            e.printStackTrace();
        }


//        mScreenBitmap = SurfaceControl.screenshot((int) dims[0], (int) dims[1]);


        mCropView.setFocusable(true);


//        mCropView.resetInnerBounds(new RectF(mScrollBounds));


    }


    @Override
    public void confirm() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void back() {

    }

    @Override
    public void home() {

    }
}
