package com.xiaopeng.instrument.onlyfortest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.ChargingState;
/* loaded from: classes.dex */
public class TestBroadcastReceiver extends BroadcastReceiver {
    private static final String ACTION_ALL_TEST_FUNCTION = "com.xiaopeng.broadcast.ACTION_ALL_TEST_FUNCTION";
    private static final String ACTION_CHARGE = "com.xiaopeng.instrument.ACTION_CHARGE";
    private static final String ACTION_DATA = "com.xiaopeng.instrument.ACTION_DATA";
    private static final String ACTION_LIST_DOWN = "com.xiaopeng.instrument.ACTION_LIST_DOWN";
    private static final String ACTION_LIST_SELECT = "com.xiaopeng.instrument.ACTION_LIST_SELECT";
    private static final String ACTION_LIST_UP = "com.xiaopeng.instrument.ACTION_LIST_UP";
    private static final String ACTION_OTA_STATE = "com.xiaopeng.broadcast.ACTION_OTA_STATE";
    private static final String ACTION_SHOW_LIST = "com.xiaopeng.instrument.ACTION_SHOW_LIST";
    private static final String ACTION_XPU_TEST_DATA = "com.xiaopeng.broadcast.ACTION_XPU_TEST_DATA";
    private static final String KEY_CHARGE_STATE = "charge_state";
    private static final String KEY_LIST_INDEX = "list_index";
    private static final String KEY_LIST_POS = "list_pos";
    private static final String KEY_LIST_VISIBLE = "list_visible";
    private static final String KEY_OTA_STATE = "state";
    private static final String TAG = "TestBroadCastReceiver";
    private static TestBroadcastReceiver sTestReceiver;
    private final int RIGHT_MAX_INDEX = 4;
    private final int LEFT_MAX_INDEX = 4;
    private int mCurrentLeftListIndex = 0;
    private int mCurrentRightListIndex = 0;

    public static void registerTest(Context context) {
        if (context == null || sTestReceiver != null) {
            return;
        }
        XILog.d(TAG, "register test broadcast");
        sTestReceiver = new TestBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_CHARGE);
        intentFilter.addAction(ACTION_SHOW_LIST);
        intentFilter.addAction(ACTION_LIST_UP);
        intentFilter.addAction(ACTION_LIST_DOWN);
        intentFilter.addAction(ACTION_LIST_SELECT);
        intentFilter.addAction(ACTION_DATA);
        intentFilter.addAction(ACTION_OTA_STATE);
        intentFilter.addAction(ACTION_ALL_TEST_FUNCTION);
        intentFilter.addAction(ACTION_XPU_TEST_DATA);
        context.registerReceiver(sTestReceiver, intentFilter);
    }

    public static void unRegisterTest(Context context) {
        TestBroadcastReceiver testBroadcastReceiver;
        if (context == null || (testBroadcastReceiver = sTestReceiver) == null) {
            return;
        }
        context.unregisterReceiver(testBroadcastReceiver);
        sTestReceiver = null;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        char c;
        String action = intent.getAction();
        XILog.d(TAG, "TestBroadCastReceiver onReceive:" + action);
        action.hashCode();
        switch (action.hashCode()) {
            case -1739084947:
                if (action.equals(ACTION_XPU_TEST_DATA)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -719890262:
                if (action.equals(ACTION_SHOW_LIST)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -503922938:
                if (action.equals(ACTION_OTA_STATE)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -396808470:
                if (action.equals(ACTION_CHARGE)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case -331115168:
                if (action.equals(ACTION_DATA)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case -204420793:
                if (action.equals(ACTION_LIST_SELECT)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -40873043:
                if (action.equals(ACTION_LIST_DOWN)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -32610241:
                if (action.equals(ACTION_ALL_TEST_FUNCTION)) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 26773606:
                if (action.equals(ACTION_LIST_UP)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
                TestXpuHelper.doAction(intent);
                return;
            case 1:
                boolean booleanExtra = intent.getBooleanExtra(KEY_LIST_VISIBLE, false);
                int intExtra = intent.getIntExtra(KEY_LIST_POS, 0);
                int intExtra2 = intent.getIntExtra(KEY_LIST_INDEX, 0);
                if (intExtra == 0) {
                    this.mCurrentLeftListIndex = intExtra2;
                    ClusterManager.getInstance().onLeftCardVisible(!booleanExtra);
                    ClusterManager.getInstance().onLeftListVisible(booleanExtra);
                    ClusterManager.getInstance().onLeftListIndex(this.mCurrentLeftListIndex);
                    return;
                }
                this.mCurrentRightListIndex = intExtra2;
                ClusterManager.getInstance().onRightCardVisible(!booleanExtra);
                ClusterManager.getInstance().onRightListVisible(booleanExtra);
                ClusterManager.getInstance().onRightListIndex(this.mCurrentRightListIndex);
                return;
            case 2:
                sendOta(intent);
                return;
            case 3:
                int intExtra3 = intent.getIntExtra(KEY_CHARGE_STATE, -1);
                if (ChargingState.parse(intExtra3) == ChargingState.CHARGING_STATE_UNKNOWN) {
                    ClusterManager.getInstance().onIsCharging(false);
                    return;
                } else if (ChargingState.parse(intExtra3) == ChargingState.CHARGING_STATE_NOT_CONNECTED) {
                    ClusterManager.getInstance().onIsCharging(true);
                    return;
                } else {
                    ClusterManager.getInstance().onChargingState(intExtra3);
                    return;
                }
            case 4:
                Bundle extras = intent.getExtras();
                if (extras != null) {
                    for (String str : extras.keySet()) {
                        TestDataHelper.handleData(str, extras.get(str));
                    }
                    return;
                }
                return;
            case 5:
                if (intent.getIntExtra(KEY_LIST_POS, 0) == 0) {
                    ClusterManager.getInstance().onLeftCardIndex(this.mCurrentLeftListIndex);
                    ClusterManager.getInstance().onLeftListVisible(false);
                    ClusterManager.getInstance().onLeftCardVisible(true);
                    return;
                }
                ClusterManager.getInstance().onRightCardIndex(this.mCurrentRightListIndex);
                ClusterManager.getInstance().onRightListVisible(false);
                ClusterManager.getInstance().onRightCardVisible(true);
                return;
            case 6:
                if (intent.getIntExtra(KEY_LIST_POS, 0) == 0) {
                    int i = this.mCurrentLeftListIndex;
                    if (i >= 4) {
                        this.mCurrentLeftListIndex = 0;
                    } else {
                        this.mCurrentLeftListIndex = i + 1;
                    }
                    ClusterManager.getInstance().onLeftListIndex(this.mCurrentLeftListIndex);
                    return;
                }
                int i2 = this.mCurrentRightListIndex;
                if (i2 >= 4) {
                    this.mCurrentRightListIndex = 0;
                } else {
                    this.mCurrentRightListIndex = i2 + 1;
                }
                ClusterManager.getInstance().onRightListIndex(this.mCurrentRightListIndex);
                return;
            case 7:
                TestAllFunctionHelper.doAction(intent);
                return;
            case '\b':
                if (intent.getIntExtra(KEY_LIST_POS, 0) == 0) {
                    int i3 = this.mCurrentLeftListIndex;
                    if (i3 <= 0) {
                        this.mCurrentLeftListIndex = 4;
                    } else {
                        this.mCurrentLeftListIndex = i3 - 1;
                    }
                    ClusterManager.getInstance().onLeftListIndex(this.mCurrentLeftListIndex);
                    return;
                }
                int i4 = this.mCurrentRightListIndex;
                if (i4 <= 0) {
                    this.mCurrentRightListIndex = 4;
                } else {
                    this.mCurrentRightListIndex = i4 - 1;
                }
                ClusterManager.getInstance().onRightListIndex(this.mCurrentRightListIndex);
                return;
            default:
                return;
        }
    }

    private void sendOta(Intent intent) {
        if (intent == null) {
            XILog.d(TAG, "sendOta intent is null");
            return;
        }
        try {
            ClusterManager.getInstance().onOtaState(intent.getIntExtra(KEY_OTA_STATE, 0));
        } catch (Exception e) {
            XILog.i(TAG, "sendOta " + e.getMessage());
            e.printStackTrace();
        }
    }
}
