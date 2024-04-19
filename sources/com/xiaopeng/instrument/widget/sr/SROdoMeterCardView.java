package com.xiaopeng.instrument.widget.sr;

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
import com.xiaopeng.instrument.viewmodel.sr.SROdoMeterViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.instrument.widget.OdoMeterCardView;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XFrameLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SROdoMeterCardView extends XFrameLayout implements IBaseCustomView {
    private static final String TAG = OdoMeterCardView.class.getSimpleName();
    private LifecycleOwner mLifecycleOwner;
    private XTextView mTvDataDistance;
    private XTextView mTvDataEnergyCost;
    private XTextView mTvDataOdometerFromLastCharge;
    SROdoMeterViewModel mViewModel;

    public SROdoMeterCardView(Context context) {
        this(context, null);
    }

    public SROdoMeterCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
        initViewModel();
        initObserver();
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (this.mViewModel == null) {
            if (getContext() instanceof ViewModelStoreOwner) {
                this.mViewModel = (SROdoMeterViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SROdoMeterViewModel.class);
            } else {
                this.mViewModel = new SROdoMeterViewModel();
            }
        }
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getDrivingOdometerFromStartup(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SROdoMeterCardView$E-M8e65Y5_5d1XdJECE-xG4IxV0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SROdoMeterCardView.this.updateOdometerFromStartup((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getAverageEnergyCost(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SROdoMeterCardView$gR-7FzBokwypuR98IH9HmerJDC8
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SROdoMeterCardView.this.updateAverageEnergyCost((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getDrivingOdometerFromLastCharge(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SROdoMeterCardView$il8QPvsraDB1FfKz-qiNqQY-f_4
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SROdoMeterCardView.this.updateOdometerFromLastCharge((String) obj);
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
        LayoutInflater.from(getContext()).inflate(R.layout.info_card_odometer_sr, this);
        this.mTvDataDistance = (XTextView) findViewById(R.id.tv_data_distance);
        this.mTvDataEnergyCost = (XTextView) findViewById(R.id.tv_data_energy_cost);
        this.mTvDataOdometerFromLastCharge = (XTextView) findViewById(R.id.tv_data_odometer_from_last_charge);
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
