package com.xiaopeng.cluster.utils;

import android.os.SystemProperties;
import android.telephony.Rlog;
import android.util.Log;
/* loaded from: classes.dex */
public class XILog {
    private static final String TAG = "xInstru_";
    private static final int XILog_TYPE_MAIN = 1;
    private static final int XILog_TYPE_RADIO = 2;
    private static final String LOG_LEVEL_NAME = "persist.hmi.log_level";
    private static final int LOG_LEVEL = SystemProperties.getInt(LOG_LEVEL_NAME, 2);
    private static int mXILogType = 2;

    public static void w(String str, String str2) {
        if (LOG_LEVEL < 1) {
            return;
        }
        String str3 = TAG + str;
        int i = mXILogType;
        if (i == 1) {
            Log.w(str3, str2);
        } else if (i == 2) {
            Rlog.w(str3, str2);
        } else {
            Rlog.w(str3, str2);
        }
    }

    public static void i(String str, String str2) {
        if (LOG_LEVEL < 2) {
            return;
        }
        String str3 = TAG + str;
        int i = mXILogType;
        if (i == 1) {
            Log.i(str3, str2);
        } else if (i == 2) {
            Rlog.i(str3, str2);
        } else {
            Rlog.i(str3, str2);
        }
    }

    public static void v(String str, String str2) {
        if (LOG_LEVEL < 4) {
            return;
        }
        String str3 = TAG + str;
        int i = mXILogType;
        if (i == 1) {
            Log.v(str3, str2);
        } else if (i == 2) {
            Rlog.v(str3, str2);
        } else {
            Rlog.v(str3, str2);
        }
    }

    public static void e(String str, String str2) {
        if (LOG_LEVEL < 0) {
            return;
        }
        String str3 = TAG + str;
        int i = mXILogType;
        if (i == 1) {
            Log.e(str3, str2);
        } else if (i == 2) {
            Rlog.e(str3, str2);
        } else {
            Rlog.e(str3, str2);
        }
    }

    public static void d(String str, String str2) {
        if (LOG_LEVEL < 3) {
            return;
        }
        String str3 = TAG + str;
        int i = mXILogType;
        if (i == 1) {
            Log.d(str3, str2);
        } else if (i == 2) {
            Rlog.d(str3, str2);
        } else {
            Rlog.d(str3, str2);
        }
    }
}
