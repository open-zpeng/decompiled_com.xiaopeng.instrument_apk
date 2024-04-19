package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface ICarConditionListener {
    void onDoorBL(boolean z);

    void onDoorBR(boolean z);

    void onDoorFL(boolean z);

    void onDoorFR(boolean z);

    void onHoodEngine(boolean z);

    void onHoodFastCharge(int i);

    void onHoodNormalCharge(int i);

    void onHoodTrunk(boolean z);
}
