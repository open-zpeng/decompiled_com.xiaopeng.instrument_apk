package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import com.xiaopeng.instrument.R;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class OsdPGearProtectView extends XRelativeLayout {
    private static final String TAG = "OsdPGearProtectView";
    private XTextView mAnswerText;
    private XTextView mContent;

    public OsdPGearProtectView(Context context) {
        this(context, null);
    }

    public OsdPGearProtectView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public OsdPGearProtectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_osd_p_gear_protect, this);
        this.mContent = (XTextView) findViewById(R.id.tv_p_content);
        this.mAnswerText = (XTextView) findViewById(R.id.p_answer_text);
    }

    public void updateConfirmContent(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mAnswerText.setText(str);
    }

    public void updatePGearProtectView(int i) {
        if (i == 2) {
            this.mContent.setText(getContext().getString(R.string.p_gear_protect_error_state));
        } else if (i == 1) {
            this.mContent.setText(getContext().getString(R.string.p_gear_protect_normal_state));
        }
    }
}
