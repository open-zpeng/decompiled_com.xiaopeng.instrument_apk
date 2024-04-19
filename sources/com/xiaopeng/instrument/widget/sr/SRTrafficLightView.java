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
import com.xiaopeng.instrument.viewmodel.sr.SRTrafficLightViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XRelativeLayout;
/* loaded from: classes.dex */
public class SRTrafficLightView extends XRelativeLayout implements IBaseCustomView {
    private static final int LEFT_ANGLE = -90;
    private static final int RIGHT_ANGLE = 90;
    private static final int STRAIGHT_ANGLE = 0;
    private static final String TAG = "SRTrafficLightView";
    private int mCurrentTrafficType;
    private XImageView mLeftLight;
    private LifecycleOwner mLifecycleOwner;
    private XImageView mRightLight;
    private XImageView mStraightLight;
    private View mView;
    private SRTrafficLightViewModel mViewModel;

    public SRTrafficLightView(Context context) {
        this(context, null);
    }

    public SRTrafficLightView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SRTrafficLightView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_trafficlight_sr, this);
        this.mView = inflate;
        this.mLeftLight = (XImageView) inflate.findViewById(R.id.traffic_left_light);
        this.mStraightLight = (XImageView) this.mView.findViewById(R.id.traffic_straight_light);
        this.mRightLight = (XImageView) this.mView.findViewById(R.id.traffic_right_light);
        initViewMode();
    }

    private void initViewMode() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        if (this.mViewModel == null) {
            if (getContext() instanceof ViewModelStoreOwner) {
                this.mViewModel = (SRTrafficLightViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SRTrafficLightViewModel.class);
            } else {
                this.mViewModel = new SRTrafficLightViewModel();
            }
        }
        initObserver();
    }

    private void initObserver() {
        setLiveDataObserver(this.mViewModel.getTrafficLightData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRTrafficLightView$Mk1yimuu0HkSeOMVXIWp8qs8XlE
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRTrafficLightView.this.setCurrentTrafficType(((Integer) obj).intValue());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setCurrentTrafficType(int i) {
        this.mCurrentTrafficType = i;
        updateTrafficLight();
    }

    private void updateTrafficLight() {
        XILog.d(TAG, "updateTrafficLight: " + this.mCurrentTrafficType);
        int i = this.mCurrentTrafficType;
        if (i >= 1 && i <= 3) {
            showOnlyStraightLight();
            int i2 = this.mCurrentTrafficType;
            if (i2 == 1) {
                this.mStraightLight.setImageResource(R.drawable.sr_straight_green_light_day);
            } else if (i2 == 2) {
                this.mStraightLight.setImageResource(R.drawable.sr_straight_yellow_light_day);
            } else if (i2 == 3) {
                this.mStraightLight.setImageResource(R.drawable.sr_straight_red_light_day);
            }
            this.mStraightLight.setVisibility(0);
        } else if (i >= 4 && i <= 6) {
            showOnlyLeftLight();
            int i3 = this.mCurrentTrafficType;
            if (i3 == 4) {
                this.mLeftLight.setImageResource(R.drawable.sr_straight_green_light_day);
            } else if (i3 == 5) {
                this.mLeftLight.setImageResource(R.drawable.sr_straight_yellow_light_day);
            } else if (i3 == 6) {
                this.mLeftLight.setImageResource(R.drawable.sr_straight_red_light_day);
            }
            this.mLeftLight.setVisibility(0);
        } else if (i >= 7 && i <= 9) {
            showOnlyRightLight();
            int i4 = this.mCurrentTrafficType;
            if (i4 == 7) {
                this.mRightLight.setImageResource(R.drawable.sr_straight_green_light_day);
            } else if (i4 == 8) {
                this.mRightLight.setImageResource(R.drawable.sr_straight_yellow_light_day);
            } else if (i4 == 9) {
                this.mRightLight.setImageResource(R.drawable.sr_straight_red_light_day);
            }
            this.mRightLight.setVisibility(0);
        } else if (i < 10 || i > 12) {
            if (i == -1) {
                this.mLeftLight.setVisibility(8);
                this.mStraightLight.setVisibility(8);
                this.mRightLight.setVisibility(8);
            }
        } else {
            showOnlyLeftLight();
            this.mLeftLight.setRotation(0.0f);
            int i5 = this.mCurrentTrafficType;
            if (i5 == 10) {
                this.mLeftLight.setImageResource(R.drawable.sr_turn_green_light_day);
            } else if (i5 == 11) {
                this.mLeftLight.setImageResource(R.drawable.sr_turn_yellow_light_day);
            } else if (i5 == 12) {
                this.mLeftLight.setImageResource(R.drawable.sr_turn_red_light_day);
            }
            this.mLeftLight.setVisibility(0);
        }
    }

    private void showOnlyLeftLight() {
        this.mStraightLight.setVisibility(8);
        this.mRightLight.setVisibility(8);
        this.mLeftLight.setRotation(-90.0f);
    }

    private void showOnlyStraightLight() {
        this.mLeftLight.setVisibility(8);
        this.mRightLight.setVisibility(8);
    }

    private void showOnlyRightLight() {
        this.mLeftLight.setVisibility(8);
        this.mStraightLight.setVisibility(8);
        this.mRightLight.setRotation(90.0f);
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
