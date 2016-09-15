package com.tplink.tpcompass.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Pooholah on 2016/9/15.
 */

public class ScreenUtils {

    /**
     * Get screen size
     * @param context
     * @return point point.x is width and point.y is height
     */
    public static Point getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point out = new Point();
        display.getSize(out);
        return out;
    }
}
