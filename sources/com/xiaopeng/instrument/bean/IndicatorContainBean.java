package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class IndicatorContainBean {
    private boolean isVisible;
    private IndicatorBean mIndicatorBean;
    private String text;

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public IndicatorBean getIndicatorBean() {
        return this.mIndicatorBean;
    }

    public void setIndicatorBean(IndicatorBean indicatorBean) {
        this.mIndicatorBean = indicatorBean;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean z) {
        this.isVisible = z;
    }
}
