package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.INormalInfoListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.BatteryBean;
import com.xiaopeng.instrument.bean.BatteryLevelState;
import com.xiaopeng.instrument.data.ChargeRepository;
import com.xiaopeng.instrument.utils.CommonUtil;
/* loaded from: classes.dex */
public class BaseBottomStatusBarViewModel extends ViewModel implements ICommonListener, INormalInfoListener {
    private static final String TAG = "BaseBottomStatusBarViewModel";
    private final MutableLiveData<Integer> mOuterTemperatureData = new MutableLiveData<>(25);
    private final MutableLiveData<Integer> mBatteryLifeStandardData = new MutableLiveData<>(0);
    private final MutableLiveData<BatteryBean> mBatteryLevelData = new MutableLiveData<>();
    private final MutableLiveData<Float> mEnduranceShowMileage = new MutableLiveData<>(Float.valueOf(-1.0f));
    private final MutableLiveData<Boolean> mReadyLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mCarGearLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDrivingModeData = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> mBarBgData = new MutableLiveData<>(1);
    private final MutableLiveData<String> mBatteryPercent = new MutableLiveData<>(ChargeRepository.DEFAULT_ENDURANCE_VALUE);
    private final MutableLiveData<Integer> mRandisDisplayType = new MutableLiveData<>();
    private int mDrivingMode = 1;
    private BatteryBean mBatteryBean = new BatteryBean();
    private float mEnduranceMileage = 0.0f;
    private boolean mIsEnduranceMileageVisible = false;
    private int mCurrentGearValue = 0;

    public BaseBottomStatusBarViewModel() {
        initSignalListener();
    }

    public MutableLiveData<Integer> getBarBgData() {
        return this.mBarBgData;
    }

    private void initSignalListener() {
        ClusterManager.getInstance().setNormalInfoListener(this);
        ClusterManager.getInstance().addCommonListener(this);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setNormalInfoListener(null);
        ClusterManager.getInstance().removeCommonListener(this);
    }

    @Override // com.xiaopeng.cluster.listener.INormalInfoListener
    public void onNormalInfoTemperature(int i) {
        this.mOuterTemperatureData.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.INormalInfoListener
    public void onBatteryLifeStandard(int i) {
        this.mBatteryLifeStandardData.postValue(Integer.valueOf(i));
    }

    private synchronized void setEnduranceMileageVisibility(boolean z) {
        XILog.d(TAG, "setEnduranceMileageVisibility :" + z);
        this.mIsEnduranceMileageVisible = z;
        if (z) {
            this.mEnduranceShowMileage.postValue(Float.valueOf(this.mEnduranceMileage));
        } else {
            this.mEnduranceShowMileage.postValue(Float.valueOf(-1.0f));
        }
    }

    private synchronized void setEnduranceMileage(float f) {
        this.mEnduranceMileage = f;
        XILog.d(TAG, "mEnduranceMileage :" + this.mEnduranceMileage);
        if (this.mIsEnduranceMileageVisible) {
            this.mEnduranceShowMileage.postValue(Float.valueOf(this.mEnduranceMileage));
        }
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onEnduranceMileage(float f) {
        setEnduranceMileage(f);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onEnduranceMileageNumVisible(boolean z) {
        setEnduranceMileageVisibility(z);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onElectricQuantity(int i) {
        this.mBatteryBean.setLevelValue(i);
        this.mBatteryLevelData.setValue(this.mBatteryBean);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onElectricityColorControlProp(int i) {
        this.mBatteryBean.setColorState(BatteryLevelState.parseFromElectricityColorControlProp(i));
        this.mBatteryLevelData.setValue(this.mBatteryBean);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onDrivingMode(int i) {
        if (CommonUtil.isEqual(this.mDrivingMode, i)) {
            XILog.d(TAG, "onDrivingMode is equal...");
            return;
        }
        this.mDrivingMode = i;
        this.mDrivingModeData.setValue(Integer.valueOf(i));
        int i2 = 1;
        this.mBarBgData.setValue(Integer.valueOf((i == 7 || i == 9) ? 2 : 2));
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onReadyIndicatorLight(boolean z) {
        this.mReadyLiveData.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onGear(int i) {
        if (i == this.mCurrentGearValue) {
            XILog.d(TAG, "onGear is equal");
            return;
        }
        this.mCurrentGearValue = i;
        this.mCarGearLiveData.setValue(Integer.valueOf(i));
    }

    public MutableLiveData<Integer> getCarGearLiveData() {
        return this.mCarGearLiveData;
    }

    public final MutableLiveData<Boolean> getReadyLiveData() {
        return this.mReadyLiveData;
    }

    public MutableLiveData<Integer> getOuterTemperatureData() {
        return this.mOuterTemperatureData;
    }

    public MutableLiveData<Integer> getDrivingModeData() {
        return this.mDrivingModeData;
    }

    public MutableLiveData<Integer> getBatteryLifeStandardData() {
        return this.mBatteryLifeStandardData;
    }

    public MutableLiveData<BatteryBean> getBatteryLevelData() {
        return this.mBatteryLevelData;
    }

    public MutableLiveData<Float> getEnduranceShowMileage() {
        return this.mEnduranceShowMileage;
    }

    public MutableLiveData<String> getmBatteryPercent() {
        return this.mBatteryPercent;
    }

    public MutableLiveData<Integer> getmRandisDisplayType() {
        return this.mRandisDisplayType;
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onCommonElectricQuantityStr(String str) {
        this.mBatteryPercent.setValue(str);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onRandisDisplayType(int i) {
        XILog.i(TAG, "onRandisDisplayType type = " + i);
        this.mRandisDisplayType.setValue(Integer.valueOf(i));
    }
}
