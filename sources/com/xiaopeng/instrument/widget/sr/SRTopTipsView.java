package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
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
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.viewmodel.sr.SRTopTipsViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SRTopTipsView extends XRelativeLayout implements IBaseCustomView {
    private static final String TAG = "SRTopTipsView";
    private LifecycleOwner mLifecycleOwner;
    private XImageView mTopTipsImg;
    private XTextView mTopTipsText;
    private View mView;
    private SRTopTipsViewModel mViewModel;

    public SRTopTipsView(Context context) {
        this(context, null);
    }

    public SRTopTipsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SRTopTipsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_top_tips_sr, this);
        this.mView = inflate;
        this.mTopTipsImg = (XImageView) inflate.findViewById(R.id.sr_top_tips_img);
        this.mTopTipsText = (XTextView) this.mView.findViewById(R.id.sr_top_tips_text);
        initViewMode();
    }

    private void initViewMode() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (this.mViewModel == null) {
            if (getContext() instanceof ViewModelStoreOwner) {
                this.mViewModel = (SRTopTipsViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SRTopTipsViewModel.class);
            } else {
                this.mViewModel = new SRTopTipsViewModel();
            }
        }
        initObserver();
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getTopTipsData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$rCWpvqw_h0h2I5pYvQxpzwTZRls
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRTopTipsView.this.updateTopTipsInfo((NGPTipsBean) obj);
            }
        });
    }

    public void updateTopTipsInfo(NGPTipsBean nGPTipsBean) {
        if (nGPTipsBean == null) {
            return;
        }
        this.mTopTipsImg.setVisibility(8);
        if (nGPTipsBean.getImgResId() != 0) {
            this.mTopTipsImg.setImageResource(nGPTipsBean.getImgResId());
            this.mTopTipsImg.setVisibility(0);
        }
        this.mTopTipsText.setText(nGPTipsBean.getText());
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
}
