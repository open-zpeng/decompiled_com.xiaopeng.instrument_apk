package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XLinearLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class OsdCallView extends AbstractOsdCallView {
    private static final String TAG = "OsdCallView";
    private XLinearLayout mCallAnswer;
    private XTextView mCallSwitchTip;
    private XLinearLayout mReject;
    private XTextView mRejectText;

    @Override // com.xiaopeng.instrument.widget.AbstractOsdCallView
    protected int getLayout() {
        return R.layout.layout_osd_call;
    }

    public OsdCallView(Context context) {
        this(context, null);
    }

    public OsdCallView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public OsdCallView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.xiaopeng.instrument.widget.AbstractOsdCallView
    public void initAdditionView() {
        this.mCallAnswer = (XLinearLayout) findViewById(R.id.call_answer);
        this.mReject = (XLinearLayout) findViewById(R.id.call_reject);
        this.mRejectText = (XTextView) findViewById(R.id.reject_text);
        this.mCallSwitchTip = (XTextView) findViewById(R.id.call_switch_tip);
    }

    @Override // com.xiaopeng.instrument.widget.AbstractOsdCallView
    public void updateCallView(int i) {
        if (i == 1) {
            this.mCallAnswer.setVisibility(0);
            this.mReject.setVisibility(0);
            this.mCallSwitchTip.setVisibility(0);
            this.mRejectText.setText(getResources().getString(R.string.osd_call_reject));
            this.mReject.setSelected(false);
        } else if (i != 2) {
        } else {
            this.mCallAnswer.setVisibility(8);
            this.mReject.setVisibility(0);
            this.mRejectText.setText(getResources().getString(R.string.osd_call_hang_up));
            this.mCallSwitchTip.setVisibility(8);
            this.mReject.setSelected(true);
        }
    }

    @Override // com.xiaopeng.instrument.widget.AbstractOsdCallView
    public void setSwitchChoiceItem(boolean z) {
        this.mCallAnswer.setSelected(z);
        this.mReject.setSelected(!z);
    }
}
