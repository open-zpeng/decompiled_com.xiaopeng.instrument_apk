package com.xiaopeng.instrument.delegate;

import com.xiaopeng.cluster.config.BaseConfig;
/* loaded from: classes.dex */
public class DelegateFactory {
    private static final String TAG = "DelegateFactory";

    public static IAppDelegate createAppDelegate() {
        if (BaseConfig.getInstance().isSupportITN()) {
            return new ITNAppDelegate();
        }
        return new DefaultAppDelegate();
    }

    public static IMainActivityDelegate createMainActivityDelegate() {
        if (BaseConfig.getInstance().isSupportITN()) {
            return new ITNMainActivityDelegate();
        }
        return new DefaultMainActivityDelegate();
    }
}
