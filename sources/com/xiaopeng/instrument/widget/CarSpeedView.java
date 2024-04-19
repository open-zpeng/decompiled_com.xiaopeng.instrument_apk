package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import com.xiaopeng.instrument.view.FragmentType;
import com.xiaopeng.instrument.widget.sr.SRTopTipsView;
import com.xiaopeng.instrument.widget.sr.SRTrafficLightView;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class CarSpeedView extends XRelativeLayout {
    private static final String TAG = "CarSpeedView";
    private FragmentType mCurrentType;
    private XImageView mHoldView;
    private XpuInstrumentClient.ITypeChangeListener mListener;
    private SDTrafficLightView mSDTrafficLightView;
    private SRTopTipsView mSRTopTipsView;
    private SRTrafficLightView mSRTrafficLightView;
    private int mSRViewType;
    private XTextView mSpeedUnit;
    private XTextView mSpeedValue;

    public CarSpeedView(Context context) {
        this(context, null);
    }

    public CarSpeedView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CarSpeedView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_car_speed, this);
        this.mSpeedValue = (XTextView) findViewById(R.id.car_speed_value);
        this.mSpeedUnit = (XTextView) findViewById(R.id.car_speed_unit);
        this.mHoldView = (XImageView) findViewById(R.id.adas_hold);
        this.mSDTrafficLightView = (SDTrafficLightView) findViewById(R.id.sd_traffic_light);
        this.mSRTrafficLightView = (SRTrafficLightView) findViewById(R.id.sr_traffic_light);
        this.mSRTopTipsView = (SRTopTipsView) findViewById(R.id.sr_top_tips);
        this.mListener = new XpuInstrumentClient.ITypeChangeListener() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarSpeedView$tdx-qdRkcFBAKiBe59cTyL5vXGU
            @Override // com.xiaopeng.instrument.manager.XpuInstrumentClient.ITypeChangeListener
            public final void onFragmentTypeChange(FragmentType fragmentType) {
                CarSpeedView.this.lambda$init$0$CarSpeedView(fragmentType);
            }
        };
        XpuInstrumentClient.getInstance().addTypeChangeListener(this.mListener);
    }

    public /* synthetic */ void lambda$init$0$CarSpeedView(FragmentType fragmentType) {
        if (this.mCurrentType != fragmentType) {
            setSpeedUnitVisible(true);
            setSDTrafficLightVisible(false);
            this.mSRTopTipsView.setVisibility(8);
            this.mSRTrafficLightView.setVisibility(8);
        }
        this.mCurrentType = fragmentType;
    }

    public void setIsShowHold(boolean z) {
        this.mHoldView.setVisibility(z ? 0 : 8);
    }

    public void setSpeedValue(String str) {
        XILog.d(TAG, str);
        this.mSpeedValue.setText(str);
    }

    public void setSpeedUnitVisible(boolean z) {
        XTextView xTextView = this.mSpeedUnit;
        if (xTextView != null) {
            xTextView.setVisibility(z ? 0 : 8);
        }
    }

    public void setSDTrafficLightVisible(boolean z) {
        SDTrafficLightView sDTrafficLightView = this.mSDTrafficLightView;
        if (sDTrafficLightView != null) {
            sDTrafficLightView.setVisibility(z ? 0 : 8);
        }
    }

    public void showSDTrafficLightByType(int i) {
        SDTrafficLightView sDTrafficLightView = this.mSDTrafficLightView;
        if (sDTrafficLightView != null) {
            sDTrafficLightView.setCurrentTrafficType(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XpuInstrumentClient.getInstance().removeTypeChangeListener(this.mListener);
    }

    public void showSRTrafficLightView(boolean z) {
        XILog.i(TAG, "showTrafficLightView mCurrentType:" + this.mCurrentType + " mSRViewType: " + this.mSRViewType + " isShow: " + z);
        if (this.mCurrentType == FragmentType.NAVI_SR || BaseConfig.getInstance().isSupportNoSRToShowTrafficLight()) {
            if (z) {
                if (this.mSRViewType == 2) {
                    this.mSRTopTipsView.setVisibility(8);
                }
                this.mSRTrafficLightView.setVisibility(0);
                setSpeedUnitVisible(false);
                this.mSRViewType = 1;
            } else if (this.mSRViewType == 1) {
                this.mSRTrafficLightView.setVisibility(8);
                setSpeedUnitVisible(true);
                this.mSRViewType = 0;
            }
        }
    }

    public void showSRTrafficEventView(boolean z) {
        XILog.i(TAG, "showTrafficEventView mode: " + this.mSRViewType + " isShow: " + z);
        if (this.mCurrentType != FragmentType.NAVI_SR) {
            return;
        }
        if (z) {
            if (this.mSRViewType == 1) {
                return;
            }
            this.mSRTopTipsView.setVisibility(0);
            setSpeedUnitVisible(false);
            this.mSRViewType = 2;
        } else if (this.mSRViewType == 2) {
            this.mSRTopTipsView.setVisibility(8);
            setSpeedUnitVisible(true);
            this.mSRViewType = 0;
        }
    }
}
