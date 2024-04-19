package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public abstract class AbstractAccSettingView extends XConstraintLayout {
    protected static final String ACC_SPEED_INIT_VALUE = "0";
    protected static final int INIT_VALUE = 0;
    private static final String TAG = "AbstractAccSettingView";
    protected XTextView mAccTitle;
    protected XImageView mBgView;
    protected int mCurrentDistance;
    protected int mCurrentMode;
    protected int[] mDistanceBgDrawableResIds;
    protected String[] mDistanceDes;
    protected XLinearLayout mLlSpeedSetting;
    protected XTextView mSpeedUseLeftScroll;
    protected XTextView mTvAccAuto;
    protected XTextView mTvAccSettingInfo;
    protected XTextView mTvAccSpeedUnit;
    protected XTextView mTvDistanceGuide;
    protected XTextView mTvGuidDistance;
    protected XTextView mTvGuidSpeed;

    protected abstract int getLayout();

    protected abstract void showSpeedSetting();

    public AbstractAccSettingView(Context context) {
        this(context, null);
    }

    public AbstractAccSettingView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public AbstractAccSettingView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentDistance = 0;
        initResource(context);
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XConstraintLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void changeTheme() {
        XImageView xImageView = this.mBgView;
        if (xImageView == null) {
            XILog.d(TAG, "mBgView is null ");
            return;
        }
        int i = this.mCurrentMode;
        if (i == 1) {
            xImageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_acc_guide, null));
        } else if (i == 2) {
            xImageView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_acc_speed, null));
        } else if (i != 3) {
        } else {
            setDistanceView();
        }
    }

    private void initResource(Context context) {
        this.mDistanceBgDrawableResIds = new int[]{R.drawable.bg_distance1, R.drawable.bg_distance2, R.drawable.bg_distance3, R.drawable.bg_distance4, R.drawable.bg_distance5};
        this.mDistanceDes = context.getResources().getStringArray(R.array.acc_distance_array);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(getLayout(), this);
        this.mBgView = (XImageView) inflate.findViewById(R.id.bg_acc_content);
        this.mTvGuidDistance = (XTextView) inflate.findViewById(R.id.tv_acc_distance_guide);
        this.mTvGuidSpeed = (XTextView) inflate.findViewById(R.id.tv_acc_speed_guide);
        this.mAccTitle = (XTextView) inflate.findViewById(R.id.tv_acc_title);
        this.mTvDistanceGuide = (XTextView) inflate.findViewById(R.id.tv_acc_distance_setting);
        this.mTvAccSettingInfo = (XTextView) inflate.findViewById(R.id.tv_acc_setting_info);
        this.mTvAccSpeedUnit = (XTextView) inflate.findViewById(R.id.tv_acc_speed_unit);
        this.mLlSpeedSetting = (XLinearLayout) inflate.findViewById(R.id.ll_acc_speed_setting);
        this.mSpeedUseLeftScroll = (XTextView) inflate.findViewById(R.id.tv_acc_speed_use_left_scroll);
        this.mTvAccAuto = (XTextView) inflate.findViewById(R.id.tv_acc_auto_bottom);
    }

    public void showMode(int i) {
        this.mCurrentMode = i;
        if (i == 1) {
            showGuide();
        } else if (i == 2) {
            showSpeedSetting();
        } else if (i == 3) {
            showDistanceSetting();
        } else if (i == 4) {
            showDistanceAuto();
        } else {
            XILog.e(TAG, "invalid mode...  mode:" + i);
        }
        super.setVisibility(0);
    }

    private void showGuide() {
        this.mBgView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_acc_guide, null));
        this.mAccTitle.setText(R.string.acc_setting_guide_title);
        this.mTvGuidDistance.setVisibility(0);
        this.mTvGuidSpeed.setVisibility(0);
        this.mTvDistanceGuide.setVisibility(8);
        this.mTvAccSettingInfo.setVisibility(8);
        this.mTvAccSpeedUnit.setVisibility(8);
        this.mLlSpeedSetting.setVisibility(8);
        this.mSpeedUseLeftScroll.setVisibility(8);
        this.mTvAccAuto.setVisibility(8);
    }

    private void showDistanceSetting() {
        setDistanceView();
        this.mAccTitle.setText(R.string.acc_setting_distance_title);
        this.mTvGuidDistance.setVisibility(8);
        this.mTvGuidSpeed.setVisibility(8);
        this.mTvDistanceGuide.setVisibility(0);
        this.mTvAccSettingInfo.setVisibility(0);
        this.mTvAccSpeedUnit.setVisibility(8);
        this.mLlSpeedSetting.setVisibility(8);
        this.mSpeedUseLeftScroll.setVisibility(8);
        this.mTvAccAuto.setVisibility(8);
    }

    private void showDistanceAuto() {
        this.mBgView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_distance_auto, null));
        this.mAccTitle.setText(R.string.acc_setting_auto_title);
        this.mTvAccSettingInfo.setText(R.string.acc_setting_auto_subtitle);
        this.mTvAccAuto.setText(R.string.acc_setting_auto_bottom);
        this.mTvAccAuto.setVisibility(0);
        this.mTvAccSettingInfo.setVisibility(0);
        this.mTvGuidDistance.setVisibility(8);
        this.mTvGuidSpeed.setVisibility(8);
        this.mTvDistanceGuide.setVisibility(8);
        this.mTvAccSpeedUnit.setVisibility(8);
        this.mLlSpeedSetting.setVisibility(8);
        this.mSpeedUseLeftScroll.setVisibility(8);
    }

    public void setDistance(int i) {
        if (this.mCurrentMode != 3) {
            XILog.d(TAG, "setDistance invalid... current mode is " + this.mCurrentMode);
            return;
        }
        this.mCurrentDistance = i - 1;
        setDistanceView();
    }

    private void setDistanceView() {
        if (this.mCurrentDistance < 0) {
            XILog.d(TAG, "setDistanceView invalid... distance: " + this.mCurrentDistance);
            return;
        }
        this.mBgView.setBackground(ResourcesCompat.getDrawable(getResources(), this.mDistanceBgDrawableResIds[this.mCurrentDistance], null));
        this.mTvAccSettingInfo.setText(this.mDistanceDes[this.mCurrentDistance]);
    }

    public void setSpeed(int i) {
        if (this.mCurrentMode != 2) {
            XILog.d(TAG, "setSpeed invalid... current mode is " + this.mCurrentMode);
        } else if (i < 0) {
            XILog.d(TAG, "setSpeed invalid... speed: " + i);
        } else {
            this.mTvAccSettingInfo.setText(i + "");
        }
    }

    public void hide() {
        super.setVisibility(8);
    }

    @Override // com.xiaopeng.xui.widget.XConstraintLayout, android.view.View
    @Deprecated
    public void setVisibility(int i) {
        XILog.e(TAG, "please use showMode..");
    }
}
