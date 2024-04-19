package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
/* loaded from: classes.dex */
public class LeftInfoViewGroup extends BaseInfoView {
    public static final String TAG = "LeftInfoViewGroup";
    private SensorFaultCardView mSensorFaultCardView;

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    int getLayout() {
        return R.layout.layout_info_left;
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    int getPosition() {
        return 0;
    }

    public LeftInfoViewGroup(Context context) {
        this(context, null);
    }

    public LeftInfoViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LeftInfoViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void init(Context context) {
        super.init(context);
        this.mSensorFaultCardView = (SensorFaultCardView) findViewById(R.id.info_sensor_fault);
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showMapCarView() {
        super.showMapCarView();
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            SurfaceViewManager.getInstance().setLeftSDSurface(this.mMapCardView);
            SurfaceViewManager.getInstance().startLeftSDChangeService();
        }
        this.mSensorFaultCardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showMediaCarView() {
        super.showMediaCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showCarConditionCarView() {
        super.showCarConditionCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showSubCardView(int i) {
        super.showSubCardView(i);
        if (i == 5) {
            showSensorFaultCardView();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showPowerConsumptionCarView() {
        super.showPowerConsumptionCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showOdoMeterCarView() {
        super.showOdoMeterCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    public void showSensorFaultCardView() {
        hideFixedCardView();
        this.mSensorFaultCardView.setVisibility(0);
    }
}
