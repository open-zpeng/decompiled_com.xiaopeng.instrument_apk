package com.xiaopeng.instrument.utils;

import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.utils.delegate.IStringUtilDelegate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
/* loaded from: classes.dex */
public class StringUtil {
    public static final String COLON_DELIMITER = ":";
    public static final String DAY = "dd";
    public static final String HOUR_12 = "hh";
    public static final String HOUR_24 = "HH";
    public static final String MINUTE = "mm";
    public static final String MONTH = "MM";
    public static final String SECONDS = "ss";
    public static final String SPLIT_DELIMITER = ".";
    public static final String TAG = "StringUtil";
    private static final int TIME_MS = 60;
    public static final String YEAR = "yyyy";
    private static IStringUtilDelegate mDelegate;
    private static final DateTimeFormatter sDateTimeFormatter = DateTimeFormatter.ofPattern("mm:ss");
    public static StringBuffer sb = new StringBuffer();

    public static void setDelegate(IStringUtilDelegate iStringUtilDelegate) {
        mDelegate = iStringUtilDelegate;
    }

    public static String switchLineWhenWidthLargerThanMax(String str) {
        IStringUtilDelegate iStringUtilDelegate = mDelegate;
        if (iStringUtilDelegate == null) {
            XILog.w(TAG, "mDelegate is null");
            return str;
        }
        return iStringUtilDelegate.switchLineWhenWidthLargerThanMax(str);
    }

    public static String switchLineWhenWidthLargerThanMax(String str, int i) {
        IStringUtilDelegate iStringUtilDelegate = mDelegate;
        if (iStringUtilDelegate == null) {
            XILog.w(TAG, "mDelegate is null");
            return str;
        }
        return iStringUtilDelegate.switchLineWhenWidthLargerThanMax(str, i);
    }

    public static String stringWrap(String str) {
        IStringUtilDelegate iStringUtilDelegate = mDelegate;
        if (iStringUtilDelegate == null) {
            XILog.w(TAG, "mDelegate is null");
            return str;
        }
        return iStringUtilDelegate.stringWrap(str);
    }

    public static String stringWrap(String str, int i) {
        IStringUtilDelegate iStringUtilDelegate = mDelegate;
        if (iStringUtilDelegate == null) {
            XILog.w(TAG, "mDelegate is null");
            return str;
        }
        return iStringUtilDelegate.stringWrap(str, i);
    }

    public static String[] splitStringByPeriod(float f) {
        IStringUtilDelegate iStringUtilDelegate = mDelegate;
        if (iStringUtilDelegate == null) {
            XILog.w(TAG, "mDelegate is null");
            return new String[]{String.valueOf(f)};
        }
        return iStringUtilDelegate.splitStringByPeriod(f);
    }

    public static String toTrim(String str) {
        return str != null ? str.trim() : str;
    }

    public static String stringForTime(int i) {
        return sDateTimeFormatter.format(LocalTime.of(0, (i / 60) % 60, i % 60));
    }
}
