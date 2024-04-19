package com.xiaopeng.instrument.viewmodel.sr;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import java.util.Map;
/* loaded from: classes.dex */
public class SRBottomTipsViewModel extends ViewModel implements XpuInstrumentClient.IBottomTipsListener {
    private static final int INVALID_VALUE = -1;
    private static final String TAG = "SRBottomTipsViewModel";
    private final MutableLiveData<NGPTipsBean> mBottomTipsData = new MutableLiveData<>();
    private Map<Integer, NGPTipsBean> mSRBottomTipsBeanMap;

    public SRBottomTipsViewModel() {
        initSignalListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        XpuInstrumentClient.getInstance().removeBottomTipsListener(this);
    }

    private void initSignalListener() {
        this.mSRBottomTipsBeanMap = DataConfigManager.getSRBottomTipsBeans();
        XILog.d(TAG, "addBottomTipsListener");
        XpuInstrumentClient.getInstance().addBottomTipsListener(this);
    }

    public MutableLiveData<NGPTipsBean> getBottomTipsData() {
        return this.mBottomTipsData;
    }

    @Override // com.xiaopeng.instrument.manager.XpuInstrumentClient.IBottomTipsListener
    public void onBottomTipsChange(int i) {
        XILog.d(TAG, "onBottomTipsChange id: " + i);
        if (i == -1) {
            this.mBottomTipsData.setValue(null);
            return;
        }
        NGPTipsBean nGPTipsBean = this.mSRBottomTipsBeanMap.get(Integer.valueOf(i));
        if (nGPTipsBean != null) {
            this.mBottomTipsData.setValue(nGPTipsBean);
        }
    }
}
