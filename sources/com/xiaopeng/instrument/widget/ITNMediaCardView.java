package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.instrument.viewmodel.ITNMediaViewModel;
import com.xiaopeng.instrument.viewmodel.MediaViewModel;
/* loaded from: classes.dex */
public class ITNMediaCardView extends MediaCardView {
    public ITNMediaCardView(Context context) {
        super(context);
    }

    public ITNMediaCardView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ITNMediaCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.MediaCardView
    protected void newMediaViewModel() {
        if (getContext() instanceof ViewModelStoreOwner) {
            this.mMediaViewModel = (MediaViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(ITNMediaViewModel.class);
        } else {
            this.mMediaViewModel = new ITNMediaViewModel();
        }
    }
}
