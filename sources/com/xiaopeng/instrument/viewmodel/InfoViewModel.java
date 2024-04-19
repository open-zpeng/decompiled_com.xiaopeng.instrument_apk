package com.xiaopeng.instrument.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICardControlListener;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.InfoBean;
import com.xiaopeng.instrument.bean.InfoContainBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.manager.SurfaceViewManager;
import com.xiaopeng.instrument.utils.CommonUtil;
import com.xiaopeng.instrument.utils.ResUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/* loaded from: classes.dex */
public class InfoViewModel extends ViewModel implements ICardControlListener {
    private static final int LIST_MIN_SIZE = 0;
    private static final int SENSOR_CARD_INDEX = 5;
    private static final int SENSOR_IMG_ID = 2131230946;
    private static final String SENSOR_NAME = App.getInstance().getString(R.string.sensor_fault_name);
    private boolean mIsAddOrInsertSensorItem;
    private boolean mIsShowLeftCardView;
    private boolean mIsShowLeftList;
    private boolean mIsShowRightCardView;
    private boolean mIsShowRightList;
    private int mLastLeftHighIndex;
    private int mLastRightHighIndex;
    public final String TAG = getClass().getSimpleName();
    public final MutableLiveData<Boolean> mLeftCardLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> mRightCardLiveData = new MutableLiveData<>();
    public final MutableLiveData<Integer> mLeftSubCardLiveData = new MutableLiveData<>(0);
    public final MutableLiveData<Integer> mRightSubCardLiveData = new MutableLiveData<>(1);
    public final MutableLiveData<InfoContainBean> mLeftListLiveData = new MutableLiveData<>();
    public final MutableLiveData<InfoContainBean> mRightListLiveData = new MutableLiveData<>();
    public final MutableLiveData<Integer> mLeftListIndexLiveData = new MutableLiveData<>();
    public final MutableLiveData<Integer> mRightListIndexLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> mLeftListInfoLiveData = new MutableLiveData<>();
    public final MutableLiveData<Boolean> mRightListInfoLiveData = new MutableLiveData<>();
    public final MutableLiveData<Integer> mGearType = new MutableLiveData<>();
    private List<InfoBean> mLeftInfoBeanList = new ArrayList();
    private List<InfoBean> mRightInfoBeanList = new ArrayList();
    private int mLeftCardIndex = -1;
    private int mRightCardIndex = -1;
    private ICommonListener mCommonListener = new ICommonListener() { // from class: com.xiaopeng.instrument.viewmodel.InfoViewModel.1
        @Override // com.xiaopeng.cluster.listener.ICommonListener
        public void onGear(int i) {
            InfoViewModel.this.mGearType.setValue(Integer.valueOf(i));
        }
    };

    public InfoViewModel() {
        ClusterManager.getInstance().addCommonListener(this.mCommonListener);
        ClusterManager.getInstance().addCardControlListener(this);
        initInfoData();
    }

    public MutableLiveData<Integer> getLeftListIndexLiveData() {
        return this.mLeftListIndexLiveData;
    }

    public MutableLiveData<Integer> getRightListIndexLiveData() {
        return this.mRightListIndexLiveData;
    }

    public MutableLiveData<Boolean> getLeftCardLiveData() {
        return this.mLeftCardLiveData;
    }

    public MutableLiveData<Boolean> getRightCardLiveData() {
        return this.mRightCardLiveData;
    }

    public MutableLiveData<Integer> getLeftSubCardLiveData() {
        return this.mLeftSubCardLiveData;
    }

    public MutableLiveData<Integer> getRightSubCardLiveData() {
        return this.mRightSubCardLiveData;
    }

    public MutableLiveData<Boolean> getLeftListInfoLiveData() {
        return this.mLeftListInfoLiveData;
    }

    public MutableLiveData<Boolean> getRightListInfoLiveData() {
        return this.mRightListInfoLiveData;
    }

    public MutableLiveData<InfoContainBean> getLeftListLiveData() {
        return this.mLeftListLiveData;
    }

    public MutableLiveData<InfoContainBean> getRightListLiveData() {
        return this.mRightListLiveData;
    }

    public MutableLiveData<Integer> getGearLiveData() {
        return this.mGearType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removeCommonListener(this.mCommonListener);
        ClusterManager.getInstance().removeCardControlListener(this);
    }

    private void initInfoData() {
        List<InfoBean> infoBeans = DataConfigManager.getInfoBeans();
        this.mLeftInfoBeanList = infoBeans;
        if (infoBeans == null || infoBeans.size() == 0) {
            XILog.d(this.TAG, "mLeftInfoBeanList is null");
            return;
        }
        for (InfoBean infoBean : this.mLeftInfoBeanList) {
            if (infoBean == null) {
                XILog.d(this.TAG, "info bean is null");
            } else if (!TextUtils.isEmpty(infoBean.getImgResName())) {
                infoBean.setImgResId(ResUtil.getDrawableResByName(infoBean.getImgResName()));
            } else {
                XILog.d(this.TAG, "infoBean img res name is empty");
            }
        }
        this.mRightInfoBeanList.clear();
        this.mRightInfoBeanList.addAll(this.mLeftInfoBeanList);
        fillAllShowInfo(this.mLeftInfoBeanList, 0);
        fillAllShowInfo(this.mRightInfoBeanList, 1);
    }

    private void showSubCard(int i, int i2) {
        if (i == 0) {
            this.mLeftSubCardLiveData.postValue(Integer.valueOf(i2));
        } else if (i == 1) {
            this.mRightSubCardLiveData.postValue(Integer.valueOf(i2));
        }
    }

    private void updateSensorList(boolean z) {
        if (z) {
            addSensorBean();
        } else {
            removeSensorBean();
        }
        fillAllShowInfo(this.mLeftInfoBeanList, 0);
    }

    private void removeSensorBean() {
        XILog.d(this.TAG, "removeSensorBean before list size:" + this.mLeftInfoBeanList.size());
        Iterator<InfoBean> it = this.mLeftInfoBeanList.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == 5) {
                it.remove();
            }
        }
        XILog.d(this.TAG, "removeSensorBean after list size:" + this.mLeftInfoBeanList.size());
    }

    private InfoBean createSensor() {
        InfoBean infoBean = new InfoBean();
        infoBean.setId(5);
        infoBean.setImgResId(R.drawable.ic_applist_rada);
        infoBean.setName(SENSOR_NAME);
        return infoBean;
    }

    private void addSensorBean() {
        InfoBean createSensor = createSensor();
        XILog.d(this.TAG, "list size:" + this.mLeftInfoBeanList.size());
        if (this.mLeftInfoBeanList.contains(createSensor)) {
            return;
        }
        this.mLeftInfoBeanList.add(createSensor);
    }

    private void fillAllShowInfo(List<InfoBean> list, int i) {
        InfoContainBean infoContainBean = new InfoContainBean();
        infoContainBean.setInfoBeanList(list);
        if (i == 0) {
            XILog.i(this.TAG, "mLastLeftHighIndex = " + this.mLastLeftHighIndex);
            infoContainBean.setSelectIndex(this.mLastLeftHighIndex);
            this.mLeftListLiveData.postValue(infoContainBean);
        } else if (i == 1) {
            XILog.i(this.TAG, "mLastRightHighIndex = " + this.mLastRightHighIndex);
            infoContainBean.setSelectIndex(this.mLastRightHighIndex);
            this.mRightListLiveData.postValue(infoContainBean);
        }
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onLeftListVisible(boolean z) {
        XILog.d(this.TAG, "onLeftListVisible:" + z);
        if (CommonUtil.isEqual(this.mIsShowLeftList, z)) {
            XILog.d(this.TAG, "mIsShowLeftList is equal");
            return;
        }
        this.mIsShowLeftList = z;
        this.mLeftListInfoLiveData.postValue(Boolean.valueOf(z));
    }

    private int getCardIndex(int i, List<InfoBean> list) {
        for (int i2 = 0; i2 < list.size(); i2++) {
            if (list.get(i2).getId() == i) {
                return i2;
            }
        }
        return 0;
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onLeftListIndex(int i) {
        int cardIndex = getCardIndex(i, this.mLeftInfoBeanList);
        this.mLastLeftHighIndex = cardIndex;
        this.mLeftListIndexLiveData.postValue(Integer.valueOf(cardIndex));
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onLeftListSensorFault(boolean z) {
        if (CommonUtil.isEqual(this.mIsAddOrInsertSensorItem, z)) {
            XILog.d(this.TAG, "mIsAddOrInsertSensorItem is equal");
            return;
        }
        this.mIsAddOrInsertSensorItem = z;
        updateSensorList(z);
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onLeftCardIndex(int i) {
        if (CommonUtil.isEqual(this.mLeftCardIndex, i)) {
            XILog.d(this.TAG, "mLeftCardIndex is equal");
            return;
        }
        this.mLeftCardIndex = i;
        if (i == 0) {
            SurfaceViewManager.getInstance().setLeftViewType(2);
        } else {
            SurfaceViewManager.getInstance().setLeftViewType(0);
        }
        showSubCard(0, i);
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onLeftCardVisible(boolean z) {
        if (CommonUtil.isEqual(this.mIsShowLeftCardView, z)) {
            XILog.d(this.TAG, "mIsShowLeftCardView is equal");
            return;
        }
        this.mIsShowLeftCardView = z;
        this.mLeftCardLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onRightListVisible(boolean z) {
        XILog.d(this.TAG, "onRightListVisible:" + z);
        if (CommonUtil.isEqual(this.mIsShowRightList, z)) {
            XILog.d(this.TAG, "mIsShowRightList is equal");
            return;
        }
        this.mIsShowRightList = z;
        this.mRightListInfoLiveData.postValue(Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onRightListIndex(int i) {
        int cardIndex = getCardIndex(i, this.mRightInfoBeanList);
        this.mLastRightHighIndex = cardIndex;
        this.mRightListIndexLiveData.postValue(Integer.valueOf(cardIndex));
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onRightCardIndex(int i) {
        if (CommonUtil.isEqual(this.mRightCardIndex, i)) {
            XILog.d(this.TAG, "mRightCardIndex is equal");
            return;
        }
        this.mRightCardIndex = i;
        showSubCard(1, i);
        if (i == 0) {
            SurfaceViewManager.getInstance().setRightViewType(3);
        } else {
            SurfaceViewManager.getInstance().setRightViewType(0);
        }
    }

    @Override // com.xiaopeng.cluster.listener.ICardControlListener
    public void onRightCardVisible(boolean z) {
        if (CommonUtil.isEqual(this.mIsShowRightCardView, z)) {
            XILog.d(this.TAG, "mIsShowRightCardView is equal");
            return;
        }
        this.mIsShowRightCardView = z;
        this.mRightCardLiveData.postValue(Boolean.valueOf(z));
    }
}
