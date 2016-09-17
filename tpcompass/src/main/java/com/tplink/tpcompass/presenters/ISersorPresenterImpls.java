package com.tplink.tpcompass.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.utils.CalculateUtils;
import com.tplink.tpcompass.views.ICompassFragment;

/**
 * Created by Pooholah on 2016/9/16.
 */

public class ISersorPresenterImpls implements ISersorPresenter {

    private float[] mMags;
    private float[] mAccs;
    private float[] mOris;
    private float[] mRotate;
    private Context mContext;
    private SensorManager mSensorManager;
    private ICompassFragment mICompassFragment;
    private Sensor mMagsensor;
    private Sensor mAccsensor;
    private float mAzimuth;
    private float mLastAzimuth;
    private float mPitch;
    private float mLastPitch;
    private float mRoll;
    private float mLastRoll;

    public ISersorPresenterImpls(Context context, ICompassFragment iCompassFragment) {
        mMags = new float[3];
        mAccs = new float[3];
        mOris = new float[3];
        mRotate = new float[9];
        this.mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.mICompassFragment = iCompassFragment;
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
        mAzimuth = CalculateUtils.lowPass(mAzimuth, mLastAzimuth);
        mPitch = CalculateUtils.lowPass(mPitch, mLastPitch);
        mRoll = CalculateUtils.lowPass(mRoll, mLastRoll);

        String directionText = getDirectionText();
        mICompassFragment.onUpdateDirectionText(directionText + (Math.round(mAzimuth)) + "°");

        mICompassFragment.onRotatePlate(mAzimuth);

        mICompassFragment.onUpdateGradienter(mPitch, mRoll);

        mICompassFragment.onRotatePlate(mAzimuth);

        if (mAzimuth < 50) {
            mICompassFragment.onHideCameraScence();
        } else if (mAzimuth >= 50) {
            mICompassFragment.onOpenCameraScence();
        }

        mLastAzimuth = mAzimuth;
        mLastPitch = mPitch;
        mLastRoll = mRoll;
    }


    @NonNull
    private String getDirectionText() {
        String directionText;
        Context context = mICompassFragment.getContext();
        Resources resources = context.getResources();
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
        } else {
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

}
