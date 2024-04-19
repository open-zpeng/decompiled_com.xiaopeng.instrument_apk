package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class CarSpeedTextView extends XTextView {
    private static final int MULTIPLE = 2;
    private static final String TAG = "CarSpeedTextView";
    private static final int endColor = 2131099780;
    private static final int endDayColor = 2131099781;
    private static final int endNightColor = 2131099782;
    private static final int startColor = 2131099783;
    private static final int startDayColor = 2131099784;
    private static final int startNightColor = 2131099785;
    private Shader mCurrentShader;
    private Shader mDayShader;
    private Shader mDefaultShader;
    private int mHeight;
    private Shader mNightShader;

    public CarSpeedTextView(Context context) {
        super(context);
        initShader();
    }

    public CarSpeedTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initShader();
    }

    public CarSpeedTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initShader();
    }

    private void initShader() {
        this.mHeight = getLineHeight() * 2;
        this.mDayShader = new LinearGradient(0.0f, 0.0f, 0.0f, this.mHeight, App.getInstance().getColor(R.color.navi_speed_start_day_color), App.getInstance().getColor(R.color.navi_speed_end_day_color), Shader.TileMode.REPEAT);
        this.mNightShader = new LinearGradient(0.0f, 0.0f, 0.0f, this.mHeight, App.getInstance().getColor(R.color.navi_speed_start_night_color), App.getInstance().getColor(R.color.navi_speed_end_night_color), Shader.TileMode.REPEAT);
        LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, this.mHeight, App.getInstance().getColor(R.color.navi_speed_start_color), App.getInstance().getColor(R.color.navi_speed_end_color), Shader.TileMode.REPEAT);
        this.mDefaultShader = linearGradient;
        this.mCurrentShader = linearGradient;
        getPaint().setShader(this.mCurrentShader);
    }

    @Override // com.xiaopeng.xui.widget.XTextView, android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void changeTheme() {
        boolean z = !ThemeManager.isNightMode(getContext());
        XILog.i(TAG, "is day:" + z);
        if (z) {
            this.mCurrentShader = this.mDayShader;
        } else {
            this.mCurrentShader = this.mNightShader;
        }
        getPaint().setShader(this.mCurrentShader);
    }
}
