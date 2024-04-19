package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class BaseSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private static final String TAG = "BaseSurfaceView";
    private static BaseSurfaceView sInstance;
    private SurfaceHolder mHolder;

    @Override // java.lang.Runnable
    public void run() {
    }

    public BaseSurfaceView(Context context) {
        super(context);
        initView();
    }

    public BaseSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView();
    }

    public BaseSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
    }

    public static BaseSurfaceView getInstance() {
        return sInstance;
    }

    private void initView() {
        XILog.d(TAG, "sr_navi_initView");
        SurfaceHolder holder = getHolder();
        this.mHolder = holder;
        holder.addCallback(this);
        sInstance = this;
    }

    public Surface getSurface() {
        return this.mHolder.getSurface();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        XILog.d(TAG, "sr_navi_surfaceCreated");
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        XILog.d(TAG, "sr_navi_surfaceChanged");
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        XILog.d(TAG, "sr_navi_surfaceDestroyed");
    }
}
