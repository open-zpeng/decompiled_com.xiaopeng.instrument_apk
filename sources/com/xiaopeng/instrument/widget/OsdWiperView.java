package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class OsdWiperView extends XRelativeLayout {
    private static final String TAG = "OsdWiperView";
    private static final int WIPER_MAX_SPEED = 4;
    private static final int WIPER_MIN_SPEED = 1;
    private Context mContext;
    private int mCurrentSpeed;
    private Map<Integer, Integer> mDrawableIdsMap;
    private Map<Integer, String> mSpeedTypeIdsMap;
    private XImageView mWiperSpeedSeekBar;
    private XTextView mWiperSpeedType;

    public OsdWiperView(Context context) {
        this(context, null);
    }

    public OsdWiperView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCurrentSpeed = 1;
        this.mDrawableIdsMap = new HashMap();
        this.mSpeedTypeIdsMap = new HashMap();
        init(context);
    }

    public OsdWiperView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentSpeed = 1;
        this.mDrawableIdsMap = new HashMap();
        this.mSpeedTypeIdsMap = new HashMap();
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
        LayoutInflater.from(getContext()).inflate(R.layout.layout_osd_wiper, this);
        this.mWiperSpeedSeekBar = (XImageView) findViewById(R.id.wiper_seek_bar);
        this.mWiperSpeedType = (XTextView) findViewById(R.id.wiper_type_name);
        initData();
    }

    private void initData() {
        this.mDrawableIdsMap.put(0, Integer.valueOf((int) R.drawable.volume_bg_lane));
        this.mDrawableIdsMap.put(1, Integer.valueOf((int) R.drawable.speed_low));
        this.mDrawableIdsMap.put(2, Integer.valueOf((int) R.drawable.speed_medium));
        this.mDrawableIdsMap.put(3, Integer.valueOf((int) R.drawable.speed_high));
        this.mDrawableIdsMap.put(4, Integer.valueOf((int) R.drawable.speed_extremely_fast));
        this.mSpeedTypeIdsMap.put(1, App.getInstance().getString(R.string.wiper_low));
        this.mSpeedTypeIdsMap.put(2, App.getInstance().getString(R.string.wiper_medium));
        this.mSpeedTypeIdsMap.put(3, App.getInstance().getString(R.string.wiper_high));
        this.mSpeedTypeIdsMap.put(4, App.getInstance().getString(R.string.wiper_fast));
    }

    public void setWiperSpeed(int i) {
        this.mCurrentSpeed = i;
        changeTheme();
    }

    private void changeTheme() {
        int i;
        Map<Integer, Integer> map = this.mDrawableIdsMap;
        if (map == null || (i = this.mCurrentSpeed) < 1 || i > 4) {
            return;
        }
        this.mWiperSpeedSeekBar.setImageDrawable(ResourcesCompat.getDrawable(getResources(), map.get(Integer.valueOf(i)).intValue(), null));
        this.mWiperSpeedType.setText(this.mSpeedTypeIdsMap.get(Integer.valueOf(this.mCurrentSpeed)));
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Map<Integer, Integer> map = this.mDrawableIdsMap;
        if (map != null) {
            map.clear();
        }
        Map<Integer, String> map2 = this.mSpeedTypeIdsMap;
        if (map2 != null) {
            map2.clear();
        }
    }
}
