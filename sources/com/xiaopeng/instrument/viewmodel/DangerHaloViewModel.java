package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.bean.WaringBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import java.util.Map;
/* loaded from: classes.dex */
public class DangerHaloViewModel extends ViewModel implements ICommonListener, XpuInstrumentClient.IBottomTipsListener {
    public static final int DANGER_FORTH = 2;
    public static final int DANGER_HIDE = 0;
    public static final int DANGER_THIRD = 1;
    private static final int DEFAULT_INVALID_INDEX = -1;
    private static final String TAG = "DangerHaloViewModel";
    private WaringBean mAutoDriveWarningBean;
    private WaringBean mBottomTipsWarningBean;
    private MutableLiveData<Integer> mDangerHaloLiveData = new MutableLiveData<>();
    private WaringBean mGenericWarningBean;
    private Map<Integer, NGPTipsBean> mSRBottomTipsBeanMap;
    private Map<Integer, WaringBean> mWaringBeanMap;

    public DangerHaloViewModel() {
        ClusterManager.getInstance().addCommonListener(this);
        XILog.d(TAG, "addBottomTipsListener");
        XpuInstrumentClient.getInstance().addBottomTipsListener(this);
        this.mWaringBeanMap = DataConfigManager.getWaringBeanMap();
        this.mSRBottomTipsBeanMap = DataConfigManager.getSRBottomTipsBeans();
    }

    public MutableLiveData<Integer> getDangerHaloLiveData() {
        return this.mDangerHaloLiveData;
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onTriggerAutoState(int i) {
        if (-1 == i && !isHaloViewHoldByGenericToast()) {
            this.mDangerHaloLiveData.postValue(0);
            this.mAutoDriveWarningBean = null;
            return;
        }
        Map<Integer, WaringBean> map = this.mWaringBeanMap;
        if (map == null) {
            XILog.d(TAG, "doDangerHalo mWaringBeanMap is null");
            return;
        }
        WaringBean waringBean = map.get(Integer.valueOf(i));
        this.mAutoDriveWarningBean = waringBean;
        if (waringBean != null) {
            if (isHaloViewHoldByGenericToast()) {
                return;
            }
            this.mDangerHaloLiveData.postValue(Integer.valueOf(this.mAutoDriveWarningBean.getEmergencyLevel() == 2 ? 2 : 0));
            return;
        }
        XILog.d(TAG, "current warning bean is not exist...");
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onTriggerState(int i) {
        if (-1 == i && !isHaloViewHoldByAutoDriveToast()) {
            this.mDangerHaloLiveData.postValue(0);
            this.mGenericWarningBean = null;
            return;
        }
        Map<Integer, WaringBean> map = this.mWaringBeanMap;
        if (map == null) {
            XILog.d(TAG, "doDangerHalo mWaringBeanMap is null");
            return;
        }
        WaringBean waringBean = map.get(Integer.valueOf(i));
        this.mGenericWarningBean = waringBean;
        if (waringBean != null) {
            if (isHaloViewHoldByAutoDriveToast()) {
                return;
            }
            this.mDangerHaloLiveData.postValue(Integer.valueOf(this.mGenericWarningBean.getEmergencyLevel() == 2 ? 2 : 0));
            return;
        }
        XILog.d(TAG, "current warning bean is not exist...");
    }

    private boolean isHaloViewHoldByAutoDriveToast() {
        return isOtherScenePrior(this.mAutoDriveWarningBean, this.mBottomTipsWarningBean);
    }

    private boolean isHaloViewHoldByGenericToast() {
        return isOtherScenePrior(this.mGenericWarningBean, this.mBottomTipsWarningBean);
    }

    private boolean isOtherScenePrior(WaringBean waringBean, WaringBean waringBean2) {
        return (waringBean != null && waringBean.getEmergencyLevel() == 2) || (waringBean2 != null && (waringBean2.getEmergencyLevel() == 2 || waringBean2.getEmergencyLevel() == 0));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removeCommonListener(this);
        XpuInstrumentClient.getInstance().removeBottomTipsListener(this);
    }

    @Override // com.xiaopeng.instrument.manager.XpuInstrumentClient.IBottomTipsListener
    public void onBottomTipsChange(int i) {
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            NGPTipsBean nGPTipsBean = this.mSRBottomTipsBeanMap.get(Integer.valueOf(i));
            XILog.d(TAG, "onBottomTipsChange warningBean: " + nGPTipsBean);
            if (nGPTipsBean != null) {
                if (nGPTipsBean.getEmergencyLevel() == 0) {
                    this.mDangerHaloLiveData.postValue(1);
                } else if (nGPTipsBean.getEmergencyLevel() == 2) {
                    this.mDangerHaloLiveData.postValue(2);
                } else {
                    this.mDangerHaloLiveData.postValue(0);
                }
                if (this.mBottomTipsWarningBean == null) {
                    this.mBottomTipsWarningBean = new WaringBean();
                }
                this.mBottomTipsWarningBean.setEmergencyLevel(nGPTipsBean.getEmergencyLevel());
                return;
            }
            this.mDangerHaloLiveData.postValue(0);
            this.mBottomTipsWarningBean = null;
        }
    }
}
