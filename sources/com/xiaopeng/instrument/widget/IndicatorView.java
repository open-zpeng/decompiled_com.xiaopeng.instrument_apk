package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.IndicatorBean;
import com.xiaopeng.instrument.bean.IndicatorContainBean;
import com.xiaopeng.instrument.bean.IndicatorViewBean;
import com.xiaopeng.instrument.viewmodel.IndicatorViewModel;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/* loaded from: classes.dex */
public class IndicatorView extends XFrameLayout implements IBaseCustomView {
    private static final int LOCATION_END_BOTTOM = 3;
    private static final int LOCATION_END_TOP = 1;
    private static final int LOCATION_START_BOTTOM = 2;
    private static final int LOCATION_START_TOP = 0;
    public static final String TAG = "IndicatorView";
    private Context mContext;
    private XRelativeLayout mIndicatorRootView;
    private LifecycleOwner mLifecycleOwner;
    private IndicatorViewModel mViewModel;
    private Map<Integer, XImageView> mXImageViewMap;

    public IndicatorView(Context context) {
        this(context, null);
    }

    public IndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mXImageViewMap = new HashMap();
        init(context);
    }

    public IndicatorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mXImageViewMap = new HashMap();
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XILog.d(TAG, "onAttachedToWindow:");
        IndicatorViewModel indicatorViewModel = this.mViewModel;
        if (indicatorViewModel != null) {
            indicatorViewModel.resumeData();
        }
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.layout_indicator, this);
        this.mIndicatorRootView = (XRelativeLayout) findViewById(R.id.indicator_root_view);
        initViewModel();
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        IndicatorViewModel indicatorViewModel = (IndicatorViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(IndicatorViewModel.class);
        this.mViewModel = indicatorViewModel;
        setLiveDataObserver(indicatorViewModel.getAllLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$IndicatorView$aws705dtYfjA8Je5xELBfFkcU6E
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                IndicatorView.this.initIndicatorView((List) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getCurrentLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$IndicatorView$ly-qtnMiyedbWd5WYtOkSBaVJhY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                IndicatorView.this.showIndicatorView((IndicatorContainBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getCacheDataLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$IndicatorView$m8da-dcLwp_vwq84ayZG1eN9EOI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                IndicatorView.this.showCacheData((Map) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.mXImageViewMap.clear();
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showIndicatorView(IndicatorContainBean indicatorContainBean) {
        if (indicatorContainBean == null || indicatorContainBean.getIndicatorBean() == null || this.mXImageViewMap == null) {
            XILog.d(TAG, "indicatorBean is null or all image views is null ");
            return;
        }
        IndicatorBean indicatorBean = indicatorContainBean.getIndicatorBean();
        boolean isVisible = indicatorContainBean.isVisible();
        XImageView xImageView = this.mXImageViewMap.get(Integer.valueOf(indicatorBean.getPos()));
        if (xImageView != null) {
            showOrHideIndicator(xImageView, indicatorBean, isVisible, indicatorContainBean.getText());
        } else {
            XILog.d(TAG, "map xImageView is null ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCacheData(Map<Integer, IndicatorContainBean> map) {
        for (Map.Entry<Integer, IndicatorContainBean> entry : map.entrySet()) {
            IndicatorContainBean value = entry.getValue();
            if (value == null) {
                XILog.d(TAG, "cache  indicatorContainBean is null");
            } else {
                showIndicatorView(value);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b2, code lost:
        if (r10 != null) goto L39;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b4, code lost:
        r10.setBitmap(null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00df, code lost:
        if (r10 == null) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e2, code lost:
        r7.setImageBitmap(r0);
        r7.setVisibility(0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:?, code lost:
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void showOrHideIndicator(com.xiaopeng.xui.widget.XImageView r7, com.xiaopeng.instrument.bean.IndicatorBean r8, boolean r9, java.lang.String r10) {
        /*
            Method dump skipped, instructions count: 247
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.instrument.widget.IndicatorView.showOrHideIndicator(com.xiaopeng.xui.widget.XImageView, com.xiaopeng.instrument.bean.IndicatorBean, boolean, java.lang.String):void");
    }

    private void showSingleIndicatorView(boolean z, int i, XImageView xImageView) {
        if (z) {
            if (i != 0) {
                xImageView.setImageResource(i);
            }
            xImageView.setVisibility(0);
            return;
        }
        xImageView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initIndicatorView(List<IndicatorViewBean> list) {
        if (list == null || list.size() == 0) {
            XILog.d(TAG, "indicatorViewBeans is null or size is 0");
            return;
        }
        for (IndicatorViewBean indicatorViewBean : list) {
            if (indicatorViewBean == null) {
                XILog.d(TAG, "current indicatorViewBean is null ");
            } else {
                XILog.d(TAG, "indicatorViewBean:" + indicatorViewBean.toString());
                XImageView xImageView = new XImageView(this.mContext);
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
                int type = indicatorViewBean.getType();
                if (type == 0) {
                    layoutParams.addRule(20);
                    layoutParams.addRule(10);
                } else if (type == 1) {
                    layoutParams.addRule(21);
                    layoutParams.addRule(10);
                } else if (type == 2) {
                    layoutParams.addRule(20);
                    layoutParams.addRule(12);
                } else if (type == 3) {
                    layoutParams.addRule(21);
                    layoutParams.addRule(12);
                }
                layoutParams.setMargins(indicatorViewBean.getLeft(), indicatorViewBean.getTop(), indicatorViewBean.getRight(), indicatorViewBean.getBottom());
                xImageView.setLayoutParams(layoutParams);
                this.mIndicatorRootView.addView(xImageView);
                this.mXImageViewMap.put(Integer.valueOf(indicatorViewBean.getPos()), xImageView);
            }
        }
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
