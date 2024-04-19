package com.xiaopeng.instrument.utils.delegate;

import com.xiaopeng.instrument.utils.LanguageUtil;
import com.xiaopeng.instrument.utils.delegate.impl.CnStringUtilDelegateImpl;
import com.xiaopeng.instrument.utils.delegate.impl.EnStringUtilDelegateImpl;
/* loaded from: classes.dex */
public class StringUtilDelegateFactory {
    public static IStringUtilDelegate createStringUtilDelegate() {
        if (LanguageUtil.isEn()) {
            return new EnStringUtilDelegateImpl();
        }
        return new CnStringUtilDelegateImpl();
    }
}
