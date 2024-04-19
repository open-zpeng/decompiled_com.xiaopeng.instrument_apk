package com.xiaopeng.instrument.utils;

import android.content.Context;
import com.xiaopeng.instrument.App;
/* loaded from: classes.dex */
public class ResUtil {
    public static final String DRAWABLE = "drawable";
    public static final String RAW = "raw";
    private static Context mContext = App.getInstance().getApplicationContext();

    public static int getDrawableResByName(String str) {
        return mContext.getResources().getIdentifier(str, DRAWABLE, mContext.getPackageName());
    }

    public static int getRawResByName(String str) {
        return mContext.getResources().getIdentifier(str, RAW, mContext.getPackageName());
    }
}
