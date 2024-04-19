package com.xiaopeng.instrument.utils;

import com.google.gson.Gson;
import java.lang.reflect.Type;
/* loaded from: classes.dex */
public class GsonUtil {
    private static final String TAG = "GsonUtil";
    private static Gson sGSon = new Gson();

    public static <T> T fromJson(String str, Class<T> cls) {
        return (T) sGSon.fromJson(str, (Class<Object>) cls);
    }

    public static <T> T fromJson(String str, Type type) {
        return (T) sGSon.fromJson(str, type);
    }

    public static String toJson(Object obj) {
        return sGSon.toJson(obj);
    }
}
