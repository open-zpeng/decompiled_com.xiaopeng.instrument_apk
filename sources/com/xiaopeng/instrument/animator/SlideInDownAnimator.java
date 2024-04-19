package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class SlideInDownAnimator extends BaseViewAnimator {
    private static final int DEFAULT_DISTANCE = 600;
    private static final String TAG = "SlideInDownAnimator";
    private ObjectAnimator mAlphaObjectAnimator;
    private ObjectAnimator mTransObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    public void prepare(View... viewArr) {
        View view = viewArr[0];
        int height = view.getHeight() + view.getTop();
        if (height == 0) {
            height = 600;
        }
        if (this.mAlphaObjectAnimator == null) {
            this.mAlphaObjectAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
        }
        if (this.mTransObjectAnimator == null) {
            this.mTransObjectAnimator = ObjectAnimator.ofFloat(view, "translationY", height, 0.0f);
        }
        getAnimatorSet().playTogether(this.mAlphaObjectAnimator, this.mTransObjectAnimator);
    }
}
