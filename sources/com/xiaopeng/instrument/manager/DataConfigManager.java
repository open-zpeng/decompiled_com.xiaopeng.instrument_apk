package com.xiaopeng.instrument.manager;

import android.text.TextUtils;
import com.google.gson.reflect.TypeToken;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.bean.AdasCcBean;
import com.xiaopeng.instrument.bean.CarBodyColorBean;
import com.xiaopeng.instrument.bean.DrivingModeBean;
import com.xiaopeng.instrument.bean.IndicatorBean;
import com.xiaopeng.instrument.bean.IndicatorViewBean;
import com.xiaopeng.instrument.bean.InfoBean;
import com.xiaopeng.instrument.bean.NGPTipsBean;
import com.xiaopeng.instrument.bean.SensorBean;
import com.xiaopeng.instrument.bean.WaringBean;
import com.xiaopeng.instrument.utils.AssetUtil;
import com.xiaopeng.instrument.utils.GsonUtil;
import com.xiaopeng.instrument.utils.ResUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
/* loaded from: classes.dex */
public class DataConfigManager {
    private static final String ASSETS_ADAS_ACC_STATE_URL = "adas_acc_state.json";
    private static final String ASSETS_ADAS_ALC_STATE_URL = "adas_alc_state.json";
    private static final String ASSETS_ADAS_CC_STATE_URL = "adas_cc_state.json";
    private static final String ASSETS_ADAS_LCC_FAILURE_STATE_URL = "adas_lcc_failure_state.json";
    private static final String ASSETS_ADAS_LCC_STATE_URL = "adas_lcc_state.json";
    private static final String ASSETS_ADAS_NGP_STATE_URL = "adas_ngp_state.json";
    private static final String ASSETS_CAR_BODY_FRONT_COLOR_URL = "car_body_front.json";
    private static final String ASSETS_INDICATOR_DYNAMIC_URL = "indicator_dynamic.json";
    private static final String ASSETS_INDICATOR_URL = "indicator_list.json";
    private static final String ASSETS_INDICATOR_VIEW_URL = "indicator_view_list.json";
    private static final String ASSETS_INFO_URL = "info_list.json";
    private static final String ASSETS_SR_BOTTOM_TIPS_URL = "sr_bottom_tips.json";
    private static final String ASSETS_SR_NGP_20_VALUE_URL = "sr_ngp_20_value.json";
    private static final String ASSETS_SR_NGP_22_VALUE_URL = "sr_ngp_22_value.json";
    private static final String ASSETS_SR_NGP_23_VALUE_URL = "sr_ngp_23_value.json";
    private static final String ASSETS_SR_TOP_TIPS_URL = "sr_top_tips.json";
    private static final String ASSETS_TSR_FORBID_URL = "indicator_tsr_forbid.json";
    private static final String ASSETS_TSR_RATE_LIMIT_URL = "indicator_tsr_rate_limit.json";
    private static final String ASSETS_TSR_WARNING_URL = "indicator_tsr_warning.json";
    private static final String ASSET_DRIVING_MODE_URL = "driving_mode.json";
    private static final String ASSET_SENSOR_TEXT_URL = "sensor_fault_text.json";
    private static final String ASSET_SENSOR_URL = "sensor_list.json";
    private static final String ASSET_WARNING_URL = "warning_list.json";
    private static final String TAG = "DataConfigManager";
    private static final int WARNING_VALID_ID = -1;
    private static List<InfoBean> mInfoBeans = new CopyOnWriteArrayList();
    private static List<IndicatorViewBean> mIndicatorViewBeans = new CopyOnWriteArrayList();
    private static Map<Integer, AdasCcBean> sAdasACCBeanMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sAdasLCCBeanMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sAdasCCBeanMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sAdasNGPBeanMap = new ConcurrentHashMap();
    private static Map<Integer, WaringBean> sWaringBeanMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sTsrWarningMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sTsrForbidMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sTsrRateLimitMap = new ConcurrentHashMap();
    private static Map<String, IndicatorBean> sAllIndicatorBeanMap = new ConcurrentHashMap();
    private static Map<String, IndicatorBean> sDynamicBeanMap = new ConcurrentHashMap();
    private static Map<Integer, SensorBean> sSensorBeanMap = new ConcurrentHashMap();
    private static Map<Integer, CarBodyColorBean> sCarFrontBodyColorBeans = new ConcurrentHashMap();
    private static Map<Integer, String> sSensorFaultTexts = new ConcurrentHashMap();
    private static Map<Integer, NGPTipsBean> sSRBottomTipsBeans = new ConcurrentHashMap();
    private static Map<Integer, NGPTipsBean> sSRTopTipsBeans = new ConcurrentHashMap();
    private static Map<Integer, DrivingModeBean> sDrivingModeBeans = new ConcurrentHashMap();
    private static Set<Integer> sNGP20Beans = new CopyOnWriteArraySet();
    private static Set<Integer> sNGP22Beans = new CopyOnWriteArraySet();
    private static Set<Integer> sNGP23Beans = new CopyOnWriteArraySet();

    private DataConfigManager() {
    }

    public static Map<Integer, AdasCcBean> getAdasNGPBeanMap() {
        return sAdasNGPBeanMap;
    }

    public static Map<Integer, String> getSensorFaultTexts() {
        return sSensorFaultTexts;
    }

    public static Map<Integer, CarBodyColorBean> getCarFrontBodyColorBeans() {
        return sCarFrontBodyColorBeans;
    }

    public static Map<Integer, AdasCcBean> getTsrRateLimitMap() {
        return sTsrRateLimitMap;
    }

    public static Map<Integer, AdasCcBean> getTsrWarningMap() {
        return sTsrWarningMap;
    }

    public static Map<Integer, AdasCcBean> getTsrForbidMap() {
        return sTsrForbidMap;
    }

    public static Map<Integer, SensorBean> getSensorBeanMap() {
        return sSensorBeanMap;
    }

    public static Map<String, IndicatorBean> getAllIndicatorBeanMap() {
        return sAllIndicatorBeanMap;
    }

    public static Map<String, IndicatorBean> getDynamicBeanMap() {
        return sDynamicBeanMap;
    }

    public static Map<Integer, WaringBean> getWaringBeanMap() {
        return sWaringBeanMap;
    }

    public static Map<Integer, AdasCcBean> getAdasACCBeanMap() {
        return sAdasACCBeanMap;
    }

    public static Map<Integer, AdasCcBean> getAdasLCCBeanMap() {
        return sAdasLCCBeanMap;
    }

    public static Map<Integer, AdasCcBean> getAdasCCBeanMap() {
        return sAdasCCBeanMap;
    }

    public static List<IndicatorViewBean> getIndicatorViewBeans() {
        return mIndicatorViewBeans;
    }

    public static List<InfoBean> getInfoBeans() {
        return mInfoBeans;
    }

    public static Map<Integer, NGPTipsBean> getSRTopTipsBeans() {
        return sSRTopTipsBeans;
    }

    public static Map<Integer, NGPTipsBean> getSRBottomTipsBeans() {
        return sSRBottomTipsBeans;
    }

    public static Set<Integer> getNGP20Beans() {
        return sNGP20Beans;
    }

    public static Set<Integer> getNGP22Beans() {
        return sNGP22Beans;
    }

    public static Set<Integer> getNGP23Beans() {
        return sNGP23Beans;
    }

    public static Map<Integer, DrivingModeBean> getDrivingModeBeans() {
        return sDrivingModeBeans;
    }

    public static void initConfigData() {
        initConfigInfoData();
        initConfigWaringData();
        initConfigIndicatorViewData();
        initConfigAllIndicatorData();
        initConfigDynamicIndicatorData();
        initConfigSensorsData();
        initAdasData();
        intiCarBodyProp();
        initConfigSensorText();
        initDrivingMode();
        if (BaseConfig.getInstance().isSupportNaviSR()) {
            initSRTopTipsInfoData();
            initSRBottomTipsInfoData();
            initSRNGP20Data();
            initSRNGP22Data();
            initSRNGP23Data();
        }
    }

    private static void initConfigSensorText() {
        try {
            sSensorFaultTexts = (Map) GsonUtil.fromJson(AssetUtil.loadAsset(ASSET_SENSOR_TEXT_URL), new TypeToken<HashMap<Integer, String>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.1
            }.getType());
        } catch (Exception e) {
            XILog.d(TAG, "initConfigSensorText loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initDrivingMode() {
        List<DrivingModeBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSET_DRIVING_MODE_URL), new TypeToken<List<DrivingModeBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.2
        }.getType());
        if (list == null || list.size() == 0) {
            XILog.d(TAG, "drivingModeBeans is null or size is 0");
            return;
        }
        for (DrivingModeBean drivingModeBean : list) {
            sDrivingModeBeans.put(Integer.valueOf(drivingModeBean.getId()), drivingModeBean);
        }
    }

    private static void initCarBodyColorProp(String str, Map<Integer, CarBodyColorBean> map) {
        try {
            List<CarBodyColorBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(str), new TypeToken<List<CarBodyColorBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.3
            }.getType());
            if (list != null && list.size() != 0) {
                for (CarBodyColorBean carBodyColorBean : list) {
                    if (carBodyColorBean == null) {
                        XILog.d(TAG, "carBodyColorBeans is null");
                    } else {
                        if (!TextUtils.isEmpty(carBodyColorBean.getImgResFrontName())) {
                            carBodyColorBean.setImgResFrontId(ResUtil.getDrawableResByName(carBodyColorBean.getImgResFrontName()));
                        } else {
                            XILog.d(TAG, "carBodyColorBeans img res front name is empty");
                        }
                        if (!TextUtils.isEmpty(carBodyColorBean.getImgResFullName())) {
                            carBodyColorBean.setImgResFullId(ResUtil.getDrawableResByName(carBodyColorBean.getImgResFullName()));
                        } else {
                            XILog.d(TAG, "carBodyColorBeans img res full name is empty");
                        }
                        if (!TextUtils.isEmpty(carBodyColorBean.getImgResRearName())) {
                            carBodyColorBean.setImgResRearId(ResUtil.getDrawableResByName(carBodyColorBean.getImgResRearName()));
                        } else {
                            XILog.d(TAG, "carBodyColorBeans img res real name is empty");
                        }
                        map.put(Integer.valueOf(carBodyColorBean.getId()), carBodyColorBean);
                    }
                }
                return;
            }
            XILog.d(TAG, "carBodyColorBeans is null or size is 0");
        } catch (Exception e) {
            XILog.d(TAG, "initCarBodyProp loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void intiCarBodyProp() {
        initCarBodyColorProp(ASSETS_CAR_BODY_FRONT_COLOR_URL, sCarFrontBodyColorBeans);
    }

    private static void initConfigSensorsData() {
        try {
            List<SensorBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSET_SENSOR_URL), new TypeToken<List<SensorBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.4
            }.getType());
            if (list != null && list.size() != 0) {
                for (SensorBean sensorBean : list) {
                    if (sensorBean != null) {
                        sSensorBeanMap.put(Integer.valueOf(sensorBean.getSensorId()), sensorBean);
                    } else {
                        XILog.d(TAG, "sensorBean is null");
                    }
                }
                return;
            }
            XILog.d(TAG, "sensorBeans is null or size is 0");
        } catch (Exception e) {
            XILog.d(TAG, "initConfigSensorsData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initConfigWaringData() {
        try {
            List<WaringBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSET_WARNING_URL), new TypeToken<List<WaringBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.5
            }.getType());
            if (list != null && list.size() != 0) {
                WaringBean waringBean = new WaringBean();
                waringBean.setShow(false);
                waringBean.setId(-1);
                sWaringBeanMap.put(-1, waringBean);
                for (WaringBean waringBean2 : list) {
                    if (waringBean2 == null) {
                        XILog.d(TAG, "waringBean is null");
                    } else {
                        waringBean2.setShow(true);
                        if (!TextUtils.isEmpty(waringBean2.getImgResName())) {
                            waringBean2.setImgResId(ResUtil.getDrawableResByName(waringBean2.getImgResName()));
                        } else {
                            XILog.d(TAG, "waringBean img res name is empty");
                        }
                        sWaringBeanMap.put(Integer.valueOf(waringBean2.getId()), waringBean2);
                    }
                }
                return;
            }
            XILog.d(TAG, "waringBeanList is null or size is 0");
        } catch (Exception e) {
            XILog.d(TAG, "initConfigWaringData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initConfigInfoData() {
        try {
            mInfoBeans = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_INFO_URL), new TypeToken<List<InfoBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.6
            }.getType());
        } catch (Exception e) {
            XILog.d(TAG, "initConfigInfoData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initSRNGP20Data() {
        try {
            sNGP20Beans = (Set) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_SR_NGP_20_VALUE_URL), new TypeToken<Set<Integer>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.7
            }.getType());
        } catch (Exception e) {
            XILog.d(TAG, "initSRNGP20Data loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initSRNGP22Data() {
        try {
            sNGP22Beans = (Set) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_SR_NGP_22_VALUE_URL), new TypeToken<Set<Integer>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.8
            }.getType());
        } catch (Exception e) {
            XILog.d(TAG, "initSRNGP22Data loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initSRNGP23Data() {
        try {
            sNGP23Beans = (Set) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_SR_NGP_23_VALUE_URL), new TypeToken<Set<Integer>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.9
            }.getType());
        } catch (Exception e) {
            XILog.d(TAG, "initSRNGP23Data loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initSRTopTipsInfoData() {
        try {
            List<NGPTipsBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_SR_TOP_TIPS_URL), new TypeToken<List<NGPTipsBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.10
            }.getType());
            if (list != null && list.size() != 0) {
                for (NGPTipsBean nGPTipsBean : list) {
                    if (nGPTipsBean == null) {
                        XILog.d(TAG, "bottomTipsBean is null");
                    } else {
                        if (!TextUtils.isEmpty(nGPTipsBean.getImgResName())) {
                            nGPTipsBean.setImgResId(ResUtil.getDrawableResByName(nGPTipsBean.getImgResName()));
                        } else {
                            XILog.d(TAG, "bottomTipsBean img res name is empty");
                        }
                        sSRTopTipsBeans.put(Integer.valueOf(nGPTipsBean.getId()), nGPTipsBean);
                    }
                }
                return;
            }
            XILog.d(TAG, "bottomTipsBeanList is null or size is 0");
        } catch (Exception e) {
            XILog.d(TAG, "initSRTopTipsInfoData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initSRBottomTipsInfoData() {
        try {
            List<NGPTipsBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_SR_BOTTOM_TIPS_URL), new TypeToken<List<NGPTipsBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.11
            }.getType());
            if (list != null && list.size() != 0) {
                for (NGPTipsBean nGPTipsBean : list) {
                    if (nGPTipsBean == null) {
                        XILog.d(TAG, "tipsBean is null");
                    } else {
                        if (!TextUtils.isEmpty(nGPTipsBean.getImgResName())) {
                            nGPTipsBean.setImgResId(ResUtil.getDrawableResByName(nGPTipsBean.getImgResName()));
                        } else {
                            XILog.d(TAG, "tipsBean img res name is empty");
                        }
                        sSRBottomTipsBeans.put(Integer.valueOf(nGPTipsBean.getId()), nGPTipsBean);
                    }
                }
                return;
            }
            XILog.d(TAG, "tipsBeanList is null or size is 0");
        } catch (Exception e) {
            XILog.d(TAG, "initConfigNGPBottomTipsData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initConfigAllIndicatorData() {
        try {
            initConfigIndicator(sAllIndicatorBeanMap, (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_INDICATOR_URL), new TypeToken<List<IndicatorBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.12
            }.getType()));
        } catch (Exception e) {
            XILog.d(TAG, "initConfigFixedIndicatorData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initConfigIndicatorViewData() {
        try {
            mIndicatorViewBeans = (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_INDICATOR_VIEW_URL), new TypeToken<List<IndicatorViewBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.13
            }.getType());
        } catch (Exception e) {
            XILog.d(TAG, "initConfigIndicatorViewData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initConfigDynamicIndicatorData() {
        try {
            initConfigIndicator(sDynamicBeanMap, (List) GsonUtil.fromJson(AssetUtil.loadAsset(ASSETS_INDICATOR_DYNAMIC_URL), new TypeToken<List<IndicatorBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.14
            }.getType()));
        } catch (Exception e) {
            XILog.d(TAG, "initConfigDynamicIndicatorData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void initConfigIndicator(Map<String, IndicatorBean> map, List<IndicatorBean> list) {
        if (list == null || list.size() == 0) {
            XILog.d(TAG, "all IndicatorBean is null or size is 0");
            return;
        }
        for (IndicatorBean indicatorBean : list) {
            if (indicatorBean == null) {
                XILog.d(TAG, "indicatorBean is null");
            } else {
                if (!TextUtils.isEmpty(indicatorBean.getImgResName())) {
                    indicatorBean.setImgResId(ResUtil.getDrawableResByName(indicatorBean.getImgResName()));
                } else {
                    XILog.d(TAG, "indicatorBean img res name is empty");
                }
                map.put(indicatorBean.getInstrumentId(), indicatorBean);
            }
        }
    }

    private static void initAdasData() {
        initCcData(ASSETS_ADAS_LCC_STATE_URL, sAdasLCCBeanMap);
        initCcData(ASSETS_ADAS_CC_STATE_URL, sAdasCCBeanMap);
        initCcData(ASSETS_ADAS_ACC_STATE_URL, sAdasACCBeanMap);
        initCcData(ASSETS_ADAS_NGP_STATE_URL, sAdasNGPBeanMap);
        initCcData(ASSETS_TSR_FORBID_URL, sTsrForbidMap);
        initCcData(ASSETS_TSR_WARNING_URL, sTsrWarningMap);
        initCcData(ASSETS_TSR_RATE_LIMIT_URL, sTsrRateLimitMap);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public static void initCcData(String str, Map<Integer, AdasCcBean> map) {
        try {
            List<AdasCcBean> list = (List) GsonUtil.fromJson(AssetUtil.loadAsset(str), new TypeToken<List<AdasCcBean>>() { // from class: com.xiaopeng.instrument.manager.DataConfigManager.15
            }.getType());
            if (list != null && list.size() != 0) {
                for (AdasCcBean adasCcBean : list) {
                    if (adasCcBean == null) {
                        XILog.d(TAG, "adasCcBean is null");
                    } else {
                        if (!TextUtils.isEmpty(adasCcBean.getImgResName())) {
                            adasCcBean.setImgResId(ResUtil.getDrawableResByName(adasCcBean.getImgResName()));
                        } else {
                            XILog.d(TAG, "adas lCC Bean img res name is empty");
                        }
                        map.put(Integer.valueOf(adasCcBean.getId()), adasCcBean);
                        XILog.d(TAG, adasCcBean.toString());
                    }
                }
                return;
            }
            XILog.d(TAG, "all adas LCC Beans is null or size is 0");
        } catch (Exception e) {
            XILog.d(TAG, "initLccData loadAsset --> " + e.getMessage());
            e.printStackTrace();
        }
    }
}
