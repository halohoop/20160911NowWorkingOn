package com.tplink.tpcompass.views;

import android.content.Context;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ICompassFragment {
    void onUpdateDirectionText(String directionText);
    Context getContext();

    void onRotatePlate(double azimuth);
}
