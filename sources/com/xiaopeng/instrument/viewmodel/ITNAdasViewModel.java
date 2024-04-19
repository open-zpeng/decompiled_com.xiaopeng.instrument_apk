package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IITNAdasListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.AdasCcBean;
import com.xiaopeng.instrument.manager.ITNDataConfigManager;
import java.util.Map;
/* loaded from: classes.dex */
public class ITNAdasViewModel extends AdasViewModel implements IITNAdasListener {
    private static final String TAG = "ITNAdasViewModel";
    private final MutableLiveData<Integer> mITNLccLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mITNAlcLiveData = new MutableLiveData<>();
    private Map<Integer, AdasCcBean> mAdasLCCFailureBeanMap = ITNDataConfigManager.getAdasLCCFailureBeanMap();
    private Map<Integer, AdasCcBean> mAdasALCBeanMap = ITNDataConfigManager.getAdasALCBeanMap();

    public ITNAdasViewModel() {
        ClusterManager.getInstance().setIITNAdasListener(this);
    }

    public MutableLiveData<Integer> getITNAlcLiveData() {
        return this.mITNAlcLiveData;
    }

    public MutableLiveData<Integer> getITNLccLiveData() {
        return this.mITNLccLiveData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.viewmodel.AdasViewModel, androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setIITNAdasListener(null);
    }

    @Override // com.xiaopeng.cluster.listener.IITNAdasListener
    public void onLCCFailureState(int i) {
        Map<Integer, AdasCcBean> map = this.mAdasLCCFailureBeanMap;
        if (map == null) {
            XILog.d(TAG, "mAdasLCCFailureBeanMap is null");
            return;
        }
        AdasCcBean adasCcBean = map.get(Integer.valueOf(i));
        if (adasCcBean == null) {
            XILog.d(TAG, "adasLCcBean is null");
        } else {
            this.mITNLccLiveData.postValue(Integer.valueOf(adasCcBean.getImgResId()));
        }
    }

    @Override // com.xiaopeng.cluster.listener.IITNAdasListener
    public void onALCState(int i) {
        Map<Integer, AdasCcBean> map = this.mAdasALCBeanMap;
        if (map == null) {
            XILog.d(TAG, "mAdasALCBeanMap is null");
            return;
        }
        AdasCcBean adasCcBean = map.get(Integer.valueOf(i));
        if (adasCcBean == null) {
            XILog.d(TAG, "adasALCBean is null");
        } else {
            this.mITNAlcLiveData.postValue(Integer.valueOf(adasCcBean.getImgResId()));
        }
    }
}
