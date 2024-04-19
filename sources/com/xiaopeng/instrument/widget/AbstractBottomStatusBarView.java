package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.instrument.bean.BatteryBean;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.text.DecimalFormat;
/* loaded from: classes.dex */
public abstract class AbstractBottomStatusBarView extends XRelativeLayout {
    private static final int ANIMATOR_DURATION_DRIVING_MODE_HINT = 100;
    public static final int ENDURANCE_STANDARD_CLTC = 3;
    public static final int ENDURANCE_STANDARD_NEDC = 1;
    public static final int ENDURANCE_STANDARD_WLTP = 2;
    private static final String TAG = "AbstractBottomStatusBarView";
    private static final DecimalFormat mRemainDistanceDecimalFormat = new DecimalFormat("##0.0");
    protected XTextView mBatteryPercent;
    protected BatterySmallView mBatteryV;
    private CarGearView mCarGearView;
    private XTextView mCarReadyView;
    private BaseViewAnimator mDrivingModeHintAnimator;
    protected XImageView mDrivingModeHintIv;
    private int mEnduranceStandard;
    XTextView mEnduranceStandardIv;
    XTextView mRemainDistanceTv;
    XTextView mTemperatureTv;

    public abstract int getLayout();

    public abstract void setDrivingMode(int i);

    public AbstractBottomStatusBarView(Context context) {
        this(context, null);
    }

    public AbstractBottomStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initContentView();
    }

    public AbstractBottomStatusBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initContentView();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void changeTheme() {
        setEnduranceStandard(this.mEnduranceStandard);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void initContentView() {
        LayoutInflater.from(getContext()).inflate(getLayout(), (ViewGroup) this, true);
        this.mTemperatureTv = (XTextView) findViewById(R.id.tv_temperature);
        this.mDrivingModeHintIv = (XImageView) findViewById(R.id.iv_driving_mode_hint);
        this.mEnduranceStandardIv = (XTextView) findViewById(R.id.iv_endurance_standard);
        this.mRemainDistanceTv = (XTextView) findViewById(R.id.tv_remain_distance);
        this.mBatteryV = (BatterySmallView) findViewById(R.id.battery_small);
        this.mCarReadyView = (XTextView) findViewById(R.id.car_ready);
        this.mCarGearView = (CarGearView) findViewById(R.id.car_gear);
        this.mBatteryPercent = (XTextView) findViewById(R.id.battery_small_percent);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        BaseViewAnimator baseViewAnimator = this.mDrivingModeHintAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
            this.mDrivingModeHintAnimator = null;
        }
        super.onDetachedFromWindow();
    }

    public void updateGear(int i) {
        this.mCarGearView.showGear(i);
    }

    public void setTemperature(int i) {
        XTextView xTextView = this.mTemperatureTv;
        if (xTextView != null) {
            xTextView.setText(String.valueOf(i));
        }
    }

    public void updateReady(boolean z) {
        this.mCarReadyView.setVisibility(z ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void startDrivingModeAnim() {
        if (this.mDrivingModeHintIv == null) {
            return;
        }
        if (this.mDrivingModeHintAnimator == null) {
            this.mDrivingModeHintAnimator = AnimatorType.SlideInDown.initAnimator().setDuration(100L).setInterpolator(new DecelerateInterpolator());
        }
        this.mDrivingModeHintAnimator.animate(this.mDrivingModeHintIv);
    }

    public void setEnduranceStandard(int i) {
        this.mEnduranceStandard = i;
        XTextView xTextView = this.mEnduranceStandardIv;
        if (xTextView != null) {
            if (i == 1) {
                xTextView.setText(R.string.endurance_standard_nedc);
            } else if (i == 2) {
                xTextView.setText(R.string.endurance_standard_wltp);
            } else if (i == 3) {
                xTextView.setText(R.string.endurance_standard_cltc);
            } else {
                XILog.e("BottomStatusBar", "setEnduranceStandard unsupported standard:" + i);
            }
            this.mEnduranceStandardIv.setVisibility(0);
        }
    }

    public void setEnduranceMileage(float f) {
        String valueOf;
        if (this.mRemainDistanceTv != null) {
            if (f < 0.0f) {
                valueOf = getResources().getString(R.string.bottom_status_endurance_mileage_empty);
            } else {
                int i = (int) f;
                if (BaseConfig.getInstance().isSupportDecimalRemainDistance()) {
                    valueOf = Float.compare(f, (float) i) == 0 ? String.valueOf(i) : mRemainDistanceDecimalFormat.format(f);
                } else {
                    valueOf = String.valueOf(i);
                }
            }
            this.mRemainDistanceTv.setText(valueOf);
        }
    }

    public void setBatteryLevel(BatteryBean batteryBean) {
        BatterySmallView batterySmallView = this.mBatteryV;
        if (batterySmallView != null) {
            batterySmallView.setBatteryLevel(batteryBean);
        }
    }
}
