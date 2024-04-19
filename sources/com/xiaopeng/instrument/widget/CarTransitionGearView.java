package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class CarTransitionGearView extends XRelativeLayout {
    public static final String TAG = "CarTransitionGearView";
    private XTextView mGearView;

    public CarTransitionGearView(Context context) {
        this(context, null);
    }

    public CarTransitionGearView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CarTransitionGearView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_transition_gear, this);
        this.mGearView = (XTextView) findViewById(R.id.transition_gear_value);
    }

    public void setGearValue(String str) {
        this.mGearView.setText(str);
    }
}
