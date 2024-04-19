package com.xiaopeng.instrument.helper;

import android.graphics.Bitmap;
/* loaded from: classes.dex */
public interface IDecodeResultListener {
    void onComplete();

    void onDecodeSuccess(Bitmap bitmap);
}
