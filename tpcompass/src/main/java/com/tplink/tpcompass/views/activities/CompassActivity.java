package com.tplink.tpcompass.views.activities;

import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.FrameLayout;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.adapters.CompassAdapter;
import com.tplink.tpcompass.listeners.CompassOnPageChangeListener;
import com.tplink.tpcompass.presenters.ICompassActivityPresenter;
import com.tplink.tpcompass.presenters.ICompassActivityPresenterImpls;
import com.tplink.tpcompass.utils.LogUtils;
import com.tplink.tpcompass.views.ICompassActivity;
import com.tplink.tpcompass.views.fragments.CompassGradienterGradienterFragment;
import com.tplink.tpcompass.views.fragments.GradienterFragment;
import com.tplink.tpcompass.widgets.CameraSurfaceView;
import com.tplink.tpcompass.widgets.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompassActivity extends AppCompatActivity
        implements ICompassActivity {

    private ViewPager mVpMain;
    private ICompassActivityPresenter mICompassActivityPresenter;
    private CirclePageIndicator mIndicator;
    private FrameLayout mFlCameraContainer;
    private CameraSurfaceView mCsv;
    private Camera mCamera;
    private boolean mIsCameraOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mICompassActivityPresenter = new ICompassActivityPresenterImpls(this);

        mVpMain = (ViewPager) findViewById(R.id.vp_main);
        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOpenCameraScence();
            }
        });
        mFlCameraContainer = (FrameLayout) findViewById(R.id.fl_camera_container);

        mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);

        CompassGradienterGradienterFragment compassGradienterFragment = new
                CompassGradienterGradienterFragment();
        GradienterFragment gradienterFragment = new GradienterFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(compassGradienterFragment);
        fragments.add(gradienterFragment);

        mVpMain.setAdapter(new CompassAdapter(getSupportFragmentManager(), fragments));
        mVpMain.addOnPageChangeListener(new CompassOnPageChangeListener(this));

        mIndicator.setViewPager(mVpMain);

    }

    @Override
    public void onShowCompassGradienterFragment() {

    }

    @Override
    public void onShowGradienterFragment() {

    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onHideCameraScence() {
        //TODO replace with animation to hide

        if (mIsCameraOpen) {
            if (mCamera != null) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            mIsCameraOpen = false;
        }

    }

    @Override
    public void onOpenCameraScence() {
        //TODO replace with animation to show

        if (!mIsCameraOpen) {
            if (mCsv == null) {
                View inflate = View.inflate(getContext(), R.layout.camera_layout, null);
                mCsv = (CameraSurfaceView) inflate.findViewById(R.id.csv);
                mFlCameraContainer.addView(inflate);
            }
            SurfaceHolder holder = mCsv.getHolder();
            mCamera = null;
            mCamera = Camera.open();
            mCamera.setDisplayOrientation(90);
            mCamera.autoFocus(null);
            mIsCameraOpen = true;
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                LogUtils.i("Camera setPreviewDisplay:" + e);
                e.printStackTrace();
                mCamera = null;
                mIsCameraOpen = false;
            }
            mIsCameraOpen = true;
        }
    }

}
