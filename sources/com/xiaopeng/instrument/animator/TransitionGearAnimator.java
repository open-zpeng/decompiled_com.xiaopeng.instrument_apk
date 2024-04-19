package com.xiaopeng.instrument.animator;

import android.animation.ObjectAnimator;
import android.view.View;
/* loaded from: classes.dex */
public class TransitionGearAnimator extends BaseViewAnimator {
    public static final float ALPHA_MAX = 1.0f;
    public static final float ALPHA_MIN = 0.0f;
    private static final long DELAY_TIME = 1000;
    private static final long DURATION = 300;
    private static final long DURATION_SPEED = 150;
    private static final String TAG = "TransitionGearAnimator";
    private static final int VIEW_FIRST_INDEX = 0;
    private static final int VIEW_SECOND_INDEX = 1;
    private static final int VIEW_SIZE = 2;
    private ObjectAnimator mAlphaFadeInObjectAnimator;
    private ObjectAnimator mAlphaInObjectAnimator;
    private ObjectAnimator mAlphaOutObjectAnimator;

    @Override // com.xiaopeng.instrument.animator.BaseViewAnimator
    protected void prepare(View... viewArr) {
        View view = viewArr[0];
        View view2 = viewArr.length == 2 ? viewArr[1] : null;
        if (this.mAlphaInObjectAnimator == null) {
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(view, "alpha", 0.0f, 1.0f);
            this.mAlphaInObjectAnimator = ofFloat;
            ofFloat.setDuration(300L);
        }
        if (this.mAlphaOutObjectAnimator == null) {
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", 1.0f, 0.0f);
            this.mAlphaOutObjectAnimator = ofFloat2;
            ofFloat2.setStartDelay(DELAY_TIME);
            this.mAlphaOutObjectAnimator.setDuration(DURATION_SPEED);
        }
        if (view2 == null) {
            getAnimatorSet().playSequentially(this.mAlphaInObjectAnimator, this.mAlphaOutObjectAnimator);
            return;
        }
        if (this.mAlphaFadeInObjectAnimator == null) {
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(view2, "alpha", 0.0f, 1.0f);
            this.mAlphaFadeInObjectAnimator = ofFloat3;
            ofFloat3.setDuration(DURATION_SPEED);
        }
        getAnimatorSet().playSequentially(this.mAlphaInObjectAnimator, this.mAlphaOutObjectAnimator, this.mAlphaFadeInObjectAnimator);
    }
}
