package com.xiaopeng.instrument.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.core.content.ContextCompat;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.utils.ResUtil;
import com.xiaopeng.xui.widget.XLinearLayout;
import java.util.List;
/* loaded from: classes.dex */
public class NaviLaneInfoView extends XLinearLayout {
    public static final String LANE_RESOURCE_PREFIX_LANDBACK = "navi_lane_ic_landback_";
    public static final String LANE_RESOURCE_PREFIX_LANDFRONT = "navi_lane_ic_landfront_";
    public static final int LaneActionBus = 21;
    private static final int LaneActionNULL = 255;
    private static final int NAV_LANES_MAX = 7;
    private static final String TAG = "LaneInfoView";
    public static final int TollLaneTypeAutomatric = 2;
    public static final int TollLaneTypeETC = 1;
    public static final int TollLaneTypeNormal = 0;
    private int mLaneItemViewHeight;
    private int mLaneItemViewWidth;
    private int mLaneNum;

    private int getTollGateResource(int i) {
        if (i == 0) {
            return R.drawable.navi_lane_ic_landtype_normal;
        }
        if (i == 1) {
            return R.drawable.navi_lane_ic_landtype_etc;
        }
        if (i == 2) {
            return R.drawable.navi_lane_ic_landtype_automatric;
        }
        return 0;
    }

    public NaviLaneInfoView(Context context) {
        this(context, null);
    }

    public NaviLaneInfoView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NaviLaneInfoView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLaneNum = 0;
        initView();
    }

    public void updateLaneBg(boolean z) {
        setBackgroundResource(z ? R.drawable.navi_bg_lane_norml : R.drawable.navi_bg_lane);
    }

    public void updateNormalLaneData(int[][] iArr) {
        if (iArr == null || iArr.length != 2) {
            return;
        }
        updateLaneData(iArr[0], iArr[1]);
    }

    private void initView() {
        setDividerDrawable(ContextCompat.getDrawable(getContext(), R.drawable.navi_lane_divider));
        setShowDividers(2);
        setDividerPadding(0);
        setBackgroundResource(R.drawable.navi_bg_lane);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.navi_lane_padding_left_right);
        setPadding(dimensionPixelOffset, getResources().getDimensionPixelOffset(R.dimen.navi_lane_item_padding_top), dimensionPixelOffset, getResources().getDimensionPixelOffset(R.dimen.navi_lane_item_padding_bottom));
        this.mLaneItemViewWidth = getResources().getDimensionPixelSize(R.dimen.navi_lane_item_width);
        this.mLaneItemViewHeight = getResources().getDimensionPixelSize(R.dimen.navi_lane_item_height);
    }

    public void updateLaneData(int[] iArr, int[] iArr2) {
        if (iArr == null || iArr.length == 0) {
            hideLaneInfoView();
        } else if (iArr2 == null || iArr2.length != iArr.length) {
            hideLaneInfoView();
            XILog.e(TAG, "frontLane.length!=backLane.length!");
        } else {
            int length = iArr.length;
            this.mLaneNum = length;
            if (length > 7) {
                setVisibility(8);
                XILog.e(TAG, "lane num is too much：" + this.mLaneNum);
                return;
            }
            setVisibility(0);
            addLaneViews(iArr, iArr2);
        }
    }

    public void updateTollGateData(List<Integer> list) {
        if (list == null || list.size() == 0) {
            hideLaneInfoView();
            return;
        }
        this.mLaneNum = list.size();
        if (list.size() > 7) {
            setVisibility(8);
            return;
        }
        setVisibility(0);
        addTollGateViews(list);
    }

    public void hideLaneInfoView() {
        this.mLaneNum = 0;
        setVisibility(8);
    }

    private void addLaneViews(int[] iArr, int[] iArr2) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            int laneResource = getLaneResource(iArr2[i2], iArr[i2]);
            if (laneResource == 0) {
                XILog.w(TAG, ">>> have not ind the resource id of lane image");
            } else {
                addLaneView(i, laneResource);
                i++;
            }
        }
        while (i < getChildCount()) {
            getChildAt(i).setVisibility(8);
            i++;
        }
    }

    private void addLaneView(int i, int i2) {
        ImageView imageView = (ImageView) getChildAt(i);
        if (imageView == null) {
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(this.mLaneItemViewWidth, this.mLaneItemViewHeight);
            layoutParams.gravity = 16;
            View imageView2 = new ImageView(getContext());
            addView(imageView2, layoutParams);
            imageView = imageView2;
        }
        imageView.setVisibility(0);
        imageView.setImageResource(i2);
        imageView.setContentDescription("LaneInfo" + i);
    }

    private void addTollGateViews(List<Integer> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int tollGateResource = getTollGateResource(list.get(i).intValue());
            if (tollGateResource == 0) {
                XILog.w(TAG, ">>> invalid toll gate");
            } else {
                addLaneView(i, tollGateResource);
            }
        }
        while (size < getChildCount()) {
            getChildAt(size).setVisibility(8);
            size++;
        }
    }

    private int getLaneResource(int i, int i2) {
        String str;
        if (255 == i) {
            if (i2 != 255) {
                return ResUtil.getDrawableResByName(LANE_RESOURCE_PREFIX_LANDFRONT + Integer.toHexString(i2));
            }
            return 0;
        }
        if (255 == i2) {
            if (21 == i) {
                str = LANE_RESOURCE_PREFIX_LANDFRONT + Integer.toHexString(i);
            } else {
                str = LANE_RESOURCE_PREFIX_LANDBACK + Integer.toHexString(i);
            }
        } else if (i == i2) {
            str = LANE_RESOURCE_PREFIX_LANDFRONT + Integer.toHexString(i2);
        } else {
            str = LANE_RESOURCE_PREFIX_LANDFRONT + Integer.toHexString(i) + Integer.toHexString(i2);
        }
        return ResUtil.getDrawableResByName(str);
    }
}
