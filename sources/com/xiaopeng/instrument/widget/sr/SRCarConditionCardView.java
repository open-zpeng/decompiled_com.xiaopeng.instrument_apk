package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.CarTyreBean;
import com.xiaopeng.instrument.viewmodel.CarConditionViewModel;
import com.xiaopeng.instrument.widget.CarConditionCardView;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SRCarConditionCardView extends XFrameLayout implements IBaseCustomView {
    public static final int CAR_ITEM_CONDITION_ERROR = 1;
    public static final int CAR_ITEM_CONDITION_NORMAL = 0;
    private static final int RES_DEFAULT_ID = 0;
    private static final float ROTATION_LEFT_CLOSE = -90.0f;
    private static final float ROTATION_LEFT_OPEN = -45.0f;
    private static final float ROTATION_RIGHT_CLOSE = 90.0f;
    private static final float ROTATION_RIGHT_OPEN = 135.0f;
    public static final String TAG = CarConditionCardView.class.getSimpleName();
    private XImageView mIvCoverBackError;
    private XImageView mIvCoverChargeLeftError;
    private XImageView mIvCoverChargeRightError;
    private XImageView mIvCoverFrontError;
    private XImageView mIvDoorLeftBack;
    private XImageView mIvDoorLeftFront;
    private XImageView mIvDoorRightBack;
    private XImageView mIvDoorRightFront;
    private XImageView mIvTyreLeftBack;
    private XImageView mIvTyreLeftFront;
    private XImageView mIvTyreRightBack;
    private XImageView mIvTyreRightFront;
    private LifecycleOwner mLifecycleOwner;
    private XTextView mTvLeftBackTyrePressure;
    private XTextView mTvLeftBackTyrePressureUnit;
    private XTextView mTvLeftFrontTyrePressure;
    private XTextView mTvLeftFrontTyrePressureUnit;
    private XTextView mTvRightBackTyrePressure;
    private XTextView mTvRightBackTyrePressureUnit;
    private XTextView mTvRightFrontTyrePressure;
    private XTextView mTvRightFrontTyrePressureUnit;
    CarConditionViewModel mViewModel;

    private boolean checkResIdInvalid(int i) {
        return i == 0;
    }

    public SRCarConditionCardView(Context context) {
        this(context, null);
    }

    public SRCarConditionCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initContentView();
        initViewModel();
        initObserver();
    }

    public SRCarConditionCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void initContentView() {
        LayoutInflater.from(getContext()).inflate(R.layout.info_card_car_condition_sr, this);
        this.mIvDoorLeftFront = (XImageView) findViewById(R.id.iv_door_left_front);
        this.mIvDoorRightFront = (XImageView) findViewById(R.id.iv_door_right_front);
        this.mIvDoorLeftBack = (XImageView) findViewById(R.id.iv_door_left_back);
        this.mIvDoorRightBack = (XImageView) findViewById(R.id.iv_door_right_back);
        this.mIvCoverFrontError = (XImageView) findViewById(R.id.iv_cover_front_error);
        this.mIvCoverBackError = (XImageView) findViewById(R.id.iv_cover_back_error);
        this.mIvCoverChargeLeftError = (XImageView) findViewById(R.id.iv_cover_charge_left_error);
        this.mIvCoverChargeRightError = (XImageView) findViewById(R.id.iv_cover_charge_right_error);
        this.mIvTyreLeftFront = (XImageView) findViewById(R.id.iv_tyre_left_front);
        this.mIvTyreRightFront = (XImageView) findViewById(R.id.iv_tyre_right_front);
        this.mIvTyreLeftBack = (XImageView) findViewById(R.id.iv_tyre_left_back);
        this.mIvTyreRightBack = (XImageView) findViewById(R.id.iv_tyre_right_back);
        this.mTvLeftFrontTyrePressure = (XTextView) findViewById(R.id.tv_left_front_tyre_pressure);
        this.mTvLeftFrontTyrePressureUnit = (XTextView) findViewById(R.id.tv_left_front_tyre_pressure_unit);
        this.mTvLeftBackTyrePressure = (XTextView) findViewById(R.id.tv_left_back_tyre_pressure);
        this.mTvLeftBackTyrePressureUnit = (XTextView) findViewById(R.id.tv_left_back_tyre_pressure_unit);
        this.mTvRightFrontTyrePressure = (XTextView) findViewById(R.id.tv_right_front_tyre_pressure);
        this.mTvRightFrontTyrePressureUnit = (XTextView) findViewById(R.id.tv_right_front_tyre_pressure_unit);
        this.mTvRightBackTyrePressure = (XTextView) findViewById(R.id.tv_right_back_tyre_pressure);
        this.mTvRightBackTyrePressureUnit = (XTextView) findViewById(R.id.tv_right_back_tyre_pressure_unit);
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (this.mViewModel == null) {
            if (getContext() instanceof ViewModelStoreOwner) {
                this.mViewModel = (CarConditionViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(CarConditionViewModel.class);
            } else {
                this.mViewModel = new CarConditionViewModel();
            }
        }
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getLeftFrontDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$73jF16YZh5XrljVrrlZKG2lPOCY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateLeftFrontDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getRightFrontDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$A3pckR0U766Sd3HB02WSXY56cw0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateRightFrontDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftFrontDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$jVCnFwlAxUlxlhtYTk611zrAPDU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateLeftFrontDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightFrontDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$TWeb-Tq0RTTbNs0gcIFXizEyCX0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateRightFrontDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftBackDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$C-huJPk7lVPUhPMms3LQoEImgg8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateLeftBackDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getRightBackDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$GNBNkfSWNzcNEMQMTbJJNPm4djU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateRightBackDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftBackDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$q9q05o-K2qRxQjunYAzEYIqmGXo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateLeftBackDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightBackDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$tzFpUnueYA27taGVz7WJydM91rY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateRightBackDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getEngineCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$ctsMCb07c47AzeVUb0eX0kQIIOA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateEngineCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getTrunkCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$RVEbYTUgN9h_HclgNl3scsg6lKA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateTrunkCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getFastChargeCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$My-OMOwOkUUP-4MYhmekda2Hc-E
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateFastChargeCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNormalChargeCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$9S_X1XHgr2uSfoR7RpVerVQQK7E
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateNormalChargeCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftFrontTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$UZcS5zx1XoOREx6TJfMTuFlRUU0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateLeftFrontTyreData((CarTyreBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightFrontTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$BR5VPc0xklZ52UtxYwewlRsxAgo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateRightFrontTyreData((CarTyreBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftBackTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$4Bd9RkIy7SVnLGjMn9j5CQcVSGk
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateLeftBackTyreData((CarTyreBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightBackTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRCarConditionCardView$UCRkNX0rl-4BbuUS8PE8pDb2p2c
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRCarConditionCardView.this.updateRightBackTyreData((CarTyreBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightBackTyreData(CarTyreBean carTyreBean) {
        updateTyreData(carTyreBean, this.mIvTyreRightBack, this.mTvRightBackTyrePressure, this.mTvRightBackTyrePressureUnit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftBackTyreData(CarTyreBean carTyreBean) {
        updateTyreData(carTyreBean, this.mIvTyreLeftBack, this.mTvLeftBackTyrePressure, this.mTvLeftBackTyrePressureUnit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightFrontTyreData(CarTyreBean carTyreBean) {
        updateTyreData(carTyreBean, this.mIvTyreRightFront, this.mTvRightFrontTyrePressure, this.mTvRightFrontTyrePressureUnit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftFrontTyreData(CarTyreBean carTyreBean) {
        updateTyreData(carTyreBean, this.mIvTyreLeftFront, this.mTvLeftFrontTyrePressure, this.mTvLeftFrontTyrePressureUnit);
    }

    private void updateTyreData(CarTyreBean carTyreBean, XImageView xImageView, XTextView xTextView, XTextView xTextView2) {
        xImageView.setImageResource(carTyreBean.getTyreImageResByTyreState());
        boolean isTyrePressureNormal = carTyreBean.isTyrePressureNormal();
        boolean isTyreTemperatureNormal = carTyreBean.isTyreTemperatureNormal();
        boolean z = true;
        xTextView.setEnabled(isTyrePressureNormal && isTyreTemperatureNormal);
        if (!isTyrePressureNormal || !isTyreTemperatureNormal) {
            z = false;
        }
        xTextView2.setEnabled(z);
        if (isTyrePressureNormal && !isTyreTemperatureNormal) {
            String tyreTemperature = carTyreBean.getTyreTemperature();
            if (!TextUtils.isEmpty(tyreTemperature)) {
                xTextView.setText(tyreTemperature);
            }
            String tyreTemperatureUnit = carTyreBean.getTyreTemperatureUnit();
            if (TextUtils.isEmpty(tyreTemperatureUnit)) {
                return;
            }
            xTextView2.setText(tyreTemperatureUnit);
            return;
        }
        String tyrePressure = carTyreBean.getTyrePressure();
        if (!TextUtils.isEmpty(tyrePressure)) {
            xTextView.setText(tyrePressure);
        }
        String tyrePressureUnit = carTyreBean.getTyrePressureUnit();
        if (TextUtils.isEmpty(tyrePressureUnit)) {
            return;
        }
        xTextView2.setText(tyrePressureUnit);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNormalChargeCoverState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvCoverChargeRightError.setVisibility((openCloseState == CarConditionCardView.OpenCloseState.ERROR || openCloseState == CarConditionCardView.OpenCloseState.OPEN) ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFastChargeCoverState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvCoverChargeLeftError.setVisibility((openCloseState == CarConditionCardView.OpenCloseState.ERROR || openCloseState == CarConditionCardView.OpenCloseState.OPEN) ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTrunkCoverState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvCoverBackError.setVisibility(openCloseState == CarConditionCardView.OpenCloseState.OPEN ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEngineCoverState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvCoverFrontError.setVisibility(openCloseState == CarConditionCardView.OpenCloseState.OPEN ? 0 : 4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftFrontDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorLeftFront.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftFrontDoorState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvDoorLeftFront.setRotation(openCloseState == CarConditionCardView.OpenCloseState.OPEN ? ROTATION_LEFT_OPEN : ROTATION_LEFT_CLOSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightFrontDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorRightFront.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightFrontDoorState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvDoorRightFront.setRotation(openCloseState == CarConditionCardView.OpenCloseState.OPEN ? ROTATION_RIGHT_OPEN : ROTATION_RIGHT_CLOSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftBackDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorLeftBack.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftBackDoorState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvDoorLeftBack.setRotation(openCloseState == CarConditionCardView.OpenCloseState.OPEN ? ROTATION_LEFT_OPEN : ROTATION_LEFT_CLOSE);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightBackDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorRightBack.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightBackDoorState(CarConditionCardView.OpenCloseState openCloseState) {
        this.mIvDoorRightBack.setRotation(openCloseState == CarConditionCardView.OpenCloseState.OPEN ? ROTATION_RIGHT_OPEN : ROTATION_RIGHT_CLOSE);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + ThemeManager.isThemeChanged(configuration));
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaopeng.instrument.widget.IBaseCustomView
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        if (lifecycleOwner == null) {
            XILog.d(TAG, "mLifecycleOwner is null");
        } else {
            liveData.observe(lifecycleOwner, observer);
        }
    }
}
