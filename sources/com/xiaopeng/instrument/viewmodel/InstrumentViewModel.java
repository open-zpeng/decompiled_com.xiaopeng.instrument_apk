package com.xiaopeng.instrument.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.IOsdWiperListener;
import com.xiaopeng.cluster.listener.InstrumentListener;
import com.xiaopeng.cluster.utils.CommonConstant;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.AirVolumeBean;
import com.xiaopeng.instrument.bean.TransGearAnimatorBean;
import com.xiaopeng.instrument.utils.CommonUtil;
import com.xiaopeng.instrument.utils.StringUtil;
/* loaded from: classes.dex */
public class InstrumentViewModel extends ViewModel implements InstrumentListener, ICommonListener, IOsdWiperListener {
    private static final int MAX_VOLUME = 10;
    private static final int MIN_SPEED_VALUE = 0;
    private static final int MIN_VOLUME = 0;
    public static final String TAG = "InstrumentViewModel";
    private static final int WIPER_MAX_SPEED = 4;
    private static final int WIPER_MIN_SPEED = 1;
    private int mAirVolumeShowState;
    private final MutableLiveData<String> mCarSpeedLiveData = new MutableLiveData<>();
    private final MutableLiveData<String[]> mAirTemperatureLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAirTemperatureViewLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mAirVolumeLiveData = new MutableLiveData<>();
    private final MutableLiveData<AirVolumeBean> mAirVolumeViewLiveData = new MutableLiveData<>();
    private final MutableLiveData<TransGearAnimatorBean> mCarTransitionGearLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mDrivingModeData = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> mWiperTypeLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mWiperShowLiveData = new MutableLiveData<>();
    private int mDrivingMode = 1;
    private int mCurrentGearValue = 0;
    private AirVolumeBean mAirVolumeBean = new AirVolumeBean();
    private TransGearAnimatorBean mTransGearAnimatorBean = new TransGearAnimatorBean();

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onAbnormalVehicleCondition(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onDayNight(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onIsCharging(boolean z) {
    }

    public InstrumentViewModel() {
        ClusterManager.getInstance().setOsdWiperListener(this);
        ClusterManager.getInstance().addCommonListener(this);
        ClusterManager.getInstance().setInstrumentListener(this);
    }

    public MutableLiveData<TransGearAnimatorBean> getCarTransitionGearLiveData() {
        return this.mCarTransitionGearLiveData;
    }

    public MutableLiveData<Integer> getDrivingModeData() {
        return this.mDrivingModeData;
    }

    public MutableLiveData<Integer> getAirVolumeLiveData() {
        return this.mAirVolumeLiveData;
    }

    public MutableLiveData<AirVolumeBean> getAirVolumeViewLiveData() {
        return this.mAirVolumeViewLiveData;
    }

    public MutableLiveData<Boolean> getAirTemperatureViewLiveData() {
        return this.mAirTemperatureViewLiveData;
    }

    public MutableLiveData<String[]> getAirTemperatureLiveData() {
        return this.mAirTemperatureLiveData;
    }

    public MutableLiveData<String> getCarSpeedLiveData() {
        return this.mCarSpeedLiveData;
    }

    public MutableLiveData<Integer> getWiperTypeLiveData() {
        return this.mWiperTypeLiveData;
    }

    public MutableLiveData<Boolean> getWiperShowLiveData() {
        return this.mWiperShowLiveData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        ClusterManager.getInstance().removeCommonListener(this);
        ClusterManager.getInstance().setInstrumentListener(null);
        ClusterManager.getInstance().setOsdWiperListener(null);
        super.onCleared();
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onDrivingMode(int i) {
        if (CommonUtil.isEqual(this.mDrivingMode, i)) {
            XILog.d(TAG, "onDrivingMode is equal...");
            return;
        }
        this.mDrivingMode = i;
        this.mDrivingModeData.setValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onGear(int i) {
        if (i == this.mCurrentGearValue) {
            XILog.i(TAG, "onGear is equal");
            return;
        }
        this.mCurrentGearValue = i;
        doShowTransitionGear(i);
    }

    private void doShowTransitionGear(int i) {
        String string;
        if (i == 1) {
            string = App.getInstance().getString(R.string.gear_d_value);
        } else if (i == 2) {
            string = App.getInstance().getString(R.string.gear_n_value);
        } else if (i != 3) {
            string = i != 4 ? "" : App.getInstance().getString(R.string.gear_p_value);
        } else {
            string = App.getInstance().getString(R.string.gear_r_value);
        }
        if (TextUtils.isEmpty(string)) {
            XILog.i(TAG, "doShowTransitionGear :gearVale is null");
            return;
        }
        boolean z = i != 4;
        this.mTransGearAnimatorBean.setGearValue(string);
        this.mTransGearAnimatorBean.setShowSpeed(z);
        this.mCarTransitionGearLiveData.setValue(this.mTransGearAnimatorBean);
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onSpeed(int i) {
        this.mCarSpeedLiveData.postValue(i < 0 ? CommonConstant.INVALID_TEXT_VALUE : String.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onTemperature(float f) {
        this.mAirTemperatureLiveData.postValue(StringUtil.splitStringByPeriod(f));
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onAirVolume(int i) {
        if (i < 0) {
            i = 0;
        } else if (i > 10) {
            i = 10;
        }
        this.mAirVolumeLiveData.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onTemperatureVisible(boolean z) {
        this.mAirTemperatureViewLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.InstrumentListener
    public void onAirVolumeState(int i) {
        if (CommonUtil.isEqual(this.mAirVolumeShowState, i)) {
            XILog.d(TAG, "onAirVolumeState is equal ");
            return;
        }
        this.mAirVolumeShowState = i;
        this.mAirVolumeBean.setShow(i != 0);
        this.mAirVolumeBean.setName(getAirVolumeName(i));
        this.mAirVolumeViewLiveData.postValue(this.mAirVolumeBean);
    }

    private String getAirVolumeName(int i) {
        App.getInstance().getString(R.string.air_volume_name);
        if (i != 2) {
            if (i == 3) {
                return App.getInstance().getString(R.string.air_volume_close_name);
            }
            return App.getInstance().getString(R.string.air_volume_name);
        }
        return App.getInstance().getString(R.string.air_volume_toast_name);
    }

    @Override // com.xiaopeng.cluster.listener.IOsdWiperListener
    public void onRainDetecSencfg(int i) {
        if (i < 1) {
            i = 1;
        } else if (i > 4) {
            i = 4;
        }
        this.mWiperTypeLiveData.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.IOsdWiperListener
    public void onRainDetecVisible(boolean z) {
        this.mWiperShowLiveData.postValue(Boolean.valueOf(z));
    }
}
