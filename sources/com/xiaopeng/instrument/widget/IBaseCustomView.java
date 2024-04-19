package com.xiaopeng.instrument.widget;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
/* loaded from: classes.dex */
public interface IBaseCustomView {
    <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer);
}
