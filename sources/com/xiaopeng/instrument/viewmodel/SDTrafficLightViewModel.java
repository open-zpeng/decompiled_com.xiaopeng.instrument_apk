package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
/* loaded from: classes.dex */
public class SDTrafficLightViewModel extends ViewModel {
    private static final String TAG = "SDTrafficLightViewModel";
    private ICommonListener mCommonListener;
    private final MutableLiveData<Integer> mTrafficLightData = new MutableLiveData<>();

    public SDTrafficLightViewModel() {
        initSignalListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removeCommonListener(this.mCommonListener);
    }

    private void initSignalListener() {
        this.mCommonListener = new ICommonListener() { // from class: com.xiaopeng.instrument.viewmodel.SDTrafficLightViewModel.1
            @Override // com.xiaopeng.cluster.listener.ICommonListener
            public void onTrafficLightsState(int i) {
                SDTrafficLightViewModel.this.mTrafficLightData.postValue(Integer.valueOf(i));
            }
        };
        ClusterManager.getInstance().addCommonListener(this.mCommonListener);
    }

    public MutableLiveData<Integer> getTrafficLightData() {
        return this.mTrafficLightData;
    }
}
