package com.tplink.tpcompass.presenters;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Criteria;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ISersorPresenter {

    Context getContext();

    void handleSensorEvent(SensorEvent event);

    void registerSensor(SensorEventListener sensorEventListener);

    void unregisterSensor(SensorEventListener sensorEventListener);

    void requestLocationUpdate();

    void requestLocationUpdate(Criteria criteria);

    void updateLocation();
}
