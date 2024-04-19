package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.utils.interpolator.BreathInterpolator;
import com.xiaopeng.instrument.viewmodel.sr.SRBottomTipsViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SRBottomTipsView extends XConstraintLayout implements IBaseCustomView {
    private static final int BREATH_ANIMATION_DURATION = 1300;
    private static final String TAG = "SRBottomTipsView";
    private XImageView mBottomTipsBorder;
    private XImageView mBottomTipsImg;
    private XTextView mBottomTipsText;
    private BaseViewAnimator mBreathAnimator;
    private LifecycleOwner mLifecycleOwner;
    private NGPTipsBean mTipsBean;
    private View mView;
    private SRBottomTipsViewModel mViewModel;

    public SRBottomTipsView(Context context) {
        this(context, null);
    }

    public SRBottomTipsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SRBottomTipsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_bottom_tips_sr, this);
        this.mView = inflate;
        this.mBottomTipsBorder = (XImageView) inflate.findViewById(R.id.sr_bottom_tips_border);
        this.mBottomTipsImg = (XImageView) this.mView.findViewById(R.id.sr_bottom_tips_img);
        this.mBottomTipsText = (XTextView) this.mView.findViewById(R.id.sr_bottom_tips_text);
        initViewMode();
    }

    private void initViewMode() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (this.mViewModel == null) {
            if (getContext() instanceof ViewModelStoreOwner) {
                this.mViewModel = (SRBottomTipsViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SRBottomTipsViewModel.class);
            } else {
                this.mViewModel = new SRBottomTipsViewModel();
            }
        }
        initObserver();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XConstraintLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            changeTheme();
        }
    }

    private void changeTheme() {
        NGPTipsBean nGPTipsBean = this.mTipsBean;
        if (nGPTipsBean == null) {
            XILog.d(TAG, "change theme waring bean is null");
        } else {
            updateAlertAnimationState(nGPTipsBean.getEmergencyLevel() == 0 || this.mTipsBean.getEmergencyLevel() == 3);
        }
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getBottomTipsData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$OTB0daEGg0qS2U7HAmfCnWyE-Cg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRBottomTipsView.this.updateBottomTipsInfo((NGPTipsBean) obj);
            }
        });
    }

    public void updateBottomTipsInfo(NGPTipsBean nGPTipsBean) {
        this.mTipsBean = nGPTipsBean;
        if (nGPTipsBean == null || nGPTipsBean.getEmergencyLevel() == 2) {
            this.mView.setVisibility(8);
            updateAlertAnimationState(false);
            return;
        }
        this.mView.setVisibility(0);
        this.mBottomTipsImg.setVisibility(8);
        if (nGPTipsBean.getImgResId() != 0) {
            this.mBottomTipsImg.setImageResource(nGPTipsBean.getImgResId());
            this.mBottomTipsImg.setVisibility(0);
        }
        this.mBottomTipsText.setText(nGPTipsBean.getText());
        changeTheme();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaopeng.instrument.widget.IBaseCustomView
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        if (lifecycleOwner == null) {
            XILog.d(TAG, "mLifecycleOwner is null");
        } else {
            liveData.observe(lifecycleOwner, observer);
        }
    }

    private void updateAlertAnimationState(boolean z) {
        if (!z) {
            BaseViewAnimator baseViewAnimator = this.mBreathAnimator;
            if (baseViewAnimator != null && baseViewAnimator.isRunning()) {
                this.mBreathAnimator.cancel();
            }
            this.mBottomTipsBorder.setVisibility(8);
            return;
        }
        if (this.mBreathAnimator == null) {
            BaseViewAnimator initAnimator = AnimatorType.BreathAnimator.initAnimator();
            this.mBreathAnimator = initAnimator;
            initAnimator.setInterpolator(new BreathInterpolator()).setDuration(1300L).setRepeatCount(-1);
        }
        this.mBottomTipsBorder.setVisibility(0);
        this.mBreathAnimator.animate(this.mBottomTipsBorder);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XConstraintLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BaseViewAnimator baseViewAnimator = this.mBreathAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
            this.mBreathAnimator = null;
        }
    }
}
