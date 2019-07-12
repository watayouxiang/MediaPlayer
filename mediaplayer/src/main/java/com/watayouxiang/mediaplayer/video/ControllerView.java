package com.watayouxiang.mediaplayer.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.watayouxiang.mediaplayer.R;
import com.watayouxiang.mediaplayer.utils.TikHandler;
import com.watayouxiang.mediaplayer.utils.TimeUtils;

class ControllerView extends RelativeLayout {
    private ImageView iv_back;
    private TextView tv_title;
    private ImageView iv_share;
    private ImageView iv_start;
    private SeekBar seekBar;
    private TextView tv_time;
    private ImageView iv_fullscreen;

    private TikHandler mTikHandler;
    private boolean mAutoHide;

    public ControllerView(Context context) {
        this(context, null);
    }

    public ControllerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControllerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.controllerview_video, this, true);
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        iv_share = findViewById(R.id.iv_share);
        iv_start = findViewById(R.id.iv_start);
        seekBar = findViewById(R.id.seekBar);
        tv_time = findViewById(R.id.tv_time);
        iv_fullscreen = findViewById(R.id.iv_fullscreen);
        mTikHandler = new TikHandler(5000, new TikHandler.OnTikListener() {
            @Override
            public void onTik() {
                if (mAutoHide && getVisibility() == VISIBLE) {
                    setVisibility(GONE);
                }
            }
        });
        setTitle(null);
        setShare(null);
    }

    /**
     * 释放资源
     */
    public void releaseRes() {
        if (mTikHandler != null) {
            mTikHandler.releaseRes();
            mTikHandler = null;
        }
    }

    /**
     * 设置自动隐藏开关
     *
     * @param autoHide 自动隐藏开关
     */
    public void setAutoHide(boolean autoHide) {
        mAutoHide = autoHide;
    }

    //==============================================================================================

    public void setSeekBar(SeekBar.OnSeekBarChangeListener listener) {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void setBackBtn(OnClickListener listener) {
        iv_back.setOnClickListener(listener);
    }

    public void setFullscreenBtn(OnClickListener listener) {
        iv_fullscreen.setOnClickListener(listener);
    }

    public void setToggleBtn(OnClickListener listener) {
        iv_start.setOnClickListener(listener);
    }

    public void setShare(OnClickListener listener) {
        if (listener != null) {
            iv_share.setVisibility(VISIBLE);
        } else {
            iv_share.setVisibility(GONE);
        }
        iv_share.setOnClickListener(listener);
    }

    public void setToggleBtn(boolean selected) {
        iv_start.setSelected(selected);
    }

    public void setFullscreen(int visibility) {
        iv_fullscreen.setVisibility(visibility);
    }

    public void setTime(long duration, long currentPosition) {
        String _duration = TimeUtils.formatMs(duration);
        String _currentPosition = TimeUtils.formatMs(currentPosition);
        tv_time.setText(_currentPosition + "/" + _duration);
    }

    public void setSeekBar(long duration, long position, long bufferPosition) {
        seekBar.setMax((int) duration);
        seekBar.setProgress((int) position);
        seekBar.setSecondaryProgress((int) bufferPosition);
    }

    public void setTitle(CharSequence title) {
        if (title != null) {
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(title);
        } else {
            tv_title.setVisibility(GONE);
        }
    }
}
