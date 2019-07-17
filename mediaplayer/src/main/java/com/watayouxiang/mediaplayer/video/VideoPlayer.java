package com.watayouxiang.mediaplayer.video;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;

import com.watayouxiang.mediaplayer.core.BasePlayer;
import com.watayouxiang.mediaplayer.core.Orientation;
import com.watayouxiang.mediaplayer.core.PlayerState;
import com.watayouxiang.mediaplayer.utils.TikHandler;

public class VideoPlayer extends BasePlayer implements VideoPlayerOperation {

    // ============================================================================
    // 无需释放的资源
    // ============================================================================
    private Activity mActivity;
    private boolean mSeekBarTrackingTouch;//seekBar是否处于拖动状态

    // ============================================================================
    // 需要释放的资源
    // ============================================================================
    private ControllerView mControllerView;
    private LoadingView mLoadingView;
    private GestureView mGestureView;
    private CoverView mCoverView;
    private TikHandler m1000TikHandler;

    public VideoPlayer(Context context) {
        super(context);
    }

    public VideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VideoPlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInitEvent(Context context) {
        super.onInitEvent(context);
        mActivity = (Activity) context;
        m1000TikHandler = new TikHandler(1000, new TikHandler.OnTikListener() {
            @Override
            public void onTik() {
                long duration = getDuration();
                long position = getPosition();
                long bufferingPosition = getBufferingPosition();
                if (!mSeekBarTrackingTouch) {
                    mControllerView.setTime(duration, position);
                    mControllerView.setSeekBar(duration, position, bufferingPosition);
                }
            }
        });
    }

    @Override
    protected void onInitView(Context context) {
        super.onInitView(context);
        mCoverView = initCoverView(context);
        addChildView(mCoverView);
        mControllerView = initControllerView(context);
        addChildView(mControllerView);
        mLoadingView = initLoadingView(context);
        addChildView(mLoadingView);
        mGestureView = initGestureView(context);
        addChildView(mGestureView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mControllerView != null) {
            mControllerView.releaseRes();
            mControllerView = null;
        }
        if (mLoadingView != null) {
            mLoadingView = null;
        }
        if (mGestureView != null) {
            mGestureView = null;
        }
        if (mCoverView != null) {
            mCoverView = null;
        }
        if (m1000TikHandler != null) {
            m1000TikHandler.releaseRes();
            m1000TikHandler = null;
        }
    }

    private void addChildView(View view) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_IN_PARENT);
        addView(view, params);
    }

    private CoverView initCoverView(Context context) {
        CoverView coverView = new CoverView(context);
        coverView.setVisibility(GONE);
        return coverView;
    }

    private GestureView initGestureView(Context context) {
        GestureView gestureView = new GestureView(context);
        gestureView.setVisibility(GONE);
        return gestureView;
    }

    private LoadingView initLoadingView(Context context) {
        LoadingView loadingView = new LoadingView(context);
        loadingView.setVisibility(GONE);
        return loadingView;
    }

    private ControllerView initControllerView(Context context) {
        ControllerView controllerView = new ControllerView(context);
        controllerView.setBackBtn(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen()) {
                    toggleOrientation();
                } else {
                    mActivity.finish();
                }
            }
        });
        controllerView.setToggleBtn(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        controllerView.setFullscreenBtn(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOrientation();
            }
        });
        controllerView.setSeekBar(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mControllerView.setTime(seekBar.getMax(), progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mSeekBarTrackingTouch = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo(seekBar.getProgress());
                mSeekBarTrackingTouch = false;
            }
        });
        return controllerView;
    }

    @Override
    protected void onLoadStart() {
        super.onLoadStart();
        mLoadingView.setVisibility(VISIBLE);
    }

    @Override
    protected void onLoadProgress(float percent) {
        super.onLoadProgress(percent);
        mLoadingView.setProgress(percent);
    }

    @Override
    protected void onLoadEnd() {
        super.onLoadEnd();
        mLoadingView.setVisibility(GONE);
    }

    @Override
    protected void onBrightnessUpdate(float brightnessPercent) {
        super.onBrightnessUpdate(brightnessPercent);
        mGestureView.updateBrightness(brightnessPercent);
    }

    @Override
    protected void onBrightnessEnd() {
        super.onBrightnessEnd();
        mGestureView.setVisibility(GONE);
    }

    @Override
    protected void onVolumeUpdate(float volumePercent) {
        super.onVolumeUpdate(volumePercent);
        mGestureView.updateVolume(volumePercent);
    }

    @Override
    protected void onVolumeEnd() {
        super.onVolumeEnd();
        mGestureView.setVisibility(GONE);
    }

    @Override
    protected void onSeekUpdate(long videoDuration, long videoPosition, long seekPosition) {
        super.onSeekUpdate(videoDuration, videoPosition, seekPosition);
        mGestureView.updateProgress(videoPosition, seekPosition);
    }

    @Override
    protected void onSeekEnd(long seekPosition) {
        super.onSeekEnd(seekPosition);
        mGestureView.setVisibility(GONE);
    }

    @Override
    protected void onOrientationChange(Orientation orientation) {
        super.onOrientationChange(orientation);
        if (isFullScreen()) {
            mControllerView.setFullscreen(GONE);
        } else {
            mControllerView.setFullscreen(VISIBLE);
        }
    }

    @Override
    protected void onSingleTap() {
        super.onSingleTap();
        mControllerView.setVisibility(mControllerView.getVisibility() == VISIBLE ? GONE : VISIBLE);
    }

    @Override
    protected void onPlayerStateChange(PlayerState playerState) {
        super.onPlayerStateChange(playerState);
        mControllerView.setToggleBtn(playerState == PlayerState.Started);
        mControllerView.setAutoHide(playerState == PlayerState.Started);
        if (playerState == PlayerState.Idle
                || playerState == PlayerState.Prepared
                || playerState == PlayerState.Paused
                || playerState == PlayerState.Completed
                || playerState == PlayerState.Error) {
            mControllerView.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onFirstFrameStart() {
        super.onFirstFrameStart();
        mCoverView.setVisibility(GONE);
    }

    @Override
    public void setTitle(String title) {
        mControllerView.setTitle(title);
    }

    @Override
    public void setCoverUrl(String coverUrl) {
        PlayerState playerState = getPlayerState();
        if (playerState == PlayerState.Idle || playerState == PlayerState.Prepared) {
            mCoverView.setCoverUrl(coverUrl);
            mCoverView.setVisibility(VISIBLE);
        }
    }
}
