package com.tplink.tpcompass.views;

import android.content.Context;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ICompassFragment {
    Context getContext();

    void onUpdateDirectionText(String directionText);

    void onRotatePlate(float azimuth);
}
