package com.xiaopeng.instrument.data;

import android.content.Context;
import android.text.Html;
import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.listener.IChargeListener;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.utils.CommonConstant;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.BatteryLevelState;
import com.xiaopeng.instrument.bean.ChargingState;
import java.text.DecimalFormat;
import org.apache.commons.compress.archivers.tar.TarConstants;
/* loaded from: classes.dex */
public class ChargeRepository implements IChargeListener, ICommonListener {
    public static final String DEFAULT_ENDURANCE_VALUE = "0";
    private static final String DEFAULT_VALUE = "0";
    public static final String DEFAULT_VOLTAGE_VALUE = "-- A -- V";
    private static final String TAG = "ChargeRepository";
    private static final DecimalFormat mRemainDistanceDecimalFormat = new DecimalFormat("##0.0");
    private String mAppointmentHour;
    private String mAppointmentMinute;
    private String mBatteryHeatHour;
    private String mBatteryHeatMinute;
    private String mBatteryHeatTipContent;
    private final MutableLiveData<Integer> mBatteryLevelLiveData;
    private final MutableLiveData<BatteryLevelState> mBatteryLevelStateLiveData;
    private final MutableLiveData<Integer> mChargeBatteryLimit;
    private final MutableLiveData<CharSequence> mChargeContentLiveData;
    protected final MutableLiveData<Integer> mChargeGunStatus;
    private String mChargeRemainHour;
    private String mChargeRemainMinute;
    private final MutableLiveData<ChargingState> mChargeStateLiveData;
    private volatile ChargingState mChargingState;
    private String mChargingTipContent;
    private String mCurrent;
    private final MutableLiveData<String> mCurrentVoltageLiveData;
    private final MutableLiveData<String> mEnduranceLiveData;
    private final MutableLiveData<String> mEnduranceLiveDataPercent;
    private final MutableLiveData<Integer> mEnduranceType;
    private final MutableLiveData<Boolean> mHideOtherInfoLiveData;
    protected final MutableLiveData<Boolean> mSuperChrgFlg;
    private String mTotalChargeHour;
    private String mTotalChargeMinute;
    private String mVoltage;
    private boolean mWillFinish;

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onPowerInformationVisible(boolean z) {
    }

    /* synthetic */ ChargeRepository(AnonymousClass1 anonymousClass1) {
        this();
    }

    private ChargeRepository() {
        this.mChargeGunStatus = new MutableLiveData<>(0);
        this.mSuperChrgFlg = new MutableLiveData<>(false);
        this.mChargeStateLiveData = new MutableLiveData<>(ChargingState.CHARGING_STATE_NOT_CONNECTED);
        this.mCurrentVoltageLiveData = new MutableLiveData<>(DEFAULT_VOLTAGE_VALUE);
        this.mEnduranceLiveData = new MutableLiveData<>("0");
        this.mBatteryLevelLiveData = new MutableLiveData<>(-1);
        this.mBatteryLevelStateLiveData = new MutableLiveData<>(BatteryLevelState.BATTERY_LEVEL_EMPTY);
        this.mChargeContentLiveData = new MutableLiveData<>("");
        this.mHideOtherInfoLiveData = new MutableLiveData<>(false);
        this.mChargeBatteryLimit = new MutableLiveData<>(0);
        this.mEnduranceLiveDataPercent = new MutableLiveData<>("0");
        this.mEnduranceType = new MutableLiveData<>(0);
        this.mChargingState = ChargingState.CHARGING_STATE_NOT_CONNECTED;
        this.mChargingTipContent = "";
        this.mBatteryHeatTipContent = "";
        this.mWillFinish = false;
    }

    public static ChargeRepository getInstance() {
        return ChargeRepositoryHolder.instance;
    }

    /* loaded from: classes.dex */
    private static class ChargeRepositoryHolder {
        private static final ChargeRepository instance = new ChargeRepository(null);

        private ChargeRepositoryHolder() {
        }
    }

    public void init() {
        ClusterManager.getInstance().setChargeListener(this);
        ClusterManager.getInstance().addCommonListener(this);
    }

    public void unInit() {
        ClusterManager.getInstance().setChargeListener(null);
        ClusterManager.getInstance().removeCommonListener(this);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onEnduranceMileage(float f) {
        String valueOf;
        XILog.d(TAG, "onEnduranceMileage:" + f);
        int i = (int) f;
        if (BaseConfig.getInstance().isSupportDecimalRemainDistance()) {
            valueOf = Float.compare(f, (float) i) == 0 ? String.valueOf(i) : mRemainDistanceDecimalFormat.format(f);
        } else {
            valueOf = String.valueOf(i);
        }
        getEnduranceLiveData().setValue(valueOf);
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onElectricQuantity(int i) {
        XILog.d(TAG, "onElectricQuantity:" + i);
        getBatteryLevelLiveData().setValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onElectricityColorControlProp(int i) {
        XILog.d(TAG, "onElectricityColorControlProp:" + i);
        getBatteryLevelStateLiveData().setValue(BatteryLevelState.parseFromElectricityColorControlProp(i));
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onChargeStatus(int i) {
        this.mChargeGunStatus.setValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onSuperChrgFlg(boolean z) {
        this.mSuperChrgFlg.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onAppointmentHour(String str) {
        this.mAppointmentHour = str;
        if (this.mChargingState == ChargingState.CHARGING_STATE_SCHEDULE_CHARGE) {
            setAppointmentHour(str);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onAppointmentMinute(String str) {
        this.mAppointmentMinute = str;
        if (this.mChargingState == ChargingState.CHARGING_STATE_SCHEDULE_CHARGE) {
            setAppointmentMinute(str);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onCompleteHour(String str) {
        setTotalChargeHour(str);
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onCompleteMinute(String str) {
        setTotalChargeMinute(str);
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onHeatBatteryHour(String str) {
        this.mBatteryHeatHour = str;
        this.mBatteryHeatTipContent = "";
        if (this.mChargingState == ChargingState.CHARGING_STATE_HEAT_BATTERY || this.mChargingState == ChargingState.CHARGING_STATE_COOLING_BATTERY) {
            setBatteryHeatHour(str);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onHeatBatteryMinute(String str) {
        this.mBatteryHeatMinute = str;
        this.mBatteryHeatTipContent = "";
        if (this.mChargingState == ChargingState.CHARGING_STATE_HEAT_BATTERY || this.mChargingState == ChargingState.CHARGING_STATE_COOLING_BATTERY) {
            setBatteryHeatMinute(str);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onPowerInformationCurrent(String str) {
        setCurrent(str);
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onPowerInformationVoltage(String str) {
        setVoltage(str);
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener, com.xiaopeng.cluster.listener.ICommonListener
    public void onChargingState(int i) {
        ChargingState parse = ChargingState.parse(i);
        this.mChargingState = parse;
        XILog.d(TAG, "charging state:" + i);
        getChargeStateLiveData().setValue(parse);
        getChargeContentLiveData().setValue("");
        showMiddleContent(parse);
        setHideOtherInfo();
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onTimeRemainingHour(String str) {
        this.mChargeRemainHour = str;
        this.mChargingTipContent = "";
        if (this.mChargingState == ChargingState.CHARGING_STATE_CHARGING) {
            setChargeRemainHour(str);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onTimeRemainingMinute(String str) {
        this.mChargeRemainMinute = str;
        this.mChargingTipContent = "";
        if (this.mChargingState == ChargingState.CHARGING_STATE_CHARGING) {
            setChargeRemainMinute(str);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onEsocmaxchg(int i) {
        getChargeBatteryLimit().setValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onTimeRemainingTipsState(int i) {
        this.mChargingTipContent = getTimeTipsByState(i);
        if (this.mChargingState == ChargingState.CHARGING_STATE_CHARGING) {
            this.mChargeContentLiveData.setValue(this.mChargingTipContent);
            this.mWillFinish = i == 2;
            setHideOtherInfo();
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener
    public void onHeatBatteryTipsState(int i) {
        this.mBatteryHeatTipContent = getTimeTipsByState(i);
        if (this.mChargingState == ChargingState.CHARGING_STATE_HEAT_BATTERY || this.mChargingState == ChargingState.CHARGING_STATE_COOLING_BATTERY) {
            this.mChargeContentLiveData.setValue(this.mBatteryHeatTipContent);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener, com.xiaopeng.cluster.listener.ICommonListener
    public void onCommonElectricQuantityStr(String str) {
        this.mEnduranceLiveDataPercent.setValue(str);
    }

    @Override // com.xiaopeng.cluster.listener.IChargeListener, com.xiaopeng.cluster.listener.ICommonListener
    public void onRandisDisplayType(int i) {
        this.mEnduranceType.setValue(Integer.valueOf(i));
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
        getSuperChargeFlag().setValue(getSuperChargeFlag().getValue());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.instrument.data.ChargeRepository$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$instrument$bean$ChargingState;

        static {
            int[] iArr = new int[ChargingState.values().length];
            $SwitchMap$com$xiaopeng$instrument$bean$ChargingState = iArr;
            try {
                iArr[ChargingState.CHARGING_STATE_SCHEDULE_CHARGE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$ChargingState[ChargingState.CHARGING_STATE_HEAT_BATTERY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$ChargingState[ChargingState.CHARGING_STATE_COOLING_BATTERY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$ChargingState[ChargingState.CHARGING_STATE_CHARGING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private void showMiddleContent(ChargingState chargingState) {
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$instrument$bean$ChargingState[chargingState.ordinal()];
        if (i == 1) {
            setAppointmentHour(this.mAppointmentHour);
            setAppointmentMinute(this.mAppointmentMinute);
        } else if (i == 2 || i == 3) {
            if (!this.mBatteryHeatTipContent.isEmpty()) {
                this.mChargeContentLiveData.setValue(this.mBatteryHeatTipContent);
                return;
            }
            setBatteryHeatHour(this.mBatteryHeatHour);
            setBatteryHeatMinute(this.mBatteryHeatMinute);
        } else if (i != 4) {
        } else {
            if (!this.mChargingTipContent.isEmpty()) {
                this.mChargeContentLiveData.setValue(this.mChargingTipContent);
                return;
            }
            setChargeRemainHour(this.mTotalChargeHour);
            setChargeRemainMinute(this.mTotalChargeMinute);
        }
    }

    private String getTimeTipsByState(int i) {
        if (i != 1) {
            if (i != 2) {
                return i != 3 ? "" : App.getInstance().getString(R.string.charge_status_charging_content_remain);
            }
            return App.getInstance().getString(R.string.charge_status_charging_content_complete);
        }
        return App.getInstance().getString(R.string.charge_status_charging_content_long);
    }

    public void updateTextForThemeChange() {
        showMiddleContent(this.mChargingState);
    }

    public void setCurrent(String str) {
        this.mCurrent = str;
        updateCurrentVoltageData();
    }

    public void setVoltage(String str) {
        this.mVoltage = str;
        updateCurrentVoltageData();
    }

    public void updateCurrentVoltageData() {
        float f;
        String string;
        try {
            f = Float.parseFloat(this.mCurrent);
        } catch (NullPointerException | NumberFormatException e) {
            XILog.d(TAG, "current format error, current = " + this.mCurrent);
            e.printStackTrace();
            f = -1.0f;
        }
        int i = (f > 1.0f ? 1 : (f == 1.0f ? 0 : -1));
        String str = CommonConstant.INVALID_TEXT_VALUE;
        if (i >= 0) {
            Context applicationContext = App.getInstance().getApplicationContext();
            Object[] objArr = new Object[2];
            objArr[0] = TextUtils.isEmpty(this.mCurrent) ? CommonConstant.INVALID_TEXT_VALUE : this.mCurrent;
            if (!TextUtils.isEmpty(this.mVoltage)) {
                str = this.mVoltage;
            }
            objArr[1] = str;
            string = applicationContext.getString(R.string.charge_current_voltage, objArr);
        } else {
            Context applicationContext2 = App.getInstance().getApplicationContext();
            Object[] objArr2 = new Object[1];
            if (!TextUtils.isEmpty(this.mVoltage)) {
                str = this.mVoltage;
            }
            objArr2[0] = str;
            string = applicationContext2.getString(R.string.charge_current_voltage_less_than_one, objArr2);
        }
        this.mCurrentVoltageLiveData.setValue(string);
    }

    public void setAppointmentHour(String str) {
        updateAppointStartTime();
    }

    public void setAppointmentMinute(String str) {
        updateAppointStartTime();
    }

    private void updateAppointStartTime() {
        XILog.d(TAG, "mAppointmentHour = " + this.mAppointmentHour + ", mAppointmentMinute = " + this.mAppointmentMinute);
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.mAppointmentHour)) {
            sb.append(this.mAppointmentHour).append(':');
        }
        if (!TextUtils.isEmpty(this.mAppointmentMinute)) {
            sb.append(this.mAppointmentMinute);
        } else {
            sb.append(TarConstants.VERSION_POSIX);
        }
        this.mChargeContentLiveData.setValue(Html.fromHtml(App.getInstance().getString(R.string.charge_appoint_start_time, new Object[]{sb.toString()}), 63));
    }

    public void setTotalChargeHour(String str) {
        this.mTotalChargeHour = str;
        setHourMinuteContent(str, this.mTotalChargeMinute);
    }

    public void setTotalChargeMinute(String str) {
        this.mTotalChargeMinute = str;
        setHourMinuteContent(this.mTotalChargeHour, str);
    }

    public void setBatteryHeatHour(String str) {
        setHourMinuteContent(this.mBatteryHeatHour, this.mBatteryHeatMinute);
    }

    public void setBatteryHeatMinute(String str) {
        setHourMinuteContent(this.mBatteryHeatHour, this.mBatteryHeatMinute);
    }

    public void setChargeRemainHour(String str) {
        setHourMinuteContent(this.mChargeRemainHour, this.mChargeRemainMinute);
    }

    public void setChargeRemainMinute(String str) {
        setHourMinuteContent(this.mChargeRemainHour, this.mChargeRemainMinute);
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

    public MutableLiveData<Integer> getmEnduranceType() {
        return this.mEnduranceType;
    }

    public MutableLiveData<String> getmEnduranceLiveDataPercent() {
        return this.mEnduranceLiveDataPercent;
    }

    public MutableLiveData<Integer> getChargeGunStatus() {
        return this.mChargeGunStatus;
    }

    public MutableLiveData<Boolean> getSuperChargeFlag() {
        return this.mSuperChrgFlg;
    }

    private boolean hasHour(String str) {
        return (TextUtils.isEmpty(str) || str.equals("0")) ? false : true;
    }

    private boolean hasMin(String str) {
        return !TextUtils.isEmpty(str);
    }

    private void setHourMinuteContent(String str, String str2) {
        String str3;
        XILog.d(TAG, "setHourMinuteContent hour = " + str + " minute = " + str2);
        if (hasHour(str) && hasMin(str2)) {
            if (str2.equals("0")) {
                str3 = App.getInstance().getString(R.string.charge_expect_remain_time_hour, new Object[]{str});
            } else {
                str3 = App.getInstance().getString(R.string.charge_expect_remain_time_hour_minute, new Object[]{str, str2});
            }
        } else if (hasHour(str)) {
            str3 = App.getInstance().getString(R.string.charge_expect_remain_time_hour, new Object[]{str});
        } else if (hasMin(str2)) {
            App app = App.getInstance();
            Object[] objArr = new Object[1];
            if (str2.equals("0")) {
                str2 = CommonConstant.INVALID_TEXT_VALUE;
            }
            objArr[0] = str2;
            str3 = app.getString(R.string.charge_expect_remain_time_minute, objArr);
        } else {
            str3 = "";
        }
        this.mChargeContentLiveData.setValue(Html.fromHtml(str3, 63));
    }

    private void setHideOtherInfo() {
        this.mHideOtherInfoLiveData.setValue(Boolean.valueOf(this.mChargingState == ChargingState.CHARGING_STATE_CHARGING && this.mWillFinish));
    }
}
