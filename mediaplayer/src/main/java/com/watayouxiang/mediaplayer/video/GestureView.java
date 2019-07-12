package com.watayouxiang.mediaplayer.video;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.watayouxiang.mediaplayer.R;
import com.watayouxiang.mediaplayer.utils.TimeUtils;

class GestureView extends RelativeLayout {
    private ImageView gestureImage;
    private TextView gestureText;

    public GestureView(Context context) {
        this(context, null);
    }

    public GestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.gestureview_video, this, true);
        gestureImage = findViewById(R.id.gesture_image);
        gestureText = findViewById(R.id.gesture_text);
    }

    /**
     * 更新亮度
     *
     * @param percent 百分比
     */
    public void updateBrightness(float percent) {
        gestureImage.setImageResource(R.drawable.icon_brightness);
        gestureText.setText((int) (percent * 100f) + "%");
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
    }

    /**
     * 更新声音
     *
     * @param percent 百分比
     */
    public void updateVolume(float percent) {
        int position = (int) (percent * 100f);
        gestureImage.setImageResource(R.drawable.icon_volume);
        gestureText.setText(position + "%");
        gestureImage.setImageLevel(position);
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
    }

    /**
     * 更新进度
     *
     * @param currentPosition 视频当前进度
     * @param seekPosition    seek的位置
     */
    public void updateProgress(long currentPosition, long seekPosition) {
        if (seekPosition >= currentPosition) {
            gestureImage.setImageResource(R.drawable.icon_seek_forward);
        } else {
            gestureImage.setImageResource(R.drawable.icon_seek_rewind);
        }
        gestureText.setText(TimeUtils.formatMs(seekPosition));
        if (getVisibility() != VISIBLE) {
            setVisibility(VISIBLE);
        }
    }
}