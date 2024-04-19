package com.xiaopeng.cluster.config;
/* loaded from: classes.dex */
public class E38Config extends BaseConfig {
    @Override // com.xiaopeng.cluster.config.BaseConfig
    public float getInvalidAverageEnergyValue() {
        return 6533.5f;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public int getSupportCharge() {
        return 3;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportArcPowerConsumptionCurveView() {
        return false;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportDecimalRemainDistance() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportIslc() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportPerspChargeView() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportShowSpeedUseLeftScroll() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportSpace() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    protected int[] getSupportDrivingModes() {
        return new int[]{1, 2, 3, 4, 7, 6, 5, 9};
    }
}
