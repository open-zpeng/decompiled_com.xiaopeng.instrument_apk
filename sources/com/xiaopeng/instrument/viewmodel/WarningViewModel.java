package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.bean.WaringBean;
import com.xiaopeng.instrument.bean.WarningType;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class WarningViewModel extends ViewModel implements ICommonListener, XpuInstrumentClient.IBottomTipsListener {
    private static final String TAG = "WarningViewModel";
    private WaringBean mCurrentBean;
    private Map<Integer, WaringBean> mWaringBeanMap = new ConcurrentHashMap();
    private Map<Integer, NGPTipsBean> mSRBottomTipsBeanMap = new ConcurrentHashMap();
    private MutableLiveData<WaringBean> mGenericWaringBeanMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WaringBean> mAutoDriveWaringBeanMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<WaringBean> mSRBottomTipsWarningMutableLiveData = new MutableLiveData<>();

    public WarningViewModel() {
        XILog.d(TAG, "addBottomTipsListener");
        ClusterManager.getInstance().addCommonListener(this);
        XpuInstrumentClient.getInstance().addBottomTipsListener(this);
        initWaringData();
    }

    public MutableLiveData<WaringBean> getGenericWaringBeanMutableLiveData() {
        return this.mGenericWaringBeanMutableLiveData;
    }

    public MutableLiveData<WaringBean> getAutoDriveWaringBeanMutableLiveData() {
        return this.mAutoDriveWaringBeanMutableLiveData;
    }

    public MutableLiveData<WaringBean> getSRBottomTipsWarningMutableLiveData() {
        return this.mSRBottomTipsWarningMutableLiveData;
    }

    private void initWaringData() {
        this.mWaringBeanMap = DataConfigManager.getWaringBeanMap();
        this.mSRBottomTipsBeanMap = DataConfigManager.getSRBottomTipsBeans();
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onTriggerAutoState(int i) {
        Map<Integer, WaringBean> map = this.mWaringBeanMap;
        if (map == null) {
            XILog.d(TAG, "onTriggerAutoState mWaringBeanMap is null");
            return;
        }
        WaringBean waringBean = map.get(Integer.valueOf(i));
        if (waringBean == null) {
            XILog.d(TAG, "onTriggerAutoState current warning bean is not exist...");
        } else {
            this.mAutoDriveWaringBeanMutableLiveData.setValue(waringBean);
        }
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onTriggerState(int i) {
        Map<Integer, WaringBean> map = this.mWaringBeanMap;
        if (map == null) {
            XILog.d(TAG, "onTriggerState mWaringBeanMap is null");
            return;
        }
        WaringBean waringBean = map.get(Integer.valueOf(i));
        if (waringBean == null) {
            XILog.d(TAG, "onTriggerState current warning bean is not exist...");
        } else {
            this.mGenericWaringBeanMutableLiveData.setValue(waringBean);
        }
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
        Map<Integer, NGPTipsBean> map = this.mSRBottomTipsBeanMap;
        if (map == null) {
            XILog.d(TAG, "onTriggerAutoState mSRBottomTipsBeanMap is null");
            return;
        }
        updateDangerBean(map.get(Integer.valueOf(i)));
        this.mSRBottomTipsWarningMutableLiveData.setValue(this.mCurrentBean);
    }

    private void updateDangerBean(NGPTipsBean nGPTipsBean) {
        XILog.d(TAG, "updateDangerBean tipsBean: " + nGPTipsBean);
        if (nGPTipsBean == null || nGPTipsBean.getEmergencyLevel() != 2) {
            this.mCurrentBean = null;
            return;
        }
        if (this.mCurrentBean == null) {
            this.mCurrentBean = new WaringBean();
        }
        this.mCurrentBean.setId(nGPTipsBean.getId());
        this.mCurrentBean.setEmergencyLevel(nGPTipsBean.getEmergencyLevel());
        this.mCurrentBean.setImgResName(nGPTipsBean.getImgResName());
        this.mCurrentBean.setImgResId(nGPTipsBean.getImgResId());
        this.mCurrentBean.setType(WarningType.SR_BOTTOM_TIPS);
        this.mCurrentBean.setShow(true);
        this.mCurrentBean.setText(nGPTipsBean.getText());
    }
}
