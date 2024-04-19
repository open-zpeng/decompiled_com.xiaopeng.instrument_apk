package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.config.BaseConfig;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.WaringBean;
import com.xiaopeng.instrument.bean.WarningType;
import com.xiaopeng.instrument.utils.StringUtil;
import com.xiaopeng.instrument.viewmodel.WarningViewModel;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class ToastView extends XLinearLayout implements IBaseCustomView {
    private static final String TAG = "ToastView";
    private static final String TYPE_AUTO_DRIVE = "AUTO_DRIVE_TOAST";
    private static final String TYPE_GENERIC = "GENERIC_TOAST";
    private Context mContext;
    private XTextView mDangerTextView;
    private XImageView mIconView;
    private LifecycleOwner mLifecycleOwner;
    private XTextView mTextView;
    private XLinearLayout mToastBgView;
    private XRelativeLayout mToastDangerView;
    private XRelativeLayout mToastRootLayout;
    private XImageView mToastShadow;
    private String mType;
    private WaringBean mWaringBean;
    private WarningViewModel mWarningViewModel;

    public ToastView(Context context) {
        this(context, null);
    }

    public ToastView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ToastView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mType = getToastType();
        init(context);
    }

    private String getToastType() {
        Object tag = getTag();
        if (tag == null || !(tag instanceof String)) {
            return null;
        }
        return (String) tag;
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_toast, this);
        this.mToastRootLayout = (XRelativeLayout) findViewById(R.id.waring_root_view);
        this.mToastBgView = (XLinearLayout) findViewById(R.id.waring_bg_view);
        this.mToastDangerView = (XRelativeLayout) findViewById(R.id.waring_danger_layout);
        this.mToastShadow = (XImageView) findViewById(R.id.toast_shadow);
        this.mIconView = (XImageView) findViewById(R.id.warning_icon);
        this.mTextView = (XTextView) findViewById(R.id.warning_text);
        this.mDangerTextView = (XTextView) findViewById(R.id.warning_danger_text);
        this.mWarningViewModel = (WarningViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(WarningViewModel.class);
        Context context2 = this.mContext;
        this.mLifecycleOwner = context2 instanceof LifecycleOwner ? (LifecycleOwner) context2 : null;
        if (TYPE_AUTO_DRIVE.equals(this.mType)) {
            setLiveDataObserver(this.mWarningViewModel.getAutoDriveWaringBeanMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$ToastView$uCSihGsJmKTd_1CRsKTIhMjKR8I
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ToastView.this.showToastText((WaringBean) obj);
                }
            });
            setLiveDataObserver(this.mWarningViewModel.getSRBottomTipsWarningMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$ToastView$jUdHvEpTvVSmZcILLznvHnLmBiU
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    ToastView.this.showSRDangerView((WaringBean) obj);
                }
            });
            return;
        }
        setLiveDataObserver(this.mWarningViewModel.getGenericWaringBeanMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.-$$Lambda$ToastView$uCSihGsJmKTd_1CRsKTIhMjKR8I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                ToastView.this.showToastText((WaringBean) obj);
            }
        });
    }

    private void hideToast() {
        this.mTextView.setText("");
        this.mDangerTextView.setText("");
        this.mIconView.setImageResource(0);
        this.mToastRootLayout.setVisibility(8);
        this.mToastDangerView.setVisibility(8);
        this.mToastBgView.setVisibility(8);
        this.mToastShadow.setVisibility(8);
    }

    private void showToastInner() {
        String str = TAG;
        XILog.d(str, "showToastInner warningBean: " + this.mWaringBean);
        if (this.mWaringBean == null) {
            XILog.d(str, " warningBean is null ");
        }
        this.mDangerTextView.setText("");
        this.mTextView.setText("");
        if (this.mWaringBean.getImgResId() != 0) {
            this.mIconView.setImageResource(this.mWaringBean.getImgResId());
            this.mIconView.setVisibility(0);
        } else {
            this.mIconView.setImageResource(0);
            this.mIconView.setVisibility(8);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mToastBgView.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mToastShadow.getLayoutParams();
        String type = this.mWaringBean.getType();
        if (type.equals(WarningType.AUTO_DRIVE)) {
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.waring_auto_driver_margin_top), 0, 0);
            layoutParams2.setMargins(0, (int) getResources().getDimension(R.dimen.waring_bg_shadow_margin_top), 0, 0);
            this.mToastShadow.setImageResource(R.drawable.bg_warning_shadow);
        } else if (type.equals(WarningType.GENERAL)) {
            layoutParams.setMargins(0, (int) getResources().getDimension(R.dimen.waring_general_driver_margin_top), 0, 0);
            layoutParams2.setMargins(0, (int) getResources().getDimension(R.dimen.osd_bg_shadow_margin_top), 0, 0);
            this.mToastShadow.setImageResource(R.drawable.bg_osd_shadow);
        }
        changeTheme();
        this.mToastBgView.setLayoutParams(layoutParams);
        this.mToastShadow.setLayoutParams(layoutParams2);
        this.mToastRootLayout.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showToastText(WaringBean waringBean) {
        String str = TAG;
        XILog.d(str, "showToastText warning bean: " + waringBean);
        this.mWaringBean = waringBean;
        if (waringBean == null) {
            XILog.d(str, "waring bean is null");
        } else if (waringBean.isShow()) {
            showToastInner();
        } else {
            hideToast();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XLinearLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    private void changeTheme() {
        String str = TAG;
        XILog.d(str, "changeTheme mWaringBean is: " + this.mWaringBean);
        WaringBean waringBean = this.mWaringBean;
        if (waringBean == null) {
            XILog.e(str, "mWaringBean is null ");
        } else if (waringBean.getEmergencyLevel() == 0 || this.mWaringBean.getEmergencyLevel() == 3) {
            this.mToastBgView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_toast_red, null));
            showNormalToastView();
        } else if (this.mWaringBean.getEmergencyLevel() == 1) {
            this.mToastBgView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_toast_normal, null));
            showNormalToastView();
        } else if (this.mWaringBean.getEmergencyLevel() == 2) {
            showDangerToastView();
        } else {
            showNormalToastView();
        }
    }

    private void showNormalToastView() {
        String str = TAG;
        XILog.d(str, "showNormalToastView waring bean is: " + this.mWaringBean);
        WaringBean waringBean = this.mWaringBean;
        if (waringBean == null) {
            XILog.d(str, "showNormalToastView waring bean is null");
            return;
        }
        String text = waringBean.getText();
        this.mToastBgView.setVisibility(0);
        this.mToastShadow.setVisibility(0);
        this.mToastDangerView.setVisibility(8);
        if (BaseConfig.getInstance().isSupportITN()) {
            this.mTextView.setText(StringUtil.switchLineWhenWidthLargerThanMax(text));
        } else {
            this.mTextView.setText(text);
        }
    }

    private void showDangerToastView() {
        String text = this.mWaringBean.getText();
        String str = TAG;
        XILog.d(str, "showDangerToastView bean: " + this.mWaringBean);
        if (BaseConfig.getInstance().isSupportITN()) {
            this.mDangerTextView.setText(StringUtil.switchLineWhenWidthLargerThanMax(text));
        } else {
            this.mDangerTextView.setText(text);
        }
        if (this.mWaringBean.getType().equals(WarningType.SR_BOTTOM_TIPS)) {
            XILog.d(str, "showDangerToastView SR_BOTTOM_TIPS");
            this.mToastDangerView.setBackground(ResourcesCompat.getDrawable(getResources(), this.mWaringBean.getImgResId(), null));
        } else {
            XILog.d(str, "showDangerToastView other");
            this.mToastDangerView.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.bg_third_lcc_exit_waring, null));
        }
        this.mToastBgView.setVisibility(8);
        this.mToastShadow.setVisibility(8);
        this.mToastDangerView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showSRDangerView(WaringBean waringBean) {
        XILog.d(TAG, "showSRDangerView bean: " + waringBean);
        this.mWaringBean = waringBean;
        if (waringBean != null) {
            showToastText(waringBean);
        } else {
            this.mToastDangerView.setVisibility(8);
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
