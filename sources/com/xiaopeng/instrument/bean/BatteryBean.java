package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class BatteryBean {
    private int levelValue = 0;
    private BatteryLevelState colorState = BatteryLevelState.BATTERY_LEVEL_EMPTY;

    public int getLevelValue() {
        return this.levelValue;
    }

    public void setLevelValue(int i) {
        this.levelValue = i;
    }

    public BatteryLevelState getColorState() {
        return this.colorState;
    }

    public void setColorState(BatteryLevelState batteryLevelState) {
        this.colorState = batteryLevelState;
    }
}
