package com.xiaopeng.cluster;
/* loaded from: classes.dex */
interface IClusterService {
    void onACCDistanceAdjust(int i);

    void onACCDistanceAdjustVisible(boolean z);

    void onACCDistanceAutoVisible(boolean z);

    void onACCIsCC(boolean z);

    void onACCOperationGuide(boolean z);

    void onACCSpeed(int i);

    void onACCSpeedAdjust(int i);

    void onACCSpeedAdjustVisible(boolean z);

    void onACCState(int i);

    void onALCIndicatorState(int i);

    void onAbnormalVehicleCondition(boolean z);

    void onAdasAEBVisible(boolean z);

    void onAdasFCWVisible(boolean z);

    void onAdasHold(boolean z);

    void onAdasLCCState(int i);

    void onAfterChargingUnits(String str);

    void onAfterChargingValue(String str);

    void onAirVolume(int i);

    void onAirVolumeState(int i);

    void onAppointmentHour(String str);

    void onAppointmentMinute(String str);

    void onAverageUnits(String str);

    void onAverageValue(String str);

    void onBSDLeft(int i);

    void onBSDRight(int i);

    void onBatteryLifeStandard(int i);

    void onBehindDistValue(String str);

    void onCallState(int i);

    void onCallTime(String str);

    void onCallVisible(boolean z);

    void onCallerID(String str);

    void onCarColorControProp(int i);

    void onChargeStatus(int i);

    void onChargingState(int i);

    void onColorTest(int i);

    void onCommonElectricQuantityStr(String str);

    void onCommonTelltale(int i, boolean z);

    void onCompleteHour(String str);

    void onCompleteMinute(String str);

    void onDoorBL(boolean z);

    void onDoorBR(boolean z);

    void onDoorFL(boolean z);

    void onDoorFR(boolean z);

    void onDrivingMode(int i);

    void onElapsedTimeUnits(String str);

    void onElapsedTimeValue(String str);

    void onElectricQuantity(int i);

    void onElectricityColorControlProp(int i);

    void onEnduranceMileage(float f);

    void onEnduranceMileageNumVisible(boolean z);

    void onEsocmaxchg(int i);

    void onForcedPowerDownState(int i);

    void onForcedPowerDownVisible(boolean z);

    void onFrontCLColor(int i);

    void onFrontCLDist(int i);

    void onFrontCRColor(int i);

    void onFrontCRDist(int i);

    void onFrontDistValue(String str);

    void onFrontOLColor(int i);

    void onFrontOLDist(int i);

    void onFrontORColor(int i);

    void onFrontORDist(int i);

    void onFrontSLColor(int i);

    void onFrontSLDist(int i);

    void onFrontSRColor(int i);

    void onFrontSRDist(int i);

    void onGear(int i);

    void onHeatBatteryHour(String str);

    void onHeatBatteryMinute(String str);

    void onHeatBatteryTipsState(int i);

    void onHoodEngine(boolean z);

    void onHoodLeftCharge(int i);

    void onHoodRightCharge(int i);

    void onHoodTrunk(boolean z);

    @Deprecated
    void onISLCState(int i);

    void onIsCharging(boolean z);

    void onIsOffControProp(boolean z);

    void onLCCFailureState(int i);

    void onLeftCardIndex(int i);

    void onLeftCardVisible(boolean z);

    void onLeftListIndex(int i);

    void onLeftListSensorFault(boolean z);

    void onLeftListVisible(boolean z);

    void onLidarRLDirt(int i);

    void onLidarRRDirt(int i);

    void onLightClearanceLamp(boolean z);

    void onLightDRL(boolean z);

    void onLightDippedHeadlight(boolean z);

    void onLightFullBeamHeadlight(boolean z);

    void onLightRearFogLamp(boolean z);

    void onLightSopLamp(boolean z);

    void onLookAroundCameraB(int i);

    void onLookAroundCameraF(int i);

    void onLookAroundCameraL(int i);

    void onLookAroundCameraR(int i);

    void onLookAroundCameraState(int i);

    void onLowSpeedAlarm(boolean z);

    void onMidVoiceVisible(boolean z);

    void onMidVolume(float f);

    void onMidVolumeSilence(boolean z);

    void onMillimeterWaveRadarFC(int i);

    void onMillimeterWaveRadarFL(int i);

    void onMillimeterWaveRadarFR(int i);

    void onMillimeterWaveRadarRCL(int i);

    void onMillimeterWaveRadarRCR(int i);

    void onMillimeterWaveRadarState(int i);

    void onMorningOrAfternoon(int i);

    void onMusicBarValue(int i);

    void onMusicBarVisible(boolean z);

    @Deprecated
    void onMusicImageState(boolean z);

    void onMusicPlayState(boolean z);

    void onMusicSoundState(int i);

    void onMusicString1(String str);

    void onMusicString2(String str);

    void onNGPIndicatorState(int i);

    void onNaviSRMode(boolean z);

    void onNaviSRTraffic(int i);

    void onNaviStart();

    void onNavigationArrowID(int i);

    void onNavigationDriveSide(boolean z);

    void onNavigationGuidanceVisible(boolean z);

    void onNavigationNormalLane(int[] iArr, int[] iArr2, boolean z);

    void onNavigationToast(String str, int i, String str2);

    void onNavigationTollGateLane(int[] iArr, boolean z);

    void onNormalInfoTemperature(int i);

    void onOtaState(int i);

    void onOverlayBTPhoneButtonState(int i);

    void onOverlayVoiceInfoState(int i);

    void onPGearSafetyProtectState(int i);

    void onPGearSafetyProtectTime(int i);

    void onPowerAverageEnergyConsumption(float f);

    void onPowerCurveValue(float f);

    void onPowerInformationCurrent(String str);

    void onPowerInformationVisible(boolean z);

    void onPowerInformationVoltage(String str);

    void onPreventNGearByMistakeTime(int i);

    void onPreventNGearByMistakeVisible(boolean z);

    void onProgressBar(int i);

    void onRadarCalibration(int i);

    void onRainDetecSencfg(int i);

    void onRainDetecVisible(boolean z);

    void onRandisDisplayType(int i);

    void onReadyIndicatorLight(boolean z);

    void onRefreshFamilyPackage(int i, int i2);

    void onRefreshImageGuidanceTexture(byte[] bArr, int i, int i2, int i3);

    void onRefreshImageMusicTexture(byte[] bArr, int i, int i2, int i3);

    void onRefreshImageNaviTexture(byte[] bArr, int i, int i2, int i3);

    void onRefreshPowerVEHpwrMax(int i);

    void onRefreshPowerVEHpwrMin(int i);

    void onRefreshPowerVEHpwrValue(int i);

    void onResAvailPower(int i);

    void onRightCardIndex(int i);

    void onRightCardVisible(boolean z);

    void onRightListIndex(int i);

    void onRightListVisible(boolean z);

    void onSASState(int i);

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

    void onSpeed(int i);

    void onSubtotalAUnits(String str);

    void onSubtotalAValue(String str);

    void onSubtotalBUnits(String str);

    void onSubtotalBValue(String str);

    void onSuperChrgFlg(boolean z);

    void onTSRForbid(int i);

    void onTSRRateLimitingType(int i);

    void onTSRRateLimitingValue(int i);

    void onTSRWarning(int i);

    void onTemperature(float f);

    void onTemperatureVisible(boolean z);

    void onThisTimeUnits(String str);

    void onThisTimeValue(String str);

    void onTime(String str);

    void onTimePattern(int i);

    void onTimeRemainingHour(String str);

    void onTimeRemainingMinute(String str);

    void onTimeRemainingTipsState(int i);

    void onTireBLPressure(String str);

    void onTireBLPressureState(int i);

    void onTireBLPressureUnits(String str);

    void onTireBLTemperatures(String str);

    void onTireBLTemperaturesState(int i);

    void onTireBLTemperaturesUnits(String str);

    void onTireBLTireState(int i);

    void onTireBRPressure(String str);

    void onTireBRPressureState(int i);

    void onTireBRPressureUnits(String str);

    void onTireBRTemperatures(String str);

    void onTireBRTemperaturesState(int i);

    void onTireBRTemperaturesUnits(String str);

    void onTireBRTireState(int i);

    void onTireFLPressure(String str);

    void onTireFLPressureState(int i);

    void onTireFLPressureUnits(String str);

    void onTireFLTemperatures(String str);

    void onTireFLTemperaturesState(int i);

    void onTireFLTemperaturesUnits(String str);

    void onTireFLTireState(int i);

    void onTireFRPressure(String str);

    void onTireFRPressureState(int i);

    void onTireFRPressureUnits(String str);

    void onTireFRTemperatures(String str);

    void onTireFRTemperaturesState(int i);

    void onTireFRTemperaturesUnits(String str);

    void onTireFRTireState(int i);

    void onTotalUnits(String str);

    void onTotalValue(String str);

    void onTrafficLightsState(int i);

    void onTriggerAutoState(int i);

    void onTriggerState(int i);

    void onTurnLeftLightControProp(boolean z);

    void onTurnRightLightControProp(boolean z);

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

    void onUnsetTelltale(int[] iArr);

    void setDisplayState(int i);

    void setScreenOn(boolean z);
}
