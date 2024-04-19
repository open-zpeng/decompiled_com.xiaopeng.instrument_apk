package com.xiaopeng.instrument.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.BatteryLevelState;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.view.XView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class BatteryView extends XView {
    private static final long CHARGING_X_ANIMATOR_DURATION = 2900;
    private static final long CHARGING_X_ANIMATOR_DURATION_FAST = 1500;
    private static final int MAX_LIMIT_LINE_PERCENT = 100;
    private static final String TAG = "BatteryView";
    private Bitmap mBatteryBgBitmap;
    private final Rect mBatteryBgRect;
    private Bitmap mBatteryChargingAnimBitmap;
    private final Rect mBatteryChargingAnimRect;
    private int mBatteryChargingAnimRectWidth;
    private final Rect mBatteryChargingAnimSrcRect;
    Map<Float, RectF> mBatteryColumnClipMap;
    float mBatteryColumnWidth;
    private Bitmap mBatteryFrameBitmap;
    private final Rect mBatteryFrameRect;
    private Bitmap mBatteryGlintBitmap;
    private final Rect mBatteryGlintRect;
    private int mBatteryLevel;
    private AnimatorSet mBatteryLevelAddAnimator;
    private Bitmap mBatteryLevelBitmap;
    private AnimatorSet mBatteryLevelFirstSetAnimator;
    private final Rect mBatteryLevelRect;
    private final Rect mBatteryLevelSrcRect;
    private BatteryLevelState mBatteryLevelState;
    private Bitmap mBatteryLimitLineBitmap;
    private final Paint mBitmapPaint;
    private AnimatorSet mChargingAnimator;
    int mColumnGrow;
    private final Path mFirstSetBatteryLevelClipPath;
    private int mFullPowerHeight;
    private int mFullPowerWidth;
    private boolean mHasPendingAnimator;
    private boolean mIsCharging;
    private boolean mIsDischarging;
    private boolean mIsShowSuperQuickChargeAnim;
    int mLastGrowth;
    private int mLimitLinePercent;
    private final Rect mLimitLineRect;
    private boolean mShouldClipBatteryLevel;
    private static final int BATTERY_FRAME_THICKNESS = (int) App.getInstance().getResources().getDimension(R.dimen.battery_frame_thickness);
    private static final int BATTERY_LIMIT_LINE_THICKNESS = (int) App.getInstance().getResources().getDimension(R.dimen.battery_limit_line_thickness);
    private static final int BATTERY_BG_PADDING_FRAME = (int) App.getInstance().getResources().getDimension(R.dimen.battery_bg_to_frame_padding);
    private static final int BATTERY_GLINT_LIGHT_THICKNESS = (int) App.getInstance().getResources().getDimension(R.dimen.battery_glint_light_thickness);
    private static final int[] LIMIT_LINE_PARAMS = App.getInstance().getResources().getIntArray(R.array.battery_limit_line_array);

    public BatteryView(Context context) {
        this(context, null);
    }

    public BatteryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFirstSetBatteryLevelClipPath = new Path();
        this.mBatteryColumnClipMap = new HashMap();
        this.mLastGrowth = 0;
        this.mBatteryLevel = 0;
        this.mLimitLinePercent = 0;
        this.mIsDischarging = false;
        this.mIsCharging = false;
        this.mBatteryLevelState = BatteryLevelState.BATTERY_LEVEL_NORMAL;
        this.mShouldClipBatteryLevel = false;
        this.mHasPendingAnimator = false;
        this.mIsShowSuperQuickChargeAnim = false;
        Paint paint = new Paint(1);
        this.mBitmapPaint = paint;
        paint.setFilterBitmap(true);
        paint.setDither(true);
        this.mBatteryFrameRect = new Rect();
        this.mBatteryBgRect = new Rect();
        this.mBatteryLevelRect = new Rect();
        this.mLimitLineRect = new Rect();
        this.mBatteryGlintRect = new Rect();
        this.mBatteryChargingAnimRect = new Rect();
        this.mBatteryLevelSrcRect = new Rect();
        this.mBatteryChargingAnimSrcRect = new Rect();
        initBatteryImages();
        post(new Runnable() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$BatteryView$PcSmZ8V3WiVyyHB2POgdA6ucHaM
            @Override // java.lang.Runnable
            public final void run() {
                BatteryView.this.lambda$new$0$BatteryView();
            }
        });
    }

    public /* synthetic */ void lambda$new$0$BatteryView() {
        int i = this.mBatteryLevel;
        if (i > 0) {
            initBatteryLevelFirstSetAnim(i);
        }
    }

    public void setIsCharging(boolean z) {
        if (z) {
            startCharging();
        } else {
            stopCharging();
        }
    }

    private void initBatteryImages() {
        this.mBatteryBgBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_bg);
        this.mBatteryFrameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_frame);
        this.mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level);
        this.mBatteryLimitLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_limit_line);
        this.mBatteryGlintBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_working_glint_light);
        this.mBatteryChargingAnimBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_charging_anim);
        this.mBatteryLevelSrcRect.left = 0;
        this.mBatteryLevelSrcRect.top = 0;
        this.mBatteryLevelSrcRect.bottom = this.mBatteryLevelBitmap.getHeight();
        this.mBatteryLevelSrcRect.right = 0;
        this.mBatteryChargingAnimSrcRect.left = 0;
        this.mBatteryChargingAnimSrcRect.top = 0;
        this.mBatteryChargingAnimSrcRect.bottom = this.mBatteryChargingAnimBitmap.getHeight();
        this.mBatteryChargingAnimSrcRect.right = this.mBatteryChargingAnimBitmap.getWidth();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XILog.d(TAG, "onConfigurationChanged");
        if (ThemeManager.isThemeChanged(configuration)) {
            this.mBatteryLimitLineBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_limit_line);
            invalidate();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.view.XView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimatorSet animatorSet = this.mChargingAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mChargingAnimator = null;
        }
        AnimatorSet animatorSet2 = this.mBatteryLevelAddAnimator;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
            this.mBatteryLevelAddAnimator = null;
        }
        destroyBatteryLevelFirstSetAnim();
    }

    private boolean hasPendingChargingAnimation() {
        return this.mHasPendingAnimator;
    }

    public void startCharging() {
        if (this.mIsCharging) {
            return;
        }
        this.mIsCharging = true;
        if (this.mBatteryChargingAnimRectWidth <= 0 && !hasPendingChargingAnimation()) {
            XILog.d(TAG, "mBatteryChargingAnimRectWidth is 0");
            this.mHasPendingAnimator = true;
            return;
        }
        startChargeAnim();
    }

    private void startChargeAnim() {
        setBatteryLevelState(BatteryLevelState.BATTERY_LEVEL_CHARGING);
        if (this.mChargingAnimator == null) {
            this.mChargingAnimator = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, "chargingRightX", this.mBatteryBgRect.left, this.mBatteryBgRect.right + this.mBatteryChargingAnimRectWidth);
            ofFloat.setRepeatCount(-1);
            ofFloat.setRepeatMode(1);
            ofFloat.setDuration(this.mIsShowSuperQuickChargeAnim ? CHARGING_X_ANIMATOR_DURATION_FAST : CHARGING_X_ANIMATOR_DURATION);
            this.mChargingAnimator.play(ofFloat);
        }
        if (this.mChargingAnimator.isRunning()) {
            this.mChargingAnimator.cancel();
        }
        this.mChargingAnimator.start();
    }

    public void stopCharging() {
        if (this.mIsCharging) {
            this.mIsCharging = false;
            AnimatorSet animatorSet = this.mChargingAnimator;
            if (animatorSet != null && (animatorSet.isStarted() || this.mChargingAnimator.isRunning())) {
                this.mChargingAnimator.cancel();
            }
            this.mHasPendingAnimator = false;
            invalidate();
        }
    }

    public void setIsDischarging(boolean z) {
        this.mIsDischarging = z;
        invalidate();
    }

    public void setBatteryLevelState(BatteryLevelState batteryLevelState) {
        if (batteryLevelState == null || this.mBatteryLevelState == batteryLevelState) {
            return;
        }
        this.mBatteryLevelState = batteryLevelState;
        int i = AnonymousClass2.$SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[batteryLevelState.ordinal()];
        if (i == 1 || i == 2) {
            this.mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level);
        } else if (i == 3) {
            this.mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level_low);
        } else if (i == 4) {
            this.mBatteryLevelBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.charge_battery_level_dangerous);
        }
        invalidate();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.instrument.widget.BatteryView$2  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState;

        static {
            int[] iArr = new int[BatteryLevelState.values().length];
            $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState = iArr;
            try {
                iArr[BatteryLevelState.BATTERY_LEVEL_CHARGING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[BatteryLevelState.BATTERY_LEVEL_NORMAL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[BatteryLevelState.BATTERY_LEVEL_LOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$bean$BatteryLevelState[BatteryLevelState.BATTERY_LEVEL_DANGEROUS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void setBatteryLevel(int i) {
        int i2;
        if (i > 100 || i < 0 || i == (i2 = this.mBatteryLevel)) {
            return;
        }
        this.mBatteryLevel = i;
        destroyBatteryLevelFirstSetAnim();
        AnimatorSet animatorSet = this.mBatteryLevelAddAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
        }
        if (i > i2) {
            startBatteryLevelAddAnim(i2, i);
        } else {
            setBatteryLevelFloat(this.mBatteryLevel);
        }
    }

    private void initBatteryLevelFirstSetAnim(int i) {
        this.mBatteryLevelRect.right = this.mBatteryBgRect.left + ((this.mFullPowerWidth * i) / 100);
        this.mBatteryLevelSrcRect.right = (this.mBatteryLevelBitmap.getWidth() * i) / 100;
        int i2 = this.mBatteryLevelRect.right - this.mBatteryLevelRect.left;
        if (this.mBatteryLevelFirstSetAnimator == null) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.mBatteryLevelFirstSetAnimator = animatorSet;
            animatorSet.setInterpolator(new LinearInterpolator());
            this.mBatteryLevelFirstSetAnimator.setDuration((i * 6000) / 100);
            this.mBatteryLevelFirstSetAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.xiaopeng.instrument.widget.BatteryView.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                    BatteryView.this.mShouldClipBatteryLevel = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    BatteryView.this.mShouldClipBatteryLevel = false;
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    BatteryView.this.mShouldClipBatteryLevel = true;
                    BatteryView.this.mBatteryColumnClipMap.clear();
                }
            });
        }
        if (this.mBatteryLevelFirstSetAnimator.isRunning()) {
            this.mBatteryLevelFirstSetAnimator.cancel();
        }
        this.mBatteryLevelFirstSetAnimator.playTogether(ObjectAnimator.ofFloat(this, "batteryLevelClipRightX", this.mBatteryLevelRect.left, this.mBatteryLevelRect.right + i2), ObjectAnimator.ofInt(this, "batteryLevelClipColumnGrowth", 0, i * 2));
        this.mBatteryLevelFirstSetAnimator.start();
    }

    private void destroyBatteryLevelFirstSetAnim() {
        AnimatorSet animatorSet = this.mBatteryLevelFirstSetAnimator;
        if (animatorSet != null) {
            animatorSet.cancel();
            this.mBatteryLevelFirstSetAnimator = null;
        }
        this.mBatteryColumnClipMap.clear();
    }

    private void startBatteryLevelAddAnim(float f, float f2) {
        if (this.mBatteryLevelAddAnimator == null) {
            AnimatorSet animatorSet = new AnimatorSet();
            this.mBatteryLevelAddAnimator = animatorSet;
            animatorSet.setInterpolator(new LinearInterpolator());
            this.mBatteryLevelAddAnimator.setDuration(600L);
        }
        ArrayList<Animator> childAnimations = this.mBatteryLevelAddAnimator.getChildAnimations();
        if (childAnimations.isEmpty()) {
            this.mBatteryLevelAddAnimator.play(ObjectAnimator.ofFloat(this, "batteryLevelFloat", f, f2));
        } else {
            for (Animator animator : childAnimations) {
                if (animator instanceof ValueAnimator) {
                    ((ValueAnimator) animator).setFloatValues(f, f2);
                }
            }
        }
        this.mBatteryLevelAddAnimator.start();
    }

    public void setLimitLinePercent(int i) {
        this.mLimitLinePercent = i <= 100 ? Math.max(i, 0) : 100;
        invalidate();
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        if (i <= 0 || i2 <= 0) {
            XILog.d(TAG, "on size changed w = 0 or h = 0");
            return;
        }
        this.mBatteryGlintRect.left = 0;
        this.mBatteryGlintRect.top = 0;
        this.mBatteryGlintRect.right = i;
        this.mBatteryGlintRect.bottom = i2;
        Rect rect = this.mBatteryFrameRect;
        int i5 = BATTERY_GLINT_LIGHT_THICKNESS;
        rect.left = i5;
        this.mBatteryFrameRect.top = i5;
        this.mBatteryFrameRect.right = i - i5;
        this.mBatteryFrameRect.bottom = i2 - i5;
        Rect rect2 = this.mBatteryBgRect;
        int i6 = this.mBatteryFrameRect.left;
        int i7 = BATTERY_FRAME_THICKNESS;
        rect2.left = i6 + i7;
        this.mBatteryBgRect.top = this.mBatteryFrameRect.top + i7;
        this.mBatteryBgRect.right = this.mBatteryFrameRect.right - i7;
        this.mBatteryBgRect.bottom = this.mBatteryFrameRect.bottom - i7;
        this.mFullPowerWidth = (this.mBatteryBgRect.right - this.mBatteryBgRect.left) - BATTERY_BG_PADDING_FRAME;
        this.mFullPowerHeight = this.mBatteryBgRect.bottom - this.mBatteryBgRect.top;
        this.mBatteryLevelRect.left = this.mBatteryBgRect.left + 4;
        this.mBatteryLevelRect.top = this.mBatteryBgRect.top + 3;
        this.mBatteryLevelRect.bottom = this.mBatteryBgRect.bottom - 3;
        this.mBatteryLevelRect.right = this.mBatteryBgRect.left - 4;
        this.mBatteryChargingAnimRect.left = this.mBatteryBgRect.left;
        this.mBatteryChargingAnimRect.top = this.mBatteryBgRect.top;
        this.mBatteryChargingAnimRect.bottom = this.mBatteryBgRect.bottom;
        this.mBatteryChargingAnimRect.right = this.mBatteryBgRect.left;
        this.mLimitLineRect.top = this.mBatteryBgRect.top;
        this.mLimitLineRect.bottom = this.mBatteryBgRect.bottom;
        this.mBatteryChargingAnimRectWidth = (this.mBatteryChargingAnimBitmap.getWidth() * (this.mBatteryChargingAnimRect.bottom - this.mBatteryChargingAnimRect.top)) / this.mBatteryChargingAnimBitmap.getHeight();
        String str = TAG;
        XILog.d(str, "on size changed w =" + i + "   h = " + i2);
        int i8 = this.mFullPowerWidth;
        this.mBatteryColumnWidth = (i8 + (i8 / 200.0f)) / 50.0f;
        this.mColumnGrow = this.mFullPowerHeight / 15;
        if (hasPendingChargingAnimation() && this.mIsCharging && this.mBatteryChargingAnimRectWidth > 0) {
            XILog.d(str, "startChargeAnim");
            startChargeAnim();
            this.mHasPendingAnimator = false;
        }
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = this.mBatteryLevelRect;
        rect.right = (int) (rect.left + ((this.mFullPowerWidth * this.mBatteryLevel) / 100.0f));
        this.mBatteryLevelSrcRect.right = (int) ((this.mBatteryLevelBitmap.getWidth() * this.mBatteryLevel) / 100.0f);
        if (!BaseConfig.getInstance().isSupportPerspChargeView()) {
            canvas.drawBitmap(this.mBatteryFrameBitmap, (Rect) null, this.mBatteryFrameRect, this.mBitmapPaint);
            canvas.drawBitmap(this.mBatteryBgBitmap, (Rect) null, this.mBatteryBgRect, this.mBitmapPaint);
        }
        if (this.mBatteryLevel > 0) {
            canvas.save();
            if (this.mShouldClipBatteryLevel) {
                canvas.clipPath(this.mFirstSetBatteryLevelClipPath);
            }
            canvas.drawBitmap(this.mBatteryLevelBitmap, this.mBatteryLevelSrcRect, this.mBatteryLevelRect, this.mBitmapPaint);
            canvas.restore();
        }
        this.mLimitLineRect.left = this.mBatteryLevelRect.left + ((this.mFullPowerWidth * this.mLimitLinePercent) / 100);
        Rect rect2 = this.mLimitLineRect;
        rect2.right = rect2.left + BATTERY_LIMIT_LINE_THICKNESS;
        int limitLineMargin = getLimitLineMargin(this.mLimitLinePercent);
        this.mLimitLineRect.top = this.mBatteryBgRect.top + limitLineMargin;
        this.mLimitLineRect.bottom = this.mBatteryBgRect.bottom - limitLineMargin;
        if (this.mLimitLinePercent > 0) {
            canvas.drawBitmap(this.mBatteryLimitLineBitmap, (Rect) null, this.mLimitLineRect, this.mBitmapPaint);
        }
        if (this.mIsDischarging) {
            canvas.drawBitmap(this.mBatteryGlintBitmap, (Rect) null, this.mBatteryGlintRect, this.mBitmapPaint);
        }
        if (this.mIsCharging) {
            canvas.drawBitmap(this.mBatteryChargingAnimBitmap, this.mBatteryChargingAnimSrcRect, this.mBatteryChargingAnimRect, this.mBitmapPaint);
        }
    }

    private int getLimitLineMargin(int i) {
        int[] iArr = LIMIT_LINE_PARAMS;
        if (i == iArr[0]) {
            return iArr[1];
        }
        return 0;
    }

    public void setBatteryLevelClipRightX(float f) {
        float f2 = this.mBatteryLevelRect.left;
        if (f - this.mBatteryColumnWidth > this.mBatteryLevelRect.left) {
            float f3 = this.mBatteryColumnWidth;
            float f4 = (f - this.mBatteryLevelRect.left) % f3;
            if (f4 != 0.0f) {
                f3 = f4;
            }
            f2 = f - f3;
        }
        RectF rectF = this.mBatteryColumnClipMap.get(Float.valueOf(f2));
        if (rectF != null) {
            rectF.right = f;
        }
        if (rectF == null && f <= this.mBatteryLevelRect.right) {
            addBatteryLevelColumnHeight(2);
            float f5 = ((this.mFullPowerHeight - this.mColumnGrow) / 2.0f) + this.mBatteryLevelRect.top;
            this.mBatteryColumnClipMap.put(Float.valueOf(f2), new RectF(f2, f5, f, this.mColumnGrow + f5));
        }
        RectF rectF2 = null;
        for (Map.Entry<Float, RectF> entry : this.mBatteryColumnClipMap.entrySet()) {
            RectF value = entry.getValue();
            if (rectF2 == null || rectF2.left > value.left) {
                rectF2 = value;
            }
        }
        this.mFirstSetBatteryLevelClipPath.reset();
        if (!this.mBatteryColumnClipMap.isEmpty()) {
            this.mFirstSetBatteryLevelClipPath.moveTo(this.mBatteryLevelRect.left, this.mBatteryLevelRect.top);
            if (rectF2 != null) {
                this.mFirstSetBatteryLevelClipPath.lineTo(rectF2.left, this.mBatteryLevelRect.top);
                this.mFirstSetBatteryLevelClipPath.lineTo(rectF2.left, this.mBatteryLevelRect.bottom);
                this.mFirstSetBatteryLevelClipPath.lineTo(this.mBatteryLevelRect.left, this.mBatteryLevelRect.bottom);
            }
            for (Map.Entry<Float, RectF> entry2 : this.mBatteryColumnClipMap.entrySet()) {
                this.mFirstSetBatteryLevelClipPath.addRect(entry2.getValue(), Path.Direction.CCW);
            }
        }
        this.mFirstSetBatteryLevelClipPath.close();
        if (f > this.mBatteryLevelRect.right && this.mBatteryColumnClipMap.isEmpty()) {
            destroyBatteryLevelFirstSetAnim();
        }
        invalidate();
    }

    public void setBatteryLevelClipColumnGrowth(int i) {
        if (this.mLastGrowth == i) {
            return;
        }
        this.mLastGrowth = i;
        addBatteryLevelColumnHeight(this.mColumnGrow);
    }

    private void addBatteryLevelColumnHeight(int i) {
        ArrayList<Float> arrayList = new ArrayList();
        for (Map.Entry<Float, RectF> entry : this.mBatteryColumnClipMap.entrySet()) {
            RectF value = entry.getValue();
            float f = i;
            value.top -= f;
            value.bottom += f;
            if (value.top <= this.mBatteryLevelRect.top) {
                arrayList.add(entry.getKey());
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (Float f2 : arrayList) {
            this.mBatteryColumnClipMap.remove(f2);
        }
    }

    private void setBatteryLevelFloat(float f) {
        invalidate();
    }

    public void setChargingRightX(float f) {
        int min = (int) Math.min(f, this.mBatteryBgRect.right);
        int max = (int) Math.max(f - this.mBatteryChargingAnimRectWidth, 0.0f);
        if (max > min) {
            return;
        }
        this.mBatteryChargingAnimRect.left = max;
        this.mBatteryChargingAnimRect.right = min;
        if (this.mBatteryChargingAnimRectWidth <= 0) {
            return;
        }
        this.mBatteryChargingAnimSrcRect.right = (this.mBatteryChargingAnimBitmap.getWidth() * (this.mBatteryChargingAnimRect.right - this.mBatteryChargingAnimRect.left)) / this.mBatteryChargingAnimRectWidth;
        invalidate();
    }

    public void setChargeAnimSpeed(boolean z) {
        AnimatorSet animatorSet = this.mChargingAnimator;
        if (animatorSet != null && this.mIsShowSuperQuickChargeAnim != z) {
            animatorSet.setDuration(z ? CHARGING_X_ANIMATOR_DURATION_FAST : CHARGING_X_ANIMATOR_DURATION);
            if (this.mChargingAnimator.isRunning()) {
                this.mChargingAnimator.cancel();
                this.mChargingAnimator.start();
            }
        }
        this.mIsShowSuperQuickChargeAnim = z;
    }
}
