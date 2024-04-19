package com.xiaopeng.instrument.utils.interpolator;

import android.view.animation.Interpolator;
/* loaded from: classes.dex */
public class BreathInterpolator implements Interpolator {
    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        double sin;
        if (f < 0.5f) {
            sin = Math.sin((-1.5707963267948966d) * (1.0f - (f / 0.5f)));
        } else {
            sin = Math.sin((((f - 0.5f) * 1.5707963267948966d) / 0.5f) + 3.141592653589793d);
        }
        return (float) (sin + 1.0d);
    }
}
