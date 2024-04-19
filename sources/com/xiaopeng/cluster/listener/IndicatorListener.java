package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IndicatorListener {
    void onCommonTelltale(int i, boolean z);

    void onTurnLeftLightControProp(boolean z);

    void onTurnRightLightControProp(boolean z);

    void onUnsetTelltale(int[] iArr);
}
