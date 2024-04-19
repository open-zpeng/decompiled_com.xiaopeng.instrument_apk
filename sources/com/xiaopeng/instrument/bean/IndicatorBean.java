package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class IndicatorBean {
    private int imgResId;
    @SerializedName("img_res_name")
    private String imgResName;
    @SerializedName("instrument_id")
    private String instrumentId;
    @SerializedName("pos")
    private int pos;
    private StringBuilder sb = null;
    @SerializedName("type")
    private int type;

    public int getImgResId() {
        return this.imgResId;
    }

    public void setImgResId(int i) {
        this.imgResId = i;
    }

    public String getImgResName() {
        return this.imgResName;
    }

    public void setImgResName(String str) {
        this.imgResName = str;
    }

    public String getInstrumentId() {
        return this.instrumentId;
    }

    public void setInstrumentId(String str) {
        this.instrumentId = str;
    }

    public int getPos() {
        return this.pos;
    }

    public void setPos(int i) {
        this.pos = i;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String toString() {
        if (this.sb == null) {
            this.sb = new StringBuilder();
        }
        this.sb.setLength(0);
        this.sb.append("IndicatorBean{");
        this.sb.append("instrumentId='").append(this.instrumentId).append('\'');
        this.sb.append(", pos=").append(this.pos);
        this.sb.append(", type=").append(this.type);
        this.sb.append(", imgResName='").append(this.imgResName).append('\'');
        this.sb.append(", imgResId=").append(this.imgResId);
        this.sb.append('}');
        return this.sb.toString();
    }
}
