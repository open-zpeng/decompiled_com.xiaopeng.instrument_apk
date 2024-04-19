package com.xiaopeng.cluster.bean;

import android.os.Parcel;
import android.os.Parcelable;
/* loaded from: classes.dex */
public class TextureImageData implements Parcelable {
    public static final Parcelable.Creator<TextureImageData> CREATOR = new Parcelable.Creator<TextureImageData>() { // from class: com.xiaopeng.cluster.bean.TextureImageData.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextureImageData createFromParcel(Parcel parcel) {
            return new TextureImageData(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TextureImageData[] newArray(int i) {
            return new TextureImageData[i];
        }
    };
    private byte[] data;
    private int format;
    private int height;
    private int width;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public TextureImageData() {
    }

    protected TextureImageData(Parcel parcel) {
        this.data = parcel.readBlob();
        this.height = parcel.readInt();
        this.width = parcel.readInt();
        this.format = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBlob(this.data);
        parcel.writeInt(this.height);
        parcel.writeInt(this.width);
        parcel.writeInt(this.format);
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] bArr) {
        this.data = bArr;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int i) {
        this.height = i;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int i) {
        this.width = i;
    }

    public int getFormat() {
        return this.format;
    }

    public void setFormat(int i) {
        this.format = i;
    }
}
