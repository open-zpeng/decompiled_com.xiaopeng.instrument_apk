package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.view.XView;
/* loaded from: classes.dex */
public class FrontRadarWarningView extends XView {
    private static final float ANTI_ALIAS_SCALE = 1.0f;
    private static final float CENTER_X = 230.0f;
    private static final float CENTER_Y = 86.0f;
    private static final int DEGREE_COUNT = 4;
    private static final float DESIRE_HEIGHT = 314.0f;
    private static final float DESIRE_WIDTH = 460.0f;
    private static final float DISTANCE_RANGE = 140.0f;
    private static final float INNER_LIMIT = 140.0f;
    private static final float OUTER_LIMIT = 210.0f;
    private static final int PAINT_BOUND_STROKE_WIDTH = 4;
    private static final int PAINT_STROKE_WIDTH = 24;
    private static final int PAINT_STROKE_WIDTH_HALF = 12;
    private static final int PART_COUNT = 6;
    private static final int PART_SWEEP = 30;
    private static final float RANCE_SCALE = 0.5f;
    private static final String TAG = "FrontRadarWarningView";
    private final int[] mBoundColors;
    private final Paint mBoundPaint;
    private final Paint mShadowPaint;
    private final int[] mWarningDegrees;
    private final float[] mWarningDistances;

    public FrontRadarWarningView(Context context) {
        this(context, null);
    }

    public FrontRadarWarningView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FrontRadarWarningView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mBoundPaint = new Paint(1);
        this.mShadowPaint = new Paint(1);
        this.mBoundColors = new int[4];
        this.mWarningDistances = new float[6];
        this.mWarningDegrees = new int[6];
        initPaint();
    }

    private void initTest() {
        int[] iArr = this.mWarningDegrees;
        iArr[0] = 2;
        iArr[1] = 1;
        iArr[2] = 3;
        iArr[3] = 3;
        iArr[4] = 1;
        iArr[5] = 2;
        float[] fArr = this.mWarningDistances;
        fArr[0] = 210.0f;
        fArr[1] = 210.0f;
        fArr[2] = 210.0f;
        fArr[3] = 210.0f;
        fArr[4] = 210.0f;
        fArr[5] = 210.0f;
    }

    private void initPaint() {
        this.mBoundColors[0] = ContextCompat.getColor(getContext(), R.color.color_transparency);
        this.mBoundColors[1] = ContextCompat.getColor(getContext(), R.color.radar_degree_danger);
        this.mBoundColors[2] = ContextCompat.getColor(getContext(), R.color.radar_degree_attention);
        this.mBoundColors[3] = ContextCompat.getColor(getContext(), R.color.radar_degree_normal);
        this.mBoundPaint.setStyle(Paint.Style.STROKE);
        this.mBoundPaint.setStrokeWidth(4.0f);
        this.mShadowPaint.setStyle(Paint.Style.STROKE);
        this.mShadowPaint.setStrokeWidth(24.0f);
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 6; i++) {
            String str = TAG;
            XILog.d(str, "mWarningDegrees:" + this.mWarningDegrees[i] + " mWarningDistances:" + this.mWarningDistances[i]);
            int[] iArr = this.mWarningDegrees;
            if (iArr[i] < 0 || iArr[i] >= 4 || this.mWarningDistances[i] < 140.0f) {
                XILog.d(str, "draw nothing, current part is PART-" + i + "  degree is:" + this.mWarningDegrees[i]);
            } else {
                this.mBoundPaint.setColor(this.mBoundColors[iArr[i]]);
                drawBounds(canvas, i);
                drawShadow(canvas, i);
            }
        }
    }

    private void drawBounds(Canvas canvas, int i) {
        float f = CENTER_X - this.mWarningDistances[i];
        float f2 = DESIRE_WIDTH - f;
        canvas.drawArc(f, f, f2, f2, (i * 30) + 180.0f, 30.0f, false, this.mBoundPaint);
    }

    private void drawShadow(Canvas canvas, int i) {
        float[] fArr = this.mWarningDistances;
        float f = (fArr[i] + 24.0f) * 1.0f;
        float f2 = CENTER_X - fArr[i];
        float f3 = (f - 24.0f) / f;
        this.mShadowPaint.setShader(new RadialGradient((float) CENTER_X, (float) CENTER_X, f, new int[]{0, 0, this.mBoundColors[this.mWarningDegrees[i]], 0}, new float[]{0.0f, f3, f3, 1.0f}, Shader.TileMode.CLAMP));
        float f4 = f2 - 12.0f;
        float f5 = (DESIRE_WIDTH - f2) + 12.0f;
        canvas.drawArc(f4, f4, f5, f5, (i * 30) + 180.0f, 30.0f, false, this.mShadowPaint);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(460, 314);
    }

    public void setLLDegree(int i) {
        this.mWarningDegrees[0] = i;
        invalidate();
    }

    public void setLCDegree(int i) {
        this.mWarningDegrees[1] = i;
        invalidate();
    }

    public void setLRDegree(int i) {
        this.mWarningDegrees[2] = i;
        invalidate();
    }

    public void setRLDegree(int i) {
        this.mWarningDegrees[3] = i;
        invalidate();
    }

    public void setRCDegree(int i) {
        this.mWarningDegrees[4] = i;
        invalidate();
    }

    public void setRRDegree(int i) {
        this.mWarningDegrees[5] = i;
        invalidate();
    }

    public void setLLDistance(float f) {
        this.mWarningDistances[0] = f;
        invalidate();
    }

    public void setLCDistance(float f) {
        this.mWarningDistances[1] = f;
        invalidate();
    }

    public void setLRDistance(float f) {
        this.mWarningDistances[2] = f;
        invalidate();
    }

    public void setRLDistance(float f) {
        this.mWarningDistances[3] = f;
        invalidate();
    }

    public void setRCDistance(float f) {
        this.mWarningDistances[4] = f;
        invalidate();
    }

    public void setRRDistance(float f) {
        this.mWarningDistances[5] = f;
        invalidate();
    }

    public void updateRadarLevel(int i, int i2) {
        if (i < 0 || i >= this.mWarningDegrees.length) {
            XILog.d(TAG, "invalid radar number: " + i);
        } else if (i2 < 0 || i2 > 3) {
            XILog.d(TAG, "invalid radar level: " + i2);
        } else {
            XILog.d(TAG, "update radar level , radar number:" + i + " level: " + i2);
            this.mWarningDegrees[i] = i2;
            invalidate();
        }
    }

    public void updateRadarDistance(int i, int i2) {
        if (i < 0 || i >= this.mWarningDistances.length) {
            XILog.d(TAG, "invalid radar number:" + i);
            return;
        }
        XILog.d(TAG, "update radar distance , radar number:" + i + " distance: " + i2);
        this.mWarningDistances[i] = (i2 * RANCE_SCALE) + 140.0f;
        invalidate();
    }
}
