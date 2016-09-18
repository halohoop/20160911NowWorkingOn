package com.tplink.tpcompass.presenters;

import android.content.Context;

import com.tplink.tpcompass.views.ICompassActivity;

/**
 * Created by Pooholah on 2016/9/17.
 */
public class ICompassActivityPresenterImpls implements ICompassActivityPresenter {
    private ICompassActivity mICompassActivity;

    public ICompassActivityPresenterImpls(ICompassActivity iCompassActivity) {
        this.mICompassActivity = iCompassActivity;
    }


    @Override
    public Context getContext() {
        return mICompassActivity.getContext();
    }

}
