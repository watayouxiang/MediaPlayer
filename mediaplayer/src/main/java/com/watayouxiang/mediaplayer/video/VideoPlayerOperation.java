package com.watayouxiang.mediaplayer.video;

interface VideoPlayerOperation {
    /**
     * 设置标题
     *
     * @param title 标题
     */
    void setTitle(String title);

    /**
     * 设置封面地址
     * <p>
     * 注：只有播放器处于 Idle||Prepared 状态时才有效
     *
     * @param coverUrl 封面地址
     */
    void setCoverUrl(String coverUrl);
}
