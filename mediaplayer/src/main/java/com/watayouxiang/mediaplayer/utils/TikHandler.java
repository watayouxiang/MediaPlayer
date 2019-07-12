package com.watayouxiang.mediaplayer.utils;

import android.os.Handler;
import android.os.Message;

public class TikHandler extends Handler {
    private final long mDelayMillis;
    private OnTikListener mListener;

    public TikHandler(long delayMillis, OnTikListener listener) {
        this.mDelayMillis = delayMillis;
        this.mListener = listener;
        start();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (mListener != null) {
            mListener.onTik();
        }
        start();
    }

    private void start() {
        stop();
        sendEmptyMessageDelayed(0, mDelayMillis);
    }

    private void stop() {
        removeMessages(0);
    }

    /**
     * 释放资源
     */
    public void releaseRes() {
        stop();
        removeCallbacksAndMessages(null);
        if (mListener != null) {
            mListener = null;
        }
    }

    public interface OnTikListener {
        void onTik();
    }
}