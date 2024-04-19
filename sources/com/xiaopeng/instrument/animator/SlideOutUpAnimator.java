package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class SlideOutUpAnimator extends BaseViewAnimator {
    ObjectAnimator mAlphaObjectAnimator;
    ObjectAnimator mTransObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    public void prepare(View... viewArr) {
        View view = viewArr[0];
        if (this.mAlphaObjectAnimator == null) {
            this.mAlphaObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
        }
        if (this.mTransObjectAnimator == null) {
            this.mTransObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0.0f, view.getBottom());
        }
        getAnimatorSet().playTogether(this.mAlphaObjectAnimator, this.mTransObjectAnimator);
    }
}
