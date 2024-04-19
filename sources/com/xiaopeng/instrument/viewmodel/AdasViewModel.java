package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.listener.IAdasListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.AdasCcBean;
import com.xiaopeng.instrument.bean.CcViewBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import java.util.Map;
/* loaded from: classes.dex */
public class AdasViewModel extends ViewModel implements IAdasListener, XpuInstrumentClient.ISpeedLimitListener {
    private static final int INVALID_SPEED = 0;
    private static final int NGP_START = 2;
    private static final int SAS_STATE_FAIL = 1;
    private static final int SAS_STATE_NO = 0;
    private static final int SPEED_LIMIT_ICON_HIDE = 0;
    private static final int SPEED_LIMIT_ICON_RED = 1;
    private static final int SPEED_LIMIT_TYPE_CAN = 0;
    private static final int SPEED_LIMIT_TYPE_XPU = 1;
    private static final String TAG = "AdasViewModel";
    protected int mAccSpeed;
    private boolean mIsCc;
    protected final MutableLiveData<CcViewBean> mCcLiveData = new MutableLiveData<>();
    protected final MutableLiveData<Boolean> mSasLiveData = new MutableLiveData<>();
    protected final MutableLiveData<CcViewBean> mTsrRateLimitLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mLccLiveData = new MutableLiveData<>();
    private final MutableLiveData<AdasCcBean> mNgpLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mTsrForbidOrWarningLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mHoldLiveData = new MutableLiveData<>();
    protected CcViewBean mCcViewBean = new CcViewBean();
    protected CcViewBean mTsrRateLimitBean = new CcViewBean();
    protected int mTSRRateLimitingType = 0;
    protected int mTSRRateLimitingValue = 0;
    private Map<Integer, AdasCcBean> mAdasCCBeanMap = DataConfigManager.getAdasCCBeanMap();
    private Map<Integer, AdasCcBean> mAdasLCCBeanMap = DataConfigManager.getAdasLCCBeanMap();
    private Map<Integer, AdasCcBean> mAdasACCBeanMap = DataConfigManager.getAdasACCBeanMap();
    private Map<Integer, AdasCcBean> mAdasNGPBeanMap = DataConfigManager.getAdasNGPBeanMap();
    private Map<Integer, AdasCcBean> mTsrWarningBeanMap = DataConfigManager.getTsrWarningMap();
    private Map<Integer, AdasCcBean> mTsrForbidBeanMap = DataConfigManager.getTsrForbidMap();
    private Map<Integer, AdasCcBean> mTsrRateLimitMap = DataConfigManager.getTsrRateLimitMap();
    private int mCurrentNGPType = 0;

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onAdasAEBVisible(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onAdasFCWVisible(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onBSDLeft(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onBSDRight(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onLightClearanceLamp(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onLightDRL(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onLightDippedHeadlight(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onLightFullBeamHeadlight(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onLightRearFogLamp(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    @Deprecated
    public void onLightSopLamp(boolean z) {
    }

    public AdasViewModel() {
        ClusterManager.getInstance().setIAdasListener(this);
        XpuInstrumentClient.getInstance().setSpeedLimitListener(this);
    }

    public MutableLiveData<Boolean> getSasLiveData() {
        return this.mSasLiveData;
    }

    public MutableLiveData<AdasCcBean> getNgpLiveData() {
        return this.mNgpLiveData;
    }

    public MutableLiveData<CcViewBean> getTsrRateLimitLiveData() {
        return this.mTsrRateLimitLiveData;
    }

    public MutableLiveData<Integer> getTsrForbidOrWarningLiveData() {
        return this.mTsrForbidOrWarningLiveData;
    }

    public MutableLiveData<Boolean> getHoldLiveData() {
        return this.mHoldLiveData;
    }

    public MutableLiveData<CcViewBean> getCcLiveData() {
        return this.mCcLiveData;
    }

    public MutableLiveData<Integer> getLccLiveData() {
        return this.mLccLiveData;
    }

    private void doCCState(Map<Integer, AdasCcBean> map, int i) {
        if (map == null) {
            XILog.d(TAG, "mAdasCCBeanMap is null");
            return;
        }
        AdasCcBean adasCcBean = map.get(Integer.valueOf(i));
        if (adasCcBean == null) {
            XILog.d(TAG, "adasCCBean is null");
            return;
        }
        this.mCcViewBean.setId(i);
        this.mCcViewBean.setResId(adasCcBean.getImgResId());
        int i2 = this.mAccSpeed;
        if (i2 == 0) {
            this.mCcViewBean.setSpeed("");
        } else {
            this.mCcViewBean.setSpeed(String.valueOf(i2));
        }
        this.mCcLiveData.postValue(this.mCcViewBean);
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onACCIsCC(boolean z) {
        this.mIsCc = z;
        CcViewBean ccViewBean = this.mCcViewBean;
        if (ccViewBean != null) {
            ccViewBean.setCc(z);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onACCSpeed(int i) {
        this.mAccSpeed = i;
        CcViewBean ccViewBean = this.mCcViewBean;
        if (ccViewBean != null) {
            ccViewBean.setSpeed(String.valueOf(i));
            this.mCcLiveData.postValue(this.mCcViewBean);
            return;
        }
        XILog.d(TAG, "mCcViewBean is null");
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onACCState(int i) {
        if (this.mIsCc) {
            doCCState(this.mAdasCCBeanMap, i);
        } else {
            doCCState(this.mAdasACCBeanMap, i);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onAdasLCCState(int i) {
        doLCCorNgp(this.mAdasLCCBeanMap, i, 2);
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onNGPIndicatorState(int i) {
        if (BaseConfig.getInstance().isSupportNaviSR() && this.mCurrentNGPType == 2 && i != 2) {
            doRateLimitByType(0);
        }
        this.mCurrentNGPType = i;
        doLCCorNgp(this.mAdasNGPBeanMap, i, 1);
    }

    private void doLCCorNgp(Map<Integer, AdasCcBean> map, int i, int i2) {
        if (map == null) {
            XILog.d(TAG, "map is null");
            return;
        }
        AdasCcBean adasCcBean = map.get(Integer.valueOf(i));
        if (adasCcBean == null) {
            XILog.d(TAG, "bean is null");
        } else if (i2 != 2) {
            if (i2 == 1) {
                this.mNgpLiveData.postValue(adasCcBean);
            }
        } else if (i == 8) {
            this.mLccLiveData.postValue(8);
        } else {
            this.mLccLiveData.postValue(Integer.valueOf(adasCcBean.getImgResId()));
        }
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onTSRForbid(int i) {
        doTsrForbidWaringIndicator(this.mTsrForbidBeanMap, i);
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onTSRWarning(int i) {
        doTsrForbidWaringIndicator(this.mTsrWarningBeanMap, i);
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onTSRRateLimitingType(int i) {
        this.mTSRRateLimitingType = i;
        controlLimitTypeByNGP(0, i);
    }

    private void doRateLimitByType(int i) {
        Map<Integer, AdasCcBean> map = this.mTsrRateLimitMap;
        if (map == null) {
            XILog.d(TAG, "onTSRRateLimitingType mTsrRateLimitMap  is null");
            return;
        }
        AdasCcBean adasCcBean = map.get(Integer.valueOf(i));
        if (adasCcBean == null) {
            XILog.d(TAG, " adas indicatorBean is null");
            return;
        }
        if (i == 2 && this.mTSRRateLimitingValue == 0) {
            this.mTsrRateLimitBean.setSpeed(null);
        } else {
            this.mTsrRateLimitBean.setSpeed(String.valueOf(this.mTSRRateLimitingValue));
        }
        this.mTsrRateLimitBean.setResId(adasCcBean.getImgResId());
        this.mTsrRateLimitLiveData.postValue(this.mTsrRateLimitBean);
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onTSRRateLimitingValue(int i) {
        distributeSign(i, 0);
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onSASState(int i) {
        this.mSasLiveData.postValue(Boolean.valueOf(i == 1));
    }

    private void doRateLimitByValue(int i) {
        if (i < 0) {
            XILog.d(TAG, "doRateLimitByValue: invalid value");
            return;
        }
        this.mTSRRateLimitingValue = i;
        if (this.mTSRRateLimitingType == 2 && i == 0) {
            this.mTsrRateLimitBean.setSpeed(null);
        } else {
            this.mTsrRateLimitBean.setSpeed(String.valueOf(i));
        }
        this.mTsrRateLimitLiveData.postValue(this.mTsrRateLimitBean);
    }

    private void doTsrForbidWaringIndicator(Map<Integer, AdasCcBean> map, int i) {
        if (map == null) {
            XILog.d(TAG, "doTsrForbidWaringIndicator map  is null");
            return;
        }
        AdasCcBean adasCcBean = map.get(Integer.valueOf(i));
        if (adasCcBean == null) {
            XILog.d(TAG, " adas adasCcBean is null");
        } else {
            this.mTsrForbidOrWarningLiveData.postValue(Integer.valueOf(adasCcBean.getImgResId()));
        }
    }

    @Override // com.xiaopeng.cluster.listener.IAdasListener
    public void onAdasHold(boolean z) {
        this.mHoldLiveData.postValue(Boolean.valueOf(z));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setIAdasListener(null);
        XpuInstrumentClient.getInstance().setSpeedLimitListener(null);
    }

    @Override // com.xiaopeng.instrument.manager.XpuInstrumentClient.ISpeedLimitListener
    public void onSpeedChange(int i) {
        distributeSign(i, 1);
    }

    private void distributeSign(int i, int i2) {
        if (i == 0) {
            XILog.i(TAG, "distributeSign: limitspeed value is 0,need hide");
            doRateLimitByType(0);
        } else if (BaseConfig.getInstance().isSupportNaviSR()) {
            int i3 = this.mCurrentNGPType;
            if (i3 == 2 && i2 == 1) {
                doRateLimitByValue(i);
                controlLimitTypeByNGP(i, 1);
            } else if (i3 != 2 && i2 == 0) {
                doRateLimitByValue(i);
            } else {
                XILog.d(TAG, "distributeSign: invalid sign");
            }
        } else {
            doRateLimitByValue(i);
        }
    }

    private void controlLimitTypeByNGP(int i, int i2) {
        if (i < 0) {
            XILog.d(TAG, "controlLimitTypeByNGP: invalid speed value");
        } else if (!BaseConfig.getInstance().isSupportNaviSR() || this.mCurrentNGPType != 2) {
            doRateLimitByType(i2);
        } else if (i == 0) {
            doRateLimitByType(0);
        } else {
            doRateLimitByType(1);
        }
    }
}
