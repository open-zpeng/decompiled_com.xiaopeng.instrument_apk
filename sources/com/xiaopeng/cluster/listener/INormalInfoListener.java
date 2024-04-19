package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface INormalInfoListener {
    void onBatteryLifeStandard(int i);

    default void onMorningOrAfternoon(int i) {
    }

    void onNormalInfoTemperature(int i);

    default void onTime(String str) {
    }

    default void onTimePattern(int i) {
    }
}
