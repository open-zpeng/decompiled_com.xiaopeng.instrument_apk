package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.PowerConsumptionBean;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.view.XView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class PowerConsumptionLineView extends XView {
    private static final float BEZIER_CURVE_SCALE = 0.5f;
    private static final int LINE_CORNER = 20;
    private static final int LINE_STYLE_CORNER = 1;
    private static final int LINE_STYLE_CUBIC = 2;
    private static final int LINE_STYLE_CUBIC_FITTING = 3;
    private static final int MAX_INDEX_COUNT = 25;
    private static final String TAG = "PowerConsumptionLineView";
    private final List<PowerConsumptionBean> data;
    private final int mAvgInnerColor;
    private final int mAvgLineWith;
    private final int mAvgOuterColor;
    private final Paint mAvgPaint;
    private final int mAvgPointerInnerSize;
    private final int mAvgPointerOuterSize;
    private float mAvgTextBottom;
    private final Paint mAvgTextPaint;
    private float mAvgTextTop;
    private float mAvgValue;
    private final List<PointF> mChartPoints;
    private final float mContentEndX;
    private final float mContentEndY;
    private final float mContentHorizontal;
    private final float mContentStartX;
    private final float mContentStartY;
    private final float mContentVertical;
    private final List<PointF> mControlPoints;
    private final float mEnergyHorizontalGap;
    private final int mEnergyLineWidth;
    private final int mExtremeEnergyDiff;
    private LinearGradient mGradient;
    private final float mIndicatorGap;
    private final int mIndicatorLineCount;
    private final int mIndicatorLineWith;
    private final Paint mIndicatorPaint;
    private final int mIndicatorTextOffset;
    private final Paint mIndicatorTextPaint;
    private final int mIndicatorTextSize;
    private final int mInstantaneousInnerColor;
    private final int mInstantaneousInnerSize;
    private final int mInstantaneousOuterColor;
    private final int mInstantaneousOuterSize;
    private final int mLineStyle;
    private final int mMaxEnergyValue;
    private final int mMinEnergyValue;
    private final Paint mPaint;
    Path mPath;
    private final Paint mPointerPaint;
    RectF mRectAvg;
    private LinearGradient mShadowGradient;
    private final Paint mShadowPaint;
    Path mShadowPath;
    private final Rect mTextMeasureRect;
    private static final float HORIZONTAL_PADDING = App.getInstance().getResources().getDimension(R.dimen.info_power_line_horizontal_padding);
    private static final float VERTICAL_PADDING = App.getInstance().getResources().getDimension(R.dimen.info_power_line_vertical_padding);
    private static final float DESIRE_HEIGHT = App.getInstance().getResources().getDimension(R.dimen.info_power_line_height);
    private static final float DESIRE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.info_power_line_width);
    private static final int AVG_TEXT_RADIUS = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_avg_text_radius);
    private static final int AVG_TEXT_WIDTH = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_avg_text_width);
    private static final int AVG_TEXT_HEIGHT = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_avg_text_height);

    public PowerConsumptionLineView(Context context) {
        super(context);
        float f = HORIZONTAL_PADDING;
        this.mContentStartX = f;
        float f2 = VERTICAL_PADDING;
        this.mContentStartY = f2;
        float f3 = DESIRE_WIDTH - f;
        this.mContentEndX = f3;
        float f4 = DESIRE_HEIGHT - f2;
        this.mContentEndY = f4;
        float f5 = f3 - f;
        this.mContentHorizontal = f5;
        float f6 = f4 - f2;
        this.mContentVertical = f6;
        this.mMaxEnergyValue = 80;
        this.mMinEnergyValue = -40;
        this.mExtremeEnergyDiff = 120;
        this.mEnergyHorizontalGap = f5 / 24.0f;
        this.data = new ArrayList();
        this.mChartPoints = new ArrayList();
        this.mControlPoints = new ArrayList();
        this.mPaint = new Paint(1);
        this.mAvgPaint = new Paint(1);
        this.mShadowPaint = new Paint(1);
        this.mIndicatorPaint = new Paint(1);
        this.mAvgTextPaint = new TextPaint(1);
        this.mPointerPaint = new Paint(1);
        this.mEnergyLineWidth = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_energy_line_width);
        this.mIndicatorLineWith = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_indicator_line_width);
        this.mIndicatorGap = f6 / 6.0f;
        this.mIndicatorLineCount = 5;
        this.mAvgLineWith = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_avg_line_width);
        this.mAvgPointerInnerSize = 4;
        this.mAvgPointerOuterSize = 6;
        this.mAvgInnerColor = App.getInstance().getColor(R.color.white);
        this.mAvgOuterColor = App.getInstance().getColor(R.color.white_36);
        this.mInstantaneousInnerSize = 4;
        this.mInstantaneousOuterSize = 8;
        this.mInstantaneousInnerColor = App.getInstance().getColor(R.color.power_line_Instantaneous_inner_color);
        this.mInstantaneousOuterColor = App.getInstance().getColor(R.color.power_line_Instantaneous_outer_color);
        this.mLineStyle = 1;
        this.mPath = new Path();
        this.mShadowPath = new Path();
        this.mRectAvg = new RectF();
        this.mIndicatorTextPaint = new Paint(1);
        this.mTextMeasureRect = new Rect();
        this.mIndicatorTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_indicator_text_size);
        this.mIndicatorTextOffset = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_indicator_text_offset);
    }

    public PowerConsumptionLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        float f = HORIZONTAL_PADDING;
        this.mContentStartX = f;
        float f2 = VERTICAL_PADDING;
        this.mContentStartY = f2;
        float f3 = DESIRE_WIDTH - f;
        this.mContentEndX = f3;
        float f4 = DESIRE_HEIGHT - f2;
        this.mContentEndY = f4;
        float f5 = f3 - f;
        this.mContentHorizontal = f5;
        float f6 = f4 - f2;
        this.mContentVertical = f6;
        this.mMaxEnergyValue = 80;
        this.mMinEnergyValue = -40;
        this.mExtremeEnergyDiff = 120;
        this.mEnergyHorizontalGap = f5 / 24.0f;
        this.data = new ArrayList();
        this.mChartPoints = new ArrayList();
        this.mControlPoints = new ArrayList();
        this.mPaint = new Paint(1);
        this.mAvgPaint = new Paint(1);
        this.mShadowPaint = new Paint(1);
        this.mIndicatorPaint = new Paint(1);
        this.mAvgTextPaint = new TextPaint(1);
        this.mPointerPaint = new Paint(1);
        this.mEnergyLineWidth = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_energy_line_width);
        this.mIndicatorLineWith = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_indicator_line_width);
        this.mIndicatorGap = f6 / 6.0f;
        this.mIndicatorLineCount = 5;
        this.mAvgLineWith = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_avg_line_width);
        this.mAvgPointerInnerSize = 4;
        this.mAvgPointerOuterSize = 6;
        this.mAvgInnerColor = App.getInstance().getColor(R.color.white);
        this.mAvgOuterColor = App.getInstance().getColor(R.color.white_36);
        this.mInstantaneousInnerSize = 4;
        this.mInstantaneousOuterSize = 8;
        this.mInstantaneousInnerColor = App.getInstance().getColor(R.color.power_line_Instantaneous_inner_color);
        this.mInstantaneousOuterColor = App.getInstance().getColor(R.color.power_line_Instantaneous_outer_color);
        this.mLineStyle = 1;
        this.mPath = new Path();
        this.mShadowPath = new Path();
        this.mRectAvg = new RectF();
        this.mIndicatorTextPaint = new Paint(1);
        this.mTextMeasureRect = new Rect();
        this.mIndicatorTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_indicator_text_size);
        this.mIndicatorTextOffset = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_line_indicator_text_offset);
        initView(context);
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
        Paint paint = this.mIndicatorPaint;
        if (paint != null) {
            paint.setColor(getContext().getColor(R.color.power_line_indicator_color));
        }
        Paint paint2 = this.mAvgPaint;
        if (paint2 != null) {
            paint2.setColor(getContext().getColor(R.color.power_avg_line_color));
        }
        Paint paint3 = this.mAvgTextPaint;
        if (paint3 != null) {
            paint3.setColor(getContext().getColor(R.color.power_avg_text_color));
        }
        Paint paint4 = this.mIndicatorTextPaint;
        if (paint4 != null) {
            paint4.setColor(getContext().getColor(R.color.power_extreme_text_color));
        }
    }

    private void initView(Context context) {
        this.mGradient = new LinearGradient(0.0f, 0.0f, 0.0f, this.mContentEndY, new int[]{ContextCompat.getColor(context, R.color.color_energy_gradient_1), ContextCompat.getColor(context, R.color.color_energy_gradient_2), ContextCompat.getColor(context, R.color.color_energy_gradient_3), ContextCompat.getColor(context, R.color.color_energy_gradient_4), ContextCompat.getColor(context, R.color.color_energy_gradient_5)}, new float[]{0.0f, BEZIER_CURVE_SCALE, 0.66999996f, 0.835f, 1.0f}, Shader.TileMode.CLAMP);
        this.mPaint.setStrokeWidth(this.mEnergyLineWidth);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setPathEffect(new CornerPathEffect(20.0f));
        this.mPaint.setShader(this.mGradient);
        this.mShadowGradient = new LinearGradient(0.0f, 0.0f, 0.0f, this.mContentEndY, ContextCompat.getColor(context, R.color.color_bg_energy_gradient_start), ContextCompat.getColor(context, R.color.color_bg_energy_gradient_end), Shader.TileMode.CLAMP);
        this.mShadowPaint.setStyle(Paint.Style.FILL);
        this.mShadowPaint.setShader(this.mShadowGradient);
        this.mShadowPaint.setPathEffect(new CornerPathEffect(20.0f));
        this.mIndicatorPaint.setColor(getContext().getColor(R.color.power_line_indicator_color));
        this.mIndicatorPaint.setStyle(Paint.Style.FILL);
        this.mIndicatorPaint.setStrokeWidth(this.mIndicatorLineWith);
        this.mAvgPaint.setStyle(Paint.Style.FILL);
        this.mAvgPaint.setColor(getContext().getColor(R.color.power_avg_line_color));
        this.mAvgPaint.setStrokeWidth(this.mAvgLineWith);
        this.mAvgPaint.setPathEffect(new DashPathEffect(new float[]{5.0f, 11.0f}, 0.0f));
        this.mAvgPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mAvgTextPaint.setColor(getContext().getColor(R.color.power_avg_text_color));
        this.mAvgTextPaint.setTextSize(getContext().getResources().getDimension(R.dimen.info_power_line_text_size));
        this.mAvgTextPaint.setStyle(Paint.Style.FILL);
        this.mAvgTextPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = this.mAvgTextPaint.getFontMetrics();
        this.mAvgTextBottom = fontMetrics.bottom;
        this.mAvgTextTop = fontMetrics.top;
        this.mIndicatorTextPaint.setTextSize(this.mIndicatorTextSize);
        this.mIndicatorTextPaint.setColor(getContext().getColor(R.color.power_extreme_text_color));
        this.mIndicatorTextPaint.setTextAlign(Paint.Align.RIGHT);
        initData();
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawIndicator(canvas);
        drawLines(canvas);
        drawAverage(canvas);
        drawPoints(canvas);
    }

    private void drawIndicator(Canvas canvas) {
        for (int i = 1; i <= 5; i++) {
            float f = this.mContentStartX;
            float f2 = this.mContentStartY;
            float f3 = i;
            float f4 = this.mIndicatorGap;
            canvas.drawLine(f, (f3 * f4) + f2, this.mContentEndX, f2 + (f4 * f3), this.mIndicatorPaint);
            if (i == 1 || i == 4 || i == 5) {
                String str = "" + (80 - (i * 20));
                this.mIndicatorTextPaint.getTextBounds(str, 0, str.length(), this.mTextMeasureRect);
                canvas.drawText(str, this.mIndicatorTextOffset, this.mContentStartY + ((f3 + 0.25f) * this.mIndicatorGap), this.mIndicatorTextPaint);
            }
        }
    }

    private void drawLines(Canvas canvas) {
        this.mPath.reset();
        this.mShadowPath.reset();
        for (int i = 0; i < this.data.size(); i++) {
            float f = this.mContentStartX + ((24 - i) * this.mEnergyHorizontalGap);
            float locationY = getLocationY(this.data.get(i).value);
            float f2 = 23 - i;
            float f3 = (this.mContentStartX + (this.mEnergyHorizontalGap * f2)) - (this.mEnergyLineWidth / 2);
            if (i == 0) {
                this.mPath.moveTo(f, locationY);
                this.mShadowPath.moveTo(f - (this.mEnergyLineWidth / 2), locationY);
                int i2 = i + 1;
                this.mPath.lineTo(this.mContentStartX + (f2 * this.mEnergyHorizontalGap), getLocationY(this.data.get(i2).value));
                this.mShadowPath.lineTo(f3, getLocationY(this.data.get(i2).value));
            } else if (i == this.data.size() - 1) {
                this.mShadowPath.lineTo(f, this.mContentEndY);
                this.mShadowPath.lineTo(this.mContentEndX, this.mContentEndY);
                this.mShadowPath.close();
            } else {
                int i3 = i + 1;
                this.mPath.lineTo(this.mContentStartX + (f2 * this.mEnergyHorizontalGap), getLocationY(this.data.get(i3).value));
                this.mShadowPath.lineTo(f3, getLocationY(this.data.get(i3).value));
            }
        }
        canvas.drawPath(this.mShadowPath, this.mShadowPaint);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    private void calcControlPoints() {
        this.mChartPoints.clear();
        for (int i = 0; i < this.data.size(); i++) {
            this.mChartPoints.add(new PointF(this.mContentStartX + ((24 - i) * this.mEnergyHorizontalGap), getLocationY(this.data.get(i).value)));
        }
        this.mControlPoints.clear();
        if (this.data.size() <= 1) {
            return;
        }
        for (int i2 = 0; i2 < this.data.size(); i2++) {
            float f = this.mContentStartX;
            float f2 = 24 - i2;
            float f3 = this.mEnergyHorizontalGap;
            float f4 = (f + (f2 * f3)) - (f3 * BEZIER_CURVE_SCALE);
            if (i2 == 0) {
                this.mControlPoints.add(new PointF(f4, getLocationY(this.data.get(i2).value)));
            } else if (i2 == this.data.size() - 1) {
                float f5 = this.mContentStartX;
                float f6 = this.mEnergyHorizontalGap;
                this.mControlPoints.add(new PointF((f5 + ((25 - i2) * f6)) - (f6 * BEZIER_CURVE_SCALE), getLocationY(this.data.get(i2).value)));
            } else {
                float locationY = (getLocationY(this.data.get(i2 - 1).value) - getLocationY(this.data.get(i2 + 1).value)) / this.mEnergyHorizontalGap;
                float locationY2 = getLocationY(this.data.get(i2).value);
                float f7 = this.mContentStartX;
                float f8 = this.mEnergyHorizontalGap;
                float f9 = locationY2 - (((f2 * f8) + f7) * locationY);
                float f10 = f7 + (f2 * f8) + (f8 * BEZIER_CURVE_SCALE);
                this.mControlPoints.add(new PointF(f10, (locationY * f10) + f9));
                this.mControlPoints.add(new PointF(f4, (locationY * f4) + f9));
            }
        }
    }

    private void drawAverage(Canvas canvas) {
        int i = AVG_TEXT_WIDTH;
        canvas.drawLine(i, getLocationY(this.mAvgValue), this.mContentEndX, getLocationY(this.mAvgValue), this.mAvgPaint);
        RectF rectF = this.mRectAvg;
        float locationY = getLocationY(this.mAvgValue);
        int i2 = AVG_TEXT_HEIGHT;
        rectF.set(0.0f, locationY - i2, i, getLocationY(this.mAvgValue) + i2);
        RectF rectF2 = this.mRectAvg;
        int i3 = AVG_TEXT_RADIUS;
        canvas.drawRoundRect(rectF2, i3, i3, this.mAvgPaint);
        canvas.drawText("AVG " + ((int) this.mAvgValue), this.mRectAvg.centerX(), (this.mRectAvg.centerY() - (this.mAvgTextTop / 2.0f)) - (this.mAvgTextBottom / 2.0f), this.mAvgTextPaint);
    }

    private void drawPoints(Canvas canvas) {
        this.mPointerPaint.setColor(this.mAvgInnerColor);
        canvas.drawCircle(this.mContentEndX, getLocationY(this.mAvgValue), 4.0f, this.mPointerPaint);
        this.mPointerPaint.setColor(this.mAvgOuterColor);
        canvas.drawCircle(this.mContentEndX, getLocationY(this.mAvgValue), 6.0f, this.mPointerPaint);
        this.mPointerPaint.setColor(this.mInstantaneousInnerColor);
        canvas.drawCircle(this.mContentEndX, getLastPointValue(), 4.0f, this.mPointerPaint);
        this.mPointerPaint.setColor(this.mInstantaneousOuterColor);
        canvas.drawCircle(this.mContentEndX, getLastPointValue(), 8.0f, this.mPointerPaint);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension((int) DESIRE_WIDTH, (int) DESIRE_HEIGHT);
    }

    public void addData(float f) {
        PowerConsumptionBean powerConsumptionBean;
        boolean z;
        if (this.data.size() >= 25) {
            powerConsumptionBean = this.data.get(24);
            this.data.remove(powerConsumptionBean);
            z = true;
        } else {
            powerConsumptionBean = null;
            z = false;
        }
        if (powerConsumptionBean == null) {
            powerConsumptionBean = new PowerConsumptionBean(f);
        } else {
            powerConsumptionBean.value = f;
            XILog.d(TAG, "data list use the cache dataRear .");
        }
        this.data.add(0, powerConsumptionBean);
        if (z) {
            invalidate();
        } else {
            XILog.d(TAG, "just add data,not need render.");
        }
    }

    public void setPowerAvg(float f) {
        if (((int) this.mAvgValue) == ((int) f)) {
            XILog.d(TAG, "mAvgValue not change. not need to invalidate. current avg:" + f);
            return;
        }
        XILog.d(TAG, "update avg,value: " + f);
        this.mAvgValue = f;
        invalidate();
    }

    private float getLastPointValue() {
        if (this.data.size() > 0) {
            return getLocationY(this.data.get(0).value);
        }
        return 0.0f;
    }

    private float getLocationY(float f) {
        float f2 = this.mContentVertical;
        return (f2 - (((f - (-40.0f)) / 120.0f) * f2)) + VERTICAL_PADDING;
    }

    private void initData() {
        for (int i = 0; i < 25; i++) {
            addData(0.0f);
        }
    }
}
