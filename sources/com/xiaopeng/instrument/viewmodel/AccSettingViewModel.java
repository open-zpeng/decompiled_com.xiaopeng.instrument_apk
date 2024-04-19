package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IAccSettingListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.AccSettingBean;
/* loaded from: classes.dex */
public class AccSettingViewModel extends ViewModel implements IAccSettingListener {
    private static final String TAG = "AccSettingViewModel";
    private final AccSettingBean accSettingBean = new AccSettingBean();
    public MutableLiveData<AccSettingBean> mAccSettingLiveData = new MutableLiveData<>();
    private int MAX_DIS_VALUE = 1;
    private int MIN_DIS_VALUE = 3;

    public AccSettingViewModel() {
        ClusterManager.getInstance().setAccSettingListener(this);
    }

    public MutableLiveData<AccSettingBean> getAccSettingLiveData() {
        return this.mAccSettingLiveData;
    }

    @Override // com.xiaopeng.cluster.listener.IAccSettingListener
    public void onACCDistanceAdjust(int i) {
        if (i < this.MAX_DIS_VALUE || i > this.MIN_DIS_VALUE) {
            XILog.d(TAG, "setDistance invalid... distance: " + i);
            return;
        }
        this.accSettingBean.setDistance(i);
        this.mAccSettingLiveData.postValue(this.accSettingBean);
    }

    @Override // com.xiaopeng.cluster.listener.IAccSettingListener
    public void onACCSpeedAdjust(int i) {
        this.accSettingBean.setSpeed(i);
        this.mAccSettingLiveData.postValue(this.accSettingBean);
    }

    @Override // com.xiaopeng.cluster.listener.IAccSettingListener
    public void onACCOperationGuide(boolean z) {
        onAccVisible(1, z);
    }

    @Override // com.xiaopeng.cluster.listener.IAccSettingListener
    public void onACCDistanceAdjustVisible(boolean z) {
        onAccVisible(3, z);
    }

    @Override // com.xiaopeng.cluster.listener.IAccSettingListener
    public void onACCDistanceAutoVisible(boolean z) {
        onAccVisible(4, z);
    }

    @Override // com.xiaopeng.cluster.listener.IAccSettingListener
    public void onACCSpeedAdjustVisible(boolean z) {
        onAccVisible(2, z);
    }

    private synchronized void onAccVisible(int i, boolean z) {
        if (!isUpdateAcc(i, z)) {
            XILog.d(TAG, "acc界面不更新，currMode = " + i);
            return;
        }
        this.accSettingBean.setMode(i);
        this.accSettingBean.setVisibility(z);
        this.mAccSettingLiveData.postValue(this.accSettingBean);
    }

    private boolean isUpdateAcc(int i, boolean z) {
        int mode = this.accSettingBean.getMode();
        return (z && !(i == mode && this.accSettingBean.isVisibility())) || (!z && i == mode);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setAccSettingListener(null);
    }
}
