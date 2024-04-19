package com.xiaopeng.MeterSD;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.xiaopeng.instrument.utils.StringUtil;
/* loaded from: classes.dex */
public class XeReceiver extends BroadcastReceiver {
    private static final String TAG = "metersd";

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.xiaopeng.xengine.debug")) {
            String str = new String();
            for (String str2 : intent.getExtras().keySet()) {
                String stringExtra = intent.getStringExtra(str2);
                if (!str2.isEmpty() && !stringExtra.isEmpty()) {
                    if (str.isEmpty()) {
                        str = str + str2 + StringUtil.COLON_DELIMITER + stringExtra;
                    } else {
                        str = str + "," + str2 + StringUtil.COLON_DELIMITER + stringExtra;
                    }
                }
            }
            if (str.isEmpty()) {
                return;
            }
            MeterSDRender.setCmdKeyValue(str);
        }
    }
}
