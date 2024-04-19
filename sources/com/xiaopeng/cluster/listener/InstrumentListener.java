package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface InstrumentListener {
    void onAbnormalVehicleCondition(boolean z);

    void onAirVolume(int i);

    void onAirVolumeState(int i);

    void onDayNight(int i);

    void onIsCharging(boolean z);

    void onSpeed(int i);

    void onTemperature(float f);

    void onTemperatureVisible(boolean z);
}
