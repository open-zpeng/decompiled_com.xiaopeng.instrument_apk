package com.xiaopeng.cluster.bean;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public class NavToastData implements Parcelable {
    public static final Parcelable.Creator<NavToastData> CREATOR = new Parcelable.Creator<NavToastData>() { // from class: com.xiaopeng.cluster.bean.NavToastData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NavToastData createFromParcel(Parcel parcel) {
            return new NavToastData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NavToastData[] newArray(int i) {
            return new NavToastData[i];
        }
    };
    private String mNavigationDistance;
    private int mNavigationDistanceUnit;
    private String mNavigationRoadName;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NavToastData() {
    }

    protected NavToastData(Parcel parcel) {
        this.mNavigationDistance = parcel.readStringNoHelper();
        this.mNavigationDistanceUnit = parcel.readInt();
        this.mNavigationRoadName = parcel.readStringNoHelper();
    }

    public String getNavigationDistance() {
        return this.mNavigationDistance;
    }

    public void setNavigationDistance(String str) {
        this.mNavigationDistance = str;
    }

    public int getNavigationDistanceUnit() {
        return this.mNavigationDistanceUnit;
    }

    public void setNavigationDistanceUnit(int i) {
        this.mNavigationDistanceUnit = i;
    }

    public String getNavigationRoadName() {
        return this.mNavigationRoadName;
    }

    public void setNavigationRoadName(String str) {
        this.mNavigationRoadName = str;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringNoHelper(this.mNavigationDistance);
        parcel.writeInt(this.mNavigationDistanceUnit);
        parcel.writeStringNoHelper(this.mNavigationRoadName);
    }
}
