package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class EnergyBean {
    private int mInstantaneousValue;
    private int mMaxValue;
    private int mMinValue;

    public int getMaxValue() {
        return this.mMaxValue;
    }

    public void setMaxValue(int i) {
        this.mMaxValue = i;
    }

    public int getMinValue() {
        return this.mMinValue;
    }

    public void setMinValue(int i) {
        this.mMinValue = i;
    }

    public int getInstantaneousValue() {
        return this.mInstantaneousValue;
    }

    public void setInstantaneousValue(int i) {
        this.mInstantaneousValue = i;
    }
}
