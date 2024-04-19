package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.IOdoMeterListener;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public abstract class AbstractOdoMeterViewModel extends ViewModel {
    private static final String TAG = "AbstractOdoMeterViewModel";
    private IOdoMeterListener mIOdoMeterListener;
    private final MutableLiveData<String> mDrivingOdometerFromStartup = new MutableLiveData<>();
    private final MutableLiveData<String> mDrivingOdometerUnitFromStartup = new MutableLiveData<>();
    private final MutableLiveData<String> mDrivingTimeFromStartup = new MutableLiveData<>();
    private final MutableLiveData<String> mDrivingTimeUnitFromStartup = new MutableLiveData<>();
    protected final MutableLiveData<String> mAverageEnergyCost = new MutableLiveData<>();
    private final MutableLiveData<String> mDrivingOdometerFromLastCharge = new MutableLiveData<>();
    private final MutableLiveData<String> mDrivingOdometerUnitFromLastCharge = new MutableLiveData<>();
    private final MutableLiveData<String> mTotalDrivingOdometer = new MutableLiveData<>();
    private final MutableLiveData<String> mTotalDrivingOdometerUnit = new MutableLiveData<>();
    protected ICommonListener mCommonListener = new ICommonListener() { // from class: com.xiaopeng.instrument.viewmodel.AbstractOdoMeterViewModel.1
        @Override // com.xiaopeng.cluster.listener.ICommonListener
        public void onPowerAverageEnergyConsumption(float f) {
            AbstractOdoMeterViewModel.this.action(f);
        }
    };

    abstract void action(float f);

    public AbstractOdoMeterViewModel() {
        initSignalListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        XILog.d(TAG, "onCleared");
        ClusterManager.getInstance().removeOdoMeterListener(this.mIOdoMeterListener);
        ClusterManager.getInstance().removeCommonListener(this.mCommonListener);
    }

    private void initSignalListener() {
        this.mIOdoMeterListener = new IOdoMeterListener() { // from class: com.xiaopeng.instrument.viewmodel.AbstractOdoMeterViewModel.2
            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onAverageUnits(String str) {
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onAverageValue(String str) {
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onSubtotalAUnits(String str) {
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onSubtotalAValue(String str) {
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onSubtotalBUnits(String str) {
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onSubtotalBValue(String str) {
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onThisTimeValue(String str) {
                XILog.d(AbstractOdoMeterViewModel.TAG, "onThisTimeValue ");
                AbstractOdoMeterViewModel.this.mDrivingOdometerFromStartup.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            @Deprecated
            public void onThisTimeUnits(String str) {
                AbstractOdoMeterViewModel.this.mDrivingOdometerUnitFromStartup.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onAfterChargingValue(String str) {
                AbstractOdoMeterViewModel.this.mDrivingOdometerFromLastCharge.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            @Deprecated
            public void onAfterChargingUnits(String str) {
                AbstractOdoMeterViewModel.this.mDrivingOdometerUnitFromLastCharge.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onTotalValue(String str) {
                AbstractOdoMeterViewModel.this.mTotalDrivingOdometer.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            @Deprecated
            public void onTotalUnits(String str) {
                AbstractOdoMeterViewModel.this.mTotalDrivingOdometerUnit.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onElapsedTimeValue(String str) {
                AbstractOdoMeterViewModel.this.mDrivingTimeFromStartup.postValue(str);
            }

            @Override // com.xiaopeng.cluster.listener.IOdoMeterListener
            public void onElapsedTimeUnits(String str) {
                AbstractOdoMeterViewModel.this.mDrivingTimeUnitFromStartup.postValue(str);
            }
        };
        ClusterManager.getInstance().addCommonListener(this.mCommonListener);
        ClusterManager.getInstance().addOdoMeterListener(this.mIOdoMeterListener);
    }

    public MutableLiveData<String> getDrivingOdometerFromStartup() {
        XILog.d(TAG, "getDrivingOdometerFromStartup :" + this.mDrivingOdometerFromStartup);
        return this.mDrivingOdometerFromStartup;
    }

    public MutableLiveData<String> getDrivingOdometerUnitFromStartup() {
        return this.mDrivingOdometerUnitFromStartup;
    }

    public MutableLiveData<String> getDrivingTimeFromStartup() {
        return this.mDrivingTimeFromStartup;
    }

    public MutableLiveData<String> getDrivingTimeUnitFromStartup() {
        return this.mDrivingTimeUnitFromStartup;
    }

    public MutableLiveData<String> getAverageEnergyCost() {
        return this.mAverageEnergyCost;
    }

    public MutableLiveData<String> getDrivingOdometerFromLastCharge() {
        return this.mDrivingOdometerFromLastCharge;
    }

    public MutableLiveData<String> getDrivingOdometerUnitFromLastCharge() {
        return this.mDrivingOdometerUnitFromLastCharge;
    }

    public MutableLiveData<String> getTotalDrivingOdometer() {
        return this.mTotalDrivingOdometer;
    }

    public MutableLiveData<String> getTotalDrivingOdometerUnit() {
        return this.mTotalDrivingOdometerUnit;
    }
}
