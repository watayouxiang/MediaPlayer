package com.watayouxiang.mediaplayer.core;

import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

class ViewSizeHelper {
    private View mView;
    private int mViewInitWidth;//view的初始化宽
    private int mViewInitHeight;//view的初始化高
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener;

    /**
     * 释放资源
     */
    void releaseRes() {
        if (mView != null && mOnGlobalLayoutListener != null) {
            mView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            mOnGlobalLayoutListener = null;
            mView = null;
        }
    }

    //=====================================================================
    // 尺寸变化监听，获取初始化尺寸
    //=====================================================================

    ViewSizeHelper(final View view, final Listener listener) {
        mView = view;
        view.getViewTreeObserver().addOnGlobalLayoutListener(
                mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
                    private int preWidth, preHeight;
                    private boolean init = false;

                    @Override
                    public void onGlobalLayout() {
                        int width = view.getMeasuredWidth();
                        int height = view.getMeasuredHeight();
                        if (preWidth != width || preHeight != height) {
                            //获取初始化尺寸
                            if (!init) {
                                init = true;
                                mViewInitWidth = width;
                                mViewInitHeight = height;
                            }
                            //通知宽高变化
                            listener.onViewSizeChange(width, height);
                            //记录宽高
                            preWidth = width;
                            preHeight = height;
                        }
                    }
                }
        );
    }

    interface Listener {
        /**
         * 视图尺寸变化
         *
         * @param width  宽
         * @param height 高
         */
        void onViewSizeChange(int width, int height);
    }

    //=====================================================================
    // 判断是否全屏化，设置原始尺寸，设置全屏化
    //=====================================================================

    private boolean mViewFullScreen;//view是否已经全屏化

    /**
     * 设置全屏化
     * <p>
     * 注意事项：
     * 该方法会导致ActionBar样式异常，所以Activity请使用不带ActionBar的主题。
     * manifest的Activity节点配置：android:theme="@style/Theme.AppCompat.Light.NoActionBar"
     */
    void setFullScreen() {
        if (!isFullScreen()) {
            // hide of status and navigation bar
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            }
            //修改 View 宽高
            ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            }
            mViewFullScreen = true;
        }
    }

    /**
     * 设置原始尺寸
     */
    void setInitSize() {
        if (isFullScreen()) {
            // show status and navigation bar
            mView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            //修改 View 宽高
            ViewGroup.LayoutParams layoutParams = mView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.width = mViewInitWidth;
                layoutParams.height = mViewInitHeight;
            }
            mViewFullScreen = false;
        }
    }
    
    /**
     * 判断视图是否全屏化
     *
     * @return 视图是否全屏化
     */
    boolean isFullScreen() {
        return mViewFullScreen;
    }
}
