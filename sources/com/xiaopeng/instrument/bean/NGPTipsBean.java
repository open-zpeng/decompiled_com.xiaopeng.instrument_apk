package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;
/* loaded from: classes.dex */
public class NGPTipsBean {
    @SerializedName("id")
    private int id;
    private int imgResId;
    @SerializedName("img_res_name")
    private String imgResName;
    @SerializedName("emergency_level")
    private int level;
    @SerializedName("text")
    private String text;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String str) {
        this.text = str;
    }

    public String getImgResName() {
        return this.imgResName;
    }

    public void setImgResName(String str) {
        this.imgResName = str;
    }

    public int getEmergencyLevel() {
        return this.level;
    }

    public void setEmergencyLevel(int i) {
        this.level = i;
    }

    public int getImgResId() {
        return this.imgResId;
    }

    public void setImgResId(int i) {
        this.imgResId = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("InfoBean{");
        sb.append("id=").append(this.id);
        sb.append(", text='").append(this.text).append('\'');
        sb.append(", imgResName='").append(this.imgResName).append('\'');
        sb.append(", emergencyLevel='").append(this.level).append('\'');
        sb.append(", imgResId=").append(this.imgResId);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof NGPTipsBean)) {
            NGPTipsBean nGPTipsBean = (NGPTipsBean) obj;
            return this.id == nGPTipsBean.getId() && Objects.equals(this.text, nGPTipsBean.getText()) && Objects.equals(this.imgResName, nGPTipsBean.getImgResName()) && Objects.equals(Integer.valueOf(this.level), Integer.valueOf(nGPTipsBean.getEmergencyLevel()));
        }
        return false;
    }
}
