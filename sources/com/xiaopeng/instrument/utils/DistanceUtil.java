package com.xiaopeng.instrument.utils;

import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
/* loaded from: classes.dex */
public class DistanceUtil {
    public static final String TAG = "DistanceUtil";

    public static String unitTypeToName(int i) {
        int i2 = i != 0 ? i != 1 ? 0 : R.string.navi_distance_km : R.string.navi_distance_m;
        if (i2 == 0) {
            XILog.w(TAG, "Unknown distance unit type");
            return "";
        }
        return App.getInstance().getApplicationContext().getString(i2);
    }
}
