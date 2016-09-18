package com.tplink.tpcompass.views;

import android.content.Context;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ICompassGradienterFragment extends ICameraScence {
    Context getContext();

    void onUpdateDirectionText(String directionText);

    void onRotatePlate(float azimuth);

    void onUpdateGradienter(float pitch, float roll);

    void onUpdateLocation(double longitude, double altitude);
}
