package com.xiaopeng.cluster.bean;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public class NavGateData implements Parcelable {
    public static final Parcelable.Creator<NavGateData> CREATOR = new Parcelable.Creator<NavGateData>() { // from class: com.xiaopeng.cluster.bean.NavGateData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NavGateData createFromParcel(Parcel parcel) {
            return new NavGateData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NavGateData[] newArray(int i) {
            return new NavGateData[i];
        }
    };
    private int[] tollGate;
    private boolean visible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NavGateData() {
    }

    protected NavGateData(Parcel parcel) {
        this.tollGate = parcel.createIntArray();
        this.visible = parcel.readByte() != 0;
    }

    public int[] getTollGate() {
        return this.tollGate;
    }

    public void setTollGate(int[] iArr) {
        this.tollGate = iArr;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean z) {
        this.visible = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.tollGate);
        parcel.writeByte(this.visible ? (byte) 1 : (byte) 0);
    }
}
