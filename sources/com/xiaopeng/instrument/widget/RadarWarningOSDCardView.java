package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.CarBodyColorBean;
import com.xiaopeng.instrument.bean.FrontDistBean;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class RadarWarningOSDCardView extends XRelativeLayout {
    private static final String TAG = "RadarWarningOSDCardView";
    private XImageView mCarFrontBody;
    private FrontDistBean mFrontDistBean;
    private FrontRadarWarningView mRadarWarning;
    private XTextView mTvDistance;

    public RadarWarningOSDCardView(Context context) {
        this(context, null);
    }

    public RadarWarningOSDCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFrontDistBean = new FrontDistBean();
        init();
    }

    public RadarWarningOSDCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFrontDistBean = new FrontDistBean();
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_radar_warning, this);
        this.mTvDistance = (XTextView) inflate.findViewById(R.id.tv_radar_distance);
        this.mRadarWarning = (FrontRadarWarningView) inflate.findViewById(R.id.frw_radar);
        this.mCarFrontBody = (XImageView) inflate.findViewById(R.id.car_front_body);
    }

    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            updateRadarDisTextValue(this.mFrontDistBean);
        }
    }

    public void updateRadarLevel(int i, int i2) {
        this.mRadarWarning.updateRadarLevel(i, i2);
    }

    public void updateRadarDisTextValue(FrontDistBean frontDistBean) {
        this.mFrontDistBean = frontDistBean;
        String distValue = frontDistBean.getDistValue();
        XILog.d(TAG, "updateRadarDisTextValue:" + frontDistBean.getState());
        int state = frontDistBean.getState();
        if (state == 0) {
            this.mTvDistance.setText("");
            this.mTvDistance.setTextColor(ContextCompat.getColor(getContext(), R.color.radar_distance_text_color_default));
            this.mTvDistance.setVisibility(8);
        } else if (state == 1) {
            this.mTvDistance.setText(distValue);
            this.mTvDistance.setTextColor(ContextCompat.getColor(getContext(), R.color.text_color_07));
            this.mTvDistance.setVisibility(0);
        } else if (state != 2) {
        } else {
            this.mTvDistance.setText(App.getInstance().getResources().getString(R.string.radar_warning_dis_value, distValue));
            this.mTvDistance.setTextColor(ContextCompat.getColor(getContext(), R.color.radar_distance_text_color_default));
            this.mTvDistance.setVisibility(0);
        }
    }

    public void updateCarBodyBg(CarBodyColorBean carBodyColorBean) {
        XILog.d(TAG, "updateCarBodyBg:" + carBodyColorBean.getImgResFrontName());
        this.mCarFrontBody.setImageResource(carBodyColorBean.getImgResFrontId());
    }

    public void updateRadarDistance(int i, int i2) {
        this.mRadarWarning.updateRadarDistance(i, i2);
    }
}
