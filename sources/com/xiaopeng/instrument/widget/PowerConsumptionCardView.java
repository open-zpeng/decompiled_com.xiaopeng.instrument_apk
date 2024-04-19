package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.TypedArray;
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
import com.xiaopeng.instrument.viewmodel.PowerConsumptionViewModel;
import com.xiaopeng.xui.widget.XConstraintLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class PowerConsumptionCardView extends XConstraintLayout implements IBaseCustomView {
    private static final String TAG = "PowerConsumptionCardView";
    private boolean isLeft;
    private PowerCurveView mCurveView;
    private LifecycleOwner mLifecycleOwner;
    private PowerConsumptionLineView mLineView;
    private PowerConsumptionViewModel mPcViewModel;
    private XTextView mTvAvgPowerConsumption;

    public PowerConsumptionCardView(Context context) {
        this(context, null);
    }

    public PowerConsumptionCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PowerConsumptionCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.PowerConsumptionCardView);
            this.isLeft = obtainStyledAttributes.getBoolean(0, this.isLeft);
            obtainStyledAttributes.recycle();
        }
        initView(context);
        initViewModel();
    }

    private void initView(Context context) {
        View inflate;
        if (this.isLeft) {
            inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_power_cosumption_left, this);
        } else {
            inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_power_cosumption_right, this);
        }
        this.mCurveView = (PowerCurveView) inflate.findViewById(R.id.pc_curve_view);
        this.mLineView = (PowerConsumptionLineView) inflate.findViewById(R.id.pcl_line_view);
        this.mTvAvgPowerConsumption = (XTextView) inflate.findViewById(R.id.tv_power_consumption_avg);
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (getContext() instanceof ViewModelStoreOwner) {
            PowerConsumptionViewModel powerConsumptionViewModel = (PowerConsumptionViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(PowerConsumptionViewModel.class);
            this.mPcViewModel = powerConsumptionViewModel;
            if (powerConsumptionViewModel != null) {
                setLiveDataObserver(powerConsumptionViewModel.getPowerAvg(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$PowerConsumptionCardView$scbWWwg_bC4Zmr8Jj09_BbCFN2w
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        PowerConsumptionCardView.this.addPowerAvg(((Float) obj).floatValue());
                    }
                });
                setLiveDataObserver(this.mPcViewModel.getPowerAvgInvalid(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$PowerConsumptionCardView$1NiFuoasRF4BdN-GBdImZS3jd_s
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        PowerConsumptionCardView.this.updatePowerAvgInvalid((String) obj);
                    }
                });
                setLiveDataObserver(this.mPcViewModel.getPowerCurve(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$PowerConsumptionCardView$fNy_x4LwvwIx8nUTQRX-ts44En0
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        PowerConsumptionCardView.this.setPowerCurve((Float) obj);
                    }
                });
                setLiveDataObserver(this.mPcViewModel.getResAvaPower(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$PowerConsumptionCardView$CYWS3CcQRFV1jyOOjA_9OlITjW0
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        PowerConsumptionCardView.this.updateResAva(((Integer) obj).intValue());
                    }
                });
                setLiveDataObserver(this.mPcViewModel.getInstantaneousPower(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$PowerConsumptionCardView$2SxCnoANQURugU_NJ4hQbIHFz3U
                    @Override // androidx.lifecycle.Observer
                    public final void onChanged(Object obj) {
                        PowerConsumptionCardView.this.updateInstantaneousPower((EnergyBean) obj);
                    }
                });
                return;
            }
            XILog.e(TAG, "mPcViewModel = null,数据无法更新");
            return;
        }
        XILog.e(TAG, "ViewModel数据初始化失败,数据无法更新");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateResAva(int i) {
        PowerCurveView powerCurveView = this.mCurveView;
        if (powerCurveView != null) {
            powerCurveView.showRemainingValue(i);
        } else {
            XILog.d(TAG, "updateResAva mCurveView is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInstantaneousPower(EnergyBean energyBean) {
        PowerCurveView powerCurveView = this.mCurveView;
        if (powerCurveView != null) {
            powerCurveView.showInstantaneousValue(energyBean);
        } else {
            XILog.e(TAG, "updateInstantaneousPower mCurveView is null");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setPowerCurve(Float f) {
        PowerConsumptionLineView powerConsumptionLineView = this.mLineView;
        if (powerConsumptionLineView != null) {
            powerConsumptionLineView.addData(f.floatValue());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addPowerAvg(float f) {
        PowerConsumptionLineView powerConsumptionLineView = this.mLineView;
        if (powerConsumptionLineView != null) {
            powerConsumptionLineView.setPowerAvg(f);
        }
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
