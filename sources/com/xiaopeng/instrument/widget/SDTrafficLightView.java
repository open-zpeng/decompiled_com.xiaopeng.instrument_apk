package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
/* loaded from: classes.dex */
public class SDTrafficLightView extends XRelativeLayout {
    private static final String TAG = "SDTrafficLightView";
    private XImageView mStraightLight;

    public SDTrafficLightView(Context context) {
        this(context, null);
    }

    public SDTrafficLightView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SDTrafficLightView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.info_trafficlight_sd, this);
        this.mStraightLight = (XImageView) findViewById(R.id.traffic_straight_light);
    }

    public void setCurrentTrafficType(int i) {
        XILog.i(TAG, "setCurrentTrafficType: " + i);
        if (this.mStraightLight.getVisibility() == 8) {
            this.mStraightLight.setVisibility(0);
        }
        if (i == 2) {
            this.mStraightLight.setImageResource(R.drawable.sr_straight_red_light_day);
        } else if (i == 3) {
            this.mStraightLight.setImageResource(R.drawable.sr_straight_yellow_light_day);
        } else if (i == 4) {
            this.mStraightLight.setImageResource(R.drawable.sr_straight_green_light_day);
        } else {
            this.mStraightLight.setVisibility(8);
        }
    }
}
