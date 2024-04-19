package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
/* loaded from: classes.dex */
public class WaringBean {
    @SerializedName("warningId")
    private int id;
    private int imgResId;
    @SerializedName("isShow")
    private boolean isShow;
    @SerializedName("emergency_level")
    private int mEmergencyLevel;
    @SerializedName("img_res_name")
    private String mImgResName;
    @SerializedName("text")
    private String mText;
    @SerializedName("type")
    private String mType;
    private StringBuilder sb = null;

    public boolean isShow() {
        return this.isShow;
    }

    public void setShow(boolean z) {
        this.isShow = z;
    }

    public String getType() {
        return this.mType;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public int getImgResId() {
        return this.imgResId;
    }

    public void setImgResId(int i) {
        this.imgResId = i;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getText() {
        return this.mText;
    }

    public void setText(String str) {
        this.mText = str;
    }

    public String getImgResName() {
        return this.mImgResName;
    }

    public void setImgResName(String str) {
        this.mImgResName = str;
    }

    public int getEmergencyLevel() {
        return this.mEmergencyLevel;
    }

    public void setEmergencyLevel(int i) {
        this.mEmergencyLevel = i;
    }

    public String toString() {
        if (this.sb == null) {
            this.sb = new StringBuilder();
        }
        this.sb.setLength(0);
        this.sb.append("WaringBean{");
        this.sb.append("id=").append(this.id);
        this.sb.append(", mText='").append(this.mText).append('\'');
        this.sb.append(", mImgResName='").append(this.mImgResName).append('\'');
        this.sb.append(", mEmergencyLevel='").append(this.mEmergencyLevel).append('\'');
        this.sb.append(", mType='").append(this.mType).append('\'');
        this.sb.append(", imgResId=").append(this.imgResId).append('\'');
        this.sb.append(", isShow=").append(this.isShow);
        this.sb.append('}');
        return this.sb.toString();
    }
}
