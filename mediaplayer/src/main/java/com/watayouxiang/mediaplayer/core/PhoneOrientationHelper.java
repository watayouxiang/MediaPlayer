package com.watayouxiang.mediaplayer.core;

import android.content.Context;
import android.hardware.SensorManager;
import android.view.OrientationEventListener;

/**
 * 参考：https://www.cnblogs.com/a284628487/p/3361555.html
 */
class PhoneOrientationHelper {
    private OrientationEventListener mOrientationEventListener;

    PhoneOrientationHelper(Context context, final Listener listener) {
        mOrientationEventListener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL) {
            Orientation preOrientation;//上次方向
            Orientation currentOrientation;//本地方向

            @Override
            public void onOrientationChanged(int angle) {
                if (angle >= 0 && angle <= 360) {
                    if (angle < 10 || angle > 350) {//0+-10度，Portrait
                        currentOrientation = Orientation.Portrait;
                    } else if (angle < 190 && angle > 170) {//180+-10度，Portrait_Reverse
                        currentOrientation = Orientation.Portrait_Reverse;
                    } else if (angle < 280 && angle > 260) {//270+-10度，LandScape
                        currentOrientation = Orientation.Landscape;
                    } else if (angle < 100 && angle > 80) {//90+-10度，LandScapeReverse
                        currentOrientation = Orientation.Landscape_Reverse;
                    }

                    if (currentOrientation != null && currentOrientation != preOrientation) {
                        listener.onPhoneOrientationChange(currentOrientation);
                        preOrientation = currentOrientation;
                    }
                }
            }
        };
        mOrientationEventListener.enable();
    }

    /**
     * 释放资源
     */
    void releaseRes() {
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
            mOrientationEventListener = null;
        }
    }

    //==============================================================================================

    interface Listener {
        /**
         * 手机方向变化
         *
         * @param orientation 手机方向
         */
        void onPhoneOrientationChange(Orientation orientation);
    }
}
