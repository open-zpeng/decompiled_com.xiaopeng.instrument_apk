package com.xiaopeng.instrument;

import android.app.Application;
import android.os.Process;
import android.os.SystemProperties;
import com.xiaopeng.MeterSD.MeterSDRender;
import com.xiaopeng.cluster.backpressure.ProcessorFactory;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.data.ChargeRepository;
import com.xiaopeng.instrument.delegate.DelegateFactory;
import com.xiaopeng.instrument.delegate.IAppDelegate;
import com.xiaopeng.instrument.manager.ClusterDisplayManager;
import com.xiaopeng.instrument.utils.StringUtil;
import com.xiaopeng.instrument.utils.WorkThreadUtil;
import com.xiaopeng.instrument.utils.delegate.StringUtilDelegateFactory;
import com.xiaopeng.lib.bughunter.BugHunter;
import com.xiaopeng.lib.utils.info.BuildInfoUtils;
import com.xiaopeng.xui.Xui;
/* loaded from: classes.dex */
public class App extends Application {
    private static final String PROPERTY_BOOT_COMPLETED = "sys.boot_completed";
    public static final String TAG = "App";
    private static App sAppInstance;
    private IAppDelegate mAppDelegate;

    public static App getInstance() {
        return sAppInstance;
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        String str = TAG;
        XILog.d(str, "App create");
        XILog.i(str, baseInfo());
        sAppInstance = this;
        init();
    }

    private String baseInfo() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(TAG);
        stringBuffer.append("\tInstrument");
        stringBuffer.append("\t");
        stringBuffer.append("RELEASE_TIME :");
        stringBuffer.append(BuildConfig.RELEASE_TIME);
        stringBuffer.append("\t");
        stringBuffer.append("FLAVOR:");
        stringBuffer.append("D55");
        stringBuffer.append("\t");
        stringBuffer.append("DebugModel:");
        stringBuffer.append(false);
        stringBuffer.append("\t");
        stringBuffer.append("PID:" + Process.myPid());
        return stringBuffer.toString();
    }

    @Override // android.app.Application
    public void onTerminate() {
        super.onTerminate();
        MeterSDRender.unInit();
        WorkThreadUtil.getInstance().release();
        ChargeRepository.getInstance().unInit();
        ProcessorFactory.destroy();
    }

    private void init() {
        BugHunter.init(this);
        ClusterDisplayManager.init();
        initDelegate();
        initConfigData();
        Xui.init(this);
        MeterSDRender.init(getApplicationContext());
        StringUtil.setDelegate(StringUtilDelegateFactory.createStringUtilDelegate());
        initCacheData();
        if (hasBootCompleted()) {
            ClusterDisplayManager.startClusterActivity(getApplicationContext());
        }
    }

    private void initCacheData() {
        ChargeRepository.getInstance().init();
    }

    private void initDelegate() {
        this.mAppDelegate = DelegateFactory.createAppDelegate();
    }

    private boolean hasBootCompleted() {
        String str = SystemProperties.get(PROPERTY_BOOT_COMPLETED);
        XILog.i(TAG, "bootState:" + str);
        return BuildInfoUtils.BID_WAN.equals(str);
    }

    private void initConfigData() {
        IAppDelegate iAppDelegate = this.mAppDelegate;
        if (iAppDelegate != null) {
            iAppDelegate.initAppConfigData();
        } else {
            XILog.e(TAG, "mAppDelegate is null");
        }
    }
}
