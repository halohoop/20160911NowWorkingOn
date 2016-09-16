package com.tplink.tpcompass.utils;

/**
 * Created by Pooholah on 2016/9/17.
 */

public class CalculateUtils {
    /**
     * 平滑过度权重
     */
    private static float ALPHA = 0.01f;

    /**
     * 平滑过度方法
     *
     * @param current
     * @param last
     * @return
     */
    public static float lowPass(float current, float last) {
        return last * (1.0f - ALPHA) + current * ALPHA;
    }
}
