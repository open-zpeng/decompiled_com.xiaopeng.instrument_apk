package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.App;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XRecyclerView;
import javax.validation.constraints.NotNull;
/* loaded from: classes.dex */
public class CardPickerLayoutManager extends LinearLayoutManager {
    private final int ItemViewHeight;
    private final boolean mIsAlpha;
    private final boolean mIsCycle;
    private int mItemCount;
    private int mItemViewHeight;
    private final LinearSnapHelper mLinearSnapHelper;
    private OnSelectedViewListener mOnSelectedViewListener;
    private final int mOrientation;
    private XRecyclerView mRecyclerView;
    private final float mScale;
    private int mShowType;

    /* loaded from: classes.dex */
    public interface OnSelectedViewListener {
        void onScrollMidView(View view, boolean z);

        void onSelectedView(View view, int i);
    }

    public boolean getCycle() {
        return true;
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
    }

    public CardPickerLayoutManager(Context context, int i, boolean z) {
        super(context, i, z);
        this.ItemViewHeight = (int) App.getInstance().getResources().getDimension(R.dimen.info_list_item_height);
        this.mIsCycle = true;
        this.mIsAlpha = true;
        this.mScale = 0.6f;
        this.mItemCount = -1;
        this.mShowType = 0;
        this.mLinearSnapHelper = new LinearSnapHelper();
        this.mOrientation = i;
    }

    public CardPickerLayoutManager(Context context, XRecyclerView xRecyclerView, int i, boolean z, int i2, int i3) {
        super(context, i, z);
        this.ItemViewHeight = (int) App.getInstance().getResources().getDimension(R.dimen.info_list_item_height);
        this.mIsCycle = true;
        this.mIsAlpha = true;
        this.mScale = 0.6f;
        this.mItemCount = -1;
        this.mShowType = 0;
        this.mLinearSnapHelper = new LinearSnapHelper();
        this.mShowType = i3;
        this.mItemCount = i2;
        if (i2 % 2 == 0) {
            this.mItemCount = i2 + 1;
        }
        this.mOrientation = i;
        this.mRecyclerView = xRecyclerView;
        xRecyclerView.setLayoutManager(this);
        if (this.mItemCount != 0) {
            setAutoMeasureEnabled(false);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onAttachedToWindow(RecyclerView recyclerView) {
        super.onAttachedToWindow(recyclerView);
        this.mLinearSnapHelper.attachToRecyclerView(recyclerView);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onMeasure(@NotNull RecyclerView.Recycler recycler, @NotNull RecyclerView.State state, int i, int i2) {
        if (getItemCount() != 0 && this.mItemCount != 0 && state.getItemCount() > 0) {
            View viewForPosition = recycler.getViewForPosition(0);
            measureChildWithMargins(viewForPosition, i, i2);
            int measuredWidth = viewForPosition.getMeasuredWidth();
            this.mItemViewHeight = viewForPosition.getMeasuredHeight();
            int i3 = this.mOrientation;
            if (i3 == 0) {
                int i4 = (int) ((((this.mItemCount - 1) * 1.0f) / 2.0f) * measuredWidth);
                this.mRecyclerView.setClipToPadding(false);
                this.mRecyclerView.setPadding(i4, 0, i4, 0);
                setMeasuredDimension(measuredWidth * this.mItemCount, this.mItemViewHeight);
                return;
            } else if (i3 == 1) {
                int i5 = (this.mItemCount - 1) / 2;
                this.mRecyclerView.setClipToPadding(false);
                setMeasuredDimension(measuredWidth, this.mItemViewHeight * this.mItemCount);
                return;
            } else {
                return;
            }
        }
        super.onMeasure(recycler, state, i, i2);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        try {
            super.onLayoutChildren(recycler, state);
        } catch (IndexOutOfBoundsException e) {
            XILog.e("CardPickerLayoutManager", "onLayoutChildren() error " + e.getMessage());
            e.printStackTrace();
        }
        if (getItemCount() < 0 || state.isPreLayout()) {
            return;
        }
        int i = this.mOrientation;
        if (i == 0) {
            scaleHorizontalChildView();
        } else if (i == 1) {
            scaleVerticalChildView();
        }
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        scaleHorizontalChildView();
        return super.scrollHorizontallyBy(i, recycler, state);
    }

    @Override // androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        scaleVerticalChildView();
        return super.scrollVerticallyBy(i, recycler, state);
    }

    private void scaleHorizontalChildView() {
        float width = getWidth() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                float min = ((Math.min(width, Math.abs(width - ((getDecoratedLeft(childAt) + getDecoratedRight(childAt)) / 2.0f))) * (-0.39999998f)) / width) + 1.0f;
                childAt.setScaleX(min);
                childAt.setScaleY(min);
                childAt.setAlpha(min);
            }
        }
    }

    private void scaleVerticalChildView() {
        View findSnapView = this.mLinearSnapHelper.findSnapView(this);
        float height = getHeight() / 2.0f;
        for (int i = 0; i < getChildCount(); i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                float decoratedTop = height - ((getDecoratedTop(childAt) + getDecoratedBottom(childAt)) / 2.0f);
                float min = ((Math.min(height, Math.abs(decoratedTop)) * (-0.39999998f)) / height) + 1.0f;
                childAt.setScaleX(min);
                childAt.setScaleY(min);
                int i2 = decoratedTop >= 0.0f ? 1 : -1;
                int i3 = this.mShowType == 0 ? 1 : -1;
                float f = 1.0f - min;
                childAt.setTranslationY(((i2 * this.mItemViewHeight) * f) / 2.0f);
                View findViewById = childAt.findViewById(R.id.item_info_name);
                findViewById.setTranslationX(i3 * f * 60.0f);
                float f2 = f + 1.0f;
                findViewById.setScaleX(f2);
                findViewById.setScaleY(f2);
                boolean equals = childAt.equals(findSnapView);
                childAt.setAlpha(equals ? 1.0f : min - 0.2f);
                this.mOnSelectedViewListener.onScrollMidView(childAt, equals);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        LinearSnapHelper linearSnapHelper;
        View findSnapView;
        super.onScrollStateChanged(i);
        if (i != 0 || this.mOnSelectedViewListener == null || (linearSnapHelper = this.mLinearSnapHelper) == null || (findSnapView = linearSnapHelper.findSnapView(this)) == null) {
            return;
        }
        this.mOnSelectedViewListener.onSelectedView(findSnapView, getPosition(findSnapView));
    }

    public void scrollToPositionForCycle(int i, int i2) {
        CardListAdapter cardListAdapter = (CardListAdapter) this.mRecyclerView.getAdapter();
        scrollToPositionWithOffset((((cardListAdapter == null ? 1 : cardListAdapter.getCycleNum() / 2) * i2) + (i % i2)) - (this.mItemCount / 2), 0);
    }

    public void scrollToTargetPosition(int i, int i2) {
        View findSnapView = this.mLinearSnapHelper.findSnapView(this);
        if (findSnapView != null) {
            int position = getPosition(findSnapView);
            int i3 = i2 - (position % i);
            if (i3 < 0) {
                i3 += i;
            }
            scrollToPositionForCycle(position + i3, i);
            return;
        }
        XILog.i("CardPickerLayoutManager", "centerView == null 测量不及时");
        if (i > 1) {
            scrollToPositionForCycle(1, i);
        }
    }

    public void smoothScrollToTargetPosition(int i, int i2) {
        View findSnapView = this.mLinearSnapHelper.findSnapView(this);
        if (findSnapView != null) {
            int position = getPosition(findSnapView) % i;
            int i3 = 1;
            int i4 = i - 1;
            int i5 = i2 - position;
            if (position == 0 && i2 == i4) {
                i3 = -1;
            } else if (position != i4 || i2 != 0) {
                i3 = i5;
            }
            this.mRecyclerView.smoothScrollBy(0, this.ItemViewHeight * i3);
        }
    }

    public void setOnSelectedViewListener(OnSelectedViewListener onSelectedViewListener) {
        this.mOnSelectedViewListener = onSelectedViewListener;
    }
}
