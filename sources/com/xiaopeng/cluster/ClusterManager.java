package com.xiaopeng.cluster;

import com.xiaopeng.cluster.backpressure.JniKey;
import com.xiaopeng.cluster.backpressure.ProcessorFactory;
import com.xiaopeng.cluster.bean.CommonTelltaleData;
import com.xiaopeng.cluster.bean.NavGateData;
import com.xiaopeng.cluster.bean.NavLaneData;
import com.xiaopeng.cluster.bean.NavToastData;
import com.xiaopeng.cluster.bean.SpaceBean;
import com.xiaopeng.cluster.bean.TextureImageData;
import com.xiaopeng.cluster.bean.UnsetTelltaleData;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.listener.IAccSettingListener;
import com.xiaopeng.cluster.listener.IAdasListener;
import com.xiaopeng.cluster.listener.ICarConditionListener;
import com.xiaopeng.cluster.listener.ICarInfoTireListener;
import com.xiaopeng.cluster.listener.ICardControlListener;
import com.xiaopeng.cluster.listener.IChargeListener;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.IControlListener;
import com.xiaopeng.cluster.listener.IFrontRadarListener;
import com.xiaopeng.cluster.listener.IITNAdasListener;
import com.xiaopeng.cluster.listener.IMusicListener;
import com.xiaopeng.cluster.listener.INavListener;
import com.xiaopeng.cluster.listener.INaviSRListener;
import com.xiaopeng.cluster.listener.INormalInfoListener;
import com.xiaopeng.cluster.listener.IOSDConfirmListener;
import com.xiaopeng.cluster.listener.IOTAListener;
import com.xiaopeng.cluster.listener.IOdoMeterListener;
import com.xiaopeng.cluster.listener.IOsdCallListener;
import com.xiaopeng.cluster.listener.IOsdMediaVolumeListener;
import com.xiaopeng.cluster.listener.IOsdWiperListener;
import com.xiaopeng.cluster.listener.IPGearProtectListener;
import com.xiaopeng.cluster.listener.IPowerConsumptionListener;
import com.xiaopeng.cluster.listener.ISensorFaultListener;
import com.xiaopeng.cluster.listener.IndicatorListener;
import com.xiaopeng.cluster.listener.InstrumentListener;
import com.xiaopeng.cluster.utils.CommonUtil;
import com.xiaopeng.cluster.utils.XILog;
import io.reactivex.rxjava3.functions.Consumer;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class ClusterManager implements IClusterService {
    private static final String TAG = "ClusterManager";
    protected IAccSettingListener mAccSettingListener;
    protected IAdasListener mAdasListener;
    protected IOsdCallListener mCallListener;
    protected final Set<ICarConditionListener> mCarConditionListeners;
    protected ICarInfoTireListener mCarInfoTireListener;
    protected final Set<ICommonListener> mCommonListeners;
    protected IFrontRadarListener mFrontRadarListener;
    protected final Set<ICardControlListener> mICardControlListeners;
    protected IChargeListener mIChargeListener;
    protected IControlListener mIControlListener;
    protected final Set<IMusicListener> mIMusicListeners;
    protected final Set<INavListener> mINavListeners;
    protected IOTAListener mIOTAListener;
    protected final Set<IOdoMeterListener> mIOdoMeterListeners;
    protected final Set<IPowerConsumptionListener> mIPowerConsumptionListeners;
    protected final Set<ISensorFaultListener> mISensorFaultListeners;
    protected IITNAdasListener mITNAdasListener;
    protected IXpPowerManger mIXpPowerManger;
    protected IndicatorListener mIndicatorListener;
    protected InstrumentListener mInstrumentListener;
    private TextureImageData mMusicTextureData;
    private NavGateData mNavGateData;
    private TextureImageData mNavGuidanceData;
    private NavLaneData mNavLaneData;
    private TextureImageData mNavTextureData;
    private NavToastData mNavToastData;
    protected final Set<INaviSRListener> mNaviSRListeners;
    protected INormalInfoListener mNormalInfoListener;
    protected IOSDConfirmListener mOSDConfirmListener;
    private Object mObject;
    protected IOsdMediaVolumeListener mOsdMediaVolumeListener;
    protected IOsdWiperListener mOsdWiperListener;
    protected IPGearProtectListener mPGearProtectListener;
    private boolean mReload;
    private UnsetTelltaleData mUnsetTelltaleData;

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAdasAEBVisible(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAdasFCWVisible(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onBSDLeft(int i) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onBSDRight(int i) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLightClearanceLamp(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLightDRL(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLightDippedHeadlight(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLightFullBeamHeadlight(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLightRearFogLamp(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLightSopLamp(boolean z) {
    }

    @Override // com.xiaopeng.cluster.IClusterService
    @Deprecated
    public void onProgressBar(int i) {
    }

    private ClusterManager() {
        this.mCommonListeners = new LinkedHashSet();
        this.mINavListeners = new LinkedHashSet();
        this.mNaviSRListeners = new LinkedHashSet();
        this.mICardControlListeners = new LinkedHashSet();
        this.mCarConditionListeners = new LinkedHashSet();
        this.mIOdoMeterListeners = new LinkedHashSet();
        this.mIMusicListeners = new LinkedHashSet();
        this.mISensorFaultListeners = new LinkedHashSet();
        this.mIPowerConsumptionListeners = new LinkedHashSet();
        this.mNavTextureData = new TextureImageData();
        this.mMusicTextureData = new TextureImageData();
        this.mNavGuidanceData = new TextureImageData();
        this.mNavGateData = new NavGateData();
        this.mNavToastData = new NavToastData();
        this.mNavLaneData = new NavLaneData();
        this.mUnsetTelltaleData = new UnsetTelltaleData();
        this.mObject = new Object();
    }

    public static ClusterManager getInstance() {
        return ClusterManagerHolder.instance;
    }

    public void init(IXpPowerManger iXpPowerManger) {
        String str = TAG;
        XILog.d(str, "AbstractClusterManager init");
        this.mIXpPowerManger = iXpPowerManger;
        subscribeProcessor();
        JniInterface.setClusterService(this);
        if (this.mReload) {
            XILog.d(str, "AbstractClusterManager reload");
            JniInterface.reloadData();
        }
    }

    public void refreshData() {
        try {
            JniInterface.refreshData();
        } catch (Throwable unused) {
            XILog.e(TAG, "JniInterface.refreshData 调用出错");
        }
    }

    public void setReloadTag(boolean z) {
        this.mReload = z;
    }

    public void setIITNAdasListener(IITNAdasListener iITNAdasListener) {
        this.mITNAdasListener = iITNAdasListener;
    }

    public void setCallListener(IOsdCallListener iOsdCallListener) {
        this.mCallListener = iOsdCallListener;
    }

    public void setIAdasListener(IAdasListener iAdasListener) {
        this.mAdasListener = iAdasListener;
    }

    public void setIndicatorListener(IndicatorListener indicatorListener) {
        this.mIndicatorListener = indicatorListener;
    }

    public void setOsdWiperListener(IOsdWiperListener iOsdWiperListener) {
        this.mOsdWiperListener = iOsdWiperListener;
    }

    public void addCommonListener(ICommonListener iCommonListener) {
        this.mCommonListeners.add(iCommonListener);
    }

    public void removeCommonListener(ICommonListener iCommonListener) {
        this.mCommonListeners.remove(iCommonListener);
    }

    public void setOsdMediaVolumeListener(IOsdMediaVolumeListener iOsdMediaVolumeListener) {
        this.mOsdMediaVolumeListener = iOsdMediaVolumeListener;
    }

    public void addCarConditionListener(ICarConditionListener iCarConditionListener) {
        this.mCarConditionListeners.add(iCarConditionListener);
    }

    public void removeCarConditionListener(ICarConditionListener iCarConditionListener) {
        this.mCarConditionListeners.remove(iCarConditionListener);
    }

    public void setNormalInfoListener(INormalInfoListener iNormalInfoListener) {
        this.mNormalInfoListener = iNormalInfoListener;
    }

    public void addCardControlListener(ICardControlListener iCardControlListener) {
        this.mICardControlListeners.add(iCardControlListener);
    }

    public void removeCardControlListener(ICardControlListener iCardControlListener) {
        this.mICardControlListeners.remove(iCardControlListener);
    }

    public void addIMusicListener(IMusicListener iMusicListener) {
        this.mIMusicListeners.add(iMusicListener);
    }

    public void removeIMusicListener(IMusicListener iMusicListener) {
        this.mIMusicListeners.remove(iMusicListener);
    }

    public void setCarTireListener(ICarInfoTireListener iCarInfoTireListener) {
        this.mCarInfoTireListener = iCarInfoTireListener;
    }

    public void setChargeListener(IChargeListener iChargeListener) {
        this.mIChargeListener = iChargeListener;
    }

    public void addINaviSRListener(INaviSRListener iNaviSRListener) {
        this.mNaviSRListeners.add(iNaviSRListener);
    }

    public void removeINaviSRListener(INaviSRListener iNaviSRListener) {
        this.mNaviSRListeners.remove(iNaviSRListener);
    }

    public void addNavListener(INavListener iNavListener) {
        this.mINavListeners.add(iNavListener);
    }

    public void removeNavListener(INavListener iNavListener) {
        this.mINavListeners.remove(iNavListener);
    }

    public void addOdoMeterListener(IOdoMeterListener iOdoMeterListener) {
        this.mIOdoMeterListeners.add(iOdoMeterListener);
    }

    public void removeOdoMeterListener(IOdoMeterListener iOdoMeterListener) {
        this.mIOdoMeterListeners.remove(iOdoMeterListener);
    }

    public void setOtaListener(IOTAListener iOTAListener) {
        this.mIOTAListener = iOTAListener;
    }

    public void setInstrumentListener(InstrumentListener instrumentListener) {
        this.mInstrumentListener = instrumentListener;
    }

    public void addSensorFaultListener(ISensorFaultListener iSensorFaultListener) {
        this.mISensorFaultListeners.add(iSensorFaultListener);
    }

    public void removeSensorFaultListener(ISensorFaultListener iSensorFaultListener) {
        this.mISensorFaultListeners.remove(iSensorFaultListener);
    }

    public void setControlListener(IControlListener iControlListener) {
        this.mIControlListener = iControlListener;
    }

    public void addPowerConsumptionListener(IPowerConsumptionListener iPowerConsumptionListener) {
        this.mIPowerConsumptionListeners.add(iPowerConsumptionListener);
    }

    public void removePowerConsumptionListener(IPowerConsumptionListener iPowerConsumptionListener) {
        this.mIPowerConsumptionListeners.remove(iPowerConsumptionListener);
    }

    public void setOSDConfirmListener(IOSDConfirmListener iOSDConfirmListener) {
        this.mOSDConfirmListener = iOSDConfirmListener;
    }

    public void setAccSettingListener(IAccSettingListener iAccSettingListener) {
        this.mAccSettingListener = iAccSettingListener;
    }

    public void setFrontRadarListener(IFrontRadarListener iFrontRadarListener) {
        this.mFrontRadarListener = iFrontRadarListener;
    }

    public void setPGearProtectListener(IPGearProtectListener iPGearProtectListener) {
        this.mPGearProtectListener = iPGearProtectListener;
    }

    private void subscribeProcessor() {
        ProcessorFactory.getProcessor(JniKey.LEFT_LIST_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$faCnJyZbUjayJZYL2Y41SpchI70
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$0$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LEFT_LIST_INDEX, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$x2BEyz7WP-zdTeouhs4taZAX-lw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$1$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LEFT_LIST_SENSOR_FAULT, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$rvLl_DwZhAgdsXNAYd5U9whlFfg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$2$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LEFT_CARD_INDEX, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$HVNOu1VX8CzQEpNvifWiNvz6fAQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$3$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LEFT_CARD_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$55oaXWb6Vz0KCFSTwKsHNKOuct4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$4$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.RIGHT_LIST_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$FFNkZepSlqyl19tonuq44QRC7NY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$5$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.RIGHT_LIST_INDEX, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$_3l5ArTsTkmuosJ81nEtF_B1fX8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$6$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.RIGHT_CARD_INDEX, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$A1Nm-PuJPeS2xjgRazmFRmRKxco
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$7$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.RIGHT_CARD_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ZvzTZVgiVp3lDHZwIA089vNeW_U
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$8$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_TOAST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$T7LqQNnc9MFfa8mDIRoAc9zKk-w
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$9$ClusterManager((NavToastData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_DRIVE_SIDE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$2UA544iuXT98iSgtKe_dOnpDBiM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$10$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_ARROW_ID, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$LPtnYGTZsc4EskFrCDjt97yKUL8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$11$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_GUIDANCE_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$YqXePGL5IadBb-BY2pfxyVOLjI8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$12$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_IMAGE_GUIDANCE_TEXTURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$5w2hYtKqtklvdqjlpWWsZe6Or5o
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$13$ClusterManager((TextureImageData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_IMAGE_NAV_TEXTURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$JagPeJsoucpIUdVMigEZyT_x09o
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$14$ClusterManager((TextureImageData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_NORMAL_LANE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$KBT7-RpKJ4jS9dZOEKfqZZSaHRA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$15$ClusterManager((NavLaneData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_TOLLGATE_LANE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$_-soXsmRfuxaN3BV5c0IvyX4d2o
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$16$ClusterManager((NavGateData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAV_START, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Zj1qP7FgcgEP_lyEYECXh766qc0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$17$ClusterManager(obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAV_SR_MODE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$jlINLAQvQQe56S2T2h1HOLdVll0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$18$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NAV_SR_TRAFFIC, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$h0TnaEArodylaEOXtoUFa4VqEWA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$19$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TIRE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$PJn43XuTCSvyl3CoAe_6km0Q1Bk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$20$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_PRESSURE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$JrMVF1qbODiNy6FVve0Z5SZNFGg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$21$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_PRESSURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$XEW82qg-qE4ut-BcX9avtuoZZTg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$22$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_PRESSURE_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$aNNiZev80x4F2IsWg9x5S0feq9U
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$23$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TEMPERATURES_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$B9afY1gUR51YtlQ7qWALEC795iI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$24$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TEMPERATURES, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$uHET4Bcs5kplOPzyt2tRx55JUiE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$25$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TEMPERATURES_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$uUJGlAmlF8gxjcl6KSqrrMJ8Vm4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$26$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TIRE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$GGVuUvKT5XWUO-yjrtCKvKQ0Flg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$27$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_PRESSURE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$3dDgcYiAj0NtePIudByso4HQ6Mg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$28$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_PRESSURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$bMePHeG1-XpyyU-spmZy8ZAR0Lk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$29$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_PRESSURE_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$aPpHPHwuP_EdTGycSw0YERlRxuI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$30$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TEMPERATURES_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$SIjd-8d8NtgOV9Di7mjCWccAIVU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$31$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TEMPERATURES, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$jovjmS2qq7wVYt9ss4l2tIK8zr0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$32$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TEMPERATURES_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$p74yNX3b7aw3Ewa-nbZoNmVs_xo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$33$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TIRE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Pb_L31P34pcOFTe8Z_Z1QipT7Ps
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$34$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_PRESSURE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$HP-26vjWPWZ0le7oaGsJBsoKmyI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$35$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_PRESSURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$n_-iHMGGX7WrG0ARBblbGyJKSuw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$36$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_PRESSURE_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$bFwNlxhcA_K67OXRfp835KCV-Hk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$37$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TEMPERATURES_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$vdj1F0jR0e_P6nEpy5oLLTX5K2o
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$38$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TEMPERATURES, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$RzeiUh3xoUQdwAghvw791xEbsGk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$39$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TEMPERATURES_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$r3YagUO43I6PVyVGySzEbwsRi64
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$40$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TIRE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$czjTymm9CPiIaQ8NAVzP0sn63AQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$41$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_PRESSURE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$FnjECFNjq0AcqYvKAHKhrQfsMr0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$42$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_PRESSURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$H-kA90-qXYZltNQPg5uO2YP32UI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$43$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_PRESSURE_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$lxU8cfWsmDJh-uc2I6x148NbEyE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$44$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TEMPERATURES_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$E9hJHlPbl5v8nJHA45i5BBpOTek
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$45$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TEMPERATURES, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$bu9nqNvnsWwf9sChMBPYs1opcno
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$46$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TEMPERATURES_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$xUjF3KBDgh7WKHNnxHBwzFVqews
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$47$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HOOD_ENGINE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$cj1cpbe0O9sF85rFsmztcr7Gviw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$48$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HOOD_LEFT_CHARGE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$w42SLZd8mq-X-QW85a4RIqTytk0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$49$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HOOD_RIGHT_CHARGE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$-aCkdhYMZsXodZQt9bQm8gOnSgQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$50$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HOOD_TRUNK, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$VB9P30bveNAmR1liQuKvmUJd7rw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$51$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.THIS_TIME_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$NQx4e0oHGLYb0pOdeNoIWS6C3I0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$52$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.THIS_TIME_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$0MCJA-JNGdL8zIshnw5ITewaZUE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$53$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.AFTER_CHARGING_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$93Lp83MywFIDww-KTeR2cs4veds
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$54$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.AFTER_CHARGING_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$5nQYtjqYNo0XO3UcRdDvxgk9h5o
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$55$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_A_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$lHapegQVmHnhCDLFjLcHwQfgvgk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$56$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_A_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$1svPJkf2be_QU9w4EEQj-USE8gw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$57$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_B_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$WTax3UcKeXRz7hVwMn0nepiYLiM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$58$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_B_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$FJa1hIlQL_WXnoMJXmnBSbM9mdU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$59$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TOTAL_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$iXoOBQkM6td8tYMqoCACONDzoTw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$60$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TOTAL_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$QaZm6rsb1QGwqzK9Ju_Vn3OvlhU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$61$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.AVERAGE_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$hzIVu81ssSP83EFPuopg7GEgcK8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$62$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.AVERAGE_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$-puT2PSyJHffPOVgi-wkVWiSI50
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$63$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ELAPSED_TIME_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$gKFjGPC_utls4BW4YCpSTXEomlc
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$64$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ELAPSED_TIME_UNITS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$mltHegaE1-Mtjedrhf2V3BOpNHA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$65$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_IMAGE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$hvOYoKU-pHgvIfgiK_rtUZe3-rw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$66$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_PLAY_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ZyDNSFJq8EyrRc7LukhIKdoh0nA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$67$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_BAR_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Am6pJTdlAFvPoc3eNkDH2HxcOc0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$68$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_SOUND_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$j_6ZtWIAbNHotFxlM6W2U9oZXSY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$69$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_STRING_1, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$GWu2Hx_t9tUpUPzFrImeznOn_34
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$70$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_STRING_2, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$iabtfNM7vRueRErvjBq0x2HOD7Y
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$71$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MUSIC_BAR_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$TJ_tNOXbC9hCKOnKsQ6nYNNaIoo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$72$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_IMAGE_MUSIC_TEXTURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$oPnLdqvi5jljfMwcLOntuqacL7U
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$73$ClusterManager((TextureImageData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$24INjbqHh1JaE_I4W9kuFJzOq10
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$74$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_FC, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$YT7VGJvEI7M1rwfOTF6AInx7WzI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$75$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_FL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$OHA2iCUP-N_IHNmQHyPrKzhYa7s
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$76$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_FR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$aUH_L05V5cS-n7DQVqf2PhMFJfI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$77$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_RCL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$qZC2QAJaAwpf1vxUUlHnfOVwVPA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$78$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_RCR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$xLd9HgvPohnGlGVMEQyBHSu3_NE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$79$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$uYw7lJzyFNHYuj3PdNCYpxh3-vM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$80$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FCL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$tS5Vvut4Kfg4RqNskOcESQJHI6k
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$81$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FCR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$qG7lIwo9Do9gLF2nArnpCq_O1JE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$82$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FOL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ulDjwYDdJyyOt6ApnLPi4uKTR60
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$83$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$WA038v4yR5TBnhn7BPiLAdp9cIo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$84$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FSL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$slwXt1I4EU8o_--1YXgNAzAseds
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$85$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FSR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$LfNEvk4-hr_OPZL9otlbkEI2BeQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$86$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RCL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$4z_2aYJCyHCXlscwJGfGIMia4xE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$87$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RCR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Otcdr_9-lHmGk7C0C97omnYgohA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$88$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_ROL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Z8Th7MM_U7LsMcKkcKDR6skPwbs
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$89$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_ROR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$tNQHLsMHcSQw1hpR2bBdL7PTdms
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$90$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RSL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$2hMeJlyod9YA1sBe-3wDxb8D594
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$91$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RSR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$rL8XeLvG00XzRtkyHHFn54uZwEM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$92$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SENSOR_FAULT_CAR_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$R67BbEasYNv8UcGxQQgr88xYIWg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$93$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$V5_CCoV9DRsF1LPRVGXbVkziqc4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$94$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE1, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$T15arUTTLrZ-YbSIHwpzmJMB_8o
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$95$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE2, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$avVOK1Hc-u_kHd6wL1Is9X8gtk0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$96$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE3, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$wkbKy92cCcgwab-GJo0IQPuewTM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$97$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE4, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$IQxvCV9yoS0lt_tEvCiWk4TkIPc
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$98$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE5, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$bjKVDA_x8lKFItxuLSHjN4UH_W8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$99$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE6, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$FGgpwcT6QluTwAVhDhYhUnFIN8g
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$100$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE7, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$sai-2rChcUaxF4Blr0wIt6Pol5k
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$101$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE8, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$0ALo7UArEtMWtfasXRfncgAv2QY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$102$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$I3sbiOyaEFcToiKwAawKtSJ_5pY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$103$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_F, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$6QPaLkXnV9scn661SBQDg_77HoA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$104$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_L, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$grhJK56EV7bVptDyT-WIcrfTLp0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$105$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_B, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$3ECpWFotUBCiuj-qlxjOqKfzRvo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$106$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_R, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$e4oLnQ7ynUteE0hhzuMJNi6MBzY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$107$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SENSOR_FAULT_TEXT_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$QekOAJ39xHPzhlhlKdTrNGEjHKY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$108$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.RADAR_CALIBRATION, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$7jDsWodYqY0llh8jjH9p8Csls7M
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$109$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LIDAR_RL_DIRT, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$DdCyA7Z9iaQc3442I15tGxjvxcQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$110$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LIDAR_RR_DIRT, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$2xwVXtC2c_XAyExT5hRrzKaF384
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$111$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.POWER_AVERAGE_ENERGY_CONSUMPTION, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$N5mySZ2yuvSfnPQbW2xuiaHM3gQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$112$ClusterManager((Float) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.POWER_CURVE_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ck_2c_vqa3mnLxoz3HTSHQyfTas
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$113$ClusterManager((Float) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_POWER_VEH_PWR_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$042SgGrm04WxBhZJclAvuUpEM7E
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$114$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_POWER_VEH_PWR_MAX, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$3MDNGUqvEoyzCtFY0osQ_32ZquI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$115$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_POWER_VEH_PWR_MIN, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$9A8-bKQDzLJrHUpAbhjK_D9Q_fk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$116$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.RES_AVAIL_POWER, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$hBGMFIcct4svhGytov5cKpva43g
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$117$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.CHARGE_STATUS, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$OBdq6UdpmQUEaIWVn2noyp662ro
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$118$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SUPER_CHARGE_FLAG, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$l7Oly_fOC9R26uQhddy-V4L6BEY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$119$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.AP_POINT_MENT_HOUR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$D3ZsIlLI-Os695z1MrefiM3Acyc
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$120$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.AP_POINT_MENT_MINUTE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$M3au8s5PViTTqtlnsCt0OJKo8m0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$121$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMPLETE_HOUR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$tyVSByARaKqGCRWg6bqlOwHkE6E
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$122$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMPLETE_MINUTE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$4t1et6BQxQRPAVMWgfu97IPR510
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$123$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HEAT_BATTERY_TIPS_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Noz5bbHuNo5AFM3gzSy0T_WMShY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$124$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIME_REMAINING_TIPS_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$PaD15x-DLSayWXoS6iz25LJdDng
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$125$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HEAT_BATTERY_HOUR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$fAtfjKLo7RvFkSU52QADcndrITY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$126$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.HEAT_BATTERY_MINUTE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$YS7MDeH479pYryMmKSWG-hoX5qs
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$127$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.POWER_INFORMATION_CURRENT, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$DsjC22YDoH2aXwBDyy4Am22jTVE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$128$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.POWER_INFORMATION_VOLTAGE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$mffAZhQTKmznM_ifAQANCZ4tnxg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$129$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.POWER_INFORMATION_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$hNtYeT7sQ17DpU1R9C3_Kk9yor8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$130$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.CHARGING_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$s2hEvKdWKaCTJztd1r0NuryILY0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$131$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIME_REMAINING_HOUR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$xAnLdUdbNYV5HRNM7DgmLsOX2-c
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$132$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TIME_REMAINING_MINUTE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ctDoXi8EEDI1GoZLq2THsx-Uqe8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$133$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.CHARGING_ESOC_MAX_CHG, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$vlrfB6kkWhTV_QvJMhhhrRv9Wdk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$134$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_GEAR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$oocogjfcZcE1YbZpMQt3Mit6Q8k
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$135$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_ELECTRIC_QUANTITY, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$CSI_-868ulz3gK6Jre047o1_FSM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$136$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_ENDURANCE_MILEAGE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$EL302fPWxcTGbzEjLasWQCsoQe8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$137$ClusterManager((Float) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ENDURANCE_MILEAGE_NUM_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$eNge-YwIsgRxq7m4ULITAST2v3Q
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$138$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_ELECTRICITY_COLOR_CONTROLPROP, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$0kCzcHhiMvBGEHCuB9ymeTId7Q4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$139$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_TURN_LEFT_LIGHT_CONTROPROP, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$raWeHhZ727-QaceEV8oLSb2Zcb4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$140$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_TURN_RIGHT_LIGHT_CONTROPROP, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$S95eTjifAAir1asZwKN8nTwLPeg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$141$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_CAR_COLOR_CONTROPROP, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$H2GPh1YRvxvhG2MjEGRULkqc5Vk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$142$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_IS_OFF_CONTRO_PROP, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$8iGy_727D7sIXwUAvlpJjF6kXFI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$143$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_IS_CHARGING, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$PwbMx7xsih0mhn-PMwTpxdVjKHA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$144$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_SPEED, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$0tdtu2fCk4SPbJHEFD_StV_klZ4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$145$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.DOOR_BL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Cgp_VJmRn7nHaDMqytJxiV34mrM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$146$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.DOOR_BR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$jAdXQZYJhrN4mfGqVWoqidBmRB8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$147$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.DOOR_FL, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$E0Euk9JrU_pQGi8jUgQQRmdUgNQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$148$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.DOOR_FR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$4SnPQ3mqExT87pRJIrpMAollRZM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$149$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_TEMPERATURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$oW19G4o78j8hn6-20JciY2t3tGw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$150$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_DRIVING_MODE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$r_-X1kFgCndvjRlgPILJvIcDQCg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$151$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_READY_INDICATOR_LIGHT, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$fAZ3H0A9Z1ec_ayjhzY77-BZxcU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$152$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_BATTERY_LIFE_STANDARD, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$KVNRRVtQW6tCfbXTzHAO4PkTgvQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$153$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_TIME, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$1lf6wTiUk3HiXya8ijNLgF0GK_A
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$154$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_TIMEPATTERN, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Wi-MpcttFq2NA6PxPH6tcMgsoFU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$155$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_MORNING_OR_AFTERNOON, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$zSS-tW2E3kPmF0p2QqJzPhmTpuo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$156$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_TEMPERATURE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Lrj1XAYX-QI8SKi20zDSyKkqdp8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$157$ClusterManager((Float) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_AIR_VOLUME, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$B_MkBOtVHPc7tfwjU-towuIclog
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$158$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_AIR_VOLUME_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$knfvWbFEhS-NwOxFkqDxI1Ym0So
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$159$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_TEMPERATURE_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$3uDjA3HZmL5Gfa26P7I4cg_AqpA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$160$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_MID_VOLUME, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$6CViXNdkfv_QVkVxzwCnWV8Q7nk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$161$ClusterManager((Float) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_MID_VOLUME_SILENCE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$UbFh-SdxkppWjjUqU3ZBi_cEfNM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$162$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_MID_VOICE_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$fDcI09FiTt8fVA7h9osdrcW47CE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$163$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_VOICE_INFO_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ECrN4UU31wTZMmyDYU14JJKuGrg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$164$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_LOW_SPEED_ALARM, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$s1GGDDXUZBcz6tNAOA7_jgxds8M
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$165$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PREVENT_NGEAR_BY_MISTAKETIME, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$DA4QjbwT9_uM4WUzsj1ComucnXA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$166$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PREVENT_NGEAR_BY_MISTAKE_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$e_l4Bnk-lJKMxrWmjW3IBRABRSs
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$167$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ABNORMAL_VEHICLE_CONDITION, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$aBaW_VHiR9WUSsbSxX-D-R8_vM8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$168$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_OPERATION_GUIDE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$SaB1ODgMqXUeBv2bAeib9t2_M3A
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$169$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_DISTANCE_ADJUST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$gXeXKhRwqIJMvbtLpISggHomHGo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$170$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_DISTANCE_ADJUST_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$-ANOHYaDseTPI9rjUzKR9JRY-sM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$171$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_DISTANCE_AUTO_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$DHjnDrikBu5D8SqJd5fHTfLIc9k
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$172$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_SPEED_ADJUST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$7cKMz1de873PJv0tV6-PZwvU7dI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$173$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_SPEED_ADJUST_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$u77qgilieL46BmYIr7CSGmKJaLQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$174$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_FORCED_POWER_DOWN_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$H9aCZzbmtNH4Ya-BIpjSMer-Ghw
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$175$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_FORCED_POWER_DOWN_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$XIFpcYLIsyY0sBTfj_ajVd8gmTY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$176$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALL_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$0CqNRDQWhB5vaxMP2FZpG1QwxY4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$177$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALL_VISIBLE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$TEyV-2FSlK_ftke51gyn5YM8kKA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$178$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_BTPHONE_BUTTON_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$gonPhzV1AhxouHfkprp8lf2mQCI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$179$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALLERID, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$fqAOUB_bSn4vzj1bIrqxQT3wO-M
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$180$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALL_TIME, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ofrHllb4Gps3lzpJMDNylsRWxKY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$181$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PGEAR_SAFETY_PROTECT_TIME, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$xjztnQGzVaS9xUaXjZOykCPBvnM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$182$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PGEAR_SAFETY_PROTECT_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$2CUDaZ4iqrhynTcMtdWibumlb-4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$183$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.REFRESH_FAMILY_PACAKGE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$puSR2HFbXV3SqYnnVqcNoWQOLpM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$184$ClusterManager((SpaceBean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ACC_IS_CC, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$GJ54Q0rPmKHHS53CDvoYx98cEFk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$185$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ACC_SPEED, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$8X_FuNLV0nCO7GPMr1ZNAissw2g
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$186$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ACC_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$gOH7gbVGjqZpzAT0QFz0krFg-jA
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$187$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.NGP_INDICATOR_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$d24GcEPBTV6cfmqK5P7LfBIjR88
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$188$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ADAS_HOLD, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$71AhjYOk6MgR2PUL0aQUCB30dlM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$189$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TSR_FORBID, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$4T9QJJVlBENoo0ilmaz3Icg0JTo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$190$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TSR_WARNING, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$EgggWVkjkJk2cqy4AYfR9-2M87I
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$191$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TSR_RATE_LIMITING_TYPE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$NzBgMV-zoNuyyU5Um7eUzXkAnO8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$192$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TSR_RATE_LIMITING_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$S7IGFsaZjfzp-tzZcTKVh0uudDU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$193$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ADAS_LCC_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ebYO4T6hDGXPOw4c8brtxR_TXgI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$194$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.BEHIND_DIST_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$9PGq3w5z2nqXHueCYzKebpqwVaE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$195$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_DIST_VALUE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$sTj7fHfEiyCclMwBCZIO1tVP07w
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$196$ClusterManager((String) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_CL_COLOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$7RYjvHiTKFtGy6uleproNprKoqo
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$197$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_CL_DIST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$bVhJeyika52PtoOLJ7sKMXH5xGM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$198$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_CR_COLOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$1vp7IklDlHMWHF3bf9jggwuZ2l8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$199$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_CR_DIST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$oAso1GDt9CTxYj7HthjwzsDuQZ4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$200$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_OL_COLOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Nqo29i20wtgc19lpi6fM3S9OOOE
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$201$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_OL_DIST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$259Lyn4vYxIMf3zv6uDB9ki1jF4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$202$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_OR_COLOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$lN-oJ-WoWa3Qa5iWI0NEGMPmiTs
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$203$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_OR_DIST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$FFcapca1sFx4oP3qmNS_hWeUhjg
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$204$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_SL_COLOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$FqD5shLTR3qELDWUOJlKLl0TEf4
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$205$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_SL_DIST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$x2-OYavyCIuCyufeYtWNY-rUM0Y
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$206$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_SR_COLOR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$2WoEU5IbDQfLsYtAckf8Nx8iLA8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$207$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.FRONT_SR_DIST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$g6U8JdYDDQvUakX-JSSHpEOW6HQ
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$208$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COLOR_TEST, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$MvfktunbkH4pIwMOe0_1gryCx18
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$209$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TRIGGER_AUTO_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$2zAUQ3g_RWypJAJQjPVTsBSXYuY
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$210$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.TRIGGER_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$YjrNxHuP0VBw-xuWAFs5uF_ayj8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$211$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SD_TRAFFIC_LIGHT, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$SezjGv0Cj4M4rDpxXNjj4vJQFmU
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$212$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.COMMON_TELLTALE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$nbS0iNkNkexWBrS7Alpvs_SMI88
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$213$ClusterManager((CommonTelltaleData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.UNSET_TELLTALE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$ATSsawPOhULhtoDKOs2-o4kMqfI
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$214$ClusterManager((UnsetTelltaleData) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.LCC_FAILURE_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$6NIjB6Uj-DP_UwvkNB9rh5rpVZM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$215$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ALC_INDICATOR_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$r7r2V7rGKEUAy0_XetjefO90FxM
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$216$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ISLC_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$I0OCFW0be-yd0qchvQk1VZDsB-8
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$217$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.SAS_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$jKhrABzXWVm82fuTF5tftY7Cyn0
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$218$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.OTA_STATE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$Df23TSswFbPT2AoXbA_bJPnvm2Q
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$219$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.WIPER_TYPE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$5xR3W0WM_NHRLUFSs9t2Y50lJ18
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$220$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.WIPER_SHOW, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$oStrB5zA1yofu12oXC41cadQrjk
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$221$ClusterManager((Boolean) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.DISTANCE_DIS_TYPE, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$BqCakN_88R2tlXO0sbUmH1Ceu-E
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$222$ClusterManager((Integer) obj);
            }
        });
        ProcessorFactory.getProcessor(JniKey.ELEC_QUA_STR, new Consumer() { // from class: com.xiaopeng.cluster.-$$Lambda$ClusterManager$eIow3enwAQttaA6DkLeI12akwRs
            @Override // io.reactivex.rxjava3.functions.Consumer
            public final void accept(Object obj) {
                ClusterManager.this.lambda$subscribeProcessor$223$ClusterManager((String) obj);
            }
        });
    }

    public /* synthetic */ void lambda$subscribeProcessor$0$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept LeftListVisible:" + bool);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onLeftListVisible(bool.booleanValue());
            } else {
                XILog.d(TAG, "onLeftListVisible mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$1$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LeftListIndex:" + num);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onLeftListIndex(num.intValue());
            } else {
                XILog.d(TAG, "onLeftListIndex mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$2$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept LeftListSensorFault:" + bool);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onLeftListSensorFault(bool.booleanValue());
            } else {
                XILog.d(TAG, "onLeftListSensorFault mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$3$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LeftCardIndex:" + num);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onLeftCardIndex(num.intValue());
            } else {
                XILog.d(TAG, "onLeftCardIndex mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$4$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept LeftCardVisible:" + bool);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onLeftCardVisible(bool.booleanValue());
            } else {
                XILog.d(TAG, "onLeftCardVisible mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$5$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept RightListVisible:" + bool);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onRightListVisible(bool.booleanValue());
            } else {
                XILog.d(TAG, "onRightListVisible mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$6$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept RightListIndex:" + num);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onRightListIndex(num.intValue());
            } else {
                XILog.d(TAG, "onRightListIndex mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$7$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept RightCardIndex:" + num);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onRightCardIndex(num.intValue());
            } else {
                XILog.d(TAG, "onRightCardIndex mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$8$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept RightCardVisible:" + bool);
        for (ICardControlListener iCardControlListener : this.mICardControlListeners) {
            if (iCardControlListener != null) {
                iCardControlListener.onRightCardVisible(bool.booleanValue());
            } else {
                XILog.d(TAG, "onRightCardVisible mICardControlListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$9$ClusterManager(NavToastData navToastData) throws Throwable {
        XILog.d(TAG, "accept NavigationToast:" + navToastData);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onNavigationToast(navToastData.getNavigationDistance(), navToastData.getNavigationDistanceUnit(), navToastData.getNavigationRoadName());
            } else {
                XILog.d(TAG, "onNavigationToast mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$10$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept NavigationDriveSide:" + bool);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onNavigationDriveSide(bool.booleanValue());
            } else {
                XILog.d(TAG, "onNavigationDriveSide mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$11$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept NavigationArrowID:" + num);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onNavigationArrowID(num.intValue());
            } else {
                XILog.d(TAG, "onNavigationArrowID mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$12$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept NavigationGuidanceVisible:" + bool);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onNavigationGuidanceVisible(bool.booleanValue());
            } else {
                XILog.d(TAG, "onNavigationGuidanceVisible mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$13$ClusterManager(TextureImageData textureImageData) throws Throwable {
        XILog.d(TAG, "accept RefreshImageGuidanceTexture:" + textureImageData);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onRefreshImageGuidanceTexture(textureImageData.getData(), textureImageData.getHeight(), textureImageData.getWidth(), textureImageData.getFormat());
            } else {
                XILog.d(TAG, "onRefreshImageGuidanceTexture mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$14$ClusterManager(TextureImageData textureImageData) throws Throwable {
        XILog.d(TAG, "accept RefreshImageNaviTexture:" + textureImageData);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onRefreshImageNaviTexture(textureImageData.getData(), textureImageData.getHeight(), textureImageData.getWidth(), textureImageData.getFormat());
            } else {
                XILog.d(TAG, "onRefreshImageNaviTexture mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$15$ClusterManager(NavLaneData navLaneData) throws Throwable {
        XILog.d(TAG, "accept NavigationNormalLane:" + navLaneData);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onNavigationNormalLane(navLaneData.getBackLane(), navLaneData.getFrontLane(), navLaneData.isVisible());
            } else {
                XILog.d(TAG, "onNavigationNormalLane mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$16$ClusterManager(NavGateData navGateData) throws Throwable {
        XILog.d(TAG, "accept NavigationTollGateLane:" + navGateData);
        for (INavListener iNavListener : this.mINavListeners) {
            if (iNavListener != null) {
                iNavListener.onNavigationTollGateLane(navGateData.getTollGate(), navGateData.isVisible());
            } else {
                XILog.d(TAG, "onNavigationTollGateLane mINavListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$17$ClusterManager(Object obj) throws Throwable {
        XILog.d(TAG, "accept onNaviStart:" + obj);
        for (INaviSRListener iNaviSRListener : this.mNaviSRListeners) {
            if (iNaviSRListener != null) {
                iNaviSRListener.onNaviStart();
            } else {
                XILog.d(TAG, "onNaviStart iNaviSRListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$18$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept onNaviSRMode:" + bool);
        for (INaviSRListener iNaviSRListener : this.mNaviSRListeners) {
            if (iNaviSRListener != null) {
                iNaviSRListener.onNaviSRMode(bool.booleanValue());
            } else {
                XILog.d(TAG, "onNaviSRMode iNaviSRListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$19$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept onNaviSRTraffic:" + num);
        for (INaviSRListener iNaviSRListener : this.mNaviSRListeners) {
            if (iNaviSRListener != null) {
                iNaviSRListener.onNaviSRTraffic(num.intValue());
            } else {
                XILog.d(TAG, "onNaviSRTraffic iNaviSRListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$20$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireFLTireState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLTireState(num.intValue());
        } else {
            XILog.d(str, "onTireFLTireState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$21$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireFLPressureState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLPressureState(num.intValue());
        } else {
            XILog.d(str, "onTireFLPressureState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$22$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFLPressure:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLPressure(str);
        } else {
            XILog.d(str2, "onTireFLPressure mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$23$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFLPressureUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLPressureUnits(str);
        } else {
            XILog.d(str2, "onTireFLPressureUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$24$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireFLTemperaturesState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLTemperaturesState(num.intValue());
        } else {
            XILog.d(str, "onTireFLTemperaturesState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$25$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFLTemperatures:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLTemperatures(str);
        } else {
            XILog.d(str2, "onTireFLTemperatures mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$26$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFLTemperaturesUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFLTemperaturesUnits(str);
        } else {
            XILog.d(str2, "onTireFLTemperaturesUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$27$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireFRTireState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRTireState(num.intValue());
        } else {
            XILog.d(str, "onTireFRTireState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$28$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireFRPressureState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRPressureState(num.intValue());
        } else {
            XILog.d(str, "onTireFRPressureState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$29$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFRPressure:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRPressure(str);
        } else {
            XILog.d(str2, "onTireFRPressure mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$30$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFRPressureUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRPressureUnits(str);
        } else {
            XILog.d(str2, "onTireFRPressureUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$31$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireFRTemperaturesState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRTemperaturesState(num.intValue());
        } else {
            XILog.d(str, "onTireFRTemperaturesState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$32$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFRTemperatures:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRTemperatures(str);
        } else {
            XILog.d(str2, "onTireFRTemperatures mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$33$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireFRTemperaturesUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireFRTemperaturesUnits(str);
        } else {
            XILog.d(str2, "onTireFRTemperaturesUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$34$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireBLTireState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLTireState(num.intValue());
        } else {
            XILog.d(str, "onTireBLTireState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$35$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireBLPressureState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLPressureState(num.intValue());
        } else {
            XILog.d(str, "onTireBLPressureState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$36$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBLPressure:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLPressure(str);
        } else {
            XILog.d(str2, "onTireBLPressure mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$37$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBLPressureUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLPressureUnits(str);
        } else {
            XILog.d(str2, "onTireBLPressureUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$38$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireBLTemperaturesState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLTemperaturesState(num.intValue());
        } else {
            XILog.d(str, "onTireBLTemperaturesState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$39$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBLTemperatures:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLTemperatures(str);
        } else {
            XILog.d(str2, "onTireBLTemperatures mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$40$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBLTemperaturesUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBLTemperaturesUnits(str);
        } else {
            XILog.d(str2, "onTireBLTemperatures mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$41$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireBRTireState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRTireState(num.intValue());
        } else {
            XILog.d(str, "onTireBRTireState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$42$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireBRPressureState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRPressureState(num.intValue());
        } else {
            XILog.d(str, "onTireBRPressureState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$43$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBRPressure:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRPressure(str);
        } else {
            XILog.d(str2, "onTireBRPressure mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$44$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBRPressureUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRPressureUnits(str);
        } else {
            XILog.d(str2, "onTireBRPressureUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$45$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TireBRTemperaturesState:" + num);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRTemperaturesState(num.intValue());
        } else {
            XILog.d(str, "onTireBRTemperaturesState mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$46$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBRTemperatures:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRTemperatures(str);
        } else {
            XILog.d(str2, "onTireBRTemperatures mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$47$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TireBRTemperaturesUnits:" + str);
        ICarInfoTireListener iCarInfoTireListener = this.mCarInfoTireListener;
        if (iCarInfoTireListener != null) {
            iCarInfoTireListener.onTireBRTemperaturesUnits(str);
        } else {
            XILog.d(str2, "onTireBRTemperaturesUnits mCarInfoTireListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$48$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept HoodEngine:" + bool);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onHoodEngine(bool.booleanValue());
            } else {
                XILog.d(TAG, "onHoodEngine ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$49$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept HoodLeftCharge:" + num);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onHoodFastCharge(num.intValue());
            } else {
                XILog.d(TAG, "onHoodFastCharge ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$50$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept HoodRightCharge:" + num);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onHoodNormalCharge(num.intValue());
            } else {
                XILog.d(TAG, "onHoodNormalCharge ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$51$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept HoodTrunk:" + bool);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onHoodTrunk(bool.booleanValue());
            } else {
                XILog.d(TAG, "onHoodTrunk ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$52$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept ThisTimeValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onThisTimeValue(str);
            } else {
                XILog.d(TAG, "onThisTimeValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$53$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept ThisTimeUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onThisTimeUnits(str);
            } else {
                XILog.d(TAG, "onThisTimeUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$54$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept AfterChargingValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onAfterChargingValue(str);
            } else {
                XILog.d(TAG, "onAfterChargingValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$55$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept AfterChargingUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onAfterChargingUnits(str);
            } else {
                XILog.d(TAG, "onAfterChargingUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$56$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept SubtotalAValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onSubtotalAValue(str);
            } else {
                XILog.d(TAG, "onSubtotalAValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$57$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept SubtotalAUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onSubtotalAUnits(str);
            } else {
                XILog.d(TAG, "onSubtotalAUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$58$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept SubtotalBValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onSubtotalBValue(str);
            } else {
                XILog.d(TAG, "onSubtotalBValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$59$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept SubtotalBUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onSubtotalBUnits(str);
            } else {
                XILog.d(TAG, "onSubtotalBUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$60$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept TotalValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onTotalValue(str);
            } else {
                XILog.d(TAG, "onTotalValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$61$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept TotalUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onTotalUnits(str);
            } else {
                XILog.d(TAG, "onTotalUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$62$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept AverageValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onAverageValue(str);
            } else {
                XILog.d(TAG, "onAverageValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$63$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept AverageUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onAverageUnits(str);
            } else {
                XILog.d(TAG, "onAverageUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$64$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept ElapsedTimeValue:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onElapsedTimeValue(str);
            } else {
                XILog.d(TAG, "onElapsedTimeValue mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$65$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept ElapsedTimeUnits:" + str);
        for (IOdoMeterListener iOdoMeterListener : this.mIOdoMeterListeners) {
            if (iOdoMeterListener != null) {
                iOdoMeterListener.onElapsedTimeUnits(str);
            } else {
                XILog.d(TAG, "onElapsedTimeUnits mIOdoMeterListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$66$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept MusicImageState:" + bool);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onMusicImageState(bool.booleanValue());
            } else {
                XILog.d(TAG, "onMusicImageState mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$67$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept MusicPlayState:" + bool);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onMusicPlayState(bool.booleanValue());
            } else {
                XILog.d(TAG, "onMusicPlayState mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$68$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept MusicBarVisible:" + bool);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onMusicBarVisible(bool.booleanValue());
            } else {
                XILog.d(TAG, "onMusicBarVisible mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$69$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MusicSoundState:" + num);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onMusicSoundState(num.intValue());
            } else {
                XILog.d(TAG, "onMusicSoundState mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$70$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept MusicString1:" + str);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onMusicName(str);
            } else {
                XILog.d(TAG, "onMusicName mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$71$ClusterManager(String str) throws Throwable {
        XILog.d(TAG, "accept MusicString2:" + str);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onSignerName(str);
            } else {
                XILog.d(TAG, "onSignerName mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$72$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MusicBarValue:" + num);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onMusicBarValue(num.intValue());
            } else {
                XILog.d(TAG, "onMusicBarValue mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$73$ClusterManager(TextureImageData textureImageData) throws Throwable {
        XILog.d(TAG, "accept RefreshImageMusicTexture:" + textureImageData);
        for (IMusicListener iMusicListener : this.mIMusicListeners) {
            if (iMusicListener != null) {
                iMusicListener.onRefreshImageMusicTexture(textureImageData.getData(), textureImageData.getHeight(), textureImageData.getWidth(), textureImageData.getFormat());
            } else {
                XILog.d(TAG, "onRefreshImageMusicTexture mIMusicListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$74$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MillimeterWaveRadarState:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onMillimeterWaveRadarState(num.intValue());
            } else {
                XILog.d(TAG, "onMillimeterWaveRadarState mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$75$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MillimeterWaveRadarFC:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onMillimeterWaveRadarFC(num.intValue());
            } else {
                XILog.d(TAG, "onMillimeterWaveRadarFC mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$76$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MillimeterWaveRadarFL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onMillimeterWaveRadarFL(num.intValue());
            } else {
                XILog.d(TAG, "onMillimeterWaveRadarFL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$77$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MillimeterWaveRadarFR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onMillimeterWaveRadarFR(num.intValue());
            } else {
                XILog.d(TAG, "onMillimeterWaveRadarFR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$78$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MillimeterWaveRadarRCL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onMillimeterWaveRadarRCL(num.intValue());
            } else {
                XILog.d(TAG, "onMillimeterWaveRadarRCL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$79$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept MillimeterWaveRadarRCR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onMillimeterWaveRadarRCR(num.intValue());
            } else {
                XILog.d(TAG, "onMillimeterWaveRadarRCR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$80$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarState:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarState(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarState mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$81$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarFCL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarFCL(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarFCL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$82$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarFCR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarFCR(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarFCR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$83$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarFOL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarFOL(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarFOL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$84$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarFOR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarFOR(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarFOR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$85$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarFSL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarFSL(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarFSL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$86$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarFSR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarFSR(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarFSR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$87$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarRCL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarRCL(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarRCL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$88$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarRCR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarRCR(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarRCR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$89$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarROL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarROL(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarROL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$90$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarROR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarROR(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarROR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$91$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarRSL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarRSL(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarRSL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$92$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept UltrasonicRadarRSR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onUltrasonicRadarRSR(num.intValue());
            } else {
                XILog.d(TAG, "onUltrasonicRadarRSR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$93$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SensorFaultCarState:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSensorFaultCarState(num.intValue());
            } else {
                XILog.d(TAG, "onSensorFaultCarState mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$94$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraState:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraState(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraState mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$95$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite1:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite1(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite1 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$96$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite2:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite2(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite2 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$97$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite3:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite3(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite3 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$98$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite4:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite4(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite4 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$99$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite5:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite5(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite5 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$100$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite6:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite6(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite6 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$101$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite7:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite7(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite7 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$102$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SmartCameraSite8:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSmartCameraSite8(num.intValue());
            } else {
                XILog.d(TAG, "onSmartCameraSite8 mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$103$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LookAroundCameraState:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLookAroundCameraState(num.intValue());
            } else {
                XILog.d(TAG, "onLookAroundCameraState mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$104$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LookAroundCameraF:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLookAroundCameraF(num.intValue());
            } else {
                XILog.d(TAG, "onLookAroundCameraF mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$105$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LookAroundCameraL:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLookAroundCameraL(num.intValue());
            } else {
                XILog.d(TAG, "onLookAroundCameraL mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$106$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LookAroundCameraB:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLookAroundCameraB(num.intValue());
            } else {
                XILog.d(TAG, "onLookAroundCameraB mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$107$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LookAroundCameraR:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLookAroundCameraR(num.intValue());
            } else {
                XILog.d(TAG, "onLookAroundCameraR mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$108$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept SensorFaultTextValue:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onSensorFaultTextValue(num.intValue());
            } else {
                XILog.d(TAG, "onTextValue mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$109$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept RadarCalibration:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onRadarCalibration(num.intValue());
            } else {
                XILog.d(TAG, "onRadarCalibration mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$110$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LidarRLDirt:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLidarRLDirt(num.intValue());
            } else {
                XILog.d(TAG, "onLidarRLDirt mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$111$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept LidarRRDirt:" + num);
        for (ISensorFaultListener iSensorFaultListener : this.mISensorFaultListeners) {
            if (iSensorFaultListener != null) {
                iSensorFaultListener.onLidarRRDirt(num.intValue());
            } else {
                XILog.d(TAG, "onLidarRLDirt mISensorFaultListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$112$ClusterManager(Float f) throws Throwable {
        XILog.d(TAG, "accept PowerAverageEnergyConsumption:" + f);
        for (IPowerConsumptionListener iPowerConsumptionListener : this.mIPowerConsumptionListeners) {
            if (iPowerConsumptionListener != null) {
                iPowerConsumptionListener.onPowerAverageEnergyConsumption(f.floatValue());
            } else {
                XILog.d(TAG, "onPowerAverageEnergyConsumption mIPowerConsumptionListener is null");
            }
        }
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            iCommonListener.onPowerAverageEnergyConsumption(f.floatValue());
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$113$ClusterManager(Float f) throws Throwable {
        XILog.d(TAG, "accept PowerCurveValue:" + f);
        for (IPowerConsumptionListener iPowerConsumptionListener : this.mIPowerConsumptionListeners) {
            if (iPowerConsumptionListener != null) {
                iPowerConsumptionListener.onPowerCurveValue(f.floatValue());
            } else {
                XILog.d(TAG, "onPowerCurveValue mIPowerConsumptionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$114$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept RefreshPowerVEHpwrValue:" + num);
        for (IPowerConsumptionListener iPowerConsumptionListener : this.mIPowerConsumptionListeners) {
            if (iPowerConsumptionListener != null) {
                iPowerConsumptionListener.onRefreshPowerVEHpwrValue(num.intValue());
            } else {
                XILog.d(TAG, "onRefreshPowerVEHpwrValue mIPowerConsumptionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$115$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept RefreshPowerVEHpwrMax:" + num);
        for (IPowerConsumptionListener iPowerConsumptionListener : this.mIPowerConsumptionListeners) {
            if (iPowerConsumptionListener != null) {
                iPowerConsumptionListener.onRefreshPowerVEHpwrMax(num.intValue());
            } else {
                XILog.d(TAG, "onRefreshPowerVEHpwrValue mIPowerConsumptionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$116$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept RefreshPowerVEHpwrMin:" + num);
        for (IPowerConsumptionListener iPowerConsumptionListener : this.mIPowerConsumptionListeners) {
            if (iPowerConsumptionListener != null) {
                iPowerConsumptionListener.onRefreshPowerVEHpwrMin(num.intValue());
            } else {
                XILog.d(TAG, "onRefreshPowerVEHpwrValue mIPowerConsumptionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$117$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept ResAvailPower:" + num);
        for (IPowerConsumptionListener iPowerConsumptionListener : this.mIPowerConsumptionListeners) {
            if (iPowerConsumptionListener != null) {
                iPowerConsumptionListener.onResAvailPower(num.intValue());
            } else {
                XILog.d(TAG, "onResAvailPower mIPowerConsumptionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$118$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ChargeStatus:" + num);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onChargeStatus(num.intValue());
        } else {
            XILog.d(str, "onChargeStatus mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$119$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept SuperChrgFlg:" + bool);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onSuperChrgFlg(bool.booleanValue());
        } else {
            XILog.d(str, "onSuperChrgFlg mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$120$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept AppointmentHour:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onAppointmentHour(str);
        } else {
            XILog.d(str2, "onAppointmentHour mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$121$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept AppointmentMinute:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onAppointmentMinute(str);
        } else {
            XILog.d(str2, "onAppointmentMinute mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$122$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept CompleteHour:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onCompleteHour(str);
        } else {
            XILog.d(str2, "onCompleteHour mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$123$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept CompleteMinute:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onCompleteMinute(str);
        } else {
            XILog.d(str2, "onCompleteMinute mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$124$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept HeatBatteryTipsState:" + num);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onHeatBatteryTipsState(num.intValue());
        } else {
            XILog.d(str, "onHeatBatteryTipsState mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$125$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TimeRemainingTipsState:" + num);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onTimeRemainingTipsState(num.intValue());
        } else {
            XILog.d(str, "onTimeRemainingTipsState mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$126$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept HeatBatteryHour:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onHeatBatteryHour(str);
        } else {
            XILog.d(str2, "onHeatBatteryHour mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$127$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept HeatBatteryMinute:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onHeatBatteryMinute(str);
        } else {
            XILog.d(str2, "onHeatBatteryMinute mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$128$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept PowerInformationCurrent:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onPowerInformationCurrent(str);
        } else {
            XILog.d(str2, "onPowerInformationCurrent mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$129$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept PowerInformationVoltage:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onPowerInformationVoltage(str);
        } else {
            XILog.d(str2, "onPowerInformationVoltage mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$130$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept onPowerInformationVisible:" + bool);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onPowerInformationVisible(bool.booleanValue());
        } else {
            XILog.d(str, "onPowerInformationVisible mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$131$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ChargingState:" + num);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onChargingState(num.intValue());
        } else {
            XILog.d(str, "onChargingState mIChargeListener is null");
        }
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onElectricQuantity mICommonListener is null");
            } else {
                iCommonListener.onChargingState(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$132$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TimeRemainingHour:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onTimeRemainingHour(str);
        } else {
            XILog.d(str2, "onTimeRemainingHour mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$133$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept TimeRemainingMinute:" + str);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onTimeRemainingMinute(str);
        } else {
            XILog.d(str2, "onTimeRemainingMinute mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$134$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ChargingEsocmaxchg:" + num);
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onEsocmaxchg(num.intValue());
        } else {
            XILog.d(str, "onEsocmaxchg mIChargeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$135$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept CommonGear:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onGear mICommonListener is null");
            } else {
                iCommonListener.onGear(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$136$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept CommonElectricQuantity:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onElectricQuantity mICommonListener is null");
            } else {
                iCommonListener.onElectricQuantity(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$137$ClusterManager(Float f) throws Throwable {
        XILog.d(TAG, "accept CommonEnduranceMileage:" + f);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onEnduranceMileage mICommonListener is null");
            } else {
                iCommonListener.onEnduranceMileage(f.floatValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$138$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept EnduranceMileageNumVisible:" + bool);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onEnduranceMileageNumVisible mICommonListener is null");
            } else {
                iCommonListener.onEnduranceMileageNumVisible(bool.booleanValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$139$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept CommonElectricityColorControlProp:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onElectricityColorControlProp mICommonListener is null");
            } else {
                iCommonListener.onElectricityColorControlProp(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$140$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept CommonTurnLeftLightControProp:" + bool);
        IndicatorListener indicatorListener = this.mIndicatorListener;
        if (indicatorListener != null) {
            indicatorListener.onTurnLeftLightControProp(bool.booleanValue());
        } else {
            XILog.d(str, "onTurnLeftLightControProp mIndicatorListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$141$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept CommonTurnRightLightControProp:" + bool);
        IndicatorListener indicatorListener = this.mIndicatorListener;
        if (indicatorListener != null) {
            indicatorListener.onTurnRightLightControProp(bool.booleanValue());
        } else {
            XILog.d(str, "onTurnRightLightControProp mIndicatorListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$142$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept CommonCarColorControProp:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onCarColorControProp mICommonListener is null");
            } else {
                iCommonListener.onCarColorControProp(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$143$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept CommonIsOffControProp:" + bool);
        IControlListener iControlListener = this.mIControlListener;
        if (iControlListener != null) {
            iControlListener.onIsOffControProp(bool.booleanValue());
        } else {
            XILog.d(str, "onIsOffControProp mIControlListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$144$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept CommonIsCharging:" + bool);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onIsCharging(bool.booleanValue());
        } else {
            XILog.d(str, "onIsCharging mInstrumentListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$145$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept CommonSpeed:" + num);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onSpeed(num.intValue());
        } else {
            XILog.d(str, "onSpeed mInstrumentListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$146$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept DoorBL:" + bool);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onDoorBL(bool.booleanValue());
            } else {
                XILog.d(TAG, "onDoorBL ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$147$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept DoorBR:" + bool);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onDoorBR(bool.booleanValue());
            } else {
                XILog.d(TAG, "onDoorBR ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$148$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept DoorFL:" + bool);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onDoorFL(bool.booleanValue());
            } else {
                XILog.d(TAG, "onDoorFL ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$149$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept DoorFR:" + bool);
        for (ICarConditionListener iCarConditionListener : this.mCarConditionListeners) {
            if (iCarConditionListener != null) {
                iCarConditionListener.onDoorFR(bool.booleanValue());
            } else {
                XILog.d(TAG, "onDoorFR ICarConditionListener is null");
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$150$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept NormalInfoTemperature:" + num);
        INormalInfoListener iNormalInfoListener = this.mNormalInfoListener;
        if (iNormalInfoListener != null) {
            iNormalInfoListener.onNormalInfoTemperature(num.intValue());
        } else {
            XILog.d(str, "onTemperature NormalInfoListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$151$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept NormalInfoDrivingMode:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onDrivingMode mICommonListener is null");
            } else {
                iCommonListener.onDrivingMode(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$152$ClusterManager(Boolean bool) throws Throwable {
        XILog.d(TAG, "accept NormalInfoReadyIndicatorLight:" + bool);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onReadyIndicatorLight mICommonListener is null");
            } else {
                iCommonListener.onReadyIndicatorLight(bool.booleanValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$153$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept NormalInfoBatteryLifeStandard:" + num);
        INormalInfoListener iNormalInfoListener = this.mNormalInfoListener;
        if (iNormalInfoListener != null) {
            iNormalInfoListener.onBatteryLifeStandard(num.intValue());
        } else {
            XILog.d(str, "onBatteryLifeStandard NormalInfoListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$154$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept NormalInfoTime:" + str);
        INormalInfoListener iNormalInfoListener = this.mNormalInfoListener;
        if (iNormalInfoListener != null) {
            iNormalInfoListener.onTime(str);
        } else {
            XILog.d(str2, "onTime NormalInfoListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$155$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept NormalInfoTimePattern:" + num);
        INormalInfoListener iNormalInfoListener = this.mNormalInfoListener;
        if (iNormalInfoListener != null) {
            iNormalInfoListener.onTimePattern(num.intValue());
        } else {
            XILog.d(str, "onTimePattern NormalInfoListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$156$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept NormalInfoMorningOrAfternoon:" + num);
        INormalInfoListener iNormalInfoListener = this.mNormalInfoListener;
        if (iNormalInfoListener != null) {
            iNormalInfoListener.onMorningOrAfternoon(num.intValue());
        } else {
            XILog.d(str, "onMorningOrAfternoon NormalInfoListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$157$ClusterManager(Float f) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayTemperature:" + f);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onTemperature(f.floatValue());
        } else {
            XILog.d(str, "onTemperature NormalInfoListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$158$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayAirVolume:" + num);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onAirVolume(num.intValue());
        } else {
            XILog.d(str, "onAirVolume mInstrumentListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$159$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayAirVolumeState:" + num);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onAirVolumeState(num.intValue());
        } else {
            XILog.d(str, "onAirVolumeState mInstrumentListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$160$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayTemperatureVisible:" + bool);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onTemperatureVisible(bool.booleanValue());
        } else {
            XILog.d(str, "onTemperatureVisible mInstrumentListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$161$ClusterManager(Float f) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayMidVolume:" + f);
        IOsdMediaVolumeListener iOsdMediaVolumeListener = this.mOsdMediaVolumeListener;
        if (iOsdMediaVolumeListener != null) {
            iOsdMediaVolumeListener.onMidVolume(f.floatValue());
        } else {
            XILog.d(str, "onMidVolume mOsdMediaVolumeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$162$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayMidVolumeSilence:" + bool);
        IOsdMediaVolumeListener iOsdMediaVolumeListener = this.mOsdMediaVolumeListener;
        if (iOsdMediaVolumeListener != null) {
            iOsdMediaVolumeListener.onMidVolumeSilence(bool.booleanValue());
        } else {
            XILog.d(str, "onMidVolumeSilence mOsdMediaVolumeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$163$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayMidVoiceVisible:" + bool);
        IOsdMediaVolumeListener iOsdMediaVolumeListener = this.mOsdMediaVolumeListener;
        if (iOsdMediaVolumeListener != null) {
            iOsdMediaVolumeListener.onMidVoiceVisible(bool.booleanValue());
        } else {
            XILog.d(str, "onMidVoiceVisible mOsdMediaVolumeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$164$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayVoiceInfoState:" + num);
        IOsdMediaVolumeListener iOsdMediaVolumeListener = this.mOsdMediaVolumeListener;
        if (iOsdMediaVolumeListener != null) {
            iOsdMediaVolumeListener.onOverlayVoiceInfoState(num.intValue());
        } else {
            XILog.d(str, "onMidVoiceVisible mOsdMediaVolumeListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$165$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayLowSpeedAlarm:" + bool);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onLowSpeedAlarm(bool.booleanValue());
        } else {
            XILog.d(str, "onLowSpeedAlarm mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$166$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayPreventNGearByMistakeTime:" + num);
        IOSDConfirmListener iOSDConfirmListener = this.mOSDConfirmListener;
        if (iOSDConfirmListener != null) {
            iOSDConfirmListener.onPreventNGearByMistakeTime(num.intValue());
        } else {
            XILog.d(str, "onPreventNGearByMistakeTime mOSDConfirmListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$167$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayPreventNGearByMistakeVisible:" + bool);
        IOSDConfirmListener iOSDConfirmListener = this.mOSDConfirmListener;
        if (iOSDConfirmListener != null) {
            iOSDConfirmListener.onPreventNGearByMistakeVisible(bool.booleanValue());
        } else {
            XILog.d(str, "onPreventNGearByMistakeVisible mOSDConfirmListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$168$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayAbnormalVehicleCondition:" + bool);
        InstrumentListener instrumentListener = this.mInstrumentListener;
        if (instrumentListener != null) {
            instrumentListener.onAbnormalVehicleCondition(bool.booleanValue());
        } else {
            XILog.d(str, "onAbnormalVehicleCondition mICommonListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$169$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayACCOperationGuide:" + bool);
        IAccSettingListener iAccSettingListener = this.mAccSettingListener;
        if (iAccSettingListener != null) {
            iAccSettingListener.onACCOperationGuide(bool.booleanValue());
        } else {
            XILog.d(str, "mAccSettingListener mAccSettingListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$170$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayACCDistanceAdjust:" + num);
        IAccSettingListener iAccSettingListener = this.mAccSettingListener;
        if (iAccSettingListener != null) {
            iAccSettingListener.onACCDistanceAdjust(num.intValue());
        } else {
            XILog.d(str, "mAccSettingListener mAccSettingListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$171$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayACCDistanceAdjustVisible:" + bool);
        IAccSettingListener iAccSettingListener = this.mAccSettingListener;
        if (iAccSettingListener != null) {
            iAccSettingListener.onACCDistanceAdjustVisible(bool.booleanValue());
        } else {
            XILog.d(str, "mAccSettingListener mAccSettingListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$172$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayACCDistanceAdjustVisible:" + bool);
        IAccSettingListener iAccSettingListener = this.mAccSettingListener;
        if (iAccSettingListener != null) {
            iAccSettingListener.onACCDistanceAutoVisible(bool.booleanValue());
        } else {
            XILog.d(str, "mAccSettingListener mAccSettingListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$173$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayACCSpeedAdjust:" + num);
        IAccSettingListener iAccSettingListener = this.mAccSettingListener;
        if (iAccSettingListener != null) {
            iAccSettingListener.onACCSpeedAdjust(num.intValue());
        } else {
            XILog.d(str, "mAccSettingListener mAccSettingListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$174$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayACCSpeedAdjustVisible:" + bool);
        IAccSettingListener iAccSettingListener = this.mAccSettingListener;
        if (iAccSettingListener != null) {
            iAccSettingListener.onACCSpeedAdjustVisible(bool.booleanValue());
        } else {
            XILog.d(str, "mAccSettingListener mAccSettingListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$175$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayForcedPowerDownVisible:" + bool);
        IOSDConfirmListener iOSDConfirmListener = this.mOSDConfirmListener;
        if (iOSDConfirmListener != null) {
            iOSDConfirmListener.onForcedPowerDownVisible(bool.booleanValue());
        } else {
            XILog.d(str, "onForcedPowerDownVisible mOSDConfirmListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$176$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayForcedPowerDownState:" + num);
        IOSDConfirmListener iOSDConfirmListener = this.mOSDConfirmListener;
        if (iOSDConfirmListener != null) {
            iOSDConfirmListener.onForcedPowerDownState(num.intValue());
        } else {
            XILog.d(str, "onForcedPowerDownState mOSDConfirmListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$177$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayCallState:" + num);
        IOsdCallListener iOsdCallListener = this.mCallListener;
        if (iOsdCallListener != null) {
            iOsdCallListener.onCallState(num.intValue());
        } else {
            XILog.d(str, "onCallState mCallListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$178$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayCallVisible:" + bool);
        IOsdCallListener iOsdCallListener = this.mCallListener;
        if (iOsdCallListener != null) {
            iOsdCallListener.onCallVisible(bool.booleanValue());
        } else {
            XILog.d(str, "onCallVisible mCallListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$179$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayBTPhoneButtonState:" + num);
        IOsdCallListener iOsdCallListener = this.mCallListener;
        if (iOsdCallListener != null) {
            iOsdCallListener.onOverlayBTPhoneButtonState(num.intValue());
        } else {
            XILog.d(str, "onOverlayBTPhoneButtonState mCallListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$180$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept OverlayCallerID:" + str);
        IOsdCallListener iOsdCallListener = this.mCallListener;
        if (iOsdCallListener != null) {
            iOsdCallListener.onCallerID(str);
        } else {
            XILog.d(str2, "onCallerID mCallListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$181$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept OverlayCallTime:" + str);
        IOsdCallListener iOsdCallListener = this.mCallListener;
        if (iOsdCallListener != null) {
            iOsdCallListener.onCallTime(str);
        } else {
            XILog.d(str2, "onCallTime mCallListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$182$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayPGearSafetyProtectTime:" + num);
        IPGearProtectListener iPGearProtectListener = this.mPGearProtectListener;
        if (iPGearProtectListener != null) {
            iPGearProtectListener.onPGearSafetyProtectTime(num.intValue());
        } else {
            XILog.d(str, "onPGearSafetyProtectTime mPGearProtectListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$183$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept OverlayPGearSafetyProtectState:" + num);
        IPGearProtectListener iPGearProtectListener = this.mPGearProtectListener;
        if (iPGearProtectListener != null) {
            iPGearProtectListener.onPGearSafetyProtectState(num.intValue());
        } else {
            XILog.d(str, "onPGearSafetyProtectState mPGearProtectListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$184$ClusterManager(SpaceBean spaceBean) throws Throwable {
        if (spaceBean == null) {
            XILog.e(TAG, "accept onRefreshFamilyPackage is null ");
            return;
        }
        String str = TAG;
        XILog.d(str, "accept onRefreshFamilyPackage: mode :" + spaceBean.getMode() + " state :" + spaceBean.getState());
        IControlListener iControlListener = this.mIControlListener;
        if (iControlListener != null) {
            iControlListener.onRefreshFamilyPackage(spaceBean);
        } else {
            XILog.d(str, "onRefreshFamilyPackage mIControlListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$185$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ACCIsCC:" + bool);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onACCIsCC(bool.booleanValue());
        } else {
            XILog.d(str, "onACCIsCC mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$186$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ACCSpeed:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onACCSpeed(num.intValue());
        } else {
            XILog.d(str, "onACCSpeed mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$187$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ACCState:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onACCState(num.intValue());
        } else {
            XILog.d(str, "onACCState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$188$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept NGPIndicatorState:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onNGPIndicatorState(num.intValue());
        } else {
            XILog.d(str, "onNGPIndicatorState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$189$ClusterManager(Boolean bool) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept AdasHold:" + bool);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onAdasHold(bool.booleanValue());
        } else {
            XILog.d(str, "onAdasHold mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$190$ClusterManager(Integer num) throws Throwable {
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onTSRForbid(num.intValue());
        } else {
            XILog.d(TAG, "onTSRForbid mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$191$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TSRWarning:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onTSRWarning(num.intValue());
        } else {
            XILog.d(str, "onTSRWarning mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$192$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TSRRateLimitingType:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onTSRRateLimitingType(num.intValue());
        } else {
            XILog.d(str, "onTSRRateLimitingType mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$193$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept TSRRateLimitingValue:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onTSRRateLimitingValue(num.intValue());
        } else {
            XILog.d(str, "onTSRRateLimitingValue mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$194$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept AdasLCCState:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onAdasLCCState(num.intValue());
        } else {
            XILog.d(str, "onAdasLCCState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$195$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept BehindDistValue:" + str);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onBehindDistValue(str);
        } else {
            XILog.d(str2, "onBehindDistValue mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$196$ClusterManager(String str) throws Throwable {
        String str2 = TAG;
        XILog.d(str2, "accept FrontDistValue:" + str);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontDistValue(str);
        } else {
            XILog.d(str2, "onFrontDistValue mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$197$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontCLColor:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontCLColor(num.intValue());
        } else {
            XILog.d(str, "onFrontCLColor mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$198$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontCLDist:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontCLDist(num.intValue());
        } else {
            XILog.d(str, "onFrontCLDist mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$199$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontCRColor:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontCRColor(num.intValue());
        } else {
            XILog.d(str, "onFrontCRColor mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$200$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontCRDist:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontCRDist(num.intValue());
        } else {
            XILog.d(str, "onFrontCRDist mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$201$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontOLColor:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontOLColor(num.intValue());
        } else {
            XILog.d(str, "onFrontOLColor mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$202$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontOLDist:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontOLDist(num.intValue());
        } else {
            XILog.d(str, "onFrontOLDist mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$203$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontORColor:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontORColor(num.intValue());
        } else {
            XILog.d(str, "onFrontORColor mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$204$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontORDist:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontORDist(num.intValue());
        } else {
            XILog.d(str, "onFrontORDist mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$205$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontSLColor:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontSLColor(num.intValue());
        } else {
            XILog.d(str, "onFrontSLColor mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$206$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontSLDist:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontSLDist(num.intValue());
        } else {
            XILog.d(str, "onFrontSLDist mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$207$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontSRColor:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontSRColor(num.intValue());
        } else {
            XILog.d(str, "onFrontSRColor mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$208$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept FrontSRDist:" + num);
        IFrontRadarListener iFrontRadarListener = this.mFrontRadarListener;
        if (iFrontRadarListener != null) {
            iFrontRadarListener.onFrontSRDist(num.intValue());
        } else {
            XILog.d(str, "onFrontSRDist mFrontRadarListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$209$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ColorTest:" + num);
        IControlListener iControlListener = this.mIControlListener;
        if (iControlListener != null) {
            iControlListener.onColorTest(num.intValue());
        } else {
            XILog.d(str, "onColorTest mIControlListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$210$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept TriggerAutoState:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onTriggerAutoState mICommonListener is null");
            } else {
                iCommonListener.onTriggerAutoState(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$211$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept TriggerState:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onTriggerState mICommonListener is null");
            } else {
                iCommonListener.onTriggerState(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$212$ClusterManager(Integer num) throws Throwable {
        XILog.d(TAG, "accept onTrafficLightsState:" + num);
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener == null) {
                XILog.d(TAG, "onTrafficLightsState mICommonListener is null");
            } else {
                iCommonListener.onTrafficLightsState(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$213$ClusterManager(CommonTelltaleData commonTelltaleData) throws Throwable {
        if (commonTelltaleData == null) {
            XILog.d(TAG, "accept  CommonTelltale is null");
            return;
        }
        String str = TAG;
        XILog.d(str, "accept CommonTelltale:" + commonTelltaleData.toString());
        IndicatorListener indicatorListener = this.mIndicatorListener;
        if (indicatorListener != null) {
            indicatorListener.onCommonTelltale(commonTelltaleData.getId(), commonTelltaleData.isShow());
        } else {
            XILog.d(str, "onCommonTelltale mIndicatorListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$214$ClusterManager(UnsetTelltaleData unsetTelltaleData) throws Throwable {
        if (unsetTelltaleData == null || unsetTelltaleData.getIds() == null || unsetTelltaleData.getIds().length == 0) {
            XILog.d(TAG, "accept UnsetTelltale: ids length is 0");
            return;
        }
        String str = TAG;
        XILog.d(str, "accept UnsetTelltale:" + unsetTelltaleData.getIds().length + " ids:" + Arrays.toString(unsetTelltaleData.getIds()));
        IndicatorListener indicatorListener = this.mIndicatorListener;
        if (indicatorListener != null) {
            indicatorListener.onUnsetTelltale(unsetTelltaleData.getIds());
        } else {
            XILog.d(str, "onUnsetTelltale mIndicatorListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$215$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept LCCFailureState:" + num);
        IITNAdasListener iITNAdasListener = this.mITNAdasListener;
        if (iITNAdasListener != null) {
            iITNAdasListener.onLCCFailureState(num.intValue());
        } else {
            XILog.d(str, "onLCCFailureState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$216$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ALCIndicatorState:" + num);
        IITNAdasListener iITNAdasListener = this.mITNAdasListener;
        if (iITNAdasListener != null) {
            iITNAdasListener.onALCState(num.intValue());
        } else {
            XILog.d(str, "onALCIndicatorState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$217$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept ISLCState:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onISLCState(num.intValue());
        } else {
            XILog.d(str, "onISLCState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$218$ClusterManager(Integer num) throws Throwable {
        String str = TAG;
        XILog.d(str, "accept SASState:" + num);
        IAdasListener iAdasListener = this.mAdasListener;
        if (iAdasListener != null) {
            iAdasListener.onSASState(num.intValue());
        } else {
            XILog.d(str, "onSASState mAdasListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$219$ClusterManager(Integer num) throws Throwable {
        IOTAListener iOTAListener = this.mIOTAListener;
        if (iOTAListener != null) {
            iOTAListener.onOtaState(num.intValue());
        } else {
            XILog.d(TAG, "onOtaState mIOTAListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$220$ClusterManager(Integer num) throws Throwable {
        IOsdWiperListener iOsdWiperListener = this.mOsdWiperListener;
        if (iOsdWiperListener != null) {
            iOsdWiperListener.onRainDetecSencfg(num.intValue());
        } else {
            XILog.d(TAG, "onRainDetecVisible mOsdWiperListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$221$ClusterManager(Boolean bool) throws Throwable {
        IOsdWiperListener iOsdWiperListener = this.mOsdWiperListener;
        if (iOsdWiperListener != null) {
            iOsdWiperListener.onRainDetecVisible(bool.booleanValue());
        } else {
            XILog.d(TAG, "onRainDetecVisible mOsdWiperListener is null");
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$222$ClusterManager(Integer num) throws Throwable {
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onRandisDisplayType(num.intValue());
        } else {
            XILog.e(TAG, "onRandisDisplayType  is null");
        }
        for (ICommonListener iCommonListener : this.mCommonListeners) {
            if (iCommonListener != null) {
                iCommonListener.onRandisDisplayType(num.intValue());
            }
        }
    }

    public /* synthetic */ void lambda$subscribeProcessor$223$ClusterManager(String str) throws Throwable {
        IChargeListener iChargeListener = this.mIChargeListener;
        if (iChargeListener != null) {
            iChargeListener.onCommonElectricQuantityStr(str);
        } else {
            XILog.e(TAG, "onCommonElectricQuantityStr mOsdWiperListener is null");
        }
        Set<ICommonListener> set = this.mCommonListeners;
        if (set != null) {
            for (ICommonListener iCommonListener : set) {
                if (iCommonListener != null) {
                    iCommonListener.onCommonElectricQuantityStr(str);
                }
            }
            return;
        }
        XILog.e(TAG, "onCommonElectricQuantityStr listener is null");
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onOtaState(int i) {
        ProcessorFactory.getProcessor(JniKey.OTA_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void setScreenOn(boolean z) {
        XILog.i(TAG, "setScreenOn:" + z);
        if (!CommonUtil.getIsControlDisplay()) {
            this.mIXpPowerManger.setScreen(z);
        } else if (z) {
            this.mIXpPowerManger.setDisplayState(2);
            this.mIXpPowerManger.setScreen(z);
        } else {
            this.mIXpPowerManger.setScreen(z);
            this.mIXpPowerManger.setDisplayState(1);
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void setDisplayState(int i) {
        XILog.i(TAG, "setDisplayState  :" + i);
        if (CommonUtil.getIsControlDisplay()) {
            return;
        }
        this.mIXpPowerManger.setDisplayState(i);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLeftListVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.LEFT_LIST_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLeftListIndex(int i) {
        ProcessorFactory.getProcessor(JniKey.LEFT_LIST_INDEX, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLeftListSensorFault(boolean z) {
        ProcessorFactory.getProcessor(JniKey.LEFT_LIST_SENSOR_FAULT, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLeftCardIndex(int i) {
        ProcessorFactory.getProcessor(JniKey.LEFT_CARD_INDEX, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLeftCardVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.LEFT_CARD_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRightListVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.RIGHT_LIST_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRightListIndex(int i) {
        ProcessorFactory.getProcessor(JniKey.RIGHT_LIST_INDEX, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRightCardIndex(int i) {
        ProcessorFactory.getProcessor(JniKey.RIGHT_CARD_INDEX, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRightCardVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.RIGHT_CARD_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNaviStart() {
        ProcessorFactory.getProcessor(JniKey.NAV_START, null).onNext(this.mObject);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNaviSRMode(boolean z) {
        ProcessorFactory.getProcessor(JniKey.NAV_SR_MODE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNaviSRTraffic(int i) {
        ProcessorFactory.getProcessor(JniKey.NAV_SR_TRAFFIC, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNavigationToast(String str, int i, String str2) {
        this.mNavToastData.setNavigationDistance(str);
        this.mNavToastData.setNavigationDistanceUnit(i);
        this.mNavToastData.setNavigationRoadName(str2);
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_TOAST, null).onNext(this.mNavToastData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNavigationDriveSide(boolean z) {
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_DRIVE_SIDE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNavigationArrowID(int i) {
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_ARROW_ID, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNavigationGuidanceVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_GUIDANCE_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshImageGuidanceTexture(byte[] bArr, int i, int i2, int i3) {
        if (bArr == null) {
            XILog.e(TAG, "onRefreshImageGuidanceTexture data is null ");
            return;
        }
        this.mNavGuidanceData.setData(bArr);
        this.mNavGuidanceData.setHeight(i);
        this.mNavGuidanceData.setWidth(i2);
        this.mNavGuidanceData.setFormat(i3);
        ProcessorFactory.getProcessor(JniKey.REFRESH_IMAGE_GUIDANCE_TEXTURE, null).onNext(this.mNavGuidanceData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshImageNaviTexture(byte[] bArr, int i, int i2, int i3) {
        if (bArr == null) {
            XILog.e(TAG, "onRefreshImageNaviTexture data is null");
            return;
        }
        this.mNavTextureData.setData(bArr);
        this.mNavTextureData.setFormat(i3);
        this.mNavTextureData.setHeight(i);
        this.mNavTextureData.setWidth(i2);
        ProcessorFactory.getProcessor(JniKey.REFRESH_IMAGE_NAV_TEXTURE, null).onNext(this.mNavTextureData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNavigationNormalLane(int[] iArr, int[] iArr2, boolean z) {
        this.mNavLaneData.setBackLane(iArr);
        this.mNavLaneData.setFrontLane(iArr2);
        this.mNavLaneData.setVisible(z);
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_NORMAL_LANE, null).onNext(this.mNavLaneData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNavigationTollGateLane(int[] iArr, boolean z) {
        this.mNavGateData.setTollGate(iArr);
        this.mNavGateData.setVisible(z);
        ProcessorFactory.getProcessor(JniKey.NAVIGATION_TOLLGATE_LANE, null).onNext(this.mNavGateData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLTireState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TIRE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLPressureState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_PRESSURE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLPressure(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_PRESSURE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLPressureUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_PRESSURE_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLTemperaturesState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TEMPERATURES_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLTemperatures(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TEMPERATURES, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFLTemperaturesUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FL_TEMPERATURES_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRTireState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TIRE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRPressureState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_PRESSURE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRPressure(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_PRESSURE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRPressureUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_PRESSURE_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRTemperaturesState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TEMPERATURES_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRTemperatures(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TEMPERATURES, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireFRTemperaturesUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_FR_TEMPERATURES_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLTireState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TIRE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLPressureState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_PRESSURE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLPressure(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_PRESSURE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLPressureUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_PRESSURE_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLTemperaturesState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TEMPERATURES_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLTemperatures(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TEMPERATURES, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBLTemperaturesUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BL_TEMPERATURES_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRTireState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TIRE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRPressureState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_PRESSURE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRPressure(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_PRESSURE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRPressureUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_PRESSURE_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRTemperaturesState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TEMPERATURES_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRTemperatures(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TEMPERATURES, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTireBRTemperaturesUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TIRE_BR_TEMPERATURES_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHoodEngine(boolean z) {
        ProcessorFactory.getProcessor(JniKey.HOOD_ENGINE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHoodLeftCharge(int i) {
        ProcessorFactory.getProcessor(JniKey.HOOD_LEFT_CHARGE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHoodRightCharge(int i) {
        ProcessorFactory.getProcessor(JniKey.HOOD_RIGHT_CHARGE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHoodTrunk(boolean z) {
        ProcessorFactory.getProcessor(JniKey.HOOD_TRUNK, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onThisTimeValue(String str) {
        ProcessorFactory.getProcessor(JniKey.THIS_TIME_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onThisTimeUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.THIS_TIME_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAfterChargingValue(String str) {
        ProcessorFactory.getProcessor(JniKey.AFTER_CHARGING_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAfterChargingUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.AFTER_CHARGING_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSubtotalAValue(String str) {
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_A_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSubtotalAUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_A_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSubtotalBValue(String str) {
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_B_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSubtotalBUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.SUB_TOTAL_B_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTotalValue(String str) {
        ProcessorFactory.getProcessor(JniKey.TOTAL_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTotalUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.TOTAL_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAverageValue(String str) {
        ProcessorFactory.getProcessor(JniKey.AVERAGE_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAverageUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.AVERAGE_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onElapsedTimeValue(String str) {
        ProcessorFactory.getProcessor(JniKey.ELAPSED_TIME_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onElapsedTimeUnits(String str) {
        ProcessorFactory.getProcessor(JniKey.ELAPSED_TIME_UNITS, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicImageState(boolean z) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_IMAGE_STATE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicPlayState(boolean z) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_PLAY_STATE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicBarVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_BAR_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicSoundState(int i) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_SOUND_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicString1(String str) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_STRING_1, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicString2(String str) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_STRING_2, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMusicBarValue(int i) {
        ProcessorFactory.getProcessor(JniKey.MUSIC_BAR_VALUE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshImageMusicTexture(byte[] bArr, int i, int i2, int i3) {
        if (bArr == null) {
            XILog.e(TAG, "onRefreshImageMusicTexture data is null");
            return;
        }
        this.mMusicTextureData.setData(bArr);
        this.mMusicTextureData.setFormat(i3);
        this.mMusicTextureData.setHeight(i);
        this.mMusicTextureData.setWidth(i2);
        ProcessorFactory.getProcessor(JniKey.REFRESH_IMAGE_MUSIC_TEXTURE, null).onNext(this.mMusicTextureData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMillimeterWaveRadarState(int i) {
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMillimeterWaveRadarFC(int i) {
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_FC, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMillimeterWaveRadarFL(int i) {
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_FL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMillimeterWaveRadarFR(int i) {
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_FR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMillimeterWaveRadarRCL(int i) {
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_RCL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMillimeterWaveRadarRCR(int i) {
        ProcessorFactory.getProcessor(JniKey.MILLIMETER_WAVE_RADAR_RCR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarState(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarFCL(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FCL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarFCR(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FCR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarFOL(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FOL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarFOR(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarFSL(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FSL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarFSR(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_FSR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarRCL(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RCL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarRCR(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RCR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarROL(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_ROL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarROR(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_ROR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarRSL(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RSL, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUltrasonicRadarRSR(int i) {
        ProcessorFactory.getProcessor(JniKey.ULTRASONIC_RADAR_RSR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSensorFaultCarState(int i) {
        ProcessorFactory.getProcessor(JniKey.SENSOR_FAULT_CAR_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraState(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite1(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE1, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite2(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE2, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite3(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE3, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite4(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE4, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite5(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE5, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite6(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE6, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite7(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE7, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSmartCameraSite8(int i) {
        ProcessorFactory.getProcessor(JniKey.SMART_CAMERA_SITE8, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLookAroundCameraState(int i) {
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLookAroundCameraF(int i) {
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_F, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLookAroundCameraL(int i) {
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_L, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLookAroundCameraB(int i) {
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_B, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLookAroundCameraR(int i) {
        ProcessorFactory.getProcessor(JniKey.LOOK_AROUND_CAMERA_R, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSensorFaultTextValue(int i) {
        ProcessorFactory.getProcessor(JniKey.SENSOR_FAULT_TEXT_VALUE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRadarCalibration(int i) {
        ProcessorFactory.getProcessor(JniKey.RADAR_CALIBRATION, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLidarRLDirt(int i) {
        ProcessorFactory.getProcessor(JniKey.LIDAR_RL_DIRT, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLidarRRDirt(int i) {
        ProcessorFactory.getProcessor(JniKey.LIDAR_RR_DIRT, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPowerAverageEnergyConsumption(float f) {
        ProcessorFactory.getProcessor(JniKey.POWER_AVERAGE_ENERGY_CONSUMPTION, null).onNext(Float.valueOf(f));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPowerCurveValue(float f) {
        ProcessorFactory.getProcessor(JniKey.POWER_CURVE_VALUE, null).onNext(Float.valueOf(f));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshPowerVEHpwrValue(int i) {
        ProcessorFactory.getProcessor(JniKey.REFRESH_POWER_VEH_PWR_VALUE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshPowerVEHpwrMax(int i) {
        ProcessorFactory.getProcessor(JniKey.REFRESH_POWER_VEH_PWR_MAX, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshPowerVEHpwrMin(int i) {
        ProcessorFactory.getProcessor(JniKey.REFRESH_POWER_VEH_PWR_MIN, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onResAvailPower(int i) {
        ProcessorFactory.getProcessor(JniKey.RES_AVAIL_POWER, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onChargeStatus(int i) {
        ProcessorFactory.getProcessor(JniKey.CHARGE_STATUS, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSuperChrgFlg(boolean z) {
        ProcessorFactory.getProcessor(JniKey.SUPER_CHARGE_FLAG, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAppointmentHour(String str) {
        ProcessorFactory.getProcessor(JniKey.AP_POINT_MENT_HOUR, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAppointmentMinute(String str) {
        ProcessorFactory.getProcessor(JniKey.AP_POINT_MENT_MINUTE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCompleteHour(String str) {
        ProcessorFactory.getProcessor(JniKey.COMPLETE_HOUR, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCompleteMinute(String str) {
        ProcessorFactory.getProcessor(JniKey.COMPLETE_MINUTE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHeatBatteryTipsState(int i) {
        ProcessorFactory.getProcessor(JniKey.HEAT_BATTERY_TIPS_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTimeRemainingTipsState(int i) {
        ProcessorFactory.getProcessor(JniKey.TIME_REMAINING_TIPS_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHeatBatteryHour(String str) {
        ProcessorFactory.getProcessor(JniKey.HEAT_BATTERY_HOUR, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onHeatBatteryMinute(String str) {
        ProcessorFactory.getProcessor(JniKey.HEAT_BATTERY_MINUTE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPowerInformationCurrent(String str) {
        ProcessorFactory.getProcessor(JniKey.POWER_INFORMATION_CURRENT, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPowerInformationVoltage(String str) {
        ProcessorFactory.getProcessor(JniKey.POWER_INFORMATION_VOLTAGE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPowerInformationVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.POWER_INFORMATION_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onChargingState(int i) {
        ProcessorFactory.getProcessor(JniKey.CHARGING_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTimeRemainingHour(String str) {
        ProcessorFactory.getProcessor(JniKey.TIME_REMAINING_HOUR, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTimeRemainingMinute(String str) {
        ProcessorFactory.getProcessor(JniKey.TIME_REMAINING_MINUTE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onEsocmaxchg(int i) {
        ProcessorFactory.getProcessor(JniKey.CHARGING_ESOC_MAX_CHG, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onGear(int i) {
        ProcessorFactory.getProcessor(JniKey.COMMON_GEAR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onElectricQuantity(int i) {
        ProcessorFactory.getProcessor(JniKey.COMMON_ELECTRIC_QUANTITY, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onEnduranceMileage(float f) {
        ProcessorFactory.getProcessor(JniKey.COMMON_ENDURANCE_MILEAGE, null).onNext(Float.valueOf(f));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onEnduranceMileageNumVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.ENDURANCE_MILEAGE_NUM_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onElectricityColorControlProp(int i) {
        ProcessorFactory.getProcessor(JniKey.COMMON_ELECTRICITY_COLOR_CONTROLPROP, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTurnLeftLightControProp(boolean z) {
        ProcessorFactory.getProcessor(JniKey.COMMON_TURN_LEFT_LIGHT_CONTROPROP, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTurnRightLightControProp(boolean z) {
        ProcessorFactory.getProcessor(JniKey.COMMON_TURN_RIGHT_LIGHT_CONTROPROP, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCarColorControProp(int i) {
        ProcessorFactory.getProcessor(JniKey.COMMON_CAR_COLOR_CONTROPROP, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onIsOffControProp(boolean z) {
        ProcessorFactory.getProcessor(JniKey.COMMON_IS_OFF_CONTRO_PROP, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onIsCharging(boolean z) {
        ProcessorFactory.getProcessor(JniKey.COMMON_IS_CHARGING, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSpeed(int i) {
        ProcessorFactory.getProcessor(JniKey.COMMON_SPEED, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onDoorBL(boolean z) {
        ProcessorFactory.getProcessor(JniKey.DOOR_BL, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onDoorBR(boolean z) {
        ProcessorFactory.getProcessor(JniKey.DOOR_BR, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onDoorFL(boolean z) {
        ProcessorFactory.getProcessor(JniKey.DOOR_FL, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onDoorFR(boolean z) {
        ProcessorFactory.getProcessor(JniKey.DOOR_FR, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNormalInfoTemperature(int i) {
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_TEMPERATURE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onDrivingMode(int i) {
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_DRIVING_MODE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onReadyIndicatorLight(boolean z) {
        ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_READY_INDICATOR_LIGHT, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onBatteryLifeStandard(int i) {
        if (i != 3 || BaseConfig.getInstance().isSupportCLTC()) {
            ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_BATTERY_LIFE_STANDARD, null).onNext(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTime(String str) {
        if (BaseConfig.getInstance().isSupportShowTime()) {
            ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_TIME, null).onNext(str);
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTimePattern(int i) {
        if (BaseConfig.getInstance().isSupportShowTime()) {
            ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_TIMEPATTERN, null).onNext(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMorningOrAfternoon(int i) {
        if (BaseConfig.getInstance().isSupportShowTime()) {
            ProcessorFactory.getProcessor(JniKey.NORMAL_INFO_MORNING_OR_AFTERNOON, null).onNext(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRainDetecSencfg(int i) {
        ProcessorFactory.getProcessor(JniKey.WIPER_TYPE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRainDetecVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.WIPER_SHOW, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTemperature(float f) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_TEMPERATURE, null).onNext(Float.valueOf(f));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAirVolume(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_AIR_VOLUME, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAirVolumeState(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_AIR_VOLUME_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTemperatureVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_TEMPERATURE_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMidVolume(float f) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_MID_VOLUME, null).onNext(Float.valueOf(f));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMidVolumeSilence(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_MID_VOLUME_SILENCE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onMidVoiceVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_MID_VOICE_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onOverlayVoiceInfoState(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_VOICE_INFO_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLowSpeedAlarm(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_LOW_SPEED_ALARM, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPreventNGearByMistakeTime(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PREVENT_NGEAR_BY_MISTAKETIME, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPreventNGearByMistakeVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PREVENT_NGEAR_BY_MISTAKE_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAbnormalVehicleCondition(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ABNORMAL_VEHICLE_CONDITION, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCOperationGuide(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_OPERATION_GUIDE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCDistanceAdjust(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_DISTANCE_ADJUST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCDistanceAutoVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_DISTANCE_AUTO_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCDistanceAdjustVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_DISTANCE_ADJUST_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCSpeedAdjust(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_SPEED_ADJUST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCSpeedAdjustVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_ACC_SPEED_ADJUST_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onForcedPowerDownVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_FORCED_POWER_DOWN_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onForcedPowerDownState(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_FORCED_POWER_DOWN_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCallState(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALL_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCallVisible(boolean z) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALL_VISIBLE, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onOverlayBTPhoneButtonState(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_BTPHONE_BUTTON_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCallerID(String str) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALLERID, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCallTime(String str) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_CALL_TIME, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPGearSafetyProtectTime(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PGEAR_SAFETY_PROTECT_TIME, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onPGearSafetyProtectState(int i) {
        ProcessorFactory.getProcessor(JniKey.OVERLAY_PGEAR_SAFETY_PROTECT_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRefreshFamilyPackage(int i, int i2) {
        if (BaseConfig.getInstance().isSupportSpace()) {
            SpaceBean spaceBean = new SpaceBean();
            spaceBean.setMode(i);
            spaceBean.setState(i2);
            ProcessorFactory.getProcessor(JniKey.REFRESH_FAMILY_PACAKGE, null).onNext(spaceBean);
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCIsCC(boolean z) {
        ProcessorFactory.getProcessor(JniKey.ACC_IS_CC, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCSpeed(int i) {
        ProcessorFactory.getProcessor(JniKey.ACC_SPEED, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onACCState(int i) {
        ProcessorFactory.getProcessor(JniKey.ACC_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onNGPIndicatorState(int i) {
        ProcessorFactory.getProcessor(JniKey.NGP_INDICATOR_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAdasHold(boolean z) {
        ProcessorFactory.getProcessor(JniKey.ADAS_HOLD, null).onNext(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTSRForbid(int i) {
        ProcessorFactory.getProcessor(JniKey.TSR_FORBID, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTSRWarning(int i) {
        ProcessorFactory.getProcessor(JniKey.TSR_WARNING, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTSRRateLimitingType(int i) {
        ProcessorFactory.getProcessor(JniKey.TSR_RATE_LIMITING_TYPE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTSRRateLimitingValue(int i) {
        ProcessorFactory.getProcessor(JniKey.TSR_RATE_LIMITING_VALUE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onAdasLCCState(int i) {
        ProcessorFactory.getProcessor(JniKey.ADAS_LCC_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onBehindDistValue(String str) {
        ProcessorFactory.getProcessor(JniKey.BEHIND_DIST_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontDistValue(String str) {
        ProcessorFactory.getProcessor(JniKey.FRONT_DIST_VALUE, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontCLColor(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_CL_COLOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontCLDist(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_CL_DIST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontCRColor(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_CR_COLOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontCRDist(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_CR_DIST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontOLColor(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_OL_COLOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontOLDist(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_OL_DIST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontORColor(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_OR_COLOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontORDist(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_OR_DIST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontSLColor(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_SL_COLOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontSLDist(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_SL_DIST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontSRColor(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_SR_COLOR, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onFrontSRDist(int i) {
        ProcessorFactory.getProcessor(JniKey.FRONT_SR_DIST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onColorTest(int i) {
        ProcessorFactory.getProcessor(JniKey.COLOR_TEST, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTriggerAutoState(int i) {
        ProcessorFactory.getProcessor(JniKey.TRIGGER_AUTO_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTriggerState(int i) {
        ProcessorFactory.getProcessor(JniKey.TRIGGER_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onTrafficLightsState(int i) {
        ProcessorFactory.getProcessor(JniKey.SD_TRAFFIC_LIGHT, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCommonElectricQuantityStr(String str) {
        ProcessorFactory.getProcessor(JniKey.ELEC_QUA_STR, null).onNext(str);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onRandisDisplayType(int i) {
        ProcessorFactory.getProcessor(JniKey.DISTANCE_DIS_TYPE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onCommonTelltale(int i, boolean z) {
        CommonTelltaleData commonTelltaleData = new CommonTelltaleData();
        commonTelltaleData.setId(i);
        commonTelltaleData.setShow(z);
        ProcessorFactory.getProcessor(JniKey.COMMON_TELLTALE, null).onNext(commonTelltaleData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onUnsetTelltale(int[] iArr) {
        this.mUnsetTelltaleData.setIds(iArr);
        ProcessorFactory.getProcessor(JniKey.UNSET_TELLTALE, null).onNext(this.mUnsetTelltaleData);
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onLCCFailureState(int i) {
        ProcessorFactory.getProcessor(JniKey.LCC_FAILURE_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onALCIndicatorState(int i) {
        ProcessorFactory.getProcessor(JniKey.ALC_INDICATOR_STATE, null).onNext(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.IClusterService
    @Deprecated
    public void onISLCState(int i) {
        if (BaseConfig.getInstance().isSupportIslc()) {
            XILog.i(TAG, "this car type is not support ISLC....");
        } else {
            ProcessorFactory.getProcessor(JniKey.ISLC_STATE, null).onNext(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.IClusterService
    public void onSASState(int i) {
        ProcessorFactory.getProcessor(JniKey.SAS_STATE, null).onNext(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes.dex */
    public static class ClusterManagerHolder {
        private static final ClusterManager instance = new ClusterManager();

        private ClusterManagerHolder() {
        }
    }
}
