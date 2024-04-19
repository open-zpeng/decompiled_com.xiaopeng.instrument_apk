package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface ICardControlListener {
    void onLeftCardIndex(int i);

    void onLeftCardVisible(boolean z);

    void onLeftListIndex(int i);

    void onLeftListSensorFault(boolean z);

    void onLeftListVisible(boolean z);

    void onRightCardIndex(int i);

    void onRightCardVisible(boolean z);

    void onRightListIndex(int i);

    void onRightListVisible(boolean z);
}
