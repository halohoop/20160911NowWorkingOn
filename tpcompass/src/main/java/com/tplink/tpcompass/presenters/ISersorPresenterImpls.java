package com.tplink.tpcompass.presenters;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.views.ICompassFragment;

/**
 * Created by Pooholah on 2016/9/16.
 */

public class ISersorPresenterImpls implements ISersorPresenter {

    private float[] mMags;
    private float[] mAccs;
    private float[] mOris;
    private float[] mRotate;
    private SensorManager mSensorManager;
    private ICompassFragment mICompassFragment;

    public ISersorPresenterImpls(SensorManager sensorManager, ICompassFragment iCompassFragment) {
        mMags = new float[3];
        mAccs = new float[3];
        mOris = new float[3];
        mRotate = new float[9];
        this.mSensorManager = sensorManager;
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

        /*oris[0]      :azimuth 方向角，但用（磁场+加速度）得到的数据范围是（-180～180）,也就是说，0表示正北，90表示正东，180/-180表示正南，-90表示正西。
        oris[1]      :pitch 倾斜角  即由静止状态开始，围绕X轴前后翻转
        oris[2]      :roll 旋转角 即由静止状态开始，围绕Y轴左右翻转*/
        double azimuth = Math.toDegrees(mOris[0]);
        if (azimuth < 0) {
            azimuth = azimuth + 360;
        }
        //
        double pitch = Math.toDegrees(mOris[1]);
        double roll = Math.toDegrees(mOris[2]);

        String directionText = "";
        Context context = mICompassFragment.getContext();
        Resources resources = context.getResources();
        if (azimuth >= 0 && azimuth <= 22 ||
                azimuth >= 338 && azimuth <= 359) {
            directionText = resources.getString(R.string.north);
        } else if (azimuth >= 23 && azimuth <= 67) {
            directionText = resources.getString(R.string.northeast);
        } else if (azimuth >= 68 && azimuth <= 112) {
            directionText = resources.getString(R.string.east);
        } else if (azimuth >= 113 && azimuth <= 157) {
            directionText = resources.getString(R.string.southeast);
        } else if (azimuth >= 158 && azimuth <= 202) {
            directionText = resources.getString(R.string.south);
        } else if (azimuth >= 203 && azimuth <= 247) {
            directionText = resources.getString(R.string.southwest);
        } else if (azimuth >= 248 && azimuth <= 292) {
            directionText = resources.getString(R.string.west);
        } else {
            directionText = resources.getString(R.string.northwest);
        }
        mICompassFragment.onUpdateDirectionText(directionText + ((int) azimuth) + "°");
        mICompassFragment.onRotatePlate(azimuth);
    }
}
