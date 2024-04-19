package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class SensorContainBean {
    private SensorBean mSensorBean;
    private int status;
    private CarBodyContain mCarBodyContain = new CarBodyContain();
    private boolean showSensorPoint = true;

    public boolean isShowSensorPoint() {
        return this.showSensorPoint;
    }

    public void setShowSensorPoint(boolean z) {
        this.showSensorPoint = z;
    }

    public CarBodyContain getCarBodyContain() {
        return this.mCarBodyContain;
    }

    public void setCarBodyContain(CarBodyContain carBodyContain) {
        this.mCarBodyContain = carBodyContain;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public SensorBean getSensorBean() {
        return this.mSensorBean;
    }

    public void setSensorBean(SensorBean sensorBean) {
        this.mSensorBean = sensorBean;
    }
}
