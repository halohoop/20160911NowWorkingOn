package com.tplink.tpcompass.views;

import android.content.Context;

/**
 * Created by Pooholah on 2016/9/15.
 */

public interface ICompassActivity extends ICameraScence {

    void onShowCompassFragment();

    void onShowGradienterFragment();

    Context getContext();
}
