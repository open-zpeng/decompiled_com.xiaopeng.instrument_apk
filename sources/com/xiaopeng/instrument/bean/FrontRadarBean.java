package com.xiaopeng.instrument.bean;
/* loaded from: classes.dex */
public class FrontRadarBean {
    int distances;
    boolean isLevel;
    int level;
    int radarNumber;

    /* loaded from: classes.dex */
    public @interface FRONT_RADAR_NUMBER {
        public static final int LC = 1;
        public static final int LL = 0;
        public static final int LR = 2;
        public static final int RC = 4;
        public static final int RL = 3;
        public static final int RR = 5;
    }

    public String toString() {
        return "FrontRadarBean{isLevel=" + this.isLevel + ", level=" + this.level + ", distances=" + this.distances + ", radarNumber=" + this.radarNumber + '}';
    }

    public int getRadarNumber() {
        return this.radarNumber;
    }

    public void setRadarNumber(int i) {
        this.radarNumber = i;
    }

    public boolean isLevel() {
        return this.isLevel;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(boolean z) {
        this.isLevel = z;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public int getDistances() {
        return this.distances;
    }

    public void setDistances(int i) {
        this.distances = i;
    }
}
