package com.xiaopeng.instrument.utils;

import android.os.SystemProperties;
import android.telephony.PhoneNumberUtils;
import com.xiaopeng.cluster.utils.XILog;
import java.util.Locale;
/* loaded from: classes.dex */
public class CommonUtil {
    private static final String SYSTEM_PROPERTY_THEME_STYLE = "persist.sys.theme.style";
    private static final String TAG = "CommonUtil";
    private static boolean mHasRegisterSystemReceiver;

    public static boolean isEqual(int i, int i2) {
        return i == i2;
    }

    public static boolean isEqual(boolean z, boolean z2) {
        return z == z2;
    }

    public static boolean hasRegisterSystemReceiver() {
        return mHasRegisterSystemReceiver;
    }

    public static void setIsHasRegisterSystemReceiver(boolean z) {
        mHasRegisterSystemReceiver = z;
    }

    public static boolean compareByThreshold(float f, float f2, float f3) {
        float f4 = f - f2;
        XILog.d(TAG, "test:" + Math.abs(f4));
        return Math.abs(f4) < f3;
    }

    public static String formatPhoneNumberByCountry(String str) {
        return PhoneNumberUtils.formatNumber(str, Locale.getDefault().getCountry());
    }

    public static String getThemeStyle() {
        String str = SystemProperties.get(SYSTEM_PROPERTY_THEME_STYLE);
        XILog.i(TAG, "isThemeStyle = " + str);
        return str;
    }
}
