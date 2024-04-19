package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IOdoMeterListener {
    void onAfterChargingUnits(String str);

    void onAfterChargingValue(String str);

    void onAverageUnits(String str);

    void onAverageValue(String str);

    void onElapsedTimeUnits(String str);

    void onElapsedTimeValue(String str);

    void onSubtotalAUnits(String str);

    void onSubtotalAValue(String str);

    void onSubtotalBUnits(String str);

    void onSubtotalBValue(String str);

    void onThisTimeUnits(String str);

    void onThisTimeValue(String str);

    void onTotalUnits(String str);

    void onTotalValue(String str);
}
