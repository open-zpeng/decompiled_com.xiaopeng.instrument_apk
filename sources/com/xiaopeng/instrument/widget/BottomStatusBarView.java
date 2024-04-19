package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.DrivingModeBean;
import com.xiaopeng.instrument.manager.DataConfigManager;
import com.xiaopeng.instrument.utils.ResUtil;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class BottomStatusBarView extends AbstractBottomStatusBarView {
    private static final String TAG = "BottomStatusBarView";
    public static final int TIME_AFTERNOON = 1;
    public static final int TIME_MODE_12 = 1;
    public static final int TIME_MODE_24 = 0;
    public static final int TIME_MORNING = 0;
    XConstraintLayout mBottomBarView;
    XTextView mTimePrefixIv;
    XTextView mTimeTv;

    @Override // com.xiaopeng.instrument.widget.AbstractBottomStatusBarView
    public int getLayout() {
        return R.layout.view_bottom_status_bar;
    }

    public BottomStatusBarView(Context context) {
        super(context);
    }

    public BottomStatusBarView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BottomStatusBarView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.AbstractBottomStatusBarView
    public void initContentView() {
        super.initContentView();
        this.mTimePrefixIv = (XTextView) findViewById(R.id.iv_time_prefix);
        this.mTimeTv = (XTextView) findViewById(R.id.tv_current_time);
        this.mBottomBarView = (XConstraintLayout) findViewById(R.id.bottom_bar_view);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XILog.i(TAG, "onAttachedToWindow");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.instrument.widget.AbstractBottomStatusBarView, com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XILog.i(TAG, "OnDetachedFromWindow");
    }

    @Override // com.xiaopeng.instrument.widget.AbstractBottomStatusBarView
    public void setDrivingMode(int i) {
        int drawableResByName;
        if (this.mDrivingModeHintIv != null) {
            DrivingModeBean drivingModeBean = DataConfigManager.getDrivingModeBeans().get(Integer.valueOf(i));
            if (drivingModeBean != null && (drawableResByName = ResUtil.getDrawableResByName(drivingModeBean.getBackground())) != 0) {
                this.mDrivingModeHintIv.setImageResource(drawableResByName);
            }
            startDrivingModeAnim();
        }
    }

    public void setBarBgData(int i) {
        XConstraintLayout xConstraintLayout = this.mBottomBarView;
        if (xConstraintLayout != null) {
            if (i == 1) {
                xConstraintLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bottom_bar_bg, null));
            } else if (i == 2) {
                xConstraintLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bottom_bar_xsprot_boost_bg, null));
            } else {
                xConstraintLayout.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bottom_bar_bg, null));
            }
        }
    }

    public void setTime(String str) {
        XTextView xTextView = this.mTimeTv;
        if (xTextView != null) {
            xTextView.setText(str);
        }
    }

    public void setTimeMode(int i) {
        XTextView xTextView = this.mTimePrefixIv;
        if (xTextView != null) {
            if (i == 1) {
                xTextView.setVisibility(0);
            } else if (i == 0) {
                xTextView.setVisibility(8);
            } else {
                XILog.e(TAG, "setTimeMode unsupported timeMode:" + i);
            }
        }
    }

    public void setTimeMorningOrAfternoon(int i) {
        XTextView xTextView = this.mTimePrefixIv;
        if (xTextView != null) {
            if (i == 0) {
                xTextView.setText(getContext().getString(R.string.time_am));
            } else if (i == 1) {
                xTextView.setText(getContext().getString(R.string.time_pm));
            } else {
                XILog.e(TAG, "setTimeMorningOrAfternoon unsupported morningOrAfternoon:" + i);
            }
        }
    }

    public void updateBatteryPercent(String str) {
        if (this.mBatteryPercent != null) {
            this.mBatteryPercent.setText(str);
        }
    }

    public void updateBatteryDisplayType(int i) {
        if (this.mBatteryPercent != null) {
            XILog.i(TAG, "updateBatteryDisplayType :\t" + i);
            this.mBatteryPercent.setVisibility(i == 1 ? 0 : 8);
        }
        if (this.mBatteryV != null) {
            this.mBatteryV.updateBatteryFrameBitmap(i);
        }
    }
}
