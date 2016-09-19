package com.tplink.tpcompass.presenters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.utils.CalculateUtils;
import com.tplink.tpcompass.utils.LogUtils;
import com.tplink.tpcompass.views.ICompassGradienterFragment;

/**
 * Created by Pooholah on 2016/9/16.
 */

public class ISersorPresenterImpls implements ISersorPresenter, LocationListener {

    private float[] mMags;
    private float[] mAccs;
    private float[] mOris;
    private float[] mRotate;
    private Context mContext;
    private SensorManager mSensorManager;
    private ICompassGradienterFragment mICompassGradienterFragment;
    private Sensor mMagsensor;
    private Sensor mAccsensor;
    private float mAzimuth;
    private float mLastAzimuth;
    private float mPitch;
    private float mLastPitch;
    private float mRoll;
    private float mLastRoll;
    private final LocationManager mLocationManager;
    private String mBestProvider;

    public ISersorPresenterImpls(Context context, ICompassGradienterFragment
            iCompassGradienterFragment) {
        mMags = new float[3];
        mAccs = new float[3];
        mOris = new float[3];
        mRotate = new float[9];
        this.mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.mICompassGradienterFragment = iCompassGradienterFragment;

        //LocationManager
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public Context getContext() {
        return mContext;
    }

    @Override
    public void handleSensorEvent(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            mMags = event.values;//x轴地磁强度
        }

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            mAccs = event.values;//x轴地磁强度
        }

        mSensorManager.getRotationMatrix(mRotate, null, mAccs, mMags);
        mSensorManager.getOrientation(mRotate, mOris);

        /*
            oris[0]      :mAzimuth 方向角，但用（磁场+加速度）得到的数据范围是（-180～180）,
                也就是说，0表示正北，90表示正东，180/-180表示正南，-90表示正西。
            oris[1]      :pitch 倾斜角  即由静止状态开始，围绕X轴前后翻转
            oris[2]      :roll 旋转角 即由静止状态开始，围绕Y轴左右翻转
        */
        mAzimuth = (float) Math.toDegrees(mOris[0]);
        mPitch = (float) Math.toDegrees(mOris[1]);
        mRoll = (float) Math.toDegrees(mOris[2]);
        if (mAzimuth < 0) {
            mAzimuth = 360 + mAzimuth;
        }


        mAzimuth = CalculateUtils.normalizeDegree(mAzimuth);
//        mAzimuth = CalculateUtils.lowPass(mAzimuth, mLastAzimuth);
//        mPitch = CalculateUtils.lowPass(mPitch, mLastPitch);
//        mRoll = CalculateUtils.lowPass(mRoll, mLastRoll);

        String directionText = getDirectionText();
        mICompassGradienterFragment.onUpdateDirectionText(directionText + (Math.round(mAzimuth))
                + "°");

        mICompassGradienterFragment.onRotatePlate(mAzimuth);

        mICompassGradienterFragment.onUpdateGradienter(mPitch, mRoll);

        mICompassGradienterFragment.onRotatePlate(mAzimuth);

        LogUtils.i("mAzimuth:" + mAzimuth);
        LogUtils.i("mPitch:" + mPitch);
        LogUtils.i("mRoll:" + mRoll);

        if (Math.abs(mPitch) >= 50) {
            mICompassGradienterFragment.onOpenCameraScence();
        } else {
            mICompassGradienterFragment.onHideCameraScence();
        }

        mLastAzimuth = mAzimuth;
        mLastPitch = mPitch;
        mLastRoll = mRoll;
    }


    @NonNull
    private String getDirectionText() {
        Context context = mICompassGradienterFragment.getContext();
        Resources resources = context.getResources();
        String directionText = resources.getString(R.string.north);
        if (mAzimuth >= 0 && mAzimuth <= 22 ||
                mAzimuth >= 338 && mAzimuth <= 359) {
            directionText = resources.getString(R.string.north);
        } else if (mAzimuth >= 23 && mAzimuth <= 67) {
            directionText = resources.getString(R.string.northeast);
        } else if (mAzimuth >= 68 && mAzimuth <= 112) {
            directionText = resources.getString(R.string.east);
        } else if (mAzimuth >= 113 && mAzimuth <= 157) {
            directionText = resources.getString(R.string.southeast);
        } else if (mAzimuth >= 158 && mAzimuth <= 202) {
            directionText = resources.getString(R.string.south);
        } else if (mAzimuth >= 203 && mAzimuth <= 247) {
            directionText = resources.getString(R.string.southwest);
        } else if (mAzimuth >= 248 && mAzimuth <= 292) {
            directionText = resources.getString(R.string.west);
        } else if (mAzimuth <= 337 && mAzimuth >= 293) {
            directionText = resources.getString(R.string.northwest);
        }
        return directionText;
    }

    @Override
    public void registerSensor(SensorEventListener sensorEventListener) {
        //Sensor.TYPE_ORIENTATION 在新版本中已经放弃
        mMagsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mAccsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

//        mSensorManager.registerListener(sensorEventListener, mMagsensor, SensorManager
//                .SENSOR_DELAY_GAME);
//        mSensorManager.registerListener(sensorEventListener, mAccsensor, SensorManager
//                .SENSOR_DELAY_GAME);
        mSensorManager.registerListener(sensorEventListener, mMagsensor, SensorManager
                .SENSOR_DELAY_FASTEST);
        mSensorManager.registerListener(sensorEventListener, mAccsensor, SensorManager
                .SENSOR_DELAY_FASTEST);
    }

    @Override
    public void unregisterSensor(SensorEventListener sensorEventListener) {
        mSensorManager.unregisterListener(sensorEventListener, mMagsensor);
        mSensorManager.unregisterListener(sensorEventListener, mAccsensor);
    }

    @Override
    public void requestLocationUpdate() {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);

        requestLocation(criteria);
    }

    private final void requestLocation(Criteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Cannot deal with a criteria");
        }
        mBestProvider = mLocationManager.getBestProvider(criteria, true);
        if (mBestProvider != null) {
            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                    .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates(mBestProvider, 2000, 1, this);
        } else {
//            LogUtils.i("mBestProvider is null");
        }
    }

    @Override
    public void requestLocationUpdate(Criteria criteria) {
        requestLocation(criteria);
    }

    @Override
    public void updateLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                .ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (mBestProvider != null) {
            Location lastKnownLocation = mLocationManager.getLastKnownLocation(mBestProvider);
            if (lastKnownLocation != null) {
                double longitude = lastKnownLocation.getLongitude();
                double altitude = lastKnownLocation.getAltitude();
                mICompassGradienterFragment.onUpdateLocation(longitude, altitude);
            } else {
                requestLocationUpdate();
            }
        } else {
            requestLocationUpdate();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double longitude = location.getLongitude();
        double altitude = location.getAltitude();
        mICompassGradienterFragment.onUpdateLocation(longitude, altitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
