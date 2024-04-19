package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.viewmodel.OdoMeterViewModel;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class OdoMeterCardView extends XFrameLayout implements IBaseCustomView {
    private static final String TAG = "OdoMeterCardView";
    private LifecycleOwner mLifecycleOwner;
    private XTextView mTvDataDistance;
    private XTextView mTvDataEnergyCost;
    private XTextView mTvDataOdometerFromLastCharge;
    private XTextView mTvDataTime;
    private XTextView mTvDataTotalOdometer;
    private XTextView mTvUnitTime;
    OdoMeterViewModel mViewModel;

    public OdoMeterCardView(Context context) {
        this(context, null);
    }

    public OdoMeterCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
        initViewModel();
        initObserver();
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (this.mViewModel == null) {
            if (getContext() instanceof ViewModelStoreOwner) {
                this.mViewModel = (OdoMeterViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(OdoMeterViewModel.class);
            } else {
                this.mViewModel = new OdoMeterViewModel();
            }
        }
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getDrivingOdometerFromStartup(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$OdoMeterCardView$rSWfegecNZDGj3jMzQtuzKU59Mg
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OdoMeterCardView.this.updateOdometerFromStartup((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getDrivingTimeFromStartup(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$OdoMeterCardView$nmuEd29Efo5jvNNHUUrzLE7uAEA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OdoMeterCardView.this.updateTimeFromStartup((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getAverageEnergyCost(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$OdoMeterCardView$HL1hsHpkthJEflZLZAqv9ISzezE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OdoMeterCardView.this.updateAverageEnergyCost((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getDrivingOdometerFromLastCharge(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$OdoMeterCardView$gaYWuPx98rUuaL1HVcg05ETuu20
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OdoMeterCardView.this.updateOdometerFromLastCharge((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getTotalDrivingOdometer(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$OdoMeterCardView$cZv_V8gaPE1vmMLbgkkMeRt9zw0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OdoMeterCardView.this.updateDrivingOdometer((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getDrivingTimeUnitFromStartup(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$OdoMeterCardView$dU8UWxRYSBmkEe-L_hzm63B1mAA
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                OdoMeterCardView.this.updateTimeUnitFromStartup((String) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XFrameLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + ThemeManager.isThemeChanged(configuration));
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.info_card_odometer, this);
        this.mTvDataDistance = (XTextView) findViewById(R.id.tv_data_distance);
        this.mTvDataTime = (XTextView) findViewById(R.id.tv_data_time);
        this.mTvDataEnergyCost = (XTextView) findViewById(R.id.tv_data_energy_cost);
        this.mTvDataOdometerFromLastCharge = (XTextView) findViewById(R.id.tv_data_odometer_from_last_charge);
        this.mTvDataTotalOdometer = (XTextView) findViewById(R.id.tv_data_total_odometer);
        this.mTvUnitTime = (XTextView) findViewById(R.id.tv_unit_time);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTimeUnitFromStartup(String str) {
        this.mTvUnitTime.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDrivingOdometer(String str) {
        this.mTvDataTotalOdometer.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOdometerFromLastCharge(String str) {
        this.mTvDataOdometerFromLastCharge.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateAverageEnergyCost(String str) {
        this.mTvDataEnergyCost.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTimeFromStartup(String str) {
        this.mTvDataTime.setText(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateOdometerFromStartup(String str) {
        this.mTvDataDistance.setText(str);
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
