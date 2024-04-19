package com.xiaopeng.cluster.bean;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.StringJoiner;
/* loaded from: classes.dex */
public class CommonTelltaleData implements Parcelable {
    public static final Parcelable.Creator<CommonTelltaleData> CREATOR = new Parcelable.Creator<CommonTelltaleData>() { // from class: com.xiaopeng.cluster.bean.CommonTelltaleData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommonTelltaleData createFromParcel(Parcel parcel) {
            return new CommonTelltaleData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CommonTelltaleData[] newArray(int i) {
            return new CommonTelltaleData[i];
        }
    };
    private int id;
    private boolean show;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CommonTelltaleData() {
    }

    protected CommonTelltaleData(Parcel parcel) {
        this.id = parcel.readInt();
        this.show = parcel.readByte() != 0;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int i) {
        this.id = i;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setShow(boolean z) {
        this.show = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeByte(this.show ? (byte) 1 : (byte) 0);
    }

    public String toString() {
        return new StringJoiner(", ", CommonTelltaleData.class.getSimpleName() + "[", "]").add("id=" + this.id).add("show=" + this.show).toString();
    }
}
