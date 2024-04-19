package com.xiaopeng.instrument.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.view.FragmentType;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
/* loaded from: classes.dex */
public class XpuInstrumentClient {
    private static final int DEFAULT_XILog_INTERVAL = 200;
    private static final String KEY_BP_TO_SM_20 = "bp2sm20";
    private static final String KEY_BP_TO_SM_22 = "bp2sm22";
    private static final String KEY_BP_TO_SM_23 = "bp2sm23";
    private static final String KEY_BP_TO_SM_24 = "bp2sm24";
    private static final String KEY_BP_TO_SM_25 = "bp2sm25";
    private static final int OFFSET_22 = 2000;
    private static final int OFFSET_23 = 3000;
    private static final int RECONNECT_EVENT = 1;
    private static final int RECONNECT_INTERVAL = 10000;
    private static final int RECONNECT_MAX_TIMES = 3;
    private static final int SIGN_BLACK_LIGHT = -1;
    private static final int SIGN_INVALID = -1;
    private static final int SIGN_LEFT_GREEN = 1;
    private static final int SIGN_LEFT_RED = 3;
    private static final int SIGN_LEFT_YELLOW = 2;
    private static final int SIGN_NO_LIGHT = 0;
    private static final int SIGN_RIGHT_GREEN = 9;
    private static final int SIGN_RIGHT_RED = 11;
    private static final int SIGN_RIGHT_YELLOW = 10;
    private static final int SIGN_STRAIGHT_GREEN = 5;
    private static final int SIGN_STRAIGHT_RED = 7;
    private static final int SIGN_STRAIGHT_YELLOW = 6;
    private static final int SIGN_TURN_AROUND_GREEN = 13;
    private static final int SIGN_TURN_AROUND_RED = 15;
    private static final int SIGN_TURN_AROUND_YELLOW = 14;
    private static final String TAG = "XpuInstrumentClient";
    private static final String XPU_CLASS_NAME = "com.xiaopeng.xpuservicelibrary.ICMDataService";
    private static final int XPU_DATA = 1;
    private static final String XPU_PACKAGE_NAME = "com.xiaopeng.xpuservice";
    private Set<Integer> m20ValueSet;
    private Set<Integer> m22ValueSet;
    private Set<Integer> m23ValueSet;
    private Context mBindingContext;
    private final Set<IBottomTipsListener> mBottomTipsListeners;
    Messenger mClientMessenger;
    private FragmentType mFragmentType;
    private ReConnectHandler mHandler;
    private HashMap<String, Set<Integer>> mMap;
    private final ServiceConnection mMessengerConnection;
    private Messenger mServerMessenger;
    private ISpeedLimitListener mSpeedLimitListener;
    private ITrafficLightListener mTrafficLightListener;
    private final Set<ITypeChangeListener> mTypeChangeListeners;
    private int retryTime;

    /* loaded from: classes.dex */
    public interface IBottomTipsListener {
        void onBottomTipsChange(int i);
    }

    /* loaded from: classes.dex */
    public interface ISpeedLimitListener {
        void onSpeedChange(int i);
    }

    /* loaded from: classes.dex */
    public interface ITrafficLightListener {
        void onTrafficLightChange(int i);
    }

    /* loaded from: classes.dex */
    public interface ITypeChangeListener {
        void onFragmentTypeChange(FragmentType fragmentType);
    }

    private XpuInstrumentClient() {
        this.retryTime = 0;
        this.mBottomTipsListeners = new LinkedHashSet();
        this.mFragmentType = FragmentType.UNKNOWN;
        this.mTypeChangeListeners = new LinkedHashSet();
        this.mMessengerConnection = new ServiceConnection() { // from class: com.xiaopeng.instrument.manager.XpuInstrumentClient.1
            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                XILog.i(XpuInstrumentClient.TAG, "onServiceConnected:");
                XpuInstrumentClient.this.mServerMessenger = new Messenger(iBinder);
                XpuInstrumentClient.this.notifyConnectToServer();
                XpuInstrumentClient.this.init();
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                XILog.i(XpuInstrumentClient.TAG, "onServiceDisconnected:");
                XpuInstrumentClient.this.mServerMessenger = null;
            }

            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                XILog.i(XpuInstrumentClient.TAG, "onBindingDied");
            }

            @Override // android.content.ServiceConnection
            public void onNullBinding(ComponentName componentName) {
                XILog.i(XpuInstrumentClient.TAG, "onNullBinding");
                XpuInstrumentClient xpuInstrumentClient = XpuInstrumentClient.this;
                xpuInstrumentClient.unBindMessengerService(xpuInstrumentClient.mBindingContext);
            }
        };
        this.mClientMessenger = new Messenger(new Handler(Looper.getMainLooper()) { // from class: com.xiaopeng.instrument.manager.XpuInstrumentClient.2
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message == null || message.what != 1 || message.getData() == null) {
                    return;
                }
                int i = message.getData().getInt(XpuInstrumentClient.KEY_BP_TO_SM_20);
                int i2 = message.getData().getInt(XpuInstrumentClient.KEY_BP_TO_SM_22);
                int i3 = message.getData().getInt(XpuInstrumentClient.KEY_BP_TO_SM_23);
                int i4 = message.getData().getInt(XpuInstrumentClient.KEY_BP_TO_SM_24);
                int i5 = message.getData().getInt(XpuInstrumentClient.KEY_BP_TO_SM_25);
                XILog.i(XpuInstrumentClient.TAG, "message_1228: value20: " + i + " value22: " + i2 + " value23: " + i3 + " value24: " + i4 + " value25: " + i5);
                XpuInstrumentClient.this.sendBottomTipsSign(i, i2, i3);
                XpuInstrumentClient.this.sendTrafficSign(i4);
                XpuInstrumentClient.this.sendSpeedLimitSign(i5);
            }
        });
    }

    /* loaded from: classes.dex */
    private static class XpuInstrumentClientHolder {
        private static final XpuInstrumentClient instance = new XpuInstrumentClient();

        private XpuInstrumentClientHolder() {
        }
    }

    public static XpuInstrumentClient getInstance() {
        return XpuInstrumentClientHolder.instance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        this.mMap = new HashMap<>();
        this.m20ValueSet = DataConfigManager.getNGP20Beans();
        this.m22ValueSet = DataConfigManager.getNGP22Beans();
        this.m23ValueSet = DataConfigManager.getNGP23Beans();
        this.mMap.put(KEY_BP_TO_SM_20, this.m20ValueSet);
        this.mMap.put(KEY_BP_TO_SM_22, this.m22ValueSet);
        this.mMap.put(KEY_BP_TO_SM_23, this.m23ValueSet);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendBottomTipsSign(int i, int i2, int i3) {
        if (!this.mMap.get(KEY_BP_TO_SM_20).contains(Integer.valueOf(i))) {
            if (this.mMap.get(KEY_BP_TO_SM_22).contains(Integer.valueOf(i2))) {
                i = i2 + 2000;
            } else {
                i = this.mMap.get(KEY_BP_TO_SM_23).contains(Integer.valueOf(i3)) ? i3 + 3000 : -1;
            }
        }
        XILog.i(TAG, "sendBottomTipsSign sign: " + i);
        for (IBottomTipsListener iBottomTipsListener : this.mBottomTipsListeners) {
            iBottomTipsListener.onBottomTipsChange(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendSpeedLimitSign(int i) {
        ISpeedLimitListener iSpeedLimitListener = this.mSpeedLimitListener;
        if (iSpeedLimitListener == null) {
            XILog.i(TAG, "sendSpeedLimitSign the mSpeedLimitListener is null");
        } else {
            iSpeedLimitListener.onSpeedChange(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTrafficSign(int i) {
        ITrafficLightListener iTrafficLightListener = this.mTrafficLightListener;
        if (iTrafficLightListener == null) {
            XILog.i(TAG, "sendTrafficSign the mTrafficLightListener is null");
            return;
        }
        switch (i) {
            case 0:
                iTrafficLightListener.onTrafficLightChange(0);
                return;
            case 1:
                iTrafficLightListener.onTrafficLightChange(4);
                return;
            case 2:
                iTrafficLightListener.onTrafficLightChange(5);
                return;
            case 3:
                iTrafficLightListener.onTrafficLightChange(6);
                return;
            case 4:
            case 8:
            case 12:
            default:
                iTrafficLightListener.onTrafficLightChange(-1);
                return;
            case 5:
                iTrafficLightListener.onTrafficLightChange(1);
                return;
            case 6:
                iTrafficLightListener.onTrafficLightChange(2);
                return;
            case 7:
                iTrafficLightListener.onTrafficLightChange(3);
                return;
            case 9:
                iTrafficLightListener.onTrafficLightChange(7);
                return;
            case 10:
                iTrafficLightListener.onTrafficLightChange(8);
                return;
            case 11:
                iTrafficLightListener.onTrafficLightChange(9);
                return;
            case 13:
                iTrafficLightListener.onTrafficLightChange(10);
                return;
            case 14:
                iTrafficLightListener.onTrafficLightChange(11);
                return;
            case 15:
                iTrafficLightListener.onTrafficLightChange(12);
                return;
        }
    }

    public void setFragmentType(FragmentType fragmentType) {
        XILog.i(TAG, "setFragmentType type: " + fragmentType);
        for (ITypeChangeListener iTypeChangeListener : this.mTypeChangeListeners) {
            iTypeChangeListener.onFragmentTypeChange(fragmentType);
        }
        this.mFragmentType = fragmentType;
    }

    private void reConnect() {
        int i = this.retryTime;
        this.retryTime = i + 1;
        if (i < 3) {
            XILog.i(TAG, "reconnect failure: retry time " + this.retryTime);
            if (this.mHandler == null) {
                this.mHandler = new ReConnectHandler(Looper.getMainLooper());
            }
            this.mHandler.sendEmptyMessageDelayed(1, 10000L);
            return;
        }
        XILog.i(TAG, "reconnect end");
        this.retryTime = 0;
    }

    public void bindXpuDataService(Context context) {
        if (context == null) {
            return;
        }
        this.mBindingContext = context;
        Intent intent = new Intent();
        intent.setClassName(XPU_PACKAGE_NAME, XPU_CLASS_NAME);
        boolean bindService = context.bindService(intent, this.mMessengerConnection, 1);
        XILog.i(TAG, "bindMessengerService result: " + bindService);
        if (bindService) {
            return;
        }
        reConnect();
    }

    public void unBindMessengerService(Context context) {
        if (context == null) {
            return;
        }
        XILog.i(TAG, "unBindMessengerService");
        context.unbindService(this.mMessengerConnection);
    }

    public void notifyConnectToServer() {
        Message obtain = Message.obtain();
        obtain.what = 0;
        obtain.replyTo = this.mClientMessenger;
        Bundle bundle = new Bundle();
        bundle.putString("clientid", "icmClient");
        obtain.setData(bundle);
        try {
            this.mServerMessenger.send(obtain);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setTrafficLightListener(ITrafficLightListener iTrafficLightListener) {
        this.mTrafficLightListener = iTrafficLightListener;
    }

    public void setSpeedLimitListener(ISpeedLimitListener iSpeedLimitListener) {
        this.mSpeedLimitListener = iSpeedLimitListener;
    }

    public void addBottomTipsListener(IBottomTipsListener iBottomTipsListener) {
        this.mBottomTipsListeners.add(iBottomTipsListener);
    }

    public void removeBottomTipsListener(IBottomTipsListener iBottomTipsListener) {
        this.mBottomTipsListeners.remove(iBottomTipsListener);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public class ReConnectHandler extends Handler {
        ReConnectHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                XpuInstrumentClient xpuInstrumentClient = XpuInstrumentClient.this;
                xpuInstrumentClient.bindXpuDataService(xpuInstrumentClient.mBindingContext);
            }
        }
    }

    public void addTypeChangeListener(ITypeChangeListener iTypeChangeListener) {
        this.mTypeChangeListeners.add(iTypeChangeListener);
    }

    public void removeTypeChangeListener(ITypeChangeListener iTypeChangeListener) {
        this.mTypeChangeListeners.remove(iTypeChangeListener);
    }
}
