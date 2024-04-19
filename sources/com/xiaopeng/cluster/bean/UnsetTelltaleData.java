package com.xiaopeng.cluster.bean;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public class UnsetTelltaleData implements Parcelable {
    public static final Parcelable.Creator<UnsetTelltaleData> CREATOR = new Parcelable.Creator<UnsetTelltaleData>() { // from class: com.xiaopeng.cluster.bean.UnsetTelltaleData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnsetTelltaleData createFromParcel(Parcel parcel) {
            return new UnsetTelltaleData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UnsetTelltaleData[] newArray(int i) {
            return new UnsetTelltaleData[i];
        }
    };
    private int[] ids;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UnsetTelltaleData() {
    }

    protected UnsetTelltaleData(Parcel parcel) {
        this.ids = parcel.createIntArray();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.ids);
    }

    public int[] getIds() {
        return this.ids;
    }

    public void setIds(int[] iArr) {
        this.ids = iArr;
    }
}
