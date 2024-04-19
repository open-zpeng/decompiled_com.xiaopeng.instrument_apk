package com.xiaopeng.cluster.config;
/* loaded from: classes.dex */
public class D55Config extends BaseConfig {
    @Override // com.xiaopeng.cluster.config.BaseConfig
    public float getInvalidAverageEnergyValue() {
        return 25.5f;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public int getSupportCharge() {
        return 1;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportNaviSR() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportNoSRToShowTrafficLight() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportShowSpeedUseLeftScroll() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportShowTime() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public int[] getSupportDrivingModes() {
        return new int[]{1, 2, 3};
    }
}
