package com.xiaopeng.instrument.view;

import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class ChargeFragment extends BaseChargeFragment {
    private static final String TAG = "ChargeFragment";

    @Override // com.xiaopeng.instrument.view.BaseChargeFragment
    protected void updateChargeGun(int i) {
        super.updateChargeGun(i);
        boolean z = i == 1;
        boolean z2 = i == 2;
        boolean z3 = i == 2 || i == 1;
        int supportCharge = BaseConfig.getInstance().getSupportCharge();
        if (supportCharge == 0) {
            this.mIvChargingPortCoverLeft.setVisibility(z ? 0 : 4);
            this.mIvChargingPortCoverRight.setVisibility(z2 ? 0 : 4);
        } else if (supportCharge == 1) {
            this.mIvChargingPortCoverLeft.setVisibility(z2 ? 0 : 4);
            this.mIvChargingPortCoverRight.setVisibility(z ? 0 : 4);
        } else if (supportCharge == 2) {
            this.mIvChargingPortCoverLeft.setVisibility(z3 ? 0 : 4);
            this.mIvChargingPortCoverRight.setVisibility(4);
        } else if (supportCharge == 3) {
            this.mIvChargingPortCoverRight.setVisibility(z3 ? 0 : 4);
            this.mIvChargingPortCoverLeft.setVisibility(4);
        } else {
            XILog.d(TAG, "updateChargeGun supportCharge: " + supportCharge);
        }
    }
}
