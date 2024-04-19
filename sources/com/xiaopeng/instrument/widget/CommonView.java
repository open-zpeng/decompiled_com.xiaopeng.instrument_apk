package com.xiaopeng.instrument.widget;

import android.animation.Animator;
import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.AccelerateInterpolator;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.instrument.bean.AdasCcBean;
import com.xiaopeng.instrument.bean.BatteryBean;
import com.xiaopeng.instrument.bean.CcViewBean;
import com.xiaopeng.instrument.bean.DrivingModeBean;
import com.xiaopeng.instrument.bean.TransGearAnimatorBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.utils.interpolator.BreathInterpolator;
import com.xiaopeng.instrument.viewmodel.AdasViewModel;
import com.xiaopeng.instrument.viewmodel.BottomStatusBarViewModel;
import com.xiaopeng.instrument.viewmodel.ITNAdasViewModel;
import com.xiaopeng.instrument.viewmodel.InstrumentViewModel;
import com.xiaopeng.instrument.viewmodel.SDTrafficLightViewModel;
import com.xiaopeng.instrument.viewmodel.sr.SRTopTipsViewModel;
import com.xiaopeng.instrument.viewmodel.sr.SRTrafficLightViewModel;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class CommonView extends XRelativeLayout implements IBaseCustomView {
    private static final int ANIMATOR_DURATION_DRIVING_MODE = 100;
    private static final int BREATH_ANIMATION_DURATION = 1500;
    private final String TAG;
    protected AdasViewModel mAdasViewModel;
    private BottomStatusBarView mBottomStatusBarView;
    protected BottomStatusBarViewModel mBottomStatusBarViewModel;
    private BaseViewAnimator mBreathAnimator;
    private XTextView mCarAccView;
    private XImageView mCarAlcView;
    private XImageView mCarLccView;
    private XImageView mCarNgpView;
    private XImageView mCarSasView;
    private CarSpeedView mCarSpeedView;
    private CarTransitionGearView mCarTransitionGearView;
    private XImageView mCarTsrForbidOrWarningView;
    private XTextView mCarTsrRateLimitView;
    private CcViewBean mCcViewBean;
    private BaseViewAnimator mDrivingModeAnimator;
    private XTextView mDrivingModeIv;
    private ITNAdasViewModel mITNAdasViewModel;
    private InstrumentViewModel mInstrumentViewModel;
    private LifecycleOwner mLifecycleOwner;
    private BaseViewAnimator mNGPBreathAnimator;
    private SDTrafficLightViewModel mSDTrafficLightViewModel;
    private SRTopTipsViewModel mSRTopTipsViewModel;
    private SRTrafficLightViewModel mSRTrafficLightViewModel;
    private BaseViewAnimator mTransitionGearAnimator;

    public CommonView(Context context) {
        this(context, null);
    }

    public CommonView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = getClass().getSimpleName();
        initView();
        initViewModel();
    }

    public CommonView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = getClass().getSimpleName();
        initView();
        initViewModel();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_common_view, this);
        changeTheme();
        initEURegion();
        this.mCarSpeedView = (CarSpeedView) findViewById(R.id.car_speed);
        this.mCarTransitionGearView = (CarTransitionGearView) findViewById(R.id.car_transition_gear);
        this.mDrivingModeIv = (XTextView) findViewById(R.id.car_driving_mode);
        initAdasView();
        this.mBottomStatusBarView = (BottomStatusBarView) findViewById(R.id.main_bottom_status_bar);
    }

    private void initEURegion() {
        if (BaseConfig.getInstance().isSupportITN()) {
            this.mCarAlcView = (XImageView) findViewById(R.id.car_alc);
            ITNAdasViewModel iTNAdasViewModel = (ITNAdasViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(ITNAdasViewModel.class);
            this.mITNAdasViewModel = iTNAdasViewModel;
            setLiveDataObserver(iTNAdasViewModel.getITNLccLiveData(), new $$Lambda$r67uHld0Zo1GSQ5PIfeKU20tAo(this));
            setLiveDataObserver(this.mITNAdasViewModel.getITNAlcLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$QIvpBfaLzgYYE3Y-PWnpl1JgyGk
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    CommonView.this.showAlcView(((Integer) obj).intValue());
                }
            });
        }
    }

    private void initAdasView() {
        this.mCarAccView = (XTextView) findViewById(R.id.car_acc);
        this.mCarLccView = (XImageView) findViewById(R.id.car_lcc);
        this.mCarNgpView = (XImageView) findViewById(R.id.car_ngp);
        this.mCarSasView = (XImageView) findViewById(R.id.car_srs_rate);
        this.mCarTsrForbidOrWarningView = (XImageView) findViewById(R.id.car_tsr_forbid_warning);
        this.mCarTsrRateLimitView = (XTextView) findViewById(R.id.car_tsr_rate_limit);
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        ViewModelStoreOwner viewModelStoreOwner = (ViewModelStoreOwner) getContext();
        this.mInstrumentViewModel = (InstrumentViewModel) new ViewModelProvider(viewModelStoreOwner).get(InstrumentViewModel.class);
        this.mBottomStatusBarViewModel = (BottomStatusBarViewModel) new ViewModelProvider(viewModelStoreOwner).get(BottomStatusBarViewModel.class);
        this.mAdasViewModel = (AdasViewModel) new ViewModelProvider(viewModelStoreOwner).get(AdasViewModel.class);
        this.mSDTrafficLightViewModel = (SDTrafficLightViewModel) new ViewModelProvider(viewModelStoreOwner).get(SDTrafficLightViewModel.class);
        this.mSRTrafficLightViewModel = (SRTrafficLightViewModel) new ViewModelProvider(viewModelStoreOwner).get(SRTrafficLightViewModel.class);
        this.mSRTopTipsViewModel = (SRTopTipsViewModel) new ViewModelProvider(viewModelStoreOwner).get(SRTopTipsViewModel.class);
        initInstrumentDataObserver();
        initBottomStatusBarDataObservers();
        initAdasDataObserver();
        initTrafficLightOrTipsObserver();
    }

    private void initTrafficLightOrTipsObserver() {
        setLiveDataObserver(this.mSDTrafficLightViewModel.getTrafficLightData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$T3w3UC5w_tbHb8W78DI5z36jVSI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showSDTrafficLightByType(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mSRTrafficLightViewModel.getLightViewShow(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$dZro_G2jxlnnFLDdyyKA-d95BkA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showSRTrafficLight(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mSRTopTipsViewModel.getTipsViewShow(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$--t_osHd1YVCRuXM8PyaqHVTIyw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showSRTopTips(((Boolean) obj).booleanValue());
            }
        });
    }

    private void initInstrumentDataObserver() {
        setLiveDataObserver(this.mInstrumentViewModel.getCarSpeedLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$AfD4I_8zzgIs_83s3BWquOL_4R8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.updateSpeed((String) obj);
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getCarTransitionGearLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$chDsjObRV7MMcItH6317XWXLr-A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.updateTransitionGearView((TransGearAnimatorBean) obj);
            }
        });
        setLiveDataObserver(this.mInstrumentViewModel.getDrivingModeData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$mGSbs6efUIdNK2YgRTkGpWYh888
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.updateDrivingMode(((Integer) obj).intValue());
            }
        });
    }

    protected void initBottomStatusBarDataObservers() {
        MutableLiveData<Integer> outerTemperatureData = this.mBottomStatusBarViewModel.getOuterTemperatureData();
        final BottomStatusBarView bottomStatusBarView = this.mBottomStatusBarView;
        bottomStatusBarView.getClass();
        setLiveDataObserver(outerTemperatureData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$0W5Nn6Zwz1eyMk-09_TWyuqEA8g
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.setTemperature(((Integer) obj).intValue());
            }
        });
        MutableLiveData<Integer> drivingModeData = this.mBottomStatusBarViewModel.getDrivingModeData();
        final BottomStatusBarView bottomStatusBarView2 = this.mBottomStatusBarView;
        bottomStatusBarView2.getClass();
        setLiveDataObserver(drivingModeData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$7cw2ItsufqUh9YPPkc51nRtuJj8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.setDrivingMode(((Integer) obj).intValue());
            }
        });
        MutableLiveData<Integer> barBgData = this.mBottomStatusBarViewModel.getBarBgData();
        final BottomStatusBarView bottomStatusBarView3 = this.mBottomStatusBarView;
        bottomStatusBarView3.getClass();
        setLiveDataObserver(barBgData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$L30t7YxTbMsmJ3JGMNya0WjlW3o
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.setBarBgData(((Integer) obj).intValue());
            }
        });
        MutableLiveData<Integer> batteryLifeStandardData = this.mBottomStatusBarViewModel.getBatteryLifeStandardData();
        final BottomStatusBarView bottomStatusBarView4 = this.mBottomStatusBarView;
        bottomStatusBarView4.getClass();
        setLiveDataObserver(batteryLifeStandardData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$RNwOym5IrZwrErRTcSHrMRQ1XcU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.setEnduranceStandard(((Integer) obj).intValue());
            }
        });
        MutableLiveData<BatteryBean> batteryLevelData = this.mBottomStatusBarViewModel.getBatteryLevelData();
        final BottomStatusBarView bottomStatusBarView5 = this.mBottomStatusBarView;
        bottomStatusBarView5.getClass();
        setLiveDataObserver(batteryLevelData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$JzhG1CKQWAH8qMwC0NZKayJrok0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.setBatteryLevel((BatteryBean) obj);
            }
        });
        MutableLiveData<Float> enduranceShowMileage = this.mBottomStatusBarViewModel.getEnduranceShowMileage();
        final BottomStatusBarView bottomStatusBarView6 = this.mBottomStatusBarView;
        bottomStatusBarView6.getClass();
        setLiveDataObserver(enduranceShowMileage, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$GVu3c74uOy8rUveo8R-p9ck_T28
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.setEnduranceMileage(((Float) obj).floatValue());
            }
        });
        MutableLiveData<Boolean> readyLiveData = this.mBottomStatusBarViewModel.getReadyLiveData();
        final BottomStatusBarView bottomStatusBarView7 = this.mBottomStatusBarView;
        bottomStatusBarView7.getClass();
        setLiveDataObserver(readyLiveData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$cN4YV4-RBUd-o9-tAjLNevlEh4s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.updateReady(((Boolean) obj).booleanValue());
            }
        });
        MutableLiveData<Integer> carGearLiveData = this.mBottomStatusBarViewModel.getCarGearLiveData();
        final BottomStatusBarView bottomStatusBarView8 = this.mBottomStatusBarView;
        bottomStatusBarView8.getClass();
        setLiveDataObserver(carGearLiveData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$goO08YtALua8j3P6_mItwuqIvr4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.updateGear(((Integer) obj).intValue());
            }
        });
        MutableLiveData<Integer> carGearLiveData2 = this.mBottomStatusBarViewModel.getCarGearLiveData();
        final BottomStatusBarView bottomStatusBarView9 = this.mBottomStatusBarView;
        bottomStatusBarView9.getClass();
        setLiveDataObserver(carGearLiveData2, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$goO08YtALua8j3P6_mItwuqIvr4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.updateGear(((Integer) obj).intValue());
            }
        });
        MutableLiveData<String> mutableLiveData = this.mBottomStatusBarViewModel.getmBatteryPercent();
        final BottomStatusBarView bottomStatusBarView10 = this.mBottomStatusBarView;
        bottomStatusBarView10.getClass();
        setLiveDataObserver(mutableLiveData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$F0fPo-qnR3pgFEc88Zxmi9FzWdw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.updateBatteryPercent((String) obj);
            }
        });
        MutableLiveData<Integer> mutableLiveData2 = this.mBottomStatusBarViewModel.getmRandisDisplayType();
        final BottomStatusBarView bottomStatusBarView11 = this.mBottomStatusBarView;
        bottomStatusBarView11.getClass();
        setLiveDataObserver(mutableLiveData2, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$LNWAbrrH4HWla5DNoxe6Fmq9V3M
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                BottomStatusBarView.this.updateBatteryDisplayType(((Integer) obj).intValue());
            }
        });
        if (BaseConfig.getInstance().isSupportShowTime()) {
            MutableLiveData<Integer> timePatternData = this.mBottomStatusBarViewModel.getTimePatternData();
            final BottomStatusBarView bottomStatusBarView12 = this.mBottomStatusBarView;
            bottomStatusBarView12.getClass();
            setLiveDataObserver(timePatternData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$6vXghqqBewxLx54w3ORYhPT2Uxs
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BottomStatusBarView.this.setTimeMode(((Integer) obj).intValue());
                }
            });
            MutableLiveData<Integer> timeMorningOrAfternoonData = this.mBottomStatusBarViewModel.getTimeMorningOrAfternoonData();
            final BottomStatusBarView bottomStatusBarView13 = this.mBottomStatusBarView;
            bottomStatusBarView13.getClass();
            setLiveDataObserver(timeMorningOrAfternoonData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$RysG3-bT-DlyOHmSpWBOSdYbxdI
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BottomStatusBarView.this.setTimeMorningOrAfternoon(((Integer) obj).intValue());
                }
            });
            MutableLiveData<String> timeData = this.mBottomStatusBarViewModel.getTimeData();
            final BottomStatusBarView bottomStatusBarView14 = this.mBottomStatusBarView;
            bottomStatusBarView14.getClass();
            setLiveDataObserver(timeData, new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$63ixjnhq7MmIwM2IcBqwohxG2ds
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BottomStatusBarView.this.setTime((String) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XILog.i(this.TAG, "CommonView onAttachedToWindow");
        if (this.mBottomStatusBarView == null || this.mBottomStatusBarViewModel.getmRandisDisplayType() == null || this.mBottomStatusBarViewModel.getmRandisDisplayType().getValue() == null) {
            return;
        }
        this.mBottomStatusBarView.updateBatteryDisplayType(this.mBottomStatusBarViewModel.getmRandisDisplayType().getValue().intValue());
    }

    private void initAdasDataObserver() {
        setLiveDataObserver(this.mAdasViewModel.getLccLiveData(), new $$Lambda$r67uHld0Zo1GSQ5PIfeKU20tAo(this));
        setLiveDataObserver(this.mAdasViewModel.getNgpLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$I_O255Ekrt0GTzRkLsuZAZ967Is
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showNgpView((AdasCcBean) obj);
            }
        });
        setLiveDataObserver(this.mAdasViewModel.getCcLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$-y5f14OX2tkJ2EP5XqwzldnoMos
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showCcView((CcViewBean) obj);
            }
        });
        setLiveDataObserver(this.mAdasViewModel.getSasLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$ESpYfsedsULjzcOYWPki6ivZelk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showSasView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mAdasViewModel.getHoldLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$8iXsGK4qIHT2ZdJ1wCJSdhHiKAE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showHoldView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mAdasViewModel.getTsrForbidOrWarningLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CommonView$IvSqnyF6_E72oFGDXIgmt_Ic6Oo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showTsrForbidOrWarningView(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mAdasViewModel.getTsrRateLimitLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$cmYwUOEbWyPpZxQ8EUAjBUR1ySw
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CommonView.this.showTsrRateLimitView((CcViewBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSasView(boolean z) {
        this.mCarSasView.setVisibility(z ? 0 : 8);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void updateDrivingMode(int i) {
        if (this.mDrivingModeIv != null) {
            DrivingModeBean drivingModeBean = DataConfigManager.getDrivingModeBeans().get(Integer.valueOf(i));
            if (drivingModeBean != null) {
                this.mDrivingModeIv.setText(drivingModeBean.getName());
            }
            startDrivingModeAnim();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showTsrRateLimitView(CcViewBean ccViewBean) {
        if (ccViewBean != null && ccViewBean.getResId() != 0) {
            this.mCarTsrRateLimitView.setBackground(ResourcesCompat.getDrawable(getResources(), ccViewBean.getResId(), null));
            this.mCarTsrRateLimitView.setText(ccViewBean.getSpeed());
            this.mCarTsrRateLimitView.setVisibility(0);
            return;
        }
        this.mCarTsrRateLimitView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTsrForbidOrWarningView(int i) {
        if (i != 0) {
            this.mCarTsrForbidOrWarningView.setImageResource(i);
            this.mCarTsrForbidOrWarningView.setVisibility(0);
            return;
        }
        this.mCarTsrForbidOrWarningView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showHoldView(boolean z) {
        this.mCarSpeedView.setIsShowHold(z);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showLccView(int i) {
        if (i == 8) {
            showLCCViewAnimation();
            return;
        }
        stopLCCViewAnimation();
        if (i != 0) {
            this.mCarLccView.setAlpha(1.0f);
            this.mCarLccView.setImageResource(i);
            this.mCarLccView.setVisibility(0);
            return;
        }
        this.mCarLccView.setVisibility(8);
    }

    private void showLCCViewAnimation() {
        if (this.mBreathAnimator == null) {
            BaseViewAnimator initAnimator = AnimatorType.PauseBreathAnimator.initAnimator();
            this.mBreathAnimator = initAnimator;
            initAnimator.setInterpolator(new BreathInterpolator()).setDuration(1500L).setRepeatCount(-1);
        }
        this.mCarLccView.setImageResource(R.drawable.ic_lcc_start);
        this.mCarLccView.setVisibility(0);
        this.mBreathAnimator.animate(this.mCarLccView);
    }

    private void stopLCCViewAnimation() {
        BaseViewAnimator baseViewAnimator = this.mBreathAnimator;
        if (baseViewAnimator == null || !baseViewAnimator.isRunning()) {
            return;
        }
        this.mBreathAnimator.cancel();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAlcView(int i) {
        if (i != 0) {
            this.mCarAlcView.setImageResource(i);
            this.mCarAlcView.setVisibility(0);
            return;
        }
        this.mCarAlcView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showNgpView(AdasCcBean adasCcBean) {
        if (this.mCarNgpView == null || adasCcBean == null) {
            return;
        }
        int imgResId = adasCcBean.getImgResId();
        if (adasCcBean.getId() == 4) {
            showNGPViewAnimation(imgResId);
            return;
        }
        stopNGPViewAnimation();
        if (imgResId != 0) {
            this.mCarNgpView.setAlpha(1.0f);
            this.mCarNgpView.setImageResource(imgResId);
            this.mCarNgpView.setVisibility(0);
            return;
        }
        this.mCarNgpView.setVisibility(8);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showCcView(CcViewBean ccViewBean) {
        this.mCcViewBean = ccViewBean;
        if (this.mCarAccView == null) {
            XILog.d(this.TAG, "mCarAccView  is null ");
        } else if (ccViewBean != null && ccViewBean.getResId() != 0) {
            this.mCarAccView.setBackground(ResourcesCompat.getDrawable(getResources(), ccViewBean.getResId(), null));
            showIslcColor(!ccViewBean.isCc(), ccViewBean.getId());
            this.mCarAccView.setText(ccViewBean.getId() == 3 ? "" : ccViewBean.getSpeed());
            this.mCarAccView.setVisibility(0);
        } else {
            this.mCarAccView.setVisibility(8);
        }
    }

    private void showIslcColor(boolean z, int i) {
        if (z && i == 4) {
            this.mCarAccView.setTextColor(ContextCompat.getColor(getContext(), R.color.adas_acc_islc_speed_color));
        } else {
            this.mCarAccView.setTextColor(ContextCompat.getColor(getContext(), R.color.adas_acc_normal_color));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTransitionGearView(TransGearAnimatorBean transGearAnimatorBean) {
        this.mCarTransitionGearView.setGearValue(transGearAnimatorBean.getGearValue());
        if (this.mTransitionGearAnimator == null) {
            this.mTransitionGearAnimator = AnimatorType.TransitionGear.initAnimator();
        }
        boolean isShowSpeed = transGearAnimatorBean.isShowSpeed();
        this.mCarSpeedView.setVisibility(isShowSpeed ? 0 : 8);
        if (!this.mTransitionGearAnimator.hasListener()) {
            this.mTransitionGearAnimator.addAnimatorListener(new Animator.AnimatorListener() { // from class: com.xiaopeng.instrument.widget.CommonView.1
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    CommonView.this.mCarTransitionGearView.setAlpha(0.0f);
                    CommonView.this.mCarSpeedView.setAlpha(0.0f);
                    CommonView.this.mCarTransitionGearView.setVisibility(0);
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    CommonView.this.resetCarTransitionGearAndSpeedView();
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    CommonView.this.resetCarTransitionGearAndSpeedView();
                }
            });
        }
        if (isShowSpeed) {
            this.mTransitionGearAnimator.animate(this.mCarTransitionGearView, this.mCarSpeedView);
        } else {
            this.mTransitionGearAnimator.animate(this.mCarTransitionGearView);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetCarTransitionGearAndSpeedView() {
        this.mCarTransitionGearView.setAlpha(1.0f);
        this.mCarTransitionGearView.setVisibility(8);
        this.mCarSpeedView.setAlpha(1.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateSpeed(String str) {
        this.mCarSpeedView.setSpeedValue(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSDTrafficLightByType(int i) {
        if (i > 0 && i <= 4) {
            this.mCarSpeedView.setSpeedUnitVisible(false);
            this.mCarSpeedView.setSDTrafficLightVisible(true);
        } else {
            this.mCarSpeedView.setSpeedUnitVisible(true);
            this.mCarSpeedView.setSDTrafficLightVisible(false);
        }
        this.mCarSpeedView.showSDTrafficLightByType(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSRTrafficLight(boolean z) {
        this.mCarSpeedView.showSRTrafficLightView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSRTopTips(boolean z) {
        this.mCarSpeedView.showSRTrafficEventView(z);
    }

    protected void startDrivingModeAnim() {
        if (this.mDrivingModeIv == null) {
            return;
        }
        if (this.mDrivingModeAnimator == null) {
            this.mDrivingModeAnimator = AnimatorType.SlideInNormal.initAnimator().setDuration(100L).setInterpolator(new AccelerateInterpolator());
        }
        this.mDrivingModeAnimator.animate(this.mDrivingModeIv);
    }

    private void showNGPViewAnimation(int i) {
        if (this.mNGPBreathAnimator == null) {
            BaseViewAnimator initAnimator = AnimatorType.PauseBreathAnimator.initAnimator();
            this.mNGPBreathAnimator = initAnimator;
            initAnimator.setInterpolator(new BreathInterpolator()).setDuration(1500L).setRepeatCount(-1);
        }
        this.mCarNgpView.setImageResource(i);
        this.mCarNgpView.setVisibility(0);
        this.mNGPBreathAnimator.animate(this.mCarNgpView);
    }

    private void stopNGPViewAnimation() {
        BaseViewAnimator baseViewAnimator = this.mNGPBreathAnimator;
        if (baseViewAnimator == null || !baseViewAnimator.isRunning()) {
            return;
        }
        this.mNGPBreathAnimator.cancel();
    }

    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(this.TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    protected void changeTheme() {
        showCcView(this.mCcViewBean);
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

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        BaseViewAnimator baseViewAnimator = this.mDrivingModeAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
            this.mDrivingModeAnimator = null;
        }
        BaseViewAnimator baseViewAnimator2 = this.mTransitionGearAnimator;
        if (baseViewAnimator2 != null) {
            baseViewAnimator2.destroy();
            this.mTransitionGearAnimator = null;
        }
        BaseViewAnimator baseViewAnimator3 = this.mBreathAnimator;
        if (baseViewAnimator3 != null) {
            baseViewAnimator3.destroy();
            this.mBreathAnimator = null;
        }
        BaseViewAnimator baseViewAnimator4 = this.mNGPBreathAnimator;
        if (baseViewAnimator4 != null) {
            baseViewAnimator4.destroy();
            this.mNGPBreathAnimator = null;
        }
    }
}
