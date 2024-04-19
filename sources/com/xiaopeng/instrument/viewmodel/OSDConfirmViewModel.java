package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IOSDConfirmListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.OSDConfirmBean;
import com.xiaopeng.instrument.utils.CommonUtil;
/* loaded from: classes.dex */
public class OSDConfirmViewModel extends ViewModel implements IOSDConfirmListener {
    private static final int OSD_CONFIRM_STATE = 1;
    private static final String TAG = "OSDConfirmViewModel";
    private boolean isShowForcedPowerDown;
    private boolean isShowPreventNGearByMistake;
    private final OSDConfirmBean mNGearConfirm = OSDConfirmBean.createNGearMistakeConfirm();
    private final OSDConfirmBean mPowerOffConfirm = OSDConfirmBean.createForcePowerOffConfirm();
    private MutableLiveData<OSDConfirmBean> mOSDConfirmLiveData = new MutableLiveData<>();
    private MutableLiveData<OSDConfirmBean> mOSDConfirmUpdateLiveData = new MutableLiveData<>();

    public OSDConfirmViewModel() {
        ClusterManager.getInstance().setOSDConfirmListener(this);
    }

    public MutableLiveData<OSDConfirmBean> getOSDConfirmLiveData() {
        return this.mOSDConfirmLiveData;
    }

    public MutableLiveData<OSDConfirmBean> getOSDConfirmUpdateLiveData() {
        return this.mOSDConfirmUpdateLiveData;
    }

    @Override // com.xiaopeng.cluster.listener.IOSDConfirmListener
    public void onPreventNGearByMistakeVisible(boolean z) {
        if (CommonUtil.isEqual(this.isShowPreventNGearByMistake, z)) {
            XILog.d(TAG, "isShowPreventNGearByMistake is equal");
            return;
        }
        this.isShowPreventNGearByMistake = z;
        this.mNGearConfirm.setShow(z);
        this.mOSDConfirmLiveData.postValue(this.mNGearConfirm);
        if (z) {
            return;
        }
        this.mNGearConfirm.setCounterTime(5);
    }

    @Override // com.xiaopeng.cluster.listener.IOSDConfirmListener
    public void onPreventNGearByMistakeTime(int i) {
        this.mNGearConfirm.setCounterTime(i);
        this.mOSDConfirmUpdateLiveData.postValue(this.mNGearConfirm);
    }

    @Override // com.xiaopeng.cluster.listener.IOSDConfirmListener
    public void onForcedPowerDownVisible(boolean z) {
        if (CommonUtil.isEqual(this.isShowForcedPowerDown, z)) {
            XILog.d(TAG, "isShowForcedPowerDown is equal");
            return;
        }
        this.isShowForcedPowerDown = z;
        this.mPowerOffConfirm.setShow(z);
        this.mOSDConfirmLiveData.postValue(this.mPowerOffConfirm);
    }

    @Override // com.xiaopeng.cluster.listener.IOSDConfirmListener
    public void onForcedPowerDownState(int i) {
        this.mPowerOffConfirm.setConfirm(i == 1);
        this.mOSDConfirmUpdateLiveData.postValue(this.mPowerOffConfirm);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setOSDConfirmListener(null);
    }
}
