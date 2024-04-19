package com.xiaopeng.instrument.manager;

import com.xiaopeng.instrument.bean.AdasCcBean;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class ITNDataConfigManager {
    private static final String ASSETS_ADAS_ALC_STATE_URL = "adas_alc_state.json";
    private static final String ASSETS_ADAS_LCC_FAILURE_STATE_URL = "adas_lcc_failure_state.json";
    private static Map<Integer, AdasCcBean> sAdasLCCFailureBeanMap = new ConcurrentHashMap();
    private static Map<Integer, AdasCcBean> sAdasALCBeanMap = new ConcurrentHashMap();

    public static Map<Integer, AdasCcBean> getAdasLCCFailureBeanMap() {
        return sAdasLCCFailureBeanMap;
    }

    public static Map<Integer, AdasCcBean> getAdasALCBeanMap() {
        return sAdasALCBeanMap;
    }

    public static void initConfigData() {
        initAdasData();
    }

    private static void initAdasData() {
        DataConfigManager.initCcData(ASSETS_ADAS_LCC_FAILURE_STATE_URL, sAdasLCCFailureBeanMap);
        DataConfigManager.initCcData(ASSETS_ADAS_ALC_STATE_URL, sAdasALCBeanMap);
    }
}
