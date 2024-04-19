package com.xiaopeng.cluster.config;

import android.os.SystemProperties;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public abstract class BaseConfig {
    public static final int CHARGE_LEFT_SLOW_RIGHT_FAST = 0;
    public static final int CHARGE_ONLY_LEFT = 2;
    public static final int CHARGE_ONLY_RIGHT = 3;
    public static final int CHARGE_RIGHT_SLOW_LEFT_FAST = 1;
    protected static final String TAG = "BaseConfig";
    private int[] drivingModesList;

    public abstract float getInvalidAverageEnergyValue();

    public abstract int getSupportCharge();

    protected abstract int[] getSupportDrivingModes();

    public boolean isSupportArcPowerConsumptionCurveView() {
        return true;
    }

    public boolean isSupportCLTC() {
        return true;
    }

    public boolean isSupportDecimalRemainDistance() {
        return false;
    }

    public boolean isSupportIslc() {
        return false;
    }

    public boolean isSupportNaviSR() {
        return false;
    }

    public boolean isSupportNoSRToShowTrafficLight() {
        return false;
    }

    public boolean isSupportPerspChargeView() {
        return false;
    }

    public boolean isSupportShowSpeedUseLeftScroll() {
        return false;
    }

    public boolean isSupportShowTime() {
        return false;
    }

    public boolean isSupportSpace() {
        return false;
    }

    static /* synthetic */ BaseConfig access$100() {
        return createCarConfig();
    }

    public static BaseConfig getInstance() {
        return SingletonHolder.sInstance;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private static BaseConfig createCarConfig() {
        char c;
        String hardwareCarType = getHardwareCarType();
        switch (hardwareCarType.hashCode()) {
            case 67044:
                if (hardwareCarType.equals("D55")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 67915:
                if (hardwareCarType.equals("E28")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 67946:
                if (hardwareCarType.equals("E38")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 68899:
                if (hardwareCarType.equals("F30")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            XILog.e(TAG, "carType: E38");
            return isEURegion() ? new E38VConfig() : new E38Config();
        } else if (c == 1) {
            XILog.e(TAG, "carType: E28A");
            return isEURegion() ? new E28aVConfig() : new E28aConfig();
        } else if (c == 2) {
            XILog.e(TAG, "carType: F30");
            return isEURegion() ? new F30VConfig() : new F30Config();
        } else {
            XILog.d(TAG, "carType: D55");
            return isEURegion() ? new D55VConfig() : new D55Config();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0039 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003a A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String getHardwareCarType() {
        /*
            java.lang.String r0 = ""
            java.lang.String r1 = android.car.Car.getHardwareCarType()     // Catch: java.lang.Exception -> L1d
            boolean r2 = r0.equals(r1)     // Catch: java.lang.Exception -> L1b
            if (r2 != 0) goto L37
            if (r1 == 0) goto L37
            int r2 = r1.length()     // Catch: java.lang.Exception -> L1b
            r3 = 3
            if (r2 <= r3) goto L37
            r2 = 0
            java.lang.String r1 = r1.substring(r2, r3)     // Catch: java.lang.Exception -> L1b
            goto L37
        L1b:
            r2 = move-exception
            goto L1f
        L1d:
            r2 = move-exception
            r1 = r0
        L1f:
            java.lang.String r3 = com.xiaopeng.cluster.config.BaseConfig.TAG
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            java.lang.String r5 = "can not get HardwareCarType error = "
            java.lang.StringBuilder r4 = r4.append(r5)
            java.lang.StringBuilder r2 = r4.append(r2)
            java.lang.String r2 = r2.toString()
            com.xiaopeng.cluster.utils.XILog.e(r3, r2)
        L37:
            if (r1 != 0) goto L3a
            return r0
        L3a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaopeng.cluster.config.BaseConfig.getHardwareCarType():java.lang.String");
    }

    private static boolean isEURegion() {
        return "EU".equals(getRegion());
    }

    private static String getRegion() {
        try {
            String str = SystemProperties.get("ro.xiaopeng.software", "");
            return (str == null || str.isEmpty()) ? str : str.substring(7, 9);
        } catch (Exception e) {
            XILog.e(TAG, "can not get getRegion error = " + e);
            return "";
        }
    }

    public boolean isSupportITN() {
        return isEURegion();
    }

    public boolean isSupportCurrentDrivingMode(int i) {
        if (this.drivingModesList == null) {
            this.drivingModesList = getSupportDrivingModes();
        }
        for (int i2 : this.drivingModesList) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    /* loaded from: classes.dex */
    private static class SingletonHolder {
        private static final BaseConfig sInstance = BaseConfig.access$100();

        private SingletonHolder() {
        }
    }
}
