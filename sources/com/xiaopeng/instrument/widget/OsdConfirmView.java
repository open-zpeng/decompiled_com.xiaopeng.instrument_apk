package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class OsdConfirmView extends XLinearLayout {
    private static final String TAG = "OsdConfirmView";
    private int mCurrentMode;
    private XTextView mTvCancel;
    private XTextView mTvConfirm;
    private XTextView mTvContent;
    private XTextView mTvTitle;

    public OsdConfirmView(Context context) {
        this(context, null);
    }

    public OsdConfirmView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
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

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.info_text_confirm, this);
        this.mTvTitle = (XTextView) inflate.findViewById(R.id.tv_text_title);
        this.mTvContent = (XTextView) inflate.findViewById(R.id.tv_text_content);
        this.mTvConfirm = (XTextView) inflate.findViewById(R.id.tv_confirm);
        this.mTvCancel = (XTextView) inflate.findViewById(R.id.tv_cancel);
    }

    public void setCountTime(int i) {
        if (this.mCurrentMode != 1) {
            XILog.d(TAG, "set setCountTime invalid... current mode is " + this.mCurrentMode);
        } else {
            this.mTvCancel.setText(String.format(getContext().getString(R.string.n_gear_mistake_cancel), Integer.valueOf(i)));
        }
    }

    public void setSwitchChoiceItem(boolean z) {
        if (this.mCurrentMode != 0) {
            XILog.d(TAG, "switchChoiceItem invalid... current mode is " + this.mCurrentMode);
            return;
        }
        this.mTvConfirm.setSelected(z);
        this.mTvCancel.setSelected(!z);
    }

    public void showMode(int i) {
        this.mCurrentMode = i;
        if (i == 0) {
            this.mTvTitle.setText(R.string.force_power_off_title);
            this.mTvContent.setText(R.string.force_power_off_content);
            this.mTvCancel.setText(R.string.force_power_cancel);
            this.mTvConfirm.setSelected(false);
            this.mTvConfirm.setText(R.string.n_gear_mistake_confirm);
            this.mTvCancel.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_text_choose_button, null));
            this.mTvCancel.setSelected(true);
            this.mTvCancel.setTextColor(ContextCompat.getColor(getContext(), R.color.text_confirm_cancel_1));
        } else if (i == 1) {
            this.mTvTitle.setText(R.string.n_gear_mistake_title);
            this.mTvContent.setText(R.string.n_gear_mistake_content);
            this.mTvConfirm.setText(R.string.n_gear_mistake_confirm);
            this.mTvConfirm.setSelected(true);
            this.mTvCancel.setText(String.format(getContext().getString(R.string.n_gear_mistake_cancel), 5));
            this.mTvCancel.setTextColor(ContextCompat.getColor(getContext(), R.color.text_confirm_cancel_2));
            this.mTvCancel.setBackground(null);
        }
        super.setVisibility(0);
    }

    private void changeTheme() {
        XILog.i(TAG, "is day:" + (!ThemeManager.isNightMode(getContext())));
        int i = this.mCurrentMode;
        if (i == 0) {
            this.mTvCancel.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_text_choose_button, null));
            this.mTvCancel.setTextColor(ContextCompat.getColor(getContext(), R.color.text_confirm_cancel_1));
        } else if (i != 1) {
        } else {
            this.mTvCancel.setTextColor(ContextCompat.getColor(getContext(), R.color.text_confirm_cancel_2));
            this.mTvCancel.setBackground(null);
        }
    }

    public void hide() {
        super.setVisibility(8);
    }

    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    @Deprecated
    public void setVisibility(int i) {
        XILog.e(TAG, "please use showMode..");
    }
}
