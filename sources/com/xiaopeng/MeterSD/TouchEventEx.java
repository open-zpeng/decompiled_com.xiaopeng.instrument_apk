package com.xiaopeng.MeterSD;

import android.view.MotionEvent;
/* loaded from: classes.dex */
public class TouchEventEx {
    private int mPrevPtCount = 0;
    private float mPrevX = 0.0f;
    private float mPrevY = 0.0f;
    private float mPrevX1 = 0.0f;
    private float mPrevY1 = 0.0f;

    /* loaded from: classes.dex */
    public static class TeeEventResult {
        public static final int TEE_RESULT_CLICK = 1;
        public static final int TEE_RESULT_NONE = 0;
        public static final int TEE_RESULT_SINGLE_MOVE = 2;
        public static final int TEE_RESULT_TWOPT_DOWN = 6;
        public static final int TEE_RESULT_TWOPT_MOVE = 3;
        public static final int TEE_RESULT_TWOPT_ROTATE = 5;
        public static final int TEE_RESULT_TWOPT_SCALE = 4;
        public float mMoveX;
        public float mMoveY;
        public float mPt1X;
        public float mPt1Y;
        public float mPt2X;
        public float mPt2Y;
        public double mRotateAngle;
        public double mScaleValue;
        public int mType;
    }

    public boolean onTouchEvent(MotionEvent motionEvent, TeeEventResult teeEventResult) {
        float f;
        float f2;
        int i;
        float f3;
        int i2;
        float f4;
        float f5;
        float f6;
        float f7;
        int action = motionEvent.getAction() & 255;
        int pointerCount = motionEvent.getPointerCount();
        boolean z = false;
        if (action != 0) {
            float f8 = 0.0f;
            if (action != 1) {
                if (action == 2) {
                    float x = motionEvent.getX();
                    float y = motionEvent.getY();
                    if (pointerCount == 2) {
                        f8 = motionEvent.getX(1);
                        f = motionEvent.getY(1);
                    } else {
                        f = 0.0f;
                    }
                    int i3 = this.mPrevPtCount;
                    float f9 = this.mPrevX;
                    float f10 = this.mPrevY;
                    float f11 = this.mPrevX1;
                    float f12 = this.mPrevY1;
                    if (pointerCount == 1 && i3 == 1) {
                        teeEventResult.mType = 2;
                        teeEventResult.mMoveX = x - f9;
                        teeEventResult.mMoveY = y - f10;
                        f2 = x;
                        i2 = pointerCount;
                        z = true;
                        f3 = f8;
                    } else {
                        if (pointerCount == 2 && i3 == 2) {
                            int i4 = (x > f9 ? 1 : (x == f9 ? 0 : -1));
                            if (i4 == 0 && y == f10 && f8 == f11 && f == f12) {
                                return false;
                            }
                            if ((i4 == 0 && y == f10) || (f8 == f11 && f == f12)) {
                                double degrees = Math.toDegrees(Math.atan2(y - f10, x - f9));
                                double degrees2 = Math.toDegrees(Math.atan2(f - f12, f8 - f11));
                                i = pointerCount;
                                f3 = f8;
                                double degrees3 = Math.toDegrees(Math.atan2(f - y, f8 - x));
                                double tanDegressSubAbs = getTanDegressSubAbs(degrees3, degrees);
                                double tanDegressSubAbs2 = getTanDegressSubAbs(degrees3, degrees2);
                                if ((tanDegressSubAbs > 50.0d && tanDegressSubAbs < 130.0d) || (tanDegressSubAbs2 > 50.0d && tanDegressSubAbs2 < 130.0d)) {
                                    teeEventResult.mType = 5;
                                    teeEventResult.mRotateAngle = degrees3 - Math.toDegrees(Math.atan2(f12 - f10, f11 - f9));
                                } else {
                                    float f13 = f11 - f9;
                                    float f14 = f12 - f10;
                                    teeEventResult.mType = 4;
                                    teeEventResult.mScaleValue = Math.sqrt((f7 * f7) + (f6 * f6)) - Math.sqrt((f13 * f13) + (f14 * f14));
                                }
                            } else {
                                i = pointerCount;
                                f3 = f8;
                                double degrees4 = Math.toDegrees(Math.atan2(y - f10, x - f9));
                                double degrees5 = Math.toDegrees(Math.atan2(f - f12, f3 - f11));
                                double tanDegressSubAbs3 = getTanDegressSubAbs(degrees4, degrees5);
                                if (tanDegressSubAbs3 < 30.0d) {
                                    teeEventResult.mType = 3;
                                    teeEventResult.mMoveX = ((x + f3) / 2.0f) - ((f9 + f11) / 2.0f);
                                    teeEventResult.mMoveY = ((f10 + f12) / 2.0f) - ((y + f) / 2.0f);
                                } else {
                                    if (tanDegressSubAbs3 > 130.0d) {
                                        f2 = x;
                                        double degrees6 = Math.toDegrees(Math.atan2(f - y, f3 - x));
                                        double tanDegressSubAbs4 = getTanDegressSubAbs(degrees6, degrees4);
                                        double tanDegressSubAbs5 = getTanDegressSubAbs(degrees6, degrees5);
                                        if ((tanDegressSubAbs4 > 50.0d && tanDegressSubAbs4 < 130.0d) || (tanDegressSubAbs5 > 50.0d && tanDegressSubAbs5 < 130.0d)) {
                                            teeEventResult.mType = 5;
                                            teeEventResult.mRotateAngle = degrees6 - Math.toDegrees(Math.atan2(f12 - f10, f11 - f9));
                                        } else {
                                            float f15 = f11 - f9;
                                            float f16 = f12 - f10;
                                            teeEventResult.mType = 4;
                                            teeEventResult.mScaleValue = Math.sqrt((f5 * f5) + (f4 * f4)) - Math.sqrt((f15 * f15) + (f16 * f16));
                                        }
                                    } else {
                                        f2 = x;
                                        float f17 = f3 - f2;
                                        float f18 = f - y;
                                        float f19 = f11 - f9;
                                        float f20 = f12 - f10;
                                        double sqrt = Math.sqrt((f17 * f17) + (f18 * f18)) - Math.sqrt((f19 * f19) + (f20 * f20));
                                        if (Math.abs(sqrt) > 2.0d) {
                                            teeEventResult.mType = 4;
                                            teeEventResult.mScaleValue = sqrt;
                                        }
                                    }
                                    i2 = i;
                                    z = true;
                                }
                            }
                            f2 = x;
                            i2 = i;
                            z = true;
                        } else {
                            f2 = x;
                            i = pointerCount;
                            f3 = f8;
                        }
                        i2 = i;
                    }
                    this.mPrevPtCount = i2;
                    this.mPrevX = f2;
                    this.mPrevY = y;
                    if (i2 == 2) {
                        this.mPrevX1 = f3;
                        this.mPrevY1 = f;
                        return z;
                    }
                    return z;
                } else if (action != 5) {
                    if (action != 6) {
                        return false;
                    }
                }
            }
            this.mPrevPtCount = 0;
            this.mPrevX = 0.0f;
            this.mPrevY = 0.0f;
            this.mPrevX1 = 0.0f;
            this.mPrevY1 = 0.0f;
            return false;
        }
        if (pointerCount == 2) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            float x3 = motionEvent.getX(1);
            float y3 = motionEvent.getY(1);
            teeEventResult.mType = 6;
            teeEventResult.mPt1X = x2;
            teeEventResult.mPt1Y = y2;
            teeEventResult.mPt2X = x3;
            teeEventResult.mPt2Y = y3;
            return true;
        }
        return false;
    }

    double getTanDegressSubAbs(double d, double d2) {
        if (d < 0.0d) {
            d += 360.0d;
        }
        if (d2 < 0.0d) {
            d2 += 360.0d;
        }
        double abs = Math.abs(d2 - d);
        return abs > 180.0d ? 360.0d - abs : abs;
    }
}
