package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.SweepGradient;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.EnergyBean;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.view.XView;
/* loaded from: classes.dex */
public class PowerCurveView extends XView {
    private static final float ARC_ANGER_HALF;
    private static final float ARC_ANGER_PERCENT_ALL;
    private static final float ARC_ANGER_PERCENT_END;
    private static final float ARC_ANGER_PERCENT_START;
    private static final float ARC_BOTTOM_HEIGHT;
    private static final float[] ARC_BOTTOM_PERCENT;
    private static final float[] ARC_BOTTOM_VALUE_DIV;
    private static final float ARC_BOTTOM_ZERO_OFFSET;
    private static final float ARC_CENTER_VERTICAL;
    private static final float ARC_GAP_HEIGHT;
    private static final float ARC_HEIGHT;
    private static final float ARC_LENGTH;
    private static final float ARC_NEGATIVE_PERCENT;
    private static final float ARC_NONE_PERCENT;
    private static final float ARC_POSITIVE_PERCENT;
    private static final float ARC_TOP_HEIGHT;
    private static final float[] ARC_TOP_PERCENT;
    private static final float[] ARC_TOP_VALUE_DIV;
    private static final float ARC_TOP_ZERO_OFFSET;
    private static final float CURVE_DIAMETER_OUTER;
    private static final float DIAL_LINE_WIDTH;
    private static final float DIAL_RECT_OFFSET;
    private static final float DIAL_SWEEP = 0.12f;
    private static final float INNER_LINE_WIDTH;
    private static final float MIN_REMAIN_ENERGY_VALUE = 0.0f;
    private static final float OUTER_LINE_WIDTH;
    private static final String TAG = "PowerCurveView";
    private static final String UNIT_KW = "kw";
    private static float mCurrentMaxEnergyValue;
    private static float mCurrentMinEnergyValue;
    private boolean isMirror;
    private final float[] mBottomMapValues;
    private final Paint mBoundLinerPaint;
    private SweepGradient mBoundSweepGradient;
    int[] mBoundsGradientColors;
    float mCenterX;
    float mCenterY;
    private final float mContentEndX;
    private final float mContentEndY;
    private final float mContentHorizontal;
    private final float mContentStartX;
    private final float mContentStartY;
    private final float mContentVertical;
    private final Paint mDialPaint;
    private final int mDynamicTextOffsetMirrorX;
    private final int mDynamicTextOffsetMirrorXRight;
    private final Paint mDynamicTextPaint;
    private final int mDynamicTextSize;
    private final int mDynamicUnitTextSize;
    private final float mExtremeDynamicTextY;
    private final float mExtremeTextBottomY;
    private final Paint mExtremeTextPaint;
    private final float mExtremeTextTopY;
    int[] mGradientColors;
    private float mInstantaneousPowerValue;
    private final Paint mInterLinerPaint;
    private final Paint mInterRemainingPaint;
    private final int[] mNegativeColorParse;
    private final Paint mOuterLinerPaint;
    private final int[] mPositiveColorParse;
    private float mRemainingPowerValue;
    private float mRemainingPowerValueOrigin;
    private float mShowTextPowerValue;
    private final int mStaticTextOffsetMirrorXLeft;
    private final int mStaticTextOffsetMirrorXRight;
    private final int mStaticTextSize;
    private SweepGradient mSweepGradient;
    private final Rect mTextMeasureRect;
    private float mTextPowerValue;
    private final float[] mTopMapValues;
    private final int mUnitTopOffset;
    private static final float DESIRE_HEIGHT = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_height);
    private static final float DESIRE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_width);
    private static final float HORIZONTAL_PADDING = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_horizontal_padding);
    private static final float VERTICAL_PADDING = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_vertical_padding);

    static {
        float integer = App.getInstance().getResources().getInteger(R.integer.PowerConsumptionCurveDiameterOuter) * 2.0f;
        CURVE_DIAMETER_OUTER = integer;
        float dimension = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_arc_top_height);
        ARC_TOP_HEIGHT = dimension;
        float dimension2 = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_arc_gap_height);
        ARC_GAP_HEIGHT = dimension2;
        float dimension3 = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_arc_bottom_height);
        ARC_BOTTOM_HEIGHT = dimension3;
        float f = dimension + dimension2 + dimension3;
        ARC_HEIGHT = f;
        ARC_TOP_PERCENT = new float[]{0.6f, 0.26f, 0.14f};
        ARC_BOTTOM_PERCENT = new float[]{0.66f, 0.34f};
        ARC_TOP_VALUE_DIV = new float[]{60.0f, 60.0f, 35.0f};
        ARC_BOTTOM_VALUE_DIV = new float[]{-35.0f, -35.0f};
        float f2 = dimension / f;
        ARC_POSITIVE_PERCENT = f2;
        float f3 = dimension2 / f;
        ARC_NONE_PERCENT = f3;
        ARC_NEGATIVE_PERCENT = dimension3 / f;
        float asin = (float) ((Math.asin(f / integer) / 3.141592653589793d) * 180.0d);
        ARC_ANGER_HALF = asin;
        float f4 = (180.0f - asin) / 360.0f;
        ARC_ANGER_PERCENT_START = f4;
        float f5 = (asin + 180.0f) / 360.0f;
        ARC_ANGER_PERCENT_END = f5;
        ARC_ANGER_PERCENT_ALL = f5 - f4;
        ARC_LENGTH = (float) (((integer * 3.141592653589793d) * asin) / 180.0d);
        ARC_CENTER_VERTICAL = (float) ((Math.cos((asin / 180.0f) * 3.141592653589793d) * integer) / 2.0d);
        ARC_TOP_ZERO_OFFSET = 180.0f - (((f2 * 2.0f) - 1.0f) * asin);
        ARC_BOTTOM_ZERO_OFFSET = 180.0f - ((((f2 + f3) * 2.0f) - 1.0f) * asin);
        DIAL_RECT_OFFSET = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dial_offset_y);
        OUTER_LINE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_outer_line_width);
        INNER_LINE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_inner_line_width);
        DIAL_LINE_WIDTH = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dial_line_width);
        mCurrentMaxEnergyValue = 0.0f;
        mCurrentMinEnergyValue = 0.0f;
    }

    public PowerCurveView(Context context) {
        super(context);
        this.mDynamicTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_text_size);
        this.mDynamicUnitTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_unit_text_size);
        this.mStaticTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_static_text_size);
        this.mUnitTopOffset = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_unit_top_offset);
        this.mDynamicTextOffsetMirrorX = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_text_offset_mirror_x);
        this.mDynamicTextOffsetMirrorXRight = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_text_offset_mirror_x_right);
        this.mStaticTextOffsetMirrorXLeft = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_static_text_offset_mirror_x_left);
        this.mStaticTextOffsetMirrorXRight = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_static_text_offset_mirror_x_right);
        this.mExtremeTextTopY = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_m_extreme_text_top_y);
        this.mExtremeTextBottomY = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_m_extreme_text_bottom_y);
        this.mExtremeDynamicTextY = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_m_extreme_dynamic_text_y);
        this.mBoundLinerPaint = new Paint(1);
        this.mExtremeTextPaint = new TextPaint(1);
        this.mDynamicTextPaint = new TextPaint(1);
        float f = HORIZONTAL_PADDING;
        this.mContentStartX = f;
        float f2 = VERTICAL_PADDING;
        this.mContentStartY = f2;
        float f3 = DESIRE_WIDTH - f;
        this.mContentEndX = f3;
        float f4 = DESIRE_HEIGHT;
        float f5 = f4 - f2;
        this.mContentEndY = f5;
        this.mContentHorizontal = f3 - f;
        this.mContentVertical = f5 - f2;
        this.mOuterLinerPaint = new Paint(1);
        this.mInterLinerPaint = new Paint(1);
        this.mInterRemainingPaint = new Paint(1);
        this.mTextMeasureRect = new Rect();
        this.mPositiveColorParse = new int[6];
        this.mNegativeColorParse = new int[6];
        this.mDialPaint = new Paint(1);
        this.mTopMapValues = new float[3];
        this.mBottomMapValues = new float[2];
        this.mCenterX = f + (CURVE_DIAMETER_OUTER / 2.0f);
        this.mCenterY = f4 / 2.0f;
        this.mTextPowerValue = 0.0f;
        this.mShowTextPowerValue = 0.0f;
        this.isMirror = false;
    }

    public PowerCurveView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDynamicTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_text_size);
        this.mDynamicUnitTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_unit_text_size);
        this.mStaticTextSize = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_static_text_size);
        this.mUnitTopOffset = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_unit_top_offset);
        this.mDynamicTextOffsetMirrorX = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_text_offset_mirror_x);
        this.mDynamicTextOffsetMirrorXRight = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_dynamic_text_offset_mirror_x_right);
        this.mStaticTextOffsetMirrorXLeft = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_static_text_offset_mirror_x_left);
        this.mStaticTextOffsetMirrorXRight = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_static_text_offset_mirror_x_right);
        this.mExtremeTextTopY = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_m_extreme_text_top_y);
        this.mExtremeTextBottomY = App.getInstance().getResources().getDimension(R.dimen.info_power_curve_m_extreme_text_bottom_y);
        this.mExtremeDynamicTextY = (int) App.getInstance().getResources().getDimension(R.dimen.info_power_curve_m_extreme_dynamic_text_y);
        this.mBoundLinerPaint = new Paint(1);
        this.mExtremeTextPaint = new TextPaint(1);
        this.mDynamicTextPaint = new TextPaint(1);
        float f = HORIZONTAL_PADDING;
        this.mContentStartX = f;
        float f2 = VERTICAL_PADDING;
        this.mContentStartY = f2;
        float f3 = DESIRE_WIDTH - f;
        this.mContentEndX = f3;
        float f4 = DESIRE_HEIGHT;
        float f5 = f4 - f2;
        this.mContentEndY = f5;
        this.mContentHorizontal = f3 - f;
        this.mContentVertical = f5 - f2;
        this.mOuterLinerPaint = new Paint(1);
        this.mInterLinerPaint = new Paint(1);
        this.mInterRemainingPaint = new Paint(1);
        this.mTextMeasureRect = new Rect();
        this.mPositiveColorParse = new int[6];
        this.mNegativeColorParse = new int[6];
        this.mDialPaint = new Paint(1);
        this.mTopMapValues = new float[3];
        this.mBottomMapValues = new float[2];
        this.mCenterX = f + (CURVE_DIAMETER_OUTER / 2.0f);
        this.mCenterY = f4 / 2.0f;
        this.mTextPowerValue = 0.0f;
        this.mShowTextPowerValue = 0.0f;
        this.isMirror = false;
        initView(context, attributeSet);
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
        Paint paint = this.mOuterLinerPaint;
        if (paint != null) {
            paint.setColor(getContext().getColor(R.color.power_outline_color));
        }
        Paint paint2 = this.mInterRemainingPaint;
        if (paint2 != null) {
            paint2.setColor(getContext().getColor(R.color.power_inter_remain_color));
        }
        Paint paint3 = this.mDialPaint;
        if (paint3 != null) {
            paint3.setColor(getContext().getColor(R.color.power_dial_color));
        }
        Paint paint4 = this.mExtremeTextPaint;
        if (paint4 != null) {
            paint4.setColor(getContext().getColor(R.color.power_extreme_text_color));
        }
    }

    private void initView(Context context, AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PowerCurveView);
            this.isMirror = obtainStyledAttributes.getBoolean(0, this.isMirror);
            obtainStyledAttributes.recycle();
        }
        int[] iArr = {ContextCompat.getColor(context, R.color.color_bottom_arc_gradient_end), ContextCompat.getColor(context, R.color.color_bottom_arc_gradient_start), ContextCompat.getColor(context, R.color.color_top_arc_gradient_transparency), ContextCompat.getColor(context, R.color.color_top_arc_gradient_transparency), ContextCompat.getColor(context, R.color.color_top_arc_gradient_end), ContextCompat.getColor(context, R.color.color_top_arc_gradient_start)};
        this.mGradientColors = iArr;
        this.mPositiveColorParse[0] = Color.red(iArr[iArr.length - 2]);
        int[] iArr2 = this.mPositiveColorParse;
        int[] iArr3 = this.mGradientColors;
        iArr2[1] = Color.green(iArr3[iArr3.length - 2]);
        int[] iArr4 = this.mPositiveColorParse;
        int[] iArr5 = this.mGradientColors;
        iArr4[2] = Color.blue(iArr5[iArr5.length - 2]);
        int[] iArr6 = this.mPositiveColorParse;
        int[] iArr7 = this.mGradientColors;
        iArr6[3] = Color.red(iArr7[iArr7.length - 1]);
        int[] iArr8 = this.mPositiveColorParse;
        int[] iArr9 = this.mGradientColors;
        iArr8[4] = Color.green(iArr9[iArr9.length - 1]);
        int[] iArr10 = this.mPositiveColorParse;
        int[] iArr11 = this.mGradientColors;
        iArr10[5] = Color.blue(iArr11[iArr11.length - 1]);
        this.mNegativeColorParse[0] = Color.red(this.mGradientColors[1]);
        this.mNegativeColorParse[1] = Color.green(this.mGradientColors[1]);
        this.mNegativeColorParse[2] = Color.blue(this.mGradientColors[1]);
        this.mNegativeColorParse[3] = Color.red(this.mGradientColors[0]);
        this.mNegativeColorParse[4] = Color.green(this.mGradientColors[0]);
        this.mNegativeColorParse[5] = Color.blue(this.mGradientColors[0]);
        this.mBoundsGradientColors = new int[]{ContextCompat.getColor(context, R.color.color_bounds_bottom_arc_gradient_end), ContextCompat.getColor(context, R.color.color_bounds_bottom_arc_gradient_start), ContextCompat.getColor(context, R.color.color_bounds_top_arc_gradient_transparency), ContextCompat.getColor(context, R.color.color_bounds_top_arc_gradient_transparency), ContextCompat.getColor(context, R.color.color_bounds_top_arc_gradient_end), ContextCompat.getColor(context, R.color.color_bounds_top_arc_gradient_start)};
        float f = ARC_ANGER_PERCENT_START;
        float f2 = ARC_ANGER_PERCENT_ALL;
        float f3 = ARC_NEGATIVE_PERCENT;
        float f4 = ARC_NONE_PERCENT;
        float[] fArr = {f, (f2 * f3) + f, (f2 * f3) + f, ((f3 + f4) * f2) + f, f + (f2 * (f3 + f4)), ARC_ANGER_PERCENT_END};
        this.mSweepGradient = new SweepGradient(this.mCenterX, this.mCenterY, this.mGradientColors, fArr);
        this.mBoundSweepGradient = new SweepGradient(this.mCenterX, this.mCenterY, this.mBoundsGradientColors, fArr);
        Paint paint = this.mOuterLinerPaint;
        float f5 = OUTER_LINE_WIDTH;
        paint.setStrokeWidth(f5);
        this.mOuterLinerPaint.setColor(getContext().getColor(R.color.power_outline_color));
        this.mOuterLinerPaint.setStyle(Paint.Style.STROKE);
        this.mOuterLinerPaint.setAntiAlias(true);
        Paint paint2 = this.mInterLinerPaint;
        float f6 = INNER_LINE_WIDTH;
        paint2.setStrokeWidth(f6);
        this.mInterLinerPaint.setStyle(Paint.Style.STROKE);
        this.mInterLinerPaint.setShader(this.mSweepGradient);
        this.mInterLinerPaint.setAntiAlias(true);
        this.mInterRemainingPaint.setStrokeWidth(f6);
        this.mInterRemainingPaint.setStyle(Paint.Style.STROKE);
        this.mInterRemainingPaint.setColor(getContext().getColor(R.color.power_inter_remain_color));
        this.mInterRemainingPaint.setAntiAlias(true);
        this.mBoundLinerPaint.setStrokeWidth(f5);
        this.mBoundLinerPaint.setStyle(Paint.Style.STROKE);
        this.mBoundLinerPaint.setShader(this.mBoundSweepGradient);
        this.mBoundLinerPaint.setAntiAlias(true);
        this.mExtremeTextPaint.setTextSize(this.mStaticTextSize);
        this.mExtremeTextPaint.setColor(getContext().getColor(R.color.power_extreme_text_color));
        this.mDialPaint.setColor(getContext().getColor(R.color.power_dial_color));
        this.mDialPaint.setStyle(Paint.Style.STROKE);
        this.mDialPaint.setStrokeWidth(DIAL_LINE_WIDTH);
        initMapValues();
        this.mInstantaneousPowerValue = mapValue(this.mTextPowerValue);
    }

    private void initMapValues() {
        int i = 0;
        int i2 = 0;
        while (true) {
            float[] fArr = this.mTopMapValues;
            if (i2 >= fArr.length) {
                break;
            }
            fArr[i2] = ARC_TOP_PERCENT[i2] * mCurrentMaxEnergyValue;
            i2++;
        }
        while (true) {
            float[] fArr2 = this.mBottomMapValues;
            if (i >= fArr2.length) {
                return;
            }
            fArr2[i] = ARC_BOTTOM_PERCENT[i] * mCurrentMinEnergyValue;
            i++;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.isMirror) {
            canvas.save();
            canvas.translate((-HORIZONTAL_PADDING) / 2.0f, 0.0f);
            canvas.rotate(180.0f, this.mCenterX, this.mCenterY);
            canvas.translate(CURVE_DIAMETER_OUTER, 0.0f);
            canvas.scale(1.0f, -1.0f, DESIRE_WIDTH / 2.0f, DESIRE_HEIGHT / 2.0f);
            drawOuter(canvas);
            drawDial(canvas);
            canvas.restore();
        } else {
            drawOuter(canvas);
            drawDial(canvas);
        }
        drawText(canvas);
    }

    private void drawDial(Canvas canvas) {
        if (BaseConfig.getInstance().isSupportArcPowerConsumptionCurveView()) {
            drawArcDialView(canvas);
        } else {
            drawLinerDialView(canvas);
        }
    }

    private void drawLinerDialView(Canvas canvas) {
        int color = getContext().getColor(R.color.power_dial_color);
        float f = this.mContentStartX + (DIAL_LINE_WIDTH / 2.0f);
        this.mDialPaint.setColor(color);
        float f2 = this.mContentStartY;
        float f3 = DIAL_RECT_OFFSET;
        canvas.drawLine(f, f2, f, f2 + f3, this.mDialPaint);
        this.mDialPaint.setColor(color);
        float f4 = this.mContentStartY;
        float f5 = ARC_TOP_HEIGHT;
        canvas.drawLine(f, (f4 + f5) - f3, f, f4 + f5, this.mDialPaint);
        this.mDialPaint.setColor(color);
        float f6 = this.mContentStartY;
        float f7 = ARC_GAP_HEIGHT;
        canvas.drawLine(f, f6 + f5 + f7, f, f6 + f5 + f7 + f3, this.mDialPaint);
        this.mDialPaint.setColor(color);
        float f8 = this.mContentStartY;
        float f9 = ARC_HEIGHT;
        canvas.drawLine(f, (f8 + f9) - f3, f, f8 + f9, this.mDialPaint);
    }

    private void drawArcDialView(Canvas canvas) {
        float f = this.mContentStartX;
        float f2 = DIAL_LINE_WIDTH;
        float f3 = OUTER_LINE_WIDTH;
        float f4 = ((f2 / 2.0f) + f) - (f3 / 2.0f);
        float f5 = DESIRE_HEIGHT;
        float f6 = CURVE_DIAMETER_OUTER;
        float f7 = (((f5 / 2.0f) - (f6 / 2.0f)) + (f2 / 2.0f)) - (f3 / 2.0f);
        float f8 = ((f + f6) - (f2 / 2.0f)) + (f3 / 2.0f);
        float f9 = (((f5 / 2.0f) + (f6 / 2.0f)) - (f2 / 2.0f)) + (f3 / 2.0f);
        int color = getContext().getColor(R.color.power_dial_color);
        this.mDialPaint.setColor(this.mInstantaneousPowerValue >= mCurrentMaxEnergyValue ? getContext().getColor(R.color.power_dial_positive_max_value_color) : color);
        float f10 = ARC_ANGER_HALF;
        canvas.drawArc(f4, f7, f8, f9, (f10 + 180.0f) - DIAL_SWEEP, DIAL_SWEEP, false, this.mDialPaint);
        this.mDialPaint.setColor(this.mInstantaneousPowerValue > 0.0f ? getContext().getColor(R.color.power_dial_positive_min_value_color) : color);
        canvas.drawArc(f4, f7, f8, f9, ARC_TOP_ZERO_OFFSET, DIAL_SWEEP, false, this.mDialPaint);
        this.mDialPaint.setColor(this.mInstantaneousPowerValue < 0.0f ? getContext().getColor(R.color.power_dial_negative_max_value_color) : color);
        canvas.drawArc(f4, f7, f8, f9, ARC_BOTTOM_ZERO_OFFSET - DIAL_SWEEP, DIAL_SWEEP, false, this.mDialPaint);
        Paint paint = this.mDialPaint;
        if (this.mInstantaneousPowerValue <= mCurrentMinEnergyValue) {
            color = getContext().getColor(R.color.power_dial_negative_min_value_color);
        }
        paint.setColor(color);
        canvas.drawArc(f4, f7, f8, f9, 180.0f - f10, DIAL_SWEEP, false, this.mDialPaint);
    }

    private void drawText(Canvas canvas) {
        String str = "" + ((int) mCurrentMaxEnergyValue);
        String str2 = "" + ((int) mCurrentMinEnergyValue);
        this.mExtremeTextPaint.getTextBounds(str, 0, str.length(), this.mTextMeasureRect);
        int width = this.mTextMeasureRect.left + this.mTextMeasureRect.width();
        this.mExtremeTextPaint.getTextBounds(str2, 0, str2.length(), this.mTextMeasureRect);
        int width2 = this.mTextMeasureRect.left + this.mTextMeasureRect.width();
        int i = this.isMirror ? this.mStaticTextOffsetMirrorXRight : this.mStaticTextOffsetMirrorXLeft;
        float f = DESIRE_WIDTH;
        float f2 = i;
        canvas.drawText(str, ((f - width) / 2.0f) + f2, this.mExtremeTextTopY, this.mExtremeTextPaint);
        canvas.drawText(str2, ((f - width2) / 2.0f) + f2, this.mExtremeTextBottomY, this.mExtremeTextPaint);
        String str3 = "" + ((int) this.mShowTextPowerValue);
        this.mDynamicTextPaint.setColor(getDynamicTextColor());
        this.mDynamicTextPaint.setTextSize(this.mDynamicTextSize);
        this.mDynamicTextPaint.getTextBounds(str3, 0, str3.length(), this.mTextMeasureRect);
        int width3 = this.mTextMeasureRect.left + this.mTextMeasureRect.width();
        float f3 = this.isMirror ? (f - this.mDynamicTextOffsetMirrorXRight) - (width3 / 2.0f) : this.mDynamicTextOffsetMirrorX - (width3 / 2.0f);
        canvas.drawText(str3, f3, this.mExtremeDynamicTextY, this.mDynamicTextPaint);
        this.mDynamicTextPaint.setTextSize(this.mDynamicUnitTextSize);
        this.mDynamicTextPaint.getTextBounds(UNIT_KW, 0, 2, this.mTextMeasureRect);
        canvas.drawText(UNIT_KW, f3 + ((width3 - (this.mTextMeasureRect.left + this.mTextMeasureRect.width())) / 2.0f), this.mExtremeDynamicTextY + this.mUnitTopOffset, this.mDynamicTextPaint);
    }

    private int getDynamicTextColor() {
        float f;
        float f2 = this.mInstantaneousPowerValue;
        if (f2 > 0.0f) {
            float f3 = mCurrentMaxEnergyValue;
            f = f3 > 0.0f ? f2 / f3 : 0.0f;
            int[] iArr = this.mPositiveColorParse;
            return Color.rgb(((int) ((iArr[3] - iArr[0]) * f)) + iArr[0], ((int) ((iArr[4] - iArr[1]) * f)) + iArr[1], ((int) (f * (iArr[5] - iArr[2]))) + iArr[2]);
        }
        float f4 = mCurrentMinEnergyValue;
        f = f4 > 0.0f ? f2 / f4 : 0.0f;
        int[] iArr2 = this.mNegativeColorParse;
        return Color.rgb(((int) ((iArr2[3] - iArr2[0]) * f)) + iArr2[0], ((int) ((iArr2[4] - iArr2[1]) * f)) + iArr2[1], ((int) (f * (iArr2[5] - iArr2[2]))) + iArr2[2]);
    }

    private void drawOuter(Canvas canvas) {
        if (BaseConfig.getInstance().isSupportArcPowerConsumptionCurveView()) {
            drawArcOuterView(canvas);
        } else {
            drawLinerOuterView(canvas);
        }
    }

    private void drawLinerOuterView(Canvas canvas) {
        float f = this.mContentStartX;
        float f2 = DIAL_LINE_WIDTH;
        float f3 = OUTER_LINE_WIDTH;
        float f4 = (f + (f2 / 2.0f)) - ((f2 - f3) / 2.0f);
        float f5 = this.mContentStartY;
        float f6 = ARC_TOP_HEIGHT;
        float f7 = DIAL_RECT_OFFSET;
        canvas.drawLine(f4, (f5 + f6) - f7, f4, f5 + f7, this.mOuterLinerPaint);
        float f8 = this.mContentStartY;
        float f9 = ARC_GAP_HEIGHT;
        float f10 = ARC_HEIGHT;
        canvas.drawLine(f4, f8 + f6 + f9 + f7, f4, (f8 + f10) - f7, this.mOuterLinerPaint);
        float f11 = this.mContentStartX + f3 + (INNER_LINE_WIDTH / 2.0f);
        float rectHeight = getRectHeight(this.mRemainingPowerValueOrigin);
        float f12 = this.mContentStartY;
        canvas.drawLine(f11, (f12 + f6) - f7, f11, ((f12 + f6) - f7) - rectHeight, this.mInterRemainingPaint);
        float f13 = this.mContentStartY;
        canvas.drawLine(f11, f13 + f6 + f9 + f7, f11, (f13 + f10) - f7, this.mInterRemainingPaint);
        float rectHeight2 = getRectHeight(this.mTextPowerValue);
        if (this.mTextPowerValue >= 0.0f) {
            float f14 = this.mContentStartY;
            canvas.drawLine(f11, (f14 + f6) - f7, f11, ((f14 + f6) - f7) - rectHeight2, this.mInterLinerPaint);
            return;
        }
        float f15 = this.mContentStartY;
        canvas.drawLine(f11, f15 + f6 + f9 + f7, f11, (((f15 + f6) + f9) + f7) - rectHeight2, this.mInterLinerPaint);
    }

    private void drawArcOuterView(Canvas canvas) {
        float f = ARC_BOTTOM_ZERO_OFFSET;
        float f2 = ARC_ANGER_HALF;
        float f3 = f - (180.0f - f2);
        float f4 = ARC_TOP_ZERO_OFFSET;
        float f5 = this.mContentStartX;
        float f6 = DESIRE_HEIGHT;
        float f7 = CURVE_DIAMETER_OUTER;
        canvas.drawArc(f5, (f6 / 2.0f) - (f7 / 2.0f), f5 + f7, (f7 / 2.0f) + (f6 / 2.0f), 180.0f - f2, f3, false, this.mOuterLinerPaint);
        float f8 = this.mContentStartX;
        float f9 = OUTER_LINE_WIDTH;
        float f10 = INNER_LINE_WIDTH;
        canvas.drawArc(((f9 + f10) / 2.0f) + f8, ((f6 / 2.0f) - (f7 / 2.0f)) + ((f9 + f10) / 2.0f), (f8 + f7) - ((f9 + f10) / 2.0f), ((f6 / 2.0f) + (f7 / 2.0f)) - ((f9 + f10) / 2.0f), 180.0f - f2, f3, false, this.mInterRemainingPaint);
        float f11 = this.mContentStartX;
        canvas.drawArc(f11, (f6 / 2.0f) - (f7 / 2.0f), f11 + f7, (f6 / 2.0f) + (f7 / 2.0f), f4, (f2 + 180.0f) - f4, false, this.mOuterLinerPaint);
        float startAngle = getStartAngle();
        if (!hasMaxMinInstantaneousValue()) {
            XILog.d(TAG, "drawOuter max min is 0");
            return;
        }
        float sweepAngle = getSweepAngle(this.mRemainingPowerValue);
        float f12 = this.mContentStartX;
        canvas.drawArc(f12 + ((f9 + f10) / 2.0f), ((f6 / 2.0f) - (f7 / 2.0f)) + ((f9 + f10) / 2.0f), (f12 + f7) - ((f9 + f10) / 2.0f), ((f6 / 2.0f) + (f7 / 2.0f)) - ((f9 + f10) / 2.0f), f4, sweepAngle, false, this.mInterRemainingPaint);
        float sweepAngle2 = getSweepAngle(this.mInstantaneousPowerValue);
        float f13 = this.mContentStartX;
        canvas.drawArc(f13 + ((f9 + f10) / 2.0f), ((f6 / 2.0f) - (f7 / 2.0f)) + ((f9 + f10) / 2.0f), (f13 + f7) - ((f9 + f10) / 2.0f), ((f6 / 2.0f) + (f7 / 2.0f)) - ((f9 + f10) / 2.0f), startAngle, sweepAngle2, false, this.mInterLinerPaint);
        float f14 = this.mContentStartX;
        canvas.drawArc(f14, (f6 / 2.0f) - (f7 / 2.0f), f14 + f7, (f6 / 2.0f) + (f7 / 2.0f), startAngle, sweepAngle2, false, this.mBoundLinerPaint);
    }

    private float getRectHeight(float f) {
        float f2;
        float f3;
        float f4;
        if (f < 0.0f) {
            f2 = (-f) / mCurrentMinEnergyValue;
            f3 = ARC_BOTTOM_HEIGHT;
            f4 = DIAL_RECT_OFFSET;
        } else {
            f2 = f / mCurrentMaxEnergyValue;
            f3 = ARC_TOP_HEIGHT;
            f4 = DIAL_RECT_OFFSET;
        }
        return f2 * (f3 - (f4 * 2.0f));
    }

    private float getSweepAngle(float f) {
        float f2;
        float f3;
        if (!hasMaxMinInstantaneousValue()) {
            XILog.d(TAG, "getSweepAngle max min is 0");
            return 0.0f;
        }
        if (f < 0.0f) {
            f2 = ARC_ANGER_HALF * 2.0f * ARC_NEGATIVE_PERCENT;
            f = -f;
            f3 = mCurrentMinEnergyValue;
        } else {
            f2 = ARC_ANGER_HALF * 2.0f * ARC_POSITIVE_PERCENT;
            f3 = mCurrentMaxEnergyValue;
        }
        return (f / f3) * f2;
    }

    private float getStartAngle() {
        if (this.mInstantaneousPowerValue < 0.0f) {
            return ARC_BOTTOM_ZERO_OFFSET;
        }
        return ARC_TOP_ZERO_OFFSET;
    }

    private boolean hasMaxMinInstantaneousValue() {
        return (mCurrentMaxEnergyValue == 0.0f || mCurrentMinEnergyValue == 0.0f) ? false : true;
    }

    public void showInstantaneousValue(EnergyBean energyBean) {
        if (energyBean == null) {
            XILog.e(TAG, "showInstantaneousValue energyBean is null");
            return;
        }
        mCurrentMinEnergyValue = energyBean.getMinValue();
        mCurrentMaxEnergyValue = energyBean.getMaxValue();
        initMapValues();
        float instantaneousValue = energyBean.getInstantaneousValue();
        this.mShowTextPowerValue = instantaneousValue;
        this.mTextPowerValue = instantaneousValue;
        float f = mCurrentMaxEnergyValue;
        if (instantaneousValue > f) {
            this.mTextPowerValue = f;
        }
        float f2 = this.mTextPowerValue;
        float f3 = mCurrentMinEnergyValue;
        if (f2 < f3) {
            this.mTextPowerValue = f3;
        }
        this.mInstantaneousPowerValue = mapValue(this.mTextPowerValue);
        if (!hasMaxMinInstantaneousValue()) {
            XILog.d(TAG, "showInstantaneousValue: not max or min value");
        } else {
            invalidate();
        }
    }

    public void showRemainingValue(float f) {
        if (!hasMaxMinInstantaneousValue()) {
            XILog.d(TAG, "showRemainingValue: not max or min value");
            return;
        }
        float f2 = mCurrentMaxEnergyValue;
        if (f > f2) {
            f = f2;
        }
        if (f <= 0.0f) {
            f = 0.0f;
        }
        this.mRemainingPowerValue = mapValue(f);
        this.mRemainingPowerValueOrigin = f;
        invalidate();
    }

    private float mapValue(float f) {
        if (f <= 0.0f) {
            if (f < 0.0f) {
                float[] fArr = ARC_BOTTOM_VALUE_DIV;
                if (f > fArr[0]) {
                    return (f * this.mBottomMapValues[0]) / fArr[0];
                }
                float f2 = f - fArr[0];
                float[] fArr2 = this.mBottomMapValues;
                return ((f2 * fArr2[1]) / fArr[1]) + fArr2[0];
            }
            return f;
        }
        float[] fArr3 = ARC_TOP_VALUE_DIV;
        if (f <= fArr3[0]) {
            return (f * this.mTopMapValues[0]) / fArr3[0];
        }
        if (f - fArr3[0] < fArr3[1]) {
            float f3 = f - fArr3[0];
            float[] fArr4 = this.mTopMapValues;
            return ((f3 * fArr4[1]) / fArr3[1]) + fArr4[0];
        }
        float f4 = (f - fArr3[0]) - fArr3[1];
        float[] fArr5 = this.mTopMapValues;
        return ((f4 * fArr5[2]) / fArr3[2]) + fArr5[0] + fArr5[1];
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension((int) DESIRE_WIDTH, (int) DESIRE_HEIGHT);
    }
}
