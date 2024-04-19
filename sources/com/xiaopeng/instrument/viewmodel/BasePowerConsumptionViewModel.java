package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IPowerConsumptionListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.EnergyBean;
/* loaded from: classes.dex */
public class BasePowerConsumptionViewModel extends ViewModel implements IPowerConsumptionListener {
    public static final String TAG = "BasePowerConsumptionViewModel";
    private static EnergyBean mEnergyBean = new EnergyBean();
    protected final MutableLiveData<Float> mPowerAvg = new MutableLiveData<>();
    private final MutableLiveData<Float> mPowerCurve = new MutableLiveData<>();
    protected final MutableLiveData<String> mPowerAvgInvalid = new MutableLiveData<>();
    private final MutableLiveData<Integer> mResAvaPower = new MutableLiveData<>();
    private final MutableLiveData<EnergyBean> mInstantaneousPower = new MutableLiveData<>();
    private final int UI_VEH_ELC_CON_SP_PER_100_MAX_VALUE = 60;
    private final int UI_VEH_ELC_CON_SP_PER_100_MIN_VALUE = -20;

    @Override // com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onPowerAverageEnergyConsumption(float f) {
    }

    public BasePowerConsumptionViewModel() {
        ClusterManager.getInstance().addPowerConsumptionListener(this);
    }

    public void setEnergyBean(EnergyBean energyBean) {
        mEnergyBean = energyBean;
    }

    public MutableLiveData<String> getPowerAvgInvalid() {
        return this.mPowerAvgInvalid;
    }

    public MutableLiveData<Integer> getResAvaPower() {
        return this.mResAvaPower;
    }

    public MutableLiveData<Float> getPowerCurve() {
        return this.mPowerCurve;
    }

    public MutableLiveData<Float> getPowerAvg() {
        return this.mPowerAvg;
    }

    public MutableLiveData<EnergyBean> getInstantaneousPower() {
        return this.mInstantaneousPower;
    }

    @Override // com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onPowerCurveValue(float f) {
        if (f > 60.0f) {
            f = 60.0f;
        } else if (f < -20.0f) {
            f = -20.0f;
        }
        this.mPowerCurve.postValue(Float.valueOf(f));
    }

    @Override // com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onResAvailPower(int i) {
        this.mResAvaPower.postValue(Integer.valueOf(i));
    }

    private boolean hasMaxMinInstantaneousValue() {
        EnergyBean energyBean = mEnergyBean;
        return (energyBean == null || energyBean.getMaxValue() == 0 || mEnergyBean.getMinValue() == 0) ? false : true;
    }

    @Override // com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onRefreshPowerVEHpwrValue(int i) {
        mEnergyBean.setInstantaneousValue(i);
        postInstantaneous();
    }

    private void postInstantaneous() {
        if (hasMaxMinInstantaneousValue()) {
            this.mInstantaneousPower.postValue(mEnergyBean);
            return;
        }
        XILog.e(TAG, "InstantaneousPower max or min value is 0, cluster manager refresh data");
        ClusterManager.getInstance().refreshData();
    }

    @Override // com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onRefreshPowerVEHpwrMax(int i) {
        XILog.i(TAG, "能耗卡片的功率最大值： " + i);
        mEnergyBean.setMaxValue(i);
        postInstantaneous();
    }

    @Override // com.xiaopeng.cluster.listener.IPowerConsumptionListener
    public void onRefreshPowerVEHpwrMin(int i) {
        XILog.i(TAG, "能耗卡片的功率最小值： " + i);
        mEnergyBean.setMinValue(i);
        postInstantaneous();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removePowerConsumptionListener(this);
    }
}
