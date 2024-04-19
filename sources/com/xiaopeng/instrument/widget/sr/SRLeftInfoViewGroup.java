package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.instrument.R;
/* loaded from: classes.dex */
public class SRLeftInfoViewGroup extends SRBaseInfoView {
    private SRSensorFaultCardView mSensorFaultCardView;

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    int getLayout() {
        return R.layout.layout_info_left_sr;
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    int getPosition() {
        return 0;
    }

    public SRLeftInfoViewGroup(Context context) {
        this(context, null);
    }

    public SRLeftInfoViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SRLeftInfoViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void init(Context context) {
        super.init(context);
        this.mSensorFaultCardView = (SRSensorFaultCardView) findViewById(R.id.info_sensor_fault);
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showMapCarView() {
        super.showMapCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showMediaCarView() {
        super.showMediaCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showCarConditionCarView() {
        super.showCarConditionCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showSubCardView(int i) {
        super.showSubCardView(i);
        if (i == 5) {
            showSensorFaultCardView();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showPowerConsumptionCarView() {
        super.showPowerConsumptionCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showOdoMeterCarView() {
        super.showOdoMeterCarView();
        this.mSensorFaultCardView.setVisibility(8);
    }

    public void showSensorFaultCardView() {
        hideFixedCardView();
        this.mSensorFaultCardView.setVisibility(0);
    }
}
