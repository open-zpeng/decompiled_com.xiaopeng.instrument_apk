package com.xiaopeng.instrument.bean;

import android.text.TextUtils;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.widget.CarConditionCardView;
/* loaded from: classes.dex */
public class CarTyreBean {
    CarConditionCardView.TyrePosition mPosition;
    String mTyreTemperature;
    String mTyreTemperatureUnit;
    int mTyreState = 0;
    int mTyrePressureState = 0;
    int mTyreTemperatureState = 0;
    String mTyrePressureUnit = "bar";
    String mTyrePressure = "-.-";

    public CarTyreBean(CarConditionCardView.TyrePosition tyrePosition) {
        this.mPosition = tyrePosition;
    }

    public String getTyreTemperatureWithUnit() {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(this.mTyreTemperature)) {
            sb.append(this.mTyreTemperature);
        }
        if (!TextUtils.isEmpty(this.mTyreTemperatureUnit)) {
            sb.append(this.mTyreTemperatureUnit);
        }
        return sb.toString();
    }

    public boolean isTyreTemperatureNormal() {
        return this.mTyreTemperatureState != 1;
    }

    public boolean isTyrePressureNormal() {
        return this.mTyrePressureState != 1;
    }

    public int getTyreImageResByTyreState() {
        int i = this.mTyreState;
        if (i == 1) {
            return R.drawable.car_condition_tyre_error;
        }
        if (i == 0) {
            return R.drawable.car_condition_tyre_normal;
        }
        XILog.i("CarTyreBean", "getTyreImageResByTyreState unknown tyre state:" + this.mTyreState);
        return R.drawable.car_condition_tyre_normal;
    }

    public CarConditionCardView.TyrePosition getPosition() {
        return this.mPosition;
    }

    public void setPosition(CarConditionCardView.TyrePosition tyrePosition) {
        this.mPosition = tyrePosition;
    }

    public int getTyreState() {
        return this.mTyreState;
    }

    public void setTyreState(int i) {
        this.mTyreState = i;
    }

    public int getTyrePressureState() {
        return this.mTyrePressureState;
    }

    public void setTyrePressureState(int i) {
        this.mTyrePressureState = i;
    }

    public String getTyrePressure() {
        return this.mTyrePressure;
    }

    public void setTyrePressure(String str) {
        this.mTyrePressure = str;
    }

    public String getTyrePressureUnit() {
        return this.mTyrePressureUnit;
    }

    public void setTyrePressureUnit(String str) {
        this.mTyrePressureUnit = str;
    }

    public int getTyreTemperatureState() {
        return this.mTyreTemperatureState;
    }

    public void setTyreTemperatureState(int i) {
        this.mTyreTemperatureState = i;
    }

    public String getTyreTemperature() {
        return this.mTyreTemperature;
    }

    public void setTyreTemperature(String str) {
        this.mTyreTemperature = str;
    }

    public String getTyreTemperatureUnit() {
        return this.mTyreTemperatureUnit;
    }

    public void setTyreTemperatureUnit(String str) {
        this.mTyreTemperatureUnit = str;
    }
}
