package com.watayouxiang.mediaplayer.core;

interface BasePlayerOperation {
    /**
     * 设置播放器方向
     *
     * @param orientation 播放器方向
     */
    void setOrientation(Orientation orientation);

    /**
     * 获取播放器方向
     *
     * @return 播放器方向
     */
    Orientation getOrientation();

    /**
     * 切换"全屏/半屏"
     */
    void toggleOrientation();

    /**
     * 设置播放器自动旋转
     *
     * @param autoRotation 播放器自动旋转
     */
    void setAutoRotation(boolean autoRotation);

    /**
     * 判断播放器是否自动旋转
     *
     * @return 播放器是否自动旋转
     */
    boolean isAutoRotation();

    /**
     * 判断是否开启手势调节亮度
     *
     * @return 是否开启手势调节亮度
     */
    boolean isGestureBrightness();

    /**
     * 设置手势调节亮度开关
     *
     * @param gestureBrightness 手势调节亮度开关
     */
    void setGestureBrightness(boolean gestureBrightness);

    /**
     * 判断是否开启手势调节音量
     *
     * @return 是否开启手势调节音量
     */
    boolean isGestureVolume();

    /**
     * 设置手势调节音量开关
     *
     * @param gestureVolume 手势调节音量开关
     */
    void setGestureVolume(boolean gestureVolume);

    /**
     * 判断是否开启手势调节进度
     *
     * @return 是否开启手势调节进度
     */
    boolean isGestureSeekTo();

    /**
     * 设置手势调节进度开关
     *
     * @param gestureSeekTo 手势调节进度开关
     */
    void setGestureSeekTo(boolean gestureSeekTo);

    /**
     * 判读是否开始手势切换"开始/暂停"
     *
     * @return 是否开始手势切换"开始/暂停"
     */
    boolean isGestureToggle();

    /**
     * 设置手势切换"开始/暂停"
     *
     * @param gestureToggle 手势切换"开始/暂停"
     */
    void setGestureToggle(boolean gestureToggle);

    /**
     * 判断是否开启后台播放
     *
     * @return 是否开启后台播放
     */
    boolean isBackgroundPlay();

    /**
     * 设置后台播放开关
     *
     * @param backgroundPlay 后台播放开关
     */
    void setBackgroundPlay(boolean backgroundPlay);

    /**
     * 获取播放器播放状态
     *
     * @return 播放器播放状态
     */
    PlayerState getPlayerState();
}
