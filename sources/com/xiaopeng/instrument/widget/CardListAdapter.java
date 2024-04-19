package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.bean.InfoBean;
import com.xiaopeng.xui.widget.XRecyclerView;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes.dex */
public class CardListAdapter extends RecyclerView.Adapter<ViewHolder> {
    private final Context mContext;
    private final int mCycleNum = 2;
    private ArrayList<InfoBean> mList = new ArrayList<>();
    private final XRecyclerView mRecyclerView;
    private final int mShowType;

    public int getCycleNum() {
        return 2;
    }

    public CardListAdapter(Context context, XRecyclerView xRecyclerView, int i) {
        this.mContext = context;
        this.mShowType = i;
        this.mRecyclerView = xRecyclerView;
    }

    public void updateAllItem(List<InfoBean> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.mContext).inflate(this.mShowType == 1 ? R.layout.item_right_info : R.layout.item_left_info, viewGroup, false));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ArrayList<InfoBean> arrayList = this.mList;
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        ArrayList<InfoBean> arrayList2 = this.mList;
        InfoBean infoBean = arrayList2.get(i % arrayList2.size());
        viewHolder.tvText.setText(infoBean.getName());
        viewHolder.imageView.setImageResource(infoBean.getImgResId());
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        ArrayList<InfoBean> arrayList = this.mList;
        if (arrayList == null || arrayList.size() == 0) {
            return 0;
        }
        if ((this.mRecyclerView.getLayoutManager() instanceof CardPickerLayoutManager) && ((CardPickerLayoutManager) this.mRecyclerView.getLayoutManager()).getCycle()) {
            return this.mList.size() + (this.mList.size() * 2);
        }
        return this.mList.size();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvText;

        public ViewHolder(View view) {
            super(view);
            this.tvText = (TextView) view.findViewById(R.id.item_info_name);
            this.imageView = (ImageView) view.findViewById(R.id.item_info_icon);
        }
    }
}
