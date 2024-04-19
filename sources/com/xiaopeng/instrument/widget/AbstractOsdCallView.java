package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public abstract class AbstractOsdCallView extends XRelativeLayout {
    private static final String TAG = "AbstractOsdCallView";
    public XTextView mCallContent;
    public XTextView mName;

    protected abstract int getLayout();

    public void initAdditionView() {
    }

    public void setSwitchChoiceItem(boolean z) {
    }

    public void updateCallView(int i) {
    }

    public AbstractOsdCallView(Context context) {
        this(context, null);
    }

    public AbstractOsdCallView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        init(context);
    }

    public AbstractOsdCallView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(getLayout(), this);
        this.mName = (XTextView) findViewById(R.id.call_from_name);
        this.mCallContent = (XTextView) findViewById(R.id.call_content);
        initAdditionView();
    }

    public void updateCallContent(String str) {
        this.mCallContent.setText(str);
    }

    public void updateName(String str) {
        this.mName.setText(str);
    }
}
