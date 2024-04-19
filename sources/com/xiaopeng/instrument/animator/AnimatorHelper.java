package com.xiaopeng.instrument.animator;

import android.animation.Animator;
import android.view.View;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class AnimatorHelper {
    private static final float FROM_ALPHA = 0.0f;
    private static final String TAG = "AnimatorHelper";
    private static final float TO_ALPHA = 1.0f;

    public void showAnimator(boolean z, BaseViewAnimator baseViewAnimator, BaseViewAnimator baseViewAnimator2, View view) {
        if (z) {
            doStartAnimator(baseViewAnimator2, baseViewAnimator, view);
        } else {
            doStartAnimator(baseViewAnimator, baseViewAnimator2, view);
        }
    }

    private void doStartAnimator(BaseViewAnimator baseViewAnimator, BaseViewAnimator baseViewAnimator2, final View view) {
        if (baseViewAnimator != null && baseViewAnimator.isRunning()) {
            baseViewAnimator.cancel();
        }
        if (baseViewAnimator2 == null) {
            XILog.d(TAG, "startAnimator is null ");
        } else if (baseViewAnimator2.hasListener()) {
            baseViewAnimator2.animate(view);
        } else {
            baseViewAnimator2.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.instrument.animator.AnimatorHelper.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    view.setAlpha(1.0f);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    view.setAlpha(1.0f);
                }
            }).animate(view);
        }
    }
}
