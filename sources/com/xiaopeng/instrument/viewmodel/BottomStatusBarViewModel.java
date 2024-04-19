package com.xiaopeng.instrument.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
/* loaded from: classes.dex */
public class BottomStatusBarViewModel extends BaseBottomStatusBarViewModel {
    private final MutableLiveData<String> mTimeData = new MutableLiveData<>("00:00");
    private final MutableLiveData<Integer> mTimePatternData = new MutableLiveData<>(1);
    private final MutableLiveData<Integer> mTimeMorningOrAfternoonData = new MutableLiveData<>(0);

    @Override // com.xiaopeng.cluster.listener.INormalInfoListener
    public void onTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mTimeData.postValue(str);
    }

    @Override // com.xiaopeng.cluster.listener.INormalInfoListener
    public void onTimePattern(int i) {
        if (i == 1 || i == 0) {
            this.mTimePatternData.postValue(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.listener.INormalInfoListener
    public void onMorningOrAfternoon(int i) {
        if (i == 1 || i == 0) {
            this.mTimeMorningOrAfternoonData.postValue(Integer.valueOf(i));
        }
    }

    public MutableLiveData<String> getTimeData() {
        return this.mTimeData;
    }

    public MutableLiveData<Integer> getTimePatternData() {
        return this.mTimePatternData;
    }

    public MutableLiveData<Integer> getTimeMorningOrAfternoonData() {
        return this.mTimeMorningOrAfternoonData;
    }
}
