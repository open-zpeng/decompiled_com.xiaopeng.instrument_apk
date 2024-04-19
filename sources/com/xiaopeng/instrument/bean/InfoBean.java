package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.apirouter.ClientConstants;
import java.util.Objects;
/* loaded from: classes.dex */
public class InfoBean {
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

    public String toString() {
        StringBuilder sb = new StringBuilder("InfoBean{");
        sb.append("id=").append(this.id);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", imgResName='").append(this.imgResName).append('\'');
        sb.append(", imgResId=").append(this.imgResId);
        sb.append('}');
        return sb.toString();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof InfoBean)) {
            InfoBean infoBean = (InfoBean) obj;
            return this.id == infoBean.getId() && Objects.equals(this.name, infoBean.getName()) && Objects.equals(this.imgResName, infoBean.getImgResName());
        }
        return false;
    }
}
