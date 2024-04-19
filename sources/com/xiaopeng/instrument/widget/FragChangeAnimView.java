package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
/* loaded from: classes.dex */
public class FragChangeAnimView extends XLinearLayout {
    private BaseViewAnimator mLeftAnimator;
    private XImageView mLeftImageView;
    private BaseViewAnimator mRightAnimator;
    private XImageView mRightImageView;

    public FragChangeAnimView(Context context) {
        this(context, null);
    }

    public FragChangeAnimView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public FragChangeAnimView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.layout_fragment_change, this);
        this.mLeftImageView = (XImageView) inflate.findViewById(R.id.img_left_mask);
        this.mRightImageView = (XImageView) inflate.findViewById(R.id.img_right_mask);
        this.mLeftAnimator = AnimatorType.FrgChangeLeft.initAnimator();
        this.mRightAnimator = AnimatorType.FrgChangeRight.initAnimator();
        this.mLeftAnimator.setInterpolator(new AccelerateInterpolator());
        this.mRightAnimator.setInterpolator(new AccelerateInterpolator());
    }

    public void startFragmentChangeAnim(long j) {
        BaseViewAnimator baseViewAnimator;
        setVisibility(0);
        BaseViewAnimator baseViewAnimator2 = this.mLeftAnimator;
        if (baseViewAnimator2 == null || baseViewAnimator2.isRunning() || (baseViewAnimator = this.mRightAnimator) == null || baseViewAnimator.isRunning() || this.mLeftImageView == null || this.mRightImageView == null) {
            return;
        }
        this.mLeftAnimator.setDuration(j);
        this.mRightAnimator.setDuration(j);
        this.mLeftAnimator.animate(this.mLeftImageView);
        this.mRightAnimator.animate(this.mRightImageView);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        BaseViewAnimator baseViewAnimator = this.mLeftAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
            this.mLeftAnimator = null;
        }
        BaseViewAnimator baseViewAnimator2 = this.mRightAnimator;
        if (baseViewAnimator2 != null) {
            baseViewAnimator2.destroy();
            this.mRightAnimator = null;
        }
        super.onDetachedFromWindow();
    }
}
