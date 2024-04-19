package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class OSDConfirmBean {
    private boolean confirm;
    private int counterTime;
    private boolean show;
    private int type;

    /* loaded from: classes.dex */
    public @interface TEXT_CONFIRM_MODE {
        public static final int FORCE_POWER_OFF = 0;
        public static final int N_GEAR_MISTAKE = 1;
    }

    private OSDConfirmBean() {
    }

    private OSDConfirmBean(int i) {
        this.type = i;
    }

    public static OSDConfirmBean createNGearMistakeConfirm() {
        return new OSDConfirmBean(1);
    }

    public static OSDConfirmBean createForcePowerOffConfirm() {
        return new OSDConfirmBean(0);
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean z) {
        this.show = z;
    }

    public boolean isConfirm() {
        return this.confirm;
    }

    public void setConfirm(boolean z) {
        this.confirm = z;
    }

    public int getCounterTime() {
        return this.counterTime;
    }

    public void setCounterTime(int i) {
        this.counterTime = i;
    }

    public int getType() {
        return this.type;
    }

    public String toString() {
        return "OSDConfirmBean{type=" + this.type + ", show=" + this.show + ", confirmSwitch=" + this.confirm + ", counterTime=" + this.counterTime + '}';
    }
}
