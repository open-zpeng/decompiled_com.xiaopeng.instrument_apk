package com.xiaopeng.cluster.config;
/* loaded from: classes.dex */
public class D55VConfig extends D55Config {
    @Override // com.xiaopeng.cluster.config.D55Config, com.xiaopeng.cluster.config.BaseConfig
    public int getSupportCharge() {
        return 2;
    }

    @Override // com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportIslc() {
        return true;
    }

    @Override // com.xiaopeng.cluster.config.D55Config, com.xiaopeng.cluster.config.BaseConfig
    public boolean isSupportNaviSR() {
        return false;
    }
}
