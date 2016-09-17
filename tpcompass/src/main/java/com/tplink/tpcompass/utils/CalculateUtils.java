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

    /**
     * 根据两个值计算出水平仪的旋转角度
     *
     * @param pitch
     * @param roll
     * @param gradienterRotateAngleAndValue
     * @return
     */
    public static void getGradienterRotateAngleAndValue(float pitch, float roll, float[]
            gradienterRotateAngleAndValue) {
        LogUtils.i("pitch:" + pitch);
        LogUtils.i("roll:" + roll);
        int mathRoundPitch = Math.round(pitch);
        int mathRoundRoll = Math.round(roll);

        int absMathRoundPitch = Math.abs(mathRoundPitch);
        int absMathRoundRoll = Math.abs(mathRoundRoll);

        gradienterRotateAngleAndValue[1] = Math.max(absMathRoundPitch, absMathRoundRoll);

        if (mathRoundPitch < 0 && mathRoundRoll <= 0) {
            //in room 3
            /*
                    2|1
             ----------------
               here 3|4
                     h
                     e
                     r
                     e
            */
            if (absMathRoundPitch >= absMathRoundRoll) {
                gradienterRotateAngleAndValue[0] = 0 + absMathRoundRoll;
            } else {//absMathRoundPitch < absMathRoundRoll
                gradienterRotateAngleAndValue[0] = 90 - absMathRoundPitch;
            }
        } else if (mathRoundPitch <= 0 && mathRoundRoll > 0) {
            //in room 4
            /*
                    2|1
             -----------here-
                    3|4 here
            */
            if (absMathRoundPitch <= absMathRoundRoll) {
                gradienterRotateAngleAndValue[0] = -90 + absMathRoundPitch;
            } else {//absMathRoundPitch > absMathRoundRoll
                gradienterRotateAngleAndValue[0] = 0 - absMathRoundRoll;
            }
        } else if (mathRoundPitch > 0 && mathRoundRoll >= 0) {
            //in room 1
            /*
                     h
                     e
                     r
                     e
                    2|1 here
             ----------------
                    3|4
            */
            if (absMathRoundPitch >= absMathRoundRoll) {
                gradienterRotateAngleAndValue[0] = -180 + absMathRoundRoll;
            } else {//absMathRoundPitch < absMathRoundRoll
                gradienterRotateAngleAndValue[0] = -90 - absMathRoundPitch;
            }
        } else if (mathRoundPitch >= 0 && mathRoundRoll < 0) {
            //in room 2
            /*
               here 2|1
             --here----------
                    3|4
            */
            if (absMathRoundPitch <= absMathRoundRoll) {
                gradienterRotateAngleAndValue[0] = 90 + absMathRoundPitch;
            } else {//absMathRoundPitch < absMathRoundRoll
                gradienterRotateAngleAndValue[0] = 180 - absMathRoundRoll;
            }
        } else if (mathRoundPitch == 0 && mathRoundRoll == 0) {
            //in middle
            /*
                    2|1
             -------here-----
                    3|4
            */
            gradienterRotateAngleAndValue[0] = 0;
            gradienterRotateAngleAndValue[1] = 0;
        }
    }
}
