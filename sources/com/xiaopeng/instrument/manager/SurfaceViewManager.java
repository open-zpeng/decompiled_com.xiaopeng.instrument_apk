package com.xiaopeng.instrument.manager;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Surface;
import android.view.SurfaceView;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.widget.MapCardView;
/* loaded from: classes.dex */
public class SurfaceViewManager {
    private static final String ACTION_MAP_SURFACE_CHANGED = "com.xiaopeng.montecarlo.minimap.ACTION_MAP_SURFACE_CHANGED";
    private static final String ACTION_MAP_SURFACE_CREATE = "com.xiaopeng.montecarlo.minimap.ACTION_MAP_SURFACE_CREATE";
    private static final String ACTION_MAP_SURFACE_DESTROY = "com.xiaopeng.montecarlo.minimap.ACTION_MAP_SURFACE_DESTROY";
    private static final String CLASS_NAME = "com.xiaopeng.montecarlo.service.minimap.MiniMapService";
    private static final String MAP_HEIGHT = "map_height";
    private static final String MAP_SURFACE = "map_surface";
    private static final String MAP_WIDTH = "map_width";
    private static final int MAX_RETRY_TIME = 10;
    private static final int MSG_WHAT_SD_LEFT = 1;
    private static final int MSG_WHAT_SD_RIGHT = 2;
    private static final int MSG_WHAT_SR = 3;
    private static final String PACKAGE_NAME = "com.xiaopeng.montecarlo";
    private static final int RETRY_TIME_INTERVAL = 200;
    private static final int SD_MAP_HEIGHT = 660;
    private static final int SD_MAP_WIDTH = 660;
    private static final int SR_MAP_HEIGHT = 720;
    private static final int SR_MAP_WIDTH = 1920;
    private static final String TAG = "SurfaceViewManager";
    private static Intent mCreateIntent;
    private static Intent mDestroyIntent;
    private Context mContext;
    private int mCurrentMapType;
    private RetryHandler mHandler;
    private boolean mIsSRMode;
    private MapCardView mLeftMapCardView;
    private Surface mLeftSDSurface;
    private int mLeftViewType;
    private MapCardView mRightMapCardView;
    private Surface mRightSDSurface;
    private int mRightViewType;
    private int mSDLeftRetryTime;
    private int mSDRightRetryTime;
    private int mSRRetryTime;
    private Surface mSRSurface;

    static /* synthetic */ int access$308(SurfaceViewManager surfaceViewManager) {
        int i = surfaceViewManager.mSDLeftRetryTime;
        surfaceViewManager.mSDLeftRetryTime = i + 1;
        return i;
    }

    static /* synthetic */ int access$408(SurfaceViewManager surfaceViewManager) {
        int i = surfaceViewManager.mSDRightRetryTime;
        surfaceViewManager.mSDRightRetryTime = i + 1;
        return i;
    }

    static /* synthetic */ int access$508(SurfaceViewManager surfaceViewManager) {
        int i = surfaceViewManager.mSRRetryTime;
        surfaceViewManager.mSRRetryTime = i + 1;
        return i;
    }

    public int getCurrentMapType() {
        return this.mCurrentMapType;
    }

    private SurfaceViewManager() {
        this.mLeftViewType = 0;
        this.mRightViewType = 0;
        this.mCurrentMapType = 0;
        init();
    }

    public static SurfaceViewManager getInstance() {
        return SurfaceViewManagerHolder.instance;
    }

    public int getLeftViewType() {
        return this.mLeftViewType;
    }

    public void setLeftViewType(int i) {
        XILog.i(TAG, "sr_navi_setLeftViewType type is: " + i);
        this.mLeftViewType = i;
    }

    public int getRightViewType() {
        return this.mRightViewType;
    }

    public void setRightViewType(int i) {
        XILog.i(TAG, "sr_navi_setRightViewType type is: " + i);
        this.mRightViewType = i;
    }

    public Surface getSRSurface() {
        return this.mSRSurface;
    }

    public void setIsSRMode(boolean z) {
        this.mIsSRMode = z;
    }

    public void setSRSurface(Surface surface) {
        XILog.i(TAG, "sr_navi_setSRSurface: " + surface);
        this.mSRSurface = surface;
    }

    public void setLeftSDSurface(MapCardView mapCardView) {
        this.mLeftSDSurface = mapCardView.getSurface();
        XILog.i(TAG, "sr_navi_setLeftSDSurface: " + this.mLeftSDSurface);
        this.mLeftMapCardView = mapCardView;
    }

    public void setRightSDSurface(MapCardView mapCardView) {
        this.mRightSDSurface = mapCardView.getSurface();
        XILog.i(TAG, "sr_navi_setRightSDSurface: " + this.mRightSDSurface);
        this.mRightMapCardView = mapCardView;
    }

    private void init() {
        this.mContext = App.getInstance();
        mCreateIntent = new Intent();
        mDestroyIntent = new Intent();
        this.mHandler = new RetryHandler(Looper.getMainLooper());
    }

    public void startCreateService() {
        String str = TAG;
        XILog.i(str, "sr_navi_startCreateService mCurrentMapType: " + this.mCurrentMapType);
        mCreateIntent.setAction(ACTION_MAP_SURFACE_CREATE);
        mCreateIntent.setClassName(PACKAGE_NAME, CLASS_NAME);
        int i = this.mCurrentMapType;
        if (i == 1) {
            mCreateIntent.putExtra(MAP_SURFACE, this.mSRSurface);
        } else if (i == 2) {
            mCreateIntent.putExtra(MAP_SURFACE, this.mLeftSDSurface);
        } else if (i == 3) {
            mCreateIntent.putExtra(MAP_SURFACE, this.mRightSDSurface);
        } else {
            XILog.i(str, "sr_navi_: no surface is return");
            return;
        }
        try {
            this.mContext.startService(mCreateIntent);
        } catch (Exception e) {
            XILog.e(TAG, "startCreateService..." + e.getMessage());
            e.printStackTrace();
        }
    }

    public void startDestroyService() {
        XILog.i(TAG, "sr_navi_startDestroyService");
        mDestroyIntent.setAction(ACTION_MAP_SURFACE_DESTROY);
        mDestroyIntent.setClassName(PACKAGE_NAME, CLASS_NAME);
        try {
            this.mContext.startService(mDestroyIntent);
        } catch (Exception e) {
            XILog.e(TAG, "notify nav destroy Service:" + e.getMessage());
            e.printStackTrace();
        }
        Surface surface = this.mSRSurface;
        if (surface != null) {
            surface.destroy();
        }
        Surface surface2 = this.mLeftSDSurface;
        if (surface2 != null) {
            surface2.destroy();
        }
        Surface surface3 = this.mRightSDSurface;
        if (surface3 != null) {
            surface3.destroy();
        }
        this.mLeftViewType = 0;
        this.mCurrentMapType = 0;
        this.mRightViewType = 0;
        this.mIsSRMode = false;
    }

    private void startChangeService(int i, int i2, Surface surface) {
        Intent intent = new Intent();
        intent.setAction(ACTION_MAP_SURFACE_CHANGED);
        intent.putExtra(MAP_WIDTH, i);
        intent.putExtra(MAP_HEIGHT, i2);
        intent.putExtra(MAP_SURFACE, surface);
        intent.setClassName(PACKAGE_NAME, CLASS_NAME);
        XILog.i(TAG, "sr_navi_startCreateService startChangeService:\twidth:\t" + i + "\theight:\t" + i2);
        try {
            this.mContext.startService(intent);
        } catch (Exception e) {
            XILog.e(TAG, "startChangeService:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void startSRChangeService() {
        if (this.mIsSRMode) {
            if (this.mCurrentMapType != 1) {
                this.mCurrentMapType = 1;
            }
            Surface surface = this.mSRSurface;
            if (surface != null) {
                boolean isValid = surface.isValid();
                XILog.i(TAG, "sr_navi_startSRChangeService valid: " + isValid);
                if (isValid) {
                    startChangeService(SR_MAP_WIDTH, SR_MAP_HEIGHT, this.mSRSurface);
                    this.mSRRetryTime = 0;
                    return;
                }
                if (this.mSRRetryTime == 0) {
                    removeOtherMsg(3);
                }
                this.mHandler.sendEmptyMessageDelayed(3, 200L);
            }
        }
    }

    public void startLeftSDChangeService() {
        if (this.mIsSRMode || this.mLeftViewType == 0) {
            return;
        }
        if (this.mCurrentMapType != 2) {
            this.mCurrentMapType = 2;
        }
        Surface surface = this.mLeftSDSurface;
        if (surface != null) {
            boolean isValid = surface.isValid();
            XILog.i(TAG, "sr_navi_startLeftSDChangeService valid: " + isValid);
            if (isValid) {
                startChangeService(660, 660, this.mLeftSDSurface);
                this.mSDLeftRetryTime = 0;
                return;
            }
            retryAndEndStartService(this.mSDLeftRetryTime, 1, this.mLeftMapCardView);
        }
    }

    public void startRightSDChangeService() {
        if (this.mIsSRMode || this.mRightViewType == 0) {
            return;
        }
        if (this.mCurrentMapType != 3) {
            this.mCurrentMapType = 3;
        }
        Surface surface = this.mRightSDSurface;
        if (surface != null) {
            boolean isValid = surface.isValid();
            XILog.i(TAG, "sr_navi_startRightSDChangeService valid: " + isValid);
            if (isValid) {
                startChangeService(660, 660, this.mRightSDSurface);
                this.mSDRightRetryTime = 0;
                return;
            }
            retryAndEndStartService(this.mSDRightRetryTime, 2, this.mRightMapCardView);
        }
    }

    private void retryAndEndStartService(int i, int i2, MapCardView mapCardView) {
        if (i == 0) {
            removeOtherMsg(i2);
        } else if (i == 5) {
            refreshMapView(mapCardView);
        }
        this.mHandler.sendEmptyMessageDelayed(i2, 200L);
    }

    private void refreshMapView(MapCardView mapCardView) {
        mapCardView.setVisibility(0);
        SurfaceView surfaceView = mapCardView.getSurfaceView();
        if (surfaceView != null) {
            surfaceView.setVisibility(0);
            surfaceView.requestLayout();
            return;
        }
        XILog.e(TAG, "Error: surfaceView is null");
    }

    public void removeOtherMsg(int i) {
        if (i != 1 && this.mHandler.hasMessages(1)) {
            this.mHandler.removeMessages(1);
            this.mSDLeftRetryTime = 0;
        }
        if (i != 2 && this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
            this.mSDRightRetryTime = 0;
        }
        if (i == 3 || !this.mHandler.hasMessages(3)) {
            return;
        }
        this.mHandler.removeMessages(3);
        this.mSRRetryTime = 0;
    }

    /* loaded from: classes.dex */
    private static class SurfaceViewManagerHolder {
        private static final SurfaceViewManager instance = new SurfaceViewManager();

        private SurfaceViewManagerHolder() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class RetryHandler extends Handler {
        RetryHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 1) {
                XILog.i(SurfaceViewManager.TAG, "left retry time: " + SurfaceViewManager.this.mSDLeftRetryTime);
                if (SurfaceViewManager.access$308(SurfaceViewManager.this) >= 10) {
                    SurfaceViewManager.this.mSDLeftRetryTime = 0;
                    XILog.i(SurfaceViewManager.TAG, "left retry failure");
                    return;
                }
                SurfaceViewManager.this.startLeftSDChangeService();
            } else if (i == 2) {
                XILog.i(SurfaceViewManager.TAG, "right retry time: " + SurfaceViewManager.this.mSDRightRetryTime);
                if (SurfaceViewManager.access$408(SurfaceViewManager.this) >= 10) {
                    SurfaceViewManager.this.mSDRightRetryTime = 0;
                    XILog.i(SurfaceViewManager.TAG, "right retry failure");
                    return;
                }
                SurfaceViewManager.this.startRightSDChangeService();
            } else if (i != 3) {
                XILog.i(SurfaceViewManager.TAG, "retry error: " + i);
            } else {
                XILog.i(SurfaceViewManager.TAG, "sr retry time: " + SurfaceViewManager.this.mSRRetryTime);
                if (SurfaceViewManager.access$508(SurfaceViewManager.this) >= 10) {
                    SurfaceViewManager.this.mSRRetryTime = 0;
                    XILog.i(SurfaceViewManager.TAG, "sr retry failure");
                    return;
                }
                SurfaceViewManager.this.startSRChangeService();
            }
        }
    }

    public void resumeMainMap() {
        String str = TAG;
        XILog.i(str, "showMainMap mRightViewType: " + this.mRightViewType + " mLeftViewType: " + this.mLeftViewType);
        if (this.mRightViewType != 0) {
            startRightSDChangeService();
        } else if (this.mLeftViewType != 0) {
            startLeftSDChangeService();
        } else {
            XILog.i(str, "There is no map in main fragment ");
        }
    }
}
