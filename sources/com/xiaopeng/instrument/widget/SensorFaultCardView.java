package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.CarBodyContain;
import com.xiaopeng.instrument.bean.SensorBean;
import com.xiaopeng.instrument.bean.SensorContainBean;
import com.xiaopeng.instrument.bean.ViewPosBean;
import com.xiaopeng.instrument.viewmodel.SensorFaultViewModel;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.HashMap;
import java.util.Map;
/* loaded from: classes.dex */
public class SensorFaultCardView extends XRelativeLayout implements IBaseCustomView {
    private static final int SPACE = App.getInstance().getResources().getInteger(R.integer.SensorFaultLightDrawableOffset);
    public static final String TAG = "SensorFaultCardView";
    private XImageView mBgBody;
    private XImageView mBgRadar;
    private Context mContext;
    private LifecycleOwner mLifecycleOwner;
    private Map<Integer, XImageView> mSensorImageViewMap;
    private XRelativeLayout mSensorRootLayout;
    private XTextView mToastView;
    private SensorFaultViewModel mViewModel;

    public SensorFaultCardView(Context context) {
        this(context, null);
    }

    public SensorFaultCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSensorImageViewMap = new HashMap();
        init(context);
    }

    public SensorFaultCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mSensorImageViewMap = new HashMap();
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        SensorFaultViewModel sensorFaultViewModel = this.mViewModel;
        if (sensorFaultViewModel != null) {
            sensorFaultViewModel.resumeData();
        }
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(R.layout.info_card_sensor_fault, this);
        this.mBgBody = (XImageView) findViewById(R.id.sensor_car_bg_view);
        this.mBgRadar = (XImageView) findViewById(R.id.sensor_radar_bg_view);
        this.mSensorRootLayout = (XRelativeLayout) findViewById(R.id.sensor_fault_root_view);
        this.mToastView = (XTextView) findViewById(R.id.sensor_toast);
        initViewModel();
    }

    private void initViewModel() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        SensorFaultViewModel sensorFaultViewModel = (SensorFaultViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SensorFaultViewModel.class);
        this.mViewModel = sensorFaultViewModel;
        setLiveDataObserver(sensorFaultViewModel.getTextLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$SensorFaultCardView$TIWiQHC8yktXgtrXrymXSxmXr9s
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SensorFaultCardView.this.showText((String) obj);
            }
        });
        setLiveDataObserver(this.mViewModel.getSensorContainBeanLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$SensorFaultCardView$ZMvUKpJzEXBhUtUp9uRMQTy8wrI
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SensorFaultCardView.this.showSensor((SensorContainBean) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        this.mSensorImageViewMap.clear();
        super.onDetachedFromWindow();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSensor(SensorContainBean sensorContainBean) {
        SensorBean sensorBean = sensorContainBean.getSensorBean();
        if (sensorBean == null) {
            XILog.d(TAG, "sensorContainBean is null");
            return;
        }
        int status = sensorContainBean.getStatus();
        CarBodyContain carBodyContain = sensorContainBean.getCarBodyContain();
        if (carBodyContain == null) {
            XILog.d(TAG, "carBodyContain is null");
            return;
        }
        int carBodyType = carBodyContain.getCarBodyType();
        sensorBean.getSensorId();
        boolean isShowSensorPoint = sensorContainBean.isShowSensorPoint();
        updateCarBodyBg(carBodyContain);
        showSensorView(sensorBean, carBodyType, status, isShowSensorPoint);
    }

    private void showSensorView(SensorBean sensorBean, int i, int i2, boolean z) {
        ViewPosBean bodyFront;
        XImageView xImageView = this.mSensorImageViewMap.get(Integer.valueOf(sensorBean.getSensorId()));
        if (xImageView == null) {
            xImageView = new XImageView(this.mContext);
            this.mSensorRootLayout.addView(xImageView);
            this.mSensorImageViewMap.put(Integer.valueOf(sensorBean.getSensorId()), xImageView);
        }
        if (!z) {
            xImageView.setImageResource(0);
            xImageView.setVisibility(8);
            return;
        }
        if (i == 1) {
            bodyFront = sensorBean.getBodyFront();
        } else if (i == 2) {
            bodyFront = sensorBean.getBodyRear();
        } else {
            bodyFront = sensorBean.getBodyFullPos();
        }
        if (bodyFront != null) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
            layoutParams.addRule(4);
            if (i2 != 0) {
                int left = bodyFront.getLeft();
                int i3 = SPACE;
                layoutParams.setMargins(left - i3, bodyFront.getTop() - i3, bodyFront.getRight(), bodyFront.getBottom());
            } else {
                layoutParams.setMargins(bodyFront.getLeft(), bodyFront.getTop(), bodyFront.getRight(), bodyFront.getBottom());
            }
            xImageView.setLayoutParams(layoutParams);
        } else {
            XILog.d(TAG, "viewPosBean is null");
        }
        if (i2 == 0) {
            xImageView.setImageResource(0);
            xImageView.setVisibility(8);
        } else if (i2 == 2) {
            xImageView.setImageResource(R.drawable.ic_sensor_error);
            xImageView.setVisibility(0);
        } else if (i2 == 1) {
            xImageView.setImageResource(R.drawable.ic_sensor_warning);
            xImageView.setVisibility(0);
        } else {
            xImageView.setImageResource(0);
            xImageView.setVisibility(8);
        }
    }

    private void updateCarBodyBg(CarBodyContain carBodyContain) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams2.addRule(14);
        int carBodyType = carBodyContain.getCarBodyType();
        if (carBodyType == 1) {
            this.mBgBody.setImageResource(R.drawable.bg_card_car_front);
            this.mBgRadar.setImageResource(R.drawable.bg_card_radar_model_front);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_body_front_top), 0, 0);
            layoutParams2.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_radar_front_top), 0, 0);
        } else if (carBodyType == 2) {
            this.mBgBody.setImageResource(R.drawable.bg_card_car_rear);
            this.mBgRadar.setImageResource(R.drawable.bg_card_radar_model_rear);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_body_rear_top), 0, 0);
            layoutParams2.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_radar_rear_top), 0, 0);
        } else if (carBodyType == 3) {
            this.mBgBody.setImageResource(R.drawable.bg_card_car_full);
            this.mBgRadar.setImageResource(R.drawable.bg_card_radar_model_full);
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_body_full_top), 0, 0);
            layoutParams2.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_radar_full_top), 0, 0);
        } else {
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_body_full_top), 0, 0);
            layoutParams2.setMargins(0, (int) getResources().getDimension(R.dimen.sensor_card_radar_full_top), 0, 0);
        }
        this.mBgBody.setLayoutParams(layoutParams);
        this.mBgRadar.setLayoutParams(layoutParams2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showText(String str) {
        XTextView xTextView = this.mToastView;
        if (xTextView == null) {
            XILog.d(TAG, "mToastView is null");
            return;
        }
        xTextView.setText(str);
        if (!TextUtils.isEmpty(str)) {
            this.mToastView.setVisibility(0);
        } else {
            this.mToastView.setVisibility(8);
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
