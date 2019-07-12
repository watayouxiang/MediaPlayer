package com.watayouxiang.mediaplayer.core;

import android.app.Activity;
import android.view.WindowManager;

class BrightnessHelper {
    private Activity mActivity;

    BrightnessHelper(Activity activity) {
        this.mActivity = activity;
    }

    /**
     * 设置亮度
     *
     * @param percent 百分比
     */
    void setBrightnessPercent(float percent) {
        if (percent > 1) {
            percent = 1;
        } else if (percent < 0) {
            percent = 0;
        }
        WindowManager.LayoutParams layoutParams = mActivity.getWindow().getAttributes();
        layoutParams.screenBrightness = percent;
        mActivity.getWindow().setAttributes(layoutParams);
    }

    /**
     * 获取亮度
     *
     * @return 百分比
     */
    float getBrightnessPercent() {
        float brightness = mActivity.getWindow().getAttributes().screenBrightness;
        //因为没有设置 screenBrightness，所以第一次取值的时候是-1。
        //解决方式：设置默认亮度为 0.5
        if (brightness == -1) {
            brightness = 0.5f;
        }
        return brightness;
    }
}
