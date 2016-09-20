/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ISersorPresenter.java
 *
 * To define the methods for handling the Sensor's data
 *
 * Author huanghaiqi, Created at 2016-09-20
 *
 * Ver 1.0, 2016-09-20, huanghaiqi, Create file.
 */

package com.tplink.tpcompass.presenters;

import android.content.Context;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.location.Criteria;

public interface ISersorPresenter {

    Context getContext();

    void handleSensorEvent(SensorEvent event);

    void registerSensor(SensorEventListener sensorEventListener);

    void unregisterSensor(SensorEventListener sensorEventListener);

    void requestLocationUpdate();

    void requestLocationUpdate(Criteria criteria);

    void updateLocation();
}
