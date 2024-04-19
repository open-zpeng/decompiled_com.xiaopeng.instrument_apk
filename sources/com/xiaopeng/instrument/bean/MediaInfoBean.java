package com.xiaopeng.instrument.bean;

import android.graphics.Bitmap;
/* loaded from: classes.dex */
public class MediaInfoBean {
    private String mMediaName;
    private Bitmap mMediaThumbnail;
    private int mMediaType;
    private float mProgress;
    private String mSinger;

    /* loaded from: classes.dex */
    public @interface MediaType {
        public static final int MEDIA_DEFAULT = 0;
        public static final int MEDIA_NORMAL = 1;
    }

    public int getMediaType() {
        return this.mMediaType;
    }

    public void setMediaType(int i) {
        this.mMediaType = i;
    }

    public String getMediaName() {
        return this.mMediaName;
    }

    public void setMediaName(String str) {
        this.mMediaName = str;
    }

    public String getSinger() {
        return this.mSinger;
    }

    public void setSinger(String str) {
        this.mSinger = str;
    }

    public float getProgress() {
        return this.mProgress;
    }

    public void setProgress(float f) {
        this.mProgress = f;
    }

    public Bitmap getMediaThumbnail() {
        return this.mMediaThumbnail;
    }

    public void setMediaThumbnail(Bitmap bitmap) {
        this.mMediaThumbnail = bitmap;
    }

    public String toString() {
        return "MediaInfoBean{mMediaType=" + this.mMediaType + ", mMediaName='" + this.mMediaName + "', mSinger='" + this.mSinger + "', mProgress=" + this.mProgress + ", mMediaThumbnail=" + this.mMediaThumbnail + '}';
    }
}
