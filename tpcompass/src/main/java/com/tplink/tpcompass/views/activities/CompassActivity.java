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
import com.tplink.tpcompass.views.fragments.CompassGradienterFragment;
import com.tplink.tpcompass.views.fragments.GradienterFragment;

import java.util.ArrayList;
import java.util.List;

import static com.tplink.tpcompass.R.id.ttv_compass;

public class CompassActivity extends AppCompatActivity
        implements ICompassActivity {

    private TextureView mTtvCompass;
    private ViewPager mVpMain;
    private boolean mShowSurfaceView = false;
    private ICompassPresenter mICompassPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mICompassPresenter = new ICompassPresenterImpls(this);

        mVpMain = (ViewPager) findViewById(R.id.vp_main);

        mTtvCompass = (TextureView) findViewById(ttv_compass);
        mTtvCompass.setSurfaceTextureListener(mICompassPresenter);

        CompassGradienterFragment compassGradienterFragment = new CompassGradienterFragment();
        GradienterFragment gradienterFragment = new GradienterFragment();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(compassGradienterFragment);
        fragments.add(gradienterFragment);

        mVpMain.setAdapter(new CompassAdapter(getSupportFragmentManager(), fragments));
        mVpMain.addOnPageChangeListener(new CompassOnPageChangeListener(this));

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
