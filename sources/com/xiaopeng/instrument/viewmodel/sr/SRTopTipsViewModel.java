package com.xiaopeng.instrument.viewmodel.sr;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.INaviSRListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import java.util.Map;
/* loaded from: classes.dex */
public class SRTopTipsViewModel extends ViewModel implements INaviSRListener {
    private static final int INVALID_VALUE = 0;
    private static final String TAG = "SRTopTipsViewModel";
    private Map<Integer, NGPTipsBean> mSRTopTipsBeanList;
    private final MutableLiveData<NGPTipsBean> mTopTipsData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mTipsView = new MutableLiveData<>(false);

    @Override // com.xiaopeng.cluster.listener.INaviSRListener
    public void onNaviSRMode(boolean z) {
    }

    @Override // com.xiaopeng.cluster.listener.INaviSRListener
    public void onNaviStart() {
    }

    public SRTopTipsViewModel() {
        initSignalListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removeINaviSRListener(this);
    }

    private void initSignalListener() {
        ClusterManager.getInstance().addINaviSRListener(this);
        this.mSRTopTipsBeanList = DataConfigManager.getSRTopTipsBeans();
    }

    public void onTopTipsChange(int i) {
        String str = TAG;
        XILog.d(str, "onBottomTipsChange id: " + i);
        if (i == 0) {
            this.mTopTipsData.setValue(null);
            this.mTipsView.postValue(false);
            return;
        }
        NGPTipsBean nGPTipsBean = this.mSRTopTipsBeanList.get(Integer.valueOf(i));
        if (nGPTipsBean != null) {
            this.mTopTipsData.setValue(nGPTipsBean);
            this.mTipsView.postValue(true);
            return;
        }
        XILog.d(str, "current tipsBean bean is not exist...");
    }

    public MutableLiveData<NGPTipsBean> getTopTipsData() {
        return this.mTopTipsData;
    }

    public MutableLiveData<Boolean> getTipsViewShow() {
        return this.mTipsView;
    }

    @Override // com.xiaopeng.cluster.listener.INaviSRListener
    public void onNaviSRTraffic(int i) {
        String str = TAG;
        XILog.d(str, "onNaviSRTraffic sign: " + i);
        if (i == 0) {
            this.mTopTipsData.setValue(null);
            this.mTipsView.postValue(false);
            return;
        }
        NGPTipsBean nGPTipsBean = this.mSRTopTipsBeanList.get(Integer.valueOf(i));
        if (nGPTipsBean != null) {
            this.mTopTipsData.setValue(nGPTipsBean);
            this.mTipsView.postValue(true);
            return;
        }
        XILog.d(str, "current tipsBean bean is not exist...");
    }
}
