package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class IndicatorViewBean {
    @SerializedName("bottom")
    private int mBottom;
    @SerializedName("left")
    private int mLeft;
    @SerializedName("pos")
    private int mPos;
    @SerializedName("right")
    private int mRight;
    @SerializedName("top")
    private int mTop;
    @SerializedName("type")
    private int mType;
    private StringBuilder sb = null;

    public int getType() {
        return this.mType;
    }

    public void setType(int i) {
        this.mType = i;
    }

    public int getPos() {
        return this.mPos;
    }

    public void setPos(int i) {
        this.mPos = i;
    }

    public int getLeft() {
        return this.mLeft;
    }

    public void setLeft(int i) {
        this.mLeft = i;
    }

    public int getTop() {
        return this.mTop;
    }

    public void setTop(int i) {
        this.mTop = i;
    }

    public int getRight() {
        return this.mRight;
    }

    public void setRight(int i) {
        this.mRight = i;
    }

    public int getBottom() {
        return this.mBottom;
    }

    public void setBottom(int i) {
        this.mBottom = i;
    }

    public String toString() {
        if (this.sb == null) {
            this.sb = new StringBuilder();
        }
        this.sb.setLength(0);
        this.sb.append("IndicatorViewBean{");
        this.sb.append("mPos=").append(this.mPos);
        this.sb.append(", mLeft=").append(this.mLeft);
        this.sb.append(", mTop=").append(this.mTop);
        this.sb.append(", mRight=").append(this.mRight);
        this.sb.append(", mBottom=").append(this.mBottom);
        this.sb.append(", mType=").append(this.mType);
        this.sb.append('}');
        return this.sb.toString();
    }
}
