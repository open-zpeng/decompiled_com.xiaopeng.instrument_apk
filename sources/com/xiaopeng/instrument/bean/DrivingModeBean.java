package com.xiaopeng.instrument.bean;

import com.google.gson.annotations.SerializedName;
import com.xiaopeng.lib.apirouter.ClientConstants;
import com.xiaopeng.libtheme.ThemeManager;
/* loaded from: classes.dex */
public class DrivingModeBean {
    @SerializedName(ThemeManager.AttributeSet.BACKGROUND)
    private String background;
    @SerializedName("id")
    private int id;
    @SerializedName(ClientConstants.ALIAS.P_NAME)
    private String name;

    public String toString() {
        StringBuilder sb = new StringBuilder("DrivingModeBean{");
        sb.append("id=").append(this.id);
        sb.append(", name='").append(this.name).append('\'');
        sb.append(", background='").append(this.background).append('\'');
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

    public String getBackground() {
        return this.background;
    }

    public void setBackground(String str) {
        this.background = str;
    }
}
