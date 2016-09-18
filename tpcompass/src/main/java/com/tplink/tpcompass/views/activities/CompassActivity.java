package com.tplink.tpcompass.views.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.TextureView;
import android.view.View;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.adapters.CompassAdapter;
import com.tplink.tpcompass.listeners.CompassOnPageChangeListener;
import com.tplink.tpcompass.presenters.ICompassPresenter;
import com.tplink.tpcompass.presenters.ICompassPresenterImpls;
import com.tplink.tpcompass.utils.LogUtils;
import com.tplink.tpcompass.views.ICompassActivity;
import com.tplink.tpcompass.views.fragments.CompassGradienterGradienterFragment;
import com.tplink.tpcompass.views.fragments.GradienterFragment;
import com.tplink.tpcompass.widgets.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

public class CompassActivity extends AppCompatActivity
        implements ICompassActivity {

    private TextureView mTtvCompass;
    private ViewPager mVpMain;
    private boolean mShowSurfaceView = false;
    private ICompassPresenter mICompassPresenter;
    private CirclePageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mICompassPresenter = new ICompassPresenterImpls(this);

        mVpMain = (ViewPager) findViewById(R.id.vp_main);

        mTtvCompass = (TextureView) findViewById(R.id.ttv_compass);
        mTtvCompass.setSurfaceTextureListener(mICompassPresenter);

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
    public void onShowCompassFragment() {
        LogUtils.i("显示Compass");
    }

    @Override
    public void onShowGradienterFragment() {
        LogUtils.i("显示Gradienter");
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void onHideCameraScence() {
        if (mShowSurfaceView) {
            LogUtils.i(" huanghaiqi onHideCameraScence");
            mShowSurfaceView = false;
            //TODO replace with animation to hide
            mTtvCompass.setVisibility(View.GONE);
        }
    }

    @Override
    public void onOpenCameraScence() {
        if (!mShowSurfaceView) {
            LogUtils.i("huanghaiqi onOpenCameraScence");
            mShowSurfaceView = true;
            //TODO replace with animation to show
            mTtvCompass.setVisibility(View.VISIBLE);
        }
    }

}
