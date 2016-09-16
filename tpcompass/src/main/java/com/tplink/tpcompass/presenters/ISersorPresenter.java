package com.tplink.tpcompass.presenters;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ISersorPresenter {

    void handleSensorEvent(SensorEvent event);

    void registerSensor(SensorEventListener sensorEventListener);

    void unregisterSensor(SensorEventListener sensorEventListener);

}
