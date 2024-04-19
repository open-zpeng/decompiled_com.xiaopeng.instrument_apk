package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorHelper;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.instrument.bean.OSDConfirmBean;
import com.xiaopeng.xui.widget.XRelativeLayout;
/* loaded from: classes.dex */
public class RightOsdViewGroup extends XRelativeLayout {
    private static final String TAG = "RightOsdViewGroup";
    private OsdConfirmView OSDConfirmView;
    private AnimatorHelper mAnimatorHelper;
    private BaseViewAnimator mOsdCallHideAnimator;
    private BaseViewAnimator mOsdCallShowAnimator;
    private OsdCallView mOsdCallView;
    private BaseViewAnimator mOsdConfirmHideAnimator;
    private BaseViewAnimator mOsdConfirmShowAnimator;
    private OsdMediaVolumeView mOsdMediaVolumeView;
    private OsdPGearProtectView mOsdPGearProtectView;

    public RightOsdViewGroup(Context context) {
        this(context, null);
    }

    public RightOsdViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public RightOsdViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        BaseViewAnimator baseViewAnimator = this.mOsdCallShowAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
        }
        BaseViewAnimator baseViewAnimator2 = this.mOsdConfirmShowAnimator;
        if (baseViewAnimator2 != null) {
            baseViewAnimator2.destroy();
        }
        BaseViewAnimator baseViewAnimator3 = this.mOsdConfirmHideAnimator;
        if (baseViewAnimator3 != null) {
            baseViewAnimator3.destroy();
        }
        BaseViewAnimator baseViewAnimator4 = this.mOsdCallHideAnimator;
        if (baseViewAnimator4 != null) {
            baseViewAnimator4.destroy();
        }
        super.onDetachedFromWindow();
    }

    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_osd_root_right, this);
        initAnimator();
        this.OSDConfirmView = (OsdConfirmView) findViewById(R.id.info_text_confirm);
        this.mOsdMediaVolumeView = (OsdMediaVolumeView) findViewById(R.id.osd_media_volume);
        this.mOsdCallView = (OsdCallView) findViewById(R.id.osd_call_view);
        this.mOsdPGearProtectView = (OsdPGearProtectView) findViewById(R.id.osd_p_gear_protect_view);
    }

    private void initAnimator() {
        this.mAnimatorHelper = new AnimatorHelper();
        this.mOsdConfirmShowAnimator = AnimatorType.SlideInNormal.initAnimator();
        this.mOsdConfirmHideAnimator = AnimatorType.SlideOutScale.initAnimator();
        this.mOsdCallShowAnimator = AnimatorType.SlideInNormal.initAnimator();
        this.mOsdCallHideAnimator = AnimatorType.SlideOutScale.initAnimator();
    }

    private void setNGearByMistakeVisibility(boolean z) {
        if (z) {
            this.OSDConfirmView.showMode(1);
        } else {
            this.OSDConfirmView.hide();
        }
        XILog.d(TAG, "setNGearByMistakeVisibility:" + z);
        this.mAnimatorHelper.showAnimator(z, this.mOsdConfirmShowAnimator, this.mOsdConfirmHideAnimator, this.OSDConfirmView);
    }

    private void setNGearByMistakeCounterTime(int i) {
        this.OSDConfirmView.setCountTime(i);
    }

    private void showForcePowerOffView(boolean z) {
        this.mAnimatorHelper.showAnimator(z, this.mOsdConfirmShowAnimator, this.mOsdConfirmHideAnimator, this.OSDConfirmView);
        if (z) {
            this.OSDConfirmView.showMode(0);
        } else {
            this.OSDConfirmView.hide();
        }
    }

    private void switchForcePowerOffConfirm(boolean z) {
        this.OSDConfirmView.setSwitchChoiceItem(z);
    }

    public void showOsdCallView(boolean z) {
        this.mAnimatorHelper.showAnimator(z, this.mOsdCallShowAnimator, this.mOsdCallHideAnimator, this.mOsdCallView);
        this.mOsdCallView.setVisibility(z ? 0 : 8);
    }

    public void updateCallName(String str) {
        this.mOsdCallView.updateName(str);
    }

    public void switchCallChoice(boolean z) {
        this.mOsdCallView.setSwitchChoiceItem(z);
    }

    public void updateCallContent(String str) {
        this.mOsdCallView.updateCallContent(str);
    }

    public void updateCallView(int i) {
        this.mOsdCallView.updateCallView(i);
    }

    public void showOSDConfirmView(OSDConfirmBean oSDConfirmBean) {
        int type = oSDConfirmBean.getType();
        if (type == 0) {
            showForcePowerOffView(oSDConfirmBean.isShow());
        } else if (type == 1) {
            setNGearByMistakeVisibility(oSDConfirmBean.isShow());
        } else {
            XILog.d(TAG, "invalid bean: " + oSDConfirmBean.toString());
        }
    }

    public void updateOsdConfirmView(OSDConfirmBean oSDConfirmBean) {
        int type = oSDConfirmBean.getType();
        if (type == 0) {
            switchForcePowerOffConfirm(oSDConfirmBean.isConfirm());
        } else if (type == 1) {
            setNGearByMistakeCounterTime(oSDConfirmBean.getCounterTime());
        } else {
            XILog.d(TAG, "invalid bean: " + oSDConfirmBean.toString());
        }
    }

    public void setMediaVolume(float f) {
        OsdMediaVolumeView osdMediaVolumeView = this.mOsdMediaVolumeView;
        if (osdMediaVolumeView != null) {
            osdMediaVolumeView.setMediaVolume(f);
        }
    }

    public void setMediaMute(boolean z) {
        OsdMediaVolumeView osdMediaVolumeView = this.mOsdMediaVolumeView;
        if (osdMediaVolumeView != null) {
            osdMediaVolumeView.setMediaMute(z);
        }
    }

    public void setMediaVolumeVisibility(boolean z) {
        OsdMediaVolumeView osdMediaVolumeView = this.mOsdMediaVolumeView;
        if (osdMediaVolumeView != null) {
            osdMediaVolumeView.setVisibility(z ? 0 : 8);
        }
    }

    public void setCallOrMediaMode(int i) {
        OsdMediaVolumeView osdMediaVolumeView = this.mOsdMediaVolumeView;
        if (osdMediaVolumeView != null) {
            osdMediaVolumeView.changeToCallMode(i);
        }
    }

    public void updatePGearProtectConfirmContent(String str) {
        OsdPGearProtectView osdPGearProtectView = this.mOsdPGearProtectView;
        if (osdPGearProtectView != null) {
            osdPGearProtectView.updateConfirmContent(str);
        }
    }

    public void updatePGearProtectView(int i) {
        OsdPGearProtectView osdPGearProtectView = this.mOsdPGearProtectView;
        if (osdPGearProtectView != null) {
            if (i == 0) {
                osdPGearProtectView.setVisibility(8);
                return;
            }
            osdPGearProtectView.setVisibility(0);
            this.mOsdPGearProtectView.updatePGearProtectView(i);
        }
    }
}
