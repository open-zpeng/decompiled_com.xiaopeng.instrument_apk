package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class ViewPosBean {
    @SerializedName("bottom")
    private int mBottom;
    @SerializedName("left")
    private int mLeft;
    @SerializedName("right")
    private int mRight;
    @SerializedName("top")
    private int mTop;
    private StringBuilder sb = null;

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
        this.sb.append("ViewPosBean{");
        this.sb.append("mLeft=").append(this.mLeft);
        this.sb.append(", mTop=").append(this.mTop);
        this.sb.append(", mRight=").append(this.mRight);
        this.sb.append(", mBottom=").append(this.mBottom);
        this.sb.append('}');
        return this.sb.toString();
    }
}
