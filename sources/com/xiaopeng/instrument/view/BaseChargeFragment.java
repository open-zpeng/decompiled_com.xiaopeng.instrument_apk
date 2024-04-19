package com.xiaopeng.instrument.view;

import android.animation.ObjectAnimator;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.BatteryLevelState;
import com.xiaopeng.instrument.bean.ChargingState;
import com.xiaopeng.instrument.viewmodel.ChargeViewModel;
import com.xiaopeng.instrument.widget.BatteryView;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class BaseChargeFragment extends BaseFragment {
    protected static final int CHARGE_QUICK_GUN_STATE = 2;
    protected static final int CHARGE_SLOW_GUN_STATE = 1;
    protected static final int REMAINDISTANCE = 0;
    protected static final int REMAINDISTANCE_PERCENT = 1;
    private static final String TAG = "BaseChargeFragment";
    private BatteryView mBatteryView;
    ChargeViewModel mChargeViewModel;
    protected XImageView mIvChargingPortCoverLeft;
    protected XImageView mIvChargingPortCoverRight;
    private XTextView mPercentSign;
    private XTextView mREmainDistancePercent;
    private XTextView mTvChargeDetail;
    private XTextView mTvChargeHint;
    private XTextView mTvChargePreparing;
    private XTextView mTvChargeStatus;
    private XTextView mTvRemainDistance;
    private XTextView mTvRemainDistanceNew;
    private XTextView mTvVoltageCurrent;
    private XProgressBar mXProgressBar;
    private StringBuffer sb = new StringBuffer();

    /* JADX INFO: Access modifiers changed from: protected */
    public boolean isSceneLegal() {
        return (!isResumed() || isDetached() || getActivity() == null) ? false : true;
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_echarge, viewGroup, false);
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        initViewModel();
        initObserver();
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        resumeData();
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        pauseData();
        super.onPause();
    }

    private void pauseData() {
        ChargeViewModel chargeViewModel = this.mChargeViewModel;
        if (chargeViewModel == null) {
            XILog.d(TAG, "pauseData... mChargeViewModel is null");
        } else if (chargeViewModel.getChargeStateLiveData().getValue() == ChargingState.CHARGING_STATE_CHARGING) {
            this.mBatteryView.stopCharging();
        }
    }

    private void resumeData() {
        ChargeViewModel chargeViewModel = this.mChargeViewModel;
        if (chargeViewModel == null) {
            XILog.d(TAG, "resumeData...mChargeViewModel is null");
        } else {
            chargeViewModel.resumeData();
        }
    }

    private void initView(View view) {
        this.mXProgressBar = (XProgressBar) view.findViewById(R.id.pb_charge_prepare);
        this.mTvRemainDistance = (XTextView) view.findViewById(R.id.tv_remain_distance);
        this.mTvRemainDistanceNew = (XTextView) view.findViewById(R.id.tv_remain_distance_new);
        this.mTvVoltageCurrent = (XTextView) view.findViewById(R.id.tv_voltage_current);
        this.mTvChargeStatus = (XTextView) view.findViewById(R.id.tv_charge_status);
        this.mTvChargeDetail = (XTextView) view.findViewById(R.id.tv_charge_detail);
        this.mTvChargeHint = (XTextView) view.findViewById(R.id.tv_charge_hint);
        this.mTvChargePreparing = (XTextView) view.findViewById(R.id.tv_charge_preparing);
        this.mBatteryView = (BatteryView) view.findViewById(R.id.battery);
        this.mIvChargingPortCoverRight = (XImageView) view.findViewById(R.id.iv_charging_port_cover_right);
        this.mIvChargingPortCoverLeft = (XImageView) view.findViewById(R.id.iv_charging_port_cover_left);
        this.mREmainDistancePercent = (XTextView) view.findViewById(R.id.tv_remain_distance_percent);
        this.mPercentSign = (XTextView) view.findViewById(R.id.tv_remain_distance_percent_sign);
        if (BaseConfig.getInstance().isSupportPerspChargeView()) {
            ObjectAnimator.ofFloat(this.mBatteryView, "rotationX", 0.0f, 37.0f).setDuration(0L).start();
        }
    }

    protected void initViewModel() {
        this.mChargeViewModel = (ChargeViewModel) new ViewModelProvider(this).get(ChargeViewModel.class);
    }

    private void initObserver() {
        setLiveDataObserver(this.mChargeViewModel.getEnduranceLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$UNqlLUyIuE_gV7J0FgHDUFoJkEI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateEndurance((String) obj);
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getCurrentVoltageLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$6U86WOCaVheCjh9N5dcvbsla2mk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateCurrentVoltage((String) obj);
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getChargeStateLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$WOQl4G4Y0-8VFTTevqxl3WFYaO4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateChargeState((ChargingState) obj);
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getChargeContentLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$baaZpwvYgqtt6XyADJ7B4lPVgPA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateChargeContent((CharSequence) obj);
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getHideOtherInfoLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$DmEhrEmUBcdZonKliXt7F1jrxok
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateHideOtherInfo(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getBatteryLevelLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$u8NYTfOcF8i09cMSDWjA0VjFUL0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateBatteryLevel(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getBatteryLevelStateLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$oIZRa_wvM6W7sHkA6M8JyFDXmgs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateBatteryLevelState((BatteryLevelState) obj);
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getChargeBatteryLimit(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BaseChargeFragment$Z-XmUEu9OmB3e2QcRmXFnYrEAB0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateChargeBatteryLimit(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getChargeGunStatus(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$DbUQ6rzG9oA4Z7f-0ZYMHJPSWDg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateChargeGun(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getSuperChrgFlg(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$UfDN-RzwZ3F_bgkresDHsHxA_Tg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateSuperChrgFlg(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getmEnduranceLiveDataPercent(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$BIYNEhqxi_0CAOeR5BPMgkrNNlA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateEndurancePercent((String) obj);
            }
        });
        setLiveDataObserver(this.mChargeViewModel.getmEnduranceType(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$wilXhbyVf_I6IYkFihxBWrX5moU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BaseChargeFragment.this.updateEnduranceType(((Integer) obj).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateChargeGun(int i) {
        if (!isSceneLegal()) {
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateEnduranceType(int i) {
        XILog.i(TAG, "updateEnduranceType type = " + i);
        boolean z = i == 1;
        this.mREmainDistancePercent.setText(this.mChargeViewModel.getmEnduranceLiveDataPercent().getValue());
        this.mREmainDistancePercent.setVisibility(z ? 0 : 4);
        this.mPercentSign.setVisibility(z ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateEndurancePercent(String str) {
        this.mREmainDistancePercent.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateSuperChrgFlg(boolean z) {
        if (isSceneLegal()) {
            this.mBatteryView.setChargeAnimSpeed(z);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChargeBatteryLimit(int i) {
        if (isSceneLegal()) {
            this.mBatteryView.setLimitLinePercent(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBatteryLevelState(BatteryLevelState batteryLevelState) {
        if (isSceneLegal()) {
            this.mBatteryView.setBatteryLevelState(batteryLevelState);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateBatteryLevel(int i) {
        if (isSceneLegal()) {
            this.mBatteryView.setBatteryLevel(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChargeContent(CharSequence charSequence) {
        if (isSceneLegal()) {
            this.mTvChargeDetail.setText(charSequence);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateHideOtherInfo(boolean z) {
        this.mTvChargeHint.setVisibility(z ? 8 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateChargeState(ChargingState chargingState) {
        if (isSceneLegal()) {
            XILog.i(TAG, "updateChargeState:" + chargingState.mData);
            this.mTvChargeStatus.setText(chargingState.mDescription);
            this.mTvChargeHint.setText(chargingState.mAddOnDescription);
            boolean z = true;
            boolean z2 = chargingState == ChargingState.CHARGING_STATE_CHARGING;
            boolean z3 = chargingState == ChargingState.CHARGING_STATE_DISCHARGING;
            this.mBatteryView.setIsCharging(z2);
            this.mBatteryView.setIsDischarging(z3);
            this.mTvVoltageCurrent.setVisibility(chargingState == ChargingState.CHARGING_STATE_CHARGING || chargingState == ChargingState.CHARGING_STATE_DISCHARGING ? 0 : 4);
            this.mTvChargePreparing.setVisibility(chargingState == ChargingState.CHARGING_STATE_PREPARE_DISCHARGE ? 0 : 4);
            if (chargingState != ChargingState.CHARGING_STATE_PREPARING && chargingState != ChargingState.CHARGING_STATE_PREPARE_DISCHARGE) {
                z = false;
            }
            this.mXProgressBar.setVisibility(z ? 0 : 4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCurrentVoltage(String str) {
        if (isSceneLegal()) {
            this.mTvVoltageCurrent.setText(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEndurance(String str) {
        if (isSceneLegal()) {
            this.mTvRemainDistance.setText(str);
            this.mTvRemainDistanceNew.setText(str);
        }
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        changeTheme(ThemeManager.isThemeChanged(configuration));
    }

    private void changeTheme(boolean z) {
        XProgressBar xProgressBar;
        ChargeViewModel chargeViewModel;
        if (z && (chargeViewModel = this.mChargeViewModel) != null) {
            chargeViewModel.updateTextForThemeChange();
        }
        if (!z || (xProgressBar = this.mXProgressBar) == null) {
            return;
        }
        xProgressBar.getIndeterminateDrawable().setTint(getResources().getColor(R.color.charge_progress_indeterminate_drawable_tint_color, null));
        this.mXProgressBar.getProgressDrawable().setTint(getResources().getColor(R.color.charge_progress_background_tint_color, null));
    }
}
