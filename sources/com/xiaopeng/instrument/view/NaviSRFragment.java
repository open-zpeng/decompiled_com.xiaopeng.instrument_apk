package com.xiaopeng.instrument.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.InfoContainBean;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
import com.xiaopeng.instrument.viewmodel.sr.SRInfoViewModel;
import com.xiaopeng.instrument.widget.NaviSurfaceView;
import com.xiaopeng.instrument.widget.sr.SRLeftInfoViewGroup;
import com.xiaopeng.instrument.widget.sr.SRRightInfoViewGroup;
/* loaded from: classes.dex */
public class NaviSRFragment extends BaseFragment {
    private static final String TAG = "NaviSRFragment";
    private SRInfoViewModel mInfoViewModel;
    private SRLeftInfoViewGroup mLeftInfoViewGroup;
    private SRRightInfoViewGroup mRightInfoViewGroup;
    private NaviSurfaceView mSrSurfaceView;

    private void initClusterInterface() {
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.fragment_sr, viewGroup, false);
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        initView(view);
        initViewModel();
        initObservers();
        initClusterInterface();
    }

    @Override // com.xiaopeng.instrument.view.BaseFragment, androidx.fragment.app.Fragment
    public void onResume() {
        super.onResume();
        XILog.d(TAG, "sr_navi_onResume: " + SurfaceViewManager.getInstance().getSRSurface());
        SurfaceViewManager.getInstance().setSRSurface(this.mSrSurfaceView.getSurface());
        SurfaceViewManager.getInstance().startSRChangeService();
    }

    protected void initView(View view) {
        this.mSrSurfaceView = (NaviSurfaceView) view.findViewById(R.id.sr_map);
        this.mLeftInfoViewGroup = (SRLeftInfoViewGroup) view.findViewById(R.id.main_left_info);
        this.mRightInfoViewGroup = (SRRightInfoViewGroup) view.findViewById(R.id.main_right_info);
    }

    protected void initViewModel() {
        FragmentActivity activity = getActivity();
        if (activity == null) {
            XILog.d(TAG, "initViewModel: activity is null");
        } else {
            this.mInfoViewModel = (SRInfoViewModel) new ViewModelProvider(activity).get(SRInfoViewModel.class);
        }
    }

    private void initObservers() {
        setLiveDataObserver(this.mInfoViewModel.getLeftSubCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$jgtK3X3qR9n6j7BqRYYGHZsHzZI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.showLeftSubCardView(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightSubCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$J195sD3F4mzPjzfCZtobSdXxb6Y
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.showSubRightCardView(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$SZRPKZ_dTxdswZLvs2zNV9HeX0w
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.showLeftCardView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightCardLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$0tNThU2sQEN6niPwO9u1f7Vyazs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.showRightCardView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftListIndexLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$IQgJiJ67OPBt3TZDOVgjAdA7bmM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.updateLeftListHighPosition(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftListLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$mc3dyoRuUdzAN-FDEOZplCZbKWo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.updateLeftListData((InfoContainBean) obj);
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getLeftListInfoLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$vBTUevhqhxCGd5u52pxTwyZVnBc
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.showLeftListView(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightListIndexLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$1bFaz-zRl0QPRkBT79gewmDFwvM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.updateRightListHighPosition(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightListLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$u1ra-wVYhd38l1QoONHz8f5N_5Q
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.updateRightListData((InfoContainBean) obj);
            }
        });
        setLiveDataObserver(this.mInfoViewModel.getRightListInfoLiveData(), new Observer() { // from class: com.xiaopeng.instrument.view.-$$Lambda$NaviSRFragment$b7WXxuE6-WbnJegBbbQ7-gZbBEM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                NaviSRFragment.this.showRightListView(((Boolean) obj).booleanValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRightListView(boolean z) {
        this.mRightInfoViewGroup.showList(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftListData(InfoContainBean infoContainBean) {
        XILog.d(TAG, "updateLeftListData infoContain bean is: " + infoContainBean);
        if (infoContainBean == null) {
            return;
        }
        this.mLeftInfoViewGroup.updateListData(infoContainBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightListData(InfoContainBean infoContainBean) {
        XILog.d(TAG, "updateRightListData infoContain bean is: " + infoContainBean);
        if (infoContainBean == null) {
            return;
        }
        this.mRightInfoViewGroup.updateListData(infoContainBean);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftListHighPosition(int i) {
        this.mLeftInfoViewGroup.updateListHighIndex(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightListHighPosition(int i) {
        this.mRightInfoViewGroup.updateListHighIndex(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLeftListView(boolean z) {
        this.mLeftInfoViewGroup.showList(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLeftCardView(boolean z) {
        this.mLeftInfoViewGroup.showCardView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRightCardView(boolean z) {
        this.mRightInfoViewGroup.showCardView(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLeftSubCardView(int i) {
        this.mLeftInfoViewGroup.showSubCardView(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSubRightCardView(int i) {
        this.mRightInfoViewGroup.showSubCardView(i);
    }
}
