package com.xiaopeng.instrument.bean;

import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
/* loaded from: classes.dex */
public enum ChargingState {
    CHARGING_STATE_UNKNOWN(-1, App.getInstance().getApplicationContext().getString(R.string.charge_status_unknown), ""),
    CHARGING_STATE_NOT_CONNECTED(0, App.getInstance().getApplicationContext().getString(R.string.charge_status_not_connected), ""),
    CHARGING_STATE_PREPARING(1, App.getInstance().getApplicationContext().getString(R.string.charge_status_prepare), ""),
    CHARGING_STATE_CHARGING(2, App.getInstance().getApplicationContext().getString(R.string.charge_status_charging), App.getInstance().getApplicationContext().getString(R.string.charge_expect_remain_time)),
    CHARGING_STATE_CHARGING_FINISH(3, App.getInstance().getApplicationContext().getString(R.string.charge_status_finish), ""),
    CHARGING_STATE_ERROR(4, App.getInstance().getApplicationContext().getString(R.string.charge_status_fail), ""),
    CHARGING_STATE_CONNECTED_UNABLE_TO_STARTUP(5, App.getInstance().getApplicationContext().getString(R.string.charge_status_connected_unable_to_startup), ""),
    CHARGING_STATE_SCHEDULE_CHARGE(6, App.getInstance().getApplicationContext().getString(R.string.charge_status_appoint), App.getInstance().getApplicationContext().getString(R.string.charge_begin_time)),
    CHARGING_STATE_HEAT_BATTERY(7, App.getInstance().getApplicationContext().getString(R.string.charge_status_heating), App.getInstance().getApplicationContext().getString(R.string.charge_remain)),
    CHARGING_STATE_COOLING_BATTERY(8, App.getInstance().getApplicationContext().getString(R.string.charge_status_cooling), ""),
    CHARGING_STATE_PREPARE_DISCHARGE(9, App.getInstance().getApplicationContext().getString(R.string.discharge_status_prepare), ""),
    CHARGING_STATE_DISCHARGING(10, App.getInstance().getApplicationContext().getString(R.string.discharge_status_discharging), ""),
    CHARGING_STATE_DISCHARGE_ERROR(11, App.getInstance().getApplicationContext().getString(R.string.discharge_status_fail), ""),
    CHARGING_STATE_STOP_DISCHARGE(12, App.getInstance().getApplicationContext().getString(R.string.discharge_status_stopped), "");
    
    public String mAddOnDescription;
    public int mData;
    public String mDescription;

    ChargingState(int i, String str, String str2) {
        this.mData = i;
        this.mDescription = str;
        this.mAddOnDescription = str2;
    }

    public static ChargingState parse(int i) {
        ChargingState[] values;
        for (ChargingState chargingState : values()) {
            if (chargingState.getData() == i) {
                return chargingState;
            }
        }
        return CHARGING_STATE_UNKNOWN;
    }

    public int getData() {
        return this.mData;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public String getAddOnDescription() {
        return this.mAddOnDescription;
    }
}
