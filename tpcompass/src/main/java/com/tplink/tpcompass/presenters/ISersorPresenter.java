package com.tplink.tpcompass.presenters;

import android.hardware.SensorEvent;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ISersorPresenter {

    void handleSensorEvent(SensorEvent event);
}
