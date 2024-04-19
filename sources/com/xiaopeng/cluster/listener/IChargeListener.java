package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IChargeListener {
    void onAppointmentHour(String str);

    void onAppointmentMinute(String str);

    void onChargeStatus(int i);

    void onChargingState(int i);

    void onCommonElectricQuantityStr(String str);

    void onCompleteHour(String str);

    void onCompleteMinute(String str);

    void onEsocmaxchg(int i);

    void onHeatBatteryHour(String str);

    void onHeatBatteryMinute(String str);

    void onHeatBatteryTipsState(int i);

    void onPowerInformationCurrent(String str);

    void onPowerInformationVisible(boolean z);

    void onPowerInformationVoltage(String str);

    void onRandisDisplayType(int i);

    void onSuperChrgFlg(boolean z);

    void onTimeRemainingHour(String str);

    void onTimeRemainingMinute(String str);

    void onTimeRemainingTipsState(int i);
}
