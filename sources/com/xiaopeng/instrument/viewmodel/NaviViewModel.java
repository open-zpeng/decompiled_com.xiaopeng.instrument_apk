package com.xiaopeng.instrument.viewmodel;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.listener.ICommonListener;
import com.xiaopeng.cluster.listener.INavListener;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.helper.BitmapImageHelper;
import com.xiaopeng.instrument.helper.IDecodeResultListener;
import com.xiaopeng.instrument.utils.ResUtil;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
/* loaded from: classes.dex */
public class NaviViewModel extends ViewModel {
    private static final int MESSAGE_TYPE_IMAGE = 1;
    private static final int NAV_ARROW_ID = 0;
    BitmapImageHelper mBitmapImageHelper;
    private ICommonListener mCommonListener;
    private boolean mHasShowCross;
    private boolean mHasShowLane;
    private INavListener mINavListener;
    private final String TAG = NaviViewModel.class.getSimpleName();
    private final MutableLiveData<Boolean> mNaviGuidenceVisibility = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNaviTBtVisibility = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> mNaviManeuverImage = new MutableLiveData<>();
    private final MutableLiveData<String> mNaviCrossDistance = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNaviCrossDistanceUnit = new MutableLiveData<>();
    private final MutableLiveData<String> mNaviCrossRoadName = new MutableLiveData<>();
    private final MutableLiveData<Integer> mNaviCrossBgResType = new MutableLiveData<>();
    private final MutableLiveData<List<Integer>> mNaviTollGateLaneData = new MutableLiveData<>();
    private final MutableLiveData<int[][]> mNaviNormalLaneData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mNaviLaneBgLiveData = new MutableLiveData<>();
    private final MutableLiveData<Bitmap> mNaviMapImage = new MutableLiveData<>();
    private MutableLiveData<Integer> mGearLiveData = new MutableLiveData<>();
    private int mCurrentGear = 0;
    private Handler mNavBitmapHandler = new Handler() { // from class: com.xiaopeng.instrument.viewmodel.NaviViewModel.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 1 || message == null) {
                return;
            }
            NaviViewModel.this.mNaviMapImage.setValue(NaviViewModel.this.mBitmapImageHelper.createShaderBitmap((Bitmap) message.obj));
        }
    };

    public NaviViewModel() {
        initSignalListener();
    }

    public MutableLiveData<Bitmap> getNaviMapImage() {
        return this.mNaviMapImage;
    }

    public MutableLiveData<Boolean> getNaviLaneBgLiveData() {
        return this.mNaviLaneBgLiveData;
    }

    public MutableLiveData<Boolean> getNaviTBtVisibility() {
        return this.mNaviTBtVisibility;
    }

    public MutableLiveData<Integer> getNaviCrossBgResType() {
        return this.mNaviCrossBgResType;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().removeNavListener(this.mINavListener);
        ClusterManager.getInstance().removeCommonListener(this.mCommonListener);
        this.mBitmapImageHelper.destroy();
    }

    private void initSignalListener() {
        BitmapImageHelper bitmapImageHelper = new BitmapImageHelper();
        this.mBitmapImageHelper = bitmapImageHelper;
        bitmapImageHelper.init();
        this.mINavListener = new INavListener() { // from class: com.xiaopeng.instrument.viewmodel.NaviViewModel.2
            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onNavigationDriveSide(boolean z) {
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onNavigationToast(String str, int i, String str2) {
                NaviViewModel.this.mNaviCrossDistance.postValue(str);
                NaviViewModel.this.mNaviCrossDistanceUnit.postValue(Integer.valueOf(i));
                NaviViewModel.this.mNaviCrossRoadName.postValue(str2);
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onNavigationArrowID(int i) {
                NaviViewModel.this.mNaviManeuverImage.postValue(i >= 0 ? NaviViewModel.this.getManeuverImageById(i) : null);
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onNavigationGuidanceVisible(boolean z) {
                NaviViewModel naviViewModel = NaviViewModel.this;
                naviViewModel.updateTbtBg(z, naviViewModel.mHasShowLane);
                NaviViewModel.this.mNaviGuidenceVisibility.postValue(Boolean.valueOf(z));
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onRefreshImageGuidanceTexture(byte[] bArr, int i, int i2, int i3) {
                Bitmap decodeByteArray;
                if (bArr == null || (decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length)) == null) {
                    return;
                }
                NaviViewModel.this.mNaviManeuverImage.postValue(decodeByteArray);
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onRefreshImageNaviTexture(byte[] bArr, int i, int i2, int i3) {
                if (BaseConfig.getInstance().isSupportNaviSR() || bArr == null) {
                    return;
                }
                NaviViewModel.this.getNavBitmap(bArr, i, i2, i3);
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onNavigationNormalLane(int[] iArr, int[] iArr2, boolean z) {
                NaviViewModel naviViewModel = NaviViewModel.this;
                naviViewModel.updateTbtBg(naviViewModel.mHasShowCross, z);
                NaviViewModel.this.setCrossBgResId(z);
                NaviViewModel.this.mNaviNormalLaneData.postValue(new int[][]{iArr2, iArr});
            }

            @Override // com.xiaopeng.cluster.listener.INavListener
            public void onNavigationTollGateLane(int[] iArr, boolean z) {
                NaviViewModel naviViewModel = NaviViewModel.this;
                naviViewModel.updateTbtBg(naviViewModel.mHasShowCross, z);
                List list = iArr != null ? (List) Arrays.stream(iArr).boxed().collect(Collectors.toList()) : null;
                NaviViewModel.this.setCrossBgResId(z);
                NaviViewModel.this.mNaviTollGateLaneData.postValue(list);
            }
        };
        this.mCommonListener = new ICommonListener() { // from class: com.xiaopeng.instrument.viewmodel.NaviViewModel.3
            @Override // com.xiaopeng.cluster.listener.ICommonListener
            public void onGear(int i) {
                if (NaviViewModel.this.mCurrentGear == i || !BaseConfig.getInstance().isSupportNaviSR()) {
                    return;
                }
                NaviViewModel.this.mCurrentGear = i;
                NaviViewModel.this.mGearLiveData.setValue(Integer.valueOf(i));
            }
        };
        ClusterManager.getInstance().addCommonListener(this.mCommonListener);
        ClusterManager.getInstance().addNavListener(this.mINavListener);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTbtBg(boolean z, boolean z2) {
        boolean z3 = false;
        if (z || z2) {
            this.mNaviTBtVisibility.setValue(true);
        } else if (!z && !z2) {
            this.mNaviTBtVisibility.setValue(false);
        }
        if (z2 && !z) {
            z3 = true;
        }
        this.mNaviLaneBgLiveData.setValue(Boolean.valueOf(z3));
        this.mHasShowCross = z;
        this.mHasShowLane = z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCrossBgResId(boolean z) {
        if (z) {
            this.mNaviCrossBgResType.setValue(1);
        } else {
            this.mNaviCrossBgResType.setValue(0);
        }
    }

    public MutableLiveData<Integer> getGearLiveData() {
        return this.mGearLiveData;
    }

    public MutableLiveData<Bitmap> getNaviManeuverImage() {
        return this.mNaviManeuverImage;
    }

    public MutableLiveData<String> getNaviCrossDistance() {
        return this.mNaviCrossDistance;
    }

    public MutableLiveData<Integer> getNaviCrossDistanceUnit() {
        return this.mNaviCrossDistanceUnit;
    }

    public MutableLiveData<String> getNaviCrossRoadName() {
        return this.mNaviCrossRoadName;
    }

    public MutableLiveData<List<Integer>> getNaviTollGateLaneData() {
        return this.mNaviTollGateLaneData;
    }

    public MutableLiveData<Boolean> getNaviGuidenceVisibility() {
        return this.mNaviGuidenceVisibility;
    }

    public MutableLiveData<int[][]> getNaviNormalLaneData() {
        return this.mNaviNormalLaneData;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getNavBitmap(byte[] bArr, int i, int i2, int i3) {
        this.mBitmapImageHelper.decodeNavArrayAsync(bArr, i2, i, i3, new IDecodeResultListener() { // from class: com.xiaopeng.instrument.viewmodel.NaviViewModel.4
            @Override // com.xiaopeng.instrument.helper.IDecodeResultListener
            public void onComplete() {
            }

            @Override // com.xiaopeng.instrument.helper.IDecodeResultListener
            public void onDecodeSuccess(Bitmap bitmap) {
                Message obtain = Message.obtain();
                obtain.obj = bitmap;
                obtain.what = 1;
                NaviViewModel.this.mNavBitmapHandler.removeMessages(1);
                NaviViewModel.this.mNavBitmapHandler.sendMessage(obtain);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Bitmap getManeuverImageById(int i) {
        int drawableResByName = ResUtil.getDrawableResByName("navi_maneuver_ic_" + i);
        if (drawableResByName != 0) {
            return BitmapFactory.decodeResource(App.getInstance().getApplicationContext().getResources(), drawableResByName);
        }
        return null;
    }
}
