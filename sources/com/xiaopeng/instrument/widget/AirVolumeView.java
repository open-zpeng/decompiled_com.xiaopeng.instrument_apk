package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.AirVolumeBean;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class AirVolumeView extends XRelativeLayout {
    private static final String TAG = "AirVolumeView";
    private XImageView mAirIcon;
    private XTextView mAirName;
    private XImageView mAirVolumeSeekBar;
    private Context mContext;
    private int mCurrentVolume;
    private Map<Integer, Integer> mDrawableIdsMap;

    public AirVolumeView(Context context) {
        this(context, null);
    }

    public AirVolumeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDrawableIdsMap = new HashMap();
        init(context);
    }

    public AirVolumeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawableIdsMap = new HashMap();
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.layout_air_volume, this);
        this.mAirVolumeSeekBar = (XImageView) findViewById(R.id.volume_seek_bar);
        this.mAirName = (XTextView) findViewById(R.id.volume_name);
        this.mAirIcon = (XImageView) findViewById(R.id.air_volume_icon);
        initDrawables();
    }

    private void initDrawables() {
        this.mDrawableIdsMap.put(0, Integer.valueOf((int) R.drawable.volume_bg_lane));
        this.mDrawableIdsMap.put(1, Integer.valueOf((int) R.drawable.volume_1));
        this.mDrawableIdsMap.put(2, Integer.valueOf((int) R.drawable.volume_2));
        this.mDrawableIdsMap.put(3, Integer.valueOf((int) R.drawable.volume_3));
        this.mDrawableIdsMap.put(4, Integer.valueOf((int) R.drawable.volume_4));
        this.mDrawableIdsMap.put(5, Integer.valueOf((int) R.drawable.volume_5));
        this.mDrawableIdsMap.put(6, Integer.valueOf((int) R.drawable.volume_6));
        this.mDrawableIdsMap.put(7, Integer.valueOf((int) R.drawable.volume_7));
        this.mDrawableIdsMap.put(8, Integer.valueOf((int) R.drawable.volume_8));
        this.mDrawableIdsMap.put(9, Integer.valueOf((int) R.drawable.volume_9));
        this.mDrawableIdsMap.put(10, Integer.valueOf((int) R.drawable.volume_10));
    }

    public void setAirVolume(int i) {
        this.mCurrentVolume = i;
        changeTheme();
    }

    private void changeTheme() {
        int i;
        Map<Integer, Integer> map = this.mDrawableIdsMap;
        if (map == null || (i = this.mCurrentVolume) < 0) {
            return;
        }
        this.mAirVolumeSeekBar.setImageDrawable(ResourcesCompat.getDrawable(getResources(), map.get(Integer.valueOf(i)).intValue(), null));
    }

    public void showAirVolume(AirVolumeBean airVolumeBean) {
        if (airVolumeBean == null) {
            XILog.d(TAG, "airVolumeBean is null ");
            return;
        }
        String name = airVolumeBean.getName();
        if (App.getInstance().getString(R.string.air_volume_toast_name).equals(name)) {
            this.mAirIcon.setVisibility(8);
        } else {
            this.mAirIcon.setVisibility(0);
        }
        setVisibility(airVolumeBean.isShow() ? 0 : 8);
        this.mAirName.setText(name);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Map<Integer, Integer> map = this.mDrawableIdsMap;
        if (map != null) {
            map.clear();
        }
    }
}
