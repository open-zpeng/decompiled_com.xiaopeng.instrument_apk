package com.xiaopeng.instrument.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.utils.WorkThreadUtil;
import java.nio.ByteBuffer;
/* loaded from: classes.dex */
public class BitmapImageHelper {
    private final String TAG = BitmapImageHelper.class.getSimpleName();
    private BitmapShader mBitmapShader;
    private Canvas mCanvas;
    private Bitmap mMarkBitmap;
    private int mMarkHeight;
    private int mMarkWidth;
    private Bitmap mNavBitmap;
    private int mNavBitmapFormat;
    private int mNavHeight;
    private int mNavWidth;
    private Paint mPaint;
    private Bitmap mTargetBitmap;

    public void init() {
        this.mMarkBitmap = BitmapFactory.decodeResource(App.getInstance().getResources(), R.drawable.navi_bg_mask);
        this.mPaint = new Paint();
        this.mCanvas = new Canvas();
        initBitmap();
    }

    private void initBitmap() {
        this.mMarkWidth = this.mMarkBitmap.getWidth();
        int height = this.mMarkBitmap.getHeight();
        this.mMarkHeight = height;
        try {
            this.mTargetBitmap = Bitmap.createBitmap(this.mMarkWidth, height, Bitmap.Config.ARGB_8888);
            this.mBitmapShader = new BitmapShader(this.mMarkBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        recycleBitmap(this.mMarkBitmap);
        this.mMarkBitmap = null;
        recycleBitmap(this.mTargetBitmap);
        this.mTargetBitmap = null;
        this.mBitmapShader = null;
        this.mCanvas.setBitmap(null);
        this.mPaint = null;
        this.mCanvas = null;
        recycleBitmap(this.mNavBitmap);
        this.mNavBitmap = null;
    }

    private void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    private boolean isRecycled(Bitmap bitmap) {
        return bitmap == null || bitmap.isRecycled();
    }

    public Bitmap createShaderBitmap(Bitmap bitmap) {
        if (isRecycled(bitmap) || isRecycled(this.mTargetBitmap) || isRecycled(this.mMarkBitmap)) {
            return null;
        }
        try {
            this.mTargetBitmap.eraseColor(0);
            this.mCanvas.setBitmap(this.mTargetBitmap);
            this.mPaint.setShader(new ComposeShader(this.mBitmapShader, new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP), PorterDuff.Mode.MULTIPLY));
            this.mCanvas.drawRect(0.0f, 0.0f, this.mMarkWidth, this.mMarkHeight, this.mPaint);
        } catch (Exception e) {
            e.printStackTrace();
            XILog.e(this.TAG, "createShaderBitmap error:" + e.toString());
        }
        return this.mTargetBitmap;
    }

    public void decodeNavArrayAsync(final byte[] bArr, final int i, final int i2, final int i3, final IDecodeResultListener iDecodeResultListener) {
        if (i <= 0 || i2 <= 0) {
            XILog.e(this.TAG, "decodeNavArrayAsync error! width:" + i + ",height:" + i2 + ",format:" + i3);
        } else {
            WorkThreadUtil.getInstance().executeIoTask(new Runnable() { // from class: com.xiaopeng.instrument.helper.-$$Lambda$BitmapImageHelper$UwoywPETT0ukekeNFYfGOoLfsc8
                @Override // java.lang.Runnable
                public final void run() {
                    BitmapImageHelper.this.lambda$decodeNavArrayAsync$0$BitmapImageHelper(bArr, i3, i, i2, iDecodeResultListener);
                }
            });
        }
    }

    public /* synthetic */ void lambda$decodeNavArrayAsync$0$BitmapImageHelper(byte[] bArr, int i, int i2, int i3, IDecodeResultListener iDecodeResultListener) {
        Bitmap.Config config;
        try {
            ByteBuffer wrap = ByteBuffer.wrap(bArr);
            if (i == 1) {
                config = Bitmap.Config.RGB_565;
            } else if (i == 5) {
                config = Bitmap.Config.ARGB_8888;
            } else {
                XILog.e(this.TAG, "decodeNavArrayAsync error! unknown format:" + i + ",use RGB_565 instead");
                config = Bitmap.Config.RGB_565;
            }
            if (isRecreateBitmap(i2, i3, i)) {
                XILog.d(this.TAG, "recreate  nav bitmap format change " + i);
                this.mNavHeight = i3;
                this.mNavWidth = i2;
                this.mNavBitmapFormat = i;
                this.mNavBitmap = Bitmap.createBitmap(i2, i3, config);
            }
            if (isRecycled(this.mNavBitmap)) {
                return;
            }
            this.mNavBitmap.copyPixelsFromBuffer(wrap);
            if (iDecodeResultListener != null) {
                iDecodeResultListener.onDecodeSuccess(this.mNavBitmap);
            } else {
                XILog.d(this.TAG, "decodeResultListener is null ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isRecreateBitmap(int i, int i2, int i3) {
        return (i == this.mNavWidth && i2 == this.mNavHeight && i3 == this.mNavBitmapFormat) ? false : true;
    }
}
