package com.watayouxiang.mediaplayer.core;

interface AliSDKOperation {
    /**
     * 准备
     *
     * @param path 播放地址
     */
    void prepare(String path);

    /**
     * 播放
     * 可操作状态（Prepared，Paused，Completed，Error）
     */
    void start();

    /**
     * 暂停
     * 可操作状态（Started，Error）
     */
    void pause();

    /**
     * 调节进度
     * 可操作状态（Started，Paused，Completed）
     *
     * @param ms 毫秒
     */
    void seekTo(int ms);

    /**
     * 切换"开始/暂停"
     */
    void toggle();

    /**
     * 获取视频时长
     *
     * @return 视频时长
     */
    long getDuration();

    /**
     * 获取视频进度
     *
     * @return 视频进度
     */
    long getPosition();

    /**
     * 获取视频缓冲进度
     *
     * @return 视频缓冲进度
     */
    long getBufferingPosition();
}
