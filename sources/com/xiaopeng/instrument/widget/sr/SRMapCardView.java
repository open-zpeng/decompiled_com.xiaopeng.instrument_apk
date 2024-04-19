package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.utils.DistanceUtil;
import com.xiaopeng.instrument.viewmodel.sr.SRNaviViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SRMapCardView extends XRelativeLayout implements IBaseCustomView {
    public static final String TAG = "SRMapCardView";
    private XImageView mIvCrossDirection;
    private LifecycleOwner mLifecycleOwner;
    private XRelativeLayout mNavCrossBg;
    private XLinearLayout mNavTbtLayout;
    private XImageView mShadowImg;
    private XTextView mTvCrossDistance;
    private XTextView mTvCrossDistanceUnit;
    private XTextView mTvCrossRoadName;
    protected SRNaviViewModel mViewModel;

    private void changeTheme() {
    }

    public SRMapCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
        initViewModel();
        initObserver();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.info_card_navi_fragment_sr, this);
        this.mNavTbtLayout = (XLinearLayout) findViewById(R.id.nav_tbt);
        this.mNavCrossBg = (XRelativeLayout) findViewById(R.id.navi_cross);
        this.mIvCrossDirection = (XImageView) findViewById(R.id.iv_cross_direction);
        this.mTvCrossRoadName = (XTextView) findViewById(R.id.tv_cross_road_name);
        this.mTvCrossDistance = (XTextView) findViewById(R.id.tv_cross_distance);
        this.mTvCrossDistanceUnit = (XTextView) findViewById(R.id.tv_cross_distance_unit);
        this.mShadowImg = (XImageView) findViewById(R.id.bg_card_shadow);
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (getContext() instanceof ViewModelStoreOwner) {
            this.mViewModel = (SRNaviViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SRNaviViewModel.class);
        } else {
            this.mViewModel = new SRNaviViewModel();
        }
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getNaviManeuverImage(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMapCardView$sYAqN44qZolvo2mNVgM_9d64jxw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMapCardView.this.showNavManeuver((Bitmap) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossRoadName(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMapCardView$yls0GhR_eq4Jb1wQFvq3eINn1ps
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMapCardView.this.showCrossRoadName((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossDistance(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMapCardView$c0Oqh-2CdLCN2Ytia8T4W_zmJvs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMapCardView.this.showCrossDistance((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossDistanceUnit(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMapCardView$GqErrc873Bk5ux9MwVpTp0QzZc0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMapCardView.this.showCrossDistanceUnit((Integer) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviGuidenceVisibility(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMapCardView$qZbKf6r8zxEQL52uzZLYzOEGo9I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMapCardView.this.showNaviCrossGuidence(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviTBtVisibility(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMapCardView$Zg-97T9skxEFrOwv8eOSTaaxTZc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMapCardView.this.showNaviTbt(((Boolean) obj).booleanValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNaviTbt(boolean z) {
        this.mNavTbtLayout.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNaviCrossGuidence(boolean z) {
        this.mNavCrossBg.setVisibility(z ? 0 : 4);
        this.mShadowImg.setVisibility(z ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCrossDistanceUnit(Integer num) {
        this.mTvCrossDistanceUnit.setText(DistanceUtil.unitTypeToName(num.intValue()));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCrossDistance(String str) {
        this.mTvCrossDistance.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCrossRoadName(String str) {
        this.mTvCrossRoadName.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNavManeuver(Bitmap bitmap) {
        XImageView xImageView = this.mIvCrossDirection;
        if (xImageView != null) {
            xImageView.setImageBitmap(bitmap);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
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
