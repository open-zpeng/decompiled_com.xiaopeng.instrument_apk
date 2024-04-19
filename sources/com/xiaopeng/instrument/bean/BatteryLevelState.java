package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public enum BatteryLevelState {
    BATTERY_LEVEL_NORMAL,
    BATTERY_LEVEL_LOW,
    BATTERY_LEVEL_DANGEROUS,
    BATTERY_LEVEL_EMPTY,
    BATTERY_LEVEL_CHARGING;

    public static BatteryLevelState parseFromElectricityColorControlProp(int i) {
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    return BATTERY_LEVEL_NORMAL;
                }
                return BATTERY_LEVEL_EMPTY;
            }
            return BATTERY_LEVEL_LOW;
        }
        return BATTERY_LEVEL_DANGEROUS;
    }
}
