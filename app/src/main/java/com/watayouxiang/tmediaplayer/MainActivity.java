package com.watayouxiang.tmediaplayer;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.watayouxiang.demoshell.DemoActivity;
import com.watayouxiang.demoshell.ListData;
import com.watayouxiang.mediaplayer.video.VideoPlayer;

public class MainActivity extends DemoActivity {
    private VideoPlayer mPlayer;
    //测试视频来源：https://www.jianshu.com/p/5c001dce85b8
    private String mVideoUrl = "http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4";
    private String mVideoUrl2 = "http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4";
    private String mVideoUrl3 = "http://vfx.mtime.cn/Video/2019/03/09/mp4/190309153658147087.mp4";
    private String mVideoUrl4 = "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4";
    //封面图来源：https://movie.douban.com/subject/26848645/
    private String mCoverUrl = "https://img3.doubanio.com/view/photo/l/public/p2543046082.jpg";

    @Override
    protected int getHolderViewId() {
        return R.layout.view_player;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mPlayer = findViewById(R.id.player);
        //设置标题
        mPlayer.setTitle("这是一个视频标题");
        //开启自动播放
        mPlayer.setAutoPlay(true);
        //开启循环播放
        mPlayer.setCirclePlay(true);
        //设置视频源
        mPlayer.prepare(mVideoUrl3);
        //...
    }

    @Override
    protected ListData getListData() {
        return new ListData()
                .addSection("准备视频源")
                .addClick("测试视频1", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl);
                    }
                })
                .addClick("测试视频2", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl2);
                    }
                })
                .addClick("测试视频3", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl3);
                    }
                })
                .addClick("测试视频4", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.prepare(mVideoUrl4);
                    }
                })
                .addSection("播放器操作")
                .addClick("设置标题", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.setTitle("将夜 预告片1：永夜将至版 (中文字幕)");
                    }
                })
                .addClick("设置封面", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.setCoverUrl(mCoverUrl);
                    }
                })
                .addClick("设置自动播放", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.setAutoPlay(!mPlayer.isAutoPlay());
                        ((TextView) v).setText(mPlayer.isAutoPlay() ?
                                "[点击事件] 自动播放（开启）" :
                                "[点击事件] 自动播放（关闭）");
                    }
                })
                .addClick("设置循环播放", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPlayer.setCirclePlay(!mPlayer.isCirclePlay());
                        ((TextView) v).setText(mPlayer.isCirclePlay() ?
                                "[点击事件] 循环播放（开启）" :
                                "[点击事件] 循环播放（关闭）");
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
                        showToast(mPlayer.getPlayerState());
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
