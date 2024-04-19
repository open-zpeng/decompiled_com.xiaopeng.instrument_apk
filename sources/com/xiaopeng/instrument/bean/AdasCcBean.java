package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.apirouter.ClientConstants;
/* loaded from: classes.dex */
public class AdasCcBean {
    @SerializedName("id")
    private int id;
    private int imgResId;
    @SerializedName("img_res_name")
    private String imgResName;
    @SerializedName(ClientConstants.ALIAS.P_NAME)
    private String name;

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("AdasCcBean{");
        sb.append("id=").append(this.id);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", imgResName='").append(this.imgResName).append('\'');
        sb.append(", imgResId=").append(this.imgResId);
        sb.append('}');
        return sb.toString();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getImgResName() {
        return this.imgResName;
    }

    public void setImgResName(String str) {
        this.imgResName = str;
    }

    public int getImgResId() {
        return this.imgResId;
    }

    public void setImgResId(int i) {
        this.imgResId = i;
    }
}
