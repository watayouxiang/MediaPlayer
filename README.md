# MediaPlayer

> 媒体播放器：视频、直播、音频

## 1、引入

**1）第一步**

implementation 'com.watayouxiang:mediaplayer:[版本号](https://dl.bintray.com/watayouxiang/maven/com/watayouxiang/mediaplayer/)'

**2） 第二步**

添加三个本地aar包：[AlivcPlayer-3.4.6.aar、AlivcReporter-1.2.aar、AliyunVodPlayer-3.4.6.aar](./mediaplayer/libs)


## 2、视频播放器

```
<com.watayouxiang.mediaplayer.video.VideoPlayer
    android:id="@+id/player"
    android:layout_width="match_parent"
    android:layout_height="200dp" />

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    private VideoPlayer mPlayer = findViewById(R.id.player);
	mPlayer.setTitle("视频1");
	mPlayer.prepare("http://player.alicdn.com/video/aliyunmedia.mp4");
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