package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class BreathAnimator extends BaseViewAnimator {
    private static final float ALPHA_END = 1.0f;
    private static final float ALPHA_START = 0.3f;
    private ObjectAnimator mObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    protected void prepare(View... viewArr) {
        View view = viewArr[0];
        if (this.mObjectAnimator == null) {
            this.mObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", ALPHA_START, 1.0f);
        }
        getAnimatorSet().playTogether(this.mObjectAnimator);
    }
}
