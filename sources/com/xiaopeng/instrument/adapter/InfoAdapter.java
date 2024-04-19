package com.xiaopeng.instrument.adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.ActivityChooserView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.InfoBean;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class InfoAdapter extends RecyclerView.Adapter<ViewHolder> {
    private static final float SELECT_ALPHA = 1.0f;
    public static final String TAG = "InfoAdapter";
    private static final float UN_SELECT_ALPHA = 0.36f;
    private List<InfoBean> mInfoBeanList = new ArrayList();
    private int mSelectIndex;
    private int mShowArea;

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        int i2 = this.mShowArea;
        int i3 = R.layout.item_left_info;
        if (i2 != 0 && i2 == 1) {
            i3 = R.layout.item_right_info;
        }
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(i3, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        bindView(viewHolder, i, this.mInfoBeanList.get(i % this.mInfoBeanList.size()));
    }

    private void bindView(ViewHolder viewHolder, int i, InfoBean infoBean) {
        if (infoBean == null) {
            XILog.d(TAG, "mInfoBean is null");
            return;
        }
        Resources resources = viewHolder.mBg.getResources();
        if (i == this.mSelectIndex) {
            viewHolder.mName.setSelected(true);
            viewHolder.mName.setTextSize(1, resources.getDimension(R.dimen.item_info_name_selected_text_size));
            viewHolder.mIcon.setAlpha(1.0f);
            ViewGroup.LayoutParams layoutParams = viewHolder.mIcon.getLayoutParams();
            layoutParams.width = resources.getDimensionPixelSize(R.dimen.info_list_icon_large_width);
            layoutParams.height = resources.getDimensionPixelSize(R.dimen.info_list_icon_large_height);
            viewHolder.mIcon.setLayoutParams(layoutParams);
        } else {
            viewHolder.mName.setSelected(false);
            viewHolder.mName.setTextSize(1, resources.getDimension(R.dimen.item_info_name_unselected_text_size));
            viewHolder.mIcon.setAlpha(UN_SELECT_ALPHA);
            ViewGroup.LayoutParams layoutParams2 = viewHolder.mIcon.getLayoutParams();
            layoutParams2.width = resources.getDimensionPixelSize(R.dimen.info_list_icon_small_width);
            layoutParams2.height = resources.getDimensionPixelSize(R.dimen.info_list_icon_small_height);
            viewHolder.mIcon.setLayoutParams(layoutParams2);
        }
        viewHolder.mIcon.setImageResource(infoBean.getImgResId());
        viewHolder.mName.setText(infoBean.getName());
    }

    private void setInfoBeanList(List<InfoBean> list) {
        this.mInfoBeanList.clear();
        this.mInfoBeanList.addAll(list);
    }

    public void updateAllItem(List<InfoBean> list, int i, int i2) {
        XILog.d(TAG, "updateData showAreaType:" + i2 + " selectIndex:" + i + " lastSelectIndex:" + this.mSelectIndex);
        resetSelectIndex(i, i2);
        setInfoBeanList(list);
        notifyDataSetChanged();
    }

    private void resetSelectIndex(int i, int i2) {
        this.mSelectIndex = i;
        this.mShowArea = i2;
    }

    public void updateItem(int i, int i2) {
        XILog.i(TAG, "updateItem showAreaType:" + i2 + " selectIndex:" + i + " lastSelectIndex:" + this.mSelectIndex);
        int i3 = this.mSelectIndex;
        resetSelectIndex(i, i2);
        notifyItemChanged(i3);
        notifyItemChanged(this.mSelectIndex);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.mInfoBeanList.size() == 0) {
            return 0;
        }
        return ActivityChooserView.ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    /* loaded from: classes.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private XLinearLayout mBg;
        private XImageView mIcon;
        private XTextView mName;

        ViewHolder(View view) {
            super(view);
            this.mName = (XTextView) view.findViewById(R.id.item_info_name);
            this.mIcon = (XImageView) view.findViewById(R.id.item_info_icon);
            this.mBg = (XLinearLayout) view.findViewById(R.id.item_info_bg);
        }
    }
}
