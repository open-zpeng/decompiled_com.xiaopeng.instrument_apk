package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface IMusicListener {
    void onMusicBarValue(int i);

    void onMusicBarVisible(boolean z);

    @Deprecated
    void onMusicImageState(boolean z);

    void onMusicName(String str);

    void onMusicPlayState(boolean z);

    void onMusicSoundState(int i);

    void onRefreshImageMusicTexture(byte[] bArr, int i, int i2, int i3);

    void onSignerName(String str);
}
