package com.xiaopeng.instrument.viewmodel.sr;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
/* loaded from: classes.dex */
public class SRTrafficLightViewModel extends ViewModel implements XpuInstrumentClient.ITrafficLightListener {
    private static final String TAG = "SRTrafficLightViewModel";
    private final MutableLiveData<Integer> mTrafficLightData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mLightViewShow = new MutableLiveData<>();

    public SRTrafficLightViewModel() {
        initSignalListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        XpuInstrumentClient.getInstance().setTrafficLightListener(null);
    }

    private void initSignalListener() {
        XpuInstrumentClient.getInstance().setTrafficLightListener(this);
    }

    public MutableLiveData<Integer> getTrafficLightData() {
        return this.mTrafficLightData;
    }

    public MutableLiveData<Boolean> getLightViewShow() {
        return this.mLightViewShow;
    }

    @Override // com.xiaopeng.instrument.manager.XpuInstrumentClient.ITrafficLightListener
    public void onTrafficLightChange(int i) {
        XILog.d(TAG, "onTrafficLightChange sign: " + i);
        if (i == 0) {
            this.mLightViewShow.postValue(false);
        } else {
            this.mLightViewShow.postValue(true);
        }
        this.mTrafficLightData.postValue(Integer.valueOf(i));
    }
}
