package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.apirouter.ClientConstants;
/* loaded from: classes.dex */
public class CarBodyColorBean {
    @SerializedName("id")
    private int id;
    private int imgResFrontId;
    @SerializedName("img_res_front_name")
    private String imgResFrontName;
    private int imgResFullId;
    @SerializedName("img_res_full_name")
    private String imgResFullName;
    @SerializedName("img_res_name")
    private String imgResName;
    private int imgResRearId;
    @SerializedName("img_res_rear_name")
    private String imgResRearName;
    @SerializedName(ClientConstants.ALIAS.P_NAME)
    private String name;

    public String toString() {
        StringBuilder sb = new StringBuilder("CarBodyColorBean{");
        sb.append("id=").append(this.id);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", imgResName='").append(this.imgResName).append('\'');
        sb.append(", imgResFrontName='").append(this.imgResFrontName).append('\'');
        sb.append(", imgResFrontId=").append(this.imgResFrontId);
        sb.append(", imgResFullName='").append(this.imgResFullName).append('\'');
        sb.append(", imgResFullId=").append(this.imgResFullId);
        sb.append(", imgResRearName='").append(this.imgResRearName).append('\'');
        sb.append(", imgResRearId=").append(this.imgResRearId);
        sb.append('}');
        return sb.toString();
    }

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

    public String getImgResFrontName() {
        return this.imgResFrontName;
    }

    public void setImgResFrontName(String str) {
        this.imgResFrontName = str;
    }

    public int getImgResFrontId() {
        return this.imgResFrontId;
    }

    public void setImgResFrontId(int i) {
        this.imgResFrontId = i;
    }

    public String getImgResFullName() {
        return this.imgResFullName;
    }

    public void setImgResFullName(String str) {
        this.imgResFullName = str;
    }

    public int getImgResFullId() {
        return this.imgResFullId;
    }

    public void setImgResFullId(int i) {
        this.imgResFullId = i;
    }

    public String getImgResRearName() {
        return this.imgResRearName;
    }

    public void setImgResRearName(String str) {
        this.imgResRearName = str;
    }

    public int getImgResRearId() {
        return this.imgResRearId;
    }

    public void setImgResRearId(int i) {
        this.imgResRearId = i;
    }
}
