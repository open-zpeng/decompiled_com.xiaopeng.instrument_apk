package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class AccSettingBean {
    private int distance;
    private int mode;
    private int speed;
    private boolean visibility;

    /* loaded from: classes.dex */
    public @interface ACC_SETTING_MODE {
        public static final int ACC_DISTANCE = 3;
        public static final int ACC_DISTANCE_AUTO = 4;
        public static final int ACC_GUIDE = 1;
        public static final int ACC_SPEED = 2;
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int i) {
        this.mode = i;
    }

    public boolean isVisibility() {
        return this.visibility;
    }

    public void setVisibility(boolean z) {
        this.visibility = z;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int i) {
        this.speed = i;
    }

    public int getDistance() {
        return this.distance;
    }

    public void setDistance(int i) {
        this.distance = i;
    }

    public String toString() {
        return "AccSettingBean{mode=" + this.mode + ", visibility=" + this.visibility + ", speed=" + this.speed + ", distance=" + this.distance + '}';
    }
}
