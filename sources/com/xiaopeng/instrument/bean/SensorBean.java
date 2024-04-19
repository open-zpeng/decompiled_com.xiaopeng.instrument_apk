package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class SensorBean {
    @SerializedName("body_front")
    private ViewPosBean mBodyFront;
    @SerializedName("body_full")
    private ViewPosBean mBodyFullPos;
    @SerializedName("body_rear")
    private ViewPosBean mBodyRear;
    @SerializedName("sensor_body_type")
    private int mSensorBodyType;
    @SerializedName("des")
    private String mSensorDes;
    @SerializedName("id")
    private int mSensorId;
    @SerializedName("sensor_type")
    private String mSensorType;

    public int getSensorBodyType() {
        return this.mSensorBodyType;
    }

    public int getSensorId() {
        return this.mSensorId;
    }

    public void setSensorId(int i) {
        this.mSensorId = i;
    }

    public String getSensorType() {
        return this.mSensorType;
    }

    public void setSensorType(String str) {
        this.mSensorType = str;
    }

    public ViewPosBean getBodyFullPos() {
        return this.mBodyFullPos;
    }

    public void setBodyFullPos(ViewPosBean viewPosBean) {
        this.mBodyFullPos = viewPosBean;
    }

    public ViewPosBean getBodyFront() {
        return this.mBodyFront;
    }

    public void setBodyFront(ViewPosBean viewPosBean) {
        this.mBodyFront = viewPosBean;
    }

    public ViewPosBean getBodyRear() {
        return this.mBodyRear;
    }

    public void setBodyRear(ViewPosBean viewPosBean) {
        this.mBodyRear = viewPosBean;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SensorBean{");
        sb.append("mSensorId=").append(this.mSensorId);
        sb.append(", mSensorDes='").append(this.mSensorDes).append('\'');
        sb.append(", mSensorType='").append(this.mSensorType).append('\'');
        sb.append(", mSensorBodyType='").append(this.mSensorBodyType).append('\'');
        sb.append(", mBodyFullPos=").append(this.mBodyFullPos);
        sb.append(", mBodyFront=").append(this.mBodyFront);
        sb.append(", mBodyRear=").append(this.mBodyRear);
        sb.append('}');
        return sb.toString();
    }
}
