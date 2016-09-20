/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * CompassAdapter.java
 *
 * FragmentPagerAdapter for main ViewPager
 *
 * Author huanghaiqi, Created at 2016-09-20
 *
 * Ver 1.0, 2016-09-20, huanghaiqi, Create file.
 */

package com.tplink.tpcompass.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class CompassAdapter extends FragmentPagerAdapter {
    private List<Fragment> mFragments;

    public CompassAdapter(FragmentManager fm, List<Fragment> fragments) {
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

