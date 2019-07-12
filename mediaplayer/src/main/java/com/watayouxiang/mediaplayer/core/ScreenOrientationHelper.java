package com.watayouxiang.mediaplayer.core;

import android.app.Activity;
import android.content.pm.ActivityInfo;

class ScreenOrientationHelper {
    private Orientation mScreenOrientation;

    /**
     * 设置屏幕方向
     *
     * @param activity          页面
     * @param screenOrientation 屏幕方向
     */
    void setScreenOrientation(Activity activity, Orientation screenOrientation) {
        if (mScreenOrientation != screenOrientation) {
            if (screenOrientation == Orientation.Portrait) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (screenOrientation == Orientation.Portrait_Reverse) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            } else if (screenOrientation == Orientation.Landscape) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else if (screenOrientation == Orientation.Landscape_Reverse) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
            mScreenOrientation = screenOrientation;
        }
    }

    /**
     * 获取屏幕方向
     *
     * @return 屏幕方向
     */
    Orientation getScreenOrientation() {
        return mScreenOrientation;
    }
}
