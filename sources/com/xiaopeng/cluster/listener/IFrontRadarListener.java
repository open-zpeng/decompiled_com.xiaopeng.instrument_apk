package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IFrontRadarListener {
    void onBehindDistValue(String str);

    void onFrontCLColor(int i);

    void onFrontCLDist(int i);

    void onFrontCRColor(int i);

    void onFrontCRDist(int i);

    void onFrontDistValue(String str);

    void onFrontOLColor(int i);

    void onFrontOLDist(int i);

    void onFrontORColor(int i);

    void onFrontORDist(int i);

    void onFrontSLColor(int i);

    void onFrontSLDist(int i);

    void onFrontSRColor(int i);

    void onFrontSRDist(int i);

    void onLowSpeedAlarm(boolean z);
}
