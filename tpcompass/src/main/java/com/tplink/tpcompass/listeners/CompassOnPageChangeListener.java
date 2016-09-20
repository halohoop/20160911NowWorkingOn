/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * CompassOnPageChangeListener.java
 *
 * Callback when page change
 *
 * Author huanghaiqi, Created at 2016-09-20
 *
 * Ver 1.0, 2016-09-20, huanghaiqi, Create file.
 */

package com.tplink.tpcompass.listeners;

import android.support.v4.view.ViewPager;

import com.tplink.tpcompass.views.ICompassActivity;

public class CompassOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private ICompassActivity mICompassActivity;

    public CompassOnPageChangeListener(ICompassActivity iCompassActivity) {
        this.mICompassActivity = iCompassActivity;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (0 == position) {
            mICompassActivity.onShowCompassGradienterFragment();
        } else if (1 == position) {
            mICompassActivity.onShowGradienterFragment();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
