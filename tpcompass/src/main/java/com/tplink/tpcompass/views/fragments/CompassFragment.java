package com.tplink.tpcompass.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.widgets.CompassView;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class CompassFragment extends Fragment {

    private CompassView mCvCompass;
    private TextView mTvDirectionCompass;
    private TextView mTvPointNorthLeftCompass;
    private TextView mTvPointNorthRightCompass;
    private TextView mTvNorthLatitude;
    private TextView mTvEastLongitude;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compass, null, false);
        mCvCompass = (CompassView) view.findViewById(R.id.cv_compass);
        mTvDirectionCompass = (TextView) view.findViewById(R.id.tv_direction_compass);
        mTvPointNorthLeftCompass = (TextView) view.findViewById(R.id.tv_point_north_left_compass);
        mTvPointNorthRightCompass = (TextView) view.findViewById(R.id.tv_point_north_right_compass);
        mTvNorthLatitude = (TextView) view.findViewById(R.id.tv_north_latitude);
        mTvEastLongitude = (TextView) view.findViewById(R.id.tv_east_longitude);
        return view;
    }
}