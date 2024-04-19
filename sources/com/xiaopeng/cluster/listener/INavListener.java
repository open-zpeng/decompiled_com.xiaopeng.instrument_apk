package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface INavListener {
    void onNavigationArrowID(int i);

    void onNavigationDriveSide(boolean z);

    void onNavigationGuidanceVisible(boolean z);

    void onNavigationNormalLane(int[] iArr, int[] iArr2, boolean z);

    void onNavigationToast(String str, int i, String str2);

    void onNavigationTollGateLane(int[] iArr, boolean z);

    void onRefreshImageGuidanceTexture(byte[] bArr, int i, int i2, int i3);

    void onRefreshImageNaviTexture(byte[] bArr, int i, int i2, int i3);
}
