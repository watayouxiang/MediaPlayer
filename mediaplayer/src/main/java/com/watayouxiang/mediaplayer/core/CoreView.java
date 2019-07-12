package com.watayouxiang.mediaplayer.core;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.Locale;

class CoreView extends RelativeLayout implements Lifecycle, CoreOperation {
    private LogDialog mLogDialog;
    private final static String TAG = "=== MediaPlayer ===";

    public CoreView(Context context) {
        this(context, null);
    }

    public CoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onInitView(context);
        onInitEvent(context);
    }

    /**
     * 初始化事件
     *
     * @param context 上下文
     */
    protected void onInitEvent(Context context) {
        mLogDialog = new LogDialog(context);
    }

    /**
     * 视图初始化
     *
     * @param context 上下文
     */
    protected void onInitView(Context context) {

    }

    /**
     * 添加日志
     *
     * @param log 日志
     */
    protected void addLog(String log) {
        if (log != null) {
            Log.d(TAG, log);
        }
    }

    /**
     * 添加信息
     *
     * @param info 信息
     */
    protected void addInfo(String info) {
        if (mLogDialog != null && info != null) {
            mLogDialog.addData(new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    .format(System.currentTimeMillis()) + " " + info);
        }
    }

    @Override
    public void onDestroy() {
        addLog("onDestroy");
        addInfo("onDestroy");
        if (mLogDialog != null) {
            mLogDialog.releaseRes();
            mLogDialog = null;
        }
        removeAllViews();
    }

    @Override
    public void onResume() {
        addLog("onResume");
        addInfo("onResume");
    }

    @Override
    public void onPause() {
        addLog("onPause");
        addInfo("onPause");
    }

    @Override
    public void showInfoDialog() {
        if (mLogDialog != null) {
            mLogDialog.show();
        }
    }
}
