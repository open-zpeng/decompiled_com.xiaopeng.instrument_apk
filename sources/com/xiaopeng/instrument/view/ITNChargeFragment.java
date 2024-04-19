package com.xiaopeng.instrument.view;

import androidx.lifecycle.ViewModelProvider;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.instrument.viewmodel.ChargeViewModel;
import com.xiaopeng.instrument.viewmodel.ITNChargeViewModel;
/* loaded from: classes.dex */
public class ITNChargeFragment extends ChargeFragment {
    @Override // com.xiaopeng.instrument.view.BaseChargeFragment
    protected void initViewModel() {
        this.mChargeViewModel = (ChargeViewModel) new ViewModelProvider(this).get(ITNChargeViewModel.class);
    }

    @Override // com.xiaopeng.instrument.view.ChargeFragment, com.xiaopeng.instrument.view.BaseChargeFragment
    protected void updateChargeGun(int i) {
        if (isSceneLegal()) {
            int supportCharge = BaseConfig.getInstance().getSupportCharge();
            boolean z = true;
            if (i != 2 && i != 1) {
                z = false;
            }
            if (supportCharge == 2) {
                this.mIvChargingPortCoverLeft.setVisibility(z ? 0 : 4);
                this.mIvChargingPortCoverRight.setVisibility(4);
                return;
            }
            this.mIvChargingPortCoverRight.setVisibility(z ? 0 : 4);
            this.mIvChargingPortCoverLeft.setVisibility(4);
        }
    }
}
