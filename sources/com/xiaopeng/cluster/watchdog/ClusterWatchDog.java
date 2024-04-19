package com.xiaopeng.cluster.watchdog;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import com.xiaopeng.cluster.utils.XILog;
/* loaded from: classes.dex */
public class ClusterWatchDog {
    private static final long DEFAULT_TIMEOUT = 2000;
    private static final int LEVEL_1 = 5;
    private static final int LEVEL_2 = 3;
    private static final int MSG_HEART_BEAT = 2;
    private static final int MSG_MAIN_RESPONSE = 1;
    private static final String TAG = "ClusterWatchDog";
    private static final String WATCH_DOG_NAME = "WATCH_DOG_THREAD";
    private final HandlerThread mClusterWatchThread;
    private final Handler mMainHandler;
    private int mTick;
    private Handler mWorkHandler;

    private ClusterWatchDog() {
        this.mClusterWatchThread = new HandlerThread(WATCH_DOG_NAME);
        this.mTick = 0;
        this.mMainHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() { // from class: com.xiaopeng.cluster.watchdog.ClusterWatchDog.1
            @Override // android.os.Handler.Callback
            public boolean handleMessage(Message message) {
                if (message.what == 1) {
                    ClusterWatchDog.this.mTick = 0;
                }
                return false;
            }
        });
    }

    public static ClusterWatchDog getInstance() {
        return ClusterWatchDogHolder.instance;
    }

    public void startMonitor() {
        if (this.mWorkHandler == null) {
            this.mClusterWatchThread.start();
            this.mWorkHandler = new Handler(this.mClusterWatchThread.getLooper(), new Handler.Callback() { // from class: com.xiaopeng.cluster.watchdog.-$$Lambda$ClusterWatchDog$H7XrupAEWH9pnbxTqX7QPJ-HXcE
                @Override // android.os.Handler.Callback
                public final boolean handleMessage(Message message) {
                    return ClusterWatchDog.this.lambda$startMonitor$0$ClusterWatchDog(message);
                }
            });
        }
        this.mTick = 0;
        sendMessage(false, 2);
    }

    public /* synthetic */ boolean lambda$startMonitor$0$ClusterWatchDog(Message message) {
        if (message.what != 2) {
            return false;
        }
        String str = TAG;
        XILog.d(str, "ClusterWatchDog heart beat : " + this.mTick);
        this.mTick++;
        this.mMainHandler.sendEmptyMessage(1);
        int i = this.mTick;
        if (i >= 5) {
            XILog.e(str, "cluster main tread anr ,kill current process ");
            Process.killProcess(Process.myPid());
        } else if (i >= 3) {
            XILog.w(str, "cluster main tread timeout 6s ");
        }
        sendMessage(true, 2);
        return false;
    }

    private void sendMessage(boolean z, int i) {
        Message obtain = Message.obtain();
        obtain.what = i;
        if (z) {
            this.mWorkHandler.sendMessageDelayed(obtain, DEFAULT_TIMEOUT);
        } else {
            this.mWorkHandler.sendMessage(obtain);
        }
    }

    public void stopMonitor() {
        XILog.i(TAG, "stopWatchDog...");
        Handler handler = this.mWorkHandler;
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    /* loaded from: classes.dex */
    private static class ClusterWatchDogHolder {
        private static final ClusterWatchDog instance = new ClusterWatchDog();

        private ClusterWatchDogHolder() {
        }
    }
}
