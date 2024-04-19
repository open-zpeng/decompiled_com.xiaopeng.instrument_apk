package com.xiaopeng.instrument.widget;

import android.animation.Animator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class CarGearView extends XLinearLayout {
    private static final float FROM_ALPHA = 0.0f;
    private static final String TAG = "CarGearView";
    private static final float TO_ALPHA = 1.0f;
    private final long ANIM_DURATION;
    private XTextView mDImg;
    private XTextView mNImg;
    private XTextView mPImg;
    private XTextView mRImg;

    public CarGearView(Context context) {
        this(context, null);
    }

    public CarGearView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ANIM_DURATION = 300L;
        init();
    }

    public CarGearView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ANIM_DURATION = 300L;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_car_gear, this);
        this.mPImg = (XTextView) findViewById(R.id.gear_p);
        this.mRImg = (XTextView) findViewById(R.id.gear_r);
        this.mNImg = (XTextView) findViewById(R.id.gear_n);
        this.mDImg = (XTextView) findViewById(R.id.gear_d);
    }

    public void showGear(int i) {
        if (i == 1) {
            showGearD();
        } else if (i == 2) {
            showGearN();
        } else if (i == 3) {
            showGearR();
        } else if (i == 4) {
            showGearP();
        } else {
            showGearDefault();
        }
    }

    private void showGearDefault() {
        this.mPImg.setSelected(false);
        this.mRImg.setSelected(false);
        this.mNImg.setSelected(false);
        this.mDImg.setSelected(false);
    }

    private void showGearP() {
        this.mPImg.setSelected(true);
        this.mRImg.setSelected(false);
        this.mNImg.setSelected(false);
        this.mDImg.setSelected(false);
        showAnimator(this.mPImg);
    }

    private void showGearR() {
        this.mPImg.setSelected(false);
        this.mRImg.setSelected(true);
        this.mNImg.setSelected(false);
        this.mDImg.setSelected(false);
        showAnimator(this.mRImg);
    }

    private void showGearN() {
        this.mPImg.setSelected(false);
        this.mRImg.setSelected(false);
        this.mNImg.setSelected(true);
        this.mDImg.setSelected(false);
        showAnimator(this.mNImg);
    }

    private void showGearD() {
        this.mPImg.setSelected(false);
        this.mRImg.setSelected(false);
        this.mNImg.setSelected(false);
        this.mDImg.setSelected(true);
        showAnimator(this.mDImg);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void showAnimator(final XTextView xTextView) {
        xTextView.animate().alpha(1.0f).setDuration(300L).setListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.instrument.widget.CarGearView.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                xTextView.setAlpha(0.0f);
                XILog.d(CarGearView.TAG, "onAnimationStart...");
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                XILog.d(CarGearView.TAG, "onAnimationEnd...");
                xTextView.setAlpha(1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                XILog.d(CarGearView.TAG, "onAnimationCancel...");
                xTextView.setAlpha(1.0f);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
                XILog.d(CarGearView.TAG, "onAnimationRepeat...");
            }
        }).start();
    }
}
