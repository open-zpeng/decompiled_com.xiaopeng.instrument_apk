package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IOsdMediaVolumeListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.utils.CommonUtil;
/* loaded from: classes.dex */
public class OsdMediaVolumeViewModel extends ViewModel implements IOsdMediaVolumeListener {
    private static final float INIT_VOLUME = 0.0f;
    private static final int MAX_VOLUME = 30;
    private static final int MIN_VOLUME = 0;
    private static String TAG = "OsdMediaVolumeViewModel";
    private boolean isSetSilence;
    private boolean isShowOsdMedia;
    private final MutableLiveData<Float> mMediaVolume = new MutableLiveData<>(Float.valueOf(0.0f));
    private final MutableLiveData<Boolean> mIsMediaMute = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> mIsMediaVolumeVisible = new MutableLiveData<>(false);
    private final MutableLiveData<Integer> mCallMode = new MutableLiveData<>(0);

    public OsdMediaVolumeViewModel() {
        ClusterManager.getInstance().setOsdMediaVolumeListener(this);
    }

    public MutableLiveData<Float> getMediaVolume() {
        return this.mMediaVolume;
    }

    public MutableLiveData<Boolean> getIsMediaMute() {
        return this.mIsMediaMute;
    }

    public MutableLiveData<Boolean> getIsMediaVolumeVisible() {
        return this.mIsMediaVolumeVisible;
    }

    public MutableLiveData<Integer> getCallMode() {
        return this.mCallMode;
    }

    @Override // com.xiaopeng.cluster.listener.IOsdMediaVolumeListener
    public void onMidVolume(float f) {
        if (f > 30.0f || f < 0.0f) {
            XILog.d(TAG, "mid volume  value invalid ");
        } else {
            this.mMediaVolume.setValue(Float.valueOf(f));
        }
    }

    @Override // com.xiaopeng.cluster.listener.IOsdMediaVolumeListener
    public void onMidVolumeSilence(boolean z) {
        if (CommonUtil.isEqual(this.isSetSilence, z)) {
            XILog.d(TAG, "onMidVolumeSilence is equal");
            return;
        }
        this.isSetSilence = z;
        this.mIsMediaMute.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IOsdMediaVolumeListener
    public void onMidVoiceVisible(boolean z) {
        if (CommonUtil.isEqual(this.isShowOsdMedia, z)) {
            XILog.d(TAG, "onMidVoiceVisible is equal");
            return;
        }
        this.isShowOsdMedia = z;
        this.mIsMediaVolumeVisible.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IOsdMediaVolumeListener
    public void onOverlayVoiceInfoState(int i) {
        this.mCallMode.setValue(Integer.valueOf(i));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setOsdMediaVolumeListener(null);
    }
}
