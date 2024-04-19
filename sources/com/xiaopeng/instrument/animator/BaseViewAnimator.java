package com.xiaopeng.instrument.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.core.view.ViewCompat;
import com.xiaopeng.cluster.utils.XILog;
import java.util.ArrayList;
import java.util.Iterator;
/* loaded from: classes.dex */
public abstract class BaseViewAnimator {
    public static final long DURATION = 300;
    private static final String TAG = "com.xiaopeng.instrument.animator.BaseViewAnimator";
    private static final int VIEW_SIZE = 0;
    private long mDuration = 300;
    private int mRepeatCount = 0;
    private int mRepeatMode = 1;
    private AnimatorSet mAnimatorSet = new AnimatorSet();

    protected abstract void prepare(View... viewArr);

    private BaseViewAnimator setTarget(View... viewArr) {
        reset(viewArr);
        prepare(viewArr);
        return this;
    }

    private boolean isEmptyView(View... viewArr) {
        return viewArr == null || viewArr.length == 0 || viewArr[0] == null;
    }

    public void animate(View... viewArr) {
        if (isEmptyView(viewArr)) {
            XILog.i(TAG, "target view is null");
        } else if (isRunning()) {
            XILog.i(TAG, "animate isRunning");
        } else {
            setTarget(viewArr);
            start();
        }
    }

    public void restart() {
        start();
    }

    public void reset(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                ViewCompat.setAlpha(view, 1.0f);
                ViewCompat.setScaleX(view, 1.0f);
                ViewCompat.setScaleY(view, 1.0f);
                ViewCompat.setTranslationX(view, 0.0f);
                ViewCompat.setTranslationY(view, 0.0f);
                ViewCompat.setRotation(view, 0.0f);
                ViewCompat.setRotationY(view, 0.0f);
                ViewCompat.setRotationX(view, 0.0f);
            }
        }
    }

    private void start() {
        startInternal();
    }

    private void startInternal() {
        if (this.mAnimatorSet.isRunning()) {
            return;
        }
        Iterator<Animator> it = this.mAnimatorSet.getChildAnimations().iterator();
        while (it.hasNext()) {
            Animator next = it.next();
            if (next instanceof ValueAnimator) {
                ValueAnimator valueAnimator = (ValueAnimator) next;
                valueAnimator.setRepeatCount(this.mRepeatCount);
                valueAnimator.setRepeatMode(this.mRepeatMode);
            }
        }
        this.mAnimatorSet.setDuration(this.mDuration);
        this.mAnimatorSet.start();
    }

    public BaseViewAnimator addAnimatorListener(Animator.AnimatorListener animatorListener) {
        ArrayList<Animator.AnimatorListener> listeners = this.mAnimatorSet.getListeners();
        if (listeners == null || !listeners.contains(animatorListener)) {
            this.mAnimatorSet.addListener(animatorListener);
        }
        return this;
    }

    public boolean hasListener() {
        ArrayList<Animator.AnimatorListener> listeners = this.mAnimatorSet.getListeners();
        return listeners != null && listeners.size() > 0;
    }

    public void destroy() {
        cancel();
        removeAllListener();
    }

    public void cancel() {
        this.mAnimatorSet.cancel();
    }

    public boolean isRunning() {
        return this.mAnimatorSet.isRunning();
    }

    public boolean isStarted() {
        return this.mAnimatorSet.isStarted();
    }

    public void removeAnimatorListener(Animator.AnimatorListener animatorListener) {
        this.mAnimatorSet.removeListener(animatorListener);
    }

    public void removeAllListener() {
        this.mAnimatorSet.removeAllListeners();
    }

    public long getStartDelay() {
        return this.mAnimatorSet.getStartDelay();
    }

    public BaseViewAnimator setStartDelay(long j) {
        getAnimatorSet().setStartDelay(j);
        return this;
    }

    public BaseViewAnimator setInterpolator(Interpolator interpolator) {
        this.mAnimatorSet.setInterpolator(interpolator);
        return this;
    }

    public long getDuration() {
        return this.mDuration;
    }

    public BaseViewAnimator setDuration(long j) {
        this.mDuration = j;
        return this;
    }

    public AnimatorSet getAnimatorSet() {
        return this.mAnimatorSet;
    }

    public BaseViewAnimator setRepeatCount(int i) {
        this.mRepeatCount = i;
        return this;
    }

    public BaseViewAnimator setRepeatMode(int i) {
        this.mRepeatMode = i;
        return this;
    }
}
