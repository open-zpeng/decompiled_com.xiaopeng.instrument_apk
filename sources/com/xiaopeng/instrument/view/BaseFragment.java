package com.xiaopeng.instrument.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class BaseFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();

    @Override // androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        XILog.i(this.TAG, "onCreate...");
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        XILog.i(this.TAG, "onCreateView...");
        return super.onCreateView(layoutInflater, viewGroup, bundle);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        XILog.i(this.TAG, "onViewCreated...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStart() {
        super.onStart();
        XILog.i(this.TAG, "onStart...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        XILog.i(this.TAG, "onResume...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onPause() {
        super.onPause();
        XILog.i(this.TAG, "onPause...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onStop() {
        super.onStop();
        XILog.i(this.TAG, "onStop...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        XILog.i(this.TAG, "onDestroyView...");
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        XILog.i(this.TAG, "onDestroy...");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        liveData.observe(this, observer);
    }
}
