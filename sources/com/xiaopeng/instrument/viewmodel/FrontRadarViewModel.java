package com.xiaopeng.instrument.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.IFrontRadarListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.CarBodyColorBean;
import com.xiaopeng.instrument.bean.FrontDistBean;
import com.xiaopeng.instrument.bean.FrontRadarBean;
import com.xiaopeng.instrument.data.ChargeRepository;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import java.util.Map;
/* loaded from: classes.dex */
public class FrontRadarViewModel extends ViewModel implements IFrontRadarListener, ICommonListener {
    public static final String TAG = "FrontRadarViewModel";
    private final MutableLiveData<FrontRadarBean> mFrontRadar = new MutableLiveData<>();
    private final MutableLiveData<FrontDistBean> mFrontRadarLiveDataValue = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mFrontRadarShowLiveData = new MutableLiveData<>();
    private final FrontRadarBean mFrontRadarBean = new FrontRadarBean();
    private final FrontDistBean mFrontDistBean = new FrontDistBean();
    private final String FRONT_DIST_DEFAULT = ChargeRepository.DEFAULT_ENDURANCE_VALUE;
    private final String FRONT_DIST_STOP = BuildInfoUtils.BID_WAN;
    private final MutableLiveData<CarBodyColorBean> mBodyColorBeanMutableLiveData = new MutableLiveData<>();
    private Map<Integer, CarBodyColorBean> mCarBodyColorBeanMap = DataConfigManager.getCarFrontBodyColorBeans();

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onBehindDistValue(String str) {
    }

    public FrontRadarViewModel() {
        ClusterManager.getInstance().setFrontRadarListener(this);
        ClusterManager.getInstance().addCommonListener(this);
    }

    public MutableLiveData<CarBodyColorBean> getBodyColorBeanMutableLiveData() {
        return this.mBodyColorBeanMutableLiveData;
    }

    public MutableLiveData<FrontDistBean> getFrontRadarLiveDataValue() {
        return this.mFrontRadarLiveDataValue;
    }

    public MutableLiveData<Boolean> getFrontRadarShowLiveData() {
        return this.mFrontRadarShowLiveData;
    }

    public MutableLiveData<FrontRadarBean> getFrontRadar() {
        return this.mFrontRadar;
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontCLColor(int i) {
        this.mFrontRadarBean.setLevel(true);
        this.mFrontRadarBean.setLevel(i);
        this.mFrontRadarBean.setRadarNumber(2);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontCLDist(int i) {
        this.mFrontRadarBean.setLevel(false);
        this.mFrontRadarBean.setDistances(i);
        this.mFrontRadarBean.setRadarNumber(2);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontSRColor(int i) {
        this.mFrontRadarBean.setLevel(true);
        this.mFrontRadarBean.setLevel(i);
        this.mFrontRadarBean.setRadarNumber(5);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontSRDist(int i) {
        this.mFrontRadarBean.setLevel(false);
        this.mFrontRadarBean.setDistances(i);
        this.mFrontRadarBean.setRadarNumber(5);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontSLColor(int i) {
        this.mFrontRadarBean.setLevel(true);
        this.mFrontRadarBean.setLevel(i);
        this.mFrontRadarBean.setRadarNumber(0);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontSLDist(int i) {
        this.mFrontRadarBean.setLevel(false);
        this.mFrontRadarBean.setDistances(i);
        this.mFrontRadarBean.setRadarNumber(0);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontORColor(int i) {
        this.mFrontRadarBean.setLevel(true);
        this.mFrontRadarBean.setLevel(i);
        this.mFrontRadarBean.setRadarNumber(4);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontORDist(int i) {
        this.mFrontRadarBean.setLevel(false);
        this.mFrontRadarBean.setDistances(i);
        this.mFrontRadarBean.setRadarNumber(4);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontOLColor(int i) {
        this.mFrontRadarBean.setLevel(true);
        this.mFrontRadarBean.setLevel(i);
        this.mFrontRadarBean.setRadarNumber(1);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontOLDist(int i) {
        this.mFrontRadarBean.setLevel(false);
        this.mFrontRadarBean.setDistances(i);
        this.mFrontRadarBean.setRadarNumber(1);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontCRColor(int i) {
        this.mFrontRadarBean.setLevel(true);
        this.mFrontRadarBean.setLevel(i);
        this.mFrontRadarBean.setRadarNumber(3);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontCRDist(int i) {
        this.mFrontRadarBean.setLevel(false);
        this.mFrontRadarBean.setDistances(i);
        this.mFrontRadarBean.setRadarNumber(3);
        this.mFrontRadar.setValue(this.mFrontRadarBean);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onFrontDistValue(String str) {
        boolean z = true;
        if (!(!TextUtils.isEmpty(str)) || ChargeRepository.DEFAULT_ENDURANCE_VALUE.equals(str)) {
            z = false;
        }
        if (z) {
            fillFrontDistBean(BuildInfoUtils.BID_WAN.equals(str), str);
        } else {
            this.mFrontDistBean.setState(0);
            this.mFrontDistBean.setDistValue(str);
        }
        this.mFrontRadarLiveDataValue.setValue(this.mFrontDistBean);
    }

    private void fillFrontDistBean(boolean z, String str) {
        int i;
        if (z) {
            i = 1;
            str = App.getInstance().getString(R.string.radar_warning_dis_stop);
        } else {
            i = 2;
        }
        this.mFrontDistBean.setState(i);
        this.mFrontDistBean.setDistValue(str);
    }

    @Override // com.xiaopeng.cluster.listener.IFrontRadarListener
    public void onLowSpeedAlarm(boolean z) {
        this.mFrontRadarShowLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    public void onCarColorControProp(int i) {
        CarBodyColorBean carBodyColorBean = this.mCarBodyColorBeanMap.get(Integer.valueOf(i));
        if (carBodyColorBean == null) {
            XILog.d(TAG, "carBodyColorBean is null");
        } else {
            this.mBodyColorBeanMutableLiveData.postValue(carBodyColorBean);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setFrontRadarListener(null);
        ClusterManager.getInstance().removeCommonListener(this);
    }
}
