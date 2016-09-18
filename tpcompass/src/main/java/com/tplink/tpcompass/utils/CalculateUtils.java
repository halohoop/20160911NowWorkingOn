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
     * @return isScreenFacingTheSky 根据roll值判断屏幕是否面向天空
     * true 面向天空 false 面向地面
     */
    public static boolean getGradienterRotateAngleAndValue(float pitch, float roll, float[]
            gradienterRotateAngleAndValue) {
        LogUtils.i("pitch:" + pitch);
        LogUtils.i("roll:" + roll);
        int mathRoundPitch = Math.round(pitch);
        int mathRoundRoll = Math.round(roll);

        int absMathRoundPitch = Math.abs(mathRoundPitch);
        int absMathRoundRoll = Math.abs(mathRoundRoll);

        if (absMathRoundRoll >= 177 || absMathRoundRoll <= 2) {
            LogUtils.i("absMathRoundRoll:" + absMathRoundRoll);
        }

        if (absMathRoundRoll <= 90) {
            gradienterRotateAngleAndValue[1] = Math.max(absMathRoundPitch, absMathRoundRoll);
            getGradienterRotationByScreenFacingSkyOrFloor(gradienterRotateAngleAndValue,
                    mathRoundPitch, mathRoundRoll, absMathRoundPitch, absMathRoundRoll, true);
            return true;//face the sky
        } else {//absMathRoundRoll > 90
            //TODO
            gradienterRotateAngleAndValue[1] = Math.max(absMathRoundPitch, absMathRoundRoll);
            getGradienterRotationByScreenFacingSkyOrFloor(gradienterRotateAngleAndValue,
                    mathRoundPitch, mathRoundRoll, absMathRoundPitch, absMathRoundRoll, false);
            return false;//face the floor
        }


    }

    private static void getGradienterRotationByScreenFacingSkyOrFloor(
            float[] gradienterRotateAngleAndValue,
            int mathRoundPitch,
            int mathRoundRoll,
            int absMathRoundPitch,
            int absMathRoundRoll, boolean isScreenFacingTheSky) {
        if (isScreenFacingTheSky) {
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
        } else {//the screen is facing the floor
            if ((mathRoundPitch < 0 && mathRoundPitch > -90)
                    && (mathRoundRoll >= -180 && mathRoundRoll < -90)) {
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
                //增量
                int deltaMathRoundPitch = Math.abs(0 - mathRoundPitch);
                int deltaMathRoundRoll = Math.abs(-180 - mathRoundRoll);
                if (deltaMathRoundPitch > deltaMathRoundRoll) {
                    gradienterRotateAngleAndValue[0] = 90 - deltaMathRoundPitch;
                } else {
                    gradienterRotateAngleAndValue[0] = 0 + deltaMathRoundRoll;
                }
            } else if ((mathRoundPitch <= 0 && mathRoundPitch > -90)
                    && (mathRoundRoll < 180 && mathRoundRoll > 90)) {
                //in room 4
                /*
                        2|1
                 -----------here-
                        3|4 here
                */
                //增量
                int deltaMathRoundPitch = Math.abs(0 - mathRoundPitch);
                int deltaMathRoundRoll = Math.abs(180 - mathRoundRoll);
                if (deltaMathRoundPitch > deltaMathRoundRoll) {
                    gradienterRotateAngleAndValue[0] = -90 + deltaMathRoundPitch;
                } else {
                    gradienterRotateAngleAndValue[0] = 0 - deltaMathRoundRoll;
                }
            } else if ((mathRoundPitch > 0 && mathRoundPitch < 90)
                    && (mathRoundRoll <= 180 && mathRoundRoll > 90)) {
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
                //增量
                int deltaMathRoundPitch = Math.abs(0 - mathRoundPitch);
                int deltaMathRoundRoll = Math.abs(180 - mathRoundRoll);
                if (deltaMathRoundPitch > deltaMathRoundRoll) {
                    gradienterRotateAngleAndValue[0] = -90 - deltaMathRoundPitch;
                } else {
                    gradienterRotateAngleAndValue[0] = -180 + deltaMathRoundRoll;
                }
            } else if ((mathRoundPitch >= 0 && mathRoundPitch < 90)
                    && (mathRoundRoll > -180 && mathRoundRoll < -90)) {
                //in room 2
                /*
                   here 2|1
                 --here----------
                        3|4
                */
                //增量
                int deltaMathRoundPitch = Math.abs(0 - mathRoundPitch);
                int deltaMathRoundRoll = Math.abs(-180 - mathRoundRoll);
                if (deltaMathRoundPitch > deltaMathRoundRoll) {
                    gradienterRotateAngleAndValue[0] = 90 + deltaMathRoundPitch;
                } else {
                    gradienterRotateAngleAndValue[0] = 180 - deltaMathRoundRoll;
                }
            } else if (mathRoundPitch == 0 && (mathRoundRoll == -180 || mathRoundRoll == 180)) {
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

    public static String getFormatLongitudeOrAltitudeValue(double value) {
        int degrees = (int) value;
        double v1 = value - degrees;
        double v2 = v1 * 60;
        int minutes = (int) v2;
        double v3 = v2 - minutes;
        double v4 = v3 * 60;
        int seconds = (int) v4;
        return " " + degrees + "°" + minutes + "'" + seconds + "\"";
    }
}
