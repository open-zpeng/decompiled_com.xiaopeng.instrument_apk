package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.animator.AnimatorHelper;
import com.xiaopeng.instrument.animator.AnimatorType;
import com.xiaopeng.instrument.animator.BaseViewAnimator;
import com.xiaopeng.instrument.bean.InfoContainBean;
import com.xiaopeng.instrument.widget.CardPickerLayoutManager;
import com.xiaopeng.xui.widget.XRecyclerView;
import com.xiaopeng.xui.widget.XRelativeLayout;
/* loaded from: classes.dex */
public abstract class BaseInfoView extends XRelativeLayout {
    protected final String TAG;
    protected CardListAdapter cardListAdapter;
    protected CardPickerLayoutManager cardPickerLayoutManager;
    private AnimatorHelper mAnimatorHelper;
    private CarConditionCardView mCarConditionCardView;
    private ViewGroup mCardView;
    public Context mContext;
    protected InfoContainBean mInfoContainBean;
    private BaseViewAnimator mListHideAnimator;
    private BaseViewAnimator mListShowAnimator;
    protected MapCardView mMapCardView;
    private MediaCardView mMediaCardView;
    private OdoMeterCardView mOdoMeterCardView;
    private PowerConsumptionCardView mPowerConsumptionCardView;
    private XRecyclerView mRecyclerView;

    abstract int getLayout();

    abstract int getPosition();

    public BaseInfoView(Context context) {
        this(context, null);
    }

    public BaseInfoView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.TAG = getClass().getSimpleName();
        init(context);
    }

    public BaseInfoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.TAG = getClass().getSimpleName();
        init(context);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        BaseViewAnimator baseViewAnimator = this.mListShowAnimator;
        if (baseViewAnimator != null) {
            baseViewAnimator.destroy();
        }
        BaseViewAnimator baseViewAnimator2 = this.mListHideAnimator;
        if (baseViewAnimator2 != null) {
            baseViewAnimator2.destroy();
        }
        super.onDetachedFromWindow();
    }

    public void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(getContext()).inflate(getLayout(), this);
        initAnimator();
        this.mRecyclerView = (XRecyclerView) findViewById(R.id.info_list);
        this.mMapCardView = (MapCardView) findViewById(R.id.info_map);
        this.mMediaCardView = (MediaCardView) findViewById(R.id.info_media);
        this.mCarConditionCardView = (CarConditionCardView) findViewById(R.id.info_car_condition);
        this.mOdoMeterCardView = (OdoMeterCardView) findViewById(R.id.info_power_odo_meter);
        this.mPowerConsumptionCardView = (PowerConsumptionCardView) findViewById(R.id.info_power_consumption);
        this.mCardView = (ViewGroup) findViewById(R.id.card_view);
        this.mMapCardView.setMapPos(getPosition());
        CardListAdapter cardListAdapter = new CardListAdapter(context, this.mRecyclerView, getPosition());
        this.cardListAdapter = cardListAdapter;
        this.mRecyclerView.setAdapter(cardListAdapter);
        CardPickerLayoutManager cardPickerLayoutManager = new CardPickerLayoutManager(getContext(), this.mRecyclerView, 1, false, 3, getPosition());
        this.cardPickerLayoutManager = cardPickerLayoutManager;
        cardPickerLayoutManager.setOnSelectedViewListener(new CardPickerLayoutManager.OnSelectedViewListener() { // from class: com.xiaopeng.instrument.widget.BaseInfoView.1
            @Override // com.xiaopeng.instrument.widget.CardPickerLayoutManager.OnSelectedViewListener
            public void onScrollMidView(View view, boolean z) {
            }

            @Override // com.xiaopeng.instrument.widget.CardPickerLayoutManager.OnSelectedViewListener
            public void onSelectedView(View view, int i) {
                int cycleNum = BaseInfoView.this.cardListAdapter.getCycleNum() / 2;
                int size = BaseInfoView.this.mInfoContainBean.getInfoBeanList().size();
                XILog.i(BaseInfoView.this.TAG, "onSelectedView() pos = " + i + " size = " + size + " name = " + BaseInfoView.this.mInfoContainBean.getInfoBeanList().get(i % size).getName() + " area = " + BaseInfoView.this.getPosition());
                if (i < size * cycleNum || i >= (cycleNum + 1) * size) {
                    BaseInfoView.this.cardPickerLayoutManager.scrollToPositionForCycle(i, size);
                }
            }
        });
    }

    private void initAnimator() {
        this.mAnimatorHelper = new AnimatorHelper();
        this.mListShowAnimator = AnimatorType.SlideInNormal.initAnimator();
        this.mListHideAnimator = AnimatorType.SlideOutScale.initAnimator();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showMapCarView() {
        this.mMediaCardView.hide();
        this.mCarConditionCardView.setVisibility(8);
        this.mPowerConsumptionCardView.setVisibility(8);
        this.mOdoMeterCardView.setVisibility(8);
        this.mMapCardView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showMediaCarView() {
        this.mMapCardView.setVisibility(8);
        this.mCarConditionCardView.setVisibility(8);
        this.mPowerConsumptionCardView.setVisibility(8);
        this.mOdoMeterCardView.setVisibility(8);
        this.mMediaCardView.showDefaultMode();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showCarConditionCarView() {
        this.mMapCardView.setVisibility(8);
        this.mMediaCardView.hide();
        this.mPowerConsumptionCardView.setVisibility(8);
        this.mOdoMeterCardView.setVisibility(8);
        this.mCarConditionCardView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showPowerConsumptionCarView() {
        this.mMapCardView.setVisibility(8);
        this.mMediaCardView.hide();
        this.mCarConditionCardView.setVisibility(8);
        this.mOdoMeterCardView.setVisibility(8);
        this.mPowerConsumptionCardView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void showOdoMeterCarView() {
        this.mMapCardView.setVisibility(8);
        this.mMediaCardView.hide();
        this.mCarConditionCardView.setVisibility(8);
        this.mPowerConsumptionCardView.setVisibility(8);
        this.mOdoMeterCardView.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void hideFixedCardView() {
        this.mMapCardView.setVisibility(8);
        this.mMediaCardView.hide();
        this.mCarConditionCardView.setVisibility(8);
        this.mPowerConsumptionCardView.setVisibility(8);
        this.mOdoMeterCardView.setVisibility(8);
    }

    public void showSubCardView(int i) {
        XILog.i(this.TAG, "显示卡片： " + i);
        if (i == 0) {
            showMapCarView();
        } else if (i == 1) {
            showMediaCarView();
        } else if (i == 2) {
            showCarConditionCarView();
        } else if (i == 3) {
            showPowerConsumptionCarView();
        } else if (i != 4) {
        } else {
            showOdoMeterCarView();
        }
    }

    public void showCardView(boolean z) {
        this.mCardView.setVisibility(z ? 0 : 8);
    }

    public void showList(boolean z) {
        this.mRecyclerView.setVisibility(z ? 0 : 8);
        this.mAnimatorHelper.showAnimator(z, this.mListShowAnimator, this.mListHideAnimator, this.mRecyclerView);
    }

    public void updateListHighIndex(int i) {
        InfoContainBean infoContainBean = this.mInfoContainBean;
        this.cardPickerLayoutManager.smoothScrollToTargetPosition(infoContainBean == null ? 0 : infoContainBean.getInfoBeanList().size(), i);
    }

    public void updateListData(InfoContainBean infoContainBean) {
        if (infoContainBean != null) {
            this.mInfoContainBean = infoContainBean;
            this.cardListAdapter.updateAllItem(infoContainBean.getInfoBeanList());
            this.cardPickerLayoutManager.scrollToTargetPosition(infoContainBean.getInfoBeanList().size(), infoContainBean.getSelectIndex());
            return;
        }
        XILog.d(this.TAG, "updateListData:infoContainBean is null");
    }
}
