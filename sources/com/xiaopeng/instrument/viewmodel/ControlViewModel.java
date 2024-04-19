package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.bean.SpaceBean;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.IControlListener;
import com.xiaopeng.cluster.listener.INaviSRListener;
import com.xiaopeng.cluster.listener.IOTAListener;
import com.xiaopeng.cluster.utils.UiHandler;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.ChargingState;
import com.xiaopeng.instrument.bean.TestColorBean;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import com.xiaopeng.instrument.view.FragmentType;
/* loaded from: classes.dex */
public class ControlViewModel extends ViewModel implements IControlListener, ICommonListener, IOTAListener, INaviSRListener {
    private static final int DURATION_TIME = 300;
    private static final int MAX_VALUE = 100;
    private static final int MIN_VALUE = 0;
    public static final String TAG = "ControlViewModel";
    private int mCurrentChargeState;
    private long mInterval;
    private Runnable mShowSRTask;
    private final MutableLiveData<FragmentType> mChargeLiveData = new MutableLiveData<>(FragmentType.MAIN);
    private final MutableLiveData<Boolean> mIndicatorControlLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mOffLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mOtaLiveData = new MutableLiveData<>();
    private final MutableLiveData<TestColorBean> mTestColorLiveData = new MutableLiveData<>();
    private final MutableLiveData<Integer> mOtaProgressLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mCommonViewLivedata = new MutableLiveData<>();
    private final MutableLiveData<SpaceBean> mSpaceBeanMutableLiveData = new MutableLiveData<>();
    private TestColorBean mTestColorBean = new TestColorBean();

    @Override // com.xiaopeng.cluster.listener.INaviSRListener
    public void onNaviSRTraffic(int i) {
    }

    public ControlViewModel() {
        ClusterManager.getInstance().setControlListener(this);
        ClusterManager.getInstance().addCommonListener(this);
        ClusterManager.getInstance().setOtaListener(this);
        ClusterManager.getInstance().addINaviSRListener(this);
        this.mShowSRTask = new Runnable() { // from class: com.xiaopeng.instrument.viewmodel.-$$Lambda$ControlViewModel$BlFmDlNFRAcNqG4HT7qJxd3FM6s
            @Override // java.lang.Runnable
            public final void run() {
                ControlViewModel.this.lambda$new$0$ControlViewModel();
            }
        };
    }

    public /* synthetic */ void lambda$new$0$ControlViewModel() {
        showLayout(FragmentType.NAVI_SR);
        this.mCommonViewLivedata.setValue(true);
    }

    public MutableLiveData<Integer> getOtaProgressLiveData() {
        return this.mOtaProgressLiveData;
    }

    public MutableLiveData<Boolean> getOtaLiveData() {
        return this.mOtaLiveData;
    }

    public MutableLiveData<SpaceBean> getSpaceBeanMutableLiveData() {
        return this.mSpaceBeanMutableLiveData;
    }

    public MutableLiveData<Boolean> getIndicatorControlLiveData() {
        return this.mIndicatorControlLiveData;
    }

    public MutableLiveData<FragmentType> getChargeLiveData() {
        return this.mChargeLiveData;
    }

    public MutableLiveData<TestColorBean> getTestColorLiveData() {
        return this.mTestColorLiveData;
    }

    public MutableLiveData<Boolean> getOffLiveData() {
        return this.mOffLiveData;
    }

    public MutableLiveData<Boolean> getCommonViewLiveData() {
        return this.mCommonViewLivedata;
    }

    public void showLayout(FragmentType fragmentType) {
        this.mChargeLiveData.postValue(fragmentType);
        XpuInstrumentClient.getInstance().setFragmentType(fragmentType);
    }

    @Override // com.xiaopeng.cluster.listener.IControlListener
    public void onIsOffControProp(boolean z) {
        XILog.d(TAG, "onIsOffControProp:" + z);
        this.mOffLiveData.setValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IControlListener
    public void onColorTest(int i) {
        if (i == 1) {
            this.mTestColorBean.setFirstId(App.getInstance().getColor(R.color.red));
            this.mTestColorBean.setSecondId(App.getInstance().getColor(R.color.green));
            this.mTestColorBean.setThirdId(App.getInstance().getColor(R.color.blue));
            this.mTestColorBean.setShow(true);
        } else if (i == 2) {
            this.mTestColorBean.setFirstId(App.getInstance().getColor(R.color.white));
            this.mTestColorBean.setSecondId(App.getInstance().getColor(R.color.white));
            this.mTestColorBean.setThirdId(App.getInstance().getColor(R.color.white));
            this.mTestColorBean.setShow(true);
        } else if (i == 3) {
            this.mTestColorBean.setFirstId(App.getInstance().getColor(R.color.red));
            this.mTestColorBean.setSecondId(App.getInstance().getColor(R.color.red));
            this.mTestColorBean.setThirdId(App.getInstance().getColor(R.color.red));
            this.mTestColorBean.setShow(true);
        } else if (i == 4) {
            this.mTestColorBean.setFirstId(App.getInstance().getColor(R.color.green));
            this.mTestColorBean.setSecondId(App.getInstance().getColor(R.color.green));
            this.mTestColorBean.setThirdId(App.getInstance().getColor(R.color.green));
            this.mTestColorBean.setShow(true);
        } else if (i == 5) {
            this.mTestColorBean.setFirstId(App.getInstance().getColor(R.color.blue));
            this.mTestColorBean.setSecondId(App.getInstance().getColor(R.color.blue));
            this.mTestColorBean.setThirdId(App.getInstance().getColor(R.color.blue));
            this.mTestColorBean.setShow(true);
        } else {
            this.mTestColorBean.setShow(false);
        }
        this.mTestColorLiveData.setValue(this.mTestColorBean);
    }

    @Override // com.xiaopeng.cluster.listener.IControlListener
    public void onRefreshFamilyPackage(SpaceBean spaceBean) {
        if (spaceBean == null) {
            XILog.e(TAG, "onRefreshFamilyPackage is null");
        } else {
            this.mSpaceBeanMutableLiveData.setValue(spaceBean);
        }
    }

    @Override // com.xiaopeng.cluster.listener.IOTAListener
    public void onProgressBar(int i) {
        if (i < 0 || i > 100) {
            XILog.e(TAG, "progress error! please check, current progress: " + i);
        } else {
            this.mOtaProgressLiveData.setValue(Integer.valueOf(i));
        }
    }

    @Override // com.xiaopeng.cluster.listener.IOTAListener
    public void onOtaState(int i) {
        XILog.i(TAG, "onOtaState:" + i);
        if (i == 2) {
            this.mOtaLiveData.setValue(true);
        } else {
            this.mOtaLiveData.setValue(false);
        }
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onChargingState(int i) {
        this.mCurrentChargeState = i;
        if (ChargingState.parse(i) != ChargingState.CHARGING_STATE_NOT_CONNECTED) {
            showLayout(FragmentType.CHARGE);
            this.mCommonViewLivedata.setValue(false);
            return;
        }
        showLayout(FragmentType.MAIN);
        if (SurfaceViewManager.getInstance().getLeftViewType() == 2) {
            XILog.i(TAG, "sr_navi_onChargingState: SD left change");
            SurfaceViewManager.getInstance().startLeftSDChangeService();
        } else if (SurfaceViewManager.getInstance().getRightViewType() == 3) {
            XILog.i(TAG, "sr_navi_onChargingState: SD right change");
            SurfaceViewManager.getInstance().startRightSDChangeService();
        }
        this.mCommonViewLivedata.setValue(true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setControlListener(null);
        ClusterManager.getInstance().setOtaListener(null);
        ClusterManager.getInstance().removeCommonListener(this);
        ClusterManager.getInstance().removeINaviSRListener(this);
        this.mShowSRTask = null;
    }

    @Override // com.xiaopeng.cluster.listener.INaviSRListener
    public void onNaviStart() {
        XILog.i(TAG, "sr_navi_onNaviStart");
        SurfaceViewManager.getInstance().startCreateService();
    }

    @Override // com.xiaopeng.cluster.listener.INaviSRListener
    public void onNaviSRMode(boolean z) {
        XILog.i(TAG, "sr_navi_onNaviStart isSRMode: " + z);
        SurfaceViewManager.getInstance().setIsSRMode(z);
        if (z) {
            this.mInterval = System.currentTimeMillis();
            SurfaceViewManager.getInstance().startSRChangeService();
            UiHandler.getInstance().postDelayed(this.mShowSRTask, 300L);
            return;
        }
        if (System.currentTimeMillis() - this.mInterval <= 300) {
            UiHandler.getInstance().removeCallbacks(this.mShowSRTask);
        }
        onChargingState(this.mCurrentChargeState);
        this.mInterval = 0L;
    }
}
