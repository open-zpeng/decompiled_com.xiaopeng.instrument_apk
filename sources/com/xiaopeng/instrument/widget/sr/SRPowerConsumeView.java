package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.EnergyBean;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.view.XView;
/* loaded from: classes.dex */
public class SRPowerConsumeView extends XView {
    private static final String TAG = "SRPowerConsumeView";
    private final float LINE_HEIGHT;
    private final float NEGATIVE_START_X;
    private final float NEGATIVE_WIDTH;
    private final float POSITIVE_END_X;
    private final float POSITIVE_WIDTH;
    private int mCurrentMaxPower;
    private int mCurrentMinPower;
    private float mCurrentRemainValue;
    private final int mInstantPowerColor;
    private Paint mInstantPowerPaint;
    private float mInstantPowerWidth;
    private boolean mIsNegative;
    private final int mRemainPowerColor;
    private Paint mRemainPowerPaint;
    private float mRemainPowerWidth;

    public SRPowerConsumeView(Context context) {
        super(context);
        this.mInstantPowerColor = App.getInstance().getResources().getColor(R.color.color_top_arc_gradient_end, null);
        this.mRemainPowerColor = App.getInstance().getResources().getColor(R.color.sr_power_dial_color, null);
        this.POSITIVE_END_X = App.getInstance().getResources().getDimension(R.dimen.power_positive_end_x);
        this.NEGATIVE_START_X = App.getInstance().getResources().getDimension(R.dimen.power_negative_start_x);
        this.POSITIVE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.power_positive_length);
        this.NEGATIVE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.power_negative_length);
        this.LINE_HEIGHT = App.getInstance().getResources().getDimension(R.dimen.power_line_height);
    }

    public SRPowerConsumeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mInstantPowerColor = App.getInstance().getResources().getColor(R.color.color_top_arc_gradient_end, null);
        this.mRemainPowerColor = App.getInstance().getResources().getColor(R.color.sr_power_dial_color, null);
        this.POSITIVE_END_X = App.getInstance().getResources().getDimension(R.dimen.power_positive_end_x);
        this.NEGATIVE_START_X = App.getInstance().getResources().getDimension(R.dimen.power_negative_start_x);
        this.POSITIVE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.power_positive_length);
        this.NEGATIVE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.power_negative_length);
        this.LINE_HEIGHT = App.getInstance().getResources().getDimension(R.dimen.power_line_height);
        initPaint();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
            invalidate();
        }
    }

    private void changeTheme() {
        this.mRemainPowerPaint.setColor(getContext().getColor(R.color.sr_power_dial_color));
    }

    private void initPaint() {
        this.mInstantPowerPaint = new Paint();
        this.mRemainPowerPaint = new Paint();
        this.mInstantPowerPaint.setColor(this.mInstantPowerColor);
        this.mInstantPowerPaint.setStyle(Paint.Style.FILL);
        this.mInstantPowerPaint.setStrokeWidth(this.LINE_HEIGHT);
        this.mInstantPowerPaint.setAntiAlias(true);
        this.mRemainPowerPaint.setColor(this.mRemainPowerColor);
        this.mRemainPowerPaint.setStyle(Paint.Style.FILL);
        this.mRemainPowerPaint.setStrokeWidth(this.LINE_HEIGHT);
        this.mInstantPowerPaint.setAntiAlias(true);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawRemainPower(canvas);
        drawInstantPower(canvas);
    }

    private void drawInstantPower(Canvas canvas) {
        boolean z = this.mIsNegative;
        canvas.drawLine(z ? this.NEGATIVE_START_X : this.POSITIVE_END_X - this.mInstantPowerWidth, 0.0f, z ? this.NEGATIVE_START_X + this.mInstantPowerWidth : this.POSITIVE_END_X, 0.0f, this.mInstantPowerPaint);
    }

    private void drawRemainPower(Canvas canvas) {
        boolean z = this.mIsNegative;
        canvas.drawLine(z ? this.NEGATIVE_START_X : this.POSITIVE_END_X - this.mRemainPowerWidth, 0.0f, z ? this.NEGATIVE_START_X + this.mRemainPowerWidth : this.POSITIVE_END_X, 0.0f, this.mRemainPowerPaint);
    }

    private void updateInstantPowerValue(float f) {
        XILog.d(TAG, "updateInstantPowerValue");
        if (!this.mIsNegative) {
            int i = this.mCurrentMaxPower;
            if (f > i) {
                this.mInstantPowerWidth = this.POSITIVE_WIDTH;
            } else {
                this.mInstantPowerWidth = (this.POSITIVE_WIDTH * f) / i;
            }
        } else {
            int i2 = this.mCurrentMinPower;
            if (f < i2) {
                this.mInstantPowerWidth = this.NEGATIVE_WIDTH;
            } else {
                this.mInstantPowerWidth = (this.NEGATIVE_WIDTH * f) / i2;
            }
        }
        invalidate();
    }

    private void updateRemainPowerValue(float f) {
        XILog.d(TAG, "updateRemainPowerValue");
        if (!this.mIsNegative) {
            int i = this.mCurrentMaxPower;
            if (f > i) {
                this.mRemainPowerWidth = this.POSITIVE_WIDTH;
            } else {
                this.mRemainPowerWidth = (this.POSITIVE_WIDTH * f) / i;
            }
        } else {
            int i2 = this.mCurrentMinPower;
            if (f < i2) {
                this.mRemainPowerWidth = Math.abs(this.NEGATIVE_WIDTH);
            } else {
                this.mRemainPowerWidth = Math.abs((this.NEGATIVE_WIDTH * f) / i2);
            }
        }
        invalidate();
    }

    public void showRemainingValue(float f) {
        String str = TAG;
        XILog.d(str, "showRemainingValue");
        if (!hasMaxMinInstantaneousValue()) {
            XILog.d(str, "showRemainingValue: not max or min value");
            return;
        }
        int i = this.mCurrentMaxPower;
        if (f > i) {
            f = i;
        }
        if (f <= 0.0f) {
            f = 0.0f;
        }
        this.mCurrentRemainValue = f;
        updateRemainPowerValue(f);
    }

    public void showInstantaneousValue(EnergyBean energyBean) {
        if (energyBean == null) {
            XILog.d(TAG, "showInstantaneousValue energyBean is null");
            return;
        }
        this.mCurrentMinPower = energyBean.getMinValue();
        this.mCurrentMaxPower = energyBean.getMaxValue();
        if (!hasMaxMinInstantaneousValue()) {
            XILog.d(TAG, "showInstantaneousValue: not max or min value");
            return;
        }
        float instantaneousValue = energyBean.getInstantaneousValue();
        this.mIsNegative = instantaneousValue < 0.0f;
        updateInstantPowerValue(instantaneousValue);
        updateRemainPowerValue(this.mCurrentRemainValue);
    }

    private boolean hasMaxMinInstantaneousValue() {
        return (this.mCurrentMaxPower == 0 || this.mCurrentMinPower == 0) ? false : true;
    }
}
