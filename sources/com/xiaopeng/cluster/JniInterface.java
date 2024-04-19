package com.xiaopeng.cluster;

import com.xiaopeng.cluster.utils.XILog;
import java.util.Arrays;
/* loaded from: classes.dex */
class JniInterface {
    private static final String JNI_LIB_NAME = "JniCluster";
    private static final String TAG = "JniInterface";
    private static IClusterService sClusterService = null;
    private static boolean sInitOnce = true;

    public static native void init();

    public static native void refreshData();

    public static native void reloadData();

    JniInterface() {
    }

    static {
        System.loadLibrary(JNI_LIB_NAME);
    }

    public static void setClusterService(IClusterService iClusterService) {
        XILog.d(TAG, "setClusterService");
        sClusterService = iClusterService;
        if (sInitOnce) {
            sInitOnce = false;
            init();
        }
    }

    public static void onLeftListVisible(boolean z) {
        XILog.i(TAG, "onLeftListVisible  " + z);
        sClusterService.onLeftListVisible(z);
    }

    public static void onLeftListIndex(int i) {
        XILog.i(TAG, "onLeftListIndex  " + i);
        sClusterService.onLeftListIndex(i);
    }

    public static void onLeftListSensorFault(boolean z) {
        XILog.d(TAG, "onLeftListSensorFault  " + z);
        sClusterService.onLeftListSensorFault(z);
    }

    public static void onLeftCardIndex(int i) {
        XILog.i(TAG, "onLeftCardIndex  " + i);
        sClusterService.onLeftCardIndex(i);
    }

    public static void onLeftCardVisible(boolean z) {
        XILog.i(TAG, "onLeftCardVisible  " + z);
        sClusterService.onLeftCardVisible(z);
    }

    public static void onRightListVisible(boolean z) {
        XILog.i(TAG, "onRightListVisible  " + z);
        sClusterService.onRightListVisible(z);
    }

    public static void onRightListIndex(int i) {
        XILog.i(TAG, "onRightListIndex  " + i);
        sClusterService.onRightListIndex(i);
    }

    public static void onRightCardIndex(int i) {
        XILog.i(TAG, "onRightCardIndex  " + i);
        sClusterService.onRightCardIndex(i);
    }

    public static void onRightCardVisible(boolean z) {
        XILog.d(TAG, "onRightCardVisible  " + z);
        sClusterService.onRightCardVisible(z);
    }

    public static void onNaviStart() {
        XILog.d(TAG, "onNaviStart");
        sClusterService.onNaviStart();
    }

    public static void onNaviSRMode(boolean z) {
        XILog.d(TAG, "onNaviSRMode: " + z);
        sClusterService.onNaviSRMode(z);
    }

    public static void onNaviSRTraffic(int i) {
        XILog.d(TAG, "onNaviSRTraffic type： " + i);
        sClusterService.onNaviSRTraffic(i);
    }

    public static void onNavigationToast(String str, int i, String str2) {
        XILog.d(TAG, "onNavigationToast navigationdistance: " + str + ",navigationdistanceunit:" + i + ",navigationroadname:" + str2);
        sClusterService.onNavigationToast(str, i, str2);
    }

    public static void onNavigationDriveSide(boolean z) {
        XILog.d(TAG, "onNavigationDriveSide  " + z);
        sClusterService.onNavigationDriveSide(z);
    }

    public static void onNavigationArrowID(int i) {
        XILog.d(TAG, "onNavigationArrowID  " + i);
        sClusterService.onNavigationArrowID(i);
    }

    public static void onNavigationGuidanceVisible(boolean z) {
        XILog.d(TAG, "onNavigationGuidanceVisible  " + z);
        sClusterService.onNavigationGuidanceVisible(z);
    }

    public static void onRefreshImageGuidanceTexture(byte[] bArr, int i, int i2, int i3) {
        XILog.d(TAG, "onRefreshImageGuidanceTexture  format:" + i3);
        sClusterService.onRefreshImageGuidanceTexture(bArr, i, i2, i3);
    }

    public static void onRefreshImageNaviTexture(byte[] bArr, int i, int i2, int i3) {
        XILog.d(TAG, "onRefreshImageNaviTexture  format:" + i3);
        sClusterService.onRefreshImageNaviTexture(bArr, i, i2, i3);
    }

    public static void onNavigationNormalLane(int[] iArr, int[] iArr2, boolean z) {
        XILog.d(TAG, "onNavigationNormalLane  backlane size :" + iArr.length + " frontlane size:" + iArr2.length + " visible:" + z);
        sClusterService.onNavigationNormalLane(iArr, iArr2, z);
    }

    public static void onNavigationTollGateLane(int[] iArr, boolean z) {
        XILog.d(TAG, "onNavigationTollGateLane  tollGate size :" + iArr.length + " visible:" + z);
        sClusterService.onNavigationTollGateLane(iArr, z);
    }

    public static void onTireFLTireState(int i) {
        XILog.d(TAG, "onTireFLTireState  " + i);
        sClusterService.onTireFLTireState(i);
    }

    public static void onTireFLPressureState(int i) {
        XILog.d(TAG, "onTireFLPressureState  " + i);
        sClusterService.onTireFLPressureState(i);
    }

    public static void onTireFLPressure(String str) {
        XILog.d(TAG, "onTireFLPressure  " + str);
        sClusterService.onTireFLPressure(str);
    }

    public static void onTireFLPressureUnits(String str) {
        XILog.d(TAG, "onTireFLPressureUnits  " + str);
        sClusterService.onTireFLPressureUnits(str);
    }

    public static void onTireFLTemperaturesState(int i) {
        XILog.d(TAG, "onTireFLTemperaturesState  " + i);
        sClusterService.onTireFLTemperaturesState(i);
    }

    public static void onTireFLTemperatures(String str) {
        XILog.d(TAG, "onTireFLTemperatures  " + str);
        sClusterService.onTireFLTemperatures(str);
    }

    public static void onTireFLTemperaturesUnits(String str) {
        XILog.d(TAG, "onTireFLTemperaturesUnits  " + str);
        sClusterService.onTireFLTemperaturesUnits(str);
    }

    public static void onTireFRTireState(int i) {
        XILog.d(TAG, "onTireFRTireState  " + i);
        sClusterService.onTireFRTireState(i);
    }

    public static void onTireFRPressureState(int i) {
        XILog.d(TAG, "onTireFRPressureState  " + i);
        sClusterService.onTireFRPressureState(i);
    }

    public static void onTireFRPressure(String str) {
        XILog.d(TAG, "onTireFRPressure  " + str);
        sClusterService.onTireFRPressure(str);
    }

    public static void onTireFRPressureUnits(String str) {
        XILog.d(TAG, "onTireFRPressureUnits  " + str);
        sClusterService.onTireFRPressureUnits(str);
    }

    public static void onTireFRTemperaturesState(int i) {
        XILog.d(TAG, "onTireFRTemperaturesState  " + i);
        sClusterService.onTireFRTemperaturesState(i);
    }

    public static void onTireFRTemperatures(String str) {
        XILog.d(TAG, "onTireFRTemperatures  " + str);
        sClusterService.onTireFRTemperatures(str);
    }

    public static void onTireFRTemperaturesUnits(String str) {
        XILog.d(TAG, "onTireFRTemperaturesUnits  " + str);
        sClusterService.onTireFRTemperaturesUnits(str);
    }

    public static void onTireBLTireState(int i) {
        XILog.d(TAG, "onTireBLTireState  " + i);
        sClusterService.onTireBLTireState(i);
    }

    public static void onTireBLPressureState(int i) {
        XILog.d(TAG, "onTireBLPressureState  " + i);
        sClusterService.onTireBLPressureState(i);
    }

    public static void onTireBLPressure(String str) {
        XILog.d(TAG, "onTireBLPressure  " + str);
        sClusterService.onTireBLPressure(str);
    }

    public static void onTireBLPressureUnits(String str) {
        XILog.d(TAG, "onTireBLPressureUnits  " + str);
        sClusterService.onTireBLPressureUnits(str);
    }

    public static void onTireBLTemperaturesState(int i) {
        XILog.d(TAG, "onTireBLTemperaturesState  " + i);
        sClusterService.onTireBLTemperaturesState(i);
    }

    public static void onTireBLTemperatures(String str) {
        XILog.d(TAG, "onTireBLTemperatures  " + str);
        sClusterService.onTireBLTemperatures(str);
    }

    public static void onTireBLTemperaturesUnits(String str) {
        XILog.d(TAG, "onTireBLTemperaturesUnits  " + str);
        sClusterService.onTireBLTemperaturesUnits(str);
    }

    public static void onTireBRTireState(int i) {
        XILog.d(TAG, "onTireBRTireState  " + i);
        sClusterService.onTireBRTireState(i);
    }

    public static void onTireBRPressureState(int i) {
        XILog.d(TAG, "onTireBRPressureState  " + i);
        sClusterService.onTireBRPressureState(i);
    }

    public static void onTireBRPressure(String str) {
        XILog.d(TAG, "onTireBRPressure  " + str);
        sClusterService.onTireBRPressure(str);
    }

    public static void onTireBRPressureUnits(String str) {
        XILog.d(TAG, "onTireBRPressureUnits  " + str);
        sClusterService.onTireBRPressureUnits(str);
    }

    public static void onTireBRTemperaturesState(int i) {
        XILog.d(TAG, "onTireBRTemperaturesState  " + i);
        sClusterService.onTireBRTemperaturesState(i);
    }

    public static void onTireBRTemperatures(String str) {
        XILog.d(TAG, "onTireBRTemperatures  " + str);
        sClusterService.onTireBRTemperatures(str);
    }

    public static void onTireBRTemperaturesUnits(String str) {
        XILog.d(TAG, "onTireBRTemperaturesUnits  " + str);
        sClusterService.onTireBRTemperaturesUnits(str);
    }

    public static void onHoodEngine(boolean z) {
        XILog.d(TAG, "onHoodEngine  " + z);
        sClusterService.onHoodEngine(z);
    }

    public static void onHoodLeftCharge(int i) {
        XILog.d(TAG, "onHoodLeftCharge  " + i);
        sClusterService.onHoodLeftCharge(i);
    }

    public static void onHoodRightCharge(int i) {
        XILog.d(TAG, "onHoodRightCharge  " + i);
        sClusterService.onHoodRightCharge(i);
    }

    public static void onHoodTrunk(boolean z) {
        XILog.d(TAG, "onHoodTrunk  " + z);
        sClusterService.onHoodTrunk(z);
    }

    public static void onThisTimeValue(String str) {
        XILog.d(TAG, "onThisTimeValue  " + str);
        sClusterService.onThisTimeValue(str);
    }

    public static void onThisTimeUnits(String str) {
        XILog.d(TAG, "onThisTimeUnits  " + str);
        sClusterService.onThisTimeUnits(str);
    }

    public static void onAfterChargingValue(String str) {
        XILog.d(TAG, "onAfterChargingValue  " + str);
        sClusterService.onAfterChargingValue(str);
    }

    public static void onAfterChargingUnits(String str) {
        XILog.d(TAG, "onAfterChargingUnits  " + str);
        sClusterService.onAfterChargingUnits(str);
    }

    public static void onSubtotalAValue(String str) {
        XILog.d(TAG, "onSubtotalAValue  " + str);
        sClusterService.onSubtotalAValue(str);
    }

    public static void onSubtotalAUnits(String str) {
        XILog.d(TAG, "onSubtotalAUnits  " + str);
        sClusterService.onSubtotalAUnits(str);
    }

    public static void onSubtotalBValue(String str) {
        XILog.d(TAG, "onSubtotalBValue  " + str);
        sClusterService.onSubtotalBValue(str);
    }

    public static void onSubtotalBUnits(String str) {
        XILog.d(TAG, "onSubtotalBUnits  " + str);
        sClusterService.onSubtotalBUnits(str);
    }

    public static void onTotalValue(String str) {
        XILog.d(TAG, "onTotalValue  " + str);
        sClusterService.onTotalValue(str);
    }

    public static void onTotalUnits(String str) {
        XILog.d(TAG, "onTotalUnits  " + str);
        sClusterService.onTotalUnits(str);
    }

    public static void onAverageValue(String str) {
        XILog.d(TAG, "onAverageValue  " + str);
        sClusterService.onAverageValue(str);
    }

    public static void onAverageUnits(String str) {
        XILog.d(TAG, "onAverageUnits  " + str);
        sClusterService.onAverageUnits(str);
    }

    public static void onElapsedTimeValue(String str) {
        XILog.d(TAG, "onElapsedTimeValue  " + str);
        sClusterService.onElapsedTimeValue(str);
    }

    public static void onElapsedTimeUnits(String str) {
        XILog.d(TAG, "onElapsedTimeUnits  " + str);
        sClusterService.onElapsedTimeUnits(str);
    }

    @Deprecated
    public static void onMusicImageState(boolean z) {
        XILog.d(TAG, "onMusicImageState  " + z);
        sClusterService.onMusicImageState(z);
    }

    public static void onMusicPlayState(boolean z) {
        XILog.d(TAG, "onMusicPlayState  " + z);
        sClusterService.onMusicPlayState(z);
    }

    public static void onMusicBarVisible(boolean z) {
        XILog.d(TAG, "onMusicBarVisible  " + z);
        sClusterService.onMusicBarVisible(z);
    }

    public static void onMusicSoundState(int i) {
        XILog.d(TAG, "onMusicSoundState  " + i);
        sClusterService.onMusicSoundState(i);
    }

    public static void onMusicString1(String str) {
        XILog.d(TAG, "onMusicString1  " + str);
        sClusterService.onMusicString1(str);
    }

    public static void onMusicString2(String str) {
        XILog.d(TAG, "onMusicString2  " + str);
        sClusterService.onMusicString2(str);
    }

    public static void onMusicBarValue(int i) {
        XILog.d(TAG, "onMusicBarValue  " + i);
        sClusterService.onMusicBarValue(i);
    }

    public static void onRefreshImageMusicTexture(byte[] bArr, int i, int i2, int i3) {
        XILog.d(TAG, "onRefreshImageMusicTexture  format:" + i3 + "height " + i + "width " + i2);
        sClusterService.onRefreshImageMusicTexture(bArr, i, i2, i3);
    }

    public static void onMillimeterWaveRadarState(int i) {
        XILog.d(TAG, "onMillimeterWaveRadarState  " + i);
        sClusterService.onMillimeterWaveRadarState(i);
    }

    public static void onMillimeterWaveRadarFC(int i) {
        XILog.d(TAG, "onMillimeterWaveRadarFC  " + i);
        sClusterService.onMillimeterWaveRadarFC(i);
    }

    public static void onMillimeterWaveRadarFL(int i) {
        XILog.d(TAG, "onMillimeterWaveRadarFL  " + i);
        sClusterService.onMillimeterWaveRadarFL(i);
    }

    public static void onMillimeterWaveRadarFR(int i) {
        XILog.d(TAG, "onMillimeterWaveRadarFR  " + i);
        sClusterService.onMillimeterWaveRadarFR(i);
    }

    public static void onMillimeterWaveRadarRCL(int i) {
        XILog.d(TAG, "onMillimeterWaveRadarRCL  " + i);
        sClusterService.onMillimeterWaveRadarRCL(i);
    }

    public static void onMillimeterWaveRadarRCR(int i) {
        XILog.d(TAG, "onMillimeterWaveRadarRCR  " + i);
        sClusterService.onMillimeterWaveRadarRCR(i);
    }

    public static void onUltrasonicRadarState(int i) {
        XILog.d(TAG, "onUltrasonicRadarState  " + i);
        sClusterService.onUltrasonicRadarState(i);
    }

    public static void onUltrasonicRadarFCL(int i) {
        XILog.d(TAG, "onUltrasonicRadarFCL  " + i);
        sClusterService.onUltrasonicRadarFCL(i);
    }

    public static void onUltrasonicRadarFCR(int i) {
        XILog.d(TAG, "onUltrasonicRadarFCR  " + i);
        sClusterService.onUltrasonicRadarFCR(i);
    }

    public static void onUltrasonicRadarFOL(int i) {
        XILog.d(TAG, "onUltrasonicRadarFOL  " + i);
        sClusterService.onUltrasonicRadarFOL(i);
    }

    public static void onUltrasonicRadarFOR(int i) {
        XILog.d(TAG, "onUltrasonicRadarFOR  " + i);
        sClusterService.onUltrasonicRadarFOR(i);
    }

    public static void onUltrasonicRadarFSL(int i) {
        XILog.d(TAG, "onUltrasonicRadarFSL  " + i);
        sClusterService.onUltrasonicRadarFSL(i);
    }

    public static void onUltrasonicRadarFSR(int i) {
        XILog.d(TAG, "onUltrasonicRadarFSR  " + i);
        sClusterService.onUltrasonicRadarFSR(i);
    }

    public static void onUltrasonicRadarRCL(int i) {
        XILog.d(TAG, "onUltrasonicRadarRCL  " + i);
        sClusterService.onUltrasonicRadarRCL(i);
    }

    public static void onUltrasonicRadarRCR(int i) {
        XILog.d(TAG, "onUltrasonicRadarRCR  " + i);
        sClusterService.onUltrasonicRadarRCR(i);
    }

    public static void onUltrasonicRadarROL(int i) {
        XILog.d(TAG, "onUltrasonicRadarROL  " + i);
        sClusterService.onUltrasonicRadarROL(i);
    }

    public static void onUltrasonicRadarROR(int i) {
        XILog.d(TAG, "onUltrasonicRadarROR  " + i);
        sClusterService.onUltrasonicRadarROR(i);
    }

    public static void onUltrasonicRadarRSL(int i) {
        XILog.d(TAG, "onUltrasonicRadarRSL  " + i);
        sClusterService.onUltrasonicRadarRSL(i);
    }

    public static void onUltrasonicRadarRSR(int i) {
        XILog.d(TAG, "onUltrasonicRadarRSR  " + i);
        sClusterService.onUltrasonicRadarRSR(i);
    }

    public static void onSensorFaultCarState(int i) {
        XILog.d(TAG, "onSensorFaultCarState  " + i);
        sClusterService.onSensorFaultCarState(i);
    }

    public static void onSmartCameraState(int i) {
        XILog.d(TAG, "onSmartCameraState  " + i);
        sClusterService.onSmartCameraState(i);
    }

    public static void onSmartCameraSite1(int i) {
        XILog.d(TAG, "onSmartCameraSite1  " + i);
        sClusterService.onSmartCameraSite1(i);
    }

    public static void onSmartCameraSite2(int i) {
        XILog.d(TAG, "onSmartCameraSite2  " + i);
        sClusterService.onSmartCameraSite2(i);
    }

    public static void onSmartCameraSite3(int i) {
        XILog.d(TAG, "onSmartCameraSite3  " + i);
        sClusterService.onSmartCameraSite3(i);
    }

    public static void onSmartCameraSite4(int i) {
        XILog.d(TAG, "onSmartCameraSite4  " + i);
        sClusterService.onSmartCameraSite4(i);
    }

    public static void onSmartCameraSite5(int i) {
        XILog.d(TAG, "onSmartCameraSite5  " + i);
        sClusterService.onSmartCameraSite5(i);
    }

    public static void onSmartCameraSite6(int i) {
        XILog.d(TAG, "onSmartCameraSite6  " + i);
        sClusterService.onSmartCameraSite6(i);
    }

    public static void onSmartCameraSite7(int i) {
        XILog.d(TAG, "onSmartCameraSite7  " + i);
        sClusterService.onSmartCameraSite7(i);
    }

    public static void onSmartCameraSite8(int i) {
        XILog.d(TAG, "onSmartCameraSite8  " + i);
        sClusterService.onSmartCameraSite8(i);
    }

    public static void onLookAroundCameraState(int i) {
        XILog.d(TAG, "onLookAroundCameraState  " + i);
        sClusterService.onLookAroundCameraState(i);
    }

    public static void onLookAroundCameraF(int i) {
        XILog.d(TAG, "onLookAroundCameraF  " + i);
        sClusterService.onLookAroundCameraF(i);
    }

    public static void onLookAroundCameraL(int i) {
        XILog.d(TAG, "onLookAroundCameraL  " + i);
        sClusterService.onLookAroundCameraL(i);
    }

    public static void onLookAroundCameraB(int i) {
        XILog.d(TAG, "onLookAroundCameraB  " + i);
        sClusterService.onLookAroundCameraB(i);
    }

    public static void onLookAroundCameraR(int i) {
        XILog.d(TAG, "onLookAroundCameraR  " + i);
        sClusterService.onLookAroundCameraR(i);
    }

    public static void onSensorFaultTextValue(int i) {
        XILog.d(TAG, "onSensorFaultTextValue id " + i);
        sClusterService.onSensorFaultTextValue(i);
    }

    public static void onRadarCalibration(int i) {
        XILog.d(TAG, "onRadarCalibration  " + i);
        sClusterService.onRadarCalibration(i);
    }

    public static void onLidarRLDirt(int i) {
        XILog.d(TAG, "onLidarRLDirt  " + i);
        sClusterService.onLidarRLDirt(i);
    }

    public static void onLidarRRDirt(int i) {
        XILog.d(TAG, "onLidarRRDirt  " + i);
        sClusterService.onLidarRRDirt(i);
    }

    public static void onPowerAverageEnergyConsumption(float f) {
        XILog.d(TAG, "onPowerAverageEnergyConsumption  " + f);
        sClusterService.onPowerAverageEnergyConsumption(f);
    }

    public static void onPowerCurveValue(float f) {
        XILog.d(TAG, "onPowerCurveValue  " + f);
        sClusterService.onPowerCurveValue(f);
    }

    public static void onRefreshPowerVEHpwrValue(int i) {
        XILog.d(TAG, "onRefreshPowerVEHpwrValue  value:" + i);
        sClusterService.onRefreshPowerVEHpwrValue(i);
    }

    public static void onRefreshPowerVEHpwrMax(int i) {
        XILog.d(TAG, "onRefreshPowerVEHpwrMax  value:" + i);
        sClusterService.onRefreshPowerVEHpwrMax(i);
    }

    public static void onRefreshPowerVEHpwrMin(int i) {
        XILog.d(TAG, "onRefreshPowerVEHpwrMin  value:" + i);
        sClusterService.onRefreshPowerVEHpwrMin(i);
    }

    public static void onResAvailPower(int i) {
        XILog.d(TAG, "onResAvailPower  value:" + i);
        sClusterService.onResAvailPower(i);
    }

    public static void onChargeStatus(int i) {
        XILog.d(TAG, "onChargeStatus  " + i);
        sClusterService.onChargeStatus(i);
    }

    public static void onSuperChrgFlg(boolean z) {
        XILog.d(TAG, "onSuperChrgFlg  " + z);
        sClusterService.onSuperChrgFlg(z);
    }

    public static void onAppointmentHour(String str) {
        XILog.d(TAG, "onAppointmentHour  " + str);
        sClusterService.onAppointmentHour(str);
    }

    public static void onAppointmentMinute(String str) {
        XILog.d(TAG, "onAppointmentMinute  " + str);
        sClusterService.onAppointmentMinute(str);
    }

    public static void onCompleteHour(String str) {
        XILog.d(TAG, "onCompleteHour  " + str);
        sClusterService.onCompleteHour(str);
    }

    public static void onCompleteMinute(String str) {
        XILog.d(TAG, "onCompleteMinute  " + str);
        sClusterService.onCompleteMinute(str);
    }

    public static void onHeatBatteryTipsState(int i) {
        XILog.d(TAG, "onHeatBatteryTipsState  " + i);
        sClusterService.onHeatBatteryTipsState(i);
    }

    public static void onTimeRemainingTipsState(int i) {
        XILog.d(TAG, "onTimeRemainingTipsState  " + i);
        sClusterService.onTimeRemainingTipsState(i);
    }

    public static void onHeatBatteryHour(String str) {
        XILog.d(TAG, "onHeatBatteryHour  " + str);
        sClusterService.onHeatBatteryHour(str);
    }

    public static void onHeatBatteryMinute(String str) {
        XILog.d(TAG, "onHeatBatteryMinute  " + str);
        sClusterService.onHeatBatteryMinute(str);
    }

    public static void onPowerInformationCurrent(String str) {
        XILog.d(TAG, "onPowerInformationCurrent  " + str);
        sClusterService.onPowerInformationCurrent(str);
    }

    public static void onPowerInformationVoltage(String str) {
        XILog.d(TAG, "onPowerInformationVoltage  " + str);
        sClusterService.onPowerInformationVoltage(str);
    }

    public static void onChargingState(int i) {
        XILog.d(TAG, "onChargingState  " + i);
        sClusterService.onChargingState(i);
    }

    public static void onTimeRemainingHour(String str) {
        XILog.d(TAG, "onTimeRemainingHour  " + str);
        sClusterService.onTimeRemainingHour(str);
    }

    public static void onTimeRemainingMinute(String str) {
        XILog.d(TAG, "onTimeRemainingMinute  " + str);
        sClusterService.onTimeRemainingMinute(str);
    }

    public static void onChargingEsocmaxchg(int i) {
        XILog.d(TAG, "onChargingEsocmaxchg  " + i);
        sClusterService.onEsocmaxchg(i);
    }

    @Deprecated
    public static void onOtaProgressBar(int i) {
        XILog.d(TAG, "onOtaProgressBar  " + i);
    }

    @Deprecated
    public static void onOtaState(int i) {
        XILog.d(TAG, "onOtaState  " + i);
    }

    @Deprecated
    public static void onCommonDayNight(int i) {
        XILog.d(TAG, "onCommonDayNight  " + i);
    }

    public static void onCommonGear(int i) {
        XILog.d(TAG, "onCommonGear  " + i);
        sClusterService.onGear(i);
    }

    public static void onCommonElectricQuantity(int i) {
        XILog.d(TAG, "onCommonElectricQuantity  " + i);
        sClusterService.onElectricQuantity(i);
    }

    public static void onCommonEnduranceMileage(float f) {
        XILog.d(TAG, "onCommonEnduranceMileage  " + f);
        sClusterService.onEnduranceMileage(f);
    }

    public static void onEnduranceMileageNumVisible(boolean z) {
        XILog.d(TAG, "onEnduranceMileageNumVisible  " + z);
        sClusterService.onEnduranceMileageNumVisible(z);
    }

    public static void onCommonElectricityColorControlProp(int i) {
        XILog.d(TAG, "onCommonElectricityColorControlProp  " + i);
        sClusterService.onElectricityColorControlProp(i);
    }

    public static void onCommonTurnLeftLightControProp(boolean z) {
        XILog.d(TAG, "onCommonTurnLeftLightControProp  " + z);
        sClusterService.onTurnLeftLightControProp(z);
    }

    public static void onCommonTurnRightLightControProp(boolean z) {
        XILog.d(TAG, "onCommonTurnRightLightControProp  " + z);
        sClusterService.onTurnRightLightControProp(z);
    }

    public static void onCommonCarColorControProp(int i) {
        XILog.d(TAG, "onCommonCarColorControProp  " + i);
        sClusterService.onCarColorControProp(i);
    }

    public static void onCommonIsOffControProp(boolean z) {
        XILog.d(TAG, "onCommonIsOffControProp  " + z);
        sClusterService.onIsOffControProp(z);
    }

    public static void onCommonIsCharging(boolean z) {
        XILog.d(TAG, "onCommonIsCharging  " + z);
        sClusterService.onIsCharging(z);
    }

    public static void onCommonSpeed(int i) {
        XILog.d(TAG, "onCommonSpeed  " + i);
        sClusterService.onSpeed(i);
    }

    public static void onDoorBL(boolean z) {
        XILog.d(TAG, "onDoorBL  " + z);
        sClusterService.onDoorBL(z);
    }

    public static void onDoorBR(boolean z) {
        XILog.d(TAG, "onDoorBR  " + z);
        sClusterService.onDoorBR(z);
    }

    public static void onDoorFL(boolean z) {
        XILog.d(TAG, "onDoorFL  " + z);
        sClusterService.onDoorFL(z);
    }

    public static void onDoorFR(boolean z) {
        XILog.d(TAG, "onDoorFR  " + z);
        sClusterService.onDoorFR(z);
    }

    public static void onNormalInfoTemperature(int i) {
        XILog.d(TAG, "onNormalInfoTemperature  " + i);
        sClusterService.onNormalInfoTemperature(i);
    }

    public static void onNormalInfoDrivingMode(int i) {
        XILog.d(TAG, "onDrivingMode  " + i);
        sClusterService.onDrivingMode(i);
    }

    public static void onNormalInfoReadyIndicatorLight(boolean z) {
        XILog.d(TAG, "onReadyIndicatorLight  " + z);
        sClusterService.onReadyIndicatorLight(z);
    }

    public static void onNormalInfoBatteryLifeStandard(int i) {
        XILog.d(TAG, "onBatteryLifeStandard  " + i);
        sClusterService.onBatteryLifeStandard(i);
    }

    @Deprecated
    public static void onNormalInfoTime(String str) {
        XILog.d(TAG, "onTime  " + str);
        sClusterService.onTime(str);
    }

    @Deprecated
    public static void onNormalInfoTimePattern(int i) {
        XILog.d(TAG, "onTimePattern  " + i);
        sClusterService.onTimePattern(i);
    }

    @Deprecated
    public static void onNormalInfoMorningOrAfternoon(int i) {
        XILog.d(TAG, "onMorningOrAfternoon  " + i);
        sClusterService.onMorningOrAfternoon(i);
    }

    public static void onRainDetecSencfg(int i) {
        XILog.d(TAG, "onRainDetecSencfg  " + i);
        sClusterService.onRainDetecSencfg(i);
    }

    public static void onRainDetecVisible(boolean z) {
        XILog.d(TAG, "onRainDetecVisible  " + z);
        sClusterService.onRainDetecVisible(z);
    }

    public static void onOverlayTemperature(float f) {
        XILog.d(TAG, "onOverlayTemperature  " + f);
        sClusterService.onTemperature(f);
    }

    public static void onOverlayAirVolume(int i) {
        XILog.d(TAG, "onOverlayAirVolume  " + i);
        sClusterService.onAirVolume(i);
    }

    public static void onOverlayAirVolumeState(int i) {
        XILog.d(TAG, "onOverlayAirVolumeState state:" + i);
        sClusterService.onAirVolumeState(i);
    }

    public static void onOverlayTemperatureVisible(boolean z) {
        XILog.d(TAG, "onOverlayTemperatureVisible  " + z);
        sClusterService.onTemperatureVisible(z);
    }

    public static void onOverlayMidVolume(float f) {
        XILog.d(TAG, "onOverlayMidVolume  " + f);
        sClusterService.onMidVolume(f);
    }

    public static void onOverlayMidVolumeSilence(boolean z) {
        XILog.d(TAG, "onOverlayMidVolumeSilence  " + z);
        sClusterService.onMidVolumeSilence(z);
    }

    public static void onOverlayMidVoiceVisible(boolean z) {
        XILog.d(TAG, "onOverlayMidVoiceVisible  " + z);
        sClusterService.onMidVoiceVisible(z);
    }

    public static void onOverlayVoiceInfoState(int i) {
        XILog.d(TAG, "onOverlayVoiceState  " + i);
        sClusterService.onOverlayVoiceInfoState(i);
    }

    public static void onOverlayLowSpeedAlarm(boolean z) {
        XILog.d(TAG, "onOverlayLowSpeedAlarm  " + z);
        sClusterService.onLowSpeedAlarm(z);
    }

    public static void onOverlayPreventNGearByMistakeTime(int i) {
        XILog.d(TAG, "onOverlayPreventNGearByMistakeTime  " + i);
        sClusterService.onPreventNGearByMistakeTime(i);
    }

    public static void onOverlayPreventNGearByMistakeVisible(boolean z) {
        XILog.d(TAG, "onOverlayPreventNGearByMistakeVisible  " + z);
        sClusterService.onPreventNGearByMistakeVisible(z);
    }

    public static void onOverlayAbnormalVehicleCondition(boolean z) {
        XILog.d(TAG, "onOverlayAbnormalVehicleCondition  " + z);
        sClusterService.onAbnormalVehicleCondition(z);
    }

    public static void onOverlayACCOperationGuide(boolean z) {
        XILog.d(TAG, "onOverlayACCOperationGuide  " + z);
        sClusterService.onACCOperationGuide(z);
    }

    public static void onOverlayACCDistanceAdjust(int i) {
        XILog.d(TAG, "onOverlayACCDistanceAdjust  " + i);
        sClusterService.onACCDistanceAdjust(i);
    }

    public static void onOverlayACCDistanceAdjustVisible(boolean z) {
        XILog.d(TAG, "onOverlayACCDistanceAdjustVisible  " + z);
        sClusterService.onACCDistanceAdjustVisible(z);
    }

    public static void onOverlayACCSpeedAdjust(int i) {
        XILog.d(TAG, "onOverlayACCSpeedAdjust  " + i);
        sClusterService.onACCSpeedAdjust(i);
    }

    public static void onOverlayACCSpeedAdjustVisible(boolean z) {
        XILog.d(TAG, "onOverlayACCSpeedAdjustVisible  " + z);
        sClusterService.onACCSpeedAdjustVisible(z);
    }

    public static void onACCDistanceAutoVisible(boolean z) {
        XILog.d(TAG, "onOverlayACCDistanceAutoVisible  " + z);
        sClusterService.onACCDistanceAutoVisible(z);
    }

    public static void onOverlayForcedPowerDownVisible(boolean z) {
        XILog.d(TAG, "onOverlayForcedPowerDownVisible  " + z);
        sClusterService.onForcedPowerDownVisible(z);
    }

    public static void onOverlayForcedPowerDownState(int i) {
        XILog.d(TAG, "onOverlayForcedPowerDownState  " + i);
        sClusterService.onForcedPowerDownState(i);
    }

    public static void onOverlayCallState(int i) {
        XILog.d(TAG, "onOverlayCallState  " + i);
        sClusterService.onCallState(i);
    }

    public static void onOverlayCallVisible(boolean z) {
        XILog.d(TAG, "onOverlayCallVisible  " + z);
        sClusterService.onCallVisible(z);
    }

    public static void onOverlayBTPhoneButtonState(int i) {
        XILog.d(TAG, "onOverlayBTPhoneButtonState  " + i);
        sClusterService.onOverlayBTPhoneButtonState(i);
    }

    public static void onOverlayCallerID(String str) {
        XILog.d(TAG, "onOverlayCallerID  " + str);
        sClusterService.onCallerID(str);
    }

    public static void onOverlayCallTime(String str) {
        XILog.d(TAG, "onOverlayCallTime  " + str);
        sClusterService.onCallTime(str);
    }

    public static void onOverlayPGearSafetyProtectTime(int i) {
        XILog.d(TAG, "onOverlayPGearSafetyProtectTime  " + i);
        sClusterService.onPGearSafetyProtectTime(i);
    }

    public static void onOverlayPGearSafetyProtectState(int i) {
        XILog.d(TAG, "onOverlayPGearSafetyProtectState  " + i);
        sClusterService.onPGearSafetyProtectState(i);
    }

    public static void onRefreshFamilyPackage(int i, int i2) {
        XILog.d(TAG, "onRefreshFamilyPackage type: " + i + " state:" + i2);
        sClusterService.onRefreshFamilyPackage(i, i2);
    }

    public static void onACCIsCC(boolean z) {
        XILog.d(TAG, "onACCIsCC  " + z);
        sClusterService.onACCIsCC(z);
    }

    public static void onACCSpeed(int i) {
        XILog.d(TAG, "onACCSpeed  " + i);
        sClusterService.onACCSpeed(i);
    }

    public static void onACCState(int i) {
        XILog.d(TAG, "onACCState  " + i);
        sClusterService.onACCState(i);
    }

    public static void onNGPIndicatorState(int i) {
        XILog.d(TAG, "onNGPIndicatorState  value:" + i);
        sClusterService.onNGPIndicatorState(i);
    }

    public static void onAdasHold(boolean z) {
        XILog.d(TAG, "onAdasHold hold:" + z);
        sClusterService.onAdasHold(z);
    }

    public static void onTSRForbid(int i) {
        XILog.d(TAG, "onTSRForbid value:" + i);
        sClusterService.onTSRForbid(i);
    }

    public static void onTSRWarning(int i) {
        XILog.d(TAG, "onTSRWarning value:" + i);
        sClusterService.onTSRWarning(i);
    }

    public static void onTSRRateLimitingType(int i) {
        XILog.d(TAG, "onTSRRateLimitingType value:" + i);
        sClusterService.onTSRRateLimitingType(i);
    }

    public static void onTSRRateLimitingValue(int i) {
        XILog.d(TAG, "onTSRRateLimitingValue value:" + i);
        sClusterService.onTSRRateLimitingValue(i);
    }

    @Deprecated
    public static void onAdasAEBVisible(boolean z) {
        XILog.d(TAG, "onAdasAEBVisible  " + z);
    }

    @Deprecated
    public static void onBSDLeft(int i) {
        XILog.d(TAG, "onBSDLeft  " + i);
    }

    @Deprecated
    public static void onBSDRight(int i) {
        XILog.d(TAG, "onBSDRight  " + i);
    }

    @Deprecated
    public static void onAdasFCWVisible(boolean z) {
        XILog.d(TAG, "onAdasFCWVisible  " + z);
    }

    public static void onAdasLCCState(int i) {
        XILog.d(TAG, "onAdasLCCState  " + i);
        sClusterService.onAdasLCCState(i);
    }

    public static void onVpaState(int i) {
        XILog.d(TAG, "onVpaState  " + i);
    }

    public static void onDeniedAccToastVisible(boolean z) {
        XILog.d(TAG, "onDeniedAccToastVisible  " + z);
    }

    public static void onTrafficLightsState(int i) {
        XILog.d(TAG, "onTrafficLightsState  " + i);
        sClusterService.onTrafficLightsState(i);
    }

    @Deprecated
    public static void onLightClearanceLamp(boolean z) {
        XILog.d(TAG, "onLightClearanceLamp  " + z);
    }

    @Deprecated
    public static void onLightDippedHeadlight(boolean z) {
        XILog.d(TAG, "onLightDippedHeadlight  " + z);
    }

    @Deprecated
    public static void onLightDRL(boolean z) {
        XILog.d(TAG, "onLightDRL  " + z);
    }

    @Deprecated
    public static void onLightFullBeamHeadlight(boolean z) {
        XILog.d(TAG, "onLightFullBeamHeadlight  " + z);
    }

    @Deprecated
    public static void onLightRearFogLamp(boolean z) {
        XILog.d(TAG, "onLightRearFogLamp  " + z);
    }

    @Deprecated
    public static void onLightSopLamp(boolean z) {
        XILog.d(TAG, "onLightSopLamp  " + z);
    }

    @Deprecated
    public static void onBehindDistValue(String str) {
        XILog.d(TAG, "onBehindDistValue behinddistvalue:" + str);
    }

    @Deprecated
    public static void onBehindCLColor(int i) {
        XILog.d(TAG, "onBehindCLColor behindclcolor:" + i);
    }

    @Deprecated
    public static void onBehindCLDist(int i) {
        XILog.d(TAG, "onBehindCLDist behindcldist:" + i);
    }

    @Deprecated
    public static void onBehindCRColor(int i) {
        XILog.d(TAG, "onBehindCRColor behindcrcolor:" + i);
    }

    @Deprecated
    public static void onBehindCRDist(int i) {
        XILog.d(TAG, "onBehindCRDist behindcrdist:" + i);
    }

    @Deprecated
    public static void onBehindOLColor(int i) {
        XILog.d(TAG, "onBehindOLColor behindolcolor:" + i);
    }

    @Deprecated
    public static void onBehindOLDist(int i) {
        XILog.d(TAG, "onBehindOLDist behindoldist:" + i);
    }

    @Deprecated
    public static void onBehindORColor(int i) {
        XILog.d(TAG, "onBehindORColor behindorcolor:" + i);
    }

    @Deprecated
    public static void onBehindORDist(int i) {
        XILog.d(TAG, "onBehindORDist behindordist:" + i);
    }

    @Deprecated
    public static void onBehindSLColor(int i) {
        XILog.d(TAG, "onBehindSLColor behindslcolor:" + i);
    }

    @Deprecated
    public static void onBehindSLDist(int i) {
        XILog.d(TAG, "onBehindSLDist behindsldist:" + i);
    }

    @Deprecated
    public static void onBehindSRColor(int i) {
        XILog.d(TAG, "onBehindSRColor behindsrcolor:" + i);
    }

    @Deprecated
    public static void onBehindSRDist(int i) {
        XILog.d(TAG, "onBehindSRDist behindsrdist:" + i);
    }

    public static void onFrontDistValue(String str) {
        XILog.d(TAG, "onFrontDistValue frontdistvalue:" + str);
        sClusterService.onFrontDistValue(str);
    }

    public static void onFrontCLColor(int i) {
        XILog.d(TAG, "onFrontCLColor frontclcolor:" + i);
        sClusterService.onFrontCLColor(i);
    }

    public static void onFrontCLDist(int i) {
        XILog.d(TAG, "onFrontCLDist frontcldist:" + i);
        sClusterService.onFrontCLDist(i);
    }

    public static void onFrontCRColor(int i) {
        XILog.d(TAG, "onFrontCRColor frontcrcolor:" + i);
        sClusterService.onFrontCRColor(i);
    }

    public static void onFrontCRDist(int i) {
        XILog.d(TAG, "onFrontCRDist frontcrdist:" + i);
        sClusterService.onFrontCRDist(i);
    }

    public static void onFrontOLColor(int i) {
        XILog.d(TAG, "onFrontOLColor frontolcolor:" + i);
        sClusterService.onFrontOLColor(i);
    }

    public static void onFrontOLDist(int i) {
        XILog.d(TAG, "onFrontOLDist frontoldist:" + i);
        sClusterService.onFrontOLDist(i);
    }

    public static void onFrontORColor(int i) {
        XILog.d(TAG, "onFrontORColor frontorcolor:" + i);
        sClusterService.onFrontORColor(i);
    }

    public static void onFrontORDist(int i) {
        XILog.d(TAG, "onFrontORDist frontordist:" + i);
        sClusterService.onFrontORDist(i);
    }

    public static void onFrontSLColor(int i) {
        XILog.d(TAG, "onFrontSLColor frontslcolor:" + i);
        sClusterService.onFrontSLColor(i);
    }

    public static void onFrontSLDist(int i) {
        XILog.d(TAG, "onFrontSLDist frontsldist:" + i);
        sClusterService.onFrontSLDist(i);
    }

    public static void onFrontSRColor(int i) {
        XILog.d(TAG, "onFrontSRColor frontsrcolor:" + i);
        sClusterService.onFrontSRColor(i);
    }

    public static void onFrontSRDist(int i) {
        XILog.d(TAG, "onFrontSRDist frontsrdist:" + i);
        sClusterService.onFrontSRDist(i);
    }

    @Deprecated
    public static void onRCTALeftVisible(boolean z) {
        XILog.d(TAG, "onRCTALeftVisible rctaleftvisible:" + z);
    }

    @Deprecated
    public static void onRCTARightVisible(boolean z) {
        XILog.d(TAG, "onRCTARightVisible rctarightvisible:" + z);
    }

    @Deprecated
    public static void onAdasRCWVisible(boolean z) {
        XILog.d(TAG, "onAdasRCWVisible rcwvisible:" + z);
    }

    @Deprecated
    public static void onLeftC0(float f) {
        XILog.d(TAG, "onLeftC0 leftc0:" + f);
    }

    @Deprecated
    public static void onLeftC1(float f) {
        XILog.d(TAG, "onLeftC1 leftc1:" + f);
    }

    @Deprecated
    public static void onLeftC2(float f) {
        XILog.d(TAG, "onLeftC2 leftc2:" + f);
    }

    @Deprecated
    public static void onLeftC3(float f) {
        XILog.d(TAG, "onLeftC3 leftc3:" + f);
    }

    @Deprecated
    public static void onLeftType(int i) {
        XILog.d(TAG, "onLeftType lefttype:" + i);
    }

    @Deprecated
    public static void onLeftLeftC0(float f) {
        XILog.d(TAG, "onLeftLeftC0 leftleftc0:" + f);
    }

    @Deprecated
    public static void onLeftLeftC1(float f) {
        XILog.d(TAG, "onLeftLeftC1 leftleftc1:" + f);
    }

    @Deprecated
    public static void onLeftLeftC2(float f) {
        XILog.d(TAG, "onLeftLeftC2 leftleftc2:" + f);
    }

    @Deprecated
    public static void onLeftLeftC3(float f) {
        XILog.d(TAG, "onLeftLeftC3 leftleftc3:" + f);
    }

    @Deprecated
    public static void onLeftLeftType(int i) {
        XILog.d(TAG, "onLeftLeftType leftlefttype:" + i);
    }

    @Deprecated
    public static void onRightC0(float f) {
        XILog.d(TAG, "onRightC0 rightc0:" + f);
    }

    @Deprecated
    public static void onRightC1(float f) {
        XILog.d(TAG, "onRightC1 rightc1:" + f);
    }

    @Deprecated
    public static void onRightC2(float f) {
        XILog.d(TAG, "onRightC2 rightc2:" + f);
    }

    @Deprecated
    public static void onRightC3(float f) {
        XILog.d(TAG, "onRightC3 rightc3:" + f);
    }

    @Deprecated
    public static void onRightType(int i) {
        XILog.d(TAG, "onRightType righttype:" + i);
    }

    @Deprecated
    public static void onRightRightC0(float f) {
        XILog.d(TAG, "onRightRightC0 rightrightc0:" + f);
    }

    @Deprecated
    public static void onRightRightC1(float f) {
        XILog.d(TAG, "onRightRightC1 rightrightc1:" + f);
    }

    @Deprecated
    public static void onRightRightC2(float f) {
        XILog.d(TAG, "onRightRightC2 rightrightc2:" + f);
    }

    @Deprecated
    public static void onRightRightC3(float f) {
        XILog.d(TAG, "onRightRightC3 rightrightc3:" + f);
    }

    @Deprecated
    public static void onRightRightType(int i) {
        XILog.d(TAG, "onRightRightType rightrighttype:" + i);
    }

    @Deprecated
    public static void onVehlnfo1Angle(int i) {
        XILog.d(TAG, "onVehlnfo1Angle vehlnfo1angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo1X(int i) {
        XILog.d(TAG, "onVehlnfo1X vehlnfo1x:" + i);
    }

    @Deprecated
    public static void onVehlnfo1Y(float f) {
        XILog.d(TAG, "onVehlnfo1Y vehlnfo1y:" + f);
    }

    @Deprecated
    public static void onVehlnfo1Color(int i) {
        XILog.d(TAG, "onVehlnfo1Color vehlnfo1color:" + i);
    }

    @Deprecated
    public static void onVehlnfo1Type(int i) {
        XILog.d(TAG, "onVehlnfo1Type vehlnfo1type:" + i);
    }

    @Deprecated
    public static void onVehlnfo2Angle(int i) {
        XILog.d(TAG, "onVehlnfo2Angle vehlnfo2angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo2X(int i) {
        XILog.d(TAG, "onVehlnfo2X vehlnfo2x:" + i);
    }

    @Deprecated
    public static void onVehlnfo2Y(float f) {
        XILog.d(TAG, "onVehlnfo2Y vehlnfo2y:" + f);
    }

    @Deprecated
    public static void onVehlnfo2Color(int i) {
        XILog.d(TAG, "onVehlnfo2Color vehlnfo2color:" + i);
    }

    @Deprecated
    public static void onVehlnfo2Type(int i) {
        XILog.d(TAG, "onVehlnfo2Type vehlnfo2type:" + i);
    }

    @Deprecated
    public static void onVehlnfo3Angle(int i) {
        XILog.d(TAG, "onVehlnfo3Angle vehlnfo3angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo3X(int i) {
        XILog.d(TAG, "onVehlnfo3X vehlnfo3x:" + i);
    }

    @Deprecated
    public static void onVehlnfo3Y(float f) {
        XILog.d(TAG, "onVehlnfo3Y vehlnfo3y:" + f);
    }

    @Deprecated
    public static void onVehlnfo3Color(int i) {
        XILog.d(TAG, "onVehlnfo3Color vehlnfo3color:" + i);
    }

    @Deprecated
    public static void onVehlnfo3Type(int i) {
        XILog.d(TAG, "onVehlnfo3Type vehlnfo3type:" + i);
    }

    @Deprecated
    public static void onVehlnfo4Angle(int i) {
        XILog.d(TAG, "onVehlnfo4Angle vehlnfo4angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo4X(int i) {
        XILog.d(TAG, "onVehlnfo4X vehlnfo4x:" + i);
    }

    @Deprecated
    public static void onVehlnfo4Y(float f) {
        XILog.d(TAG, "onVehlnfo4Y vehlnfo4y:" + f);
    }

    @Deprecated
    public static void onVehlnfo4Color(int i) {
        XILog.d(TAG, "onVehlnfo4Color vehlnfo4color:" + i);
    }

    @Deprecated
    public static void onVehlnfo4Type(int i) {
        XILog.d(TAG, "onVehlnfo4Type vehlnfo4type:" + i);
    }

    @Deprecated
    public static void onVehlnfo5Angle(int i) {
        XILog.d(TAG, "onVehlnfo5Angle vehlnfo5angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo5X(int i) {
        XILog.d(TAG, "onVehlnfo5X vehlnfo5x:" + i);
    }

    @Deprecated
    public static void onVehlnfo5Y(float f) {
        XILog.d(TAG, "onVehlnfo5Y vehlnfo5y:" + f);
    }

    @Deprecated
    public static void onVehlnfo5Color(int i) {
        XILog.d(TAG, "onVehlnfo5Color vehlnfo5color:" + i);
    }

    @Deprecated
    public static void onVehlnfo5Type(int i) {
        XILog.d(TAG, "onVehlnfo5Type vehlnfo5type:" + i);
    }

    @Deprecated
    public static void onVehlnfo6Angle(int i) {
        XILog.d(TAG, "onVehlnfo6Angle vehlnfo6angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo6X(int i) {
        XILog.d(TAG, "onVehlnfo6X vehlnfo6x:" + i);
    }

    @Deprecated
    public static void onVehlnfo6Y(float f) {
        XILog.d(TAG, "onVehlnfo6Y vehlnfo6y:" + f);
    }

    @Deprecated
    public static void onVehlnfo6Color(int i) {
        XILog.d(TAG, "onVehlnfo6Color vehlnfo6color:" + i);
    }

    @Deprecated
    public static void onVehlnfo6Type(int i) {
        XILog.d(TAG, "onVehlnfo6Type vehlnfo6type:" + i);
    }

    @Deprecated
    public static void onVehlnfo7Angle(int i) {
        XILog.d(TAG, "onVehlnfo7Angle vehlnfo7angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo7X(int i) {
        XILog.d(TAG, "onVehlnfo7X vehlnfo7x:" + i);
    }

    @Deprecated
    public static void onVehlnfo7Y(float f) {
        XILog.d(TAG, "onVehlnfo7Y vehlnfo7y:" + f);
    }

    @Deprecated
    public static void onVehlnfo7Color(int i) {
        XILog.d(TAG, "onVehlnfo7Color vehlnfo7color:" + i);
    }

    @Deprecated
    public static void onVehlnfo7Type(int i) {
        XILog.d(TAG, "onVehlnfo7Type vehlnfo7type:" + i);
    }

    @Deprecated
    public static void onVehlnfo8Angle(int i) {
        XILog.d(TAG, "onVehlnfo8Angle vehlnfo8angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo8X(int i) {
        XILog.d(TAG, "onVehlnfo8X vehlnfo8x:" + i);
    }

    @Deprecated
    public static void onVehlnfo8Y(float f) {
        XILog.d(TAG, "onVehlnfo8Y vehlnfo8y:" + f);
    }

    @Deprecated
    public static void onVehlnfo8Color(int i) {
        XILog.d(TAG, "onVehlnfo8Color vehlnfo8color:" + i);
    }

    @Deprecated
    public static void onVehlnfo8Type(int i) {
        XILog.d(TAG, "onVehlnfo8Type vehlnfo8type:" + i);
    }

    @Deprecated
    public static void onVehlnfo9Angle(int i) {
        XILog.d(TAG, "onVehlnfo9Angle vehlnfo9angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo9X(int i) {
        XILog.d(TAG, "onVehlnfo9X vehlnfo9x:" + i);
    }

    @Deprecated
    public static void onVehlnfo9Y(float f) {
        XILog.d(TAG, "onVehlnfo9Y vehlnfo9y:" + f);
    }

    @Deprecated
    public static void onVehlnfo9Color(int i) {
        XILog.d(TAG, "onVehlnfo9Color vehlnfo9color:" + i);
    }

    @Deprecated
    public static void onVehlnfo9Type(int i) {
        XILog.d(TAG, "onVehlnfo9Type vehlnfo9type:" + i);
    }

    @Deprecated
    public static void onVehlnfo10Angle(int i) {
        XILog.d(TAG, "onVehlnfo10Angle vehlnfo10angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo10X(int i) {
        XILog.d(TAG, "onVehlnfo10X vehlnfo10x:" + i);
    }

    @Deprecated
    public static void onVehlnfo10Y(float f) {
        XILog.d(TAG, "onVehlnfo10Y vehlnfo10y:" + f);
    }

    @Deprecated
    public static void onVehlnfo10Color(int i) {
        XILog.d(TAG, "onVehlnfo10Color vehlnfo10color:" + i);
    }

    @Deprecated
    public static void onVehlnfo10Type(int i) {
        XILog.d(TAG, "onVehlnfo10Type vehlnfo10type:" + i);
    }

    @Deprecated
    public static void onVehlnfo11Angle(int i) {
        XILog.d(TAG, "onVehlnfo11Angle vehlnfo11angle:" + i);
    }

    @Deprecated
    public static void onVehlnfo11X(int i) {
        XILog.d(TAG, "onVehlnfo11X vehlnfo11x:" + i);
    }

    @Deprecated
    public static void onVehlnfo11Y(float f) {
        XILog.d(TAG, "onVehlnfo11Y vehlnfo11y:" + f);
    }

    @Deprecated
    public static void onVehlnfo11Color(int i) {
        XILog.d(TAG, "onVehlnfo11Color vehlnfo11color:" + i);
    }

    @Deprecated
    public static void onVehlnfo11Type(int i) {
        XILog.d(TAG, "onVehlnfo11Type vehlnfo11type:" + i);
    }

    @Deprecated
    public static void onParkSpaceALLState(int i) {
        XILog.d(TAG, "onParkSpaceALLState parkspaceallstate:" + i);
    }

    @Deprecated
    public static void onParkSpaceL1State(int i) {
        XILog.d(TAG, "onParkSpaceL1State parkspacel1state:" + i);
    }

    @Deprecated
    public static void onParkSpaceL2State(int i) {
        XILog.d(TAG, "onParkSpaceL2State parkspacel2state:" + i);
    }

    @Deprecated
    public static void onParkSpaceL3State(int i) {
        XILog.d(TAG, "onParkSpaceL3State parkspacel3state:" + i);
    }

    @Deprecated
    public static void onParkSpaceR1State(int i) {
        XILog.d(TAG, "onParkSpaceR1State parkspacer1state:" + i);
    }

    @Deprecated
    public static void onParkSpaceR2State(int i) {
        XILog.d(TAG, "onParkSpaceR2State parkspacer2state:" + i);
    }

    @Deprecated
    public static void onParkSpaceR3State(int i) {
        XILog.d(TAG, "onParkSpaceR3State parkspacer3state:" + i);
    }

    @Deprecated
    public static void onLeftLeftStart(int i) {
        XILog.d(TAG, "onLeftLeftStart leftleftstart:" + i);
    }

    @Deprecated
    public static void onLeftLeftRange(int i) {
        XILog.d(TAG, "onLeftLeftRange leftleftrange:" + i);
    }

    @Deprecated
    public static void onLeftStart(int i) {
        XILog.d(TAG, "onLeftStart leftstart:" + i);
    }

    @Deprecated
    public static void onLeftRange(int i) {
        XILog.d(TAG, "onLeftRange leftrange:" + i);
    }

    @Deprecated
    public static void onRightRightStart(int i) {
        XILog.d(TAG, "onRightRightStart rightrightstart:" + i);
    }

    @Deprecated
    public static void onRightRightRange(int i) {
        XILog.d(TAG, "onRightRightRange rightrightrange:" + i);
    }

    @Deprecated
    public static void onRightStart(int i) {
        XILog.d(TAG, "onRightStart rightstart:" + i);
    }

    @Deprecated
    public static void onRightRange(int i) {
        XILog.d(TAG, "onRightRange rightrange:" + i);
    }

    @Deprecated
    public static void onALCState(int i) {
        XILog.d(TAG, "onALCState alcstate:" + i);
    }

    @Deprecated
    public static void onALCX(int i) {
        XILog.d(TAG, "onALCX alcx:" + i);
    }

    @Deprecated
    public static void onALCY(int i) {
        XILog.d(TAG, "onALCY alcy:" + i);
    }

    @Deprecated
    public static void onALCAngle(int i) {
        XILog.d(TAG, "onALCAngle value:" + i);
    }

    @Deprecated
    public static void onACCDisableChangeCard(boolean z) {
        XILog.d(TAG, "onACCDisableChangeCard show:" + z);
    }

    public static void onColorTest(int i) {
        XILog.d(TAG, "onColorTest value:" + i);
        sClusterService.onColorTest(i);
    }

    public static void onTriggerAutoState(int i) {
        XILog.d(TAG, "onTriggerAutoState  value:" + i);
        sClusterService.onTriggerAutoState(i);
    }

    public static void onTriggerState(int i) {
        XILog.d(TAG, "onTriggerState  value:" + i);
        sClusterService.onTriggerState(i);
    }

    public static void onCommonTelltale(int i, boolean z) {
        XILog.d(TAG, "onCommonTelltale  id:" + i + "show:" + z);
        sClusterService.onCommonTelltale(i, z);
    }

    public static void onUnsetTelltale(int[] iArr) {
        XILog.d(TAG, "onUnsetTelltale  ids:" + Arrays.toString(iArr));
        sClusterService.onUnsetTelltale(iArr);
    }

    public static void onPowerIsInitOver(boolean z) {
        XILog.d(TAG, "onPowerIsInitOver  flag:" + z);
    }

    public static void setScreenOn(boolean z) {
        XILog.d(TAG, "setScreenOn  :" + z);
        sClusterService.setScreenOn(z);
    }

    public static void setDisplayState(int i) {
        XILog.d(TAG, "setDisplayState  :" + i);
        sClusterService.setDisplayState(i);
    }

    public static void onLCCFailureState(int i) {
        XILog.d(TAG, "onLCCFailureState  value:" + i);
        sClusterService.onLCCFailureState(i);
    }

    public static void onALCIndicatorState(int i) {
        XILog.d(TAG, "onALCIndicatorState  value:" + i);
        sClusterService.onALCIndicatorState(i);
    }

    @Deprecated
    public static void onISLCState(int i) {
        XILog.d(TAG, "onISLCState  value:" + i);
        sClusterService.onISLCState(i);
    }

    public static void onSASState(int i) {
        XILog.d(TAG, "onSASState  value:" + i);
        sClusterService.onSASState(i);
    }

    public static void onCommonElectricQuantityStr(String str) {
        XILog.d(TAG, "onCommonElectricQuantityStr  " + str);
        sClusterService.onCommonElectricQuantityStr(str);
    }

    public static void onRandisDisplayType(int i) {
        XILog.i(TAG, "onRandisDisplayType  " + i);
        sClusterService.onRandisDisplayType(i);
    }
}
