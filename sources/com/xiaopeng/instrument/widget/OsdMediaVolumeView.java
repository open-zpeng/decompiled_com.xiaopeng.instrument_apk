package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class OsdMediaVolumeView extends XLinearLayout {
    private static final int OSD_CALL_VOLUME = 1;
    private static final int OSD_MEDIA_VOLUME = 0;
    private static final int OSD_OUTSIDECAR_MEDIA_VOLUME = 2;
    private static final String TAG = "OsdMediaVolumeView";
    private boolean mIsMute;
    private int mMediaMode;
    private XImageView mMediaVolumeIconV;
    private XProgressBar mMediaVolumeSeekBar;
    private XTextView mMediaVolumeTitleV;

    public OsdMediaVolumeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void changeTheme() {
        if (this.mMediaVolumeSeekBar != null) {
            this.mMediaVolumeSeekBar.setProgressTintList(ColorStateList.valueOf(this.mIsMute ? ContextCompat.getColor(getContext(), R.color.osd_media_volume_progress_bar_color_mute) : ContextCompat.getColor(getContext(), R.color.osd_media_volume_progress_bar_color)));
        }
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_osd_media_volume, this);
        this.mMediaVolumeTitleV = (XTextView) findViewById(R.id.tv_media_volume);
        this.mMediaVolumeSeekBar = (XProgressBar) findViewById(R.id.pb_media_volume);
        this.mMediaVolumeIconV = (XImageView) findViewById(R.id.volume_icon);
    }

    public void setMediaVolume(float f) {
        XProgressBar xProgressBar = this.mMediaVolumeSeekBar;
        if (xProgressBar != null) {
            xProgressBar.setProgress((int) f);
        }
    }

    public void setMediaMute(boolean z) {
        int color;
        this.mIsMute = z;
        XILog.i(TAG, "mIsMute is : " + this.mIsMute);
        XImageView xImageView = this.mMediaVolumeIconV;
        if (xImageView == null || this.mMediaVolumeSeekBar == null) {
            return;
        }
        int i = this.mMediaMode;
        int i2 = i == 0 ? z ? R.drawable.osd_media_mute : R.drawable.osd_media_volume : i == 2 ? z ? R.drawable.osd_outsidecar_media_mute : R.drawable.osd_outsidecar_media_volume : -1;
        if (i2 != -1) {
            xImageView.setImageResource(i2);
            if (z) {
                color = ContextCompat.getColor(getContext(), R.color.osd_media_volume_progress_bar_color_mute);
            } else {
                color = ContextCompat.getColor(getContext(), R.color.osd_media_volume_progress_bar_color);
            }
            this.mMediaVolumeSeekBar.setProgressTintList(ColorStateList.valueOf(color));
        }
    }

    public void changeToCallMode(int i) {
        if (this.mMediaVolumeTitleV == null) {
            XILog.i(TAG, "mMediaVolumeTitleV is null");
        } else if (this.mMediaMode == i) {
        } else {
            this.mMediaMode = i;
            if (i == 0) {
                this.mMediaVolumeIconV.setImageResource(R.drawable.osd_media_volume);
                this.mMediaVolumeTitleV.setText(R.string.osd_media_volume);
            } else if (i == 1) {
                this.mMediaVolumeIconV.setImageResource(R.drawable.osd_call_volume);
                this.mMediaVolumeTitleV.setText(R.string.osd_call_volume);
            } else if (i == 2) {
                this.mMediaVolumeIconV.setImageResource(R.drawable.osd_outsidecar_media_volume);
                this.mMediaVolumeTitleV.setText(R.string.osd_media_volume);
            }
        }
    }
}
