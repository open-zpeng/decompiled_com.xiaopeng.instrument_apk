package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IPGearProtectListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
/* loaded from: classes.dex */
public class OsdPGearProtectViewModel extends ViewModel implements IPGearProtectListener {
    private static final int CONTENT_MIN_LENGTH = 0;
    private static final String TAG = "OsdPGearProtectViewModel";
    private static final int TIME_MAX = 5;
    private StringBuilder mOsdContent = new StringBuilder();
    private static final MutableLiveData<Integer> mStateLiveData = new MutableLiveData<>();
    private static final MutableLiveData<String> mConfirmContentLiveData = new MutableLiveData<>();

    public OsdPGearProtectViewModel() {
        ClusterManager.getInstance().setPGearProtectListener(this);
    }

    public MutableLiveData<Integer> getStateLiveData() {
        return mStateLiveData;
    }

    public MutableLiveData<String> getConfirmContentLiveData() {
        return mConfirmContentLiveData;
    }

    @Override // com.xiaopeng.cluster.listener.IPGearProtectListener
    public void onPGearSafetyProtectTime(int i) {
        if (i < 0 || i > 5) {
            XILog.d(TAG, "p gear protect time is error : " + i);
            return;
        }
        this.mOsdContent.setLength(0);
        String string = App.getInstance().getString(R.string.p_gear_protect_time, new Object[]{String.valueOf(i)});
        this.mOsdContent.append(App.getInstance().getString(R.string.p_gear_protect_confirm_text));
        if (i != 0) {
            this.mOsdContent.append(string);
        }
        mConfirmContentLiveData.setValue(this.mOsdContent.toString());
    }

    @Override // com.xiaopeng.cluster.listener.IPGearProtectListener
    public void onPGearSafetyProtectState(int i) {
        if (i < 0 || i > 2) {
            XILog.d(TAG, "p gear protect state is error : " + i);
        } else {
            mStateLiveData.setValue(Integer.valueOf(i));
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setPGearProtectListener(null);
    }
}
