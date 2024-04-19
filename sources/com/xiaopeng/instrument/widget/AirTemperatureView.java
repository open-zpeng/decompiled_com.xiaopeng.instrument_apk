package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class AirTemperatureView extends XRelativeLayout {
    private static final String DEFAULT_INT_TEMPERATURE = "16";
    private static final String DEFAULT_TEMPERATURE_AFTER = "0";
    private static final String TAG = "AirTemperatureView";
    private static final int TEMPERATURE_VALUE_MAX_SIZE = 2;
    private String[] defaultTemperatureValue;
    private XTextView mAirTemperatureAfterValue;
    private XTextView mAirTemperatureBeforeValue;
    private XTextView mUnit;

    public AirTemperatureView(Context context) {
        this(context, null);
    }

    public AirTemperatureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.defaultTemperatureValue = new String[]{DEFAULT_INT_TEMPERATURE, "0"};
        initView(context);
    }

    public AirTemperatureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.defaultTemperatureValue = new String[]{DEFAULT_INT_TEMPERATURE, "0"};
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.layout_air, this);
        this.mAirTemperatureBeforeValue = (XTextView) findViewById(R.id.temperature_value_decimal_point_start);
        this.mAirTemperatureAfterValue = (XTextView) findViewById(R.id.temperature_value_decimal_point_after);
        this.mUnit = (XTextView) findViewById(R.id.iv_temperature_unit);
    }

    public void setTemperatureValue(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            XILog.d(TAG, "temperature value is null or value is 0");
            strArr = this.defaultTemperatureValue;
        } else if (strArr.length < 2) {
            strArr[1] = "0";
        }
        this.mAirTemperatureBeforeValue.setText(new StringBuilder(strArr[0]).append("."));
        this.mAirTemperatureAfterValue.setText(strArr[1]);
    }
}
