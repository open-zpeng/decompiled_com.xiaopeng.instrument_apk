package com.xiaopeng.xui.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import com.xiaopeng.xui.view.XViewDelegate;
import com.xiaopeng.xui.vui.VuiView;
/* loaded from: classes.dex */
public class XTextView extends AppCompatTextView implements VuiView {
    protected XViewDelegate mXViewDelegate;

    public XTextView(Context context) {
        super(context);
    }

    public XTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mXViewDelegate = XViewDelegate.create(this, attributeSet);
        initVui(this, attributeSet);
    }

    public XTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mXViewDelegate = XViewDelegate.create(this, attributeSet, i);
        initVui(this, attributeSet);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onConfigurationChanged(configuration);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onAttachedToWindow();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        XViewDelegate xViewDelegate = this.mXViewDelegate;
        if (xViewDelegate != null) {
            xViewDelegate.onDetachedFromWindow();
        }
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        super.setVisibility(i);
        setVuiVisibility(this, i);
    }

    @Override // android.widget.TextView, android.view.View
    public void setSelected(boolean z) {
        super.setSelected(z);
        setVuiSelected(this, z);
    }

    protected void finalize() throws Throwable {
        super.finalize();
        releaseVui();
    }
}
