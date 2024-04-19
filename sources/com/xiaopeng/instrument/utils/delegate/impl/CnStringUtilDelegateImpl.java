package com.xiaopeng.instrument.utils.delegate.impl;

import android.graphics.Paint;
import android.text.TextUtils;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate;
/* loaded from: classes.dex */
public class CnStringUtilDelegateImpl implements IStringUtilDelegate {
    private static final int DEFAULT_COZY_WIDTH = 120;
    private static final int DEFAULT_MAX_WIDTH = 145;
    private static final int DEFAULT_WRAP_SIZE = 13;
    private static final int INDEX = 0;
    public static final String SPLIT_DELIMITER = ".";
    public static final String TAG = "CnStringUtilDelegateImpl";
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
        int length = str.length();
        if (sTextMeasurePaint.measureText(str, 0, length) > i) {
            while (sTextMeasurePaint.measureText(str, 0, length) > 120.0f) {
                length--;
                if (length <= 0) {
                    return str;
                }
            }
        }
        return length < str.length() ? str.substring(0, length) + WRAP_PRE + str.substring(length) : str;
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
