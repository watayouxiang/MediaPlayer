package com.watayouxiang.mediaplayer.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.aliyun.vodplayer.media.AliyunLocalSource;
import com.aliyun.vodplayer.media.AliyunVodPlayer;
import com.aliyun.vodplayer.media.IAliyunVodPlayer;

import java.util.Locale;

class AliSDK extends BaseView implements AliSDKOperation {
    private AliyunVodPlayer mAliyunVodPlayer;//阿里播放器

    // ============================================================================
    // 临时变量
    // ============================================================================
    private Integer mReplaySuccessSeekToPosition;//重播成功后再调节进度
    private PlayerState mBeforeLoadingPlayerState;//进度加载前的播放器状态

    // ============================================================================
    // 需要默认值
    // ============================================================================
    private PlayerState mPlayerState;//播放器状态
    private boolean mAutoPlay;//自动播放开关
    private boolean mCirclePlay;//循环播放开关

    public AliSDK(Context context) {
        super(context);
    }

    public AliSDK(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AliSDK(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInitView(Context context) {
        super.onInitView(context);
        //初始化 AliyunVodPlayer
        mAliyunVodPlayer = new AliyunVodPlayer(context);
        mAliyunVodPlayer.disableNativeLog();//关闭native底层日志
        setAliyunVodPlayerListener(mAliyunVodPlayer, this);
        //初始化 SurfaceView
        SurfaceView surfaceView = new SurfaceView(context);
        addSurfaceViewCallback(surfaceView, mAliyunVodPlayer);
        //添加到本视图
        addView(surfaceView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onInitEvent(Context context) {
        super.onInitEvent(context);
        //初始化配置
        setPlayerState(PlayerState.Idle);
        setAutoPlay(false);
        setCirclePlay(false);
    }

    private void addSurfaceViewCallback(SurfaceView surfaceView, final AliyunVodPlayer aliyunVodPlayer) {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                aliyunVodPlayer.setDisplay(surfaceHolder);
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
                aliyunVodPlayer.surfaceChanged();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });
        aliyunVodPlayer.setDisplay(surfaceHolder);
    }

    private void setAliyunVodPlayerListener(AliyunVodPlayer aliyunVodPlayer, final AliSDK player) {
        //准备成功
        aliyunVodPlayer.setOnPreparedListener(new IAliyunVodPlayer.OnPreparedListener() {
            @Override
            public void onPrepared() {
                player.onPrepared();
            }
        });
        //第一帧显示成功
        aliyunVodPlayer.setOnFirstFrameStartListener(new IAliyunVodPlayer.OnFirstFrameStartListener() {
            @Override
            public void onFirstFrameStart() {
                player.onFirstFrameStart();
            }
        });
        //播放器出错监听
        aliyunVodPlayer.setOnErrorListener(new IAliyunVodPlayer.OnErrorListener() {
            @Override
            public void onError(int errorCode, int errorEvent, String errorMsg) {
                player.onError(errorCode, errorEvent, errorMsg);
            }
        });
        //播放器加载回调
        aliyunVodPlayer.setOnLoadingListener(new IAliyunVodPlayer.OnLoadingListener() {
            @Override
            public void onLoadStart() {
                player.onLoadStart();
            }

            @Override
            public void onLoadEnd() {
                player.onLoadEnd();
            }

            @Override
            public void onLoadProgress(int percent) {
                player.onLoadProgress((float) percent / 100);
            }
        });
        //seek结束事件
        aliyunVodPlayer.setOnSeekCompleteListener(new IAliyunVodPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete() {
                addLog("onSeekComplete");
            }
        });
        //播放结束
        aliyunVodPlayer.setOnCompletionListener(new IAliyunVodPlayer.OnCompletionListener() {
            @Override
            public void onCompletion() {
                player.onCompletion();
            }
        });
        //重播成功
        aliyunVodPlayer.setOnRePlayListener(new IAliyunVodPlayer.OnRePlayListener() {
            @Override
            public void onReplaySuccess() {
                player.onStarted();
                if (mReplaySuccessSeekToPosition != null) {
                    seekTo(mReplaySuccessSeekToPosition);
                    mReplaySuccessSeekToPosition = null;
                }
            }
        });
        //自动播放成功
        aliyunVodPlayer.setOnAutoPlayListener(new IAliyunVodPlayer.OnAutoPlayListener() {
            @Override
            public void onAutoPlayStarted() {
                addLog("onAutoPlayStarted");
            }
        });
        //开始循环播放
        aliyunVodPlayer.setOnCircleStartListener(new IAliyunVodPlayer.OnCircleStartListener() {
            @Override
            public void onCircleStart() {
                addLog("onCircleStart");
            }
        });
        //视频画面大小变化
        aliyunVodPlayer.setOnVideoSizeChangedListener(new IAliyunVodPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(int width, int height) {
                addLog(String.format(Locale.getDefault(), "onVideoSizeChanged: width=%d, height=%d", width, height));
                addInfo(String.format(Locale.getDefault(), "onVideoSizeChanged: width=%d, height=%d", width, height));
            }
        });
        aliyunVodPlayer.setOnBufferingUpdateListener(new IAliyunVodPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(int i) {
                addLog(String.format(Locale.getDefault(), "onBufferingUpdate: i=%d", i));
            }
        });
        aliyunVodPlayer.setOnUrlTimeExpiredListener(new IAliyunVodPlayer.OnUrlTimeExpiredListener() {
            @Override
            public void onUrlTimeExpired(String vid, String quality) {
                addLog(String.format(Locale.getDefault(), "onUrlTimeExpired: vid=%s, quality=%s", vid, quality));
            }
        });
        aliyunVodPlayer.setOnTimeExpiredErrorListener(new IAliyunVodPlayer.OnTimeExpiredErrorListener() {
            @Override
            public void onTimeExpiredError() {
                addLog("onTimeExpiredError");
            }
        });
    }

    private void setPlayerState(PlayerState playerState) {
        if (playerState != getPlayerState()) {
            mPlayerState = playerState;
            onPlayerStateChange(playerState);
        }
    }

    protected void onPlayerStateChange(PlayerState playerState) {
        addLog("播放器状态：" + playerState);
    }

    protected void onFirstFrameStart() {
        addLog("onFirstFrameStart");
    }

    protected void onPrepared() {
        addLog("onPrepared");
        setPlayerState(PlayerState.Prepared);
        if (isAutoPlay()) {
            start();
        }
    }

    protected void onStarted() {
        addLog("onStarted");
        setPlayerState(PlayerState.Started);
    }

    protected void onPaused() {
        addLog("onPaused");
        setPlayerState(PlayerState.Paused);
    }

    protected void onLoadStart() {
        addLog("onLoadStart");
        mBeforeLoadingPlayerState = getPlayerState();
    }

    protected void onLoadProgress(float percent) {
        addLog("onLoadProgress: percent=" + percent);
        setPlayerState(PlayerState.Loading);
    }

    protected void onLoadEnd() {
        addLog("onLoadEnd");
        setPlayerState(mBeforeLoadingPlayerState);
    }

    protected void onCompletion() {
        addLog("onCompletion");
        setPlayerState(PlayerState.Completed);
        if (isCirclePlay()) {
            start();
        }
    }

    protected void onError(int errorCode, int errorEvent, String errorMsg) {
        addLog(String.format(Locale.getDefault(),
                "onError: errorCode=%d, errorEvent=%d, errorMsg=%s",
                errorCode, errorEvent, errorMsg));
        addInfo(String.format(Locale.getDefault(),
                "onError: errorCode=%d, errorEvent=%d, errorMsg=%s",
                errorCode, errorEvent, errorMsg));
        setPlayerState(PlayerState.Error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAliyunVodPlayer != null) {
            mAliyunVodPlayer.release();
            mAliyunVodPlayer = null;
        }
    }

    @Override
    public void prepare(String path) {
        if (path != null) {
            addLog("媒体源：" + path);
            addInfo("媒体源：" + path);
            AliyunLocalSource.AliyunLocalSourceBuilder builder = new AliyunLocalSource.AliyunLocalSourceBuilder();
            builder.setSource(path);
            AliyunLocalSource aliyunLocalSource = builder.build();
            mAliyunVodPlayer.prepareAsync(aliyunLocalSource);
        }
    }

    @Override
    public void start() {
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Prepared
                || playerState == IAliyunVodPlayer.PlayerState.Paused
                || playerState == IAliyunVodPlayer.PlayerState.Stopped
                || playerState == IAliyunVodPlayer.PlayerState.Completed
                || playerState == IAliyunVodPlayer.PlayerState.Error) {
            //先尝试播放
            mAliyunVodPlayer.start();
            if (mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Started) {
                onStarted();
                return;
            }
            //如果播放失败，那么再尝试重播
            mAliyunVodPlayer.replay();
        }
    }

    @Override
    public void pause() {
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started
                || playerState == IAliyunVodPlayer.PlayerState.Error) {
            //先尝试暂停
            mAliyunVodPlayer.pause();
            if (mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Paused) {
                onPaused();
                return;
            }
            //如果暂停失败，那么再尝试停播
            mAliyunVodPlayer.stop();
            if (mAliyunVodPlayer.getPlayerState() == IAliyunVodPlayer.PlayerState.Stopped) {
                onPaused();
            }
        }
    }

    @Override
    public void seekTo(int ms) {
        IAliyunVodPlayer.PlayerState playerState = mAliyunVodPlayer.getPlayerState();
        if (playerState == IAliyunVodPlayer.PlayerState.Started
                || playerState == IAliyunVodPlayer.PlayerState.Paused) {
            mAliyunVodPlayer.seekTo(ms);
        } else if (playerState == IAliyunVodPlayer.PlayerState.Completed) {
            mReplaySuccessSeekToPosition = ms;
            mAliyunVodPlayer.replay();
        }
    }

    @Override
    public void toggle() {
        if (isPlaying()) {
            pause();
        } else {
            start();
        }
    }

    @Override
    public PlayerState getPlayerState() {
        return mPlayerState;
    }

    @Override
    public boolean isPlaying() {
        return getPlayerState() == PlayerState.Started;
    }

    @Override
    public long getDuration() {
        return mAliyunVodPlayer.getDuration();
    }

    @Override
    public long getPosition() {
        return mAliyunVodPlayer.getCurrentPosition();
    }

    @Override
    public long getBufferingPosition() {
        return mAliyunVodPlayer.getBufferingPosition();
    }

    @Override
    public void setAutoPlay(boolean autoPlay) {
        mAutoPlay = autoPlay;
    }

    @Override
    public boolean isAutoPlay() {
        return mAutoPlay;
    }

    @Override
    public void setCirclePlay(boolean circlePlay) {
        mCirclePlay = circlePlay;
    }

    @Override
    public boolean isCirclePlay() {
        return mCirclePlay;
    }

    public void showAliSDKState() {
        IAliyunVodPlayer.PlayerState state = mAliyunVodPlayer.getPlayerState();
        Toast.makeText(getContext(), "AliSDK: " + state, Toast.LENGTH_SHORT).show();
    }
}
