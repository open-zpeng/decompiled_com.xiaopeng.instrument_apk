package com.xiaopeng.instrument.onlyfortest;

import android.content.Intent;
import android.os.Bundle;
import com.xiaopeng.cluster.utils.XILog;
import java.lang.reflect.Method;
import java.util.HashMap;
/* loaded from: classes.dex */
public class TestAllFunctionHelper {
    private static final String METHOD_NAME = "methodName";
    private static final String METHOD_PARAMETER = "param";
    private static final String METHOD_PARAMETER_1 = "param1";
    private static final String METHOD_PARAMETER_2 = "param2";
    private static final String TAG = "TestAllFunctionHelper";

    public static void doAction(Intent intent) {
        try {
            HashMap hashMap = new HashMap();
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String str : extras.keySet()) {
                    hashMap.put(str, extras.get(str));
                }
            }
            String str2 = (String) hashMap.get(METHOD_NAME);
            int size = hashMap.size() - 1;
            Class<?> cls = Class.forName("com.xiaopeng.cluster.JniInterface");
            Object obj = hashMap.get(METHOD_PARAMETER);
            if (size == 1) {
                Method method = cls.getMethod(str2, convertToClass(obj));
                method.setAccessible(true);
                method.invoke(null, obj);
            } else if (size == 2) {
                Object obj2 = hashMap.get(METHOD_PARAMETER_1);
                Method method2 = cls.getMethod(str2, convertToClass(obj), convertToClass(obj2));
                method2.setAccessible(true);
                method2.invoke(null, obj, obj2);
            } else if (size == 3) {
                Object obj3 = hashMap.get(METHOD_PARAMETER_1);
                Object obj4 = hashMap.get(METHOD_PARAMETER_2);
                Method method3 = cls.getMethod(str2, convertToClass(obj), convertToClass(obj3), convertToClass(obj4));
                method3.setAccessible(true);
                method3.invoke(null, obj, obj3, obj4);
            }
        } catch (Exception e) {
            XILog.i(TAG, "方法，调用失败");
            e.printStackTrace();
        }
    }

    private static Class convertToClass(Object obj) {
        if (obj instanceof Boolean) {
            return Boolean.TYPE;
        }
        if (obj instanceof Boolean[]) {
            return boolean[].class;
        }
        if (obj instanceof Integer) {
            return Integer.TYPE;
        }
        if (obj instanceof Integer[]) {
            return int[].class;
        }
        if (obj instanceof Byte) {
            return Byte.TYPE;
        }
        if (obj instanceof Byte[]) {
            return byte[].class;
        }
        if (obj instanceof Float) {
            return Float.TYPE;
        }
        if (obj instanceof Float[]) {
            return float[].class;
        }
        return obj.getClass();
    }
}
