package com.watayouxiang.mediaplayer.core;

import android.app.Service;
import android.content.Context;
import android.media.AudioManager;

class VolumeHelper {
    private Context mContext;

    VolumeHelper(Context context) {
        this.mContext = context;
    }

    /**
     * 获取音量
     *
     * @return 百分比
     */
    float getVolumePercent() {
        float percent = 0;
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
        if (audioManager != null) {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            percent = ((float) volume) / ((float) maxVolume);
        }
        return percent;
    }

    /**
     * 设置音量
     *
     * @param percent 百分比
     */
    void setVolumePercent(float percent) {
        if (percent > 1) {
            percent = 1;
        } else if (percent < 0) {
            percent = 0;
        }
        AudioManager audioManager = (AudioManager) mContext.getSystemService(Service.AUDIO_SERVICE);
        if (audioManager != null) {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int index = (int) (maxVolume * percent);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);
        }
    }
}
