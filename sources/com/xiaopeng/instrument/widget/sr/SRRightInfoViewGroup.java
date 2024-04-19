package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.instrument.R;
/* loaded from: classes.dex */
public class SRRightInfoViewGroup extends SRBaseInfoView {
    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    int getLayout() {
        return R.layout.layout_info_right_sr;
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    int getPosition() {
        return 1;
    }

    public SRRightInfoViewGroup(Context context) {
        this(context, null);
    }

    public SRRightInfoViewGroup(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SRRightInfoViewGroup(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void init(Context context) {
        super.init(context);
    }

    @Override // com.xiaopeng.instrument.widget.sr.SRBaseInfoView
    public void showMapCarView() {
        super.showMapCarView();
    }
}
