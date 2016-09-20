/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ICompassGradienterFragment.java
 *
 * Compass and gradienter page views callback
 *
 * Author huanghaiqi, Created at 2016-09-20
 *
 * Ver 1.0, 2016-09-20, huanghaiqi, Create file.
 */

package com.tplink.tpcompass.views;

import android.content.Context;

public interface ICompassGradienterFragment extends ICameraScence {
    Context getContext();

    void onUpdateDirectionText(String directionText);

    void onRotatePlate(float azimuth);

    void onUpdateGradienter(float pitch, float roll);

    void onUpdateLocation(double longitude, double altitude);
}
