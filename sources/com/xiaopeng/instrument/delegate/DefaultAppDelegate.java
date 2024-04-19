package com.xiaopeng.instrument.delegate;

import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.utils.WorkThreadUtil;
/* loaded from: classes.dex */
public class DefaultAppDelegate implements IAppDelegate {
    @Override // com.xiaopeng.instrument.delegate.IAppDelegate
    public void initAppConfigData() {
        WorkThreadUtil.getInstance().executeAppInitTask(new Runnable() { // from class: com.xiaopeng.instrument.delegate.-$$Lambda$CKHdgL0S75frE93qgnAR-vYGSds
            @Override // java.lang.Runnable
            public final void run() {
                DataConfigManager.initConfigData();
            }
        });
    }
}
