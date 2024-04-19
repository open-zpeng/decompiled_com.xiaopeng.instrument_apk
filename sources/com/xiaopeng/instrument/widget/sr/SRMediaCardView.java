package com.xiaopeng.instrument.widget.sr;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import com.xiaopeng.cluster.utils.XILog;
import com.xiaopeng.instrument.R;
import com.xiaopeng.instrument.viewmodel.sr.SRMediaViewModel;
import com.xiaopeng.instrument.widget.IBaseCustomView;
import com.xiaopeng.libtheme.ThemeManager;
import com.xiaopeng.xui.widget.XImageView;
import com.xiaopeng.xui.widget.XProgressBar;
import com.xiaopeng.xui.widget.XRelativeLayout;
import com.xiaopeng.xui.widget.XTextView;
/* loaded from: classes.dex */
public class SRMediaCardView extends XRelativeLayout implements IBaseCustomView {
    private static final String TAG = "SRMediaCardView";
    private boolean alreadyNormalMode;
    private boolean isOnlineImage;
    private XImageView mBgCover;
    private int mCurrentMode;
    private XImageView mDefaultCover;
    private XTextView mDefaultSXILogan;
    private LifecycleOwner mLifecycleOwner;
    private XTextView mMediaName;
    private int mMediaType;
    protected SRMediaViewModel mMediaViewModel;
    private XImageView mPauseCover;
    private XProgressBar mProgress;
    private RoundedBitmapDrawable mRoundedBitmapDrawable;
    private XTextView mSingerName;
    private XImageView mThumbnail;

    public SRMediaCardView(Context context) {
        this(context, null);
    }

    public SRMediaCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SRMediaCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCurrentMode = -1;
        init();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        boolean isThemeChanged = ThemeManager.isThemeChanged(configuration);
        XILog.i(TAG, "isThemeChange :" + isThemeChanged);
        if (isThemeChanged) {
            changeTheme();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.xiaopeng.xui.widget.XRelativeLayout, android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRoundedBitmapDrawable = null;
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.info_media_sr, this);
        this.mDefaultCover = (XImageView) inflate.findViewById(R.id.iv_default_cover);
        this.mDefaultSXILogan = (XTextView) inflate.findViewById(R.id.tv_media_default_slogan);
        this.mBgCover = (XImageView) inflate.findViewById(R.id.iv_bg_media_cover);
        this.mThumbnail = (XImageView) inflate.findViewById(R.id.iv_media_thumbnail);
        this.mPauseCover = (XImageView) inflate.findViewById(R.id.iv_media_pause);
        this.mProgress = (XProgressBar) inflate.findViewById(R.id.pb_media_progress);
        this.mMediaName = (XTextView) findViewById(R.id.tv_media_name);
        this.mSingerName = (XTextView) findViewById(R.id.tv_singer_name);
        initViewMode();
    }

    private void initViewMode() {
        this.mLifecycleOwner = getContext() instanceof LifecycleOwner ? (LifecycleOwner) getContext() : null;
        newMediaViewModel();
        setLiveDataObserver(this.mMediaViewModel.getDefaultImageMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMediaCardView$t-LaKFxDBOuyxrbUVCTHaMPL1rY
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.showDefaultCover(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mMediaViewModel.getPlayStateMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$NuG_TGj5Fu2dOH5_lKWJzGA8syM
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.updatePause(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mMediaViewModel.getProgressBarMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$6Y4-nZxEaUFwGkERzFqjK9Q7ahU
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.updateProgressVisibility(((Boolean) obj).booleanValue());
            }
        });
        setLiveDataObserver(this.mMediaViewModel.getSignerNameMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$fzoykrjwaXKOt5KxVf3JI0zL9_A
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.updateSinger((String) obj);
            }
        });
        setLiveDataObserver(this.mMediaViewModel.getMediaNameMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$-Zl-PAHL4ZQAxQkY1mY40AUOyag
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.updateMediaName((String) obj);
            }
        });
        setLiveDataObserver(this.mMediaViewModel.getBarValueMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$WPT6y-TcEDKXB3Fb_Ka4km7aVDo
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.updateProgress(((Integer) obj).intValue());
            }
        });
        setLiveDataObserver(this.mMediaViewModel.getBitmapMutableLiveData(), new Observer() { // from class: com.xiaopeng.instrument.widget.sr.-$$Lambda$SRMediaCardView$1tk0DdQyffR7txoSZQ_LziAEX-I
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                SRMediaCardView.this.updateMediaImage((Bitmap) obj);
            }
        });
    }

    protected void newMediaViewModel() {
        if (getContext() instanceof ViewModelStoreOwner) {
            this.mMediaViewModel = (SRMediaViewModel) new ViewModelProvider((ViewModelStoreOwner) getContext()).get(SRMediaViewModel.class);
        } else {
            this.mMediaViewModel = new SRMediaViewModel();
        }
    }

    private void changeTheme() {
        if (this.isOnlineImage) {
            return;
        }
        showDefaultCover(this.mMediaType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDefaultCover(int i) {
        this.mMediaType = i;
        this.isOnlineImage = false;
        if (i == 1) {
            this.mThumbnail.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.sr_ic_netradio_default, null));
        } else if (i == 2) {
            this.mThumbnail.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.sr_ic_read_default, null));
        } else {
            this.mThumbnail.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.sr_ic_music_album_default, null));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateMediaImage(Bitmap bitmap) {
        this.isOnlineImage = true;
        updateThumbnail(bitmap);
    }

    private void showMode(int i) {
        if (this.mCurrentMode == i) {
            XILog.d(TAG, "current mode already : " + this.mCurrentMode + ", mode:" + i);
        } else if (i == 0) {
            this.mDefaultCover.setVisibility(0);
            this.mDefaultSXILogan.setVisibility(0);
            this.mBgCover.setVisibility(8);
            this.mThumbnail.setVisibility(8);
            this.mPauseCover.setVisibility(8);
            this.mMediaName.setVisibility(8);
            this.mSingerName.setVisibility(8);
            this.mCurrentMode = i;
        } else if (i == 1) {
            this.mDefaultCover.setVisibility(8);
            this.mDefaultSXILogan.setVisibility(8);
            this.mBgCover.setVisibility(0);
            this.mThumbnail.setVisibility(0);
            this.mMediaName.setVisibility(0);
            this.mSingerName.setVisibility(0);
            this.mCurrentMode = i;
        } else {
            XILog.d(TAG, "invalid mode: " + i);
        }
    }

    public void hide() {
        super.setVisibility(8);
    }

    public void updateThumbnail(Bitmap bitmap) {
        showNormalMode();
        float dimension = getResources().getDimension(R.dimen.navi_sr_media_thumbnail_corner_radius);
        float dimension2 = getResources().getDimension(R.dimen.navi_sr_media_image_size);
        if (bitmap != null && bitmap.getWidth() > 0 && dimension2 > 0.0f) {
            dimension = (float) Math.ceil((bitmap.getWidth() / dimension2) * dimension);
        }
        RoundedBitmapDrawable create = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        this.mRoundedBitmapDrawable = create;
        create.setCornerRadius(dimension);
        this.mThumbnail.setImageDrawable(this.mRoundedBitmapDrawable);
    }

    public void updateMediaName(String str) {
        showNormalMode();
        this.mMediaName.setText(str);
    }

    public void updateSinger(String str) {
        showNormalMode();
        this.mSingerName.setText(str);
    }

    public void updateProgress(int i) {
        showNormalMode();
        this.mProgress.setProgress(i);
    }

    public void updateProgressVisibility(boolean z) {
        showNormalMode();
        this.mProgress.setVisibility(z ? 0 : 8);
    }

    public void updatePause(boolean z) {
        showNormalMode();
        this.mPauseCover.setVisibility(z ? 8 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.xiaopeng.instrument.widget.IBaseCustomView
    public <T> void setLiveDataObserver(LiveData<T> liveData, Observer<T> observer) {
        LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
        if (lifecycleOwner == null) {
            XILog.d(TAG, "mLifecycleOwner is null");
        } else {
            liveData.observe(lifecycleOwner, observer);
        }
    }

    public void showNormalMode() {
        this.alreadyNormalMode = true;
        showMode(1);
    }

    public void showDefaultMode() {
        if (!this.alreadyNormalMode) {
            XILog.d(TAG, "show default mode");
            showMode(0);
        } else {
            XILog.d(TAG, "already normal mode. no need change mode,just visible");
        }
        setVisibility(0);
    }
}
