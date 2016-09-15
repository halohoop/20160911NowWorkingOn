package com.tplink.tpcompass.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tplink.tpcompass.R;
import com.tplink.tpcompass.widgets.GradienterView;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class GradienterFragment extends Fragment {

    private GradienterView mGvCompass;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gradient, null, false);
        mGvCompass = (GradienterView) view.findViewById(R.id.gv_compass);
        return view;
    }
}
