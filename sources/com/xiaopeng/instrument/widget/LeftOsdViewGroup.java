package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorHelper;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.instrument.bean.AccSettingBean;
import com.xiaopeng.instrument.bean.AirVolumeBean;
import com.xiaopeng.instrument.bean.CarBodyColorBean;
import com.xiaopeng.instrument.bean.FrontDistBean;
import com.xiaopeng.instrument.bean.FrontRadarBean;
import com.xiaopeng.xui.widget.XRelativeLayout;
/* loaded from: classes.dex */
public class LeftOsdViewGroup extends XRelativeLayout {
    private static final String TAG = "LeftOsdViewGroup";
    private AccSettingView mAccSettingView;
    private AirTemperatureView mAirTemperatureView;
    private AirVolumeView mAirVolumeView;
    private AnimatorHelper mAnimatorHelper;
    private Context mContext;
    private OsdWiperView mOsdWiperView;
    private BaseViewAnimator mRadarWarningHideAnimator;
    private RadarWarningOSDCardView mRadarWarningOSDCardView;
    private BaseViewAnimator mRadarWarningShowAnimator;

    public LeftOsdViewGroup(Context context) {
        this(context, null);
    }

    public LeftOsdViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public LeftOsdViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        BaseViewAnimator baseViewAnimator = this.mRadarWarningShowAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
        }
        BaseViewAnimator baseViewAnimator2 = this.mRadarWarningHideAnimator;
        if (baseViewAnimator2 != null) {
            baseViewAnimator2.destroy();
        }
        super.onDetachedFromWindow();
    }

    public void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.layout_osd_root_left, this);
        initAnimator();
        this.mRadarWarningOSDCardView = (RadarWarningOSDCardView) findViewById(R.id.osd_info_radar_warning);
        this.mAirTemperatureView = (AirTemperatureView) findViewById(R.id.osd_air_temperature);
        this.mAirVolumeView = (AirVolumeView) findViewById(R.id.osd_air_volume);
        this.mAccSettingView = (AccSettingView) findViewById(R.id.acc_setting);
        this.mOsdWiperView = (OsdWiperView) findViewById(R.id.osd_wiper);
    }

    private void initAnimator() {
        this.mAnimatorHelper = new AnimatorHelper();
        this.mRadarWarningShowAnimator = AnimatorType.SlideInNormal.initAnimator();
        this.mRadarWarningHideAnimator = AnimatorType.SlideOutScale.initAnimator();
    }

    public void showAirTemperature(boolean z) {
        this.mAirTemperatureView.setVisibility(z ? 0 : 8);
    }

    public void setAirTemperature(String[] strArr) {
        this.mAirTemperatureView.setTemperatureValue(strArr);
    }

    public void showWiper(boolean z) {
        this.mOsdWiperView.setVisibility(z ? 0 : 8);
    }

    public void setWiperSpeed(int i) {
        this.mOsdWiperView.setWiperSpeed(i);
    }

    public void showAirVolume(AirVolumeBean airVolumeBean) {
        this.mAirVolumeView.showAirVolume(airVolumeBean);
    }

    public void setAirVolume(int i) {
        this.mAirVolumeView.setAirVolume(i);
    }

    private void showAccGuide(boolean z) {
        if (z) {
            this.mAccSettingView.showMode(1);
        } else {
            this.mAccSettingView.hide();
        }
        XILog.d(TAG, "showAccGuide:" + z);
    }

    private void showAccSpeed(boolean z) {
        if (z) {
            this.mAccSettingView.showMode(2);
        } else {
            this.mAccSettingView.hide();
        }
        XILog.d(TAG, "showAccSpeed:" + z);
    }

    private void showAccDistance(boolean z) {
        if (z) {
            this.mAccSettingView.showMode(3);
        } else {
            this.mAccSettingView.hide();
        }
    }

    private void showAccDistanceAuto(boolean z) {
        if (z) {
            this.mAccSettingView.showMode(4);
        } else {
            this.mAccSettingView.hide();
        }
    }

    private void setAccSpeed(int i) {
        this.mAccSettingView.setSpeed(i);
    }

    private void setAccDistance(int i) {
        this.mAccSettingView.setDistance(i);
    }

    public void updateAccSetting(AccSettingBean accSettingBean) {
        boolean isVisibility = accSettingBean.isVisibility();
        int mode = accSettingBean.getMode();
        if (mode == 1) {
            showAccGuide(isVisibility);
        } else if (mode == 2) {
            showAccSpeed(isVisibility);
            if (isVisibility) {
                setAccSpeed(accSettingBean.getSpeed());
            }
        } else if (mode != 3) {
            if (mode == 4) {
                showAccDistanceAuto(isVisibility);
            } else {
                XILog.d(TAG, "invalid bean: " + accSettingBean.toString());
            }
        } else {
            showAccDistance(isVisibility);
            if (isVisibility) {
                setAccDistance(accSettingBean.getDistance());
            }
        }
    }

    private void updateRadarLevel(int i, int i2) {
        this.mRadarWarningOSDCardView.updateRadarLevel(i, i2);
    }

    public void updateRadarTextValue(FrontDistBean frontDistBean) {
        this.mRadarWarningOSDCardView.updateRadarDisTextValue(frontDistBean);
    }

    public void showRadarWarningView(boolean z) {
        this.mAnimatorHelper.showAnimator(z, this.mRadarWarningShowAnimator, this.mRadarWarningHideAnimator, this.mRadarWarningOSDCardView);
        this.mRadarWarningOSDCardView.setVisibility(z ? 0 : 8);
    }

    private void updateRadarDistance(int i, int i2) {
        this.mRadarWarningOSDCardView.updateRadarDistance(i, i2);
    }

    public void updateRadarFrontBody(CarBodyColorBean carBodyColorBean) {
        this.mRadarWarningOSDCardView.updateCarBodyBg(carBodyColorBean);
    }

    public void updateRadarBean(FrontRadarBean frontRadarBean) {
        if (frontRadarBean.isLevel()) {
            updateRadarLevel(frontRadarBean.getRadarNumber(), frontRadarBean.getLevel());
        } else {
            updateRadarDistance(frontRadarBean.getRadarNumber(), frontRadarBean.getDistances());
        }
    }
}
