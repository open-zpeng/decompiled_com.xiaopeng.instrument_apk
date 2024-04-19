package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class CarBodyContain {
    private CarBodyColorBean mCarBodyColorBean;
    private int mCarBodyType = 0;

    public int getCarBodyType() {
        return this.mCarBodyType;
    }

    public void setCarBodyType(int i) {
        this.mCarBodyType = i;
    }

    public CarBodyColorBean getCarBodyColorBean() {
        return this.mCarBodyColorBean;
    }

    public void setCarBodyColorBean(CarBodyColorBean carBodyColorBean) {
        this.mCarBodyColorBean = carBodyColorBean;
    }
}
