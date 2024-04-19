package com.xiaopeng.instrument.viewmodel;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.xiaopeng.cluster.ClusterManager;
import com.xiaopeng.cluster.listener.ICarConditionListener;
import com.xiaopeng.cluster.listener.ICarInfoTireListener;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.CarTyreBean;
import com.xiaopeng.instrument.widget.CarConditionCardView;
/* loaded from: classes.dex */
public class CarConditionViewModel extends ViewModel {
    private ICarConditionListener mICarConditionListener;
    private final LiveData<Integer> mLeftBackDoorDrawable;
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mLeftBackDoorState;
    private final LiveData<Integer> mLeftFrontDoorDrawable;
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mLeftFrontDoorState;
    private final LiveData<Integer> mRightBackDoorDrawable;
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mRightBackDoorState;
    private final LiveData<Integer> mRightFrontDoorDrawable;
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mRightFrontDoorState;
    CarTyreBean mLeftFrontTyre = new CarTyreBean(CarConditionCardView.TyrePosition.LEFT_FRONT);
    CarTyreBean mRightFrontTyre = new CarTyreBean(CarConditionCardView.TyrePosition.RIGHT_FRONT);
    CarTyreBean mLeftBackTyre = new CarTyreBean(CarConditionCardView.TyrePosition.LEFT_BACK);
    CarTyreBean mRightBackTyre = new CarTyreBean(CarConditionCardView.TyrePosition.RIGHT_BACK);
    private final MutableLiveData<CarTyreBean> mLeftFrontTyreData = new MutableLiveData<>(this.mLeftFrontTyre);
    private final MutableLiveData<CarTyreBean> mRightFrontTyreData = new MutableLiveData<>(this.mRightFrontTyre);
    private final MutableLiveData<CarTyreBean> mLeftBackTyreData = new MutableLiveData<>(this.mLeftBackTyre);
    private final MutableLiveData<CarTyreBean> mRightBackTyreData = new MutableLiveData<>(this.mRightBackTyre);
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mEngineCoverState = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mTrunkCoverState = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mFastChargeCoverState = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
    private final MutableLiveData<CarConditionCardView.OpenCloseState> mNormalChargeCoverState = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);

    public CarConditionViewModel() {
        MutableLiveData<CarConditionCardView.OpenCloseState> mutableLiveData = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
        this.mLeftFrontDoorState = mutableLiveData;
        MutableLiveData<CarConditionCardView.OpenCloseState> mutableLiveData2 = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
        this.mLeftBackDoorState = mutableLiveData2;
        MutableLiveData<CarConditionCardView.OpenCloseState> mutableLiveData3 = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
        this.mRightFrontDoorState = mutableLiveData3;
        MutableLiveData<CarConditionCardView.OpenCloseState> mutableLiveData4 = new MutableLiveData<>(CarConditionCardView.OpenCloseState.CLOSE);
        this.mRightBackDoorState = mutableLiveData4;
        this.mLeftFrontDoorDrawable = Transformations.map(mutableLiveData, new Function() { // from class: com.xiaopeng.instrument.viewmodel.-$$Lambda$CarConditionViewModel$O7kjqOkjhDvuAkCHM9Hrt9fzupk
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                CarConditionCardView.OpenCloseState openCloseState = (CarConditionCardView.OpenCloseState) obj;
                valueOf = Integer.valueOf(r1 == CarConditionCardView.OpenCloseState.CLOSE ? R.drawable.car_condition_door_left_front : R.drawable.car_condition_door_left_front_error);
                return valueOf;
            }
        });
        this.mLeftBackDoorDrawable = Transformations.map(mutableLiveData2, new Function() { // from class: com.xiaopeng.instrument.viewmodel.-$$Lambda$CarConditionViewModel$qtQGJkYzbSFXX2b4sDdSC1FFiWQ
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                CarConditionCardView.OpenCloseState openCloseState = (CarConditionCardView.OpenCloseState) obj;
                valueOf = Integer.valueOf(r1 == CarConditionCardView.OpenCloseState.CLOSE ? R.drawable.car_condition_door_left_back : R.drawable.car_condition_door_left_back_error);
                return valueOf;
            }
        });
        this.mRightFrontDoorDrawable = Transformations.map(mutableLiveData3, new Function() { // from class: com.xiaopeng.instrument.viewmodel.-$$Lambda$CarConditionViewModel$lE7wuJjxPklqKEaPpAtLDDcb9IQ
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                CarConditionCardView.OpenCloseState openCloseState = (CarConditionCardView.OpenCloseState) obj;
                valueOf = Integer.valueOf(r1 == CarConditionCardView.OpenCloseState.CLOSE ? R.drawable.car_condition_door_left_front : R.drawable.car_condition_door_left_front_error);
                return valueOf;
            }
        });
        this.mRightBackDoorDrawable = Transformations.map(mutableLiveData4, new Function() { // from class: com.xiaopeng.instrument.viewmodel.-$$Lambda$CarConditionViewModel$8tHn4qQPrE2ohstI3571s0mxK-o
            @Override // androidx.arch.core.util.Function
            public final Object apply(Object obj) {
                Integer valueOf;
                CarConditionCardView.OpenCloseState openCloseState = (CarConditionCardView.OpenCloseState) obj;
                valueOf = Integer.valueOf(r1 == CarConditionCardView.OpenCloseState.CLOSE ? R.drawable.car_condition_door_left_back : R.drawable.car_condition_door_left_back_error);
                return valueOf;
            }
        });
        initSignalListener();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.lifecycle.ViewModel
    public void onCleared() {
        super.onCleared();
        ClusterManager.getInstance().setCarTireListener(null);
        ClusterManager.getInstance().removeCarConditionListener(this.mICarConditionListener);
    }

    private void initSignalListener() {
        this.mICarConditionListener = new ICarConditionListener() { // from class: com.xiaopeng.instrument.viewmodel.CarConditionViewModel.1
            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onDoorBL(boolean z) {
                CarConditionViewModel.this.mLeftBackDoorState.postValue(z ? CarConditionCardView.OpenCloseState.OPEN : CarConditionCardView.OpenCloseState.CLOSE);
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onDoorBR(boolean z) {
                CarConditionViewModel.this.mRightBackDoorState.postValue(z ? CarConditionCardView.OpenCloseState.OPEN : CarConditionCardView.OpenCloseState.CLOSE);
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onDoorFL(boolean z) {
                CarConditionViewModel.this.mLeftFrontDoorState.postValue(z ? CarConditionCardView.OpenCloseState.OPEN : CarConditionCardView.OpenCloseState.CLOSE);
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onDoorFR(boolean z) {
                CarConditionViewModel.this.mRightFrontDoorState.postValue(z ? CarConditionCardView.OpenCloseState.OPEN : CarConditionCardView.OpenCloseState.CLOSE);
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onHoodEngine(boolean z) {
                CarConditionViewModel.this.mEngineCoverState.postValue(z ? CarConditionCardView.OpenCloseState.OPEN : CarConditionCardView.OpenCloseState.CLOSE);
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onHoodFastCharge(int i) {
                CarConditionViewModel.this.mFastChargeCoverState.postValue(CarConditionCardView.OpenCloseState.parseOpenCloseState(i));
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onHoodNormalCharge(int i) {
                CarConditionViewModel.this.mNormalChargeCoverState.postValue(CarConditionCardView.OpenCloseState.parseOpenCloseState(i));
            }

            @Override // com.xiaopeng.cluster.listener.ICarConditionListener
            public void onHoodTrunk(boolean z) {
                CarConditionViewModel.this.mTrunkCoverState.postValue(z ? CarConditionCardView.OpenCloseState.OPEN : CarConditionCardView.OpenCloseState.CLOSE);
            }
        };
        ClusterManager.getInstance().addCarConditionListener(this.mICarConditionListener);
        ClusterManager.getInstance().setCarTireListener(new ICarInfoTireListener() { // from class: com.xiaopeng.instrument.viewmodel.CarConditionViewModel.2
            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLTireState(int i) {
                CarConditionViewModel.this.setTyreState(CarConditionCardView.TyrePosition.LEFT_FRONT, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLPressureState(int i) {
                CarConditionViewModel.this.setTyrePressureState(CarConditionCardView.TyrePosition.LEFT_FRONT, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLPressure(String str) {
                CarConditionViewModel.this.setTyrePressure(CarConditionCardView.TyrePosition.LEFT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLPressureUnits(String str) {
                CarConditionViewModel.this.setTyrePressureUnit(CarConditionCardView.TyrePosition.LEFT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLTemperaturesState(int i) {
                CarConditionViewModel.this.setTyreTemperatureState(CarConditionCardView.TyrePosition.LEFT_FRONT, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLTemperatures(String str) {
                CarConditionViewModel.this.setTyreTemperature(CarConditionCardView.TyrePosition.LEFT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFLTemperaturesUnits(String str) {
                CarConditionViewModel.this.setTyreTemperatureUnit(CarConditionCardView.TyrePosition.LEFT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRTireState(int i) {
                CarConditionViewModel.this.setTyreState(CarConditionCardView.TyrePosition.RIGHT_FRONT, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRPressureState(int i) {
                CarConditionViewModel.this.setTyrePressureState(CarConditionCardView.TyrePosition.RIGHT_FRONT, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRPressure(String str) {
                CarConditionViewModel.this.setTyrePressure(CarConditionCardView.TyrePosition.RIGHT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRPressureUnits(String str) {
                CarConditionViewModel.this.setTyrePressureUnit(CarConditionCardView.TyrePosition.RIGHT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRTemperaturesState(int i) {
                CarConditionViewModel.this.setTyreTemperatureState(CarConditionCardView.TyrePosition.RIGHT_FRONT, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRTemperatures(String str) {
                CarConditionViewModel.this.setTyreTemperature(CarConditionCardView.TyrePosition.RIGHT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireFRTemperaturesUnits(String str) {
                CarConditionViewModel.this.setTyreTemperatureUnit(CarConditionCardView.TyrePosition.RIGHT_FRONT, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLTireState(int i) {
                CarConditionViewModel.this.setTyreState(CarConditionCardView.TyrePosition.LEFT_BACK, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLPressureState(int i) {
                CarConditionViewModel.this.setTyrePressureState(CarConditionCardView.TyrePosition.LEFT_BACK, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLPressure(String str) {
                CarConditionViewModel.this.setTyrePressure(CarConditionCardView.TyrePosition.LEFT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLPressureUnits(String str) {
                CarConditionViewModel.this.setTyrePressureUnit(CarConditionCardView.TyrePosition.LEFT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLTemperaturesState(int i) {
                CarConditionViewModel.this.setTyreTemperatureState(CarConditionCardView.TyrePosition.LEFT_BACK, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLTemperatures(String str) {
                CarConditionViewModel.this.setTyreTemperature(CarConditionCardView.TyrePosition.LEFT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBLTemperaturesUnits(String str) {
                CarConditionViewModel.this.setTyreTemperatureUnit(CarConditionCardView.TyrePosition.LEFT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRTireState(int i) {
                CarConditionViewModel.this.setTyreState(CarConditionCardView.TyrePosition.RIGHT_BACK, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRPressureState(int i) {
                CarConditionViewModel.this.setTyrePressureState(CarConditionCardView.TyrePosition.RIGHT_BACK, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRPressure(String str) {
                CarConditionViewModel.this.setTyrePressure(CarConditionCardView.TyrePosition.RIGHT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRPressureUnits(String str) {
                CarConditionViewModel.this.setTyrePressureUnit(CarConditionCardView.TyrePosition.RIGHT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRTemperaturesState(int i) {
                CarConditionViewModel.this.setTyreTemperatureState(CarConditionCardView.TyrePosition.RIGHT_BACK, i);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRTemperatures(String str) {
                CarConditionViewModel.this.setTyreTemperature(CarConditionCardView.TyrePosition.RIGHT_BACK, str);
            }

            @Override // com.xiaopeng.cluster.listener.ICarInfoTireListener
            public void onTireBRTemperaturesUnits(String str) {
                CarConditionViewModel.this.setTyreTemperatureUnit(CarConditionCardView.TyrePosition.RIGHT_BACK, str);
            }
        });
    }

    public void setTyreState(CarConditionCardView.TyrePosition tyrePosition, int i) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyreState(i);
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public void setTyrePressureState(CarConditionCardView.TyrePosition tyrePosition, int i) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyrePressureState(i);
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public void setTyrePressure(CarConditionCardView.TyrePosition tyrePosition, String str) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyrePressure(str);
        findTyreByPosition.setTyrePressureUnit("bar");
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public void setTyrePressureUnit(CarConditionCardView.TyrePosition tyrePosition, String str) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyrePressureUnit(str);
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public void setTyreTemperatureState(CarConditionCardView.TyrePosition tyrePosition, int i) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyreTemperatureState(i);
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public void setTyreTemperature(CarConditionCardView.TyrePosition tyrePosition, String str) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyreTemperature(str);
        findTyreByPosition.setTyreTemperatureUnit("℃");
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public void setTyreTemperatureUnit(CarConditionCardView.TyrePosition tyrePosition, String str) {
        CarTyreBean findTyreByPosition = findTyreByPosition(tyrePosition);
        findTyreByPosition.setTyreTemperatureUnit(str);
        findTyreLiveDataByPosition(tyrePosition).postValue(findTyreByPosition);
    }

    public MutableLiveData<CarTyreBean> getLeftFrontTyreData() {
        return this.mLeftFrontTyreData;
    }

    public MutableLiveData<CarTyreBean> getRightFrontTyreData() {
        return this.mRightFrontTyreData;
    }

    public MutableLiveData<CarTyreBean> getLeftBackTyreData() {
        return this.mLeftBackTyreData;
    }

    public MutableLiveData<CarTyreBean> getRightBackTyreData() {
        return this.mRightBackTyreData;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getEngineCoverState() {
        return this.mEngineCoverState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getTrunkCoverState() {
        return this.mTrunkCoverState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getFastChargeCoverState() {
        return this.mFastChargeCoverState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getNormalChargeCoverState() {
        return this.mNormalChargeCoverState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getLeftFrontDoorState() {
        return this.mLeftFrontDoorState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getLeftBackDoorState() {
        return this.mLeftBackDoorState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getRightFrontDoorState() {
        return this.mRightFrontDoorState;
    }

    public MutableLiveData<CarConditionCardView.OpenCloseState> getRightBackDoorState() {
        return this.mRightBackDoorState;
    }

    public LiveData<Integer> getLeftFrontDoorDrawable() {
        return this.mLeftFrontDoorDrawable;
    }

    public LiveData<Integer> getLeftBackDoorDrawable() {
        return this.mLeftBackDoorDrawable;
    }

    public LiveData<Integer> getRightFrontDoorDrawable() {
        return this.mRightFrontDoorDrawable;
    }

    public LiveData<Integer> getRightBackDoorDrawable() {
        return this.mRightBackDoorDrawable;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.xiaopeng.instrument.viewmodel.CarConditionViewModel$3  reason: invalid class name */
    /* loaded from: classes.dex */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition;

        static {
            int[] iArr = new int[CarConditionCardView.TyrePosition.values().length];
            $SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition = iArr;
            try {
                iArr[CarConditionCardView.TyrePosition.LEFT_FRONT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition[CarConditionCardView.TyrePosition.RIGHT_FRONT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition[CarConditionCardView.TyrePosition.LEFT_BACK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition[CarConditionCardView.TyrePosition.RIGHT_BACK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private CarTyreBean findTyreByPosition(CarConditionCardView.TyrePosition tyrePosition) {
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition[tyrePosition.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return this.mRightBackTyre;
                    }
                    throw new IllegalArgumentException(tyrePosition + " this tyre position is not supported!");
                }
                return this.mLeftBackTyre;
            }
            return this.mRightFrontTyre;
        }
        return this.mLeftFrontTyre;
    }

    private MutableLiveData<CarTyreBean> findTyreLiveDataByPosition(CarConditionCardView.TyrePosition tyrePosition) {
        int i = AnonymousClass3.$SwitchMap$com$xiaopeng$instrument$widget$CarConditionCardView$TyrePosition[tyrePosition.ordinal()];
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i == 4) {
                        return this.mRightBackTyreData;
                    }
                    throw new IllegalArgumentException(tyrePosition + " this tyre position is not supported!");
                }
                return this.mLeftBackTyreData;
            }
            return this.mRightFrontTyreData;
        }
        return this.mLeftFrontTyreData;
    }
}
