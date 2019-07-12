package com.watayouxiang.mediaplayer.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watayouxiang.mediaplayer.R;

class LoadingView extends RelativeLayout {
    private TextView tv_progress;
    private ProgressBar progressBar;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.loadingview_video, this, true);
        tv_progress = findViewById(R.id.tv_progress);
        progressBar = findViewById(R.id.progressBar);
    }

    public void setProgress(float percent) {
        int _percent = (int) (percent * 100);
        tv_progress.setText(_percent + "%");
    }
}