package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IOsdCallListener {
    void onCallState(int i);

    void onCallTime(String str);

    void onCallVisible(boolean z);

    void onCallerID(String str);

    void onOverlayBTPhoneButtonState(int i);
}
