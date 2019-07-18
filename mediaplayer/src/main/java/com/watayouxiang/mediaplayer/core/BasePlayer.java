package com.watayouxiang.mediaplayer.core;

import android.content.Context;
import android.util.AttributeSet;

import java.util.Locale;

public class BasePlayer extends AliSDK implements BasePlayerOperation {

    // ============================================================================
    // 需要默认值的参数
    // ============================================================================
    private boolean mAutoRotation;//播放器自动旋转开关
    private Orientation mOrientation;//播放器方向
    private boolean mGestureBrightness;//手势调节亮度开关
    private boolean mGestureVolume;//手势调节音量开关
    private boolean mGestureSeekTo;//手势调节进度开关
    private boolean mGestureToggle;//手势切换"开始/暂停"开关
    private boolean mBackgroundPlay;//台后播放开关

    // ============================================================================
    // 临时变量
    // ============================================================================
    private float mStartScrollBrightnessPercent;//开始滚动前的亮度
    private float mStartScrollVolumePercent;//开始滚动前的音量
    private long mStartScrollVideoPosition;//开始滚动前的视频进度
    private long mStartScrollVideoDuration;//开始滚动前的视频长度
    private long mSeekPosition;//待调节的视频进度
    private boolean mResumePlay;//返回前台是否需要播放

    public BasePlayer(Context context) {
        super(context);
    }

    public BasePlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BasePlayer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onInitEvent(Context context) {
        super.onInitEvent(context);
        //默认配置
        setOrientation(Orientation.Portrait);
        setAutoRotation(true);
        setGestureBrightness(true);
        setGestureVolume(true);
        setGestureSeekTo(true);
        setGestureToggle(true);
        setBackgroundPlay(false);
    }

    protected void onOrientationChange(Orientation orientation) {
        addLog("onOrientationChange：" + orientation);
    }

    protected void onBrightnessStart() {
        addLog("onBrightnessStart");
    }

    protected void onBrightnessUpdate(float brightnessPercent) {
        addLog("onBrightnessUpdate: brightnessPercent=" + brightnessPercent);
    }

    protected void onBrightnessEnd() {
        addLog("onBrightnessEnd");
    }

    protected void onVolumeStart() {
        addLog("onVolumeStart");
    }

    protected void onVolumeUpdate(float volumePercent) {
        addLog("onVolumeUpdate: volumePercent=" + volumePercent);
    }

    protected void onVolumeEnd() {
        addLog("onVolumeEnd");
    }

    protected void onSeekStart() {
        addLog("onSeekStart");
    }

    protected void onSeekUpdate(long videoDuration, long videoPosition, long seekPosition) {
        addLog(String.format(Locale.getDefault(),
                "onSeekUpdate: videoDuration=%d, videoPosition=%d, seekPosition=%d",
                videoDuration, videoPosition, seekPosition));
    }

    protected void onSeekEnd(long seekPosition) {
        addLog("onSeekEnd: seekPosition=" + seekPosition);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mResumePlay) {
            start();
            mResumePlay = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!isBackgroundPlay() && isPlaying()) {
            pause();
            mResumePlay = true;
        }
    }

    @Override
    public void setOrientation(Orientation orientation) {
        if (getOrientation() != orientation) {
            setScreenOrientation(orientation);
            if (orientation == Orientation.Portrait
                    || orientation == Orientation.Portrait_Reverse) {
                setFullScreen(false);
            } else if (orientation == Orientation.Landscape
                    || orientation == Orientation.Landscape_Reverse) {
                setFullScreen(true);
            }
            mOrientation = orientation;
            onOrientationChange(orientation);
        }
    }

    @Override
    public Orientation getOrientation() {
        return mOrientation;
    }

    @Override
    public void toggleOrientation() {
        Orientation orientation = getOrientation();
        if (orientation == Orientation.Portrait
                || orientation == Orientation.Portrait_Reverse) {
            setOrientation(Orientation.Landscape);
        } else if (orientation == Orientation.Landscape
                || orientation == Orientation.Landscape_Reverse) {
            setOrientation(Orientation.Portrait);
        }
    }

    @Override
    public void setAutoRotation(boolean autoRotation) {
        mAutoRotation = autoRotation;
    }

    @Override
    public boolean isAutoRotation() {
        return mAutoRotation;
    }

    @Override
    public boolean isGestureBrightness() {
        return mGestureBrightness;
    }

    @Override
    public void setGestureBrightness(boolean gestureBrightness) {
        mGestureBrightness = gestureBrightness;
    }

    @Override
    public boolean isGestureVolume() {
        return mGestureVolume;
    }

    @Override
    public void setGestureVolume(boolean gestureVolume) {
        mGestureVolume = gestureVolume;
    }

    @Override
    public boolean isGestureSeekTo() {
        return mGestureSeekTo;
    }

    @Override
    public void setGestureSeekTo(boolean gestureSeekTo) {
        mGestureSeekTo = gestureSeekTo;
    }

    @Override
    public boolean isGestureToggle() {
        return mGestureToggle;
    }

    @Override
    public void setGestureToggle(boolean gestureToggle) {
        mGestureToggle = gestureToggle;
    }

    @Override
    public boolean isBackgroundPlay() {
        return mBackgroundPlay;
    }

    @Override
    public void setBackgroundPlay(boolean backgroundPlay) {
        mBackgroundPlay = backgroundPlay;
    }

    @Override
    protected void onStartScroll(ScrollMode mode) {
        super.onStartScroll(mode);
        if (mode == ScrollMode.VERTICAL_LEFT) {
            if (isGestureBrightness()) {
                mStartScrollBrightnessPercent = getBrightnessPercent();
                onBrightnessStart();
            }
        } else if (mode == ScrollMode.VERTICAL_RIGHT) {
            if (isGestureVolume()) {
                mStartScrollVolumePercent = getVolumePercent();
                onVolumeStart();
            }
        } else if (mode == ScrollMode.HORIZONTAL) {
            if (isGestureSeekTo()) {
                mStartScrollVideoPosition = getPosition();
                mStartScrollVideoDuration = getDuration();
                onSeekStart();
            }
        }
    }

    @Override
    protected void onScrolling(ScrollMode mode, float percent) {
        super.onScrolling(mode, percent);
        if (mode == ScrollMode.VERTICAL_LEFT) {
            if (isGestureBrightness()) {
                float brightnessPercent = mStartScrollBrightnessPercent + percent;
                if (brightnessPercent > 1) {
                    brightnessPercent = 1;
                } else if (brightnessPercent < 0) {
                    brightnessPercent = 0;
                }
                setBrightnessPercent(brightnessPercent);
                onBrightnessUpdate(brightnessPercent);
            }
        } else if (mode == ScrollMode.VERTICAL_RIGHT) {
            if (isGestureVolume()) {
                float volumePercent = mStartScrollVolumePercent + percent;
                if (volumePercent > 1) {
                    volumePercent = 1;
                } else if (volumePercent < 0) {
                    volumePercent = 0;
                }
                setVolumePercent(volumePercent);
                onVolumeUpdate(volumePercent);
            }
        } else if (mode == ScrollMode.HORIZONTAL) {
            if (isGestureSeekTo()) {
                long deltaPosition = (long) (mStartScrollVideoDuration * percent);
                mSeekPosition = mStartScrollVideoPosition + deltaPosition;
                if (mSeekPosition > mStartScrollVideoDuration) {
                    mSeekPosition = mStartScrollVideoDuration;
                } else if (mSeekPosition < 0) {
                    mSeekPosition = 0;
                }
                if (mStartScrollVideoDuration > 0) {
                    onSeekUpdate(mStartScrollVideoDuration, mStartScrollVideoPosition, mSeekPosition);
                }
            }
        }
    }

    @Override
    protected void onEndScroll(ScrollMode mode, float percent) {
        super.onEndScroll(mode, percent);
        if (mode == ScrollMode.VERTICAL_LEFT) {
            if (isGestureBrightness()) {
                onBrightnessEnd();
            }
        } else if (mode == ScrollMode.VERTICAL_RIGHT) {
            if (isGestureVolume()) {
                onVolumeEnd();
            }
        } else if (mode == ScrollMode.HORIZONTAL) {
            if (isGestureSeekTo()) {
                seekTo((int) mSeekPosition);
                onSeekEnd(mSeekPosition);
            }
        }
    }

    @Override
    protected void onDoubleTap() {
        super.onDoubleTap();
        if (isGestureToggle()) {
            toggle();
        }
    }

    @Override
    protected void onPhoneOrientationChange(Orientation orientation) {
        super.onPhoneOrientationChange(orientation);
        if (mAutoRotation) {
            setOrientation(orientation);
        }
    }
}
