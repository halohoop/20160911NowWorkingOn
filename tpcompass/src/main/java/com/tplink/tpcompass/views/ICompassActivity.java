/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ICompassActivity.java
 *
 * Activty views callback
 *
 * Author huanghaiqi, Created at 2016-09-20
 *
 * Ver 1.0, 2016-09-20, huanghaiqi, Create file.
 */

package com.tplink.tpcompass.views;

import android.content.Context;

public interface ICompassActivity extends ICameraScence {

    void onShowCompassGradienterFragment();

    void onShowGradienterFragment();

    Context getContext();

}
