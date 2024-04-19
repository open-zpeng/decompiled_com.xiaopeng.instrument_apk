package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.data.ChargeRepository;
/* loaded from: classes.dex */
public class AccSettingView extends AbstractAccSettingView {
    @Override // com.xiaopeng.instrument.widget.AbstractAccSettingView
    protected int getLayout() {
        return R.layout.info_acc_setting;
    }

    public AccSettingView(Context context) {
        super(context);
    }

    public AccSettingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AccSettingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.AbstractAccSettingView
    protected void showSpeedSetting() {
        this.mBgView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_acc_speed, null));
        this.mAccTitle.setText(R.string.acc_setting_speed_title);
        this.mTvGuidDistance.setVisibility(8);
        this.mTvGuidSpeed.setVisibility(8);
        this.mTvDistanceGuide.setVisibility(8);
        this.mTvAccSettingInfo.setVisibility(0);
        this.mTvAccSettingInfo.setText(ChargeRepository.DEFAULT_ENDURANCE_VALUE);
        this.mTvAccSpeedUnit.setVisibility(0);
        this.mLlSpeedSetting.setVisibility(0);
        this.mSpeedUseLeftScroll.setVisibility(BaseConfig.getInstance().isSupportShowSpeedUseLeftScroll() ? 0 : 8);
    }
}
