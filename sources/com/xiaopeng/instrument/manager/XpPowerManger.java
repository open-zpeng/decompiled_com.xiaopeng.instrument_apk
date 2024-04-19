package com.xiaopeng.instrument.manager;

import android.os.PowerManager;
import android.os.SystemClock;
import com.xiaopeng.cluster.IXpPowerManger;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
/* loaded from: classes.dex */
public class XpPowerManger implements IXpPowerManger {
    private static final String TAG = "XpPowerManger";
    private PowerManager mPowerManager;

    private XpPowerManger() {
        this.mPowerManager = (PowerManager) App.getInstance().getSystemService(PowerManager.class);
    }

    public static XpPowerManger getInstance() {
        return XpPowerMangerHolder.instance;
    }

    /* loaded from: classes.dex */
    private static class XpPowerMangerHolder {
        private static final XpPowerManger instance = new XpPowerManger();

        private XpPowerMangerHolder() {
        }
    }

    @Override // com.xiaopeng.cluster.IXpPowerManger
    public void setScreen(boolean z) {
        XILog.i(TAG, "setScreen:" + z);
        long uptimeMillis = SystemClock.uptimeMillis();
        try {
            if (z) {
                this.mPowerManager.setXpIcmScreenOn(uptimeMillis);
            } else {
                this.mPowerManager.setXpIcmScreenOff(uptimeMillis);
            }
        } catch (Exception e) {
            XILog.e(TAG, "setScreen error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override // com.xiaopeng.cluster.IXpPowerManger
    public void setDisplayState(int i) {
        XILog.i(TAG, "setXpIcmScreenState:" + i);
        try {
            this.mPowerManager.setXpIcmScreenState(i);
        } catch (Exception e) {
            XILog.e(TAG, "setXpIcmScreenState error:" + e.getMessage());
            e.printStackTrace();
        }
    }
}
