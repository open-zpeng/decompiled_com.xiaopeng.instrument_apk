package com.xiaopeng.instrument.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IMusicListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.utils.CommonUtil;
import com.xiaopeng.instrument.utils.StringUtil;
/* loaded from: classes.dex */
public class MediaViewModel extends ViewModel implements IMusicListener {
    private static final long INTERVAL_TIME = 200;
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;
    private boolean mHasMusicBar;
    private final String TAG = getClass().getSimpleName();
    int mMediaType = 4;
    private MutableLiveData<Integer> mDefaultImageMutableLiveData = new MutableLiveData<>();
    private Handler mHandler = new Handler() { // from class: com.xiaopeng.instrument.viewmodel.MediaViewModel.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            if (message.what != 1) {
                return;
            }
            MediaViewModel.this.mDefaultImageMutableLiveData.setValue(Integer.valueOf(message.arg1));
        }
    };
    private MutableLiveData<Boolean> mPlayStateMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> mProgressBarMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mSignerNameMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<String> mMediaNameMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> mBarValueMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Bitmap> mBitmapMutableLiveData = new MutableLiveData<>();

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    @Deprecated
    public void onMusicImageState(boolean z) {
    }

    public MediaViewModel() {
        ClusterManager.getInstance().addIMusicListener(this);
    }

    public MutableLiveData<Bitmap> getBitmapMutableLiveData() {
        return this.mBitmapMutableLiveData;
    }

    public MutableLiveData<Integer> getDefaultImageMutableLiveData() {
        return this.mDefaultImageMutableLiveData;
    }

    public MutableLiveData<Boolean> getPlayStateMutableLiveData() {
        return this.mPlayStateMutableLiveData;
    }

    public MutableLiveData<Boolean> getProgressBarMutableLiveData() {
        return this.mProgressBarMutableLiveData;
    }

    public MutableLiveData<String> getSignerNameMutableLiveData() {
        return this.mSignerNameMutableLiveData;
    }

    public MutableLiveData<String> getMediaNameMutableLiveData() {
        return this.mMediaNameMutableLiveData;
    }

    public MutableLiveData<Integer> getBarValueMutableLiveData() {
        return this.mBarValueMutableLiveData;
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onMusicPlayState(boolean z) {
        this.mPlayStateMutableLiveData.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onMusicBarVisible(boolean z) {
        if (CommonUtil.isEqual(this.mHasMusicBar, z)) {
            return;
        }
        this.mHasMusicBar = z;
        this.mProgressBarMutableLiveData.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onMusicSoundState(int i) {
        this.mMediaType = i;
        doDefaultImage();
    }

    private void doDefaultImage() {
        removeMessage(1);
        Message obtain = Message.obtain();
        obtain.what = 1;
        obtain.arg1 = this.mMediaType;
        this.mHandler.sendMessageDelayed(obtain, INTERVAL_TIME);
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onMusicName(String str) {
        this.mMediaNameMutableLiveData.setValue(StringUtil.toTrim(str));
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onSignerName(String str) {
        this.mSignerNameMutableLiveData.setValue(StringUtil.toTrim(str));
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onMusicBarValue(int i) {
        if (i < 0 || i > 100) {
            XILog.e(this.TAG, "progress error! please check, current progress: " + i);
        } else {
            this.mBarValueMutableLiveData.setValue(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.listener.IMusicListener
    public void onRefreshImageMusicTexture(byte[] bArr, int i, int i2, int i3) {
        if (bArr != null) {
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
            if (decodeByteArray != null) {
                removeMessage(1);
                this.mBitmapMutableLiveData.setValue(decodeByteArray);
                return;
            }
            XILog.d(this.TAG, "image is null");
        }
    }

    private void removeMessage(int i) {
        Handler handler = this.mHandler;
        if (handler == null || !handler.hasMessages(i)) {
            return;
        }
        this.mHandler.removeMessages(i);
    }

    private void removeAllMessage() {
        Handler handler = this.mHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        removeAllMessage();
        ClusterManager.getInstance().removeIMusicListener(this);
    }
}
