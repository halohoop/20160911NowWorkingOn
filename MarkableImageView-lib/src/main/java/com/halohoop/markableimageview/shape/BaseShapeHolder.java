/*
 * Copyright (C) 2016, TP-LINK TECHNOLOGIES CO., LTD.
 *
 * BaseShapeHolder.java
 *
 * Description
 *
 * Author huanghaiqi
 *
 * Ver 1.0, 2016-09-09, huanghaiqi, Create file
 */

package com.halohoop.markableimageview.shape;

public class BaseShapeHolder {

    protected int mOrder;
    protected Mode mMode = Mode.EDITING;
    protected TouchableArea mTouchableArea;

    protected enum Mode {
        NORMAL, EDITING
    }

    public Mode getMode() {
        return mMode;
    }

    public void setMode(Mode mode) {
        this.mMode = mode;
    }

    public int getOder() {
        return mOrder;
    }

    public void setOder(int oder) {
        this.mOrder = oder;
    }

    public TouchableArea getTouchableArea() {
        return mTouchableArea;
    }

    public void setTouchableArea(TouchableArea touchableArea) {
        this.mTouchableArea = touchableArea;
    }

    @Override
    public String toString() {
        return "BaseShapeHolder{" +
                "mOrder=" + mOrder +
                ", mMode=" + mMode +
                ", mTouchableArea=" + mTouchableArea +
                '}';
    }
}
