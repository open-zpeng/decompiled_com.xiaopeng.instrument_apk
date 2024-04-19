package com.xiaopeng.cluster.utils;

import android.os.Handler;
import android.os.Looper;
/* loaded from: classes.dex */
public class UiHandler extends Handler {
    private static final String TAG = "UiHandler";

    private UiHandler() {
        super(Looper.getMainLooper());
    }

    private UiHandler(Looper looper) {
        super(looper);
    }

    public static UiHandler getInstance() {
        return UiHandlerHolder.instance;
    }

    /* loaded from: classes.dex */
    private static class UiHandlerHolder {
        private static final UiHandler instance = new UiHandler();

        private UiHandlerHolder() {
        }
    }
}
