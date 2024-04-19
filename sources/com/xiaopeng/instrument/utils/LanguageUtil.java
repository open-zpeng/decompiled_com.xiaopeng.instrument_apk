package com.xiaopeng.instrument.utils;

import com.xiaopeng.instrument.App;
/* loaded from: classes.dex */
public class LanguageUtil {
    public static boolean isEn() {
        return App.getInstance().getApplicationContext().getResources().getConfiguration().getLocales().get(0).getLanguage().equalsIgnoreCase("en");
    }
}
