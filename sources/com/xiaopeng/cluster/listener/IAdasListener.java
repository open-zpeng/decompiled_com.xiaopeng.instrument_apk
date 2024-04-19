package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IAdasListener {
    void onACCIsCC(boolean z);

    void onACCSpeed(int i);

    void onACCState(int i);

    void onAdasAEBVisible(boolean z);

    void onAdasFCWVisible(boolean z);

    void onAdasHold(boolean z);

    void onAdasLCCState(int i);

    void onBSDLeft(int i);

    void onBSDRight(int i);

    @Deprecated
    default void onISLCState(int i) {
    }

    void onLightClearanceLamp(boolean z);

    void onLightDRL(boolean z);

    void onLightDippedHeadlight(boolean z);

    void onLightFullBeamHeadlight(boolean z);

    void onLightRearFogLamp(boolean z);

    void onLightSopLamp(boolean z);

    void onNGPIndicatorState(int i);

    void onSASState(int i);

    void onTSRForbid(int i);

    void onTSRRateLimitingType(int i);

    void onTSRRateLimitingValue(int i);

    void onTSRWarning(int i);
}
