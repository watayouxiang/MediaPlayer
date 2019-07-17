package com.watayouxiang.tmediaplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.watayouxiang.demoshell.DemoActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.mediaplayer.core.PlayerState;
import com.watayouxiang.mediaplayer.video.VideoPlayer;

public class MainActivity extends DemoActivity {
    private VideoPlayer mPlayer;
    private String mVideoUrl = "http://player.alicdn.com/video/aliyunmedia.mp4";
    private String mVideoUrl2 = "https://nb-live-record.oss-cn-shanghai.aliyuncs.com/video/2018-05-31/Ax8826WfNm.mp4";
    private String mVideoUrl3 = "https://nb-live-record.oss-cn-shanghai.aliyuncs.com/video/2018-06-04/Md8PRFWxbY.mp4";
    private String mLiveUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

    @Override
    protected int getHolderViewId() {
        return R.layout.view_player;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mPlayer = findViewById(R.id.player);
    }

    @Override
    protected ListData getListData() {
        return new ListData()
                .addSection("准备视频源")
                .addClick("视频1", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl);
                    }
                })
                .addClick("视频2", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl2);
                    }
                })
                .addClick("视频3", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl3);
                    }
                })
                .addSection("播放器操作")
                .addClick("设置标题", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.setTitle("这是一个视频标题");
                    }
                })
                .addClick("设置封面", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.setCoverUrl("https://upload.nb350.com/cover/20190715/541_1529160.jpg");
                    }
                })
                .addClick("设置自动播放开关", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPlayer.isAutoPlay()) {
                            mPlayer.setAutoPlay(false);
                            ((TextView) v).setText("自动播放（关闭）");
                        } else {
                            mPlayer.setAutoPlay(true);
                            ((TextView) v).setText("自动播放（开启）");
                        }
                    }
                })
                .addSection("调试操作")
                .addClick("显示播放器信息弹窗", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.showInfoDialog();
                    }
                })
                .addClick("获取播放器状态", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PlayerState playerState = mPlayer.getPlayerState();
                        showToast(playerState);
                    }
                })
                .addClick("显示AliSDK状态", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.showAliSDKState();
                    }
                });
    }

    private void showToast(Object o) {
        Toast.makeText(this, String.valueOf(o), Toast.LENGTH_SHORT).show();
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
}
