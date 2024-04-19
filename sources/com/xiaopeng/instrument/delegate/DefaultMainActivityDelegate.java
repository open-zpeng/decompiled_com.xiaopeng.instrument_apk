package com.xiaopeng.instrument.delegate;

import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.view.ChargeFragment;
import com.xiaopeng.instrument.view.FragmentType;
import com.xiaopeng.instrument.view.MainFragment;
import com.xiaopeng.instrument.view.NaviSRFragment;
/* loaded from: classes.dex */
public class DefaultMainActivityDelegate implements IMainActivityDelegate {
    public static final String TAG = "DefaultMainActivityDelegate";

    @Override // com.xiaopeng.instrument.delegate.IMainActivityDelegate
    public Class getFragmentClassByType(FragmentType fragmentType) {
        if (fragmentType == FragmentType.MAIN) {
            return MainFragment.class;
        }
        if (fragmentType == FragmentType.CHARGE) {
            return ChargeFragment.class;
        }
        if (fragmentType == FragmentType.NAVI_SR) {
            return NaviSRFragment.class;
        }
        XILog.e(TAG, "Unknown fragment type '" + fragmentType + "', use MainFragment instead");
        return MainFragment.class;
    }
}
