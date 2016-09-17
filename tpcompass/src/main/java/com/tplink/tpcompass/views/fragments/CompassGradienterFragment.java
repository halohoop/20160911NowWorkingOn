package com.tplink.tpcompass.views.fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.presenters.ISersorPresenter;
import com.tplink.tpcompass.presenters.ISersorPresenterImpls;
import com.tplink.tpcompass.views.ICompassActivity;
import com.tplink.tpcompass.views.ICompassFragment;
import com.tplink.tpcompass.widgets.CompassGradienterView;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class CompassGradienterFragment extends Fragment
        implements SeekBar.OnSeekBarChangeListener,
        SensorEventListener, ICompassFragment {

    private CompassGradienterView mCvCompass;
    private TextView mTvDirectionCompass;
    private TextView mTvPointNorthLeftCompass;
    private TextView mTvPointNorthRightCompass;
    private TextView mTvNorthLatitude;
    private TextView mTvEastLongitude;

    private ICompassActivity mICompassActivity;

    //presenter
    private ISersorPresenter mISersorPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mISersorPresenter = new ISersorPresenterImpls(getContext(), this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mICompassActivity = (ICompassActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        mISersorPresenter.registerSensor(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        mISersorPresenter.unregisterSensor(this);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, null, false);
        mCvCompass = (CompassGradienterView) view.findViewById(R.id.cv_compass);
        mTvDirectionCompass = (TextView) view.findViewById(R.id.tv_direction_compass);
        mTvPointNorthLeftCompass = (TextView) view.findViewById(R.id.tv_point_north_left_compass);
        mTvPointNorthRightCompass = (TextView) view.findViewById(R.id.tv_point_north_right_compass);
        mTvNorthLatitude = (TextView) view.findViewById(R.id.tv_north_latitude);
        mTvEastLongitude = (TextView) view.findViewById(R.id.tv_east_longitude);
        //test
        SeekBar svRotation = (SeekBar) view.findViewById(R.id.sb_rotation);
        svRotation.setOnSeekBarChangeListener(this);
        //test
        return view;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.sb_rotation:
                mCvCompass.setNorthOffsetAngle(progress);
                break;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mISersorPresenter.handleSensorEvent(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onUpdateDirectionText(String directionText) {
        mTvDirectionCompass.setText(directionText);
    }

    @Override
    public void onRotatePlate(float azimuth) {
        mCvCompass.setNorthOffsetAngle(360 - azimuth);
    }

    @Override
    public void onUpdateGradienter(float pitch, float roll) {
        mCvCompass.setGradienterData(pitch, roll);
    }

    @Override
    public void onHideCameraScence() {
        mTvPointNorthLeftCompass.setVisibility(View.INVISIBLE);
        mTvPointNorthRightCompass.setVisibility(View.INVISIBLE);

        //for Surfaceview in activity
        if (mICompassActivity != null) {
            mICompassActivity.onHideCameraScence();
        }
    }

    @Override
    public void onOpenCameraScence() {
        //for Surfaceview in activity
        if (mICompassActivity != null) {
            mICompassActivity.onOpenCameraScence();
        }
    }
}
