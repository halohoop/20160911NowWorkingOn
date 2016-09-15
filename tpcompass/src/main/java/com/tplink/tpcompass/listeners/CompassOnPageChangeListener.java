package com.tplink.tpcompass.listeners;

import android.support.v4.view.ViewPager;

import com.tplink.tpcompass.views.ICompassActivity;

/**
 * Created by Pooholah on 2016/9/15.
 */
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
            mICompassActivity.onShowCompassFragment();
        } else if (1 == position) {
            mICompassActivity.onShowGradienterFragment();
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
