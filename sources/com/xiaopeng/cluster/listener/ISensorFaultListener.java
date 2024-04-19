package com.xiaopeng.cluster.listener;
/* loaded from: classes.dex */
public interface ISensorFaultListener {
    void onLidarRLDirt(int i);

    void onLidarRRDirt(int i);

    void onLookAroundCameraB(int i);

    void onLookAroundCameraF(int i);

    void onLookAroundCameraL(int i);

    void onLookAroundCameraR(int i);

    void onLookAroundCameraState(int i);

    void onMillimeterWaveRadarFC(int i);

    void onMillimeterWaveRadarFL(int i);

    void onMillimeterWaveRadarFR(int i);

    void onMillimeterWaveRadarRCL(int i);

    void onMillimeterWaveRadarRCR(int i);

    void onMillimeterWaveRadarState(int i);

    void onRadarCalibration(int i);

    void onSensorFaultCarState(int i);

    void onSensorFaultTextValue(int i);

    void onSmartCameraSite1(int i);

    void onSmartCameraSite2(int i);

    void onSmartCameraSite3(int i);

    void onSmartCameraSite4(int i);

    void onSmartCameraSite5(int i);

    void onSmartCameraSite6(int i);

    void onSmartCameraSite7(int i);

    void onSmartCameraSite8(int i);

    void onSmartCameraState(int i);

    void onUltrasonicRadarFCL(int i);

    void onUltrasonicRadarFCR(int i);

    void onUltrasonicRadarFOL(int i);

    void onUltrasonicRadarFOR(int i);

    void onUltrasonicRadarFSL(int i);

    void onUltrasonicRadarFSR(int i);

    void onUltrasonicRadarRCL(int i);

    void onUltrasonicRadarRCR(int i);

    void onUltrasonicRadarROL(int i);

    void onUltrasonicRadarROR(int i);

    void onUltrasonicRadarRSL(int i);

    void onUltrasonicRadarRSR(int i);

    void onUltrasonicRadarState(int i);
}
