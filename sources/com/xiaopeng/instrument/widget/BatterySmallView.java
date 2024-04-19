package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.BatteryBean;
import com.xiaopeng.instrument.bean.BatteryLevelState;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.view.XView;
/* loaded from: classes.dex */
public class BatterySmallView extends XView {
    private static final int BATTERY_COVER_THICKNESS = 4;
    private static final int BATTERY_FRAME_THICKNESS = 5;
    private static final int BATTERY_LEVEL_RADIUS = 3;
    private static final String TAG = "BatterySmallView";
    private BatteryBean mBatteryBean;
    private Bitmap mBatteryFrameBitmap;
    private final Rect mBatteryFrameRect;
    private final int mBatteryLevelDangerousColor;
    private final int mBatteryLevelLowColor;
    private final int mBatteryLevelNormalColor;
    private final Paint mBatteryLevelPaint;
    private final Rect mBatteryLevelRect;
    private final Paint mBitmapPaint;
    private int mDisplayType;
    private int mFullPowerWidth;

    public BatterySmallView(Context context) {
        this(context, null);
    }

    public BatterySmallView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mBatteryBean = new BatteryBean();
        this.mDisplayType = 0;
        int color = ContextCompat.getColor(getContext(), R.color.green_0AE988);
        this.mBatteryLevelNormalColor = color;
        this.mBatteryLevelLowColor = ContextCompat.getColor(getContext(), R.color.orange_F5A623);
        this.mBatteryLevelDangerousColor = ContextCompat.getColor(getContext(), R.color.red_D0021B);
        Paint paint = new Paint(1);
        this.mBitmapPaint = paint;
        paint.setFilterBitmap(true);
        paint.setDither(true);
        Paint paint2 = new Paint(1);
        this.mBatteryLevelPaint = paint2;
        paint2.setColor(color);
        paint2.setStyle(Paint.Style.FILL);
        this.mBatteryFrameRect = new Rect();
        this.mBatteryLevelRect = new Rect();
        this.mBatteryFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bottom_bar_battery_frame);
    }

    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ThemeManager.isThemeChanged(configuration)) {
            XILog.i(TAG, "BatterySmallView onConfigurationChanged" + this.mDisplayType);
            updateBatteryFrameBitmap(this.mDisplayType);
            invalidate();
        }
    }

    public void setBatteryLevel(BatteryBean batteryBean) {
        XILog.d(TAG, "setBatteryLevel " + batteryBean.getLevelValue() + " color:" + batteryBean.getColorState());
        this.mBatteryBean = batteryBean;
        int i = AnonymousClass1.$SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[batteryBean.getColorState().ordinal()];
        if (i == 1) {
            this.mBatteryLevelPaint.setColor(this.mBatteryLevelLowColor);
        } else if (i == 2) {
            this.mBatteryLevelPaint.setColor(this.mBatteryLevelNormalColor);
        } else if (i == 3 || i == 4) {
            this.mBatteryLevelPaint.setColor(this.mBatteryLevelDangerousColor);
        }
        invalidate();
    }

    /* renamed from: com.xiaopeng.instrument.widget.BatterySmallView$1  reason: invalid class name */
    /* loaded from: classes.dex */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState;

        static {
            int[] iArr = new int[BatteryLevelState.values().length];
            $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState = iArr;
            try {
                iArr[BatteryLevelState.BATTERY_LEVEL_LOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[BatteryLevelState.BATTERY_LEVEL_NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[BatteryLevelState.BATTERY_LEVEL_DANGEROUS.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[BatteryLevelState.BATTERY_LEVEL_EMPTY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i <= 0 || i2 <= 0) {
            return;
        }
        this.mBatteryFrameRect.left = 0;
        this.mBatteryFrameRect.top = 0;
        this.mBatteryFrameRect.right = i;
        this.mBatteryFrameRect.bottom = i2;
        this.mBatteryLevelRect.left = this.mBatteryFrameRect.left + 4;
        this.mBatteryLevelRect.top = this.mBatteryFrameRect.top + 5;
        this.mBatteryLevelRect.bottom = this.mBatteryFrameRect.bottom - 5;
        this.mFullPowerWidth = ((this.mBatteryFrameRect.right - 5) - 4) - this.mBatteryLevelRect.left;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int levelValue = this.mBatteryBean.getLevelValue();
        Rect rect = this.mBatteryLevelRect;
        rect.right = rect.left + Math.round((this.mFullPowerWidth * levelValue) / 100.0f);
        canvas.drawBitmap(this.mBatteryFrameBitmap, (Rect) null, this.mBatteryFrameRect, this.mBitmapPaint);
        if (levelValue >= 0) {
            canvas.drawRoundRect(this.mBatteryLevelRect.left, this.mBatteryLevelRect.top, this.mBatteryLevelRect.right, this.mBatteryLevelRect.bottom, 3.0f, 3.0f, this.mBatteryLevelPaint);
        }
    }

    public void updateBatteryFrameBitmap(int i) {
        XILog.i(TAG, "updateBatteryFrameBitmap :" + i);
        this.mDisplayType = i;
        this.mBatteryFrameBitmap = BitmapFactory.decodeResource(getResources(), i == 1 ? R.drawable.bottom_bar_battery_frame_percent : R.drawable.bottom_bar_battery_frame);
        invalidate();
    }
}
