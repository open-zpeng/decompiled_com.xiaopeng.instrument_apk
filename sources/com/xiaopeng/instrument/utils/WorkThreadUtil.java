package com.xiaopeng.instrument.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/* loaded from: classes.dex */
public class WorkThreadUtil {
    private static final String TAG = "com.xiaopeng.instrument.utils.WorkThreadUtil";
    private static final WorkThreadUtil sInstance = new WorkThreadUtil();
    private final ExecutorService mInitBaseThreadPool = Executors.newSingleThreadExecutor();
    private final ExecutorService mIoThreadPool = Executors.newSingleThreadExecutor();

    public static WorkThreadUtil getInstance() {
        return sInstance;
    }

    public void executeAppInitTask(Runnable runnable) {
        this.mInitBaseThreadPool.execute(runnable);
    }

    public void executeIoTask(Runnable runnable) {
        this.mIoThreadPool.execute(runnable);
    }

    public void release() {
        if (!this.mInitBaseThreadPool.isShutdown()) {
            this.mInitBaseThreadPool.shutdownNow();
        }
        if (this.mIoThreadPool.isShutdown()) {
            return;
        }
        this.mIoThreadPool.shutdownNow();
    }
}
