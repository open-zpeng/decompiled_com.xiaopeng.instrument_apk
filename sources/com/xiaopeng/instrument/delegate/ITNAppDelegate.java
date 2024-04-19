package com.xiaopeng.instrument.delegate;

import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.manager.ITNDataConfigManager;
import com.xiaopeng.instrument.utils.WorkThreadUtil;
/* loaded from: classes.dex */
public class ITNAppDelegate implements IAppDelegate {
    @Override // com.xiaopeng.instrument.delegate.IAppDelegate
    public void initAppConfigData() {
        WorkThreadUtil.getInstance().executeAppInitTask(new Runnable() { // from class: com.xiaopeng.instrument.delegate.-$$Lambda$ITNAppDelegate$DCTWITfFNuYWR9zv8oM4SNQuBbA
            @Override // java.lang.Runnable
            public final void run() {
                ITNAppDelegate.lambda$initAppConfigData$0();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$initAppConfigData$0() {
        DataConfigManager.initConfigData();
        ITNDataConfigManager.initConfigData();
    }
}
