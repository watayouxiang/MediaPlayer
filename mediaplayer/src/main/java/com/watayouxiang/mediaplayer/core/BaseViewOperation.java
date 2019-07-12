package com.watayouxiang.mediaplayer.core;

interface BaseViewOperation {
    /**
     * 获取网络状况
     *
     * @return 网络状况
     */
    NetworkState getNetworkState();

    /**
     * 设置全屏化
     * <p>
     * 注意事项：
     * 该方法会导致ActionBar样式异常，所以Activity请使用不带ActionBar的主题。
     * manifest的Activity节点配置：android:theme="@style/Theme.AppCompat.Light.NoActionBar"
     *
     * @param fullScreen 视图是否全屏化
     */
    void setFullScreen(boolean fullScreen);

    /**
     * 判断是否全屏化
     *
     * @return 是否全屏化
     */
    boolean isFullScreen();

    /**
     * 设置屏幕方向
     *
     * @param orientation 手机屏幕方向
     */
    void setScreenOrientation(Orientation orientation);

    /**
     * 获屏幕方向
     *
     * @return 手机屏幕方向
     */
    Orientation getScreenOrientation();

    /**
     * 设置亮度
     *
     * @param percent 百分比
     */
    void setBrightnessPercent(float percent);

    /**
     * 获取亮度
     *
     * @return 百分比
     */
    float getBrightnessPercent();

    /**
     * 获取音量
     *
     * @return 百分比
     */
    float getVolumePercent();

    /**
     * 设置音量
     *
     * @param percent 百分比
     */
    void setVolumePercent(float percent);
}
