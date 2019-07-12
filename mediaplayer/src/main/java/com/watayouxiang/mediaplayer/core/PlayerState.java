package com.watayouxiang.mediaplayer.core;

public enum PlayerState {
    /**
     * 闲置
     */
    Idle,
    /**
     * 准备完毕
     */
    Prepared,
    /**
     * 播放中
     */
    Started,
    /**
     * 暂停中
     */
    Paused,
    /**
     * 播放完成
     */
    Completed,
    /**
     * 错误
     */
    Error,
    /**
     * 加载中
     */
    Loading
}
