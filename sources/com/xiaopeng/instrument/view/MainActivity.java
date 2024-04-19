package com.xiaopeng.instrument.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.bean.SpaceBean;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.cluster.watchdog.ClusterWatchDog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.AccSettingBean;
import com.xiaopeng.instrument.bean.AirVolumeBean;
import com.xiaopeng.instrument.bean.CarBodyColorBean;
import com.xiaopeng.instrument.bean.FrontDistBean;
import com.xiaopeng.instrument.bean.FrontRadarBean;
import com.xiaopeng.instrument.bean.OSDConfirmBean;
import com.xiaopeng.instrument.bean.TestColorBean;
import com.xiaopeng.instrument.broadcast.SystemChangeBroadReceiver;
import com.xiaopeng.instrument.delegate.DelegateFactory;
import com.xiaopeng.instrument.delegate.IMainActivityDelegate;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import com.xiaopeng.instrument.utils.CommonUtil;
import com.xiaopeng.instrument.view.MainFragment;
import com.xiaopeng.instrument.viewmodel.AccSettingViewModel;
import com.xiaopeng.instrument.viewmodel.ControlViewModel;
import com.xiaopeng.instrument.viewmodel.DangerHaloViewModel;
import com.xiaopeng.instrument.viewmodel.FrontRadarViewModel;
import com.xiaopeng.instrument.viewmodel.IndicatorViewModel;
import com.xiaopeng.instrument.viewmodel.InstrumentViewModel;
import com.xiaopeng.instrument.viewmodel.OSDConfirmViewModel;
import com.xiaopeng.instrument.viewmodel.OsdCallViewModel;
import com.xiaopeng.instrument.viewmodel.OsdMediaVolumeViewModel;
import com.xiaopeng.instrument.viewmodel.OsdPGearProtectViewModel;
import com.xiaopeng.instrument.widget.CommonView;
import com.xiaopeng.instrument.widget.DangerHaloView;
import com.xiaopeng.instrument.widget.FragChangeAnimView;
import com.xiaopeng.instrument.widget.IndicatorView;
import com.xiaopeng.instrument.widget.LeftOsdViewGroup;
import com.xiaopeng.instrument.widget.RightOsdViewGroup;
import com.xiaopeng.xui.view.XView;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XProgressBar;
/* loaded from: classes.dex */
public class MainActivity extends BaseActivity implements MainFragment.OnMainFragmentListener {
    private static final long DURATION_TIME = 300;
    private static final long FIRST_TIME = 1500;
    public static final String TAG = "MainActivity";
    private AccSettingViewModel mAccSettingViewModel;
    private CommonView mCommonView;
    public ControlViewModel mControlViewModel;
    private DangerHaloView mDangerHaloView;
    public DangerHaloViewModel mDangerHaloViewModel;
    private FragChangeAnimView mFragChangeAnimView;
    private FragmentManager mFragmentManager;
    private FrontRadarViewModel mFrontRadarViewModel;
    private IndicatorView mIndicatorView;
    private IndicatorViewModel mIndicatorViewModel;
    private InstrumentViewModel mInstrumentViewModel;
    private boolean mIsFirstTime;
    private LeftOsdViewGroup mLeftOsdViewGroup;
    private IMainActivityDelegate mMainActivityDelegate;
    private OSDConfirmViewModel mOSDConfirmViewModel;
    private XFrameLayout mOffLayout;
    private OsdCallViewModel mOsdCallViewModel;
    private OsdMediaVolumeViewModel mOsdMediaVolumeViewModel;
    private OsdPGearProtectViewModel mOsdPGearProtectViewModel;
    private ViewGroup mOtaLayout;
    private XProgressBar mOtaProgress;
    private RightOsdViewGroup mRightOsdViewGroup;
    private XImageView mSpaceView;
    private ViewGroup mTestColorView;
    private XView mTestColorViewOne;
    private XView mTestColorViewThree;
    private XView mTestColorViewTwo;
    private String mCurrentFragmentName = "";
    private FragmentType mCurrentFragmentType = FragmentType.UNKNOWN;
    private boolean isShowCommonView = true;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.view.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ClusterWatchDog.getInstance().startMonitor();
        XILog.i(TAG, "mCurrentFragmentName: " + this.mCurrentFragmentName + " mFragmentManager: " + this.mFragmentManager);
        FragmentManager fragmentManager = this.mFragmentManager;
        if (fragmentManager != null) {
            fragmentManager.getFragments().clear();
        }
        setContentView(R.layout.activity_main);
        initDelegate();
        initView();
        initViewModel();
        initFragment(bundle);
        registerService();
    }

    private void registerService() {
        if (!CommonUtil.hasRegisterSystemReceiver()) {
            SystemChangeBroadReceiver.registerSystemChangeReceiver(getApplicationContext());
            CommonUtil.setIsHasRegisterSystemReceiver(true);
        }
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            XpuInstrumentClient.getInstance().bindXpuDataService(this);
        }
    }

    private void initDelegate() {
        this.mMainActivityDelegate = DelegateFactory.createMainActivityDelegate();
    }

    private void initFragment(Bundle bundle) {
        if (bundle == null) {
            if (BaseConfig.getInstance().isSupportNaviSR()) {
                showFragment(FragmentType.NAVI_SR);
                this.mIsFirstTime = true;
            }
            showFragment(FragmentType.MAIN);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.view.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        IndicatorViewModel indicatorViewModel = this.mIndicatorViewModel;
        if (indicatorViewModel != null) {
            indicatorViewModel.resumeData();
        }
    }

    @Override // android.app.Activity
    public void finish() {
        XILog.i(TAG, " instrument activity finish exception...");
        new Exception(" instrument activity finish exception ").printStackTrace();
    }

    @Override // android.app.Activity
    public void recreate() {
        XILog.i(TAG, " instrument activity recreate exception...");
        new Exception(" instrument activity recreate exception ").printStackTrace();
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        XILog.i(TAG, " instrument activity onBackPressed exception...");
        new Exception(" instrument activity onBackPressed exception ").printStackTrace();
    }

    private void initView() {
        this.mOffLayout = (XFrameLayout) findViewById(R.id.off_layout);
        this.mOtaLayout = (ViewGroup) findViewById(R.id.ota_layout);
        this.mOtaProgress = (XProgressBar) findViewById(R.id.ota_progress);
        this.mIndicatorView = (IndicatorView) findViewById(R.id.indicator_layout);
        this.mTestColorView = (ViewGroup) findViewById(R.id.color_test);
        this.mTestColorViewOne = (XView) findViewById(R.id.color_test_one);
        this.mTestColorViewTwo = (XView) findViewById(R.id.color_test_two);
        this.mTestColorViewThree = (XView) findViewById(R.id.color_test_three);
        this.mDangerHaloView = (DangerHaloView) findViewById(R.id.danger_halo_layout);
        this.mCommonView = (CommonView) findViewById(R.id.common_view);
        this.mFragChangeAnimView = (FragChangeAnimView) findViewById(R.id.frag_change_layout);
        this.mSpaceView = (XImageView) findViewById(R.id.space_bg);
        this.mLeftOsdViewGroup = (LeftOsdViewGroup) findViewById(R.id.main_left_osd);
        this.mRightOsdViewGroup = (RightOsdViewGroup) findViewById(R.id.main_right_osd);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.view.BaseActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        XILog.i(TAG, "onDestroy....super..." + hashCode());
        unRegisterService();
        ClusterManager.getInstance().setReloadTag(true);
        super.onDestroy();
        SurfaceViewManager.getInstance().startDestroyService();
    }

    public void unRegisterService() {
        ClusterWatchDog.getInstance().stopMonitor();
        if (CommonUtil.hasRegisterSystemReceiver()) {
            SystemChangeBroadReceiver.unRegisterSystemChangeReceiver(getApplicationContext());
            CommonUtil.setIsHasRegisterSystemReceiver(false);
        }
    }

    protected void finalize() throws Throwable {
        XILog.e(TAG, "finalize...");
        super.finalize();
    }

    @Override // androidx.fragment.app.FragmentActivity
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof MainFragment) {
            ((MainFragment) fragment).setOnMainFragmentListener(this);
        }
    }

    private void initViewModel() {
        this.mControlViewModel = (ControlViewModel) new ViewModelProvider(this).get(ControlViewModel.class);
        this.mDangerHaloViewModel = (DangerHaloViewModel) new ViewModelProvider(this).get(DangerHaloViewModel.class);
        this.mIndicatorViewModel = (IndicatorViewModel) new ViewModelProvider(this).get(IndicatorViewModel.class);
        this.mInstrumentViewModel = (InstrumentViewModel) new ViewModelProvider(this).get(InstrumentViewModel.class);
        this.mOSDConfirmViewModel = (OSDConfirmViewModel) new ViewModelProvider(this).get(OSDConfirmViewModel.class);
        this.mAccSettingViewModel = (AccSettingViewModel) new ViewModelProvider(this).get(AccSettingViewModel.class);
        this.mFrontRadarViewModel = (FrontRadarViewModel) new ViewModelProvider(this).get(FrontRadarViewModel.class);
        this.mOsdMediaVolumeViewModel = (OsdMediaVolumeViewModel) new ViewModelProvider(this).get(OsdMediaVolumeViewModel.class);
        this.mOsdPGearProtectViewModel = (OsdPGearProtectViewModel) new ViewModelProvider(this).get(OsdPGearProtectViewModel.class);
        this.mOsdCallViewModel = (OsdCallViewModel) new ViewModelProvider(this).get(OsdCallViewModel.class);
        setLiveDataObserver(this.mControlViewModel.getIndicatorControlLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$eo3d189NM2J4t1yPv6iDL1hQ9NY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showIndicatorLayout(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mControlViewModel.getChargeLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$YLko3z5p4nAPqbDtU3XvMizpj4g
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showFragment((FragmentType) obj);
            }
        });
        setLiveDataObserver(this.mControlViewModel.getOffLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$nyCLN8xiYahRfGXKi0gYv7njvrE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showOffLayout(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mControlViewModel.getTestColorLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$j4_TspOxZhE4BHKahma2kwqELJE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showTestColorLayout((TestColorBean) obj);
            }
        });
        setLiveDataObserver(this.mControlViewModel.getCommonViewLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$j5yDBq_A5V04pEDk-MEGPAjuR_s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showCommonView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mControlViewModel.getSpaceBeanMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$cN8zdqR9_vlqOhSg7z6ObsD7jfw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showSpaceView((SpaceBean) obj);
            }
        });
        setLiveDataObserver(this.mDangerHaloViewModel.getDangerHaloLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$SR4idYiRHifzd95IjHjpaHD4ncE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showDangerHaloView(((Integer) obj).intValue());
            }
        });
        initOtaViewModel();
        initOsdObserver();
    }

    private void initOtaViewModel() {
        setLiveDataObserver(this.mControlViewModel.getOtaLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$oElhTQZgjd-efUFt-vK9FQox8DI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showOtaLayout(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mControlViewModel.getOtaProgressLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$sq6wMo4k8HMeh4dgpV1n_022qYM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateOtaProgress(((Integer) obj).intValue());
            }
        });
    }

    private void initOsdObserver() {
        initInstrumentDataObserver();
        initOsdCallDataObservers();
        initOsdConfirmDataObservers();
        initOsdMediaVolumeDataObservers();
        initOsdPGearProtectDataObservers();
        setLiveDataObserver(this.mAccSettingViewModel.getAccSettingLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$yoGMenP8yNU_W9B_Ch2YkUnBazI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateAccSetting((AccSettingBean) obj);
            }
        });
        initFrontRadarDataObservers();
    }

    private void initInstrumentDataObserver() {
        setLiveDataObserver(this.mInstrumentViewModel.getAirTemperatureLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$QZ6ZUomUsmdWfSTM43FboX_6PZE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateAirTemperature((String[]) obj);
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getAirTemperatureViewLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$XpvohuLu62uSfCvLXArFQWgmzok
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showAirTemperature(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getAirVolumeLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$83P0whlGQmvP_fNJ2AqInOHSk2Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateAirVolume(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getAirVolumeViewLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$GNUEuP4L0h2a0Vo05YN6HhuMnl0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showAirVolumeView((AirVolumeBean) obj);
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getWiperTypeLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$StWlvEL4nv4yBkUrchD1Nyi9BqE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateWiperSpeed(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getWiperShowLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$Wef5NBXTuSdnxvpD_XvrjUh8Sp8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showWiperView(((Boolean) obj).booleanValue());
            }
        });
    }

    private void initOsdConfirmDataObservers() {
        setLiveDataObserver(this.mOSDConfirmViewModel.getOSDConfirmLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$2_LlmIyvRFoyElUmHepomhcOIcM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showOSDConfirmView((OSDConfirmBean) obj);
            }
        });
        setLiveDataObserver(this.mOSDConfirmViewModel.getOSDConfirmUpdateLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$X7sgcLIi8zOuRw0rca2GlFTFOgU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateOSDConfirmView((OSDConfirmBean) obj);
            }
        });
    }

    private void initOsdCallDataObservers() {
        setLiveDataObserver(this.mOsdCallViewModel.getNameLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$ALiH-WjVCHTYUKAobjFh4nKBZ3Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateCallName((String) obj);
            }
        });
        setLiveDataObserver(this.mOsdCallViewModel.getAnswerOrRejectLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$4ZWwClFc1VDRZ0N35ijYZc3keUY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.switchCallChoice(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mOsdCallViewModel.getCallingContentLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$sEqXKB35recToriZv_73IEDlnoQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateCallContent((String) obj);
            }
        });
        setLiveDataObserver(this.mOsdCallViewModel.getCallStateLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$p2uXxRPXpK1iVp9ERBA09FL2u2Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateCallView(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mOsdCallViewModel.getCallVisibleLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$bp6AaYS-teLPZgzwT3x0p_1fSaQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showCallView(((Boolean) obj).booleanValue());
            }
        });
    }

    private void initOsdMediaVolumeDataObservers() {
        MutableLiveData<Boolean> isMediaMute = this.mOsdMediaVolumeViewModel.getIsMediaMute();
        final RightOsdViewGroup rightOsdViewGroup = this.mRightOsdViewGroup;
        rightOsdViewGroup.getClass();
        setLiveDataObserver(isMediaMute, new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$WjH61kC5sSToEg3y8RDNb8u-WYs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                RightOsdViewGroup.this.setMediaMute(((Boolean) obj).booleanValue());
            }
        });
        MutableLiveData<Boolean> isMediaVolumeVisible = this.mOsdMediaVolumeViewModel.getIsMediaVolumeVisible();
        final RightOsdViewGroup rightOsdViewGroup2 = this.mRightOsdViewGroup;
        rightOsdViewGroup2.getClass();
        setLiveDataObserver(isMediaVolumeVisible, new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$7d9b_UyAGSmAnAPEN0UhmOSomCo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                RightOsdViewGroup.this.setMediaVolumeVisibility(((Boolean) obj).booleanValue());
            }
        });
        MutableLiveData<Float> mediaVolume = this.mOsdMediaVolumeViewModel.getMediaVolume();
        final RightOsdViewGroup rightOsdViewGroup3 = this.mRightOsdViewGroup;
        rightOsdViewGroup3.getClass();
        setLiveDataObserver(mediaVolume, new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$Qk-s16rjZvxniE4R8UX4FA9PyMo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                RightOsdViewGroup.this.setMediaVolume(((Float) obj).floatValue());
            }
        });
        MutableLiveData<Integer> callMode = this.mOsdMediaVolumeViewModel.getCallMode();
        final RightOsdViewGroup rightOsdViewGroup4 = this.mRightOsdViewGroup;
        rightOsdViewGroup4.getClass();
        setLiveDataObserver(callMode, new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$F8sGR7GvwyxS-nxmdrq5QWpalNg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                RightOsdViewGroup.this.setCallOrMediaMode(((Integer) obj).intValue());
            }
        });
    }

    private void initOsdPGearProtectDataObservers() {
        MutableLiveData<Integer> stateLiveData = this.mOsdPGearProtectViewModel.getStateLiveData();
        final RightOsdViewGroup rightOsdViewGroup = this.mRightOsdViewGroup;
        rightOsdViewGroup.getClass();
        setLiveDataObserver(stateLiveData, new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$-h3JZrI3HsYlTjmFGL9JkvEBlCw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                RightOsdViewGroup.this.updatePGearProtectView(((Integer) obj).intValue());
            }
        });
        MutableLiveData<String> confirmContentLiveData = this.mOsdPGearProtectViewModel.getConfirmContentLiveData();
        final RightOsdViewGroup rightOsdViewGroup2 = this.mRightOsdViewGroup;
        rightOsdViewGroup2.getClass();
        setLiveDataObserver(confirmContentLiveData, new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$bxBs3tv07qGJ59KTeHnZUaL1pbY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                RightOsdViewGroup.this.updatePGearProtectConfirmContent((String) obj);
            }
        });
    }

    private void initFrontRadarDataObservers() {
        setLiveDataObserver(this.mFrontRadarViewModel.getFrontRadar(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$z7-QOlFkY3NJgZVHzl29EhwJ7EQ
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateFrontRadarView((FrontRadarBean) obj);
            }
        });
        setLiveDataObserver(this.mFrontRadarViewModel.getFrontRadarShowLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$EroHZrG3z-DJxewJIBACRGDp2Mg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showFrontRadarView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mFrontRadarViewModel.getFrontRadarLiveDataValue(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$rmhRg9H7K__6bDU4W2JlfWnFzpI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.showFrontRadarDisValue((FrontDistBean) obj);
            }
        });
        setLiveDataObserver(this.mFrontRadarViewModel.getBodyColorBeanMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$MainActivity$O6PRlwAu-uAr-wzGfTp8t7OXmS0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                MainActivity.this.updateRadarFrontCardBodyBg((CarBodyColorBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDangerHaloView(int i) {
        if (i == 0) {
            this.mDangerHaloView.stop();
            return;
        }
        this.mDangerHaloView.setDangerType(i);
        this.mDangerHaloView.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOtaProgress(int i) {
        this.mOtaProgress.setProgress(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOtaLayout(boolean z) {
        this.mOtaLayout.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showIndicatorLayout(boolean z) {
        this.mIndicatorView.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOffLayout(boolean z) {
        XILog.d(TAG, "showOffLayout:" + z);
        this.mOffLayout.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSpaceView(SpaceBean spaceBean) {
        if (spaceBean == null) {
            XILog.e(TAG, "spaceBean is null");
            return;
        }
        int mode = spaceBean.getMode();
        if (mode == 1) {
            updateSpaceView(R.drawable.bg_cluster_movie, spaceBean.getState());
        } else if (mode == 2) {
            updateSpaceView(R.drawable.bg_cluster_sleep, spaceBean.getState());
        } else if (mode == 3) {
            updateSpaceView(R.drawable.bg_cluster_5d_cinema, spaceBean.getState());
        } else {
            XILog.e(TAG, "spaceBean mode is not exist...");
        }
    }

    private void updateSpaceView(int i, int i2) {
        if (i2 == 2) {
            this.mSpaceView.setImageResource(i);
            this.mSpaceView.setVisibility(0);
        } else if (i2 == 0) {
            this.mSpaceView.setVisibility(8);
        } else {
            Log.e(TAG, "state is not exist ");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTestColorLayout(TestColorBean testColorBean) {
        this.mTestColorViewOne.setBackgroundColor(testColorBean.getFirstId());
        this.mTestColorViewTwo.setBackgroundColor(testColorBean.getSecondId());
        this.mTestColorViewThree.setBackgroundColor(testColorBean.getThirdId());
        this.mTestColorView.setVisibility(testColorBean.isShow() ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCommonView(boolean z) {
        this.isShowCommonView = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showOSDConfirmView(OSDConfirmBean oSDConfirmBean) {
        if (oSDConfirmBean == null) {
            XILog.d(TAG, "osd confirm  bean is null");
            return;
        }
        XILog.d(TAG, "osd confirm bean:" + oSDConfirmBean.toString());
        this.mRightOsdViewGroup.showOSDConfirmView(oSDConfirmBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOSDConfirmView(OSDConfirmBean oSDConfirmBean) {
        if (oSDConfirmBean == null) {
            XILog.d(TAG, "osd confirm  bean is null");
            return;
        }
        XILog.d(TAG, "osd confirm bean:" + oSDConfirmBean.toString());
        this.mRightOsdViewGroup.updateOsdConfirmView(oSDConfirmBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAccSetting(AccSettingBean accSettingBean) {
        if (accSettingBean == null) {
            XILog.d(TAG, "acc setting bean  bean is null");
            return;
        }
        XILog.d(TAG, "acc setting bean:" + accSettingBean.toString());
        this.mLeftOsdViewGroup.updateAccSetting(accSettingBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFrontRadarView(FrontRadarBean frontRadarBean) {
        if (frontRadarBean == null) {
            XILog.d(TAG, "front radar  bean is null");
            return;
        }
        XILog.d(TAG, "front bean:" + frontRadarBean.toString());
        this.mLeftOsdViewGroup.updateRadarBean(frontRadarBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRadarFrontCardBodyBg(CarBodyColorBean carBodyColorBean) {
        this.mLeftOsdViewGroup.updateRadarFrontBody(carBodyColorBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFrontRadarView(boolean z) {
        this.mLeftOsdViewGroup.showRadarWarningView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFrontRadarDisValue(FrontDistBean frontDistBean) {
        this.mLeftOsdViewGroup.updateRadarTextValue(frontDistBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCallContent(String str) {
        this.mRightOsdViewGroup.updateCallContent(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCallView(int i) {
        this.mRightOsdViewGroup.updateCallView(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void switchCallChoice(boolean z) {
        this.mRightOsdViewGroup.switchCallChoice(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCallView(boolean z) {
        this.mRightOsdViewGroup.showOsdCallView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateCallName(String str) {
        this.mRightOsdViewGroup.updateCallName(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAirTemperature(boolean z) {
        this.mLeftOsdViewGroup.showAirTemperature(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAirTemperature(String[] strArr) {
        this.mLeftOsdViewGroup.setAirTemperature(strArr);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAirVolume(int i) {
        this.mLeftOsdViewGroup.setAirVolume(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAirVolumeView(AirVolumeBean airVolumeBean) {
        this.mLeftOsdViewGroup.showAirVolume(airVolumeBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateWiperSpeed(int i) {
        this.mLeftOsdViewGroup.setWiperSpeed(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showWiperView(boolean z) {
        this.mLeftOsdViewGroup.showWiper(z);
    }

    public void showFragment(FragmentType fragmentType) {
        IMainActivityDelegate iMainActivityDelegate = this.mMainActivityDelegate;
        if (iMainActivityDelegate != null) {
            Class fragmentClassByType = iMainActivityDelegate.getFragmentClassByType(fragmentType);
            if (fragmentClassByType != null && fragmentType != FragmentType.UNKNOWN) {
                if (fragmentType == FragmentType.NAVI_SR && this.mCurrentFragmentType != FragmentType.NAVI_SR) {
                    if (this.mIsFirstTime) {
                        this.mIsFirstTime = false;
                        this.mFragChangeAnimView.startFragmentChangeAnim(FIRST_TIME);
                        XILog.d(TAG, "navi_sr first time");
                    } else {
                        this.mFragChangeAnimView.startFragmentChangeAnim(300L);
                        XILog.d(TAG, "navi_sr other time");
                    }
                }
                this.mCurrentFragmentType = fragmentType;
                showFragmentByClass(fragmentClassByType);
                return;
            }
            XILog.e(TAG, "fragmentClass is null");
            return;
        }
        XILog.e(TAG, "mMainActivityDelegate is null");
    }

    private void showFragmentByClass(Class cls) {
        String name = cls.getName();
        String str = TAG;
        XILog.d(str, "new fragment name:" + name);
        if (this.mCurrentFragmentName.equals(name)) {
            XILog.d(str, "fragment name is equal ");
            return;
        }
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        this.mFragmentManager = supportFragmentManager;
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        BaseFragment baseFragment = (BaseFragment) this.mFragmentManager.findFragmentByTag(name);
        XILog.d(str, "fragment size is: " + this.mFragmentManager.getFragments().size());
        if (!TextUtils.isEmpty(this.mCurrentFragmentName) && !this.mCurrentFragmentName.equals(name)) {
            BaseFragment baseFragment2 = (BaseFragment) this.mFragmentManager.findFragmentByTag(this.mCurrentFragmentName);
            if (baseFragment2 != null) {
                beginTransaction.hide(baseFragment2);
                XILog.d(str, "hide " + this.mCurrentFragmentName);
            } else {
                XILog.d(str, "currentFragment is null ");
            }
        }
        if (baseFragment == null) {
            baseFragment = (BaseFragment) initFragment(cls);
            beginTransaction.add(R.id.fragment_container, baseFragment, name);
            XILog.d(str, "add " + name);
        } else {
            beginTransaction.show(baseFragment);
            XILog.d(str, "show " + baseFragment.getTag());
        }
        beginTransaction.commitNow();
        this.mCurrentFragmentName = baseFragment.getClass().getName();
        this.mCommonView.setVisibility(this.isShowCommonView ? 0 : 8);
    }

    @Override // com.xiaopeng.instrument.view.MainFragment.OnMainFragmentListener
    public void onMainFragmentInit() {
        XILog.d(TAG, "onMainFragmentInit...");
    }
}
