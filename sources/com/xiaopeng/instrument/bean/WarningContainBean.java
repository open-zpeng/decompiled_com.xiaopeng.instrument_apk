package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class WarningContainBean {
    private boolean isVisible;
    private WaringBean mWaringBean;

    public WaringBean getWaringBean() {
        return this.mWaringBean;
    }

    public void setWaringBean(WaringBean waringBean) {
        this.mWaringBean = waringBean;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean z) {
        this.isVisible = z;
    }
}
