package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class PauseBreathAnimator extends BaseViewAnimator {
    private static final float ALPHA_END = 0.5f;
    private static final float ALPHA_START = 0.0f;
    private ObjectAnimator mObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    protected void prepare(View... viewArr) {
        View view = viewArr[0];
        if (this.mObjectAnimator == null) {
            this.mObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, ALPHA_END);
        }
        getAnimatorSet().playTogether(this.mObjectAnimator);
    }
}
