package com.xiaopeng.instrument.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.text.format.DateFormat;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
/* loaded from: classes.dex */
public class SystemChangeBroadReceiver extends BroadcastReceiver {
    private static final String ACTION_OTA_STATE = "com.xiaopeng.broadcast.ACTION_OTA_STATE";
    private static final String KEY_OTA_STATE = "state";
    private static final String TAG = "SystemChangeBroadReceiver";
    private static final int TIME_PATTERN_12 = 1;
    private static final int TIME_PATTERN_24 = 0;
    private static SystemChangeBroadReceiver sReceiver;
    final SimpleDateFormat m24Format = new SimpleDateFormat("HH:mm", Locale.getDefault());
    final SimpleDateFormat m12Format = new SimpleDateFormat("hh:mm", Locale.getDefault());
    private SimpleDateFormat mCurrentTimeFormat = null;

    public static synchronized void registerSystemChangeReceiver(Context context) {
        synchronized (SystemChangeBroadReceiver.class) {
            if (sReceiver == null) {
                sReceiver = new SystemChangeBroadReceiver();
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(ACTION_OTA_STATE);
                context.registerReceiver(sReceiver, intentFilter);
            }
        }
    }

    public static synchronized void unRegisterSystemChangeReceiver(Context context) {
        synchronized (SystemChangeBroadReceiver.class) {
            SystemChangeBroadReceiver systemChangeBroadReceiver = sReceiver;
            if (systemChangeBroadReceiver != null) {
                context.unregisterReceiver(systemChangeBroadReceiver);
                sReceiver = null;
            }
        }
    }

    public static synchronized void refreshTime() {
        synchronized (SystemChangeBroadReceiver.class) {
            SystemChangeBroadReceiver systemChangeBroadReceiver = sReceiver;
            if (systemChangeBroadReceiver != null) {
                systemChangeBroadReceiver.resetTimePattern();
                sReceiver.sendTime();
            }
        }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(intent.getAction(), "android.intent.action.TIME_TICK") || TextUtils.equals(intent.getAction(), "android.intent.action.TIME_SET") || !TextUtils.equals(intent.getAction(), ACTION_OTA_STATE)) {
            return;
        }
        sendOta(intent);
    }

    private void sendOta(Intent intent) {
        if (intent == null) {
            XILog.d(TAG, "sendOta intent is null");
            return;
        }
        try {
            int intExtra = intent.getIntExtra(KEY_OTA_STATE, 0);
            XILog.i(TAG, "sendOta:" + intExtra);
            ClusterManager.getInstance().onOtaState(intExtra);
        } catch (Exception e) {
            XILog.i(TAG, "sendOta " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendTime() {
        if (this.mCurrentTimeFormat != null) {
            Calendar calendar = Calendar.getInstance(Locale.getDefault());
            String format = this.mCurrentTimeFormat.format(calendar.getTime());
            ClusterManager.getInstance().onMorningOrAfternoon(calendar.get(9));
            ClusterManager.getInstance().onTime(format);
            XILog.i(TAG, "sendTime:" + format);
        }
    }

    private void resetTimePattern() {
        int i;
        if (DateFormat.is24HourFormat(App.getInstance())) {
            this.mCurrentTimeFormat = this.m24Format;
            i = 0;
        } else {
            this.mCurrentTimeFormat = this.m12Format;
            i = 1;
        }
        XILog.i(TAG, "resetTimePattern:" + i);
        ClusterManager.getInstance().onTimePattern(i);
    }
}
