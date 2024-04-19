package com.xiaopeng.instrument.utils.delegate.impl;

import android.graphics.Paint;
import android.text.TextUtils;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate;
/* loaded from: classes.dex */
public class EnStringUtilDelegateImpl implements IStringUtilDelegate {
    private static final int DEFAULT_MAX_WIDTH = 145;
    private static final int DEFAULT_WRAP_SIZE = 13;
    private static final int INDEX = 0;
    public static final String SPLIT_DELIMITER = ".";
    public static final String TAG = "EnStringUtilDelegateImpl";
    private static final String WRAP_PRE = "\n";
    private static StringBuilder mStringBuilder;
    private static final Paint sTextMeasurePaint = new Paint();

    @Override // com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate
    public String switchLineWhenWidthLargerThanMax(String str) {
        return switchLineWhenWidthLargerThanMax(str, DEFAULT_MAX_WIDTH);
    }

    @Override // com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate
    public String switchLineWhenWidthLargerThanMax(String str, int i) {
        if (TextUtils.isEmpty(str) || i <= 0 || str.contains(WRAP_PRE)) {
            return str;
        }
        float f = i;
        if (sTextMeasurePaint.measureText(str, 0, str.length()) < f) {
            return str;
        }
        int i2 = 0;
        for (int i3 = 1; i3 < str.length() && sTextMeasurePaint.measureText(str, 0, i3) <= f; i3++) {
            char charAt = str.charAt(i3);
            if (charAt == ' ' || charAt == ',' || charAt == '.') {
                i2 = i3 + 1;
            }
        }
        if (i2 == 0) {
            i2 = str.length();
            while (sTextMeasurePaint.measureText(str, 0, i2) > f) {
                i2--;
                if (i2 <= 0) {
                    return str;
                }
            }
        }
        return i2 < str.length() ? str.substring(0, i2).trim() + WRAP_PRE + str.substring(i2).trim() : str;
    }

    @Override // com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate
    public String stringWrap(String str) {
        return stringWrap(str, 13);
    }

    @Override // com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate
    public String stringWrap(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            if (str.length() >= i) {
                StringBuilder sb = new StringBuilder();
                mStringBuilder = sb;
                int i2 = i - 1;
                sb.append(str.substring(0, i2));
                mStringBuilder.append(WRAP_PRE);
                mStringBuilder.append(str.substring(i2));
                return mStringBuilder.toString();
            }
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    @Override // com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate
    public String[] splitStringByPeriod(float f) {
        String valueOf = String.valueOf(f);
        if (TextUtils.isEmpty(valueOf)) {
            return null;
        }
        if (valueOf.contains(".")) {
            return valueOf.split("\\.");
        }
        XILog.d(TAG, "str is not contain splitStr");
        return null;
    }
}
