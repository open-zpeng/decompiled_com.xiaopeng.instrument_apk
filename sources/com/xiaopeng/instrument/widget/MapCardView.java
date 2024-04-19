package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceView;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.config.BaseConfig;
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
public class MapCardView extends XRelativeLayout implements IBaseCustomView {
    public static final String MANEUVER_RESOURCE_PREFIX = "navi_maneuver_ic_";
    private static int mCrossBgType;
    private static int mGearType;
    private final String TAG;
    private XImageView mIvCrossDirection;
    private XImageView mIvMap;
    private LifecycleOwner mLifecycleOwner;
    private XRelativeLayout mNavCrossBg;
    private XLinearLayout mNavTbtLayout;
    private NaviLaneInfoView mNaviLaneInfoView;
    private XImageView mNaviMask;
    private CardMapSurfaceView mSurfaceView;
    private XTextView mTvCrossDistance;
    private XTextView mTvCrossDistanceUnit;
    private XTextView mTvCrossRoadName;
    protected NaviViewModel mViewModel;

    public MapCardView(Context context, AttributeSet attributeSet) {
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

    protected int getLayout() {
        XILog.d(this.TAG, "isSRMode: " + BaseConfig.getInstance().isSupportNaviSR());
        return BaseConfig.getInstance().isSupportNaviSR() ? R.layout.info_card_navi_normal_sr : R.layout.info_card_navi;
    }

    private void initNaviSRMode() {
        this.mSurfaceView = (CardMapSurfaceView) findViewById(R.id.iv_map);
    }

    private void initNormalMode() {
        this.mIvMap = (XImageView) findViewById(R.id.iv_map);
    }

    public Surface getSurface() {
        CardMapSurfaceView cardMapSurfaceView = this.mSurfaceView;
        if (cardMapSurfaceView != null) {
            return cardMapSurfaceView.getHolder().getSurface();
        }
        return null;
    }

    public SurfaceView getSurfaceView() {
        return this.mSurfaceView;
    }

    private void initContentView() {
        LayoutInflater.from(getContext()).inflate(getLayout(), this);
        this.mNavTbtLayout = (XLinearLayout) findViewById(R.id.nav_tbt);
        this.mNavCrossBg = (XRelativeLayout) findViewById(R.id.navi_cross);
        this.mIvCrossDirection = (XImageView) findViewById(R.id.iv_cross_direction);
        this.mNaviLaneInfoView = (NaviLaneInfoView) findViewById(R.id.navi_lane_info);
        this.mTvCrossRoadName = (XTextView) findViewById(R.id.tv_cross_road_name);
        this.mTvCrossDistance = (XTextView) findViewById(R.id.tv_cross_distance);
        this.mTvCrossDistanceUnit = (XTextView) findViewById(R.id.tv_cross_distance_unit);
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            this.mNaviMask = (XImageView) findViewById(R.id.navi_mask);
            initNaviSRMode();
            return;
        }
        initNormalMode();
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (getContext() instanceof ViewModelStoreOwner) {
            this.mViewModel = (NaviViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(NaviViewModel.class);
        } else {
            this.mViewModel = new NaviViewModel();
        }
        setLiveDataObserver(this.mViewModel.getNaviManeuverImage(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$m0oIif2pbQmapI-4XBGAUnR3aT4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNavManeuver((Bitmap) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossBgResType(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$6A5R9FcCcm7dRr-tUwGEt_RVpxc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNavCrossBg(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviNormalLaneData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$l8wm8w4cqjmCUNIsuO8Kqw3Xmxk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNavNormal((int[][]) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviTollGateLaneData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$Hh3ML6bLxC2RTzcwf5nWKC_67cU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showTollGateLane((List) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossRoadName(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$7wZqHLdo8N1h-lsL1htOVuGhtfA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showCrossRoadName((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossDistance(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$eQkk-cxQM5-TnHeB6EY_312mZtw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showCrossDistance((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviCrossDistanceUnit(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$6aqwMUhESjLhwJ_QcfKWEnIRKNE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showCrossDistanceUnit((Integer) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviLaneBgLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$imaCmDlSqiKRBcdjNEp5DV5tw5M
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNaviLaneBg(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviGuidenceVisibility(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$aCm7zhGlVWaFAc_F53QhqcbhZb0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNaviCrossGuidence(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviTBtVisibility(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$iVCo3yj679oF1wQAAdH4Fq-XdSs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNaviTbt(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getNaviMapImage(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$2w0lbuzQGPuG2crHq-iKdeeOJLM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MapCardView.this.showNavMap((Bitmap) obj);
            }
        });
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            setLiveDataObserver(this.mViewModel.getGearLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$MapCardView$-WBGuQx6abFTEarjcBF3A5mw4Zc
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MapCardView.this.setGearType(((Integer) obj).intValue());
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNavMap(Bitmap bitmap) {
        XImageView xImageView = this.mIvMap;
        if (xImageView != null) {
            xImageView.setImageBitmap(bitmap);
        }
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
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            changeBackground();
        }
        XRelativeLayout xRelativeLayout = this.mNavCrossBg;
        if (xRelativeLayout == null) {
            XILog.e(this.TAG, "mNavCrossBg is null ");
        } else if (i == 0) {
            xRelativeLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.navi_bg_cross, null));
        } else if (i != 1) {
        } else {
            xRelativeLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.navi_bg_cross_lane, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setGearType(int i) {
        mGearType = i;
        changeBackground();
    }

    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void setVisibility(int i) {
        if (BaseConfig.getInstance().isSupportNaviSR() && i == 0) {
            changeBackground();
        }
        super.setVisibility(i);
    }

    private void changeBackground() {
        CardMapSurfaceView cardMapSurfaceView = this.mSurfaceView;
        if (cardMapSurfaceView == null) {
            XILog.e(this.TAG, "mSurfaceView is null ");
            return;
        }
        int i = mGearType;
        if (i > 2 || i == 0) {
            if (((Integer) cardMapSurfaceView.getTag()).intValue() == 0) {
                this.mNaviMask.setImageResource(R.drawable.p_left);
            } else {
                this.mNaviMask.setImageResource(R.drawable.p_right);
            }
        } else if (((Integer) cardMapSurfaceView.getTag()).intValue() == 0) {
            this.mNaviMask.setImageResource(R.drawable.d_left);
        } else {
            this.mNaviMask.setImageResource(R.drawable.d_right);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void setMapPos(int i) {
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            CardMapSurfaceView cardMapSurfaceView = this.mSurfaceView;
            if (cardMapSurfaceView != null) {
                cardMapSurfaceView.setTag(Integer.valueOf(i));
                return;
            }
            return;
        }
        XImageView xImageView = this.mIvMap;
        if (xImageView != null) {
            xImageView.setTag(Integer.valueOf(i));
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
