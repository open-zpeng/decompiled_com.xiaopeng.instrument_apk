package com.xiaopeng.instrument.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IOsdCallListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.utils.CommonUtil;
import com.xiaopeng.instrument.utils.StringUtil;
/* loaded from: classes.dex */
public class OsdCallViewModel extends ViewModel implements IOsdCallListener {
    private static final String TAG = "OsdCallViewModel";
    private boolean mCallVisible;
    private final MutableLiveData<Boolean> mCallVisibleLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mAnswerOrRejectLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mCallingContentLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mCallStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mNameLiveData = new MutableLiveData<>();
    private final int CONTENT_MIN_LENGTH = 0;
    private int mCallState = 1;
    private StringBuilder mOsdContent = new StringBuilder();

    public OsdCallViewModel() {
        ClusterManager.getInstance().setCallListener(this);
    }

    public MutableLiveData<String> getCallingContentLiveData() {
        return this.mCallingContentLiveData;
    }

    public MutableLiveData<Integer> getCallStateLiveData() {
        return this.mCallStateLiveData;
    }

    public MutableLiveData<Boolean> getAnswerOrRejectLiveData() {
        return this.mAnswerOrRejectLiveData;
    }

    public MutableLiveData<Boolean> getCallVisibleLiveData() {
        return this.mCallVisibleLiveData;
    }

    public MutableLiveData<String> getNameLiveData() {
        return this.mNameLiveData;
    }

    @Override // com.xiaopeng.cluster.listener.IOsdCallListener
    public void onCallState(int i) {
        this.mCallState = i;
        this.mOsdContent.setLength(0);
        if (i == 1) {
            this.mOsdContent.append(App.getInstance().getString(R.string.osd_call_incoming_content));
        } else if (i == 2) {
            this.mOsdContent.append(App.getInstance().getString(R.string.osd_call_connect_content));
        } else {
            this.mOsdContent.append(App.getInstance().getString(R.string.osd_call_incoming_content));
        }
        this.mCallingContentLiveData.postValue(this.mOsdContent.toString());
        this.mCallStateLiveData.postValue(Integer.valueOf(i));
    }

    @Override // com.xiaopeng.cluster.listener.IOsdCallListener
    public void onCallVisible(boolean z) {
        if (CommonUtil.isEqual(this.mCallVisible, z)) {
            XILog.d(TAG, "onCallVisible value is equal ");
            return;
        }
        this.mCallVisible = z;
        this.mCallVisibleLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IOsdCallListener
    public void onCallerID(String str) {
        String formatPhoneNumberByCountry = CommonUtil.formatPhoneNumberByCountry(str);
        if (!TextUtils.isEmpty(formatPhoneNumberByCountry)) {
            str = formatPhoneNumberByCountry;
        }
        this.mNameLiveData.postValue(str);
    }

    @Override // com.xiaopeng.cluster.listener.IOsdCallListener
    public void onCallTime(String str) {
        String str2;
        if (TextUtils.isEmpty(str)) {
            XILog.d(TAG, "call time is null");
            return;
        }
        this.mOsdContent.setLength(0);
        if (this.mCallState == 2) {
            this.mOsdContent.append(App.getInstance().getString(R.string.osd_call_connect_content));
            try {
                str2 = StringUtil.stringForTime(Integer.parseInt(str));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                str2 = "";
            }
            this.mOsdContent.append(str2);
            this.mCallingContentLiveData.postValue(this.mOsdContent.toString());
        }
    }

    @Override // com.xiaopeng.cluster.listener.IOsdCallListener
    public void onOverlayBTPhoneButtonState(int i) {
        this.mAnswerOrRejectLiveData.postValue(Boolean.valueOf(i != 0));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setCallListener(null);
    }
}
