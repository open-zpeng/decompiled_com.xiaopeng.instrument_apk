package com.xiaopeng.instrument.widget;

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
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class CarConditionCardView extends XFrameLayout implements IBaseCustomView {
    public static final int CAR_ITEM_CONDITION_ERROR = 1;
    public static final int CAR_ITEM_CONDITION_NORMAL = 0;
    private static final int RES_DEFAULT_ID = 0;
    private static final float ROTATION_MIN_VALUE = 0.0f;
    public static final String TAG = "CarConditionCardView";
    private final float ROTATION_MAX_VALUE;
    private XImageView mIvCoverBackError;
    private XImageView mIvCoverChargeLeftError;
    private XImageView mIvCoverChargeRightError;
    private XImageView mIvCoverFrontError;
    private XImageView mIvDoorLeftBack;
    private XImageView mIvDoorLeftFront;
    private XImageView mIvDoorRightBack;
    private XImageView mIvDoorRightFront;
    private XImageView mIvIndicatorCoverBackOpen;
    private XImageView mIvIndicatorCoverChargeLeftOpen;
    private XImageView mIvIndicatorCoverChargeRightOpen;
    private XImageView mIvIndicatorCoverFrontOpen;
    private XImageView mIvTyreLeftBack;
    private XImageView mIvTyreLeftFront;
    private XImageView mIvTyreRightBack;
    private XImageView mIvTyreRightFront;
    private CarTyreBean mLeftBackBean;
    private CarTyreBean mLeftFrontBean;
    private LifecycleOwner mLifecycleOwner;
    private CarTyreBean mRightBackBean;
    private CarTyreBean mRightFrontBean;
    private XTextView mTvLeftBackTyrePressure;
    private XTextView mTvLeftBackTyrePressureUnit;
    private XTextView mTvLeftBackTyreTemperature;
    private XTextView mTvLeftFrontTyrePressure;
    private XTextView mTvLeftFrontTyrePressureUnit;
    private XTextView mTvLeftFrontTyreTemperature;
    private XTextView mTvRightBackTyrePressure;
    private XTextView mTvRightBackTyrePressureUnit;
    private XTextView mTvRightBackTyreTemperature;
    private XTextView mTvRightFrontTyrePressure;
    private XTextView mTvRightFrontTyrePressureUnit;
    private XTextView mTvRightFrontTyreTemperature;
    CarConditionViewModel mViewModel;

    /* loaded from: classes.dex */
    public enum TyrePosition {
        LEFT_FRONT,
        LEFT_BACK,
        RIGHT_FRONT,
        RIGHT_BACK
    }

    private boolean checkResIdInvalid(int i) {
        return i == 0;
    }

    public CarConditionCardView(Context context) {
        this(context, null);
    }

    public CarConditionCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.ROTATION_MAX_VALUE = getResources().getInteger(R.integer.CarConditionDoorRotationMaxValue);
        initContentView();
        initViewModel();
        initObserver();
    }

    public CarConditionCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.ROTATION_MAX_VALUE = getResources().getInteger(R.integer.CarConditionDoorRotationMaxValue);
    }

    private void initContentView() {
        LayoutInflater.from(getContext()).inflate(R.layout.info_card_car_condition, this);
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
        this.mTvLeftFrontTyreTemperature = (XTextView) findViewById(R.id.tv_left_front_tyre_temperature);
        this.mTvLeftBackTyreTemperature = (XTextView) findViewById(R.id.tv_left_back_tyre_temperature);
        this.mTvRightFrontTyreTemperature = (XTextView) findViewById(R.id.tv_right_front_tyre_temperature);
        this.mTvRightBackTyreTemperature = (XTextView) findViewById(R.id.tv_right_back_tyre_temperature);
        this.mIvIndicatorCoverFrontOpen = (XImageView) findViewById(R.id.iv_indicator_cover_front_open);
        this.mIvIndicatorCoverBackOpen = (XImageView) findViewById(R.id.iv_indicator_cover_back_open);
        this.mIvIndicatorCoverChargeLeftOpen = (XImageView) findViewById(R.id.iv_indicator_cover_charge_left_open);
        this.mIvIndicatorCoverChargeRightOpen = (XImageView) findViewById(R.id.iv_indicator_cover_charge_right_open);
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
        setLiveDataObserver(this.mViewModel.getLeftFrontDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$e0OPh8k6-kPyihSN8dzzRVcTpOA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateLeftFrontDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getRightFrontDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$yU20FTJ2dcuJ97WJFY75_506Fwg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateRightFrontDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftFrontDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$nNTElsxMStjefAq07oEGfH-UQS4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateLeftFrontDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightFrontDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$1-4FDJNj77jzF1W2oitLCFRQDj8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateRightFrontDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftBackDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$nkhKbXhqt9yQbvCKdbLy-ChB9eI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateLeftBackDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getRightBackDoorDrawable(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$sDEAeOc2Hc4B5lGtYBpD74a6rD4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateRightBackDoorDrawable(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftBackDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$WWMknWz2RwNz4W9qGecFxB2_BBM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateLeftBackDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightBackDoorState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$v6qWdrZfGQrIxZoy3fG01SqX7B8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateRightBackDoorState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getEngineCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$sHYN-qCoW0BqH0BRnJKbwGUdJRI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateEngineCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getTrunkCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$NhCRySELSrPtH3HOXvtXCStfz58
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateTrunkCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getFastChargeCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$r8ZaXvKWiRE5IGH2lYwdVUNMWIs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateFastChargeCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getNormalChargeCoverState(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$rwJqLspYWrUg8lElMx_NGAHGbMs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateNormalChargeCoverState((CarConditionCardView.OpenCloseState) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftFrontTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$NfJmhVSgdRcRpb1roYerBFo99ME
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateLeftFrontTyreData((CarTyreBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightFrontTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$wVn_H2xOSsQrLlfE8l1a5Zvf1WE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateRightFrontTyreData((CarTyreBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getLeftBackTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$cda6iSRHaQdAPEzw1giIeSRRLQs
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateLeftBackTyreData((CarTyreBean) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getRightBackTyreData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$CarConditionCardView$vvzowwQDI77zAdLLGRjk4SB3w-k
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CarConditionCardView.this.updateRightBackTyreData((CarTyreBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightBackTyreData(CarTyreBean carTyreBean) {
        this.mRightBackBean = carTyreBean;
        this.mIvTyreRightBack.setImageResource(carTyreBean.getTyreImageResByTyreState());
        boolean isTyrePressureNormal = carTyreBean.isTyrePressureNormal();
        this.mTvRightBackTyrePressure.setEnabled(isTyrePressureNormal);
        this.mTvRightBackTyrePressureUnit.setEnabled(isTyrePressureNormal);
        String tyrePressure = carTyreBean.getTyrePressure();
        if (!TextUtils.isEmpty(tyrePressure)) {
            this.mTvRightBackTyrePressure.setText(tyrePressure);
        }
        String tyrePressureUnit = carTyreBean.getTyrePressureUnit();
        if (!TextUtils.isEmpty(tyrePressureUnit)) {
            this.mTvRightBackTyrePressureUnit.setText(tyrePressureUnit);
        }
        boolean isTyreTemperatureNormal = carTyreBean.isTyreTemperatureNormal();
        this.mTvRightBackTyreTemperature.setText(carTyreBean.getTyreTemperatureWithUnit());
        this.mTvRightBackTyreTemperature.setVisibility(isTyreTemperatureNormal ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftBackTyreData(CarTyreBean carTyreBean) {
        this.mLeftBackBean = carTyreBean;
        this.mIvTyreLeftBack.setImageResource(carTyreBean.getTyreImageResByTyreState());
        boolean isTyrePressureNormal = carTyreBean.isTyrePressureNormal();
        this.mTvLeftBackTyrePressure.setEnabled(isTyrePressureNormal);
        this.mTvLeftBackTyrePressureUnit.setEnabled(isTyrePressureNormal);
        String tyrePressure = carTyreBean.getTyrePressure();
        if (!TextUtils.isEmpty(tyrePressure)) {
            this.mTvLeftBackTyrePressure.setText(tyrePressure);
        }
        String tyrePressureUnit = carTyreBean.getTyrePressureUnit();
        if (!TextUtils.isEmpty(tyrePressureUnit)) {
            this.mTvLeftBackTyrePressureUnit.setText(tyrePressureUnit);
        }
        boolean isTyreTemperatureNormal = carTyreBean.isTyreTemperatureNormal();
        this.mTvLeftBackTyreTemperature.setText(carTyreBean.getTyreTemperatureWithUnit());
        this.mTvLeftBackTyreTemperature.setVisibility(isTyreTemperatureNormal ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightFrontTyreData(CarTyreBean carTyreBean) {
        this.mRightFrontBean = carTyreBean;
        this.mIvTyreRightFront.setImageResource(carTyreBean.getTyreImageResByTyreState());
        boolean isTyrePressureNormal = carTyreBean.isTyrePressureNormal();
        this.mTvRightFrontTyrePressure.setEnabled(isTyrePressureNormal);
        this.mTvRightFrontTyrePressureUnit.setEnabled(isTyrePressureNormal);
        String tyrePressure = carTyreBean.getTyrePressure();
        if (!TextUtils.isEmpty(tyrePressure)) {
            this.mTvRightFrontTyrePressure.setText(tyrePressure);
        }
        String tyrePressureUnit = carTyreBean.getTyrePressureUnit();
        if (!TextUtils.isEmpty(tyrePressureUnit)) {
            this.mTvRightFrontTyrePressureUnit.setText(tyrePressureUnit);
        }
        boolean isTyreTemperatureNormal = carTyreBean.isTyreTemperatureNormal();
        this.mTvRightFrontTyreTemperature.setText(carTyreBean.getTyreTemperatureWithUnit());
        this.mTvRightFrontTyreTemperature.setVisibility(isTyreTemperatureNormal ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftFrontTyreData(CarTyreBean carTyreBean) {
        this.mLeftFrontBean = carTyreBean;
        this.mIvTyreLeftFront.setImageResource(carTyreBean.getTyreImageResByTyreState());
        boolean isTyrePressureNormal = carTyreBean.isTyrePressureNormal();
        this.mTvLeftFrontTyrePressure.setEnabled(isTyrePressureNormal);
        this.mTvLeftFrontTyrePressureUnit.setEnabled(isTyrePressureNormal);
        String tyrePressure = carTyreBean.getTyrePressure();
        if (!TextUtils.isEmpty(tyrePressure)) {
            this.mTvLeftFrontTyrePressure.setText(tyrePressure);
        }
        String tyrePressureUnit = carTyreBean.getTyrePressureUnit();
        if (!TextUtils.isEmpty(tyrePressureUnit)) {
            this.mTvLeftFrontTyrePressureUnit.setText(tyrePressureUnit);
        }
        boolean isTyreTemperatureNormal = carTyreBean.isTyreTemperatureNormal();
        this.mTvLeftFrontTyreTemperature.setText(carTyreBean.getTyreTemperatureWithUnit());
        this.mTvLeftFrontTyreTemperature.setVisibility(isTyreTemperatureNormal ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateNormalChargeCoverState(OpenCloseState openCloseState) {
        this.mIvCoverChargeRightError.setVisibility((openCloseState == OpenCloseState.ERROR || openCloseState == OpenCloseState.OPEN) ? 0 : 4);
        if (openCloseState == OpenCloseState.ERROR) {
            this.mIvIndicatorCoverChargeRightOpen.setVisibility(0);
            this.mIvIndicatorCoverChargeRightOpen.setImageResource(R.drawable.car_condition_indicator_cover_charge_right_error);
        } else if (openCloseState == OpenCloseState.OPEN) {
            this.mIvIndicatorCoverChargeRightOpen.setVisibility(0);
            this.mIvIndicatorCoverChargeRightOpen.setImageResource(R.drawable.car_condition_indicator_cover_charge_right_open);
        } else {
            this.mIvIndicatorCoverChargeRightOpen.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateFastChargeCoverState(OpenCloseState openCloseState) {
        this.mIvCoverChargeLeftError.setVisibility((openCloseState == OpenCloseState.ERROR || openCloseState == OpenCloseState.OPEN) ? 0 : 4);
        if (openCloseState == OpenCloseState.ERROR) {
            this.mIvIndicatorCoverChargeLeftOpen.setVisibility(0);
            this.mIvIndicatorCoverChargeLeftOpen.setImageResource(R.drawable.car_condition_indicator_cover_charge_left_error);
        } else if (openCloseState == OpenCloseState.OPEN) {
            this.mIvIndicatorCoverChargeLeftOpen.setVisibility(0);
            this.mIvIndicatorCoverChargeLeftOpen.setImageResource(R.drawable.car_condition_indicator_cover_charge_left_open);
        } else {
            this.mIvIndicatorCoverChargeLeftOpen.setVisibility(4);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTrunkCoverState(OpenCloseState openCloseState) {
        this.mIvCoverBackError.setVisibility(openCloseState == OpenCloseState.OPEN ? 0 : 4);
        this.mIvIndicatorCoverBackOpen.setVisibility(openCloseState != OpenCloseState.OPEN ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateEngineCoverState(OpenCloseState openCloseState) {
        this.mIvCoverFrontError.setVisibility(openCloseState == OpenCloseState.OPEN ? 0 : 4);
        this.mIvIndicatorCoverFrontOpen.setVisibility(openCloseState != OpenCloseState.OPEN ? 4 : 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftFrontDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorLeftFront.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftFrontDoorState(OpenCloseState openCloseState) {
        this.mIvDoorLeftFront.setRotation(openCloseState == OpenCloseState.OPEN ? this.ROTATION_MAX_VALUE : 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightFrontDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorRightFront.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightFrontDoorState(OpenCloseState openCloseState) {
        this.mIvDoorRightFront.setRotation(openCloseState == OpenCloseState.OPEN ? this.ROTATION_MAX_VALUE : 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftBackDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorLeftBack.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLeftBackDoorState(OpenCloseState openCloseState) {
        this.mIvDoorLeftBack.setRotation(openCloseState == OpenCloseState.OPEN ? this.ROTATION_MAX_VALUE : 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightBackDoorDrawable(int i) {
        if (checkResIdInvalid(i)) {
            return;
        }
        this.mIvDoorRightBack.setImageResource(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateRightBackDoorState(OpenCloseState openCloseState) {
        this.mIvDoorRightBack.setRotation(openCloseState == OpenCloseState.OPEN ? this.ROTATION_MAX_VALUE : 0.0f);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void changeTheme() {
        CarTyreBean carTyreBean = this.mLeftFrontBean;
        if (carTyreBean != null) {
            this.mIvTyreLeftFront.setImageResource(carTyreBean.getTyreImageResByTyreState());
        }
        CarTyreBean carTyreBean2 = this.mLeftBackBean;
        if (carTyreBean2 != null) {
            this.mIvTyreLeftBack.setImageResource(carTyreBean2.getTyreImageResByTyreState());
        }
        CarTyreBean carTyreBean3 = this.mRightFrontBean;
        if (carTyreBean3 != null) {
            this.mIvTyreRightFront.setImageResource(carTyreBean3.getTyreImageResByTyreState());
        }
        CarTyreBean carTyreBean4 = this.mRightBackBean;
        if (carTyreBean4 != null) {
            this.mIvTyreRightBack.setImageResource(carTyreBean4.getTyreImageResByTyreState());
        }
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

    /* loaded from: classes.dex */
    public enum OpenCloseState {
        CLOSE,
        OPEN,
        ERROR;

        public static OpenCloseState parseOpenCloseState(int i) {
            if (i != 0) {
                if (i == 1) {
                    return OPEN;
                }
                return ERROR;
            }
            return CLOSE;
        }
    }
}
