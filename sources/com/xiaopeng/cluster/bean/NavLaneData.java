package com.xiaopeng.cluster.bean;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public class NavLaneData implements Parcelable {
    public static final Parcelable.Creator<NavLaneData> CREATOR = new Parcelable.Creator<NavLaneData>() { // from class: com.xiaopeng.cluster.bean.NavLaneData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NavLaneData createFromParcel(Parcel parcel) {
            return new NavLaneData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NavLaneData[] newArray(int i) {
            return new NavLaneData[i];
        }
    };
    private int[] mBackLane;
    private int[] mFrontLane;
    private boolean visible;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NavLaneData() {
    }

    protected NavLaneData(Parcel parcel) {
        this.mBackLane = parcel.createIntArray();
        this.mFrontLane = parcel.createIntArray();
        this.visible = parcel.readByte() != 0;
    }

    public int[] getBackLane() {
        return this.mBackLane;
    }

    public void setBackLane(int[] iArr) {
        this.mBackLane = iArr;
    }

    public int[] getFrontLane() {
        return this.mFrontLane;
    }

    public void setFrontLane(int[] iArr) {
        this.mFrontLane = iArr;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean z) {
        this.visible = z;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeIntArray(this.mBackLane);
        parcel.writeIntArray(this.mFrontLane);
        parcel.writeByte(this.visible ? (byte) 1 : (byte) 0);
    }
}
