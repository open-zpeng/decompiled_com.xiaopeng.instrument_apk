package com.xiaopeng.MeterSD;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;
import com.xiaopeng.MeterSD.TouchEventEx;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
/* loaded from: classes.dex */
public class MeterSDRender {
    private static final String TAG = "metersd";
    public static boolean mInited = false;
    public static MainSurfaceView mMainSurfaceView;
    public static String mResPath;

    public static void changeTheme(String str) {
    }

    private static native boolean native_getXeConfigBool(String str);

    private static native boolean native_init(String str, String str2);

    private static native boolean native_initBase(String str);

    private static native boolean native_isPauseRender();

    private static native void native_onDraw();

    private static native void native_onPause();

    private static native void native_onResume();

    private static native void native_onSurfaceChanged(int i, int i2);

    private static native void native_onSurfaceCreated();

    private static native void native_onSurfaceDestroyed();

    private static native void native_onTouch(int i, float f, float f2);

    private static native void native_onTouchEventResult(Object obj);

    private static native void native_pauseRender();

    private static native void native_replay();

    private static native void native_resetView();

    private static native void native_setAssetManager(Object obj);

    private static native void native_setCmdKeyValue(String str);

    private static native void native_setDebugString(String str);

    private static native void native_setFillFrameFPS(int i);

    private static native void native_setGear(String str);

    private static native void native_setMeterData(String str, String str2);

    private static native void native_setPlateNo(String str);

    private static native void native_setXeConfigBool(String str, boolean z, boolean z2);

    private static native void native_setXeConfigInt(String str, int i);

    private static native void native_showTestOtherCar(boolean z);

    private static native void native_switchDayNight(boolean z);

    private static native void native_switchShowPoint();

    private static native void native_testLidarSDView(boolean z);

    private static native void native_testRoadline(boolean z);

    private static native void native_unInit();

    private static native void native_unInitBase();

    private static native void native_useOnlineData(boolean z);

    public static void jniRequestRender() {
        MainSurfaceView mainSurfaceView = mMainSurfaceView;
        if (mainSurfaceView != null) {
            mainSurfaceView.requestRender();
        }
    }

    public static boolean init(Context context) {
        String str = context.getFilesDir().getAbsolutePath() + "/data";
        mResPath = str;
        Log.v("metersd", "[MeterSDRender::init] path = " + str);
        if (!mInited) {
            copyAssets(context.getAssets(), "data", str);
            mInited = true;
        }
        Log.v("metersd", "[MeterSDRender::init] product = " + Build.PRODUCT);
        return native_initBase(BuildConfig.MAVEN_VERSION_NAME);
    }

    private static void copyAssets(AssetManager assetManager, String str, String str2) {
        boolean z;
        InputStream open;
        try {
            String[] list = assetManager.list(str);
            File file = new File(str2);
            if (!file.exists()) {
                file.mkdirs();
            }
            for (int i = 0; i < list.length; i++) {
                try {
                    String str3 = list[i];
                    try {
                        assetManager.open(str + "/" + str3).close();
                    } catch (FileNotFoundException unused) {
                        z = true;
                    } catch (IOException e) {
                        Log.e("metersd", "[copyAssets] open file failed. (" + i + ") filename = " + str3);
                        e.printStackTrace();
                    }
                    z = false;
                    if (z) {
                        if (str.length() == 0) {
                            copyAssets(assetManager, str3, str2 + str3 + "/");
                        } else {
                            copyAssets(assetManager, str + "/" + str3, str2 + "/" + str3 + "/");
                        }
                    } else {
                        File file2 = new File(file, str3);
                        if (str.length() != 0) {
                            open = assetManager.open(str + "/" + str3);
                        } else {
                            open = assetManager.open(str3);
                        }
                        if (file2.exists()) {
                            file2.delete();
                        }
                        FileOutputStream fileOutputStream = new FileOutputStream(file2);
                        byte[] bArr = new byte[4096];
                        while (true) {
                            int read = open.read(bArr);
                            if (read <= 0) {
                                break;
                            }
                            fileOutputStream.write(bArr, 0, read);
                        }
                        open.close();
                        fileOutputStream.close();
                    }
                } catch (FileNotFoundException e2) {
                    Log.e("metersd", "[copyAssets] FileNotFoundException (" + i + ")");
                    e2.printStackTrace();
                } catch (IOException e3) {
                    Log.e("metersd", "[copyAssets] IOException (" + i + ")");
                    e3.printStackTrace();
                }
            }
        } catch (IOException unused2) {
            Log.e("metersd", "[copyAssets] list dir failed.");
        }
    }

    public static void unInit() {
        native_unInitBase();
    }

    public static boolean initNative(Context context) {
        return native_init(mResPath, Build.PRODUCT);
    }

    public static void unInitNative() {
        native_unInit();
    }

    public static void onSurfaceCreated() {
        native_onSurfaceCreated();
    }

    public static void onSurfaceDestroyed() {
        native_onSurfaceDestroyed();
    }

    public static void onSurfaceChanged(int i, int i2) {
        native_onSurfaceChanged(i, i2);
    }

    public static void onDraw() {
        native_onDraw();
    }

    public static void onPause() {
        native_onPause();
    }

    public static void onResume() {
        native_onResume();
    }

    public static void setAssetManager(AssetManager assetManager) {
        native_setAssetManager(assetManager);
    }

    public static void onTouch(int i, float f, float f2) {
        native_onTouch(i, f, f2);
    }

    public static void onTouchEventResult(TouchEventEx.TeeEventResult teeEventResult) {
        native_onTouchEventResult(teeEventResult);
    }

    public static void replay() {
        native_replay();
    }

    public static void pauseRender() {
        native_pauseRender();
    }

    public static boolean isPauseRender() {
        return native_isPauseRender();
    }

    public static void resetView() {
        native_resetView();
    }

    public static void switchShowPoint() {
        native_switchShowPoint();
    }

    public static void setXeConfigBool(String str, boolean z, boolean z2) {
        native_setXeConfigBool(str, z, z2);
    }

    public static boolean getXeConfigBool(String str) {
        return native_getXeConfigBool(str);
    }

    public static void setXeConfigInt(String str, int i) {
        native_setXeConfigInt(str, i);
    }

    public static void setMeterData(String str, String str2) {
        native_setMeterData(str, str2);
    }

    public static void setFillFrameFPS(int i) {
        native_setFillFrameFPS(i);
    }

    public static void useOnlineData(boolean z) {
        native_useOnlineData(z);
    }

    public static void setGear(String str) {
        native_setGear(str);
    }

    public static void setPlateNo(String str) {
        native_setPlateNo(str);
    }

    public static void setCmdKeyValue(String str) {
        native_setCmdKeyValue(str);
    }

    public static void switchDayNight(boolean z) {
        native_switchDayNight(z);
    }

    public static void testLidarSDView(boolean z) {
        native_testLidarSDView(z);
    }

    public static void testRoadline(boolean z) {
        native_testRoadline(z);
    }

    public static void showTestOtherCar(boolean z) {
        native_showTestOtherCar(z);
    }

    static {
        System.loadLibrary("renderjni");
    }
}
