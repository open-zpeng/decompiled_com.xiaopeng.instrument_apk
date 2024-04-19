package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IPowerConsumptionListener {
    void onPowerAverageEnergyConsumption(float f);

    void onPowerCurveValue(float f);

    void onRefreshPowerVEHpwrMax(int i);

    void onRefreshPowerVEHpwrMin(int i);

    void onRefreshPowerVEHpwrValue(int i);

    void onResAvailPower(int i);
}
