package com.xiaopeng.instrument.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.MeterSD.MainSurfaceView;
import com.xiaopeng.MeterSD.MeterSDRender;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.UiHandler;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.InfoContainBean;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
import com.xiaopeng.instrument.manager.XpPowerManger;
import com.xiaopeng.instrument.utils.CommonUtil;
import com.xiaopeng.instrument.view.MainFragment;
import com.xiaopeng.instrument.viewmodel.InfoViewModel;
import com.xiaopeng.instrument.widget.LeftInfoViewGroup;
import com.xiaopeng.instrument.widget.RightInfoViewGroup;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
/* loaded from: classes.dex */
public class MainFragment extends BaseFragment {
    private static final String TAG = "MainFragment";
    private InfoViewModel mInfoViewModel;
    private LeftInfoViewGroup mLeftInfoViewGroup;
    OnMainFragmentListener mOnMainFragmentListener;
    private RightInfoViewGroup mRightInfoViewGroup;
    private MainSurfaceView mSurfaceView;
    private XImageView mTopMask;
    private final MainSurfaceView.NaviSRCallback mSurfaceViewCallback = new AnonymousClass1();
    private int mCurrentGearType = 0;

    /* loaded from: classes.dex */
    public interface OnMainFragmentListener {
        void onMainFragmentInit();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.instrument.view.MainFragment$1  reason: invalid class name */
    /* loaded from: classes.dex */
    public class AnonymousClass1 implements MainSurfaceView.NaviSRCallback {
        AnonymousClass1() {
        }

        @Override // com.xiaopeng.MeterSD.MainSurfaceView.NaviSRCallback
        public void onInitedOpenGL() {
            UiHandler.getInstance().post(new Runnable() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$1$EMMjeYbFbtiDwIJ1V7Pl0VptRsI
                @Override // java.lang.Runnable
                public final void run() {
                    MainFragment.AnonymousClass1.this.lambda$onInitedOpenGL$0$MainFragment$1();
                }
            });
        }

        public /* synthetic */ void lambda$onInitedOpenGL$0$MainFragment$1() {
            if (MainFragment.this.mOnMainFragmentListener != null) {
                MainFragment.this.mOnMainFragmentListener.onMainFragmentInit();
            }
        }
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        initViewModel();
        initObservers();
        initClusterInterface();
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
    }

    @Override // androidx.fragment.app.Fragment, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    protected void initView(View view) {
        MainSurfaceView mainSurfaceView = (MainSurfaceView) view.findViewById(R.id.surface_driver_layout);
        this.mSurfaceView = mainSurfaceView;
        mainSurfaceView.init(false, this.mSurfaceViewCallback);
        this.mLeftInfoViewGroup = (LeftInfoViewGroup) view.findViewById(R.id.main_left_info);
        this.mRightInfoViewGroup = (RightInfoViewGroup) view.findViewById(R.id.main_right_info);
        this.mTopMask = (XImageView) view.findViewById(R.id.top_mask);
        changeTheme();
    }

    private void initClusterInterface() {
        ClusterManager.getInstance().init(XpPowerManger.getInstance());
    }

    private void initViewModel() {
        this.mInfoViewModel = (InfoViewModel) new ViewModelProvider(this).get(InfoViewModel.class);
    }

    private void initObservers() {
        setLiveDataObserver(this.mInfoViewModel.getLeftSubCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$1Mekn_IbcyOIxsCN0enmhTZG_L4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.showLeftSubCardView(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightSubCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$Z7OsYrlcU9hjShTki_dC0dD0HqU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.showSubRightCardView(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$ID4n6gajhKuiuh72XtN1XFSzqA0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.showLeftCardView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$mdlvO-qvygmS7S0DXObTT8TmuNc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.showRightCardView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftListLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$M5jwixrQtB0qGjfRC5s0FvDHl78
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.updateLeftListData((InfoContainBean) obj);
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightListLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$7sbnMmLRQv8MuCLx8HYOWL8eJkI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.updateRightListData((InfoContainBean) obj);
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftListIndexLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$Pw_jXjx_xhHBiITcihmKJzjubpg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.updateLeftListHighPosition(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftListInfoLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$xco2maJKlngWWW6Y1x1nJ4ibimg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.showLeftListView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightListIndexLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$ZsTZJiW8AjJM7LoLDMoyW79QZ6o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.updateRightListHighPosition(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightListInfoLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$WEXaNegySTCv2k5XLifghSAzWek
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainFragment.this.showRightListView(((Boolean) obj).booleanValue());
            }
        });
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            setLiveDataObserver(this.mInfoViewModel.getGearLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainFragment$4ZY1WgStr3_S1Aa4h-Yg12P8HaM
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    MainFragment.this.changeBackground(((Integer) obj).intValue());
                }
            });
        }
    }

    protected void changeTheme() {
        boolean z = !ThemeManager.isNightMode(getContext());
        String themeStyle = CommonUtil.getThemeStyle();
        XILog.i(TAG, "is day:" + z + " theme:" + themeStyle);
        MeterSDRender.switchDayNight(z);
        MeterSDRender.changeTheme(themeStyle);
        changeBackground(this.mCurrentGearType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeBackground(int i) {
        XILog.i(TAG, "changeBackground currentType: " + this.mCurrentGearType + " type: " + i);
        if (i <= 2 && i > 0) {
            this.mTopMask.setImageResource(R.drawable.bg_cluster_horizon);
        } else {
            this.mTopMask.setImageResource(0);
        }
        this.mCurrentGearType = i;
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        MeterSDRender.onResume();
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            XILog.i(TAG, "SurfaceViewManager resumeMainMap");
            SurfaceViewManager.getInstance().resumeMainMap();
        }
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        MeterSDRender.onPause();
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
    }

    @Override // androidx.fragment.app.Fragment
    public void onHiddenChanged(boolean z) {
        super.onHiddenChanged(z);
        XILog.i(TAG, "onHiddenChanged:" + z);
        if (z) {
            MainSurfaceView mainSurfaceView = this.mSurfaceView;
            if (mainSurfaceView != null) {
                mainSurfaceView.onPause();
                return;
            }
            return;
        }
        MainSurfaceView mainSurfaceView2 = this.mSurfaceView;
        if (mainSurfaceView2 != null) {
            mainSurfaceView2.onResume();
        }
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        MainSurfaceView mainSurfaceView = this.mSurfaceView;
        if (mainSurfaceView != null) {
            mainSurfaceView.unInit();
            this.mSurfaceView = null;
        }
    }

    public void setOnMainFragmentListener(OnMainFragmentListener onMainFragmentListener) {
        this.mOnMainFragmentListener = onMainFragmentListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftListData(InfoContainBean infoContainBean) {
        if (infoContainBean == null) {
            XILog.d(TAG, "updateLeftListData infoContain bean is null");
        } else {
            this.mLeftInfoViewGroup.updateListData(infoContainBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightListData(InfoContainBean infoContainBean) {
        if (infoContainBean == null) {
            XILog.d(TAG, "updateRightListData infoContain bean is null");
        } else {
            this.mRightInfoViewGroup.updateListData(infoContainBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftListHighPosition(int i) {
        XILog.i(TAG, "updateLeftListHighPosition index:" + i);
        this.mLeftInfoViewGroup.updateListHighIndex(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightListHighPosition(int i) {
        XILog.i(TAG, "updateRightListHighPosition index:" + i);
        this.mRightInfoViewGroup.updateListHighIndex(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRightListView(boolean z) {
        this.mRightInfoViewGroup.showList(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLeftListView(boolean z) {
        this.mLeftInfoViewGroup.showList(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLeftCardView(boolean z) {
        this.mLeftInfoViewGroup.showCardView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRightCardView(boolean z) {
        this.mRightInfoViewGroup.showCardView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLeftSubCardView(int i) {
        this.mLeftInfoViewGroup.showSubCardView(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSubRightCardView(int i) {
        this.mRightInfoViewGroup.showSubCardView(i);
    }
}
