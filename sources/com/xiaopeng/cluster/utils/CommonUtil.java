package com.xiaopeng.cluster.utils;

import android.os.SystemProperties;
/* loaded from: classes.dex */
public class CommonUtil {
    private static final String SYSTEM_PROPERTY_CLUSTER_DISPLAY_SCREEN = "persist.sys.cluster.screen.display";
    private static final String TAG = "CommonUtil";

    public static boolean compareByThreshold(float f, float f2, float f3) {
        return Math.abs(f - f2) < f3;
    }

    public static boolean getIsControlDisplay() {
        boolean z = SystemProperties.getBoolean(SYSTEM_PROPERTY_CLUSTER_DISPLAY_SCREEN, false);
        XILog.i(TAG, "isControlDisplay = " + z);
        return z;
    }
}
