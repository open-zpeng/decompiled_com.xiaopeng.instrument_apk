package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.EnergyBean;
import com.xiaopeng.instrument.viewmodel.sr.SRPowerConsumptionViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SRPowerConsumptionCardView extends XConstraintLayout implements IBaseCustomView {
    private static final String TAG = "SRPowerConsumptionCardView";
    private XTextView mCurrentTextView;
    private LifecycleOwner mLifecycleOwner;
    private XTextView mMaxTextView;
    private XTextView mMinTextView;
    private SRPowerConsumptionViewModel mPcViewModel;
    private SRPowerConsumeView mPowerView;
    private XTextView mTvAvgPowerConsumption;

    public SRPowerConsumptionCardView(Context context) {
        this(context, null);
    }

    public SRPowerConsumptionCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SRPowerConsumptionCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initView();
        initViewModel();
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_power_cosumption_sr, this);
        this.mTvAvgPowerConsumption = (XTextView) inflate.findViewById(R.id.tv_power_consumption_avg);
        this.mPowerView = (SRPowerConsumeView) inflate.findViewById(R.id.power_consume_view);
        this.mMaxTextView = (XTextView) inflate.findViewById(R.id.tv_max_value);
        this.mCurrentTextView = (XTextView) inflate.findViewById(R.id.tv_current_value);
        this.mMinTextView = (XTextView) inflate.findViewById(R.id.tv_min_value);
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (getContext() instanceof ViewModelStoreOwner) {
            this.mPcViewModel = (SRPowerConsumptionViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SRPowerConsumptionViewModel.class);
        }
        SRPowerConsumptionViewModel sRPowerConsumptionViewModel = this.mPcViewModel;
        if (sRPowerConsumptionViewModel != null) {
            setLiveDataObserver(sRPowerConsumptionViewModel.getPowerAvg(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRPowerConsumptionCardView$MYlZSBp_dtIOjisjREe1xi22yAQ
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SRPowerConsumptionCardView.this.setPowerAvg(((Float) obj).floatValue());
                }
            });
            setLiveDataObserver(this.mPcViewModel.getPowerAvgInvalid(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRPowerConsumptionCardView$CKYR-r4XkiY_jNRf3Nc-I_hlEmU
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SRPowerConsumptionCardView.this.updatePowerAvgInvalid((String) obj);
                }
            });
            setLiveDataObserver(this.mPcViewModel.getResAvaPower(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRPowerConsumptionCardView$vlQT_PkS2N8dvahTEkUvafYGN6A
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SRPowerConsumptionCardView.this.updateResAva(((Integer) obj).intValue());
                }
            });
            setLiveDataObserver(this.mPcViewModel.getInstantaneousPower(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRPowerConsumptionCardView$f-Kuumj-LiBuzC80YtxmMcip5Uo
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    SRPowerConsumptionCardView.this.updateInstantaneousPower((EnergyBean) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPowerAvg(float f) {
        XTextView xTextView = this.mTvAvgPowerConsumption;
        if (xTextView != null) {
            xTextView.setText(String.valueOf(f));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updatePowerAvgInvalid(String str) {
        XTextView xTextView = this.mTvAvgPowerConsumption;
        if (xTextView != null) {
            xTextView.setText(str);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResAva(int i) {
        SRPowerConsumeView sRPowerConsumeView = this.mPowerView;
        if (sRPowerConsumeView != null) {
            sRPowerConsumeView.showRemainingValue(i);
        } else {
            XILog.d(TAG, "updateResAva mCurveView is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInstantaneousPower(EnergyBean energyBean) {
        SRPowerConsumeView sRPowerConsumeView = this.mPowerView;
        if (sRPowerConsumeView != null) {
            sRPowerConsumeView.showInstantaneousValue(energyBean);
            setText(energyBean);
            return;
        }
        XILog.d(TAG, "updateInstantaneousPower mCurveView is null");
    }

    private void setText(EnergyBean energyBean) {
        if (energyBean == null) {
            XILog.d(TAG, "setText energyBean is null");
            return;
        }
        int minValue = energyBean.getMinValue();
        int maxValue = energyBean.getMaxValue();
        int instantaneousValue = energyBean.getInstantaneousValue();
        if (maxValue == 0 || minValue == 0) {
            XILog.d(TAG, "setText: not max or min value");
            return;
        }
        this.mMaxTextView.setText(String.valueOf(maxValue));
        this.mCurrentTextView.setText(String.valueOf(instantaneousValue));
        this.mMinTextView.setText(String.valueOf(minValue));
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
