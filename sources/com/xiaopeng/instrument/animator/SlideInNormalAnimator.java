package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class SlideInNormalAnimator extends BaseViewAnimator {
    private static final String TAG = "SlideInNormalAnimator";
    private ObjectAnimator mObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    public void prepare(View... viewArr) {
        View view = viewArr[0];
        if (this.mObjectAnimator == null) {
            this.mObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        }
        getAnimatorSet().playTogether(this.mObjectAnimator);
    }
}
