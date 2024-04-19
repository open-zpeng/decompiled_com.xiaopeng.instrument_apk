package com.xiaopeng.instrument.onlyfortest;

import android.content.Intent;
import android.util.Log;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.instrument.manager.XpuInstrumentClient;
import com.xiaopeng.instrument.view.FragmentType;
import java.lang.reflect.Method;
import java.util.Arrays;
/* loaded from: classes.dex */
public class TestXpuHelper {
    private static final String INIT = "init";
    private static final String PARAM = "param";
    private static final String SEND_BOTTOM_TIPS_SIGN = "sendBottomTipsSign";
    private static final String SEND_SPEED_LIMIT_SIGN = "sendSpeedLimitSign";
    private static final String SEND_TRAFFIC_SIGN = "sendTrafficSign";
    private static final String TAG = "com.xiaopeng.instrument.onlyfortest.TestXpuHelper";

    public static void doAction(Intent intent) {
        if (!BaseConfig.getInstance().isSupportNaviSR()) {
            Log.i(TAG, "不支持NaviSR模式，请打开开关后，在测试！！！");
            return;
        }
        try {
            int[] intArray = intent.getExtras().getIntArray(PARAM);
            Log.i(TAG, "xpu测试数据： values = " + Arrays.toString(intArray));
            XpuInstrumentClient xpuInstrumentClient = XpuInstrumentClient.getInstance();
            xpuInstrumentClient.setFragmentType(FragmentType.NAVI_SR);
            doInit(xpuInstrumentClient);
            doMethod(intArray, xpuInstrumentClient);
        } catch (Exception e) {
            Log.i(TAG, "执行Xpu测试代码出错");
            e.printStackTrace();
        }
    }

    private static void doInit(XpuInstrumentClient xpuInstrumentClient) throws Exception {
        Method declaredMethod = xpuInstrumentClient.getClass().getDeclaredMethod(INIT, new Class[0]);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(xpuInstrumentClient, new Object[0]);
    }

    private static void doMethod(int[] iArr, XpuInstrumentClient xpuInstrumentClient) throws Exception {
        Method declaredMethod = xpuInstrumentClient.getClass().getDeclaredMethod(SEND_BOTTOM_TIPS_SIGN, Integer.TYPE, Integer.TYPE, Integer.TYPE);
        declaredMethod.setAccessible(true);
        declaredMethod.invoke(xpuInstrumentClient, Integer.valueOf(iArr[0]), Integer.valueOf(iArr[1]), Integer.valueOf(iArr[2]));
        Method declaredMethod2 = xpuInstrumentClient.getClass().getDeclaredMethod(SEND_TRAFFIC_SIGN, Integer.TYPE);
        declaredMethod2.setAccessible(true);
        declaredMethod2.invoke(xpuInstrumentClient, Integer.valueOf(iArr[3]));
        Method declaredMethod3 = xpuInstrumentClient.getClass().getDeclaredMethod(SEND_SPEED_LIMIT_SIGN, Integer.TYPE);
        declaredMethod3.setAccessible(true);
        declaredMethod3.invoke(xpuInstrumentClient, Integer.valueOf(iArr[4]));
    }
}
