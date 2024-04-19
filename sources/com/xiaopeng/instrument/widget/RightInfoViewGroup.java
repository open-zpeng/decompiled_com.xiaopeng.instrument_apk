package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
/* loaded from: classes.dex */
public class RightInfoViewGroup extends BaseInfoView {
    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    int getLayout() {
        return R.layout.layout_info_right;
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    int getPosition() {
        return 1;
    }

    public RightInfoViewGroup(Context context) {
        this(context, null);
    }

    public RightInfoViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public RightInfoViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void init(Context context) {
        super.init(context);
    }

    @Override // com.xiaopeng.instrument.widget.BaseInfoView
    public void showMapCarView() {
        super.showMapCarView();
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            SurfaceViewManager.getInstance().setRightSDSurface(this.mMapCardView);
            SurfaceViewManager.getInstance().startRightSDChangeService();
        }
    }
}
