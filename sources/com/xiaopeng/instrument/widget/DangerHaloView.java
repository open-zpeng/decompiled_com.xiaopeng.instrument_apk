package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.LinearInterpolator;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.xui.widget.XRelativeLayout;
/* loaded from: classes.dex */
public class DangerHaloView extends XRelativeLayout {
    private static final long DURATION_TIME = 1200;
    private static final int THIRD_TYPE = 1;
    private BaseViewAnimator mHaloAnimator;
    private XRelativeLayout mLayout;

    public DangerHaloView(Context context) {
        this(context, null);
    }

    public DangerHaloView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public DangerHaloView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        this.mLayout = (XRelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_danger_halo, this).findViewById(R.id.danger_background);
        setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_third_exit_waring_edge, null));
        BaseViewAnimator initAnimator = AnimatorType.HaloAnimator.initAnimator();
        this.mHaloAnimator = initAnimator;
        initAnimator.setInterpolator(new LinearInterpolator()).setDuration(DURATION_TIME).setRepeatCount(-1).setRepeatMode(1);
    }

    public void start() {
        setVisibility(0);
        BaseViewAnimator baseViewAnimator = this.mHaloAnimator;
        if (baseViewAnimator == null || baseViewAnimator.isRunning()) {
            return;
        }
        this.mHaloAnimator.animate(this);
    }

    public void stop() {
        BaseViewAnimator baseViewAnimator = this.mHaloAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.cancel();
        }
        setVisibility(8);
    }

    public void setDangerType(int i) {
        if (i == 1) {
            XILog.d("DangerHaloView", "setDangerType: " + i);
            this.mLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_third_exit_waring_edge, null));
            return;
        }
        this.mLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_danger_halo, null));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        BaseViewAnimator baseViewAnimator = this.mHaloAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
        }
        super.onDetachedFromWindow();
    }
}
