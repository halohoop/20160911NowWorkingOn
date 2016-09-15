package com.tplink.tpcompass.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Pooholah on 2016/9/15.
 */
public class CompassAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public CompassAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }
}
