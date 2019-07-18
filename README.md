# MediaPlayer

> 媒体播放器：视频、直播、音频

## 1、引入

**1）第一步**

implementation 'com.watayouxiang:mediaplayer:[版本号](https://dl.bintray.com/watayouxiang/maven/com/watayouxiang/mediaplayer/)'

**2） 第二步**

添加三个本地aar包：[AlivcPlayer-3.4.6.aar、AlivcReporter-1.2.aar、AliyunVodPlayer-3.4.6.aar](./mediaplayer/libs)

## 2、基础播放器

> BasePlayer功能：
> 
> - 自动切换 “半屏/全屏”
> - 滑动手势调节 “进度/音量/亮度”、双击手势 “播放/暂停”
> - 进入后台暂停播放，返回前台继续播放
> - 自动播放，循环播放，断网重连
> - 等...

如需自定义播放器样式，继承BasePlayer即可。

## 3、视频播放器

> VideoPlayer样式：
> 
> - 操控栏
> - 视频封面
> - 手势操作提示
> - 视频加载进度提示

```
<com.watayouxiang.mediaplayer.video.VideoPlayer
    android:id="@+id/player"
    android:layout_width="match_parent"
    android:layout_height="200dp" />

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mPlayer = findViewById(R.id.player);
    //设置标题
    mPlayer.setTitle("这是一个视频标题");
    //开启自动播放
    mPlayer.setAutoPlay(true);
    //开启循环播放
    mPlayer.setCirclePlay(true);
    //设置视频源
    mPlayer.prepare(mVideoUrl3);
	...
}


@Override
protected void onResume() {
    super.onResume();
    mPlayer.onResume();
}

@Override
protected void onPause() {
    super.onPause();
    mPlayer.onPause();
}

@Override
protected void onDestroy() {
    super.onDestroy();
    if (mPlayer != null) {
        mPlayer.onDestroy();
        mPlayer = null;
    }
}
```



## 未完待续...