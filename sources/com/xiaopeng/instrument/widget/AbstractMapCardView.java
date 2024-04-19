package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.utils.DistanceUtil;
import com.xiaopeng.instrument.viewmodel.NaviViewModel;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.List;
/* loaded from: classes.dex */
public abstract class AbstractMapCardView extends XRelativeLayout implements IBaseCustomView {
    public static final String MANEUVER_RESOURCE_PREFIX = "navi_maneuver_ic_";
    private static int mCrossBgType;
    private final String TAG;
    private XImageView mIvCrossDirection;
    private LifecycleOwner mLifecycleOwner;
    private XRelativeLayout mNavCrossBg;
    private XLinearLayout mNavTbtLayout;
    private NaviLaneInfoView mNaviLaneInfoView;
    private XTextView mTvCrossDistance;
    private XTextView mTvCrossDistanceUnit;
    private XTextView mTvCrossRoadName;
    protected NaviViewModel mViewModel;

    protected abstract void addObserver();

    protected abstract void addView();

    protected abstract int getLayout();

    protected abstract void setMapPos(int i);

    public AbstractMapCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = getClass().getSimpleName();
        initContentView();
        initViewModel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNavCrossBg(int i) {
        mCrossBgType = i;
        changeTheme(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNavManeuver(Bitmap bitmap) {
        XImageView xImageView = this.mIvCrossDirection;
        if (xImageView != null) {
            xImageView.setImageBitmap(bitmap);
        }
    }

    protected void initContentView() {
        LayoutInflater.from(getContext()).inflate(getLayout(), this);
        this.mNavTbtLayout = (XLinearLayout) findViewById(R.id.nav_tbt);
        this.mNavCrossBg = (XRelativeLayout) findViewById(R.id.navi_cross);
        this.mIvCrossDirection = (XImageView) findViewById(R.id.iv_cross_direction);
        this.mNaviLaneInfoView = (NaviLaneInfoView) findViewById(R.id.navi_lane_info);
        this.mTvCrossRoadName = (XTextView) findViewById(R.id.tv_cross_road_name);
        this.mTvCrossDistance = (XTextView) findViewById(R.id.tv_cross_distance);
        this.mTvCrossDistanceUnit = (XTextView) findViewById(R.id.tv_cross_distance_unit);
        addView();
    }

    protected void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (getContext() instanceof ViewModelStoreOwner) {
            this.mViewModel = (NaviViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(NaviViewModel.class);
        } else {
            this.mViewModel = new NaviViewModel();
        }
        setLiveDataObserver(this.mViewModel.getNaviManeuverImage(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$8WI-egrg2CvXzTfJKHICPTuAQYc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showNavManeuver((Bitmap) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossBgResType(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$9ZA24N8ZeovVckmuByi1rXIrOLs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showNavCrossBg(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviNormalLaneData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$DSAQttyVdGBHKf8k9kp7WxH3a8Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showNavNormal((int[][]) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviTollGateLaneData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$ZKsM1WpaNzONfWjgFBMSnuXqp-c
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showTollGateLane((List) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossRoadName(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$zrKKUan_DtFd-ErVQDiKa8qiFpo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showCrossRoadName((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossDistance(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$8k5A4gnHu66kDJKsvHn1dEbSBUk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showCrossDistance((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossDistanceUnit(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$60cKwYgZg4QRq1RotRrKejfBaCA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showCrossDistanceUnit((Integer) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviLaneBgLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$Vd47ACNqJ61m2Zw53lK6ZXeLHj8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showNaviLaneBg(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviGuidenceVisibility(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$e5doKOipdOsYSv_h6LnhawHZ0b4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showNaviCrossGuidence(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviTBtVisibility(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$AbstractMapCardView$kz4QLVVAExcEYFKvpZ3_NgIiiEk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                AbstractMapCardView.this.showNaviTbt(((Boolean) obj).booleanValue());
            }
        });
        addObserver();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNaviTbt(boolean z) {
        this.mNavTbtLayout.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNaviCrossGuidence(boolean z) {
        this.mNavCrossBg.setVisibility(z ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNaviLaneBg(boolean z) {
        this.mNaviLaneInfoView.updateLaneBg(z);
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
    public void showTollGateLane(List<Integer> list) {
        this.mNaviLaneInfoView.updateTollGateData(list);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNavNormal(int[][] iArr) {
        this.mNaviLaneInfoView.updateNormalLaneData(iArr);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(this.TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme(mCrossBgType);
        }
    }

    private void changeTheme(int i) {
        XRelativeLayout xRelativeLayout = this.mNavCrossBg;
        if (xRelativeLayout != null) {
            if (i == 0) {
                xRelativeLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.navi_bg_cross, null));
            } else if (i != 1) {
            } else {
                xRelativeLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.navi_bg_cross_lane, null));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaopeng.instrument.widget.IBaseCustomView
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        if (lifecycleOwner == null) {
            XILog.d(this.TAG, "mLifecycleOwner is null");
        } else {
            liveData.observe(lifecycleOwner, observer);
        }
    }
}
