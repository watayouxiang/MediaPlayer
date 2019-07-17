package com.watayouxiang.mediaplayer.video;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatImageView;

import com.watayouxiang.mediaplayer.R;
import com.watayouxiang.mediaplayer.utils.ImageLoader;

public class CoverView extends RelativeLayout {
    private AppCompatImageView iv_cover;

    public CoverView(Context context) {
        this(context, null);
    }

    public CoverView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoverView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.coverview_video, this, true);
        iv_cover = findViewById(R.id.iv_cover);
    }

    public void setCoverUrl(String coverUrl) {
        if (!TextUtils.isEmpty(coverUrl)) {
            (new ImageLoader(iv_cover)).loadAsync(coverUrl);
        }
    }
}
