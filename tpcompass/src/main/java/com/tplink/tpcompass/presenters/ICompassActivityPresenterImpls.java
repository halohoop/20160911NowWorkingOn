/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * ICompassActivityPresenterImpls.java
 *
 * Implements of ICompassActivityPresenter
 *
 * Author huanghaiqi, Created at 2016-09-20
 *
 * Ver 1.0, 2016-09-20, huanghaiqi, Create file.
 */

package com.tplink.tpcompass.presenters;

import android.content.Context;

import com.tplink.tpcompass.views.ICompassActivity;

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
