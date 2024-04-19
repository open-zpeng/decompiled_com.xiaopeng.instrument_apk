package com.xiaopeng.instrument.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.IndicatorListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.IndicatorBean;
import com.xiaopeng.instrument.bean.IndicatorContainBean;
import com.xiaopeng.instrument.bean.IndicatorViewBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class IndicatorViewModel extends ViewModel implements IndicatorListener {
    private static final int DOOR_MAX = 11;
    private static final int DOOR_MIN = 7;
    private static final String DYNAMIC_PRE = "dynamic_";
    private static final String FIXED_PRE = "fixed_";
    private static final String INSTRUMENT_TURN_LEFT_ID = "fixed_500";
    private static final String INSTRUMENT_TURN_RIGHT_ID = "fixed_501";
    private static final int INVALID_ID = -1;
    private static final int MIN_SIZE = 0;
    public static final String TAG = "IndicatorViewModel";
    private static final String TURN_LEFT_ID = "500";
    private static final String TURN_RIGHT_ID = "501";
    private Map<String, IndicatorBean> mAllIndicatorBeanMap;
    private Map<String, IndicatorBean> mDynamicBeanMap;
    private final MutableLiveData<List<IndicatorViewBean>> mAllLiveData = new MutableLiveData<>();
    private final MutableLiveData<IndicatorContainBean> mCurrentLiveData = new MutableLiveData<>();
    private final MutableLiveData<Map<Integer, IndicatorContainBean>> mCacheDataLiveData = new MutableLiveData<>();
    private Map<Integer, IndicatorContainBean> mCacheContainBeanMap = new HashMap();
    private Map<Integer, Boolean> mDoorStateMap = new HashMap();

    public IndicatorViewModel() {
        this.mAllIndicatorBeanMap = new ConcurrentHashMap();
        this.mDynamicBeanMap = new ConcurrentHashMap();
        ClusterManager.getInstance().setIndicatorListener(this);
        this.mAllIndicatorBeanMap = DataConfigManager.getAllIndicatorBeanMap();
        this.mDynamicBeanMap = DataConfigManager.getDynamicBeanMap();
        initViewData();
    }

    public void resumeData() {
        Map<Integer, IndicatorContainBean> map = this.mCacheContainBeanMap;
        if (map == null || map.size() == 0) {
            String str = TAG;
            StringBuilder append = new StringBuilder().append("resumeData size ");
            Map<Integer, IndicatorContainBean> map2 = this.mCacheContainBeanMap;
            XILog.i(str, append.append(map2 == null ? null : Integer.valueOf(map2.size())).toString());
            return;
        }
        this.mCacheDataLiveData.setValue(this.mCacheContainBeanMap);
        for (Map.Entry<Integer, Boolean> entry : this.mDoorStateMap.entrySet()) {
            Integer key = entry.getKey();
            Boolean value = entry.getValue();
            XILog.i(TAG, "resumeData door status key: " + key + " value: " + value);
            reloadDoorData(key.intValue(), value.booleanValue());
        }
    }

    private void reloadDoorData(int i, boolean z) {
        doIndicator(FIXED_PRE + i, z);
    }

    public MutableLiveData<Map<Integer, IndicatorContainBean>> getCacheDataLiveData() {
        return this.mCacheDataLiveData;
    }

    public MutableLiveData<List<IndicatorViewBean>> getAllLiveData() {
        return this.mAllLiveData;
    }

    public MutableLiveData<IndicatorContainBean> getCurrentLiveData() {
        return this.mCurrentLiveData;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setIndicatorListener(null);
    }

    private void initViewData() {
        List<IndicatorViewBean> indicatorViewBeans = DataConfigManager.getIndicatorViewBeans();
        if (indicatorViewBeans == null || indicatorViewBeans.size() == 0) {
            XILog.d(TAG, "allIndicatorViewBeans is null or size is 0");
        } else {
            this.mAllLiveData.setValue(indicatorViewBeans);
        }
    }

    private void doIndicator(String str, boolean z) {
        Map<String, IndicatorBean> map = this.mAllIndicatorBeanMap;
        if (map != null) {
            IndicatorBean indicatorBean = map.get(str);
            if (indicatorBean == null) {
                XILog.d(TAG, "current fixed indicator is not exist...");
                return;
            }
            IndicatorContainBean indicatorContainBean = new IndicatorContainBean();
            indicatorContainBean.setIndicatorBean(indicatorBean);
            indicatorContainBean.setVisible(z);
            updateIndicatorContainData(indicatorContainBean);
            this.mCurrentLiveData.setValue(indicatorContainBean);
        }
    }

    private void updateIndicatorContainData(IndicatorContainBean indicatorContainBean) {
        if (indicatorContainBean.getIndicatorBean() == null) {
            XILog.d(TAG, "updateIndicatorContainData is null ");
            return;
        }
        int pos = indicatorContainBean.getIndicatorBean().getPos();
        XILog.d(TAG, "updateIndicatorContainData bean:" + this.mCacheContainBeanMap.size() + " id:" + pos);
        this.mCacheContainBeanMap.put(Integer.valueOf(pos), indicatorContainBean);
    }

    private void doDynamicIndicator(int[] iArr) {
        if (iArr.length == 0) {
            XILog.d(TAG, "dynamic ids length is 0");
            return;
        }
        int i = 0;
        while (i < iArr.length) {
            int i2 = i + 1;
            IndicatorBean indicatorBean = this.mAllIndicatorBeanMap.get(DYNAMIC_PRE + i2);
            if (indicatorBean == null) {
                XILog.d(TAG, "dynamic Pos is null");
            } else {
                int i3 = iArr[i];
                IndicatorContainBean indicatorContainBean = new IndicatorContainBean();
                IndicatorBean indicatorBean2 = new IndicatorBean();
                if (i3 == -1) {
                    indicatorContainBean.setVisible(false);
                } else {
                    indicatorContainBean.setVisible(true);
                    indicatorBean2 = this.mDynamicBeanMap.get(DYNAMIC_PRE + i3);
                    if (indicatorBean2 == null) {
                        XILog.d(TAG, "current dynamic indicator is  not exist...");
                    }
                }
                indicatorBean2.setPos(indicatorBean.getPos());
                indicatorContainBean.setIndicatorBean(indicatorBean2);
                updateIndicatorContainData(indicatorContainBean);
                this.mCurrentLiveData.setValue(indicatorContainBean);
            }
            i = i2;
        }
    }

    @Override // com.xiaopeng.cluster.listener.IndicatorListener
    public void onTurnLeftLightControProp(boolean z) {
        doIndicator(INSTRUMENT_TURN_LEFT_ID, z);
    }

    @Override // com.xiaopeng.cluster.listener.IndicatorListener
    public void onTurnRightLightControProp(boolean z) {
        doIndicator(INSTRUMENT_TURN_RIGHT_ID, z);
    }

    @Override // com.xiaopeng.cluster.listener.IndicatorListener
    public void onCommonTelltale(int i, boolean z) {
        doIndicator(FIXED_PRE + i, z);
        if (i < 7 || i > 11) {
            return;
        }
        this.mDoorStateMap.put(Integer.valueOf(i), Boolean.valueOf(z));
    }

    @Override // com.xiaopeng.cluster.listener.IndicatorListener
    public void onUnsetTelltale(int[] iArr) {
        doDynamicIndicator(iArr);
    }
}
