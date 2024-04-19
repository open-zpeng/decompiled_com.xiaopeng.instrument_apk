package com.xiaopeng.instrument.viewmodel;

import android.text.TextUtils;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.ISensorFaultListener;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.CarBodyContain;
import com.xiaopeng.instrument.bean.SensorBean;
import com.xiaopeng.instrument.bean.SensorContainBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
/* loaded from: classes.dex */
public class SensorFaultViewModel extends ViewModel implements ISensorFaultListener, ICommonListener {
    private static final int RADAR_CALIBRATION_ENTER = 1;
    private static final int RADAR_CALIBRATION_EXIT = 0;
    private static final String RADAR_CALIBRATION_TEXT = App.getInstance().getString(R.string.radar_calibration_text);
    private final String TAG = getClass().getSimpleName();
    private final MutableLiveData<SensorContainBean> mSensorContainBeanLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> mTextLiveData = new MutableLiveData<>();
    private String mRadarText = "";
    private String mSensorText = "";
    private Map<Integer, SensorBean> mSensorBeanMap = new ConcurrentHashMap();
    private Map<Integer, String> mIntegerStringMap = new ConcurrentHashMap();
    private CarBodyContain mCarBodyContain = new CarBodyContain();
    private Map<Integer, SensorContainBean> mCurrentSensorMaps = new HashMap();

    @Override // com.xiaopeng.cluster.listener.ICommonListener
    @Deprecated
    public void onCarColorControProp(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLookAroundCameraState(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onMillimeterWaveRadarState(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraState(int i) {
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarState(int i) {
    }

    public SensorFaultViewModel() {
        ClusterManager.getInstance().addSensorFaultListener(this);
        ClusterManager.getInstance().addCommonListener(this);
        initSensorsData();
    }

    public MutableLiveData<SensorContainBean> getSensorContainBeanLiveData() {
        return this.mSensorContainBeanLiveData;
    }

    public MutableLiveData<String> getTextLiveData() {
        return this.mTextLiveData;
    }

    private void initSensorsData() {
        this.mSensorBeanMap = DataConfigManager.getSensorBeanMap();
        this.mIntegerStringMap = DataConfigManager.getSensorFaultTexts();
    }

    public void resumeData() {
        Map<Integer, SensorContainBean> map = this.mCurrentSensorMaps;
        if (map == null || map.size() == 0) {
            return;
        }
        doCardBody(this.mCarBodyContain.getCarBodyType());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removeSensorFaultListener(this);
        ClusterManager.getInstance().removeCommonListener(this);
    }

    private void doSensor(int i, int i2) {
        Map<Integer, SensorBean> map = this.mSensorBeanMap;
        if (map == null) {
            XILog.d(this.TAG, "doSensor SensorBeanMap is null");
            return;
        }
        SensorBean sensorBean = map.get(Integer.valueOf(i));
        if (sensorBean == null) {
            XILog.d(this.TAG, "doSensor SensorBeanMap is null");
            return;
        }
        SensorContainBean sensorContainBean = new SensorContainBean();
        sensorContainBean.setSensorBean(sensorBean);
        sensorContainBean.setStatus(i2);
        sensorContainBean.setCarBodyContain(this.mCarBodyContain);
        this.mCurrentSensorMaps.put(Integer.valueOf(i), sensorContainBean);
        doInternalSensor(sensorBean.getSensorBodyType(), sensorContainBean);
    }

    private void doInternalSensor(int i, SensorContainBean sensorContainBean) {
        boolean z = true;
        boolean z2 = this.mCarBodyContain.getCarBodyType() != 0;
        boolean z3 = i == this.mCarBodyContain.getCarBodyType();
        boolean z4 = this.mCarBodyContain.getCarBodyType() == 3;
        if (!z3 && !z4) {
            z = false;
        }
        sensorContainBean.setShowSensorPoint(z);
        if (z2) {
            this.mSensorContainBeanLiveData.setValue(sensorContainBean);
        }
    }

    private void doCardBody(int i) {
        this.mCarBodyContain.setCarBodyType(i);
        for (Map.Entry<Integer, SensorContainBean> entry : this.mCurrentSensorMaps.entrySet()) {
            SensorContainBean value = entry.getValue();
            if (value == null) {
                XILog.d(this.TAG, "cache  sensor is null");
            } else {
                SensorBean sensorBean = value.getSensorBean();
                XILog.d(this.TAG, "sensor id " + sensorBean.getSensorId());
                doInternalSensor(sensorBean.getSensorBodyType(), value);
            }
        }
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarFSL(int i) {
        doSensor(1, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarFOL(int i) {
        doSensor(2, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarFCL(int i) {
        doSensor(3, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarFCR(int i) {
        doSensor(4, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarFOR(int i) {
        doSensor(5, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarFSR(int i) {
        doSensor(6, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onMillimeterWaveRadarFC(int i) {
        doSensor(7, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onMillimeterWaveRadarFL(int i) {
        doSensor(8, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onMillimeterWaveRadarFR(int i) {
        doSensor(9, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLookAroundCameraF(int i) {
        doSensor(10, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLookAroundCameraL(int i) {
        doSensor(11, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLookAroundCameraR(int i) {
        doSensor(12, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite4(int i) {
        doSensor(13, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite5(int i) {
        doSensor(14, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite6(int i) {
        doSensor(15, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite7(int i) {
        doSensor(16, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite3(int i) {
        doSensor(17, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite2(int i) {
        doSensor(18, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite1(int i) {
        doSensor(19, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarRSL(int i) {
        doSensor(20, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarROL(int i) {
        doSensor(21, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarRCL(int i) {
        doSensor(22, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarRCR(int i) {
        doSensor(23, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarROR(int i) {
        doSensor(24, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onUltrasonicRadarRSR(int i) {
        doSensor(25, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSmartCameraSite8(int i) {
        doSensor(26, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onMillimeterWaveRadarRCL(int i) {
        doSensor(27, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onMillimeterWaveRadarRCR(int i) {
        doSensor(28, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLookAroundCameraB(int i) {
        doSensor(29, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLidarRLDirt(int i) {
        doSensor(30, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onLidarRRDirt(int i) {
        doSensor(31, i);
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSensorFaultTextValue(int i) {
        Map<Integer, String> map = this.mIntegerStringMap;
        if (map == null) {
            XILog.d(this.TAG, "mIntegerStringMap is null ... ");
            return;
        }
        String str = map.get(Integer.valueOf(i));
        this.mSensorText = str;
        if (str == null) {
            this.mSensorText = "";
        }
        this.mTextLiveData.setValue(getSensorTipValue(this.mSensorText, this.mRadarText));
    }

    private String getSensorTipValue(String str, String str2) {
        return (!TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) ? str : str2;
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onRadarCalibration(int i) {
        if (1 == i) {
            this.mRadarText = RADAR_CALIBRATION_TEXT;
        } else {
            this.mRadarText = "";
        }
        this.mTextLiveData.setValue(getSensorTipValue(this.mRadarText, this.mSensorText));
    }

    @Override // com.xiaopeng.cluster.listener.ISensorFaultListener
    public void onSensorFaultCarState(int i) {
        doCardBody(i);
    }
}
