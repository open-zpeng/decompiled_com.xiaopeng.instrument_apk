package com.xiaopeng.cluster.config;
/* loaded from: classes.dex */
public class E28aConfig extends BaseConfig {
    @Override // com.xiaopeng.cluster.config.BaseConfig
    public float getInvalidAverageEnergyValue() {
        return 6533.5f;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public int getSupportCharge() {
        return 0;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportIslc() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportShowTime() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportSpace() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    protected int[] getSupportDrivingModes() {
        return new int[]{1, 2, 3, 4, 8, 5, 7, 9};
    }
}
