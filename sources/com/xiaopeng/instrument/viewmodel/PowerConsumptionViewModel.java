package com.xiaopeng.instrument.viewmodel;

import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.CommonConstant;
import com.xiaopeng.cluster.utils.CommonUtil;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class PowerConsumptionViewModel extends BasePowerConsumptionViewModel {
    public PowerConsumptionViewModel() {
        XILog.i(TAG, "PowerConsumptionViewModel 被初始化");
    }

    @Override // com.xiaopeng.instrument.viewmodel.BasePowerConsumptionViewModel, com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onPowerAverageEnergyConsumption(float f) {
        if (CommonUtil.compareByThreshold(f, BaseConfig.getInstance().getInvalidAverageEnergyValue(), 1.0E-4f)) {
            XILog.d(TAG, "onPowerAverageEnergyConsumption value is invalid ");
            this.mPowerAvgInvalid.postValue(CommonConstant.INVALID_TEXT_VALUE);
            return;
        }
        this.mPowerAvg.postValue(Float.valueOf(f));
    }
}
