package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.instrument.bean.BatteryLevelState;
import com.xiaopeng.instrument.bean.ChargingState;
import com.xiaopeng.instrument.data.ChargeRepository;
/* loaded from: classes.dex */
public class ChargeViewModel extends ViewModel {
    private static final String TAG = "ChargeViewModel";
    private final MutableLiveData<Integer> mBatteryLevelLiveData;
    private final MutableLiveData<BatteryLevelState> mBatteryLevelStateLiveData;
    private final MutableLiveData<Integer> mChargeBatteryLimit;
    private final MutableLiveData<CharSequence> mChargeContentLiveData;
    protected final MutableLiveData<Integer> mChargeGunStatus;
    private final ChargeRepository mChargeRepository;
    private final MutableLiveData<ChargingState> mChargeStateLiveData;
    private final MutableLiveData<String> mCurrentVoltageLiveData;
    private final MutableLiveData<String> mEnduranceLiveData;
    private final MutableLiveData<String> mEnduranceLiveDataPercent;
    private final MutableLiveData<Integer> mEnduranceType;
    private final MutableLiveData<Boolean> mHideOtherInfoLiveData;
    protected final MutableLiveData<Boolean> mSuperChrgFlg;

    public ChargeViewModel() {
        ChargeRepository chargeRepository = ChargeRepository.getInstance();
        this.mChargeRepository = chargeRepository;
        this.mChargeGunStatus = chargeRepository.getChargeGunStatus();
        this.mSuperChrgFlg = chargeRepository.getSuperChargeFlag();
        this.mChargeStateLiveData = chargeRepository.getChargeStateLiveData();
        this.mCurrentVoltageLiveData = chargeRepository.getCurrentVoltageLiveData();
        this.mEnduranceLiveData = chargeRepository.getEnduranceLiveData();
        this.mBatteryLevelLiveData = chargeRepository.getBatteryLevelLiveData();
        this.mBatteryLevelStateLiveData = chargeRepository.getBatteryLevelStateLiveData();
        this.mChargeContentLiveData = chargeRepository.getChargeContentLiveData();
        this.mHideOtherInfoLiveData = chargeRepository.getHideOtherInfoLiveData();
        this.mChargeBatteryLimit = chargeRepository.getChargeBatteryLimit();
        this.mEnduranceType = chargeRepository.getmEnduranceType();
        this.mEnduranceLiveDataPercent = chargeRepository.getmEnduranceLiveDataPercent();
    }

    public MutableLiveData<Integer> getmEnduranceType() {
        return this.mEnduranceType;
    }

    public MutableLiveData<String> getmEnduranceLiveDataPercent() {
        return this.mEnduranceLiveDataPercent;
    }

    public MutableLiveData<ChargingState> getChargeStateLiveData() {
        return this.mChargeStateLiveData;
    }

    public MutableLiveData<Boolean> getHideOtherInfoLiveData() {
        return this.mHideOtherInfoLiveData;
    }

    public MutableLiveData<String> getCurrentVoltageLiveData() {
        return this.mCurrentVoltageLiveData;
    }

    public MutableLiveData<String> getEnduranceLiveData() {
        return this.mEnduranceLiveData;
    }

    public MutableLiveData<Integer> getBatteryLevelLiveData() {
        return this.mBatteryLevelLiveData;
    }

    public MutableLiveData<BatteryLevelState> getBatteryLevelStateLiveData() {
        return this.mBatteryLevelStateLiveData;
    }

    public MutableLiveData<CharSequence> getChargeContentLiveData() {
        return this.mChargeContentLiveData;
    }

    public MutableLiveData<Integer> getChargeBatteryLimit() {
        return this.mChargeBatteryLimit;
    }

    public MutableLiveData<Integer> getChargeGunStatus() {
        return this.mChargeGunStatus;
    }

    public MutableLiveData<Boolean> getSuperChrgFlg() {
        return this.mSuperChrgFlg;
    }

    public void updateTextForThemeChange() {
        this.mChargeRepository.updateTextForThemeChange();
    }

    public void resumeData() {
        getEnduranceLiveData().setValue(getEnduranceLiveData().getValue());
        getCurrentVoltageLiveData().setValue(getCurrentVoltageLiveData().getValue());
        getChargeStateLiveData().setValue(getChargeStateLiveData().getValue());
        getChargeContentLiveData().setValue(getChargeContentLiveData().getValue());
        getBatteryLevelLiveData().setValue(getBatteryLevelLiveData().getValue());
        getBatteryLevelStateLiveData().setValue(getBatteryLevelStateLiveData().getValue());
        getChargeBatteryLimit().setValue(getChargeBatteryLimit().getValue());
        getChargeGunStatus().setValue(getChargeGunStatus().getValue());
        getSuperChrgFlg().setValue(getSuperChrgFlg().getValue());
    }
}
