package com.xiaopeng.instrument.animator;

import android.view.animation.Interpolator;
/* loaded from: classes.dex */
public class DelayLinearInterpolator implements Interpolator {
    float mActualEndInput;

    public DelayLinearInterpolator(float f, float f2) {
        float f3 = (f - f2) / f;
        this.mActualEndInput = f3;
        if (f3 <= 0.0f || f3 > 1.0f) {
            throw new IllegalArgumentException("totalTime or delayTime is invalid!");
        }
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        float f2 = f / this.mActualEndInput;
        if (f2 > 1.0f) {
            return 1.0f;
        }
        return f2;
    }
}
