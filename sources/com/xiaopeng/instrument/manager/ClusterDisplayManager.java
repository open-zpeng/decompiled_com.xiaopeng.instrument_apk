package com.xiaopeng.instrument.manager;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.view.MainActivity;
/* loaded from: classes.dex */
public class ClusterDisplayManager {
    private static final String CATEGORY_SECOND_HOME = "android.intent.category.SECONDARY_HOME";
    private static final int DISPLAY_MIN_LENGTH = 1;
    private static final int DISPLAY_TYPE_ICM = 6;
    private static final String TAG = "ClusterDisplayManager";
    private static DisplayManager mDisplayManager;

    private static Display getInstrumentClusterDisplay() {
        DisplayManager displayManager = mDisplayManager;
        if (displayManager == null) {
            XILog.i(TAG, "mDisplayManager is null ");
            return null;
        }
        Display[] displays = displayManager.getDisplays();
        XILog.d(TAG, "There are currently " + displays.length + " displays connected.");
        if (displays.length < 1) {
            return null;
        }
        for (Display display : displays) {
            if (display != null && display.getType() == 6) {
                return display;
            }
        }
        return null;
    }

    public static void init() {
        if (mDisplayManager == null) {
            mDisplayManager = (DisplayManager) App.getInstance().getSystemService(DisplayManager.class);
        }
    }

    public static void startClusterActivity(Context context) {
        Display instrumentClusterDisplay = getInstrumentClusterDisplay();
        if (instrumentClusterDisplay == null) {
            XILog.i(TAG, "cluster Display is null ");
            return;
        }
        XILog.i(TAG, "cluster display id is  " + instrumentClusterDisplay.getDisplayId());
        ActivityOptions makeBasic = ActivityOptions.makeBasic();
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(268435456);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory(CATEGORY_SECOND_HOME);
        makeBasic.setLaunchDisplayId(instrumentClusterDisplay.getDisplayId());
        context.startActivity(intent, makeBasic.toBundle());
    }
}
