package com.watayouxiang.mediaplayer.core;

interface Lifecycle {
    /**
     * 销毁
     */
    void onDestroy();

    /**
     * 回到前台
     */
    void onResume();

    /**
     * 进入后台
     */
    void onPause();
}
