package com.tplink.tpcompass.utils;

import android.util.Log;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class LogUtils {
    private static final String TAG = "huanghaiqi";
    private static final boolean DEBUG = true;

    public static void i(String s) {
        if (DEBUG) {
            Log.i(TAG, s);
        }
    }
}
