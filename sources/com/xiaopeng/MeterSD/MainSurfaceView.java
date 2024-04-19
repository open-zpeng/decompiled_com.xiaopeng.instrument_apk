package com.xiaopeng.MeterSD;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import com.xiaopeng.MeterSD.TouchEventEx;
import java.util.ArrayList;
import java.util.List;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;
/* loaded from: classes.dex */
public class MainSurfaceView extends GLSurfaceView {
    private static final int ONEGG_TOUCH_COUNT = 8;
    private static final String TAG = "metersd";
    private boolean mIsDrawing;
    private NaviSRCallback mNaviSRCallback;
    private MainRenderer mRenderer;
    private boolean mStopDraw;
    public int mSurfaceHeight;
    public int mSurfaceWidth;
    private TouchEventEx.TeeEventResult mTeeEventResult;
    private TouchEventEx mTouchEventEx;
    private List<Long> mTouchTime;

    /* loaded from: classes.dex */
    public interface NaviSRCallback {
        void onInitedOpenGL();
    }

    public MainSurfaceView(Context context) {
        super(context);
        this.mTouchEventEx = new TouchEventEx();
        this.mTeeEventResult = new TouchEventEx.TeeEventResult();
        this.mIsDrawing = false;
        this.mStopDraw = false;
        this.mTouchTime = new ArrayList();
    }

    public MainSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTouchEventEx = new TouchEventEx();
        this.mTeeEventResult = new TouchEventEx.TeeEventResult();
        this.mIsDrawing = false;
        this.mStopDraw = false;
        this.mTouchTime = new ArrayList();
    }

    public void init(boolean z, NaviSRCallback naviSRCallback) {
        setEGLContextClientVersion(3);
        this.mNaviSRCallback = naviSRCallback;
        setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.1
            @Override // android.opengl.GLSurfaceView.EGLConfigChooser
            public EGLConfig chooseConfig(EGL10 egl10, EGLDisplay eGLDisplay) {
                EGLConfig[] eGLConfigArr = new EGLConfig[1];
                int[] iArr = new int[1];
                egl10.eglChooseConfig(eGLDisplay, new int[]{12329, 0, 12352, 4, 12351, 12430, 12324, 8, 12323, 8, 12322, 8, 12325, 24, 12338, 1, 12337, 4, 12344}, eGLConfigArr, 1, iArr);
                if (iArr[0] == 0) {
                    Log.e("metersd", "eglChooseConfig failed!");
                    return null;
                }
                return eGLConfigArr[0];
            }
        });
        MainRenderer mainRenderer = new MainRenderer();
        this.mRenderer = mainRenderer;
        setRenderer(mainRenderer);
        setRenderMode(0);
        MeterSDRender.mMainSurfaceView = this;
        setPreserveEGLContextOnPause(true);
        MeterSDRender.initNative(getContext());
    }

    public void unInit() {
        MeterSDRender.unInitNative();
        this.mNaviSRCallback = null;
        MeterSDRender.mMainSurfaceView = null;
    }

    @Override // android.opengl.GLSurfaceView, android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        super.surfaceCreated(surfaceHolder);
        Log.v("metersd", "[MainSurfaceView] surfaceCreated...");
    }

    @Override // android.opengl.GLSurfaceView, android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.v("metersd", "[MainSurfaceView] surfaceDestroyed...");
        super.surfaceDestroyed(surfaceHolder);
    }

    /* loaded from: classes.dex */
    public class MainRenderer implements GLSurfaceView.Renderer {
        public MainRenderer() {
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig) {
            Log.v("metersd", "[MainRenderer.onSurfaceCreated] " + gl10.glGetString(7938));
            MeterSDRender.onSurfaceCreated();
            if (MainSurfaceView.this.mNaviSRCallback != null) {
                MainSurfaceView.this.mNaviSRCallback.onInitedOpenGL();
            }
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onSurfaceChanged(GL10 gl10, int i, int i2) {
            if (MainSurfaceView.this.mStopDraw) {
                return;
            }
            Log.v("metersd", "[MainRenderer.onSurfaceChanged] width = " + i + ", height = " + i2);
            MainSurfaceView.this.mSurfaceWidth = i;
            MainSurfaceView.this.mSurfaceHeight = i2;
            MeterSDRender.onSurfaceChanged(i, i2);
        }

        @Override // android.opengl.GLSurfaceView.Renderer
        public void onDrawFrame(GL10 gl10) {
            if (!MainSurfaceView.this.mStopDraw) {
                MainSurfaceView.this.mIsDrawing = true;
                MeterSDRender.onDraw();
                MainSurfaceView.this.mIsDrawing = false;
                return;
            }
            Log.v("metersd", "[MainSurfaceView] onDrawFrame...");
            GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
            GLES20.glClear(16640);
        }
    }

    private Bitmap getBitmap(Context context, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        return BitmapFactory.decodeResource(context.getResources(), i, options);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction() & 255;
        MeterSDRender.onTouch(action, motionEvent.getX(), motionEvent.getY());
        TouchEventEx.TeeEventResult teeEventResult = this.mTeeEventResult;
        if (this.mTouchEventEx.onTouchEvent(motionEvent, teeEventResult)) {
            MeterSDRender.onTouchEventResult(teeEventResult);
        }
        if (action == 5 && motionEvent.getPointerCount() == 2) {
            this.mTouchTime.add(Long.valueOf(System.currentTimeMillis()));
            if (this.mTouchTime.size() == 9) {
                this.mTouchTime.remove(0);
            }
            if (this.mTouchTime.size() != 8 || System.currentTimeMillis() - this.mTouchTime.get(0).longValue() >= 1500) {
                return true;
            }
            showSettings();
            this.mTouchTime.clear();
            return true;
        }
        return true;
    }

    private void showSettings() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.navisr_settings, (ViewGroup) null, false);
        AlertDialog create = new AlertDialog.Builder(getContext()).setView(inflate).create();
        final CheckBox checkBox = (CheckBox) inflate.findViewById(R.id.showLog);
        checkBox.setChecked(MeterSDRender.getXeConfigBool("showDebugString"));
        checkBox.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainSurfaceView.this.showDebugString(checkBox.isChecked());
            }
        });
        final CheckBox checkBox2 = (CheckBox) inflate.findViewById(R.id.fillFrame);
        checkBox2.setChecked(MeterSDRender.getXeConfigBool("enableInsertFrame"));
        checkBox2.setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MeterSDRender.setXeConfigBool("enableInsertFrame", false, true);
            }
        });
        inflate.findViewById(R.id.fillFrame20).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainSurfaceView.this.setFillFrameFPS(21, checkBox2);
            }
        });
        inflate.findViewById(R.id.fillFrame30).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainSurfaceView.this.setFillFrameFPS(31, checkBox2);
            }
        });
        inflate.findViewById(R.id.fillFrame40).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainSurfaceView.this.setFillFrameFPS(41, checkBox2);
            }
        });
        inflate.findViewById(R.id.fillFrame50).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainSurfaceView.this.setFillFrameFPS(51, checkBox2);
            }
        });
        inflate.findViewById(R.id.fillFrame60).setOnClickListener(new View.OnClickListener() { // from class: com.xiaopeng.MeterSD.MainSurfaceView.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                MainSurfaceView.this.setFillFrameFPS(60, checkBox2);
            }
        });
        create.show();
    }

    public void setFillFrameFPS(int i, CheckBox checkBox) {
        MeterSDRender.setFillFrameFPS(i);
        MeterSDRender.setXeConfigBool("enableInsertFrame", true, false);
        checkBox.setChecked(true);
    }

    public void showDebugString(boolean z) {
        MeterSDRender.setXeConfigBool("showDebugString", z, false);
        requestRender();
    }
}
