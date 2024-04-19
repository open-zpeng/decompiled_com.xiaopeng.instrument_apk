package com.xiaopeng.instrument.delegate;

import com.xiaopeng.instrument.view.FragmentType;
import com.xiaopeng.instrument.view.ITNChargeFragment;
import com.xiaopeng.instrument.view.MainFragment;
/* loaded from: classes.dex */
public class ITNMainActivityDelegate extends DefaultMainActivityDelegate {
    @Override // com.xiaopeng.instrument.delegate.DefaultMainActivityDelegate, com.xiaopeng.instrument.delegate.IMainActivityDelegate
    public Class getFragmentClassByType(FragmentType fragmentType) {
        if (fragmentType == FragmentType.MAIN) {
            return MainFragment.class;
        }
        if (fragmentType == FragmentType.CHARGE) {
            return ITNChargeFragment.class;
        }
        return super.getFragmentClassByType(fragmentType);
    }
}
