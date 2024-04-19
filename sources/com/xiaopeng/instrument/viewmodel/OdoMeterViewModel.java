package com.xiaopeng.instrument.viewmodel;

import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.CommonConstant;
import com.xiaopeng.cluster.utils.CommonUtil;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class OdoMeterViewModel extends AbstractOdoMeterViewModel {
    private static final String TAG = "OdoMeterViewModel";

    @Override // com.xiaopeng.instrument.viewmodel.AbstractOdoMeterViewModel
    void action(float f) {
        String valueOf = String.valueOf(f);
        if (CommonUtil.compareByThreshold(f, BaseConfig.getInstance().getInvalidAverageEnergyValue(), 1.0E-4f)) {
            XILog.d(TAG, "onPowerAverageEnergyConsumption value is invalid ");
            valueOf = CommonConstant.INVALID_TEXT_VALUE;
        }
        this.mAverageEnergyCost.postValue(valueOf);
    }
}
