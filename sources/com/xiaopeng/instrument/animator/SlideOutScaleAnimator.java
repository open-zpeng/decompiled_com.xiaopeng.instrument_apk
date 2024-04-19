package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class SlideOutScaleAnimator extends BaseViewAnimator {
    private static final String TAG = "SlideOutScaleAnimator";
    private ObjectAnimator mAlphaObjectAnimator;
    private ObjectAnimator mScaleXObjectAnimator;
    private ObjectAnimator mScaleYObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    public void prepare(View... viewArr) {
        View view = viewArr[0];
        if (this.mAlphaObjectAnimator == null) {
            this.mAlphaObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        }
        if (this.mScaleYObjectAnimator == null) {
            this.mScaleYObjectAnimator = ObjectAnimator.ofFloat(view, "scaleY", 1.0f, 0.95f);
        }
        if (this.mScaleXObjectAnimator == null) {
            this.mScaleXObjectAnimator = ObjectAnimator.ofFloat(view, "scaleX", 1.0f, 0.95f);
        }
        getAnimatorSet().playTogether(this.mAlphaObjectAnimator, this.mScaleYObjectAnimator, this.mScaleXObjectAnimator);
    }
}
