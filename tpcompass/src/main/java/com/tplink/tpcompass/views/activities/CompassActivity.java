package com.tplink.tpcompass.views.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.adapters.CompassAdapter;
import com.tplink.tpcompass.listeners.CompassOnPageChangeListener;
import com.tplink.tpcompass.utils.LogUtils;
import com.tplink.tpcompass.views.ICompassActivity;
import com.tplink.tpcompass.views.fragments.CompassGradienterFragment;
import com.tplink.tpcompass.views.fragments.GradienterFragment;

import java.util.ArrayList;
import java.util.List;

public class CompassActivity extends AppCompatActivity
        implements ICompassActivity {

    private SurfaceView mSfvCompass;
    private ViewPager mVpMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSfvCompass = (SurfaceView) findViewById(R.id.sfv_compass);
        mVpMain = (ViewPager) findViewById(R.id.vp_main);

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
    public void onHideCameraScence() {

    }

    @Override
    public void onOpenCameraScence() {

    }

}
